package volunteersservice.repositories.hibernate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.repositories.EventRepository;
import volunteersservice.utils.HibernateUtil;

@Repository
public class EventRepositoryHibernate implements EventRepository {

    private final static Logger LOG = Logger.getLogger(EventRepositoryHibernate.class);

    @Override
    public Event getEventByID(int eventID) {
        return HibernateUtil.getSession().get(Event.class, eventID);
    }

    @Override
    public boolean save(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            LOG.error(ex);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Event event) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) HibernateUtil.getSession().createQuery("from Event as event order by event.dateStart")
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getActiveEvents() {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.dateStart > :date order by event.dateStart")
                .setParameter("date", LocalDateTime.now().plusHours(6)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.status.name = :statusName order by event.dateStart")
                .setParameter("statusName", status.name()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getActiveEventsByStatus(EventStatusEnum status) {
        return (List<Event>) HibernateUtil.getSession().createQuery(
                "from Event as event where event.status.name = :statusName and event.dateStart > :date order by event.dateStart")
                .setParameter("statusName", status.name()).setParameter("date", LocalDateTime.now().plusHours(6))
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsCoordinatedBy(User coordinator, boolean active) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.coordinator.userID = :coordinatorID and event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow order by event.dateStart")
                .setParameter("coordinatorID", coordinator.getUserID()).setParameter("dateNow", LocalDateTime.now())
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsWithVolunteer(User volunteer, boolean active) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("select new Event(event) from Event as event inner join VolunteerFunction as vf on "
                        + "vf.event.eventID = event.eventID inner join UserVolunteerFunction as uvf "
                        + "on uvf.volunteerFunction.volunteerFunctionID = vf.volunteerFunctionID inner join User as user on "
                        + "user.userID = uvf.user.userID where user.userID = :volunteerID and event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow order by event.dateStart")
                .setParameter("volunteerID", volunteer.getUserID()).setParameter("dateNow", LocalDateTime.now()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsOfOrganiser(User organiser, boolean active) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.organiser.userID = :organiserID and (event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow " + (active ? "and not" : "or") + " event.status.name = 'REJECTED'"
                        + ") order by event.dateStart")
                .setParameter("organiserID", organiser.getUserID()).setParameter("dateNow", LocalDateTime.now()).list();
    }
}
