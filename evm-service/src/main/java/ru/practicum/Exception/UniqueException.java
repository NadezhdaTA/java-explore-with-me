package ru.practicum.Exception;

public class UniqueException extends RuntimeException {
    public UniqueException(final String message) {
        super(message);
    }

    public UniqueException() {
        super();
    }
}
