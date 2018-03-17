package src.exercicio2;

import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class MainHuffman {

    public static void main(String[] args) {

        IOUtils.printInfo();

        try {
            BufferedReader bufferedReader = IOUtils.readFileFromConsole();
            read(bufferedReader);
        } catch (IOException e) {
            IOUtils.logError(e);
        }

    }

    private static void read(final BufferedReader fileBufferedReader) throws IOException {

    }

}
