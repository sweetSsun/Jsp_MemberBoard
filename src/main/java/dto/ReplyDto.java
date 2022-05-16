package dto;

public class ReplyDto {
	private int renum;
	private int rebno;
	private String rewriter;
	private String redate;
	private String recontents;
	private int restate;
	
	public int getRenum() {
		return renum;
	}
	public void setRenum(int renum) {
		this.renum = renum;
	}
	public int getRebno() {
		return rebno;
	}
	public void setRebno(int rebno) {
		this.rebno = rebno;
	}
	public String getRewriter() {
		return rewriter;
	}
	public void setRewriter(String rewriter) {
		this.rewriter = rewriter;
	}
	public String getRedate() {
		return redate;
	}
	public void setRedate(String redate) {
		this.redate = redate;
	}
	public String getRecontents() {
		return recontents;
	}
	public void setRecontents(String recontents) {
		this.recontents = recontents;
	}
	public int getRestate() {
		return restate;
	}
	public void setRestate(int restate) {
		this.restate = restate;
	}
	@Override
	public String toString() {
		return "ReplyDto [renum=" + renum + ", rebno=" + rebno + ", rewriter=" + rewriter + ", redate=" + redate
				+ ", recontents=" + recontents + ", restate=" + restate + "]";
	}
	
	
	
}
