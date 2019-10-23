package service.businesslogic;

import java.sql.Date;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import service.dal.dao.DAOFabric;
import service.dal.dao.UsersDAO;
import service.dal.models.User;
import service.dal.models.UserType;
import service.utils.Utils;

public class UsersManager {

	private final UsersDAO usersDAO;
	private final static Logger LOG = LogManager.getLogger(UsersManager.class.getName());

	public UsersManager() {
		usersDAO = DAOFabric.getUserDAO();
	}

	public User getUserByID(int id) {
		return usersDAO.getUserByID(id);
	}

	public User getUserByEmail(String email) {
		return usersDAO.getUserByEmail(email);
	}

	User[] getUsers() {
		return usersDAO.findAll().toArray(new User[0]);
	}

	public void addUser(String email, String login, String userName, String password,
			UserTypeManager.USER_TYPE typeEnum) {
		Date registerDate = Date.valueOf(LocalDate.now());
		UserType userType = new UserTypeManager().getTypeByEnum(typeEnum);
		usersDAO.save(new User(email, login, userName, registerDate, password, userType));
	}

	public boolean passwordOkay(String email, String password) {
		LOG.info("Trying to get user: email=\"" + email + "\"");
		User logging = usersDAO.getUserByEmail(email);
		if (logging == null) {
			LOG.warn(String.format("User is not found: (email = %s)", email));
			return false;
		}
		LOG.info("User is got from DB: " + logging);
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}
}