package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.*;
import volunteersservice.models.enums.*;

@Repository
public interface StatusRepository {
    @Deprecated
    public EventStatus getStatusByName(String typeName);

    public EventStatus getStatusByEnum(EventStatusEnum statusEnum);

    public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum);

    public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum);

    public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum);

    @Deprecated
    public UserRole getRoleByName(String typeName);

    public UserRole getRoleByEnum(UserRoleEnum roleEnum);
}
