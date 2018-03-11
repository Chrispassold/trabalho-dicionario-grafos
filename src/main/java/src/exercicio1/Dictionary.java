package src.exercicio1;

import src.exercicio1.exception.FileLineLimitException;
import src.exercicio1.exception.LineLengthException;
import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {

    public static final int LIMITE_CARACTERES_LINHA = 200;
    public static final int LIMITE_LINHAS_ARQUIVO = 10000;
    public static final int LIMITE_PALAVRAS_DICIONARIO = 5000;

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

    /**
     * Regras:
     *  O dicionario pode ter no maximo {@code LIMITE_PALAVRAS_DICIONARIO} de palavras distintas
     * */
    private void process() {

        Set<Map.Entry<Integer, String>> entries = lineStringMap.entrySet();

        for (Map.Entry<Integer, String> line : entries) {

            if (words.size() > LIMITE_PALAVRAS_DICIONARIO) {
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

        /**
         * Regras:
         *  O arquivo deve ter no maximo {@code LIMITE_LINHAS_ARQUIVO} de linhas no arquivo
         *  O arquivo deve ter no maximo {@code LIMITE_CARACTERES_LINHA} de caracteres por linha
         * */
        public static Dictionary build(BufferedReader bufferedReader) throws IOException {

            Map<Integer, String> lines = new HashMap<Integer, String>();

            String line;
            int countLine = 0;
            while ((line = bufferedReader.readLine()) != null) {

                countLine++;

                if (line.isEmpty()) {
                    continue;
                }

                if (countLine > LIMITE_LINHAS_ARQUIVO) {
                    throw new FileLineLimitException();
                }

                if (line.length() > LIMITE_CARACTERES_LINHA) {
                    throw new LineLengthException(countLine);
                }

                lines.put(countLine, line);
            }

            return new Dictionary(lines);

        }

    }

}
