package volunteersservice.services.defaults;

import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.Event;
import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionRepository;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.services.UserVolunteerFunctionService;
import volunteersservice.utils.RepositoryFactory;
import volunteersservice.utils.exceptions.UserVolunteerFunctionCreationException;

@Service
public class UserVolunteerFunctionServiceDefault implements UserVolunteerFunctionService {
	private final UserVolunteerFunctionRepository userVolunteerFunctions;

	public UserVolunteerFunctionServiceDefault() {
		userVolunteerFunctions = RepositoryFactory.getUserVolunteerFunctionRepository();
	}

	@Override
	public UserVolunteerFunction getUserVolunteerFunctionByID(int id) {
		return userVolunteerFunctions.getUserVolunteerFunctionByID(id);
	}

	@Override
	public UserVolunteerFunction getUserVolunteerFunction(User user, VolunteerFunction volunteerFunction) {
		return userVolunteerFunctions.getUserVolunteerFunction(user.getUserID(), volunteerFunction.getVolunteerFunctionID());
	}

	@Override
	public UserVolunteerFunction addUserVolunteerFunction(User user, VolunteerFunction volunteerFunction) {
		UserVolunteerFunction res = new UserVolunteerFunction(user, volunteerFunction);
		if (userVolunteerFunctions.alreadySignedUp(user.getUserID(), volunteerFunction.getVolunteerFunctionID())) {
			throw new UserVolunteerFunctionCreationException("User is already signed up to this VolunteerFunction");
		}
		userVolunteerFunctions.save(res);
		return res;
	}

	@Override
	public boolean alreadySignedUp(User user, VolunteerFunction volunteerFunction) {
		return userVolunteerFunctions.alreadySignedUp(user.getUserID(), volunteerFunction.getVolunteerFunctionID());
	}

	@Override
	public void deleteUserVolunteerFunction(UserVolunteerFunction userVolunteerFunction) {
		userVolunteerFunctions.delete(userVolunteerFunction);
	}

	@Override
	public void setStatus(UserVolunteerFunction userVolunteerFunction, UserVolunteerFunctionStatusEnum statusEnum) {
		UserVolunteerFunctionStatusRepository volunteerFunctionStatusRepository = RepositoryFactory.getUserVolunteerFunctionStatusRepository();
		userVolunteerFunction.setStatus(volunteerFunctionStatusRepository.getStatusByEnum(statusEnum));
		userVolunteerFunctions.update(userVolunteerFunction);
	}

	public void setEstimation(UserVolunteerFunction userVolunteerFunction, int numberOfHours, int estimation){
		userVolunteerFunction.setEstimation(numberOfHours, estimation);
		userVolunteerFunctions.update(userVolunteerFunction);
	}

	@Override
	public List<User> getAllVolunteersOfFunction(VolunteerFunction volunteerFunction) {
		return userVolunteerFunctions.getAllVolunteersOfFunction(volunteerFunction);
	}

	@Override
	public List<User> getVoluteersOfFunction(VolunteerFunction volunteerFunction, UserVolunteerFunctionStatusEnum status) {
		return userVolunteerFunctions.getVolunteersOfFunction(volunteerFunction, status);
	}

	@Override
	public List<UserVolunteerFunction> getUserVolunteerFunctionsOfVolunteerFunction(VolunteerFunction volunteerFunction) {
		return userVolunteerFunctions.getUserVolunteerFunctionsOfVolunteerFunction(volunteerFunction);
	}

	@Override
	public List<UserVolunteerFunction> getAllVolunteersOfEvent(Event event){
		return userVolunteerFunctions.getAllVolunteersOfEvent(event.getEventID());
	}

	@Override
	public Long getHoursOfVolunteer(User currentVolunteer){
		return userVolunteerFunctions.getHoursOfVolunteer(currentVolunteer.getUserID());
	}

	@Override
	public double getAVGRating(User currentVolunteer){
		return userVolunteerFunctions.getAVGRating(currentVolunteer.getUserID());
	}
}