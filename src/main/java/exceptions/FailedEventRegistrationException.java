package exceptions;

public class FailedEventRegistrationException extends RuntimeException {
    public FailedEventRegistrationException(Exception innerException) {
        super(innerException);
    }
}
