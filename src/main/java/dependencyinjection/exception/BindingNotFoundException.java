package dependencyinjection.exception;

public class BindingNotFoundException extends Exception {
    public BindingNotFoundException(String message) {
        super(message);
    }
}
