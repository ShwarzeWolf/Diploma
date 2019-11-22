package volunteersservice.repositories.hibernate;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserRole;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.repositories.UserRoleRepository;
import volunteersservice.utils.HibernateUtil;

@Repository
public class UserRoleRepositoryHibernate implements UserRoleRepository {

    @Deprecated
    @Override
    public UserRole getRoleByName(String typeName) {
        return (UserRole) HibernateUtil.getSession()
                .createQuery("select new UserRole(userRole) from UserRole as userRole where userRole.name = :name")
                .setParameter("name", typeName).uniqueResult();
    }

    @Override
    public UserRole getRoleByEnum(UserRoleEnum roleEnum) {
        return (UserRole) HibernateUtil.getSession()
                .createQuery("select new UserRole(userRole) from UserRole as userRole where userRole.name = :name")
                .setParameter("name", roleEnum.name()).uniqueResult();
    }
}
