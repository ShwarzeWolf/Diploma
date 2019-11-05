package volunteersservice.businesslogic.services.defaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import volunteersservice.businesslogic.ManagerFabric;
import volunteersservice.businesslogic.services.EventManager;
import volunteersservice.businesslogic.services.RoleManager;
import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.EventDAO;
import volunteersservice.models.Event;
import volunteersservice.models.Role;
import volunteersservice.businesslogic.services.EventStatusManager.EVENT_STATUS;

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
    public List<Event> getEventsByStatus(EVENT_STATUS status) {
        return eventDAO.getEventsByStatus(status);
    }

    @Override
    public List<Event> getActiveEventsByStatus(EVENT_STATUS status) {
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
        RoleManager roleManager = ManagerFabric.getRoleManager();
        for (Role r : roles) {
            r.setEvent(event);
            roleManager.addRole(r);
        }
    }

    @Override
    public void setStatus(Event event, EVENT_STATUS status) {
        event.setStatus(status);
        eventDAO.update(event);
    }
}