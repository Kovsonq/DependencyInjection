package App.Service;

import App.DAO.EventDao;
import DependencyInjection.Annotation.Inject;

public class EventServiceImplPrototype implements EventService {

    private String name;

    private EventDao eventDao;

    @Inject(scope = "prototype")
    public EventServiceImplPrototype(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public EventServiceImplPrototype(String name) {
        this.name = name;
    }

    public EventServiceImplPrototype() {
    }

}
