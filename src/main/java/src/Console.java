package src;

import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;

public class Console {

    //D:\Documentos\Arquivos\Projetos\FURB\Grafos\teste.txt

    public static void main(String[] args) {

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
