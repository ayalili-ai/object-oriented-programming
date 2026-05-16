package exceptions;

public class MaxApplicationReachedException extends RuntimeException {
    public MaxApplicationReachedException(String message) {
        super(message);
    }
}
