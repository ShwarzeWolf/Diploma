package service.dal.dao;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

import service.dal.dbdriver.DBDriver;
import service.dal.entity.UserEntity;
// import DAL.SearchRequest.SearchRequest;

public class UsersDAO implements DAO<UserEntity> {
	private final DBDriver db;
	private static final String tableName = "Users";
	public UsersDAO(DBDriver db) {
		this.db = db;
	}
	public void insert(UserEntity userEntity) throws SQLException {
        db.execute(String.format(
                "INSERT INTO %s (Name, Email, PasswdHash1, PasswdHash2) VALUES ('%s', '%s', '%s', '%s')",
                tableName, userEntity.getName(), userEntity.getEmail(), userEntity.getHash1(), userEntity.getHash2()
            )
        );
	}
	public void update(UserEntity oldShop, UserEntity newShop) throws SQLException {
		db.execute(String.format("UPDATE %s SET ShopName = '%s', Address = '%s' where ShopID = %d", tableName, newShop.getName(), newShop.getAddress(), oldShop.getName(), oldShop.getAddress()));
	}
	// public void delete(UserEntity userEntity) throws SQLException {
	// 	db.execute(String.format("DELETE FROM %s where ShopID = %s", tableName, userEntity.getID()));
	// }
	// public UserEntity getOne(SearchRequest<UserEntity> sr) throws SQLException {
	// 	ResultSet rs = db.execute(String.format("SELECT * from %s%s", tableName, sr.whereString()));
	// 	UserEntity result;
	// 	try {
	// 		result = new UserEntity(rs);
	// 	} catch (SQLException ex) {
	// 		if (ex.getMessage().equals("ResultSet closed"))
	// 			throw new SQLException("SELECT is empty");
	// 		else
	// 			throw ex;
	// 	}
	// 	try {
	// 		rs.close();
	// 	} catch (Exception ex) { }
	// 	return result;
	// }
	// public UserEntity[] get(SearchRequest<UserEntity> sr) throws SQLException {
	// 	ResultSet rs = db.execute(String.format("SELECT * from %s%s", tableName, sr.whereString()));
	// 	List<UserEntity> ans = new ArrayList<UserEntity>();
	// 	while (rs.next())
	// 		ans.add(new UserEntity(rs));
	// 	return ans.toArray(new UserEntity[0]);
	// }
}