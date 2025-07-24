package kz.pelmen_delivery.exception;

public class IllegalStatusException extends RuntimeException{
    public IllegalStatusException(String message) {
        super(message);
    }
}
