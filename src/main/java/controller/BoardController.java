package controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.BoardDto;
import dto.ReplyDto;
import service.BoardService;

/**
 * Servlet implementation class BoardController
 */
@WebServlet({"/Board/boardWrite", "/Board/boardList", "/Board/boardView",
			"/Board/boardModiInfo", "/Board/boardModify", "/Board/boardDelete",	"/Board/boardSearch",
			"/Board/replyWrite", "/Board/replyWrite_ajax", "/Board/replyList_ajax", 
			"/Board/replyDelete", "/Board/replyDelete_ajax",
			"/Board/replyModify_ajax"})
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
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
		String contextPath = request.getContextPath();
		System.out.println("contextPath : " + contextPath);

		BoardService bsvc = new BoardService();
		request.setCharacterEncoding("UTF-8");
		
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		MultipartRequest multi;
						
		String savePath = "D:\\Sun_Workspace\\Jsp_MemberBoard\\src\\main\\webapp\\FileUpload";
		int size = 1024*1024*10;
		
		switch(url) {
		case "/Board/boardWrite":
			System.out.println("글작성 요청");
			
			multi = new MultipartRequest(request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
			
			String bwriter = (String) session.getAttribute("loginId");
			String btitle = multi.getParameter("btitle");
			String bcontents = multi.getParameter("bcontents");
			String bfilename = multi.getFilesystemName( (String)multi.getFileNames().nextElement() );
			// getOriginalFileName : 원본 파일명
			// getFilesystemName : 중복이름 처리 후의 파일명
			BoardDto boardWrite = new BoardDto();
			boardWrite.setBwriter(bwriter);
			boardWrite.setBtitle(btitle);
			boardWrite.setBcontents(bcontents);
			boardWrite.setBfilename(bfilename);			
			
			int insertResult = bsvc.boardWrite(boardWrite, savePath);
			if (insertResult > 0) {
				// 글 작성 성공
				response.sendRedirect(contextPath + "/Board/boardList");
			} else {
				// 글 작성 실패				
				String checkMsg = "글 작성에 실패했습니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(checkMsg, "UTF-8"));
			}
			break;
		
		case "/Board/boardList":
			System.out.println("글목록 요청");
			String orderType = request.getParameter("orderType");
			System.out.println("글목록 정렬 타입 : " + orderType);
			
			ArrayList<BoardDto> boardList = bsvc.getBoardList(orderType);
			
			request.setAttribute("boardList", boardList);
			// 파라미터 있는지 확인
//			String checkMsg2 = request.getParameter("successMsg");
//			System.out.println("boardList에서의 checkMsg : " + checkMsg2);
//			if (checkMsg2==null) {
				// 없으면 단순히 글목록 페이지
				dispatcher = request.getRequestDispatcher("BoardList.jsp");				
//			} else {
//				// 있으면 파라미터값 함께 보내기
//				dispatcher = request.getRequestDispatcher("BoardList.jsp?checkMsg=" + checkMsg2);				
//			}
			/* 2. delete 성공 후 넘어와서 보내준 checkMsg 파라미터가 request 영역에 담겨있음.
			 * 3. 디스패처로 forward 하기 때문에 request 영역에 살아있는 상태로 BoardList.jsp 페이지 이동
			 * 4. BoardList.jsp에서 request 영역의 파라미터값이 있어서 alert 창이 실행됨 */
			dispatcher.forward(request, response);
			break;
			
		case "/Board/boardDelete":
			System.out.println("글삭제 요청");			
			// 로그인 된 상태인지 확인
			// 보다 정확하게 하기 위해서 세션의 loginId와 글삭제 요청을 한 계정 ID가 같은지 비교해야함.
			String loginCheck = (String) session.getAttribute("loginId");
			String writerCheck = request.getParameter("bwriter");
			System.out.println("loginCheck : " + loginCheck);
			System.out.println("writerCheck : " + writerCheck);
			if(loginCheck == null || !loginCheck.equals(writerCheck)) {
				String loginConfirm = "잘못된 요청입니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(loginConfirm, "UTF-8"));
				return;
			}
			// bstate 수정
			int delBno = Integer.parseInt(request.getParameter("bno"));
			System.out.println("삭제할 글 번호 : " + delBno);
			int delResult = bsvc.boardDelete(delBno);
			if (delResult > 0) {
				// 글 삭제 성공
				String delSuccessMsg = delBno + "번 글이 삭제되었습니다.";
				response.sendRedirect(contextPath + "/Board/boardList?checkMsg="
						+ URLEncoder.encode(delSuccessMsg, "UTF-8"));		
				/* 1. 여기서 sendRedirect로 보내준 request 파라미터값이 boardList 서블릿에서 살아있음 */
			} else {
				// 글 삭제 실패
				String delFailMsg = delBno + "번 글 삭제에 실패했습니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(delFailMsg, "UTF-8"));
			}
			break;		
			
		case "/Board/boardView":
			System.out.println("글정보 요청");
			int bno = Integer.parseInt(request.getParameter("bno"));		
			// 글내용 상세 조회
			BoardDto boardView = bsvc.getBoardView(bno, true);
			request.setAttribute("boardView", boardView);
			
			// 댓글목록 조회
			ArrayList<ReplyDto> replyList = bsvc.getReplyList(bno);
			request.setAttribute("replyList", replyList);
			System.out.println("bno : " + bno);
			
			dispatcher = request.getRequestDispatcher("BoardView.jsp");
			dispatcher.forward(request, response);
			break;
		
			
		case "/Board/boardModiInfo":
			System.out.println("수정할 글정보 요청");
			// 로그인 된 상태인지 확인
			// 보다 정확하게 하기 위해서 세션의 loginId와 글삭제 요청을 한 계정 ID가 같은지 비교해야함.
			loginCheck = (String) session.getAttribute("loginId");
			writerCheck = request.getParameter("bwriter");
			System.out.println("loginCheck : " + loginCheck);
			System.out.println("writerCheck : " + writerCheck);
			if(loginCheck == null || !loginCheck.equals(writerCheck)) {
				String loginConfirm = "잘못된 요청입니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(loginConfirm, "UTF-8"));
				return;
			}
						
			int modiBno = Integer.parseInt(request.getParameter("bno"));
			System.out.println("수정할 글 번호 : " + modiBno);
			BoardDto modiBoardView = bsvc.getBoardView(modiBno, false);
			
			request.setAttribute("boardView", modiBoardView);
			dispatcher = request.getRequestDispatcher("BoardModifyForm.jsp");
			dispatcher.forward(request, response);
			break;
		
		case "/Board/boardModify":
			System.out.println("글수정 요청");			
						
			multi = new MultipartRequest(request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
			BoardDto modiBoard = new BoardDto();
			modiBoard.setBno(Integer.parseInt(multi.getParameter("bno")));
			modiBoard.setBtitle(multi.getParameter("btitle"));
			modiBoard.setBcontents(multi.getParameter("bcontents"));

			String changeBfilename = multi.getFilesystemName( (String)multi.getFileNames().nextElement());
			String originalBfilename = multi.getParameter("originalBfile");
			String delOrgnBfilename = multi.getParameter("delOrgnBfile");
			System.out.println("changeBfilename : " + changeBfilename);
			System.out.println("originalBfilename : " + originalBfilename);
			System.out.println("delOrgnBfilename : " + delOrgnBfilename);
			
//			첨부파일 삭제 기능이 없을 때의 조건문
//			modiBoard.setBfilename(changeBfilename);
//			if (originalBfilename != null && changeBfilename != null) {
//				// 첨부파일이 있었는데 수정하면서 새로운 파일 첨부
//				File delFile = new File(savePath, originalBfilename);
//				delFile.delete();
//			} else if (originalBfilename != null && changeBfilename == null) {
//				// 첨부파일이 있었고 수정하지 않음
//				modiBoard.setBfilename(originalBfilename);
//			} else {
//				// 첨부파일이 없었는데 수정하면서 첨부
//			}
			
			int updateResult = bsvc.updateBoard(modiBoard, changeBfilename, originalBfilename, delOrgnBfilename, savePath);
			if( updateResult > 0 ) {
				// 글 수정 성공
				response.sendRedirect(contextPath + "/Board/boardView?bno=" + modiBoard.getBno());
			} else {
				// 글 수정 실패
				String modiFailkMsg = modiBoard.getBno() + "번 글 수정에 실패했습니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(modiFailkMsg, "UTF-8"));
			}
			break;			
		
		case "/Board/boardSearch":
			System.out.println("글목록 검색 요청");
			String searchText = request.getParameter("searchText");
			String searchType = request.getParameter("searchType");
			ArrayList<BoardDto> searchList = bsvc.searchBoard(searchText, searchType);
			request.setAttribute("boardList", searchList);
			dispatcher = request.getRequestDispatcher("/Board/BoardList.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/Board/replyWrite":
			System.out.println("댓글 작성 요청");
			int rebno = Integer.parseInt( request.getParameter("rebno") );
			String rewriter = request.getParameter("rewriter");
			String recontents = request.getParameter("recontents");
			int restate = Integer.parseInt( request.getParameter("restate") );
			
			ReplyDto reply = new ReplyDto();
			reply.setRebno(rebno);
			reply.setRewriter(rewriter);
			reply.setRecontents(recontents);
			reply.setRestate(restate);
			System.out.println(reply);
			
			insertResult = bsvc.replyWrite(reply);
			if (insertResult > 0) {
				String WriteSuccess = "댓글이 등록되었습니다.";
				response.sendRedirect(contextPath + "/Board/boardView?bno=" + rebno
									+ "&checkMsg=" + URLEncoder.encode(WriteSuccess, "UTF-8"));
			} else {
				String FailkMsg = rebno + "번 글 댓글 작성에 실패했습니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg="
									+ URLEncoder.encode(FailkMsg, "UTF-8"));
			}
			
			break;
			
		case "/Board/replyWrite_ajax":
			System.out.println("ajax로 댓글 작성 요청");
			rebno = Integer.parseInt(request.getParameter("rebno"));
			restate = Integer.parseInt(request.getParameter("restate"));
			rewriter = request.getParameter("rewriter");
			recontents = request.getParameter("recontents");
			reply = new ReplyDto();
			reply.setRebno(rebno);
			reply.setRewriter(rewriter);
			reply.setRecontents(recontents);
			reply.setRestate(restate);
			
			insertResult = bsvc.replyWrite(reply);
			System.out.println(insertResult);
			if (insertResult > 0) {
				String result = "OK";
				response.getWriter().append(result);
			}
			break;
		
		case "/Board/replyList_ajax":
			System.out.println("댓글 목록 요청_ajax");
			bno = Integer.parseInt(request.getParameter("bno"));
			System.out.println("댓글목록 조회할 글 번호 : " + bno);
			ArrayList<ReplyDto> ajaxReplyList = bsvc.getReplyList(bno);
			System.out.println(ajaxReplyList);
			// ajaxReplyList >> json 형식으로 변환 (스크립트에서 활용하는 형태)
			// {"renum" : 10, "rebno : 1 ...}
			
			Gson gson = new Gson();
			
			String replyList_json = gson.toJson(ajaxReplyList);
			System.out.println(replyList_json);
			
			response.getWriter().println(replyList_json);
			break;
			
		case "/Board/replyDelete":
			System.out.println("댓글 삭제 요청");
			int renum = Integer.parseInt(request.getParameter("renum"));
			bno = Integer.parseInt(request.getParameter("bno"));
			System.out.println("삭제하고자 하는 댓글 번호 : " + renum);
			System.out.println("삭제 후 이동할 글 번호 : " + bno);
			int deleteResult = bsvc.replyDelete(renum);
			if (deleteResult > 0) {
				System.out.println("댓글 삭제 성공");
				String deleteSuccess = renum + "번 댓글이 삭제되었습니다.";
				response.sendRedirect(contextPath + "/Board/boardView?bno=" + bno
									+ "&checkMsg=" + URLEncoder.encode(deleteSuccess, "UTF-8"));
			} else {
				System.out.println("댓글 삭제 실패");
				String deleteFail = renum + "번 댓글 삭제에 실패하였습니다.";
				response.sendRedirect(contextPath + "/Board/Fail.jsp?checkMsg=" 
									+ URLEncoder.encode(deleteFail, "UTF-8"));
			}
			break;

		case "/Board/replyDelete_ajax":
			System.out.println("댓글 삭제 요청_ajax");
			renum = Integer.parseInt(request.getParameter("renum"));
			deleteResult = bsvc.replyDelete(renum);
			String result = null;
			if (deleteResult > 0) {
				result = "OK";
			} else {
				result = "Fail";
			}
			response.getWriter().append(result);
			break;
		
		case "/Board/replyModify_ajax":
			System.out.println("댓글 수정 요청_ajax");
			renum = Integer.parseInt(request.getParameter("renum"));
			
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
