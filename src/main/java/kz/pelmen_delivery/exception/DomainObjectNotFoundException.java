package kz.pelmen_delivery.exception;

public class DomainObjectNotFoundException extends RuntimeException{
    public DomainObjectNotFoundException(String message) {
        super(message);
    }
}
