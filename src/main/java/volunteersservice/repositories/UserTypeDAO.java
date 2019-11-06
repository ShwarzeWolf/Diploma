package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserType;

@Repository
public interface UserTypeDAO {
    public UserType getTypeByName(String typeName);
}
