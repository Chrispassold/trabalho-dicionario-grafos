package src.exception;

import src.Dictionary;

import java.io.IOException;

public class FileLineLimitException extends IOException {
    public FileLineLimitException() {
        super(String.format("Quantidade de linhas excede o limite estabelecido (%d)", Dictionary.FILE_LINE_LIMIT));
    }
}
