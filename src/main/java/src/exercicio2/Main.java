package src.exercicio2;

import src.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Main {

    static CharacterCounter characterCounter = new CharacterCounter();
    static PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>((o1, o2) -> (o1.value < o2.value) ? -1 : 1);
    static TreeMap<Character, String> codes = new TreeMap<>();
    static BufferedReader bufferedReader;
    static StringBuilder stringBuilder;

    public static void main(String[] args) throws IOException {
        IOUtils.printInfo();

        try {
            bufferedReader = IOUtils.readFile("D:\\Documentos\\Arquivos\\Projetos\\FURB\\Grafos\\trabalho1\\tmp\\encode.txt");
            loadStringBuilder();
            initNodes();
            buildTree();
            generateCodes(nodes.peek(), "");

            System.out.println("--- Printing Codes ---");
            codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));

            System.out.println("-- Encoding/Decoding --");
            System.out.println("Encoded Text: " + encodeFile());
            System.out.println("Decoded Text: " + decodeFile());
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

    private static void loadStringBuilder() throws IOException {
        String line;
        stringBuilder = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
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

    private static String encodeFile() {
        String text = stringBuilder.toString();
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }

        return encoded.toString();
    }

    private static String decodeFile() {
        String encoded = encodeFile();
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
