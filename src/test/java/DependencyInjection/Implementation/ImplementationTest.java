package DependencyInjection.Implementation;

import App.DAO.EventDao;
import App.DAO.InMemoryEventDaoImpl;
import App.Service.*;
import DependencyInjection.Exception.BindingNotFoundException;
import DependencyInjection.Exception.ConstructorNotFoundException;
import DependencyInjection.Exception.TooManyConstructorsException;
import DependencyInjection.Interface.Injector;
import DependencyInjection.Interface.Provider;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ImplementationTest {
    Injector injector;

    @Before
    public void init(){
        injector = new InjectorImpl(); //создаем имплементацию инжектора
    }

    @Test
    public void testExistingBinding() throws Exception {
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class); //добавляем в инжектор реализацию интерфейса
        Provider<EventDao> daoProvider = injector.getProvider(EventDao.class); //получаем инстанс класса из инжектора

        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());
        assertSame(InMemoryEventDaoImpl.class, daoProvider.getInstance().getClass());
    }

    @Test
    public void testNotExistingBinding() throws Exception {
        Provider<EventDao> daoProvider = injector.getProvider(EventDao.class);
        assertNull(daoProvider);
    }

    @Test
    public void testCreateSingletonObject() throws Exception {
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);

        EventServiceImpl eventService2 = injector.getObject(EventServiceImpl.class);
        EventServiceImpl eventService3 = injector.getObject(EventServiceImpl.class);

        assertSame(eventService2, eventService3);
    }

    @Test
    public void testCreatePrototypeObject() throws Exception {
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);

        EventServiceImplPrototype eventService2 = injector.getObject(EventServiceImplPrototype.class);
        EventServiceImplPrototype eventService3 = injector.getObject(EventServiceImplPrototype.class);

        assertNotSame(eventService2, eventService3);
    }

    @Test(expected = TooManyConstructorsException.class)
    public void testTooManyConstructorsException() throws Exception {
        injector.getObject(EventServiceImplTwoInject.class);
    }

    @Test(expected = ConstructorNotFoundException.class)
    public void testConstructorNotFoundException() throws Exception {
        injector.getObject(EventServiceImplNoArgConstructor.class);
    }

    @Test(expected = BindingNotFoundException.class)
    public void testBindingNotFoundException() throws Exception {
        injector.bind(EventDao.class, InMemoryEventDaoImpl.class);
        injector.getObject(EventServiceImplNoBinding.class);
    }

}
