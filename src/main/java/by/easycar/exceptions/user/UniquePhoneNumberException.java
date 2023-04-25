package by.easycar.exceptions.user;

public class UniquePhoneNumberException extends RuntimeException {
    public UniquePhoneNumberException(String message) {
        super(message);
    }
}
