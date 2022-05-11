package dto;

public class BoardDto {

	private int bno;
	private String bwriter;
	private String btitle;
	private String bcontents;
	private String bdate;
	private String bfilename;
	private int bhits;
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getBwriter() {
		return bwriter;
	}
	public void setBwriter(String bwriter) {
		this.bwriter = bwriter;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	public String getBcontents() {
		return bcontents;
	}
	public void setBcontents(String bcontents) {
		this.bcontents = bcontents;
	}
	public String getBdate() {
		return bdate;
	}
	public void setBdate(String bdate) {
		this.bdate = bdate;
	}
	public String getBfilename() {
		return bfilename;
	}
	public void setBfilename(String bfilename) {
		this.bfilename = bfilename;
	}
	public int getBhits() {
		return bhits;
	}
	public void setBhits(int bhits) {
		this.bhits = bhits;
	}
	@Override
	public String toString() {
		return "BoardDto [bno=" + bno + ", bwriter=" + bwriter + ", btitle=" + btitle + ", bcontents=" + bcontents
				+ ", bdate=" + bdate + ", bfilename=" + bfilename + ", bhits=" + bhits + "]";
	}
	
	
}
