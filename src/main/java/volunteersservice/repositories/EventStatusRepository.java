package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.EventStatus;

@Repository
public interface EventStatusRepository {
    public EventStatus getStatusByName(String typeName);
}
