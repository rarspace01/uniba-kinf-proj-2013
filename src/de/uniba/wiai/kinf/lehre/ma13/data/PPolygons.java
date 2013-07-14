package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.SQLException;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class PPolygons {

	public void saveToDB(List<IGeometry> toBeSaved){
		
		PPoint ppoint=new PPoint();
		
		for (int i = 0; i < toBeSaved.size(); i++) {

			try {
				DataManagerSQLite.getInstance()
						.execute(
								"REPLACE INTO polygon (polygonid, layerid, isvisible, color) VALUES ('"
										+ toBeSaved.get(i).getObjectId()
										+ "','" + toBeSaved.get(i).getParent().getObjectId()
										+ "','" + toBeSaved.get(i).isVisible()
										+ "','"
										+ toBeSaved.get(i).getColor().getRGB()
										+ "'); ");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// save the points
			ppoint.saveToDB(toBeSaved.get(i));

		}
		
	}
	
}
