package App.Service;

import App.DAO.EventDao;
import App.DAO.OtherEventDao;
import DependencyInjection.Annotation.Inject;

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
