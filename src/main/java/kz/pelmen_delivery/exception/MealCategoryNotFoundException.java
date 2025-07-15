package kz.pelmen_delivery.exception;

public class MealCategoryNotFoundException extends RuntimeException {
    public MealCategoryNotFoundException(String message) {
        super(message);
    }
}
