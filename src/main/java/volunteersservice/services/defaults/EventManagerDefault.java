package volunteersservice.services.defaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventDAO;
import volunteersservice.services.EventManager;
import volunteersservice.services.RoleManager;
import volunteersservice.utils.DAOFabric;
import volunteersservice.utils.ManagerFactory;

@Service
public class EventManagerDefault implements EventManager {
    private final EventDAO eventDAO;
    private final static Logger LOG = LogManager.getLogger(EventManager.class.getName());

    public EventManagerDefault() {
        eventDAO = DAOFabric.getEventDAO();
    }

    @Override
    public Event getEventByID(int eventID) {
        return eventDAO.getEventByID(eventID);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    @Override
    public List<Event> getActiveEvents() {
        return eventDAO.getActiveEvents();
    }

    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return eventDAO.getEventsByStatus(status);
    }

    @Override
    public List<Event> getActiveEventsByStatus(EventStatusEnum status) {
        return eventDAO.getActiveEventsByStatus(status);
    }

    @Override
    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish) {
        eventDAO.save(new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish)));
    }

    @Override
    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish,
            List<Role> roles) {
        Event event = new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish));
        eventDAO.save(event);
        LOG.info("Event is saved: [{}]", event.toString());
        RoleManager roleManager = ManagerFactory.getRoleManager();
        for (Role r : roles) {
            r.setEvent(event);
            roleManager.addRole(r);
        }
    }

    @Override
    public void setStatus(Event event, EventStatusEnum status) {
        event.setStatus(status);
        eventDAO.update(event);
    }
}