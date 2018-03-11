package src.exercicio1;

import src.exercicio1.exception.FileLineLimitException;
import src.exercicio1.exception.LineLengthException;
import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {

    public static final int LINE_SIZE_LIMIT = 200;
    public static final int FILE_LINE_LIMIT = 10000;
    public static final int DICTIONARY_WORDS_LIMIT = 5000;

    private Map<Integer, String> lineStringMap;
    private TreeSet<String> words;

    private Dictionary(Map<Integer, String> lines) {
        lineStringMap = lines;
        words = new TreeSet<String>();

        process();
    }

    private void addToDictionary(String word) {
        if (word != null && !word.isEmpty()) {
            if (!words.contains(word.toLowerCase())) {
                words.add(word.toLowerCase());
            }
        }
    }

    private void process() {

        Set<Map.Entry<Integer, String>> entries = lineStringMap.entrySet();

        for (Map.Entry<Integer, String> line : entries) {

            if (words.size() > DICTIONARY_WORDS_LIMIT) {
                break;
            }

            String value = line.getValue();
            if (value == null) {
                continue;
            }

            if (value.isEmpty()) {
                continue;
            }

            String[] lineWords = value.split("[^a-zA-Z]+");

            for (String lineWord : lineWords) {
                addToDictionary(lineWord);
            }

        }
    }

    public void show() {

        IOUtils.writeConsole("\n\n\n\n");
        IOUtils.writeConsole("------------- INICIO DICIONARIO -------------------");

        for (String word : this.words) {
            IOUtils.writeConsole(word);
        }

        IOUtils.writeConsole("------------- FIM DICIONARIO -------------------");
        IOUtils.writeConsole("\n\n\n\n");
    }


    static class Builder {

        public static Dictionary build(BufferedReader bufferedReader) throws IOException {

            Map<Integer, String> lines = new HashMap<Integer, String>();

            String line;
            int countLine = 0;
            while ((line = bufferedReader.readLine()) != null) {

                countLine++;

                if (line.isEmpty()) {
                    continue;
                }

                if (countLine > FILE_LINE_LIMIT) {
                    throw new FileLineLimitException();
                }

                if (line.length() > LINE_SIZE_LIMIT) {
                    throw new LineLengthException(countLine);
                }

                lines.put(countLine, line);
            }

            return new Dictionary(lines);

        }

    }

}
