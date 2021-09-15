package com.app;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            var content = FileProcessor.parseDataForTesting();
            FileProcessor.writeToFileExecutedTestsResult(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
