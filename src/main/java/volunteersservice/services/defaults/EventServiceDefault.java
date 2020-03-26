package volunteersservice.services.defaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventRepository;
import volunteersservice.services.EventService;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.RepositoryFactory;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.exceptions.EventCreationException;

@Service
public class EventServiceDefault implements EventService {
    private final EventRepository eventRepository;

    public EventServiceDefault() {
        eventRepository = RepositoryFactory.getEventRepository();
    }

    @Override
    public Event getEventByID(int eventID) {
        return eventRepository.getEventByID(eventID);
    }

    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public void updateEventInformation(Event event) {
        eventRepository.update(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    @Override
    public List<Event> getActiveEvents() {
        return eventRepository.getActiveEvents();
    }

    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return eventRepository.getEventsByStatus(status);
    }

    @Override
    public List<Event> getActiveEventsByStatus(EventStatusEnum status) {
        return eventRepository.getActiveEventsByStatus(status);
    }

    @Override
    public List<Event> getEventsForManagers() {
        return getActiveEventsByStatus(EventStatusEnum.UNCHECKED);
    }

    @Override
    public List<Event> getEventsForCoordinators() {
        return getActiveEventsByStatus(EventStatusEnum.APPROVED);
    }

    @Override
    public List<Event> getEventsForVolunteers() {
        return getActiveEventsByStatus(EventStatusEnum.PUBLISHED);
    }

    @Override
    public List<Event> getActiveEventsCoordinatedBy(User coordinator) {
        return eventRepository.getEventsCoordinatedBy(coordinator, true);
    }

    @Override
    public List<Event> getExpiredEventsCoordinatedBy(User coordinator) {
        return eventRepository.getEventsCoordinatedBy(coordinator, false);
    }

    @Override
    public List<Event> getActiveEventsWithVolunteer(User volunteer) {
        return eventRepository.getEventsWithVolunteer(volunteer, true);
    }

    @Override
    public List<Event> getExpiredEventsWithVolunteer(User volunteer) {
        return eventRepository.getEventsWithVolunteer(volunteer, false);
    }

    @Override
    public List<Event> getEventsWithVolunteer(User volunteer, LocalDate dateStart, LocalDate dateFinish) {
        return eventRepository.getEventsWithVolunteer(volunteer, dateStart, dateFinish);
    }

    @Override
    public List<Event> getEventsWithVolunteer(User volunteer, String dateStart, String dateFinish) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = null, finish = null;
        try {
            start = LocalDate.parse(dateStart, formatter);
        } catch (Exception ex) {
        }
        try {
            finish = LocalDate.parse(dateFinish, formatter);
        } catch (Exception ex) {
        }
        return eventRepository.getEventsWithVolunteer(volunteer, start, finish);
    }

    @Override
    public List<Event> getActiveEventsOfOrganiser(User organiser) {
        return eventRepository.getEventsOfOrganiser(organiser, true);
    }

    @Override
    public List<Event> getExpiredEventsOfOrganiser(User organiser) {
        return eventRepository.getEventsOfOrganiser(organiser, false);
    }

    @Override
    public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish,
            List<VolunteerFunction> volunteerFunctions) {
        if (dateStart.isAfter(dateFinish))
            throw new EventCreationException("Start date-time is after finish date-time");
        if (dateStart.isBefore(LocalDateTime.now().plusHours(24)))
            throw new EventCreationException("You cannot create event which starts in less than 24 hours");

        Event event = new Event(name, organiser, description, place, dateStart, dateFinish);
        eventRepository.save(event);
        if (volunteerFunctions != null) {
            VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();
            for (VolunteerFunction r : volunteerFunctions) {
                r.setEvent(event);
                volunteerFunctionService.addVolunteerFunction(r);
            }
        }
        return event;
    }

    @Override
    public Event addEvent(String name, User organiser, String description, String place, LocalDateTime dateStart, LocalDateTime dateFinish) {
        return addEvent(name, organiser, description, place, dateStart, dateFinish, null);
    }

    @Override
    public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish,
            List<VolunteerFunction> volunteerFunctions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start, finish;
        try {
            start = LocalDateTime.parse(dateStart, formatter);
            finish = LocalDateTime.parse(dateFinish, formatter);
        } catch (DateTimeParseException ex) {
            throw new EventCreationException(ex);
        }

        return addEvent(name, organiser, description, place, start, finish, volunteerFunctions);
    }

    @Override
    public Event addEvent(String name, User organiser, String description, String place, String dateStart, String dateFinish) {
        return addEvent(name, organiser, description, place, dateStart, dateFinish, null);
    }

    @Override
    public void setStatus(Event event, EventStatusEnum status) {
        event.setStatus(status);
        eventRepository.update(event);
    }

    @Override
    public void setMessage(Event event, String message) {
        event.setMessage(message);
        eventRepository.update(event);
    }

    @Override
    public void addToMessage(Event event, String messageAddition) {
        event.addToMessage(messageAddition);
        eventRepository.update(event);
    }

    @Override
    public void setCoordinator(Event event, User coordinator) {
        event.setCoordinator(coordinator);
        eventRepository.update(event);
    }
}