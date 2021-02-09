package App.Service;

import App.DAO.EventDao;
import App.DAO.OtherEventDao;
import DependencyInjection.Annotation.Inject;

public class EventServiceImplTwoInject implements EventService {

    private String name;
    private EventDao eventDao;

    @Inject
    public EventServiceImplTwoInject(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Inject
    public EventServiceImplTwoInject(String name) {
        this.name = name;
    }

    public EventServiceImplTwoInject() {
    }

}
