package App.Service;

import App.DAO.EventDao;
import App.DAO.OtherEventDao;
import DependencyInjection.Annotation.Inject;

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
