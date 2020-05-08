package volunteersservice.repositories.hibernate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByStatus(EventStatusEnum status) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.status.name = :statusName order by event.dateStart")
                .setParameter("statusName", status.name())
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByStatusOrganisedByUser(User user, EventStatusEnum status){
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.status.name = :statusName and event.organiser.userID = :organiserID order by event.dateStart")
                .setParameter("statusName", status.name())
                .setParameter("organiserID", user.getUserID())
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsByStatusCoordinatedByUser(User user, EventStatusEnum status){
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.status.name = :statusName and event.coordinator.userID = :coordinatorID order by event.dateStart")
                .setParameter("statusName", status.name())
                .setParameter("coordinatorID", user.getUserID())
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsOfOrganiser(User organiser, boolean active) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.organiser.userID = :organiserID and (event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow and not event.status.name = 'DENIED'" + "and not event.status.name = 'CREATED'" + ") order by event.dateStart")
                .setParameter("organiserID", organiser.getUserID()).setParameter("dateNow", LocalDateTime.now()).list();
    }

    @Override
    public boolean save(Event event) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.save(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            LOG.error(ex);
            return false;
        }
    }

    @Override
    public boolean update(Event event) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.update(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean delete(Event event) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.delete(event);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
