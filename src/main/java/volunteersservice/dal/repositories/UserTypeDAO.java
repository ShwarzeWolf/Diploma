package volunteersservice.dal.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.UserType;

@Repository
public interface UserTypeDAO {
    public UserType getTypeByName(String typeName);
}
