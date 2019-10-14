package service.dal.entity;

import java.sql.SQLException;
import service.util.Util;
import java.sql.ResultSet;

public class UserEntity implements Entity {
    private int id;
    private String name, email, hash1, hash2;
    boolean isComplete;

    public UserEntity(String userName, String email, String password) throws SQLException {
        if (userName == null || email == null || password == null || userName.equals("null") || email.equals("null")
                || password.equals("null"))
            throw new SQLException("UserName, Email and Password must not be null");
        if (userName.contains("'") || email.contains("'") || userName.contains("\"") || email.contains("\""))
            throw new SQLException("UserName and Email cannot contain quotes");
        id = -1;
        this.name = userName;
        this.email = email;

        hash1 = Util.calcSHA256(password);
        hash2 = Util.calcMD5(password);
        
        
        isComplete = false;
    }

    public UserEntity(ResultSet rs) throws SQLException {
        try {
            rs.getString("UserID");
            rs.getString("Name");
            rs.getString("Email");
            rs.getString("Hash1");
            rs.getString("Hash2");
        } catch (SQLException ex) {
            if (ex.getMessage().equals("ResultSet closed"))
                throw ex;
            throw new SQLException("Error at UserEntity constructor from ResultSet\n", ex);
        }
        id = new Integer(rs.getString("UserID"));
        name = rs.getString("Name");
        email = rs.getString("Email");
        hash1 = rs.getString("PasswdHash1");
        hash2 = rs.getString("PasswdHash2");
        isComplete = true;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getHash1() {
        return hash1;
    }

    public String getHash2() {
        return hash2;
    }

    public boolean isComplete() {
        return this.isComplete;
    }

    @Override
    public String toString() {
        return String.format("(UserEntity) %d: %s; %s; %s + %s", id, name, email, hash1, hash2);
    }
}