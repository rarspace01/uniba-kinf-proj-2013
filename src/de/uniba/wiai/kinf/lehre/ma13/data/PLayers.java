package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

/**
 * Persistance class of Layer - handles the saving and loading of multiple Layer Objects
 * @author denis
 *
 */
public class PLayers {
	
	private IAppDelegate appDelegate_;
	
	public PLayers(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}

	public void saveToDB(List<ILayer> toBeSaved) {

		PPolygons ppolygon = new PPolygons(appDelegate_);
		
		for (int i = 0; i < toBeSaved.size(); i++) {

			// save the Layer

			if(toBeSaved.get(i).getColor() == null){
				toBeSaved.get(i).setColor(Color.black);
			}
			
			//toBeSaved.get(i);

			try {
				
				int isVisible=0;
				if(toBeSaved.get(i).isVisible()){
					isVisible=1;
				}
				
				DataManagerSQLiteSingleton.getInstance()
						.execute(
								"REPLACE INTO layer (layerid, name, isvisible, color, opacity) VALUES ('"
										+ toBeSaved.get(i).getObjectId()
										+ "','" + toBeSaved.get(i).getName()
										+ "','" + isVisible
										+ "','"
										+ toBeSaved.get(i).getColor().getRGB()
										+ "','"
										+ toBeSaved.get(i).getOpacity()
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
			.getInstance().select("SELECT layerid, name, isvisible, color, opacity FROM layer;");
			
			while(resultSet.next()){
				
				ILayer layer=new Layer(resultSet.getLong("layerid"));

				// check for global object-id
				if(resultSet.getLong("layerid") > appDelegate_.getUtil().getId()) {
					appDelegate_.getUtil().setId(resultSet.getLong("layerid"));
				}
				
				layer.setName(resultSet.getString("name"));
				layer.setVisibility(resultSet.getBoolean("isvisible"));
				if(resultSet.getBoolean("isvisible")){
					System.out.println("Layer ["+layer.getName()+"] is visible");
				}
				layer.setColor(new Color(resultSet.getInt("color")));
				layer.setOpacity(resultSet.getFloat("opacity"));
				//
				PPolygons ppolygons = new PPolygons(appDelegate_);
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
