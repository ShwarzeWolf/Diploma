package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserRole;
import volunteersservice.models.enums.UserRoleEnum;

@Repository
public interface UserRoleRepository {
    @Deprecated
    public UserRole getRoleByName(String typeName);

    public UserRole getRoleByEnum(UserRoleEnum roleEnum);
}
