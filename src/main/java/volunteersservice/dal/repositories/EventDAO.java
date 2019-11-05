package volunteersservice.dal.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.enums.EventStatusEnum;
import volunteersservice.models.Event;

@Repository
public interface EventDAO {
    public Event getEventByID(int eventID);
    public boolean save(Event event);
    public boolean delete(Event event);
    public boolean update(Event event);
    public List<Event> getAllEvents();
    public List<Event> getActiveEvents();
    public List<Event> getEventsByStatus(EventStatusEnum status);
    public List<Event> getActiveEventsByStatus(EventStatusEnum status);
}
