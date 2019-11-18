package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserVolunteerFunctionStatus;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class UserVolunteerFunctionStatusRepositoryHibernate implements UserVolunteerFunctionStatusRepository {

    @Override
    public UserVolunteerFunctionStatus getStatusByName(String statusName) {
        UserVolunteerFunctionStatus res = (UserVolunteerFunctionStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new UserVolunteerFunctionStatus(status) from UserVolunteerFunctionStatus as status where status.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
