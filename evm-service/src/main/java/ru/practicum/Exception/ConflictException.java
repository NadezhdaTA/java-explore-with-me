package ru.practicum.Exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }

    public ConflictException() {
        super();
    }
}
