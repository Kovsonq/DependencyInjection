package app.dao;

public class InMemoryEventDaoImpl implements EventDao {
    @Override
    public void getEvent() {
        System.out.println("Get info from memory");
    }

}
