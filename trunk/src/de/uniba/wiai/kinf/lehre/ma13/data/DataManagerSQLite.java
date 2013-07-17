package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManagerSQL;

/**
 * class for SQLite access. supports multiple instances for sub qry contexts
 * needed in the load procedure of a {@link IDataManager}
 * 
 * @author denis
 * 
 */
public class DataManagerSQLite implements IDataManagerSQL {
	private static String filename_ = "default.sqlite";

	private java.sql.Connection conn_;
	private Statement stmt_;

	public DataManagerSQLite(String filename) {
		filename_ = filename;
		try {
			Class.forName("org.sqlite.JDBC");
			conn_ = DriverManager.getConnection("jdbc:sqlite:" + filename_);
			stmt_ = conn_.createStatement();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {

			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void dispose() {
		try {
			stmt_.close();
			conn_.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet select(String SQLString) throws SQLException {
		return stmt_.executeQuery(SQLString);
	}

	@Override
	public void execute(String SQLString) throws SQLException {
		stmt_.executeUpdate(SQLString);
	}

	@Override
	public Connection getConnection() {
		return conn_;
	}

}