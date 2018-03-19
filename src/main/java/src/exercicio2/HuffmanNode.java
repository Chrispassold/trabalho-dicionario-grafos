package src.exercicio2;

public class HuffmanNode {
    int value;
    String character;
    HuffmanNode zero; //left
    HuffmanNode one; //right

    public HuffmanNode(int value, String character) {
        this.value = value;
        this.character = character;
        zero = null;
        one = null;
    }

    public HuffmanNode(HuffmanNode zero, HuffmanNode one) {
        this.value = zero.value + one.value;
        character = zero.character + one.character;

        if (zero.value < one.value) {
            this.one = one;
            this.zero = zero;
        } else {
            this.one = zero;
            this.zero = one;
        }
    }
}