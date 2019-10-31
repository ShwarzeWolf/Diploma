package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.Event;

@Repository
public interface EventDAO {
    public Event getEventByID(int eventID);
    public void save(Event event);
    public void delete(Event event);
    public List<Event> getAllEvents();
    public List<Event> getActiveEvents();
}
