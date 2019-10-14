package service.dal.dbdriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class SQLite implements DBDriver {
	protected String path;
	protected String connectionString;
	protected Connection conn;

	public SQLite(String dbPath) throws ClassNotFoundException, SQLException {
		path = dbPath;
		connectionString = "jdbc:sqlite:" + path;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException ex) {
			System.err.println("Class for sqlite database is not found, aborting");
			throw ex;
		}
		conn = DriverManager.getConnection(connectionString);
	}
	public ResultSet execute(String queryString) throws SQLException {
		ResultSet rs = null;
		Statement statement;
		statement = conn.createStatement();
		try {
			rs = statement.executeQuery(queryString);
		} catch (SQLException ex) {
			if (ex.getMessage().equals("query does not return ResultSet"))
				return null;
			else
				throw ex;
		}
		return rs;
    }
}