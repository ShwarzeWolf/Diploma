package volunteersservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;

@Repository
public interface VolunteerFunctionRepository {
    public boolean save(VolunteerFunction volunteerFunction);
    public List<VolunteerFunction> getVolunteerFunctions(Event event);
}