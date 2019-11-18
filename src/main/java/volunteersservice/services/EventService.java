package volunteersservice.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.EventStatusEnum;

@Service
public interface EventService {

	public Event getEventByID(int eventID);

	public List<Event> getAllEvents();

	public List<Event> getActiveEvents();

	public List<Event> getEventsByStatus(EventStatusEnum status);

	public List<Event> getActiveEventsByStatus(EventStatusEnum status);
	
	public List<Event> getEventsForManagers();
	
	public List<Event> getEventsForCoordinators();

	public List<Event> getEventsForVolunteers();
	
	// public List<Event> getOrganiserEvents(User organiser); // TODO create such mechanism
	
	public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish,
			List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish);

	public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish,
			List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish);

	public void setStatus(Event event, EventStatusEnum status);
}