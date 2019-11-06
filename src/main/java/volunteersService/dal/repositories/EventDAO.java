package volunteersService.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersService.models.Event;

@Repository
public interface EventDAO {
    public Event getEventByID(int eventID);
    public void save(Event event);
    public void delete(Event event);
    public List<Event> getAllEvents();
    public List<Event> getActiveEvents();
}
