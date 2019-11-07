package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserRole;
import volunteersservice.repositories.UserRoleRepository;
import volunteersservice.utils.HibernateSessionFactoryUtil;

@Repository
public class UserRoleRepositoryHibernate implements UserRoleRepository {

    @Override
    public UserRole getRoleByName(String typeName) {
        return (UserRole) HibernateSessionFactoryUtil.getSessionFactory().openSession()
                .createQuery("select new UserRole(userRole) from UserRole as userRole where userRole.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }
}
