package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.VolunteerFunctionStatus;
import volunteersservice.repositories.VolunteerFunctionStatusRepository;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class VolunteerFunctionStatusRepositoryHibernate implements VolunteerFunctionStatusRepository {

    @Override
    public VolunteerFunctionStatus getStatusByName(String statusName) {
        VolunteerFunctionStatus res = (VolunteerFunctionStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new VolunteerFunctionStatus(status) from VolunteerFunctionStatus as status where status.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
