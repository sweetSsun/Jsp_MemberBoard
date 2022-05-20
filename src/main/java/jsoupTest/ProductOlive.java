package jsoupTest;

public class ProductOlive {

	private String brandName;
	private String prdName;
	private int prdPrice; 	// jsoup으로 받아오는 값은 문자열. int로 변환해주는 과정이 필요
	

	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getPrdName() {
		return prdName;
	}
	public void setPrdName(String prdName) {
		this.prdName = prdName;
	}
	public int getPrdPrice() {
		return prdPrice;
	}
	public void setPrdPrice(int prdPrice) {
		this.prdPrice = prdPrice;
	}
	
	@Override
	public String toString() {
		return "ProductOlive [brandName=" + brandName + ", prdName=" + prdName + ", prdPrice=" + prdPrice + "]";
	}
	
}
