package kz.pelmen_delivery.exception;

public class RestaurantAlreadyExistsException extends RuntimeException{
    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }
}
