package src.util;

import java.io.*;

public class IOUtils {

    private static final String EXIT_APPLICATION = "exit";


    public static void printInfo() {
        IOUtils.writeConsole("------Autores------");
        IOUtils.writeConsole("Christian Passold");
        IOUtils.writeConsole("Luma Kühl");
        IOUtils.writeConsole("\n");
    }

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

    public static String readFromConsole() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public static FileHelper readFileFromConsole() throws IOException, NullPointerException {
        writeConsole(String.format("Para sair informe a palavra chave '%s'", EXIT_APPLICATION));
        writeConsole("Informe o caminho do arquivo a ser computado:");
        return readFile(new BufferedReader(new InputStreamReader(System.in)).readLine());
    }

    public static void exit() {
        System.exit(0);
    }

    public static FileHelper readFile(String path) throws IOException, NullPointerException {
        if (EXIT_APPLICATION.equalsIgnoreCase(path)) {
            exit();
        }

        return new FileHelper(path);
    }

    public static void logError(Throwable e) {
        System.out.println("Ocorreu um problema: " + e.getMessage());
    }

    public static void logError(String s) {
        System.out.println("Ocorreu um problema: " + s);
    }

    public static void writeConsole(String s) {
        System.out.println(s);
    }

    public static void writeToFile(String toWrite, String filename) throws IOException {
        BufferedWriter writer = null;
        try {
            //create a temporary file
            File logFile = new File(String.format("files/%s.out", filename));

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(toWrite);
            writeConsole("Arquivo 'out' está no endereço: " + logFile.getAbsolutePath());
        } finally {
            try {
                // Close the writer regardless of what happens...
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception ignore) {
            }
        }
    }

    public static void writeToFile(StringBuilder toWrite, String filename) throws IOException {
        writeToFile(toWrite.toString(), filename);
    }


}
