package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.Event;
import volunteersservice.businesslogic.services.EventStatusManager.EVENT_STATUS;

@Repository
public interface EventDAO {
    public Event getEventByID(int eventID);
    public boolean save(Event event);
    public boolean delete(Event event);
    public boolean update(Event event);
    public List<Event> getAllEvents();
    public List<Event> getActiveEvents();
    public List<Event> getEventsByStatus(EVENT_STATUS status);
    public List<Event> getActiveEventsByStatus(EVENT_STATUS status);
}
