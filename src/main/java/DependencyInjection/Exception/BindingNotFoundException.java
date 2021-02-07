package DependencyInjection.Exception;

public class BindingNotFoundException extends Exception {
    public BindingNotFoundException(String message) {
        super(message);
    }
}
