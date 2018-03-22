package src.exercicio1;

import src.util.FileHelper;
import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class MainDictionary {

    public static void main(String[] args) {

        IOUtils.printInfo();

        try {

            final FileHelper fileHelper = IOUtils.readFileFromConsole();
            BufferedReader bufferedReader = fileHelper.bufferedReader;
            read(bufferedReader);
        } catch (Exception e) {
            IOUtils.logError(e);
        }

    }

    private static void read(final BufferedReader fileBufferedReader) throws IOException {
        if (fileBufferedReader == null) {
            return;
        }

        try {
            Dictionary dictionary = Dictionary.Builder.build(fileBufferedReader);
            dictionary.show();
        } finally {

            try {
                fileBufferedReader.close();
            } catch (IOException ignore) {
            }

        }
    }

}
