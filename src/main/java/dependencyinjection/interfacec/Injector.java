package dependencyinjection.interfacec;

import java.lang.reflect.InvocationTargetException;

public interface Injector {

<T> Provider<T> getProvider(Class<T> type) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException; //получение инстанса класса со всеми иньекциями по классу интерфейса

<T> void bind(Class<T> intf, Class<? extends T> impl); //регистрация байндинга по классу интерфейса и его реализации

<T> void bindSingleton(Class<T> intf, Class<? extends T> impl); //регистрация синглтон класса

<T> T getObject(Class<T> type) throws Exception;

}