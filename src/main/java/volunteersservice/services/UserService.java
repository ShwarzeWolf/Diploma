package volunteersservice.services;

import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.enums.UserRoleEnum;
import volunteersservice.models.entities.User;

@Service
public interface UserService {

	public User getUserByID(int id);

	public User getUserByEmail(String email);

	public User getUserByLogin(String login);

	List<User> getUsers();

	public boolean addUser(String email, String login, String userName, String password,
			UserRoleEnum roleEnum);

	public boolean emailPasswordOkay(String email, String password);

	public boolean loginPasswordOkay(String login, String password);

	public boolean changePassword(String login, String oldPassword, String newPassword1, String newPassword2);
}