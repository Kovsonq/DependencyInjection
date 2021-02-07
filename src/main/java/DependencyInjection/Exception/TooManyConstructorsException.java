package DependencyInjection.Exception;

public class TooManyConstructorsException extends Exception {
    public TooManyConstructorsException(String message) {
        super(message);
    }
}
