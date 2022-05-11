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
	public int insertBoardWrite(BoardDto boardWrite) {
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
		String sql = "SELECT * FROM BOARDS ORDER BY BNO";
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

	public BoardDto getBoardInfo(int bno) {
		String sql = "SELECT * FROM BOARDS WHERE BNO=?";
		BoardDto boardInfo = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				// 조회수 증가
				sql = "UPDATE BOARDS SET BHITS=BHITS+1 WHERE BNO=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, bno);
				int updateResult = pstmt.executeUpdate();
				
				boardInfo = new BoardDto();
				boardInfo.setBno(rs.getInt(1));
				boardInfo.setBwriter(rs.getString(2));
				boardInfo.setBtitle(rs.getString(3));
				boardInfo.setBcontents(rs.getString(4));
				boardInfo.setBdate(rs.getString(5));
				boardInfo.setBfilename(rs.getString(6));
				boardInfo.setBhits(rs.getInt(7)+1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return boardInfo;
	}

}
