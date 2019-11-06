package volunteersservice.repositories.hibernate;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import volunteersservice.repositories.RoleDAO;
import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.Role;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class RoleDAOHibernate implements RoleDAO {
    private final Logger LOG = LogManager.getLogger(RoleDAOHibernate.class.getName());

    @Override
    public boolean save(Role role) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(role);
            tx.commit();
            return true;
        } catch (Exception ex) {
            LOG.error(ex);
            return false;
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Role> getRoles(Event event) {
        return (List<Role>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "Select new Role(role) From Role as role inner join Event as event on event.eventID = role.event.eventID where event.eventID = :eventID")
                .setParameter("eventID", event.getEventID()).list();
    }
}