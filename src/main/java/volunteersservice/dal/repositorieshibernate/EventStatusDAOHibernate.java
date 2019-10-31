package volunteersservice.dal.repositorieshibernate;

import volunteersservice.models.EventStatus;
import volunteersservice.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersservice.dal.repositories.EventStatusDAO;

@Repository
public class EventStatusDAOHibernate implements EventStatusDAO {

    @Override
    public EventStatus getStatusByName(String statusName) {
        EventStatus res = (EventStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new EventStatus(eventStatus) from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
