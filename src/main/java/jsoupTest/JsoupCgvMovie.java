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
		System.out.println(movieList_div.size());
		
		Elements movieList_ol = movieList_div.eq(0).select("ol");
//		System.out.println(movieList_ol);
		System.out.println(movieList_ol.size());
		System.out.println(movieList_ol.select("li").eq(0));
		System.out.println(movieList_ol.select("li").size());
		
		/*
		Elements movieList_ol_li = movieList_ol.eq(0).select("li"); // 첫번째 ol태그의 li		
		// 제목
		System.out.println("제목: " + movieList_ol.eq(0).select("li").eq(0).select("div.box-contents strong.title").text());
		// 포스터이미지
		System.out.println("포스터이미지: " + movieList_ol.eq(0).select("li").eq(0).select("span.thumb-image img").attr("src"));
		// 개봉일
		System.out.println(("개봉일: " +  movieList_ol.eq(0).select("li").eq(0).select("span.txt-info strong").text()));
		*/
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
		
	}

}
