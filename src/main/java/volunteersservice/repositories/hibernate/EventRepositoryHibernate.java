package volunteersservice.repositories.hibernate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
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
        return (List<Event>) HibernateUtil.getSession().createQuery("From Event as event order by event.dateStart")
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getActiveEvents() {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("From Event as event where event.dateStart > :date order by event.dateStart")
                .setParameter("date", LocalDateTime.now().plusHours(6)).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("From Event as event where event.status.name = :statusName order by event.dateStart")
                .setParameter("statusName", status.name().toLowerCase()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getActiveEventsByStatus(EventStatusEnum status) {
        return (List<Event>) HibernateUtil.getSession().createQuery(
                "From Event as event where event.status.name = :statusName and event.dateStart > :date order by event.dateStart")
                .setParameter("statusName", status.name().toLowerCase())
                .setParameter("date", LocalDateTime.now().plusHours(6)).list();
    }
}
