package jsoupTest;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupTest1 {
	
	public static void main(String[] args) throws IOException {
		
		String url = "https://www.oliveyoung.co.kr/store/main/getBestList.do";
		Document doc =  Jsoup.connect(url).get();
		// Document :: 해당 url의 모든 HTML을 다 담겨져있음 (전체문서)
		System.out.println(doc);
		
								   // div 태그의 TabsConts 클래스 지정
		Elements elementList = doc.select("div.TabsConts");
		// Elements :: 필요한 부분을 선택하여 요소를 저장
//		System.out.println(elementList);
//		System.out.println(elementList.size());
		
										// elementList 내에서 ul 태그의 cate_prd_list 클래스
		Elements prd_list = elementList.select("ul.cate_prd_list"); 
//		System.out.println(prd_list.size());
		
						// 0번 인덱스 줄의 상품리스트	// 그 줄의 0번 인덱스 li 태그
//		System.out.println(prd_list.eq(0).select("li").eq(0));
		
		// 첫번째 줄 첫번째 상품의 정보
		System.out.println(prd_list.eq(0).select("li").eq(0).select("span.tx_brand").text());
		System.out.println(prd_list.eq(0).select("li").eq(0).select("p.tx_name").text());
		System.out.println(prd_list.eq(0).select("li").eq(0).select("span.tx_cur span.tx_num").text());
		
		ArrayList<ProductOlive> productOList = new ArrayList<ProductOlive>();
		
		for(int i = 0; i < prd_list.size(); i++ ) { // i번째 줄
			for(int j = 0; j < prd_list.eq(i).select("li").size(); j++) { // i번째 줄의 j번째 상품
				String brandName = prd_list.eq(i).select("li").eq(j).select("span.tx_brand").text();
				String prdName = prd_list.eq(i).select("li").eq(j).select("p.tx_name").text();
				// 상품 원가
				String prdPrice_org = prd_list.eq(i).select("li").eq(j).select("span.tx_org span.tx_num").text();
				// 상품 할인가 (현재 판매가)
				String prdPrice_cur = prd_list.eq(i).select("li").eq(j).select("span.tx_cur span.tx_num").text();
				if (prdPrice_org.length()==0) {
					prdPrice_org = prdPrice_cur;
				}
//				// 숫자변환에 split 사용
//				String[] strPriceSplit = strPrdPrice.split(",");
//				String strPrice = "";
//				for(int k = 0; k < strPriceSplit.length; k++) {
//					strPrice = strPrice + strPriceSplit[k];
//				}
//				int prdPrice = Integer.parseInt(strPrice);
	
				// 숫자변환에 replace 사용
				prdPrice_org = prdPrice_org.replace(",", "");
				int prdPrice = Integer.parseInt(prdPrice_org);
				
				System.out.println(prdPrice);
				ProductOlive productO = new ProductOlive();
				productO.setBrandName(brandName);
				productO.setPrdName(prdName);
				productO.setPrdPrice(prdPrice);
				productOList.add(productO);
			}
		}
		System.out.println(productOList);
		/*
		String sql = "INSERT INTO TBL() VALUES(?,?,?)"; //DAO.INSERT()
		for (int z = 0; z < oliveBestPrdList.size(); z++) {
			DAO.INSERT( olibeBestPrdList.get(z) );
		}
		*/
		
		
		
	}
	
}
