package app.service;

import app.dao.EventDao;
import dependencyinjection.annotation.Inject;

public class EventServiceImpl implements EventService {

    private String name;

    private EventDao eventDao;

    @Inject(scope = "singleton")
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public EventServiceImpl(String name) {
        this.name = name;
    }

    public EventServiceImpl() {
    }

}
