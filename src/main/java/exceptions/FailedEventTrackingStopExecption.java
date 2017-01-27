package exceptions;

public class FailedEventTrackingStopExecption extends RuntimeException {
    public FailedEventTrackingStopExecption (Exception innerException) {
        super(innerException);
    }
}
