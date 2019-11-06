package volunteersService.dal.repositories;

import org.springframework.stereotype.Repository;

import volunteersService.models.EventStatus;

@Repository
public interface EventStatusDAO {
    public EventStatus getStatusByName(String typeName);
}
