package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasse für SQLite Zugriff
 * @author denis
 *
 */
public class DataManagerSQLite {
	private static DataManagerSQLite uniqueInstance_ = null;
	private static String filename_ = "default.db";
	
	private java.sql.Connection conn_;
	private Statement stmt_;
	
	private DataManagerSQLite(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn_ = DriverManager.getConnection("jdbc:sqlite:"+filename);
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

	public static DataManagerSQLite getInstance(String filename)
	{
			if(uniqueInstance_== null)
			{
			new DataManagerSQLite(filename);
			}
		return uniqueInstance_;	
	}
	
	public static DataManagerSQLite getInstance()
	{
			if(uniqueInstance_== null)
			{
			new DataManagerSQLite(filename_);
			}
		return uniqueInstance_;	
	}
	
	public void dispose()
	{
		try {
			stmt_.close();
			conn_.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uniqueInstance_=null;
	}
	
	//Methode für normale Select Operationen
	public ResultSet select(String SQLString) throws SQLException
	{
			return stmt_.executeQuery(SQLString);
	}

	/*unsichere Methode für update/insert into, da kein Rückgabewert
	 * und somit keine Fehlerüberprüfung möglich
	 */
	public void execute(String SQLString) throws SQLException
	{
		stmt_.executeUpdate(SQLString);
	}
	
    public Connection getConnection() {
        return conn_;
    }

	
}