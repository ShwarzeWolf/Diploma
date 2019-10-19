package service.businesslogic;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import service.dal.models.UserEntity;
import service.utils.Utils;
import service.dal.dao.UsersDAO;

public class UsersManager {
	private final UsersDAO usersDAO;
	private final static Logger LOG = LogManager.getLogger(UsersManager.class.getName());

	public UsersManager() {
		usersDAO = new UsersDAO();
	}

	public UserEntity getUserByID(int id) {
		return usersDAO.getUserByID(id);
	}

	public UserEntity getUserByEmail(String email) {
		return usersDAO.getUserByEmail(email);
	}

	UserEntity[] getUsers() {
		return usersDAO.findAll().toArray(new UserEntity[0]);
	}

	public void addUser(String userName, String email, String password) {
		usersDAO.save(new UserEntity(userName, email, password));
	}

	public boolean passwordOkay(String email, String password) {
		LOG.info("Trying to get user: email=\"" + email + "\"");
		UserEntity logging = usersDAO.getUserByEmail(email);
		if (logging == null){ 
			LOG.warn(String.format("User is not found: (email = %s)", email));
			return false;
		}
		LOG.info("User is got from DB: " + logging);
		if (Utils.calcSHA256(password).equals(logging.getHash1()) && Utils.calcMD5(password).equals(logging.getHash2()))
			return true;
		return false;
	}
}