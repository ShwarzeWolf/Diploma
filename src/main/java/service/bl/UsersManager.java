package service.bl;

import java.sql.SQLException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import service.dal.dbdriver.DBDriver;
import service.dal.dbdriver.SQLite;
import service.dal.entity.UserEntity;
import service.dal.searchrequest.SearchRequest;
import service.dal.searchrequest.UserSearchRequest;
import service.util.Util;
import service.dal.dao.UsersDAO;


public class UsersManager {
	private final UsersDAO usersDAO;
	static Logger log = LogManager.getLogger(UsersManager.class.getName());
	// private final String connectionType;

	public UsersManager(String connectionType) throws BLException {
		// this.connectionType = connectionType;
		DBDriver db;
		try {
			db = new SQLite("./Database/Database.sqlite3");
		} catch (SQLException ex) {
			throw new BLException(ex);
		} catch (ClassNotFoundException ex) {
			throw new BLException("Class not found");
		} catch (Exception ex) {
			throw new BLException("Unknown exception at Users conctructor", ex);
		}
		usersDAO = new UsersDAO(db);
	}

	public UsersManager() throws BLException {
		this("SQLite");
	}

	/* default */ UserEntity getOne(SearchRequest<UserEntity> sr) throws BLException {
		try {
			return usersDAO.getOne(sr);
		} catch (SQLException ex) {
			if (ex.getMessage().equals("SELECT is empty"))
				throw new BLException("No such User: " + sr);
			throw new BLException(ex);
		}
	}

	// public User[] getUsers() throws BLException {
	// UserDTO[] DTOUsers;
	// User[] Users;
	// try {
	// DTOUsers = this.Users.get(new UserSearchRequest());
	// } catch (Exception ex) {
	// throw new BLException(ex);
	// }
	// Users = new User[DTOUsers.length];
	// for (int i = 0; i < DTOUsers.length; ++i)
	// Users[i] = new User(DTOUsers[i]);
	// return Users;
	// }
	public void addUser(String userName, String email, String password) throws BLException {
		try {
			usersDAO.insert(new UserEntity(userName, email, password));
		} catch (Exception ex) {
			throw new BLException(ex);
		}
	}

	public boolean passwordOkay(String email, String password) {
		try {
			log.info("Trying to get user: email=" + email);
			// log.info("Search string is " + new UserSearchRequest().setemail(email).whereString());
			UserEntity real = usersDAO.getOne(new UserSearchRequest().setemail(email));
			log.info("User is got from DB");
			if (Util.calcSHA256(password).equals(real.getHash1()) && Util.calcMD5(password).equals(real.getHash2()))
				return true;
		} catch (SQLException ex) {
			log.error(ex);
			return false;
		}
		return false;
	}
	// public User getUser(String UserName, String UserAddress) throws BLException {
	// try {
	// return new User(Users.getOne(new
	// UserSearchRequest().setName(UserName).setAddress(UserAddress)));
	// } catch (SQLException ex) {
	// if (ex.getMessage().equals("SELECT is empty"))
	// throw new BLException("No such User: (" + UserName + ", " + UserAddress +
	// ")");
	// throw new BLException(ex);
	// } catch (Exception ex) {
	// throw new BLException(ex);
	// }
	// }
	// public void deleteUser(User User) throws BLException {
	// try {
	// Users.delete(User.getDTO());
	// } catch (Exception ex) {
	// throw new BLException(ex);
	// }
	// }
}