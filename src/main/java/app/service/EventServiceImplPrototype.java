package app.service;

import app.dao.EventDao;
import dependencyinjection.annotation.Inject;

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
