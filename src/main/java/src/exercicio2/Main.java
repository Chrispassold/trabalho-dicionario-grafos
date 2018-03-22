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
            bufferedReader = IOUtils.readFileFromConsole();
            loadFileText();
            compress();

            createMenu();
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

        Float fileTextBits = (float) (Main.fileText.toString().length() * 8);
        Float encodedTextBits = (float) Main.encodedText.length(); // already in bits

        IOUtils.writeConsole(String.format("File size: %d bytes", fileTextBits.intValue() / 8));
        IOUtils.writeConsole(String.format("Compression size: %d bytes", encodedTextBits.intValue() / 8));
        IOUtils.writeConsole(String.format("Compression rate : %1$,.2f%%", ((1 - (encodedTextBits / fileTextBits)) * 100)));
    }

    public static void createMenu() {
        GenericMenu genericMenu = new GenericMenu();

        genericMenu.addMenuItem("1", "Exibir arvore e referencias", Main::printCodeReferences);

        genericMenu.addMenuItem("2", "Exibir arquivo codificado", () -> IOUtils.writeConsole(encodedText));

        genericMenu.addMenuItem("3", "Codificar texto baseado na arvore", () -> {
            IOUtils.writeConsole("Digite o texto a ser codificado");
            try {
                String s = IOUtils.readFromConsole();
                IOUtils.writeConsole("Texto codificado: " + encodeText(s));
            } catch (Exception e) {
                IOUtils.logError(e);
            }
        });

        genericMenu.addMenuItem("4", "Decodificar texto baseado na arvore", () -> {
            IOUtils.writeConsole("Digite o texto a ser decodificado");
            try {
                String s = IOUtils.readFromConsole();
                IOUtils.writeConsole("Texto decodificado: " + decodeText(s));
            } catch (Exception e) {
                IOUtils.logError(e);
            }
        });

        genericMenu.initMenu();
    }

    public static void compress() throws InvalidParameterException{
        System.out.println("-- Compressing --");
        long time_1 = System.currentTimeMillis();
        initNodes();
        buildTree();
        generateCodes(nodes.peek(), "");
        encodedText = encodeFile();

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

    public static String encodeFile() throws InvalidParameterException{
        String text = fileText.toString();
        return encodeText(text);
    }

    public static String encodeText(String text) throws InvalidParameterException {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char key = text.charAt(i);
            if(!codes.containsKey(key)){
                throw new InvalidParameterException(String.format("Caracter '%s' não existe na arvore", key));
            }
            encoded.append(codes.get(key));
        }

        return encoded.toString();
    }

    public static String decodeText(String encoded) throws InvalidParameterException {

        if (encoded == null || !encoded.matches("[01]+")) {
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
