package DependencyInjection.Implementation;


import DependencyInjection.Annotation.Inject;
import DependencyInjection.Exception.BindingNotFoundException;
import DependencyInjection.Exception.ConstructorNotFoundException;
import DependencyInjection.Exception.TooManyConstructorsException;
import DependencyInjection.Interface.Injector;
import DependencyInjection.Interface.Provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class InjectorImpl implements Injector {
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    private Map<Class, Class> interfaceToImplClass = new ConcurrentHashMap<>();

    //получение инстанса класса со всеми иньекциями по классу интерфейса
    @Override
    public <T> Provider<T> getProvider(Class<T> type) {

        Class<T> implClass = type;

        if (type.isInterface()) {
            implClass = interfaceToImplClass.get(type);
            if (implClass == null) {
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
    public <T> T getObject(Class<T> type) throws Exception {
        Constructor<T>[] constructors = (Constructor<T>[]) type.getDeclaredConstructors();
        String scope = Arrays.stream(constructors).filter(c -> c.isAnnotationPresent(Inject.class)).findFirst().get().getAnnotation(Inject.class).scope();
        T t = null;
        if (scope.equals("singleton") || scope.isEmpty()) {
            if (cache.containsKey(type)) {
                return (T) cache.get(type);
            }
            t = createObject(type);
            cache.putIfAbsent(type, t);
        } else if (scope.equals("prototype")) {
            t = createObject(type);
        } else throw new IllegalArgumentException("Wrong scope type");
        return t;
    }

    public <T> T createObject(Class<T> type) throws Exception {
        Constructor<T>[] constructors = (Constructor<T>[]) type.getDeclaredConstructors();
        T t = null;
        if (Arrays.stream(constructors).filter(c -> c.isAnnotationPresent(Inject.class)).count() > 1) {
            throw new TooManyConstructorsException("More than 1 constructor has 'Inject' annotation.");
        } else if (Arrays.stream(constructors).filter(c -> c.isAnnotationPresent(Inject.class)).count() == 1) {
            for (Constructor<T> constructor : constructors) {
                if (constructor.isAnnotationPresent(Inject.class)) {
                    Parameter[] parameters = constructor.getParameters();
                    Object[] list = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        if (interfaceToImplClass.get(parameters[i].getType()) != null) {
                            list[i] = interfaceToImplClass.get(parameters[i].getType()).getDeclaredConstructor().newInstance();
                        } else
                            throw new BindingNotFoundException("No found any binding for " + parameters[i].getType() + " argument");
                    }
                    t = (T) constructor.newInstance(list);
                }
            }
        } else {
            if (Arrays.stream(constructors).anyMatch(o -> o.getParameterCount() == 0)) {
                t = (T) Arrays.stream(constructors).filter(o -> o.getParameterCount() == 0).findFirst().get().newInstance();
            } else throw new ConstructorNotFoundException("No no-args constructor and 'Inject' annotated constructor");
        }
        return t;
    }

}
