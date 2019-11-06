package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.EventStatus;

@Repository
public interface EventStatusDAO {
    public EventStatus getStatusByName(String typeName);
}
