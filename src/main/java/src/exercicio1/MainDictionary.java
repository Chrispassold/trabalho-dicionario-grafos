package src.exercicio1;

import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class MainDictionary {

    public static void main(String[] args) {

        IOUtils.writeConsole("------Autores------");
        IOUtils.writeConsole("Christian Passold");
        IOUtils.writeConsole("Luma Kühl");
        IOUtils.writeConsole("\n");

        try {
            BufferedReader bufferedReader = IOUtils.readFileFromConsole();
            read(bufferedReader);
        } catch (IOException e) {
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
