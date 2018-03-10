package src;

import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Console {

    //D:\Documentos\Arquivos\Projetos\FURB\Grafos\teste.txt

    private static final String EXIT_APPLICATION = "exit";

    private static void logError(Throwable e) {
        System.out.println("Ocorreu um problema, tente novamente: " + e.getMessage());
    }

    private static void writeConsole(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        boolean exit = false;
        writeConsole(String.format("Para sair informe a palavra chave '%s'", EXIT_APPLICATION));
        writeConsole("Informe o caminho do arquivo a ser computado:");
        String path;
        BufferedReader fileBufferedReader = null;

        do {
            try {
                path = IOUtils.readFromConsole();

                if (EXIT_APPLICATION.equalsIgnoreCase(path)) {
                    exit = true;
                }

                if (!exit && path != null && !path.isEmpty()) {
                    fileBufferedReader = IOUtils.loadFile(path);
                    exit = true;
                }


            } catch (IOException e) {
                logError(e);
            }

        } while (!exit);

        if (fileBufferedReader == null) {
            System.exit(0);
            return;
        }

        read(fileBufferedReader);

    }

    private static void read(final BufferedReader fileBufferedReader) {

        writeConsole("\n\n\n\n");

        if (fileBufferedReader == null) {
            return;
        }

        try {
            Dictionary dictionary = Dictionary.Builder.build(fileBufferedReader);
        } catch (IOException e) {
            logError(e);
        } finally {

            try {
                fileBufferedReader.close();
            } catch (IOException ignore) {
            }

        }
    }

}
