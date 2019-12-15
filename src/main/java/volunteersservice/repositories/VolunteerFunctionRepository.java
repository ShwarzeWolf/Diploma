package volunteersservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;

@Repository
public interface VolunteerFunctionRepository {
    public void save(VolunteerFunction volunteerFunction);

    public void update(VolunteerFunction volunteerFunction);

    public VolunteerFunction getVolunteerFunction(int volunteerFunctionID);

    public List<VolunteerFunction> getVolunteerFunctions(Event event);
}