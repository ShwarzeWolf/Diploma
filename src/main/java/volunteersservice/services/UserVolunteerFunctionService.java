package volunteersservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;

@Service
public interface UserVolunteerFunctionService {

	public UserVolunteerFunction getUserVolunteerFunctionByID(int id);

	public UserVolunteerFunction getUserVolunteerFunction(User user, VolunteerFunction volunteerFunction);

	public UserVolunteerFunction addUserVolunteerFunction(User user, VolunteerFunction volunteerFunction);

	public void setStatus(UserVolunteerFunction userVolunteerFunction, UserVolunteerFunctionStatusEnum status);

	public void setEstimation(UserVolunteerFunction userVolunteerFunction, int numberOfHours, int estimation);

	public List<User> getAllVolunteersOfFunction(VolunteerFunction volunteerFunction);

	public List<User> getVoluteersOfFunction(VolunteerFunction volunteerFunction, UserVolunteerFunctionStatusEnum status);

	public List<UserVolunteerFunction> getUserVolunteerFunctionsOfVolunteerFunction(VolunteerFunction volunteerFunction);

	public List<UserVolunteerFunction> getAllVolunteersOfEvent(Event event);
}