package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.MemberDto;

public class MemberDao {

	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public static Connection getConnection() throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection
				("jdbc:oracle:thin:@//localhost:1521/xe","KJS_MBOARD","1111"); 
		return con;
	}
	
	public MemberDao() {
		try {
			con = getConnection();
			System.out.println("DB연결 성공!");
		} catch (Exception e) {
			System.out.println("DB연결 실패...");
			e.printStackTrace();
		}
	}

	public int memberJoin(MemberDto joinMember) {
		String sql = "INSERT INTO MEMBERS VALUES (?,?,?,TO_DATE(?, 'YYYY-MM-DD'),?,?)";
		int insertResult = 0;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, joinMember.getMid());
			pstmt.setString(2, joinMember.getMpw());
			pstmt.setString(3, joinMember.getMname());
			pstmt.setString(4, joinMember.getMbirth());
			pstmt.setString(5, joinMember.getMemail());
			pstmt.setString(6, joinMember.getMaddress());
			insertResult = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return insertResult;
	}

	public String memberLogin(String inputId, String inputPw) {
		String sql = "SELECT MID FROM MEMBERS WHERE MID=? AND MPW=?";
		String loginId = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, inputId);
			pstmt.setString(2, inputPw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				loginId = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return loginId;
	}

	public MemberDto getMemberInfo(String loginedId) {
		String sql = "SELECT MID, MPW, MNAME, TO_CHAR(MBIRTH, 'YYYY.MM.DD'), MEMAIL, MADDRESS"
				+ " FROM MEMBERS WHERE MID=?";
		MemberDto memberInfo = null;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginedId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				memberInfo = new MemberDto();
				memberInfo.setMid(rs.getString(1));
				memberInfo.setMpw(rs.getString(2));
				memberInfo.setMname(rs.getString(3));
				memberInfo.setMbirth(rs.getString(4));
				memberInfo.setMemail(rs.getString(5));
				memberInfo.setMaddress(rs.getString(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return memberInfo;
	}

	public String selectMemberIdCheck(String confirmId) {
		String sql = "SELECT MID FROM MEMBERS WHERE MID=?";
		String checkResult = "NO";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, confirmId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				checkResult = "OK";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkResult;
	}
	
	
	
	
}
