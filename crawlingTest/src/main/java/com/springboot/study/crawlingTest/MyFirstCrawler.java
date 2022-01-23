package com.springboot.study.crawlingTest;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.SocketException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class MyFirstCrawler extends WebCrawler{
    private final static Pattern EXCLUSIONS =
            Pattern.compile(".*(\\.(css|js|xml|gif|jpg|png|zip|pdf))$");
    @Override
    public boolean shouldVisit(Page page, WebURL url){
        String urlString = url.getURL().toLowerCase();
        return !EXCLUSIONS.matcher(urlString).matches() && urlString.startsWith("https://www.codingworldnews.com/news/articleList.html?view_type=sm");
    }


    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();

        if(page.getParseData() instanceof HtmlParseData){   //html 형태의 Data 일경우
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String title = htmlParseData.getTitle();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            System.out.println(title);
            System.out.println(text);
            System.out.println(html);
        }



    }

    public static void main(String[] args) throws Exception {
        File crawlStorage = new File("src/test/resources/crawler"); //크럴러의 데이터 저장 디렉터리
        CrawlConfig config = new CrawlConfig();


        config.setCrawlStorageFolder(crawlStorage.getAbsolutePath());

        int numCrawlers = 12; //크롤러 동시 실행 수 지정

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig,pageFetcher);
        CrawlController controller = new CrawlController(config,pageFetcher,robotstxtServer);
        config.setMaxPagesToFetch(10);// 크롤링할 페이지수

        controller.addSeed("");

        CrawlController.WebCrawlerFactory<MyFirstCrawler> factory = MyFirstCrawler::new;
        //전역 연산자 사용해 공간을 할당,생성 중인 개체가 메모리의 특정위치에 배치,전역범위로 백업을호출,
        //새로운 배치를 사용하고 범위 체인의 어딘가에 도입된 다른 할당 함수를 실수로 호출하지 않음
        controller.start(factory,numCrawlers);


    }
}

