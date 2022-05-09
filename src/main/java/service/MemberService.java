package service;

import dao.MemberDao;
import dto.MemberDto;

public class MemberService {

	private MemberDao mdao = new MemberDao();
	public int memberJoin(MemberDto joinMember) {
		System.out.println("MemberService.memberJoin() 호출");
		int insertResult = mdao.memberJoin(joinMember);
		System.out.println("insertResult : " + insertResult);
		return insertResult;
	}
	public String memberLogin(String inputId, String inputPw) {
		System.out.println("MemberService.memberLogin() 호출");
		String loginId = mdao.memberLogin(inputId, inputPw);
		System.out.println("loginId : " + loginId);
		return loginId;
	}

}
