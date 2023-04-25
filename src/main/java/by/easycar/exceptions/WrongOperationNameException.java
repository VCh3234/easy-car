package by.easycar.exceptions;

public class WrongOperationNameException extends RuntimeException {
    public WrongOperationNameException(String message) {
        super(message);
    }
}
