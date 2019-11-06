package volunteersservice.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;

@Service
public interface EventManager {

	public Event getEventByID(int eventID);

	public List<Event> getAllEvents();

	public List<Event> getActiveEvents();

	public List<Event> getEventsByStatus(EventStatusEnum status);

	public List<Event> getActiveEventsByStatus(EventStatusEnum status);

	public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish);

	public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish,
			List<Role> roles);

	public void setStatus(Event event, EventStatusEnum status);
}