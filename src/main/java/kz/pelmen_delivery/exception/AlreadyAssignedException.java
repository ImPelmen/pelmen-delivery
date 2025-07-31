package kz.pelmen_delivery.exception;

public class AlreadyAssignedException extends RuntimeException{
    public AlreadyAssignedException(String message) {
        super(message);
    }
}
