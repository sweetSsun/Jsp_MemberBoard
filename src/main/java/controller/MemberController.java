package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("/Member/memberJoin")
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
		request.setCharacterEncoding("UTF-8");
					
		switch(url) {
		case "/Member/memberJoin":
			System.out.println("회원가입 요청");
			String memberId = request.getParameter("memberId");					
			String memberPw = request.getParameter("memberPw");					
			String memberName = request.getParameter("memberName");					
			String memberBirth = request.getParameter("memberBirth");					
			String memberEmail = request.getParameter("memberEmailId") + "@" + request.getParameter("memberEmailDomain");					
			String memberAddr = "(" + request.getParameter("memberPostCode") + ") "
										+ request.getParameter("memberAddress") + " "
										+ request.getParameter("memberDetailAddress")
										+ request.getParameter("memberExtraAddress");
		
			System.out.println("memberId : " + memberId);
			System.out.println("memberPw : " + memberPw);
			System.out.println("memberName : " + memberName);
			System.out.println("memberBirth : " + memberBirth);
			System.out.println("memberEmail : " + memberEmail);
			System.out.println("memberAddr : " + memberAddr);
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
