package DependencyInjection.Implementation;

import App.DAO.EventDao;
import App.DAO.InMemoryEventDaoImpl;
import App.Service.EventServiceImpl;
import DependencyInjection.Exception.BindingNotFoundException;
import DependencyInjection.Exception.ConstructorNotFoundException;
import DependencyInjection.Exception.TooManyConstructorsException;
import DependencyInjection.Interface.Injector;
import DependencyInjection.Interface.Provider;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class ImplementationTest {


    @Test
    public void testExistingBinding() throws Exception {
        Injector injector = new InjectorImpl(); //создаем имплементацию инжектора
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class); //добавляем в инжектор реализацию интерфейса
        Provider<EventDao> daoProvider = injector.getProvider(EventDao.class); //получаем инстанс класса из инжектора


        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDaoImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    public void testCreateObjectByDi() throws Exception {
        EventServiceImpl eventService = new EventServiceImpl(new InMemoryEventDaoImpl());

        InjectorImpl injector = new InjectorImpl();
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);

        EventServiceImpl eventService2 = injector.createObject(EventServiceImpl.class);
        EventServiceImpl eventService3 = injector.createObject(EventServiceImpl.class);

        assertSame(eventService2, eventService3);
    }

}
