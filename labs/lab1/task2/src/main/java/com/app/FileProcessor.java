package com.app;

import com.google.common.base.Splitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Util class for parsing test cases
 */
public final class FileProcessor {

    private static final String FILE_PATH = "../task2/data/test-cases.txt";
    private static final String PATH_PREFIX_TO_EXE = "./data/task1.exe ";
    private static final String PATH_TO_RESULT = "./data/result-of-executing-tests.txt";

    private FileProcessor() {
    }

    /**
     * Parse test cases for executing with exe file
     */
    public static Map<String, String> parseDataForTesting() throws IOException {
        String content = readContentFromFile();
        String normalizedContent = content.substring(0, content.length() - 1);
        return Splitter.on("\n").withKeyValueSeparator(";").split(normalizedContent);
    }

    /**
     * Execute all test cases, compare with expected result and write to file
     */
    public static void writeToFileExecutedTestsResult(Map<String, String> content) throws IOException {
        var writer = new BufferedWriter(new FileWriter(PATH_TO_RESULT));
        AtomicInteger counter = new AtomicInteger(1);
        content.forEach((key, value) -> {
            try {
                String output = CommandLineProcessor.execCmd(PATH_PREFIX_TO_EXE + key);
                String[] splitOut = output.split("\n");
                String res = splitOut[1].substring(0, splitOut[1].length() - 1);
                String writeToFile = counter + (res.equals(value) ? " success;\n" : " error;\n");
                writer.append(writeToFile);
                counter.getAndIncrement();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    private static String readContentFromFile() throws IOException {
        Path path = Paths.get(FILE_PATH);
        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();
        return data;
    }
}
