package de.uniba.wiai.kinf.lehre.ma13.data.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * interface for SQL based Datamanger implementations. ensures every datamanger
 * supports the given methods.
 * 
 * @author denis
 * 
 */
public interface IDataManagerSQL {

	/**
	 * diposes the current Datamaner, closes everything, frees the file to
	 * ensure proper GC handling
	 */
	public void dispose();

	/**
	 * executes a sql select command
	 * 
	 * @param SQLString
	 *            - the SQL String of the select query to be executed
	 * @return {@link ResultSet} of the Query
	 * @throws SQLException
	 */
	public ResultSet select(String SQLString) throws SQLException;

	/**
	 * executes a sql execute command
	 * 
	 * @param SQLString
	 *            - - the SQL String of the query to be executed
	 * @throws SQLException
	 */
	public void execute(String SQLString) throws SQLException;

	/**
	 * retrieves the connection (for statements etc.)
	 * 
	 * @return {@link Connection} of the current {@link IDataManagerSQL} object
	 */
	public Connection getConnection();

}
