package src.util;

import java.io.*;

public class IOUtils {

    private static final String EXIT_APPLICATION = "exit";


    public static BufferedReader loadFile(String path) throws IOException {

        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado");
        }

        if (!file.isFile()) {
            throw new IOException("Arquivo inválido");
        }

        return new BufferedReader(new FileReader(path));
    }

    public static BufferedReader readFileFromConsole() throws IOException {
        boolean exit = false;

        writeConsole(String.format("Para sair informe a palavra chave '%s'", EXIT_APPLICATION));
        writeConsole("Informe o caminho do arquivo a ser computado:");
        String path;
        BufferedReader fileBufferedReader = null;

        do {
            try {
                path = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if (EXIT_APPLICATION.equalsIgnoreCase(path)) {
                    System.exit(0);
                }

                if (path != null && !path.isEmpty()) {
                    fileBufferedReader = IOUtils.loadFile(path);
                    exit = true;
                }


            } catch (IOException e) {
                logError(e);
            }

        } while (!exit);

        return fileBufferedReader;
    }


    public static void logError(Throwable e) {
        System.out.println("Ocorreu um problema: " + e.getMessage());
    }

    public static void writeConsole(String s) {
        System.out.println(s);
    }

}
