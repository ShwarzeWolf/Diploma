package volunteersservice.repositories.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.repositories.VolunteerFunctionRepository;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class VolunteerFunctionRepositoryHibernate implements VolunteerFunctionRepository {
    private final Logger LOG = Logger.getLogger(VolunteerFunctionRepositoryHibernate.class);

    @Override
    public boolean save(VolunteerFunction VolunteerFunction) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(VolunteerFunction);
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
    public VolunteerFunction getVolunteerFunction(int volunteerFunctionID) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(VolunteerFunction.class, volunteerFunctionID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VolunteerFunction> getVolunteerFunctions(Event event) {
        return (List<VolunteerFunction>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "Select new VolunteerFunction(volunteerFunction) From VolunteerFunction as volunteerFunction inner join Event as event on event.eventID = volunteerFunction.event.eventID where event.eventID = :eventID")
                .setParameter("eventID", event.getEventID()).list();
    }
}