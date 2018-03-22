package src.util;

import java.io.BufferedReader;
import java.io.IOException;

public class FileHelper {
    public String name;
    public BufferedReader bufferedReader;

    public FileHelper(String path) throws IOException {
        BufferedReader fileBufferedReader = null;

        if (path != null && !path.isEmpty()) {
            fileBufferedReader = IOUtils.loadFile(path);
        }

        if (fileBufferedReader == null) {
            throw new NullPointerException("Não foi possível carregar o arquivo");
        }

        final java.io.File file = new java.io.File(path);
        name = removeExtension(file.getName());
        bufferedReader = fileBufferedReader;
    }

    public static String removeExtension(String str){
        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }

}
