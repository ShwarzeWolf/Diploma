package volunteersservice.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.VolunteerFunction;

@Service
public interface VolunteerFunctionService {

	public VolunteerFunction getVolunteerFunctionByID(int volunteerFunctionID);

	public VolunteerFunction addVolunteerFunction(Event event, String name, String description, String requirements,
			LocalDateTime timeStart, LocalDateTime timeFinish, int numberNeeded);

	public VolunteerFunction addVolunteerFunction(VolunteerFunction volunteerFunction);

	public VolunteerFunction addVolunteerFunction(Event event, String name, String description, String requirements,
			String timeStart, String timeFinish, int numberNeeded);

	public List<VolunteerFunction> getVolunteerFunctions(Event event);

	public void updateVolunteerFunctionInformation(VolunteerFunction volunteerFunction);
}