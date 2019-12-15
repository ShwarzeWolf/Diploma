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
        } finally {
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
        } finally {
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
        } finally {
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
                .createQuery("select event from Event as event inner join VolunteerFunction as vf on "
                        + "vf.event.eventID = event.eventID inner join UserVolunteerFunction as uvf "
                        + "on uvf.volunteerFunction.volunteerFunctionID = vf.volunteerFunctionID inner join User as user on "
                        + "user.userID = uvf.user.userID where user.userID = :volunteerID and event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow order by event.dateStart")
                .setParameter("volunteerID", volunteer.getUserID()).setParameter("dateNow", LocalDateTime.now()).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsWithVolunteer(User volunteer, LocalDate dateStart, LocalDate dateFinish) {
        Query<Event> query = HibernateUtil.getSession()
                .createQuery("select event from Event as event inner join VolunteerFunction as vf on "
                        + "vf.event.eventID = event.eventID inner join UserVolunteerFunction as uvf "
                        + "on uvf.volunteerFunction.volunteerFunctionID = vf.volunteerFunctionID inner join User as user on "
                        + "user.userID = uvf.user.userID where user.userID = :volunteerID"
                        + (dateStart != null || dateFinish != null ? " and " : "")
                        + (dateStart != null ? "event.dateFinish >= :dateStart " : "")
                        + (dateStart != null && dateFinish != null ? "and " : "")
                        + (dateFinish != null ? "event.dateStart <= :dateFinish " : ""))
                .setParameter("volunteerID", volunteer.getUserID());
        if (dateStart != null)
            query.setParameter("dateStart", dateStart != null ? LocalDateTime.of(dateStart, LocalTime.of(0, 0)) : null);
        if (dateFinish != null)
            query.setParameter("dateFinish",
                    dateFinish != null ? LocalDateTime.of(dateFinish, LocalTime.of(23, 59)) : null);
        return (List<Event>) query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getEventsOfOrganiser(User organiser, boolean active) {
        return (List<Event>) HibernateUtil.getSession()
                .createQuery("from Event as event where event.organiser.userID = :organiserID and (event.dateFinish "
                        + (active ? ">" : "<") + ":dateNow " + (active ? "and not" : "or")
                        + " event.status.name = 'REJECTED'" + ") order by event.dateStart")
                .setParameter("organiserID", organiser.getUserID()).setParameter("dateNow", LocalDateTime.now()).list();
    }
}
