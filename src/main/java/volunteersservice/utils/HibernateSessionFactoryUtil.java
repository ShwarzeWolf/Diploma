package volunteersservice.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import volunteersservice.models.Event;
import volunteersservice.models.EventStatus;
import volunteersservice.models.User;
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
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(UserType.class);
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(EventStatus.class);
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