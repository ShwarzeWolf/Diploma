package volunteersService.dal.repositorieshibernate;

import volunteersService.models.UserType;
import volunteersService.utils.HibernateSessionFactoryUtil;

import org.springframework.stereotype.Repository;

import volunteersService.dal.repositories.UserTypeDAO;

@Repository
public class UserTypeDAOHibernate implements UserTypeDAO {

    @Override
    public UserType getTypeByName(String typeName) {
        return (UserType) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new UserType(userType) from UserType as userType where userType.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }
}
