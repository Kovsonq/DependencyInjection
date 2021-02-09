package app.service;

import app.dao.EventDao;

public class EventServiceImplNoArgConstructor implements EventService {

    private String name;
    private EventDao eventDao;

    public EventServiceImplNoArgConstructor(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public EventServiceImplNoArgConstructor(String name) {
        this.name = name;
    }

}
