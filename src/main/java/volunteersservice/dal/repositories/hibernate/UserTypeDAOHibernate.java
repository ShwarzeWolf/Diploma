package volunteersservice.dal.repositories.hibernate;

import volunteersservice.models.UserType;
import volunteersservice.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersservice.dal.repositories.UserTypeDAO;

@Repository
public class UserTypeDAOHibernate implements UserTypeDAO {

    @Override
    public UserType getTypeByName(String typeName) {
        return (UserType) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new UserType(userType) from UserType as userType where userType.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }
}
