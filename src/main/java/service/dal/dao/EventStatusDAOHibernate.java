package service.dal.dao;

import service.dal.models.EventStatus;
import service.utils.HibernateSessionFactoryUtil;

public class EventStatusDAOHibernate implements EventStatusDAO {

    @Override
    public EventStatus getStatusByName(String statusName) {
        EventStatus res = (EventStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new EventStatus(eventStatus) from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
