package service.dal.dao;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import service.dal.models.Event;
import service.utils.HibernateSessionFactoryUtil;

public class EventDAOHibernate implements EventDAO {

    @Override
    public Event getEventByID(int eventID) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Event.class, eventID);
    }

    @Override
    public void save(Event event) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(event);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Event event) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(event);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery("From Event as event order by event.dateStart")
                .list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Event> getActiveEvents() {
        return (List<Event>) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("From Event as event where event.dateStart > :date order by event.dateStart")
                .setParameter("date", Timestamp.valueOf(LocalDateTime.now().plusHours(6))).list();
    }
}
