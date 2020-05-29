package volunteersservice.services.defaults;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.entities.User;
import volunteersservice.models.entities.UserRole;
import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.repositories.UserRepository;
import volunteersservice.services.UserService;
import volunteersservice.utils.RepositoryFactory;
import volunteersservice.utils.ServiceFactory;
import volunteersservice.utils.Utils;

@Service
public class UserServiceDefault implements UserService {

	private final UserRepository userRepository;

	public UserServiceDefault() {
		userRepository = RepositoryFactory.getUserRepository();
	}

	@Override
	public User getUserByID(int id) {
		return userRepository.getUserByID(id);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	@Override
	public User getUserByLogin(String login) {
		return userRepository.getUserByLogin(login);
	}

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public boolean addUser(String email,
						   String login,
						   String userName,
						   String surname,
						   String password,
						   UserRoleEnum roleEnum,
						   String contactPhone) {

		UserRole userRole = ServiceFactory.getStatusService().getRoleByEnum(roleEnum);
		return userRepository.save(new User(email, login, userName, surname, LocalDateTime.now(), password, userRole, contactPhone));
	}

	@Override
	public boolean changePassword(String login, String oldPassword, String newPassword1, String newPassword2){
		if (!(loginPasswordOkay(login, oldPassword)))
			return false;
		if (newPassword1.equals(newPassword2)){
			User logging = userRepository.getUserByLogin(login);
			logging.setHash1(newPassword1);
			logging.setHash2(newPassword1);
			return userRepository.update(logging);
		}
		else
			return false;
	}

	@Override
	public boolean emailPasswordOkay(String email, String password) {
		User logging = userRepository.getUserByEmail(email);
		if (logging == null) {
			return false;
		}
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}

	@Override
	public boolean loginPasswordOkay(String login, String password) {
		User logging = userRepository.getUserByLogin(login);
		if (logging == null) {
			return false;
		}
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}
}