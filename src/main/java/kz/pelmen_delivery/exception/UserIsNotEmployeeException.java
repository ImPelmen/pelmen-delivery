package kz.pelmen_delivery.exception;

public class UserIsNotEmployeeException extends RuntimeException{
    public UserIsNotEmployeeException(String message) {
        super(message);
    }
}
