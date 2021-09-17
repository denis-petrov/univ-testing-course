package com.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Util class for parsing urls
 */
public final class FileProcessor {

    private FileProcessor() {
    }

    /**
     * Method parses all lines with split by \n
     * @param path String path to file
     * @throws IOException if unable to write to file
     * @return List<String> lines from file
     */
    public static List<String> parseUrlsFromInput(String path) throws IOException {
        String content = readContentFromFile(path);
        String[] splitContent = content.split("\n");
        return List.of(splitContent);
    }

    /**
     * Method write content to file
     * @param content Map<String, String> content that will be written
     * @param pathToFile String path to file
     * @throws IOException if unable to open|write|close file
     */
    public static void writeMapToFile(Map<String, String> content, String pathToFile) throws IOException {
        var writer = new BufferedWriter(new FileWriter(pathToFile));
        AtomicInteger counter = new AtomicInteger(1);
        content.forEach((link, desc) -> {
            try {
                String writeStr = counter.getAndIncrement() + ". " + link + " : " + desc + "\n";
                writer.write(writeStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private static String readContentFromFile(String pathToFile) throws IOException {
        Path path = Paths.get(pathToFile);
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }
}
