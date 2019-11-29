package volunteersservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;

@Repository
public interface EventRepository {
    public Event getEventByID(int eventID);

    public boolean save(Event event);

    public boolean delete(Event event);

    public boolean update(Event event);

    public List<Event> getAllEvents();

    public List<Event> getActiveEvents();

    public List<Event> getEventsByStatus(EventStatusEnum status);

    public List<Event> getActiveEventsByStatus(EventStatusEnum status);

    public List<Event> getEventsCoordinatedBy(User coordinator, boolean active);

    public List<Event> getEventsWithVolunteer(User volunteer, boolean active);

    public List<Event> getEventsOfOrganiser(User organiser, boolean active);

}
