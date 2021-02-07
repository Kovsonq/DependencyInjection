package App.Service;

import App.DAO.EventDao;
import App.DAO.OtherEventDao;
import DependencyInjection.Annotation.Inject;

public class EventServiceImpl implements EventService {

    private String name;

    private EventDao eventDao;
    private OtherEventDao otherEventDao;

    @Inject
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public EventServiceImpl(EventDao eventDao, OtherEventDao otherEventDao) {
        this.otherEventDao = otherEventDao;
        this.eventDao = eventDao;
    }

    public EventServiceImpl(String name) {
        this.name = name;
    }

    public EventServiceImpl() {
    }

}
