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
	public MemberDto getMemberInfo(String loginedId) {
		System.out.println("MemberService.getMemberInfo() 호출");
		MemberDto memberInfo = mdao.getMemberInfo(loginedId);
		
		// 이메일
		String memail = memberInfo.getMemail();
		String memailId = memail.split("@")[0];
		String memailDomain = memail.split("@")[1];
		memberInfo.setMemailId(memailId);
		memberInfo.setMemailDomain(memailDomain);
		
		String maddress = memberInfo.getMaddress();
		String[] maddress_split = maddress.split("/");
		memberInfo.setMemberPostCode(maddress_split[0]);
		memberInfo.setMemberAddress(maddress_split[1]);
		memberInfo.setMemberDetailAddress(maddress_split[2]);
		memberInfo.setMemberExtraAddress(maddress_split[3]);
		
		System.out.println(memberInfo);
		return memberInfo;
	}
	public String memberIdCheck(String confirmId) {
		System.out.println("MemberService.memberIdCheck() 호출");
		MemberDto memberCheck = mdao.getMemberInfo(confirmId);
		String result = "NO";
		if (memberCheck == null) {
			result = "OK";
		}
		return result;
	}
	public int memberModify(MemberDto modifyInfo) {
		System.out.println("MemberService.memberModify() 호출");
		int updateResult = mdao.updateMember(modifyInfo);
		return updateResult;
	}

}
