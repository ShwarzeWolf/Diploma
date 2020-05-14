package volunteersservice.utils;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import volunteersservice.models.entities.*;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Session session;
    private final static Logger LOG = Logger.getLogger(HibernateUtil.class);

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(EventStatus.class);
                configuration.addAnnotatedClass(UserRole.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(VolunteerFunction.class);
                configuration.addAnnotatedClass(FirstPartOfReport.class);
                configuration.addAnnotatedClass(CategoryStatus.class);
                configuration.addAnnotatedClass(LevelStatus.class);
                configuration.addAnnotatedClass(PublicityStatus.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                LOG.fatal(e + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null || !session.isOpen())
            session = getSessionFactory().openSession();
        return session;
    }
}