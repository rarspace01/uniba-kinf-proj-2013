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
	private static DataManagerSQLite uniqueInstance = null;
	
	private java.sql.Connection conn;
	private Statement stmt;
	
	private DataManagerSQLite(String filename) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+filename);
			stmt = conn.createStatement();
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			System.exit(1);
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.exit(1);
		}
		uniqueInstance = this;
	}

	public static DataManagerSQLite getInstance(String filename)
	{
			if(uniqueInstance== null)
			{
			new DataManagerSQLite(filename);
			}
		return uniqueInstance;	
	}
	
	public void dispose()
	{
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		uniqueInstance=null;
	}
	
	//Methode für normale Select Operationen
	public ResultSet select(String SQLString) throws SQLException
	{
			return stmt.executeQuery(SQLString);
	}

	/*unsichere Methode für update/insert into, da kein Rückgabewert
	 * und somit keine Fehlerüberprüfung möglich
	 */
	public void execute(String SQLString) throws SQLException
	{
		stmt.executeUpdate(SQLString);
	}
	
    public Connection getConnection() {
        return conn;
    }

	
}