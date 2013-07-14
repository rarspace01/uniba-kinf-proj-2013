package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Point;
import java.sql.SQLException;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class PPoint {

	public void saveToDB(IGeometry toBeSaved){
		
		if(toBeSaved instanceof Polygon){
		
			List<Point> pointList = ((Polygon) toBeSaved).getPoints();
			
			for (int i = 0; i < pointList.size(); i++) {
	
				try {
					DataManagerSQLite.getInstance()
							.execute(
									"REPLACE INTO point (polygonid, x, y) VALUES ('"
											+ toBeSaved.getObjectId()
											+ "','" + pointList.get(i).getX()
											+ "','" + pointList.get(i).getY()
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
	
}
