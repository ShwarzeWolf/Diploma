package volunteersservice.dal.repositories.hibernate;

import volunteersservice.models.RoleStatus;
import volunteersservice.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersservice.dal.repositories.RoleStatusDAO;

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
