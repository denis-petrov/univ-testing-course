package com.app;

public class App {

    private static final String PATH_TO_INPUT = "./data/input.txt";
    private static final String PATH_TO_VALID_LINKS = "./data/valid-links.txt";
    private static final String PATH_TO_INVALID_LINKS = "./data/invalid-links.txt";

    public static void main(String[] args) {
        try {
            var urls = FileProcessor.parseUrlsFromInput(PATH_TO_INPUT);
            var result = UrlProcessor.crawlingThroughUrls(urls, true);
            var brokenLinks = result.getValue0();
            var stableLinks = result.getValue1();
            FileProcessor.writeMapToFile(brokenLinks, PATH_TO_INVALID_LINKS);
            FileProcessor.writeMapToFile(stableLinks, PATH_TO_VALID_LINKS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

