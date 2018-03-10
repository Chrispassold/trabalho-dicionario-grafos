package src.exception;

import src.Dictionary;

import java.io.IOException;

public class LineLengthException extends IOException {
    public LineLengthException(int line) {
        super(String.format("Quantidade de caracteres na linha %d excede o limite estabelecido (%d)", line, Dictionary.LINE_SIZE_LIMIT));
    }
}
