package com.app;

import org.javatuples.Pair;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UrlProcessor {

    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp|file)://([-a-zA-Z0-9+&@#/%?=~_|!:,.;][^/]*)[-a-zA-Z0-9+&@#/%=~_|]");

    private UrlProcessor() {
    }

    public static Pair<Map<String, String>, Map<String, String>> crawlingThroughUrls(List<String> urls) {
        Map<String, String> brokenLinks = new LinkedHashMap<>();
        Map<String, String> stableLinks = new LinkedHashMap<>();
        urls.forEach(url -> {
            try {
                HttpURLConnection con = (HttpURLConnection) (new URL(url)).openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(500);
                con.connect();
                con.disconnect();
                int respCode = con.getResponseCode();
                if (!isResponseCodeCorrect(respCode)) {
                    throw new RuntimeException();
                }
                stableLinks.putIfAbsent(url, "stable");
            } catch (Exception e) {
                Matcher matcher = URL_PATTERN.matcher(url);
                brokenLinks.putIfAbsent(url, isReceivedMessageNonCorrect(e, matcher)
                        ? "link does not exist, received 404 code"
                        : e.getMessage());
            }
        });
        return new Pair<>(brokenLinks, stableLinks);
    }

    private static boolean isReceivedMessageNonCorrect(Exception e, Matcher matcher) {
        return matcher.find() && matcher.group(2).equals(e.getMessage());
    }

    private static boolean isResponseCodeCorrect(int code) {
        return code == HttpURLConnection.HTTP_OK;
    }
}
