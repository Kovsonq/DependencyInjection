package app.service;

import app.dao.EventDao;
import app.dao.OtherEventDao;
import dependencyinjection.annotation.Inject;

public class EventServiceImplNoBinding implements EventService {

    private String name;

    private EventDao eventDao;
    private OtherEventDao otherEventDao;

    public EventServiceImplNoBinding(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Inject
    public EventServiceImplNoBinding(EventDao eventDao, OtherEventDao otherEventDao) {
        this.otherEventDao = otherEventDao;
        this.eventDao = eventDao;
    }

    public EventServiceImplNoBinding(String name) {
        this.name = name;
    }

}
