package volunteersservice.services.defaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish) {
        eventRepository.save(new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish)));
    }

    @Override
    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish,
            List<VolunteerFunction> volunteerFunctions) {
        Event event = new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish));
        eventRepository.save(event);
        LOG.info("Event is saved: [{}]", event.toString());
        VolunteerFunctionService volunteerFunctionService = ServiceFactory.getVolunteerFunctionService();
        for (VolunteerFunction r : volunteerFunctions) {
            r.setEvent(event);
            volunteerFunctionService.addVolunteerFunction(r);
        }
    }

    @Override
    public void setStatus(Event event, EventStatusEnum status) {
        event.setStatus(status);
        eventRepository.update(event);
    }
}