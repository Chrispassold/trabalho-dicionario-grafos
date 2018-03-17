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

    public static void main(String[] args) {

        IOUtils.printInfo();

        try {
            bufferedReader = IOUtils.readFile("D:\\Documentos\\Arquivos\\Projetos\\FURB\\Grafos\\trabalho1\\tmp\\encode.txt");
            initNodes();
            buildTree();
            generateCodes(nodes.peek(), "");

            System.out.println("--- Printing Codes ---");
            codes.forEach((k, v) -> System.out.println("'" + k + "' : " + v));

            System.out.println("-- Encoding/Decoding --");
            encodeFile();

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

    private static void initNodes() throws IOException {
        characterCounter.load(bufferedReader);

        for (Map.Entry<Character, Integer> characterIntegerEntry : characterCounter.getCharacterCounts()) {
            nodes.add(new HuffmanNode(characterIntegerEntry.getValue(), characterIntegerEntry.getKey().toString()));
        }
    }

    private static void buildTree() {
        while (nodes.size() > 1)
            nodes.add(new HuffmanNode(nodes.poll(), nodes.poll()));
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

    private static void encodeFile() throws IOException {
        String line;
        StringBuilder encoded = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                encoded.append(codes.get(line.charAt(i)));
            }
        }

        System.out.println("Encoded Text: " + encoded.toString());
    }

}
