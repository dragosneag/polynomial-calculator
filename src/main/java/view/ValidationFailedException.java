package view;

public class ValidationFailedException extends NumberFormatException{

    public ValidationFailedException(String message) {
        super(message);
    }
}
