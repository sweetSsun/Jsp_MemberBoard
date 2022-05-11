package service;

import java.io.File;
import java.util.ArrayList;

import dao.BoardDao;
import dto.BoardDto;

public class BoardService {

	private BoardDao bdao = new BoardDao();
	
	public int boardWrite(BoardDto boardWrite, String savePath) {
		System.out.println("BoardService.boardWrite() 호출");
		// 글번호 생성
		int maxBno = bdao.getMaxBno();
		int bno = maxBno + 1;	
		boardWrite.setBno(bno);
		System.out.println("bno : " + bno);
		System.out.println(boardWrite);
		
		int insertResult = bdao.insertBoardWrite(boardWrite);
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

	public ArrayList<BoardDto> getBoardList() {
		System.out.println("BoardService.getBoardList() 호출");
		ArrayList<BoardDto> boardList = bdao.getBoardList();		
		return boardList;
	}

	public BoardDto getBoardInfo(int bno) {
		System.out.println("BoardService.getBoardInfo() 호출");
		BoardDto boardInfo = bdao.getBoardInfo(bno);
		return boardInfo;
	}



}
