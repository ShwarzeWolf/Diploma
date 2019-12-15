package volunteersservice.services;

import java.time.LocalDate;
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

	public void deleteEvent(Event event);

	public void updateEventInformation(Event event);

	public List<Event> getAllEvents();

	public List<Event> getActiveEvents();

	public List<Event> getEventsByStatus(EventStatusEnum status);

	public List<Event> getActiveEventsByStatus(EventStatusEnum status);
	
	public List<Event> getEventsForManagers();
	
	public List<Event> getEventsForCoordinators();

	public List<Event> getEventsForVolunteers();
	
	public List<Event> getActiveEventsCoordinatedBy(User coordinator);
	
	public List<Event> getExpiredEventsCoordinatedBy(User coordinator);

	public List<Event> getActiveEventsWithVolunteer(User volunteer);

	public List<Event> getExpiredEventsWithVolunteer(User volunteer);
	
	public List<Event> getEventsWithVolunteer(User volunteer, LocalDate dateStart, LocalDate dateFinish);
	
	public List<Event> getEventsWithVolunteer(User volunteer, String dateStart, String dateFinish);
	
	public List<Event> getActiveEventsOfOrganiser(User organiser);
	
	public List<Event> getExpiredEventsOfOrganiser(User organiser);

	public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish,
			List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish);

	public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish,
			List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish);

	public void setStatus(Event event, EventStatusEnum status);

	public void setMessage(Event event, String message);

	public void addToMessage(Event event, String messageAddition);

	public void setCoordinator(Event event, User coordinator);
}