package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManagerSQL;

/**
 * class fir SQLite access. singelton - supports only one instances. should be
 * the default {@link IDataManagerSQL} to be used
 * 
 * @author denis
 * 
 */
public class DataManagerSQLiteSingleton implements IDataManagerSQL {
	private static DataManagerSQLiteSingleton uniqueInstance_ = null;
	private static String filename_ = "default.sqlite";

	private java.sql.Connection conn_;
	private Statement stmt_;

	private DataManagerSQLiteSingleton(String filename) {
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
		uniqueInstance_ = this;
	}

	public static DataManagerSQLiteSingleton getInstance(String filename) {
		if (uniqueInstance_ == null) {
			new DataManagerSQLiteSingleton(filename);
		}
		return uniqueInstance_;
	}

	public static DataManagerSQLiteSingleton getInstance() {
		if (uniqueInstance_ == null) {
			new DataManagerSQLiteSingleton(filename_);
		}
		return uniqueInstance_;
	}

	/**
	 * diposes the current Datamaner, closes everything, frees the file to
	 * ensure proper GC handling
	 */
	public void dispose() {
		try {
			stmt_.close();
			conn_.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uniqueInstance_ = null;
	}

	public ResultSet select(String SQLString) throws SQLException {
		return stmt_.executeQuery(SQLString);
	}

	/**
	 * unsichere Methode für update/insert into, da kein Rückgabewert und somit
	 * keine Fehlerüberprüfung möglich
	 */
	public void execute(String SQLString) throws SQLException {
		stmt_.executeUpdate(SQLString);
	}

	public Connection getConnection() {
		return conn_;
	}

	public String getCurrentFilename() {
		return filename_;
	}

}