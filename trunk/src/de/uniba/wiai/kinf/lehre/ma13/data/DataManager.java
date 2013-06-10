package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataManager {

	private String filename_="";
	private DataManagerSQLite currentDB_;
	
	public DataManager(String filename) {
		this.filename_=filename;
		currentDB_=DataManagerSQLite.getInstance(this.filename_);
		
		if(isEmpty()){
			createTables();
		}
		
	}
	

	/**
	 * creates the initial table
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
	
}
