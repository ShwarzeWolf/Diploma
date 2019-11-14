package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserVolunteerFunctionStatus;

@Repository
public interface UserVolunteerFunctionStatusRepository {
    public UserVolunteerFunctionStatus getStatusByName(String typeName);
}
