package volunteersservice.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;

@Repository
public interface EventRepository {
    public Event getEventByID(int eventID);

    public List<Event> getEventsByStatus(EventStatusEnum status);

    public List<Event> getEventsByStatusOrganisedByUser(User user, EventStatusEnum status);

    public List<Event> getEventsByStatusCoordinatedByUser(User user, EventStatusEnum status);

    public boolean save(Event event);

    public boolean update(Event event);

    public boolean delete(Event event);

}
