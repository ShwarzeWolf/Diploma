package volunteersservice.repositories.hibernate;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersservice.repositories.EventStatusRepository;

@Repository
public class EventStatusRepositoryHibernate implements EventStatusRepository {

    @Override
    public EventStatus getStatusByName(String statusName) {
        EventStatus res = (EventStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new EventStatus(eventStatus) from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
