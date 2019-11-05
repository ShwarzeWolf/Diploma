package volunteersservice.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.EventDAO;
import volunteersservice.models.Event;
import volunteersservice.models.Role;
import volunteersservice.services.EventStatusManager.EVENT_STATUS;

@Service
public class EventManager {
    private final EventDAO eventDAO;
    private final static Logger LOG = LogManager.getLogger(EventManager.class.getName());

    public EventManager() {
        eventDAO = DAOFabric.getEventDAO();
    }

    public Event getEventByID(int eventID) {
        return eventDAO.getEventByID(eventID);
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAllEvents();
    }

    public List<Event> getActiveEvents() {
        return eventDAO.getActiveEvents();
    }
    
    public List<Event> getEventsByStatus(EVENT_STATUS status) {
        return eventDAO.getEventsByStatus(status);
    }

    public List<Event> getActiveEventsByStatus(EVENT_STATUS status) {
        return eventDAO.getActiveEventsByStatus(status);
    }

    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish) {
        eventDAO.save(new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish)));
    }

    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish, List<Role> roles) {
        Event event = new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish));
        eventDAO.save(event);
        LOG.info("Event is saved: [{}]", event.toString());
        RoleManager roleManager = new RoleManager();
        for (Role r: roles) {
            r.setEvent(event);
            roleManager.addRole(r);
        }
    }

    public void setStatus(Event event, EVENT_STATUS status) {
        event.setStatus(status);
        eventDAO.update(event);
    }
}