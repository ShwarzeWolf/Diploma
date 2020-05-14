package volunteersservice.repositories.hibernate;

import volunteersservice.models.entities.CategoryStatus;
import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.entities.LevelStatus;
import volunteersservice.models.entities.PublicityStatus;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;
import volunteersservice.utils.HibernateUtil;

import org.springframework.stereotype.Repository;
import volunteersservice.repositories.StatusRepository;

@Repository
public class StatusRepositoryHibernate implements StatusRepository {

    @Deprecated
    @Override
    public EventStatus getStatusByName(String statusName) {
        EventStatus res = (EventStatus) HibernateUtil.getSession().createQuery(
                "from EventStatus as eventStatus where eventStatus.name = :name").setParameter("name", statusName).uniqueResult();
        return res;
    }

    @Override
    public EventStatus getStatusByEnum(EventStatusEnum statusEnum) {
        EventStatus res = (EventStatus) HibernateUtil.getSession().createQuery(
                "from EventStatus as eventStatus where eventStatus.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }

    @Override
    public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum) {
        CategoryStatus res = (CategoryStatus) HibernateUtil.getSession().createQuery(
                "from CategoryStatus as categoryStatus where categoryStatus.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }

    @Override
    public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum) {
        PublicityStatus res = (PublicityStatus) HibernateUtil.getSession().createQuery(
                "from PublicityStatus as publicityStatus where publicityStatus.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }

    @Override
    public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum) {
        LevelStatus res = (LevelStatus) HibernateUtil.getSession().createQuery(
                "from LevelStatus as levelStatus where levelStatus.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }
}
