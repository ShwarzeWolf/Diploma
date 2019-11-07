package volunteersservice.services.defaults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
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
    private final static Logger LOG = LogManager.getLogger(EventService.class.getName());

    public EventServiceDefault() {
        eventRepository = RepositoryFactory.getEventRepository();
    }

    @Override
    public Event getEventByID(int eventID) {
        return eventRepository.getEventByID(eventID);
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
    public Event addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish,
            List<VolunteerFunction> volunteerFunctions) {
        if (dateStart.isAfter(dateFinish))
            throw new EventCreationException("Start date-time is after finish date-time");
        if (dateStart.isBefore(LocalDateTime.now().plusHours(24)))
            throw new EventCreationException("You cannot create event which starts in less than 24 hours");

        Event event = new Event(name, description, dateStart, dateFinish);
        eventRepository.save(event);
        LOG.info("Event is saved: [{}]", event.toString());
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
    public Event addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish) {
        return addEvent(name, description, dateStart, dateFinish, null);
    }

    @Override
    public Event addEvent(String name, String description, String dateStart, String dateFinish,
            List<VolunteerFunction> volunteerFunctions) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start, finish;
        try {
            start = LocalDateTime.parse(dateStart, formatter);
            finish = LocalDateTime.parse(dateStart, formatter);
        } catch (DateTimeParseException ex) {
            throw new EventCreationException(ex);
        }

        return addEvent(name, description, start, finish, volunteerFunctions);
    }

    @Override
    public Event addEvent(String name, String description, String dateStart, String dateFinish) {
        return addEvent(name, description, dateStart, dateFinish, null);
    }

    @Override
    public void setStatus(Event event, EventStatusEnum status) {
        event.setStatus(status);
        eventRepository.update(event);
    }
}