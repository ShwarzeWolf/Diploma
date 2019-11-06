package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserType;
import volunteersservice.repositories.UserTypeDAO;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class UserTypeDAOHibernate implements UserTypeDAO {

    @Override
    public UserType getTypeByName(String typeName) {
        return (UserType) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new UserType(userType) from UserType as userType where userType.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }
}
