package volunteersservice.repositories.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.repositories.VolunteerFunctionRepository;
import volunteersservice.utils.HibernateUtil;

@Repository
public class VolunteerFunctionRepositoryHibernate implements VolunteerFunctionRepository {
    private final Logger LOG = Logger.getLogger(VolunteerFunctionRepositoryHibernate.class);

    @Override
    public void save(VolunteerFunction VolunteerFunction) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(VolunteerFunction);
            tx.commit();
        } catch (Exception ex) {
            LOG.error(ex);
        } finally {
        }
    }

    @Override
    public void update(VolunteerFunction VolunteerFunction) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(VolunteerFunction);
            tx.commit();
        } catch (Exception ex) {
            LOG.error(ex);
        } finally {
        }
    }

    @Override
    public VolunteerFunction getVolunteerFunction(int volunteerFunctionID) {
        return HibernateUtil.getSession().get(VolunteerFunction.class, volunteerFunctionID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VolunteerFunction> getVolunteerFunctions(Event event) {
        return (List<VolunteerFunction>) HibernateUtil.getSession().createQuery(
                "select volunteerFunction From VolunteerFunction as volunteerFunction inner join Event as event on event.eventID = volunteerFunction.event.eventID where event.eventID = :eventID")
                .setParameter("eventID", event.getEventID()).list();
    }

    @Override
    public boolean delete(VolunteerFunction volunteerFunction) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();

        try {
            session.delete(volunteerFunction);
            tx.commit();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}