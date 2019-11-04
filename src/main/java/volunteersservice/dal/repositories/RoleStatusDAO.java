package volunteersservice.dal.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.RoleStatus;

@Repository
public interface RoleStatusDAO {
    public RoleStatus getStatusByName(String typeName);
}
