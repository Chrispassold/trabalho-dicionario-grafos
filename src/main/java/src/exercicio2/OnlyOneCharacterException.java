package src.exercicio2;

import java.io.IOException;

public class OnlyOneCharacterException extends IOException {

    public OnlyOneCharacterException(String character, int quantity) {
        super(String.format("Existe apenas uma caracter distinto no arquivo '%s' que é repetido %d vez(es)", character, quantity));
    }
}
