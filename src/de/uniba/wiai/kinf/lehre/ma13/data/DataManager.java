package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;

/**
 * class for managing the save files
 * @author denis
 *
 */
public class DataManager implements IDataManager{

	private String filename_="";
	private DataManagerSQLite currentDB_;

	/**
	 * creates the initial tables
	 */
	private void createTables() {
		
		try {
			//point table
			currentDB_.execute("CREATE TABLE points (polyid NUMERIC, pointid INTEGER PRIMARY KEY, ordernumber NUMERIC, x NUMERIC, y NUMERIC, colour NUMERIC);");
			//poly table
			currentDB_.execute("CREATE TABLE poly (polyid INTEGER PRIMARY KEY, colour NUMERIC, polygroupid NUMERIC, isvisible NUMERIC);");
			//polygroup
			currentDB_.execute("CREATE TABLE polygroup (polygroupid INTEGER PRIMARY KEY, name TEXT, isvisible NUMERIC, colour NUMERIC);");
			//background image
			currentDB_.execute("CREATE TABLE backgroundimage (imageid INTEGER PRIMARY KEY, scalex NUMERIC, scaley NUMERIC, x NUMERIC, y NUMERIC, image BLOB);");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * checks if the sqlite db is empty
	 * @return
	 */
	private boolean isEmpty(){
		ResultSet rs;
		int count=0;
		boolean isEmpty = true;
		
		try {
			rs = currentDB_.select("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name != 'android_metadata' AND name != 'sqlite_sequence';");
			
			while(rs.next()){
				count=rs.getInt(1);
				System.out.println("setted count to: "+count);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count>0){
			isEmpty=false;
		}
		
		return isEmpty;
	}


	@Override
	public void closeDb() {
		currentDB_.dispose();
	}



	@Override
	public ResultSet select(String sql) {
		ResultSet rs = null;
		try {
			rs=this.currentDB_.select(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public void execute(String sql) {
		try {
			this.currentDB_.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void openDb(String filename) {
		this.filename_=filename;
		currentDB_=DataManagerSQLite.getInstance(this.filename_);
		
		if(isEmpty()){
			createTables();
		}
	}
	
}
