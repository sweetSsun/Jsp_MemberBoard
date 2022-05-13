package dto;

public class MemberDto {
	private String mid;
	private String mpw;
	private String mname;
	private String mbirth;
	private String memail;
	private String maddress;
	
	private String memailId;
	private String memailDomain;
	
	private String memberPostCode;
	private String memberAddress;
	private String memberDetailAddress;
	private String memberExtraAddress;
		
	
		public String getMemberPostCode() {
		return memberPostCode;
	}

	public void setMemberPostCode(String memberPostCode) {
		this.memberPostCode = memberPostCode;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberDetailAddress() {
		return memberDetailAddress;
	}

	public void setMemberDetailAddress(String memberDetailAddress) {
		this.memberDetailAddress = memberDetailAddress;
	}

	public String getMemberExtraAddress() {
		return memberExtraAddress;
	}

	public void setMemberExtraAddress(String memberExtraAddress) {
		this.memberExtraAddress = memberExtraAddress;
	}

	public String getMemailId() {
		return memailId;
	}

	public void setMemailId(String memailId) {
		this.memailId = memailId;
	}

	public String getMemailDomain() {
		return memailDomain;
	}

	public void setMemailDomain(String memailDomain) {
		this.memailDomain = memailDomain;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpw() {
		return mpw;
	}
	public void setMpw(String mpw) {
		this.mpw = mpw;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMbirth() {
		return mbirth;
	}
	public void setMbirth(String mbirth) {
		this.mbirth = mbirth;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	public String getMaddress() {
		return maddress;
	}
	public void setMaddress(String maddress) {
		this.maddress = maddress;
	}
	@Override
	public String toString() {
		return "MemberDto [mid=" + mid + ", mpw=" + mpw + ", mname=" + mname + ", mbirth=" + mbirth + ", memail="
				+ memail + ", maddress=" + maddress + "]";
	}
	
}
