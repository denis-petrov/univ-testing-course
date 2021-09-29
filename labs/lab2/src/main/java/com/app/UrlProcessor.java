package com.app;


import org.javatuples.Pair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class UrlProcessor {

    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp|file)://([-a-zA-Z0-9+&@#/%?=~_|!:,.;][^/]*)[-a-zA-Z0-9+&@#/%=~_|]");

    private UrlProcessor() {
    }

    public static Pair<Map<String, String>, Map<String, String>> crawlingThroughUrls(
            List<String> urls,
            boolean isNeedToProcessNestedLinks
    ) {
        var brokenLinks = new LinkedHashMap<String, String>();
        var stableLinks = new LinkedHashMap<String, String>();
        urls.forEach(url -> {
            try {
                Connection.Response response = Jsoup.connect(url)
                        .method(Connection.Method.GET)
                        .timeout(3000)
                        .execute();

                if (response.statusCode() != HttpURLConnection.HTTP_OK)
                    throw new RuntimeException();

                if (isNeedToProcessNestedLinks)
                    processNestedLinks(brokenLinks, stableLinks, url);

                stableLinks.putIfAbsent(url, "stable");
            } catch (Exception e) {
                processException(brokenLinks, url, e);
            }
        });
        return new Pair<>(brokenLinks, stableLinks);
    }

    private static void processNestedLinks(
            Map<String, String> brokenLinks,
            Map<String, String> stableLinks,
            String url
    ) throws IOException {
        List<String> linksFromPage = findLinksFromPage(url).stream()
                .filter(URL_PATTERN.asPredicate())
                .collect(Collectors.toList());
        var nestedLinks = crawlingThroughUrls(linksFromPage, false);
        brokenLinks.putAll(nestedLinks.getValue0());
        stableLinks.putAll(nestedLinks.getValue1());
    }

    private static void processException(LinkedHashMap<String, String> brokenLinks, String url, Exception e) {
        Matcher matcher = URL_PATTERN.matcher(url);
        String message = isReceivedMessageNonCorrect(e, matcher) ? "link does not exist, received 404 code" : e.getMessage();
        brokenLinks.putIfAbsent(url, message);
    }

    private static Set<String> findLinksFromPage(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .data("query", "Java")
                .cookie("auth", "token")
                .timeout(3000)
                .get();

        Elements elements = doc.select("a[href]");
        return elements.stream()
                .map(element -> element.attr("href"))
                .collect(Collectors.toSet());
    }

    private static boolean isReceivedMessageNonCorrect(Exception e, Matcher matcher) {
        return matcher.find() && matcher.group(2).equals(e.getMessage());
    }
}
