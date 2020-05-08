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

	public Event addEvent(String name,
						  User organiser,
						  String description,
						  String place,
						  LocalDateTime dateStart,
						  LocalDateTime dateFinish,
						  String requirements,
						  String clothesType,
						  String accommodation,
						  String food,
						  List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name,
						  User organiser,
						  String description,
						  String place,
						  LocalDateTime dateStart,
						  LocalDateTime dateFinish,
						  String requirements,
						  String clothesType,
						  String accommodation,
						  String food);

	public Event addEvent(String name,
						  User organiser,
						  String description,
						  String place,
						  String dateStart,
						  String dateFinish,
						  String requirements,
						  String clothesType,
						  String accommodation,
						  String food,
						  List<VolunteerFunction> volunteerFunctions);

	public Event addEvent(String name,
						  User organiser,
						  String description,
						  String place,
						  String dateStart,
						  String dateFinish,
						  String requirements,
						  String clothesType,
						  String accommodation,
						  String food);

	public Event getEventByID(int eventID);

	public List<Event> getEventsByStatus(EventStatusEnum status);

	public List<Event> getEventsByStatusOrganisedByUser(User user, EventStatusEnum status);

	public List<Event> getEventsByStatusCoordinatedByUser(User user, EventStatusEnum status);

	public List<Event> getOrganiserInProcessEvents(User user);

	public List<Event> getOrganiserExpiredEvents(User user);

	public void updateEventInformation(Event event);

	public void setStatus(Event event, EventStatusEnum status);

	public void setMessage(Event event, String message);

	public void addToMessage(Event event, String messageAddition);

	public void setCoordinator(Event event, User coordinator);

	public void deleteEvent(Event event);
}