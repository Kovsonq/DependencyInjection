package dependencyinjection.exception;

public class TooManyConstructorsException extends Exception {
    public TooManyConstructorsException(String message) {
        super(message);
    }
}
