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
    static StringBuilder stringBuilder;

    public static void main(String[] args) {
        IOUtils.printInfo();
        try {
            bufferedReader = IOUtils.readFileFromConsole();
            loadStringBuilder();
            initNodes();
            buildTree();
            generateCodes(nodes.peek(), "");

            createMenu();
        } catch (Exception e) {
            IOUtils.logError(e);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ignored) {
            } finally {
                bufferedReader = null;
            }
        }

    }

    public static void createMenu() {
        GenericMenu genericMenu = new GenericMenu();

        genericMenu.addMenuItem("1", "Print references", Main::printCodeReferences);
        genericMenu.addMenuItem("2", "Print encoded file", () -> IOUtils.writeConsole("Encoded Text: " + encodeFile()));
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

    public static void printCodeReferences() {
        System.out.println("--- Printing Codes ---");
        codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));
    }


    private static void loadStringBuilder() throws IOException {
        String line;
        stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        if (stringBuilder.toString().isEmpty()) {
            throw new InvalidParameterException("Arquivo vazio");
        }

    }

    private static void initNodes() throws IOException {
        characterCounter.load(stringBuilder);

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
        System.out.println("-- Encoding --");
        String text = stringBuilder.toString();
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
            } else {
                System.out.println("Input not Valid");
            }

        }
        return decoded.toString();
    }

}
