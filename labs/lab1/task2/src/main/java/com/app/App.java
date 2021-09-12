package com.app;

import java.io.IOException;

public class App {

    private static final String PATH_PREFIX_TO_EXE = "./data/task1.exe ";

    public static void main(String[] args) {
        try {
            String test = CommandLineProcessor.execCmd(PATH_PREFIX_TO_EXE + "ё                @@@             #123");
            System.out.println(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
