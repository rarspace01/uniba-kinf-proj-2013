package de.uniba.wiai.kinf.lehre.ma13.data;

import java.sql.SQLException;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class PLayers {

	public void saveToDB(List<ILayer> toBeSaved) {

		PPolygons ppolygon = new PPolygons();
		
		for (int i = 0; i < toBeSaved.size(); i++) {

			// save the Layer

			//toBeSaved.get(i);

			try {
				DataManagerSQLite.getInstance()
						.execute(
								"REPLACE INTO layer (layerid, name, isvisible, colour) VALUES ('"
										+ toBeSaved.get(i).getObjectId()
										+ "','" + toBeSaved.get(i).getName()
										+ "','" + toBeSaved.get(i).isVisible()
										+ "','"
										+ toBeSaved.get(i).getColor().getRGB()
										+ "'); ");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// save the Geometry/poly
				ppolygon.saveToDB(toBeSaved.get(i).getGeometries());

		}

	}

}
