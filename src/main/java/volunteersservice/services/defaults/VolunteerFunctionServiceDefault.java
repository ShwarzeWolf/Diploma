package volunteersservice.services.defaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.repositories.VolunteerFunctionRepository;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.RepositoryFactory;

@Service
public class VolunteerFunctionServiceDefault implements VolunteerFunctionService {

    private final VolunteerFunctionRepository volunteerFunctionRepository;

    public VolunteerFunctionServiceDefault() {
        volunteerFunctionRepository = RepositoryFactory.getVolunteerFunctionRepository();
    }

    @Override
    public boolean addVolunteerFunction(Event event, String name, String description, String requirements, LocalDateTime timeStart,
            LocalDateTime timeFinish, int numberNeeded) {
        if (event.getDateStart().toLocalDateTime().isAfter(timeStart)
                || event.getDateFinish().toLocalDateTime().isBefore(timeFinish) || timeStart.isAfter(timeFinish))
            return false;
        return volunteerFunctionRepository.save(new VolunteerFunction(event, name, description, requirements, Timestamp.valueOf(timeStart),
                Timestamp.valueOf(timeFinish), numberNeeded));
    }

    @Override
    public boolean addVolunteerFunction(VolunteerFunction volunteerFunction) {
        return volunteerFunctionRepository.save(volunteerFunction);
    }

    @Override
    public List<VolunteerFunction> getVolunteerFunctions(Event event) {
        return volunteerFunctionRepository.getVolunteerFunctions(event);
    }
}