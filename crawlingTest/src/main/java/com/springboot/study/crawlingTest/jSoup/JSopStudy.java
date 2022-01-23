package com.springboot.study.crawlingTest.jSoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.List;

public class JSopStudy {
    public static void main(String[] args){
        String url = "https://www.codingworldnews.com/news/articleList.html?view_type=sm";
        Document document = null;   //Html 전체 문서

        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Element : Document 의 html 요소
        //Elements : Element 가 모인 자료형
        Elements elements = document.getElementsByClass("view-cont");

        String title = elements.select("h4.title").text();
        System.out.println(title);

        Elements content= elements.select(".titles a");
        for(int i = 0; i < content.size(); i++) {
            System.out.println(content.get(i).text());
        };
        System.out.println(content.get(2).text());



    }
}
