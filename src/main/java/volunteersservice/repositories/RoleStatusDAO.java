package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.RoleStatus;

@Repository
public interface RoleStatusDAO {
    public RoleStatus getStatusByName(String typeName);
}
