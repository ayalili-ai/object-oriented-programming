package exceptions;
// Thrown when a student tries to apply twice to the same offer.
public class DeadlinePassedException extends RuntimeException {
    public DeadlinePassedException(String message) {

        super(message);
    }
}
