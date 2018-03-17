package src.exercicio2;

import java.util.Comparator;

public class HuffmanNode {
    char ch;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char ch, int frequency) {
        this.ch = ch;
        this.frequency = frequency;
    }

    HuffmanNode(char ch, int frequency, HuffmanNode left, HuffmanNode right) {
        this.ch = ch;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public static class HuffmanComparator implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode node1, HuffmanNode node2) {
            return node1.frequency - node2.frequency;
        }
    }
}