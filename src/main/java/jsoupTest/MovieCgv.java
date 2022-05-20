package jsoupTest;

public class MovieCgv {
	
	private String mvTitle;
	private String mvImg;
	private String mvOpenDate;
	
	public String getMvTitle() {
		return mvTitle;
	}
	public void setMvTitle(String mvTitle) {
		this.mvTitle = mvTitle;
	}
	public String getMvImg() {
		return mvImg;
	}
	public void setMvImg(String mvImg) {
		this.mvImg = mvImg;
	}
	public String getMvOpenDate() {
		return mvOpenDate;
	}
	public void setMvOpenDate(String mvOpenDate) {
		this.mvOpenDate = mvOpenDate;
	}
	
	@Override
	public String toString() {
		return "MovieCgv [mvTitle=" + mvTitle + ", mvImg=" + mvImg + ", mvOpenDate=" + mvOpenDate + "]";
	}
	
	
}
