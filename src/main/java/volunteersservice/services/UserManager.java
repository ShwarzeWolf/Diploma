package volunteersservice.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import volunteersservice.dal.DAOFabric;
import volunteersservice.dal.repositories.UserDAO;
import volunteersservice.models.User;
import volunteersservice.models.UserType;
import volunteersservice.utils.Utils;

@Service
public class UserManager {

	private final UserDAO userDAO;
	private final static Logger LOG = LogManager.getLogger(UserManager.class.getName());

	public UserManager() {
		userDAO = DAOFabric.getUserDAO();
	}

	public User getUserByID(int id) {
		return userDAO.getUserByID(id);
	}

	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

	public User getUserByLogin(String login) {
		return userDAO.getUserByLogin(login);
	}

	List<User> getUsers() {
		return userDAO.findAll();
	}

	public void addUser(String email, String login, String userName, String password,
			UserTypeManager.USER_TYPE typeEnum) {
		Timestamp registerDate = Timestamp.valueOf(LocalDateTime.now());
		UserType userType = new UserTypeManager().getTypeByEnum(typeEnum);
		userDAO.save(new User(email, login, userName, registerDate, password, userType));
	}

	public boolean emailPasswordOkay(String email, String password) {
		// LOG.info("Trying to get user: email=\"" + email + "\"");
		User logging = userDAO.getUserByEmail(email);
		if (logging == null) {
			LOG.warn(String.format("User is not found: (email = %s)", email));
			return false;
		}
		// LOG.info("User is got from DB: " + logging);
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}

	public boolean loginPasswordOkay(String login, String password) {
		User logging = userDAO.getUserByLogin(login);
		if (logging == null) {
			LOG.warn(String.format("User is not found: (login = %s)", login));
			return false;
		}
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}
}