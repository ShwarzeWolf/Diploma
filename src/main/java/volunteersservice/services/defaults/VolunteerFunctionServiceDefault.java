package volunteersservice.services.defaults;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.repositories.VolunteerFunctionRepository;
import volunteersservice.services.VolunteerFunctionService;
import volunteersservice.utils.RepositoryFactory;
import volunteersservice.utils.exceptions.EventCreationException;

@Service
public class VolunteerFunctionServiceDefault implements VolunteerFunctionService {

    private final VolunteerFunctionRepository volunteerFunctionRepository;

    public VolunteerFunctionServiceDefault() {
        volunteerFunctionRepository = RepositoryFactory.getVolunteerFunctionRepository();
    }

    @Override
    public VolunteerFunction getVolunteerFunctionByID(int volunteerFunctionID) {
        return volunteerFunctionRepository.getVolunteerFunction(volunteerFunctionID);
    }

    @Override
    public VolunteerFunction addVolunteerFunction(Event event, String name, String description, String requirements,
            LocalDateTime timeStart, LocalDateTime timeFinish, int numberNeeded) {
        VolunteerFunction volunteerFunction = new VolunteerFunction(event, name, description, requirements, timeStart, timeFinish, numberNeeded);
        volunteerFunctionRepository.save(volunteerFunction);
        
        return volunteerFunction;
    }

    @Override
    public VolunteerFunction addVolunteerFunction(Event event, String name, String description, String requirements,
            String timeStart, String timeFinish, int numberNeeded) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start, finish;
        try {
            start = LocalDateTime.parse(timeStart, formatter);
            finish = LocalDateTime.parse(timeFinish, formatter);
        } catch (DateTimeParseException ex) {
            throw new EventCreationException(ex);
        }

        return addVolunteerFunction(event, name, description, requirements, start, finish, numberNeeded);
    }

    @Override
    public VolunteerFunction addVolunteerFunction(VolunteerFunction volunteerFunction) {
        volunteerFunctionRepository.save(volunteerFunction);
        return volunteerFunction;
    }

    @Override
    public List<VolunteerFunction> getVolunteerFunctions(Event event) {
        return volunteerFunctionRepository.getVolunteerFunctions(event);
    }
}