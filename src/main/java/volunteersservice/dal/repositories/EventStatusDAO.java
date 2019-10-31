package volunteersservice.dal.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.EventStatus;

@Repository
public interface EventStatusDAO {
    public EventStatus getStatusByName(String typeName);
}
