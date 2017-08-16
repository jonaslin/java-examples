package com.jonas.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

// mvn exec:java -Dexec.mainClass=com.jonas.crawler.App -Dseed= -Dmatched_url=
public class App {
	private static final String CRAWL_STORAGE_FOLDER = System.getProperty("user.dir") + "/resources";
	private static final int MAX_DEPTH_OF_CRAWLING = 5;
	private static final int MAX_PAGES_TO_FETCH = 100000;
	private static final int NUM_OF_CRAWLERS = 2;

	public static void main(String[] args) throws Exception {
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(App.CRAWL_STORAGE_FOLDER);
		config.setMaxDepthOfCrawling(App.MAX_DEPTH_OF_CRAWLING);
		config.setMaxPagesToFetch(App.MAX_PAGES_TO_FETCH);

		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
		controller.addSeed(System.getProperty("seed"));
		controller.start(MyWebCrawler.class, App.NUM_OF_CRAWLERS);
	}
}
