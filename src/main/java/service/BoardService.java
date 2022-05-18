package service;

import java.io.File;
import java.util.ArrayList;

import dao.BoardDao;
import dto.BoardDto;
import dto.ReplyDto;

public class BoardService {

	private BoardDao bdao = new BoardDao();
	
	// 글 작성
	public int boardWrite(BoardDto boardWrite, String savePath) {
		System.out.println("BoardService.boardWrite() 호출");
		// 글번호 생성
		int maxBno = bdao.getMaxBno();
		int bno = maxBno + 1;	
		boardWrite.setBno(bno);
		System.out.println("bno : " + bno);
		System.out.println(boardWrite);
		
		int insertResult = bdao.insertBoard(boardWrite);
		System.out.println("insertResult : " + insertResult);
		if(insertResult <= 0) {
			// 글 등록 실패 시 파일 삭제
			if (boardWrite.getBfilename() != null ) {
				File delFile = new File(savePath, boardWrite.getBfilename());
				delFile.delete();				
			}	
		}
		return insertResult;
	}

	// 글 목록 조회
	public ArrayList<BoardDto> getBoardList(String orderType) {
		System.out.println("BoardService.getBoardList() 호출");
		if (orderType == null) {
			orderType = "bno";
		}
		ArrayList<BoardDto> boardList = bdao.getBoardList(orderType);		
		System.out.println(boardList);
		// 댓글수 조회
//		for (int i = 0; boardList.size() > i; i++) {
//			int bno = boardList.get(i).getBno();
//			ArrayList<ReplyDto>replyList = getReplyList(bno);
//			int recount = replyList.size();
//			boardList.get(i).setRecount(recount);			
//		}
		return boardList;
	}

//	public int plusBhits(int bno) {
//		System.out.println("BoardService.plusBhits() 호출");
//		int updateResult = bdao.plusBhits(bno);
//		return updateResult;
//	}

	// 글 상세정보 조회
	public BoardDto getBoardView(int bno, boolean check) {
		System.out.println("BoardService.getBoardInfo() 호출");
		BoardDto boardView = bdao.getBoardView(bno);
		
		if(check) { // 글 상세보기인지, 글 수정인지 확인
			// 조회수 증가
			System.out.println("BoardService.plusBhits() 호출");
			bdao.plusBhits(bno);
			// 개행문자 변환
			String bcontents = boardView.getBcontents();
			bcontents = bcontents.replaceAll(" ", "&nbsp;");
			bcontents = bcontents.replaceAll("\r\n", "<br>");
			boardView.setBcontents(bcontents);			
			System.out.println("개행문자 변환 후 : " + boardView.getBcontents());
		}
		return boardView;
	}

	// 글 삭제 (state 변경)
	public int boardDelete(int delBno) {
		System.out.println("BoardService.boardDelete() 호출");
		int delResult = bdao.updateBstate(delBno);
		return delResult;
	}

	// 글 수정
	public int updateBoard(BoardDto modiBoard, String changeBfilename, String originalBfilename, String delOrgnBfilename, String savePath) {
		System.out.println("BoardService.updateBoard() 호출");
		
//		// 기존 첨부파일 있었던 것을 삭제
//		if (delOrgnBfilename.length() == 0 && originalBfilename.length() > 0 && changeBfilename == null) {
//			// 기존 첨부파일 삭제
//			File delFile = new File(savePath, originalBfilename);
//			delFile.delete();		
//			// 기존 첨부파일 없었고 수정 X
//		} else if  (delOrgnBfilename.length() == 0 && originalBfilename.length() == 0 && changeBfilename == null) {
//			
//			// 기존 첨부파일 있었고 수정 X
//		} else if (originalBfilename != null && changeBfilename == null) {
//			// 기존 첨부파일 이름을 set
//			modiBoard.setBfilename(originalBfilename);			
//			// 기존 첨부파일 있었고 파일을 수정
//		} else if (originalBfilename != null && changeBfilename != null) {
//			// 기존 첨부파일 삭제 && 새로운 첨부파일 이름 set
//			File delFile = new File(savePath, originalBfilename);
//			delFile.delete();		
//			modiBoard.setBfilename(changeBfilename);
//		}
		
		
		// 수정X
		if (changeBfilename == null) {
			// 기존 첨부파일 O
			if (delOrgnBfilename.length() > 0 && originalBfilename.length() > 0) {
				// 기존 첨부파일 이름을 set
				modiBoard.setBfilename(originalBfilename);	
			// 기존 첨부파일 삭제
			} else if (delOrgnBfilename.length() == 0 && originalBfilename.length() > 0) {
				modiBoard.setBfilename(changeBfilename);
				changeBfilename = ""; // 수정없이 첨부파일 삭제하고 싶을 때 null값이 아니도록 하기 위함
			}
		// 수정 O
		} else {
			modiBoard.setBfilename(changeBfilename);
		}		
		
		int updateResult = bdao.updateBoard(modiBoard);		
		if (updateResult > 0) { // update 성공 시 기존 파일 삭제
			// 기존 첨부파일 O && 수정파일
			if (originalBfilename.length() > 0 && changeBfilename != null) {
				File delFile = new File(savePath, originalBfilename);
				delFile.delete();
			}
		}
		return updateResult;
	}

	public ArrayList<BoardDto> searchBoard(String searchStr, String searchType) {
		System.out.println("BoardService.searchBoard() 호출");
		ArrayList<BoardDto> searchList = bdao.searchBoard(searchStr, searchType);
		return searchList;
	}

	// 댓글 작성
	public int replyWrite(ReplyDto reply) {
		System.out.println("BoardService.replyWrite() 호출");
		// 댓글 번호 조회
		int maxRenum = bdao.getMaxRenum();
		int renum = maxRenum + 1;
		System.out.println("댓글 번호 : " + renum);
		reply.setRenum(renum);
		
		int insertResult = bdao.insertBoardReply(reply);
		
		return insertResult;
	}

	// 댓글 목록 조회
	public ArrayList<ReplyDto> getReplyList(int bno) {
		System.out.println("BoardService.getReplyList() 호출");
		ArrayList<ReplyDto> replyList = bdao.getReplyList(bno);
		// 개행문자 변환
		for(int i = 0; replyList.size() < i; i++) {
			String recontents = replyList.get(i).getRecontents();
			recontents = recontents.replaceAll(" ", "&nbsp;");
			recontents = recontents.replaceAll("\r\n", "<br>");
			replyList.get(i).setRecontents(recontents);
			System.out.println("개행문자 변환 후 : " + replyList.get(i).getRecontents());			
		}
		return replyList;
	}

	// 댓글 삭제
	public int replyDelete(int renum) {
		System.out.println("BoardService.replyDelete() 호출");
		int deleteResult = bdao.replyDelete(renum);

		return deleteResult;
	}
	
	



}
