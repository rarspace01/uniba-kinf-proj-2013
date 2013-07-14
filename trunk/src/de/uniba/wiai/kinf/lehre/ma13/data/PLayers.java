package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class PLayers {

	public void saveToDB(List<ILayer> toBeSaved) {

		PPolygons ppolygon = new PPolygons();
		
		for (int i = 0; i < toBeSaved.size(); i++) {

			// save the Layer

			//toBeSaved.get(i);

			try {
				DataManagerSQLiteSingleton.getInstance()
						.execute(
								"REPLACE INTO layer (layerid, name, isvisible, color) VALUES ('"
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
	
	public List<ILayer> loadFromDB() {

		List<ILayer> layerList = new LinkedList<ILayer>();
		
		try {
			ResultSet resultSet= 		
			DataManagerSQLiteSingleton
			.getInstance().select("SELECT layerid, name, isvisible, color FROM layer;");
			
			while(resultSet.next()){
				
				ILayer layer=new Layer(resultSet.getLong("layerid"));
				
				layer.setName(resultSet.getString("name"));
				layer.setVisibility(resultSet.getBoolean("isvisible"));
				layer.setColor(new Color(resultSet.getInt("color")));
				
				//
				PPolygons ppolygons = new PPolygons();
				//ppolygons
				layer.getGeometries().clear();
				layer.getGeometries().addAll(ppolygons.loadFromDB(layer));
				
				
				layerList.add(layer);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return layerList;
		
	}

}
