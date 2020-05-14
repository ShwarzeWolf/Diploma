package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.EventStatus;
import volunteersservice.models.enums.EventStatusEnum;

@Repository
public interface StatusRepository {
    @Deprecated
    public EventStatus getStatusByName(String typeName);

    public EventStatus getStatusByEnum(EventStatusEnum statusEnum);
}
