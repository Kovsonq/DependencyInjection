package DependencyInjection.Implementation;


import DependencyInjection.Annotation.Inject;
import DependencyInjection.Exception.BindingNotFoundException;
import DependencyInjection.Exception.ConstructorNotFoundException;
import DependencyInjection.Exception.TooManyConstructorsException;
import DependencyInjection.Interface.Injector;
import DependencyInjection.Interface.Provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class InjectorImpl implements Injector {
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    private Map<Class, Class> interfaceToImplClass = new ConcurrentHashMap<>();

    //получение инстанса класса со всеми иньекциями по классу интерфейса
    @Override
    public <T> Provider<T> getProvider(Class<T> type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<T> implClass = type;

        if (type.isInterface()) {
            implClass =  interfaceToImplClass.get(type);
            if (implClass==null){
                return null;
            }
        }

        Class<T> finalImplClass = implClass;
        Provider<T> provider = () -> {
            try {
                return finalImplClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        };

        return provider;
    }

    //регистрация байндинга по классу интерфейса и его реализации
    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        interfaceToImplClass.computeIfAbsent(intf, aClass -> {
            Class<?>[] classes = impl.getInterfaces();
            if (classes.length == 1) {
                return impl;
            } else throw new RuntimeException(intf + "has 0 or more than 1 implementation");
        });
    }

    //регистрация синглтон класса
    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {

    }

    @Override
    public <T> T createObject(Class<T> type) throws IllegalAccessException, InvocationTargetException, InstantiationException, TooManyConstructorsException, NoSuchMethodException, ConstructorNotFoundException, BindingNotFoundException {

        Constructor<T>[]  constructors = (Constructor<T>[]) type.getDeclaredConstructors();
        T t;
        int annotatedConstructors = 0;
        for (Constructor<T> constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class)){
                annotatedConstructors++;
                if (annotatedConstructors > 1) {
                    throw new TooManyConstructorsException("More than 1 constructor has Inject annotation.");
                }
                for (Parameter parameter : constructor.getParameters()) {
                    t = (T) getProvider(parameter.getType());
                    if (t == null){
                        throw new BindingNotFoundException("No found any binding for " + parameter.getType() + " argument");
                    }
                }
            }
        }

        if (Arrays.stream(constructors).anyMatch(o -> o.getParameterCount()==0)){
            t = (T) Arrays.stream(constructors).filter(o -> o.getParameterCount()==0).findAny().get().newInstance();
        }   else throw new ConstructorNotFoundException("No no-args constructor and Inject annotated constructor");

        return t;
    }

}
