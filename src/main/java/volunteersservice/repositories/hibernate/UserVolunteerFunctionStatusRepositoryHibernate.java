package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserVolunteerFunctionStatus;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.utils.HibernateUtil;

@Repository
public class UserVolunteerFunctionStatusRepositoryHibernate implements UserVolunteerFunctionStatusRepository {

    @Deprecated
    @Override
    public UserVolunteerFunctionStatus getStatusByName(String statusName) {
        UserVolunteerFunctionStatus res = (UserVolunteerFunctionStatus) HibernateUtil.getSession().createQuery(
                "select status from UserVolunteerFunctionStatus as status where status.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }

    @Override
    public UserVolunteerFunctionStatus getStatusByEnum(UserVolunteerFunctionStatusEnum statusEnum) {
        UserVolunteerFunctionStatus res = (UserVolunteerFunctionStatus) HibernateUtil.getSession().createQuery(
                "select status from UserVolunteerFunctionStatus as status where status.name = :name")
                .setParameter("name", statusEnum.name()).uniqueResult();
        return res;
    }
}
