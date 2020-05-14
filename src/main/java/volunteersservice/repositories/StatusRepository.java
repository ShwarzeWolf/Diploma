package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.CategoryStatus;
import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.entities.LevelStatus;
import volunteersservice.models.entities.PublicityStatus;
import volunteersservice.models.enums.CategoryStatusEnum;
import volunteersservice.models.enums.EventStatusEnum;
import volunteersservice.models.enums.LevelStatusEnum;
import volunteersservice.models.enums.PublicityStatusEnum;

@Repository
public interface StatusRepository {
    @Deprecated
    public EventStatus getStatusByName(String typeName);

    public EventStatus getStatusByEnum(EventStatusEnum statusEnum);

    public CategoryStatus getStatusByEnum(CategoryStatusEnum statusEnum);

    public PublicityStatus getStatusByEnum(PublicityStatusEnum statusEnum);

    public LevelStatus getStatusByEnum(LevelStatusEnum statusEnum);
}
