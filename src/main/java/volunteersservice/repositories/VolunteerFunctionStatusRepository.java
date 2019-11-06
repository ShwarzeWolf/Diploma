package volunteersservice.repositories;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.VolunteerFunctionStatus;

@Repository
public interface VolunteerFunctionStatusRepository {
    public VolunteerFunctionStatus getStatusByName(String typeName);
}
