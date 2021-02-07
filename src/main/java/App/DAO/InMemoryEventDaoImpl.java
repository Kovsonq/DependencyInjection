package App.DAO;

public class InMemoryEventDaoImpl implements EventDao {
    @Override
    public void getInfoFromDb() {
        System.out.println("Get info from memory");
    }

}
