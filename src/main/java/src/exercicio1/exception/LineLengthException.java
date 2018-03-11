package src.exercicio1.exception;

import src.exercicio1.Dictionary;

import java.io.IOException;

public class LineLengthException extends IOException {
    public LineLengthException(int line) {
        super(String.format("Quantidade de caracteres na linha %d excede o limite estabelecido (%d)", line, Dictionary.LIMITE_CARACTERES_LINHA));
    }
}
