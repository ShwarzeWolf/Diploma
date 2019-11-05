package volunteersservice.businesslogic.services;

import java.util.List;

import org.springframework.stereotype.Service;

import volunteersservice.models.User;

@Service
public interface UserManager {

	public User getUserByID(int id);

	public User getUserByEmail(String email);

	public User getUserByLogin(String login);

	List<User> getUsers();

	public boolean addUser(String email, String login, String userName, String password,
			UserTypeManager.USER_TYPE typeEnum);

	public boolean emailPasswordOkay(String email, String password);

	public boolean loginPasswordOkay(String login, String password);
}