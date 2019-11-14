package volunteersservice.services.defaults;

import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserVolunteerFunction;
import volunteersservice.models.entities.VolunteerFunction;
import volunteersservice.models.enums.UserVolunteerFunctionStatusEnum;
import volunteersservice.repositories.UserVolunteerFunctionRepository;
import volunteersservice.repositories.UserVolunteerFunctionStatusRepository;
import volunteersservice.services.UserVolunteerFunctionService;
import volunteersservice.utils.RepositoryFactory;

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
		userVolunteerFunctions.save(res);
		return res;
	}

	@Override
	public void setStatus(UserVolunteerFunction userVolunteerFunction, UserVolunteerFunctionStatusEnum statusEnum) {
		UserVolunteerFunctionStatusRepository volunteerFunctionStatusRepository = RepositoryFactory.getUserVolunteerFunctionStatusRepository();
		userVolunteerFunction.setStatus(volunteerFunctionStatusRepository.getStatusByName(statusEnum.name().toLowerCase()));
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

}