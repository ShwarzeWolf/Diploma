package service.dal.dao;

import service.dal.models.UserType;
import service.utils.HibernateSessionFactoryUtil;

public class UserTypeDAOHibernate implements UserTypeDAO {

    @Override
    public UserType getTypeByName(String typeName) {
        return (UserType) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new UserType(userType) from UserType as userType where userType.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }
}
