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

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dto.BoardDto;
import service.BoardService;

/**
 * Servlet implementation class BoardController
 */
@WebServlet({"/Board/boardWrite", "/Board/boardList", "/Board/boardInfo"})
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
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
						
		String savePath = "D:\\Sun_Workspace\\Jsp_MemberBoard\\src\\main\\webapp\\FileUpload";
		int size = 1024*1024*10;
		
		switch(url) {
		case "/Board/boardWrite":
			System.out.println("글작성 요청");
			
			MultipartRequest multi 
			= new MultipartRequest(request, savePath, size, "UTF-8", new DefaultFileRenamePolicy());
			
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
			
			ArrayList<BoardDto> boardList = bsvc.getBoardList();
			
			request.setAttribute("boardList", boardList);
			dispatcher = request.getRequestDispatcher("BoardList.jsp");
			dispatcher.forward(request, response);
			break;
		
		case "/Board/boardInfo":
			System.out.println("글정보 요청");
			int bno = Integer.parseInt(request.getParameter("bno"));
			BoardDto boardInfo = bsvc.getBoardInfo(bno);
			
			// 조회수 증가
			request.setAttribute("boardInfo", boardInfo);
			dispatcher = request.getRequestDispatcher("BoardView.jsp");
			dispatcher.forward(request, response);
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
