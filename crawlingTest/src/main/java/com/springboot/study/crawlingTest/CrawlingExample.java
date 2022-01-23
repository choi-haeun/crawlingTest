package com.springboot.study.crawlingTest;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class CrawlingExample {
    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "data/crawler"; //데이터 임시저장장소
        int numberOfCrawlers = 7; //크롤러 개수

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * 크롤러 대상 주소
         */
        controller.addSeed("https://www.codingworldnews.com/news/articleList.html?view_type=sm/");

        /*
         * 크롤러 시작
         */
        controller.start(MyCrawler.class, numberOfCrawlers);
    }
}
