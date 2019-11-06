package volunteersService.dal.repositorieshibernate;

import volunteersService.models.EventStatus;
import volunteersService.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersService.dal.repositories.EventStatusDAO;

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
