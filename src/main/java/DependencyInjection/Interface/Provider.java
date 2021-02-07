package DependencyInjection.Interface;

import java.lang.reflect.InvocationTargetException;

public interface Provider<T> {
    T getInstance();
}
