package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.UserVolunteerFunctionStatus;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;

@Repository
public interface UserVolunteerFunctionStatusRepository {
    @Deprecated
    public UserVolunteerFunctionStatus getStatusByName(String typeName);

    public UserVolunteerFunctionStatus getStatusByEnum(UserVolunteerFunctionStatusEnum statusEnum);
}
