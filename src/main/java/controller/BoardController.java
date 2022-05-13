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
@WebServlet({"/Board/boardWrite", "/Board/boardList", "/Board/boardView",
			"/Board/boardModiInfo", "/Board/boardModify", "/Board/boardDelete"})
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
			
			ArrayList<BoardDto> boardList = bsvc.getBoardList();
			
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
			// 글 상세 조회
			BoardDto boardView = bsvc.getBoardView(bno, true);
		
			
			request.setAttribute("boardView", boardView);
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
			
			// 기존 첨부파일 있었던 것을 삭제
			if (delOrgnBfilename.length() == 0 && originalBfilename.length() > 0 && changeBfilename == null) {
				// 기존 첨부파일 삭제
				File delFile = new File(savePath, originalBfilename);
				delFile.delete();		
			// 기존 첨부파일 없었고 수정 X
			} else if  (delOrgnBfilename.length() == 0 && originalBfilename.length() == 0 && changeBfilename == null) {
				
				
			// 기존 첨부파일 있었고 수정 X
			} else if (originalBfilename != null && changeBfilename == null) {
				// 기존 첨부파일 이름을 set
				modiBoard.setBfilename(originalBfilename);
				
			// 기존 첨부파일 있었고 파일을 수정
			} else if (originalBfilename != null && changeBfilename != null) {
				// 기존 첨부파일 삭제 && 새로운 첨부파일 이름 set
				File delFile = new File(savePath, originalBfilename);
				delFile.delete();		
				modiBoard.setBfilename(changeBfilename);
			}

//			첨부파일 삭제 기능이 없을 때의 조건문
//			if (originalBfilename != null && modiBoard.getBfilename() != null) {
//				// 첨부파일이 있었는데 수정하면서 새로운 파일 첨부
//				File delFile = new File(savePath, originalBfilename);
//				delFile.delete();
//			} else if (originalBfilename != null && modiBoard.getBfilename() == null) {
//				// 첨부파일이 있었고 수정하지 않음
//				modiBoard.setBfilename(originalBfilename);
//			} else {
//				// 첨부파일이 없었는데 수정하면서 첨부
//			}
			
			int updateResult = bsvc.updateBoard(modiBoard);
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
