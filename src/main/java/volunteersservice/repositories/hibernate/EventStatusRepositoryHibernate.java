package volunteersservice.repositories.hibernate;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersservice.repositories.EventStatusRepository;

@Repository
public class EventStatusRepositoryHibernate implements EventStatusRepository {

    @Deprecated
    @Override
    public EventStatus getStatusByName(String statusName) {
        EventStatus res = (EventStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new EventStatus(eventStatus) from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }

    @Override
    public EventStatus getStatusByEnum(EventStatusEnum statusEnum) {
        EventStatus res = (EventStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new EventStatus(eventStatus) from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }
}
