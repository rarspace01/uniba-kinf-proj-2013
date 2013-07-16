package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class PPoint {

	public void saveToDB(IGeometry toBeSaved){
		
		if(toBeSaved instanceof Polygon){
		
			List<Point> pointList = ((Polygon) toBeSaved).getPoints();
			
			for (int i = 0; i < pointList.size(); i++) {
				
				try {
					DataManagerSQLiteSingleton.getInstance()
							.execute(
									"REPLACE INTO point (polygonid, x, y) VALUES ('"
											+ toBeSaved.getObjectId()
											+ "','" + pointList.get(i).x
											+ "','" + pointList.get(i).y
											+ "'); ");
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				// save the Geometry/poly
					//ppolygon.saveToDB(toBeSaved.get(i));
	
			}
		
		}
		
		
	}

	public List<Point> loadFromDB(IGeometry polygon) {
		
		List<Point> pointList = new LinkedList<Point>();
		
		DataManagerSQLite dataManager=new DataManagerSQLite(DataManagerSQLiteSingleton.getInstance().getCurrentFilename());
		
		try {
			ResultSet resultSet= 		
					dataManager.select("SELECT pointid, polygonid, x, y FROM point WHERE polygonid = '"+polygon.getObjectId()+"';");
			
			while(resultSet.next()){
				
				Point point=new Point();
				point.x = resultSet.getInt("x");
				point.y = resultSet.getInt("y");
				//point.setLocation(resultSet.getDouble("x"), resultSet.getDouble("y"));
				pointList.add(point);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataManager.dispose();
		
		return pointList;
	}	
	
}
