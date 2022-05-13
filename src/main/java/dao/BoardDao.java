package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.BoardDto;

public class BoardDao {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public static Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection
				("jdbc:oracle:thin:@//localhost:1521/xe","KJS_MBOARD","1111"); 
		return con;
	}
	
	public BoardDao() {
		try {
			con = getConnection();
			System.out.println("DB연결 성공!");
		} catch (Exception e) {
			System.out.println("DB연결 실패...");
			e.printStackTrace();
		}
	}

	//글번호 최대값 select
	public int getMaxBno() {
		String sql = "SELECT NVL(MAX(BNO), 0) FROM BOARDS";
		int maxBno = 0;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				maxBno = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return maxBno;
	}

	//글 등록 기능
	public int insertBoard(BoardDto boardWrite) {
		String sql = "INSERT INTO BOARDS(BNO, BWRITER, BTITLE, BCONTENTS, BDATE, BFILENAME, BHITS)"
				+ " VALUES (?,?,?,?,SYSDATE,?,0)";
		int insertResult = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, boardWrite.getBno());
			pstmt.setString(2, boardWrite.getBwriter());
			pstmt.setString(3, boardWrite.getBtitle());
			pstmt.setString(4, boardWrite.getBcontents());
			pstmt.setString(5, boardWrite.getBfilename());
			insertResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		return insertResult;
	}

	// 글 목록 select
	public ArrayList<BoardDto> getBoardList() {
		String sql = "SELECT * FROM BOARDS WHERE BSTATE=0 ORDER BY BNO";
		ArrayList<BoardDto> boardList = new ArrayList<BoardDto>();
		BoardDto board = null;
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				board = new BoardDto();
				board.setBno(rs.getInt(1));
				board.setBwriter(rs.getString(2));
				board.setBtitle(rs.getString(3));
				board.setBcontents(rs.getString(4));
				board.setBdate(rs.getString(5));
				board.setBfilename(rs.getString(6));
				board.setBhits(rs.getInt(7));
				boardList.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return boardList;
	}

	public int plusBhits(int bno) {
		String sql = "UPDATE BOARDS SET BHITS=BHITS+1 WHERE BNO=?";
		int updateResult = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			updateResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return updateResult;
	}
	
	public BoardDto getBoardView(int bno) {
		String sql = "SELECT * FROM BOARDS WHERE BNO=?";
		BoardDto boardView = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardView = new BoardDto();
				boardView.setBno(rs.getInt(1));
				boardView.setBwriter(rs.getString(2));
				boardView.setBtitle(rs.getString(3));
				boardView.setBcontents(rs.getString(4));
				boardView.setBdate(rs.getString(5));
				boardView.setBfilename(rs.getString(6));
				boardView.setBhits(rs.getInt(7)+1); // 증가된 조회수 보이도록
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return boardView;
	}

	public int updateBstate(int delBno) {
		String sql = "UPDATE BOARDS SET BSTATE=1 WHERE BNO=?";
		int delResult = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, delBno);
			delResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return delResult;
	}

	public int updateBoard(BoardDto modiBoard) {
		String sql = "UPDATE BOARDS SET BTITLE=?, BCONTENTS=?, BFILENAME=?"
					+ " WHERE BNO=?";
		int updateResult = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, modiBoard.getBtitle());
			pstmt.setString(2, modiBoard.getBcontents());
			pstmt.setString(3, modiBoard.getBfilename());
			pstmt.setInt(4, modiBoard.getBno());
			updateResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return updateResult;
	}

	public ArrayList<BoardDto> searchBoard(String searchStr, String searchType) {
		String sql = "SELECT * FROM BOARDS WHERE " + searchType + " LIKE '%'||?||'%'";
		ArrayList<BoardDto> searchList = new ArrayList<BoardDto>();
		BoardDto searchBoard = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchStr);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				searchBoard = new BoardDto();
				searchBoard.setBno(rs.getInt(1));
				searchBoard.setBwriter(rs.getString(2));
				searchBoard.setBtitle(rs.getString(3));
				searchBoard.setBcontents(rs.getString(4));
				searchBoard.setBdate(rs.getString(5));
				searchBoard.setBfilename(rs.getString(6));
				searchBoard.setBhits(rs.getInt(7));
				searchList.add(searchBoard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return searchList;
	}


}
