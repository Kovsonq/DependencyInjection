package App.Service;

import App.DAO.EventDao;
import App.DAO.OtherEventDao;
import DependencyInjection.Annotation.Inject;

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
