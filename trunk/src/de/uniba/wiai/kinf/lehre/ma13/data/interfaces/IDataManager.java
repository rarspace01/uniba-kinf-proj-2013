package de.uniba.wiai.kinf.lehre.ma13.data.interfaces;

import java.sql.ResultSet;

/**
 * itnerface for  handling the databasefiles
 * @author denis
 *
 */
public interface IDataManager {

	/**
	 * open a SQLite DB
	 * @param filename
	 */
	public void openDb(String filename);
	
	/**
	 * close the current DB
	 */
	public void closeDb();
	
	/**
	 * executes a sql select command 
	 * @param sql - {@link String}
	 * @return {@link ResultSet}
	 */
	public ResultSet select(String sql);
	/**
	 * executes a sql select command 
	 * @param sql - {@link String}
	 * @return {@link ResultSet}
	 */
	public void execute(String sql);
	
}
