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

    public static BufferedReader readFileFromConsole() throws IOException {
        writeConsole(String.format("Para sair informe a palavra chave '%s'", EXIT_APPLICATION));
        writeConsole("Informe o caminho do arquivo a ser computado:");

        String path;

        path = new BufferedReader(new InputStreamReader(System.in)).readLine();

        BufferedReader bufferedReader = readFile(path);

        if (bufferedReader != null) {
            return readFile(path);
        }

        return null;
    }

    public static void exit(){
        System.exit(0);
    }

    public static BufferedReader readFile(String path) throws IOException {
        BufferedReader fileBufferedReader = null;

        if (EXIT_APPLICATION.equalsIgnoreCase(path)) {
            exit();
        }

        if (path != null && !path.isEmpty()) {
            fileBufferedReader = IOUtils.loadFile(path);
        }

        if (fileBufferedReader == null) {
            throw new NullPointerException("Não foi possível carregar o arquivo");
        }

        return fileBufferedReader;
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

}
