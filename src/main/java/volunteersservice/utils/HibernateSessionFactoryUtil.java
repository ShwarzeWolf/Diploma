package volunteersservice.utils;

import javax.management.relation.RoleStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import volunteersservice.models.Event;
import volunteersservice.models.EventStatus;
import volunteersservice.models.Role;
import volunteersservice.models.User;
// import volunteersservice.models.UserRole;
import volunteersservice.models.UserType;

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
                configuration.addAnnotatedClass(UserType.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(Role.class);
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