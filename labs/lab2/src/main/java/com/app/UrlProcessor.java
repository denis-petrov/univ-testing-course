package com.app;


import org.javatuples.Pair;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class UrlProcessor {

    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp|file)://([-a-zA-Z0-9+&@#/%?=~_|!:,.;][^/]*)[-a-zA-Z0-9+&@#/%=~_|]");
    private static final Pattern EXCEPTION_PATTERN = Pattern.compile(".*(Status=(\\d*)),.*");
    private static final Set<String> VISITED_PAGES = new HashSet();

    private UrlProcessor() {
    }

    public static Pair<Map<String, String>, Map<String, String>> crawlingThroughUrls(
            List<String> urls
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
                    throw new RuntimeException(response.statusMessage() + ":" + response.statusCode());

                if (!VISITED_PAGES.contains(url)) {
                    VISITED_PAGES.add(url);
                    processNestedLinks(brokenLinks, stableLinks, url);
                }

                stableLinks.putIfAbsent(url, "stable");
            } catch (Exception e) {
                processException(brokenLinks, url, e);
            }
        });
        return new Pair<>(brokenLinks, stableLinks);
    }

    private static void processException(LinkedHashMap<String, String> brokenLinks, String url, Exception e) {
        Matcher matcher = EXCEPTION_PATTERN.matcher(e.toString());
        String message = e.getMessage() + (matcher.find() ? ":" + matcher.group(2) : "");
        brokenLinks.putIfAbsent(url, message);
    }

    private static void processNestedLinks(
            Map<String, String> brokenLinks,
            Map<String, String> stableLinks,
            String url
    ) throws IOException {
        Predicate<String> predicate = URL_PATTERN.asPredicate();
        String urlWithoutPage = isLinkPage(url) ? getCleanUrl(url) : url;
        List<String> linksFromPage = findLinksFromPage(url).stream()
                .filter(UrlProcessor::isLinkPage)
                .map(link -> predicate.test(link) ? link : urlWithoutPage + link)
                .filter(s -> !VISITED_PAGES.contains(s))
                .collect(Collectors.toList());
        var nestedLinks = crawlingThroughUrls(linksFromPage);
        brokenLinks.putAll(nestedLinks.getValue0());
        stableLinks.putAll(nestedLinks.getValue1());
    }

    private static boolean isLinkPage(String url) {
        return url.contains(".html") || url.contains(".php");
    }

    private static String getCleanUrl(String url) {
        List<Integer> indexes = IntStream
                .iterate(url.indexOf("/"), index -> index >= 0, index -> url.indexOf("/", index + 1))
                .boxed()
                .collect(Collectors.toList());
        return url.substring(0, indexes.get(indexes.size() - 1)) + "/";
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
}
