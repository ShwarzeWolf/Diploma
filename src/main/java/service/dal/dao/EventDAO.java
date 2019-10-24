package service.dal.dao;

import java.util.List;

import service.dal.models.Event;

public interface EventDAO {
    public Event getEventByID(int eventID);
    public void save(Event event);
    public void delete(Event event);
    public List<Event> getAllEvents();
    public List<Event> getActiveEvents();
}
