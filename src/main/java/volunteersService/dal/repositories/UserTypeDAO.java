package volunteersService.dal.repositories;

import org.springframework.stereotype.Repository;

import volunteersService.models.UserType;

@Repository
public interface UserTypeDAO {
    public UserType getTypeByName(String typeName);
}
