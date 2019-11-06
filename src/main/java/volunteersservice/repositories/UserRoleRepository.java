package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserRole;

@Repository
public interface UserRoleRepository {
    public UserRole getRoleByName(String typeName);
}
