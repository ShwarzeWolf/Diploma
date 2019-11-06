package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.RoleStatus;
import volunteersservice.repositories.RoleStatusDAO;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class RoleStatusDAOHibernate implements RoleStatusDAO {

    @Override
    public RoleStatus getStatusByName(String statusName) {
        RoleStatus res = (RoleStatus) HibernateSessionFactoryUtil.getSessionFactory().openSession().createQuery(
                "select new RoleStatus(status) from RoleStatus as status where status.name = :name")
                .setParameter("name", statusName).uniqueResult();
        return res;
    }
}
