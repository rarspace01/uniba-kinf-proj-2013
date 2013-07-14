package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Klasse f�r SQLite Zugriff
 * @author denis
 *
 */
public class DataManagerSQLite {
	private static String filename_ = "default.sqlite";
	
	private java.sql.Connection conn_;
	private Statement stmt_;
	
	public DataManagerSQLite(String filename) {
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
	}
	
	//Methode f�r normale Select Operationen
	public ResultSet select(String SQLString) throws SQLException
	{
			return stmt_.executeQuery(SQLString);
	}

	/*unsichere Methode f�r update/insert into, da kein R�ckgabewert
	 * und somit keine Fehler�berpr�fung m�glich
	 */
	public void execute(String SQLString) throws SQLException
	{
		stmt_.executeUpdate(SQLString);
	}
	
    public Connection getConnection() {
        return conn_;
    }

	
}