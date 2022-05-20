package jsoupTest;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupCgvMovie {

	public static void main(String[] args) throws IOException {

		String cgvMovieUrl = "http://www.cgv.co.kr/movies/?lt=1&ft=0";
		Document doc = Jsoup.connect(cgvMovieUrl).get();
//		System.out.println(doc);
		Elements movieList_div = doc.select("div.sect-movie-chart");
//		System.out.println(movieList_div.size());
		
		Elements movieList_ol = movieList_div.eq(0).select("ol");
//		System.out.println(movieList_ol);
//		System.out.println(movieList_ol.size());
//		System.out.println(movieList_ol.select("li").eq(0));
//		System.out.println(movieList_ol.select("li").size());
		
		/*
		Elements movieList_ol_li = movieList_ol.eq(0).select("li"); // 첫번째 ol태그의 li		
		// 제목
		System.out.println("제목: " + movieList_ol.eq(0).select("li").eq(0).select("div.box-contents strong.title").text());
		// 포스터이미지
		System.out.println("포스터이미지: " + movieList_ol.eq(0).select("li").eq(0).select("span.thumb-image img").attr("src"));
		// 개봉일
		System.out.println(("개봉일: " +  movieList_ol.eq(0).select("li").eq(0).select("span.txt-info strong").text()));
		
		ArrayList<MovieCgv> mvList = new ArrayList<MovieCgv>();
		
		for (int i = 0 ; i < movieList_ol.size(); i++) { // i번째 ol 태그
			for (int j = 0; j < movieList_ol.eq(i).select("li").size(); j++) { // i번째 ol태그의 j번째 li태그 (각 영화)
				System.out.println("제목: " + movieList_ol.eq(i).select("li").eq(j).select("div.box-contents strong.title").text());
				System.out.println("포스터이미지: " + movieList_ol.eq(i).select("li").eq(j).select("span.thumb-image img").attr("src"));
				System.out.println("개봉일: " +  movieList_ol.eq(i).select("li").eq(j).select("span.txt-info strong").text());
				
				MovieCgv movie = new MovieCgv();
				movie.setMvTitle(movieList_ol.eq(i).select("li").eq(j).select("div.box-contents strong.title").text());
				movie.setMvImg(movieList_ol.eq(i).select("li").eq(j).select("span.thumb-image img").attr("src"));
				movie.setMvOpenDate(movieList_ol.eq(i).select("li").eq(j).select("span.txt-info strong").text());
				mvList.add(movie);
			}
		}
		System.out.println(mvList);
		*/
		
		// 각 영화 상세페이지에 들어가서 정보 받아오기
		for (int i = 0 ; i < movieList_ol.select("li").size(); i++) {
			// url :: http://www.cgv.co.kr/movies/detail-view/?midx=85813
			// href ::  /movies/detail-view/?midx=85813
			String datailUrl = "http://www.cgv.co.kr" + movieList_ol.select("li").eq(i).select("div.box-image a").eq(0).attr("href");
//			System.out.println("datailUrl : " + datailUrl);			
			// 영화 상세페이지의 HTML 문서를 파싱
			Document detailDoc = Jsoup.connect(datailUrl).get();			
			// 각 영화 상세페이지의 영화 정보
			Elements baseMovie = detailDoc.select("div.sect-base-movie");
//			System.out.println(baseMovie.size());
			
			String movieTitle = baseMovie.select("div.box-contents div.title strong").text();
			System.out.println("\nmovieTitle : " + movieTitle);
			
			String posterUrl = baseMovie.select("div.box-image a").attr("href");
			System.out.println("posterUrl : " + posterUrl);

			String Director = baseMovie.select("div.spec dd a").eq(0).text();
			System.out.println("Director : " + Director);
			
			String Actors = baseMovie.select("div.spec dd.on").eq(0).text();
			System.out.println("Actors : " + Actors);
			
			String Genre =  baseMovie.select("div.spec dd.on").eq(0).next().text().split(" : ")[1];
			System.out.println("Genre : " + Genre);
			
			String Grade = baseMovie.select("div.spec dd.on").eq(1).text().split(", ")[0];
			System.out.println("Grade : " + Grade);
			
			String Time = baseMovie.select("div.spec dd.on").eq(1).text().split(", ")[1];
			System.out.println("Time : " + Time);
			
			String Open = baseMovie.select("div.spec dd.on").eq(2).text();
			System.out.println("Open : " + Open);
		}
	}

}
