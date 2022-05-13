package service;

import java.io.File;
import java.util.ArrayList;

import dao.BoardDao;
import dto.BoardDto;

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
	public ArrayList<BoardDto> getBoardList() {
		System.out.println("BoardService.getBoardList() 호출");
		ArrayList<BoardDto> boardList = bdao.getBoardList();		
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
	public int updateBoard(BoardDto modiBoard) {
		System.out.println("BoardService.updateBoard() 호출");
		int updateResult = bdao.updateBoard(modiBoard);
		return updateResult;
	}

	public ArrayList<BoardDto> searchBoard(String searchStr, String searchType) {
		System.out.println("BoardService.searchBoard() 호출");
		ArrayList<BoardDto> searchList = bdao.searchBoard(searchStr, searchType);
		return searchList;
	}



}
