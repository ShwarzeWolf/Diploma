package volunteersservice.services.defaults;

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
                          List<VolunteerFunction> volunteerFunctions) {

        if (dateStart.isAfter(dateFinish))
            throw new EventCreationException("Start date-time is after finish date-time");

        if (dateStart.isBefore(LocalDateTime.now().plusHours(24)))
            throw new EventCreationException("You cannot create event which starts in less than 24 hours");

        Event event = new Event(name, organiser, description, place, dateStart, dateFinish, requirements, clothesType, accommodation, food);
        eventRepository.save(event);

        if (volunteerFunctions != null) {
            VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();
            for (VolunteerFunction currentFunction : volunteerFunctions) {
                currentFunction.setEvent(event);
                volunteerFunctionService.addVolunteerFunction(currentFunction);
            }
        }

        return event;
    }

    @Override
    public Event addEvent(String name,
                          User organiser,
                          String description,
                          String place,
                          LocalDateTime dateStart,
                          LocalDateTime dateFinish,
                          String requirements,
                          String clothesType,
                          String accommodation,
                          String food) {
        return addEvent(name, organiser, description, place, dateStart, dateFinish, requirements, clothesType, accommodation, food, null);
    }

    @Override
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
                          List<VolunteerFunction> volunteerFunctions) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start, finish;

        try {
            start = LocalDateTime.parse(dateStart, formatter);
            finish = LocalDateTime.parse(dateFinish, formatter);
        } catch (DateTimeParseException ex) {
            throw new EventCreationException(ex);
        }

        return addEvent(name, organiser, description, place, start, finish, requirements, clothesType, accommodation, food, volunteerFunctions);
    }

    @Override
    public Event addEvent(String name,
                          User organiser,
                          String description,
                          String place,
                          String dateStart,
                          String dateFinish,
                          String requirements,
                          String clothesType,
                          String accommodation,
                          String food) {
        return addEvent(name, organiser, description, place, dateStart, dateFinish, requirements, clothesType, accommodation, food, null);
    }

    @Override
    public Event getEventByID(int eventID) {
        return eventRepository.getEventByID(eventID);
    }

    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return eventRepository.getEventsByStatus(status);
    }

    @Override
    public List<Event> getEventsByStatusOrganisedByUser(User user, EventStatusEnum status){
        return eventRepository.getEventsByStatusOrganisedByUser(user, status);
    }

    @Override
    public List<Event> getEventsByStatusCoordinatedByUser(User user, EventStatusEnum status){
        return eventRepository.getEventsByStatusCoordinatedByUser(user, status);
    }

    @Override
    public List<Event> getOrganiserInProcessEvents(User user){
       return eventRepository.getEventsOfOrganiser(user, true);
    }

    @Override
    public List<Event> getOrganiserExpiredEvents(User user){
        return eventRepository.getEventsOfOrganiser(user, false);
    }

    @Override
    public void updateEventInformation(Event event) {
        eventRepository.update(event);
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

    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

}