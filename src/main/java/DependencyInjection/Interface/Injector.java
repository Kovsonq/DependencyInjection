package DependencyInjection.Interface;

import DependencyInjection.Exception.BindingNotFoundException;
import DependencyInjection.Exception.ConstructorNotFoundException;
import DependencyInjection.Exception.TooManyConstructorsException;

import java.lang.reflect.InvocationTargetException;

public interface Injector {

<T> Provider<T> getProvider(Class<T> type) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException; //получение инстанса класса со всеми иньекциями по классу интерфейса

<T> void bind(Class<T> intf, Class<? extends T> impl); //регистрация байндинга по классу интерфейса и его реализации

<T> void bindSingleton(Class<T> intf, Class<? extends T> impl); //регистрация синглтон класса

<T> T createObject(Class<T> type) throws Exception;

}