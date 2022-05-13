package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.MemberDto;
import service.MemberService;

/**
 * Servlet implementation class MemberController
 */
@WebServlet({"/Member/memberIdCheck", "/Member/memberJoin", "/Member/memberLogin", "/Member/memberLogout", 
			"/Member/memberInfo", "/Member/memberModifyForm", "/Member/memberModify"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String url = request.getServletPath();
		System.out.println("url : " + url);
		String contaxtPath = request.getContextPath();
		System.out.println("contaxtPath : " + contaxtPath);
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		String afterUrl;
		
		request.setCharacterEncoding("UTF-8");
		MemberService msvc = new MemberService();
					
		switch(url) {
		
		case "/Member/memberIdCheck":
			System.out.println("아이디 중복확인 요청");
			String confirmId = request.getParameter("inputId");
			System.out.println("중복확인 할 아이디 : " + confirmId);
			// 아이디 중복확인 서비스 호출 (검색결과가 없으면 OK, 있으면 NO)
			String result = msvc.memberIdCheck(confirmId);
			response.getWriter().append(result);			
			break;
			
		case "/Member/memberJoin":
			System.out.println("회원가입 요청");
			String memberId = request.getParameter("memberId");					
			String memberPw = request.getParameter("memberPw");					
			String memberName = request.getParameter("memberName");					
			String memberBirth = request.getParameter("memberBirth");					
			String memberEmail = request.getParameter("memberEmailId") + "@" + request.getParameter("memberEmailDomain");					
			String memberAddr = request.getParameter("memberPostCode") + "/"
								+ request.getParameter("memberAddress") + "/"
								+ request.getParameter("memberDetailAddress") + "/"
								+ request.getParameter("memberExtraAddress");
		
			System.out.println("memberId : " + memberId);
			System.out.println("memberPw : " + memberPw);
			System.out.println("memberName : " + memberName);
			System.out.println("memberBirth : " + memberBirth);
			System.out.println("memberEmail : " + memberEmail);
			System.out.println("memberAddr : " + memberAddr);
			
			MemberDto joinMember = new MemberDto();
			joinMember.setMid(memberId);
			joinMember.setMpw(memberPw);
			joinMember.setMname(memberName);
			joinMember.setMbirth(memberBirth);
			joinMember.setMemail(memberEmail);
			joinMember.setMaddress(memberAddr);
			
			int insertResult = msvc.memberJoin(joinMember);
			if (insertResult > 0) {
				System.out.println("회원가입 성공");
				response.sendRedirect(contaxtPath + "/MainPage.jsp");
			} else {
				System.out.println("회원가입 실패");
			}
			break;
			
		case "/Member/memberLogin":
			System.out.println("로그인 요청");
			String inputId = request.getParameter("userId");
			String inputPw = request.getParameter("userPw");
			afterUrl = request.getParameter("afterUrl");
			System.out.println("입력한 아이디 : " + inputId);
			System.out.println("입력한 비밀번호 : " + inputPw);
			System.out.println("로그인 후 URL : " + afterUrl);
			
			// 1. 일치하는 회원정보 확인
			String loginId = msvc.memberLogin(inputId, inputPw);
			if (loginId != null) {
				System.out.println("로그인 성공");
				// 로그인 처리 - session에 로그인 아이디 저장
				session.setAttribute("loginId", loginId);
				// 파라미터가 아니라 세션 영역에 저장했기 때문에 브라우저에 소속. 값이 계속해서 남아있음.
				if (afterUrl.length() > 0) {
					// afterUrl이 있을 경우 로그인 후 글목록페이지로
					response.sendRedirect(contaxtPath + "/" + afterUrl);
				} else {
					// afterUrl이 없을 경우 로그인 후 메인페이지로
					response.sendRedirect(contaxtPath + "/MainPage.jsp");					
				}
			} else {
				System.out.println("로그인 실패");
				String errorMsg = "아이디나 비밀번호가 일치하지 않습니다.";
				response.sendRedirect(contaxtPath + "/Member/MemberLoginForm.jsp?checkMsg="
						+ URLEncoder.encode(errorMsg, "UTF-8"));
			}
			break;
			
		case "/Member/memberLogout":
			System.out.println("로그아웃 요청");
			session.invalidate(); // 세션값 초기화
//			session.removeAttribute("loginId"); // 지정된 Attribute만 삭제
			response.sendRedirect(contaxtPath + "/MainPage.jsp");
			
			// 경로 테스트
//			response.sendRedirect("/Member/MainPage.jsp"); // X. 절대경로 :: http://localhost:8080/Member/MainPage.jsp
//			response.sendRedirect("/MainPage.jsp"); // X. 절대경로. :: http://localhost:8080/MainPage.jsp
//			response.sendRedirect("/Jsp_MemberBoard/MainPage.jsp"); // O. 절대경로. :: http://localhost:8080/Jsp_MemberBoard/MainPage.jsp
//			response.sendRedirect("Member/MainPage.jsp"); // X. 상대경로 :: http://localhost:8080/Jsp_MemberBoard/Member/Member/MainPage.jsp
//			response.sendRedirect("MainPage.jsp"); // X. 상대경로 :: http://localhost:8080/Jsp_MemberBoard/Member/MainPage.jsp
//			response.sendRedirect("../MainPage.jsp"); // O. 상대경로 :: http://localhost:8080/Jsp_MemberBoard/MainPage.jsp
			/* redirect 방식은 브라우저에서 호출됨
			 * 절대경로의 기준은 IP 주소
			 * 상대경로의 기준은 호출된 서블릿 맵핑주소
			 */			
			break;
		
		case "/Member/memberInfo":
			System.out.println("내정보확인 요청");
			String session_mid = (String) session.getAttribute("loginId");
			System.out.println("세션 아이디 : " + session_mid);
			String param_mid = request.getParameter("mid");
			System.out.println("파라미터 아이디 : " + param_mid);
	
			if (session_mid != null) {
				MemberDto memberInfo = msvc.getMemberInfo(session_mid);
				if (memberInfo != null) {
					request.setAttribute("memberInfo", memberInfo);
					dispatcher = request.getRequestDispatcher("/Member/MemberInfo.jsp");					
					dispatcher.forward(request, response);	
				}
			} else {
				response.sendRedirect(contaxtPath + "/Board/Fail.jsp?checkMsg=" +
									URLEncoder.encode("로그인 후 사용 가능합니다.", "UTF-8"));
			}
			break;
				
				// 경로 테스트
//				dispatcher = request.getRequestDispatcher("MainPage.jsp"); // X. 상대경로 :: 파일 [/Member/MainPage.jsp]을(를) 찾을 수 없습니다.
//				dispatcher = request.getRequestDispatcher("../MainPage.jsp"); // O. 상대경로
//				dispatcher = request.getRequestDispatcher("/MainPage.jsp"); // O. 절대경로
				
//				dispatcher = request.getRequestDispatcher("MemberInfo.jsp"); // O. 상대경로
//				dispatcher = request.getRequestDispatcher("/MemberInfo.jsp"); // X. 절대경로
//				dispatcher = request.getRequestDispatcher("/Member/MemberInfo.jsp"); // O. 절대경로
				/* dispatcher 방식은 서블릿에서 호출됨
				 * 절대경로의 기준은 현재 프로젝트 경로(contextPath)
				 * 상대경로의 기준은 호출된 서블릿 맵핑주소
				 */
	
		case "/Member/memberModifyForm":
			System.out.println("정보수정페이지 요청");
			String loginid = (String) session.getAttribute("loginId");
			System.out.println("로그인 아이디 : " + loginid);
			if (loginid != null) {
				// 회원정보 조회
				MemberDto memberModiInfo = msvc.getMemberInfo(loginid);
				request.setAttribute("memberInfo", memberModiInfo);
				dispatcher = request.getRequestDispatcher("/Member/MemberModifyForm.jsp");
				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(contaxtPath + "/Board/Fail.jsp?checkMsg=" +
						URLEncoder.encode("로그인 후 사용 가능합니다.", "UTF-8"));
			}
			break;
			
		case "/Member/memberModify":
			System.out.println("정보수정 요청");
			MemberDto modifyInfo = new MemberDto();
						
			memberId = request.getParameter("memberId");					
			memberPw = request.getParameter("memberPw");					
			memberName = request.getParameter("memberName");					
			memberBirth = request.getParameter("memberBirth");					
			memberEmail = request.getParameter("memberEmailId") + "@" + request.getParameter("memberEmailDomain");					
			memberAddr = request.getParameter("memberPostCode") + "/"
								+ request.getParameter("memberAddress") + "/"
								+ request.getParameter("memberDetailAddress") + "/"
								+ request.getParameter("memberExtraAddress");
			modifyInfo.setMid(memberId);
			modifyInfo.setMpw(memberPw);
			modifyInfo.setMname(memberName);
			modifyInfo.setMbirth(memberBirth);
			modifyInfo.setMemail(memberEmail);
			modifyInfo.setMaddress(memberAddr);
			
			int updateResult = msvc.memberModify(modifyInfo);
			if (updateResult > 0) {
				response.sendRedirect(contaxtPath + "/Member/memberInfo");
			} else {
				response.sendRedirect(contaxtPath + "/Board/Fail.jsp?checkMsg=" 
									+ URLEncoder.encode("회원정보 수정에 실패했습니다.", "UTF-8"));
			}			
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
