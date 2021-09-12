package com.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Util class for working with standard command line
 */
public final class CommandLineProcessor {

    public static final String EMPTY_RESPONSE = "";

    private CommandLineProcessor() {
    }

    /**
     * Execute command in the command line
     * @param cmd String cmd - command that will be executed
     * @return returns all received data after executing cmd
     */
    public static String execCmd(String cmd) throws IOException {
        InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : EMPTY_RESPONSE;
    }
}
