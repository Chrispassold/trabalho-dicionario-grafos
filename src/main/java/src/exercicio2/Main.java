package src.exercicio2;

import src.exercicio2.menu.GenericMenu;
import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Main {

    static CharacterCounter characterCounter = new CharacterCounter();
    static PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static BufferedReader bufferedReader;
    static StringBuilder fileText;
    static String encodedText;

    public static void main(String[] args) {
        IOUtils.printInfo();
        try {
            while (true) {
                bufferedReader = IOUtils.readFileFromConsole();
                loadFileText();
                compress();
            }

            //createMenu();
        } catch (Exception e) {
            IOUtils.logError(e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ignored) {
            } finally {
                bufferedReader = null;
            }
        }
    }

    private static void calculateCompression() {

        if (Main.fileText == null) {
            return;
        }

        char[] fileText = Main.fileText.toString().toCharArray();
        String encodedText = Main.encodedText;

        StringBuilder finalString = new StringBuilder();
        for (char aFileText : fileText) {
            int tempChar = (int) aFileText;
            finalString.append(Integer.toString(tempChar, 2));
        }

        IOUtils.writeConsole(String.format("File size (bytes): %d", finalString.toString().length()));
        IOUtils.writeConsole(String.format("Compression size (bytes): %d", encodedText.length()));
    }

    public static void createMenu() {
        GenericMenu genericMenu = new GenericMenu();

        genericMenu.addMenuItem("1", "Print references", Main::printCodeReferences);
        genericMenu.addMenuItem("3", "Print decoded file", () -> {
            IOUtils.writeConsole("Digite o texto a ser decodificado");
            try {
                String s = IOUtils.readFromConsole();
                IOUtils.writeConsole("Decoded Text: " + decodeText(s));
            } catch (Exception e) {
                IOUtils.logError(e);
            }
        });

        genericMenu.initMenu();
    }

    public static void compress() {
        System.out.println("-- Compressing --");
        long time_1 = System.currentTimeMillis();
        initNodes();
        buildTree();
        generateCodes(nodes.peek(), "");
        encodedText = encodeFile();
        IOUtils.writeConsole("Encoded Text: " + encodedText);

        long time_2 = System.currentTimeMillis();
        long difference = time_2 - time_1;
        System.out.println("Compress time: " + difference + " milliseconds");
        calculateCompression();
    }

    public static void printCodeReferences() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }


    private static void loadFileText() throws IOException {
        String line;
        fileText = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            fileText.append(line);
        }

        if (fileText.toString().isEmpty()) {
            throw new InvalidParameterException("Arquivo vazio");
        }

    }

    private static void initNodes() {
        characterCounter.load(fileText);
        nodes.clear();
        for (Map.Entry<Character, Integer> characterIntegerEntry : characterCounter.getCharacterCounts()) {
            nodes.add(new HuffmanNode(characterIntegerEntry.getValue(), characterIntegerEntry.getKey().toString()));
        }
    }

    private static void buildTree() {
        while (nodes.size() > 1) {
            nodes.add(new HuffmanNode(nodes.poll(), nodes.poll()));
        }
    }

    private static void generateCodes(HuffmanNode node, String s) {
        if (s.isEmpty()) {
            codes.clear();
        }

        if (node != null) {
            if (node.one != null)
                generateCodes(node.one, s + "1");

            if (node.zero != null)
                generateCodes(node.zero, s + "0");

            if (node.zero == null && node.one == null)
                codes.put(node.character.charAt(0), s);
        }
    }

    public static String encodeFile() {
        String text = fileText.toString();
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }

        return encoded.toString();
    }

    public static String decodeText(String encoded) throws InvalidParameterException {

        if (encoded == null || encoded.isEmpty()) {
            throw new InvalidParameterException("Valor inválido");
        }

        System.out.println("-- Decoding --");

        StringBuilder decoded = new StringBuilder();
        HuffmanNode node = nodes.peek();
        for (int i = 0; i < encoded.length(); ) {
            HuffmanNode tmpNode = node;
            while (tmpNode.zero != null && tmpNode.one != null && i < encoded.length()) {
                if (encoded.charAt(i) == '1')
                    tmpNode = tmpNode.one;
                else tmpNode = tmpNode.zero;
                i++;
            }

            if (tmpNode.character.length() == 1) {
                decoded.append(tmpNode.character);
            }

        }
        return decoded.toString();
    }

}
