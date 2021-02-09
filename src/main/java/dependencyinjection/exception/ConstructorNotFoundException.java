package dependencyinjection.exception;

public class ConstructorNotFoundException extends Exception {
    public ConstructorNotFoundException(String message) {
        super(message);
    }
}
