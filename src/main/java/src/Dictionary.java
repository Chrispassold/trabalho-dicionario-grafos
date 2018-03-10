package src;

import src.exception.FileLineLimitException;
import src.exception.LineLengthException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    public static final int LINE_SIZE_LIMIT = 200;
    public static final int FILE_LINE_LIMIT = 10000;

    private Map<Integer, String> mapLines;

    private Dictionary(Map<Integer, String> lines) {
        mapLines = lines;
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
