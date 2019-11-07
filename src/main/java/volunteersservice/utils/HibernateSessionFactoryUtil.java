package volunteersservice.utils;

import javax.management.relation.RoleStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserRole;
import volunteersservice.models.entities.VolunteerFunction;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    private final static Logger LOG = LogManager.getLogger(HibernateSessionFactoryUtil.class.getName());

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(EventStatus.class);
                configuration.addAnnotatedClass(RoleStatus.class);
                configuration.addAnnotatedClass(UserRole.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(VolunteerFunction.class);
                // configuration.addAnnotatedClass(UserRole.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                LOG.fatal(e + e.getMessage());
            }
        }
        return sessionFactory;
    }
}