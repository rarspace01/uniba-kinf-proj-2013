package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;

/**
 * class for managing the save files
 * @author denis
 *
 */
public class DataBaseManager implements IDataManager{

	private String filename_="";
	private DataManagerSQLiteSingleton currentDB_;

	/**
	 * creates the initial tables
	 */
	private void createTables() {
		
		try {
			//point table
			currentDB_.execute("CREATE TABLE point (pointid INTEGER PRIMARY KEY, polygonid NUMERIC, x NUMERIC, y NUMERIC);");
			//poly table
			currentDB_.execute("CREATE TABLE polygon (polygonid INTEGER PRIMARY KEY, name TEXT, color NUMERIC, layerid NUMERIC, isvisible NUMERIC);");
			//layer table
			currentDB_.execute("CREATE TABLE layer (layerid INTEGER PRIMARY KEY, name TEXT, isvisible NUMERIC, color NUMERIC);");
			//background image
			currentDB_.execute("CREATE TABLE backgroundimage (imageid INTEGER PRIMARY KEY, scalex NUMERIC, scaley NUMERIC, x NUMERIC, y NUMERIC, imagepath TEXT);");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void cleanTables(){
		
		try {
			currentDB_.execute("DELETE FROM point");
			currentDB_.execute("DELETE FROM polygon");
			currentDB_.execute("DELETE FROM layer");
			currentDB_.execute("DELETE FROM backgroundimage");
			
			
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
				System.out.println("table count: "+count);
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
		currentDB_=DataManagerSQLiteSingleton.getInstance(this.filename_);
		
		if(isEmpty()){
			createTables();
		}
	}
	
}
