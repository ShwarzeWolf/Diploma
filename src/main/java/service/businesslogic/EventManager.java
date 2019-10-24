package service.businesslogic;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import service.dal.dao.DAOFabric;
import service.dal.dao.EventDAO;
import service.dal.models.Event;

public class EventManager {
    private final EventDAO eventDAO;

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

    public void addEvent(String name, String description, LocalDateTime dateStart, LocalDateTime dateFinish) {
        eventDAO.save(new Event(name, description, Timestamp.valueOf(dateStart), Timestamp.valueOf(dateFinish)));
    }
}