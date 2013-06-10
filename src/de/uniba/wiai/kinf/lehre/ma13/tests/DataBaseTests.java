package de.uniba.wiai.kinf.lehre.ma13.tests;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import de.uniba.wiai.kinf.lehre.ma13.data.DataManager;

public class DataBaseTests {

	@Test
	public void test() {
		
		DataManager dm= new DataManager();
		dm.openDb("test.dbd");
		
		ResultSet rs;
		
		rs=dm.select("SELECT * FROM points");
		
		try {
			
			while(rs.next()){
				System.out.println(rs.getString(1)+" - "+rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
