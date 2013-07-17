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
 * Persistance class of Layer - handles the saving and loading of multiple Layer
 * Objects
 * 
 * @author denis
 * 
 */
public class PLayers {

	private IAppDelegate appDelegate_;

	public PLayers(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}

	/**
	 * saves a given {@link ILayer} {@link List} to the database. recursively
	 * saves the objects inside the {@link Layer}
	 * 
	 * @param toBeSaved
	 */
	public void saveToDB(List<ILayer> toBeSaved) {

		// create polygon perstiance object, which can save every polygon inside
		// of a layer
		PPolygons ppolygon = new PPolygons(appDelegate_);

		// iterate over the List of Layers
		for (int i = 0; i < toBeSaved.size(); i++) {

			// if the current Layer hasnt a set color, the color will be set to
			// black (default color)
			if (toBeSaved.get(i).getColor() == null) {
				toBeSaved.get(i).setColor(Color.black);
			}

			try {

				// transfer boolean to save boolean without using prepared
				// statement
				int isVisible = 0;
				if (toBeSaved.get(i).isVisible()) {
					isVisible = 1;
				}

				// execute SQL query for saving the current layer
				DataManagerSQLiteSingleton.getInstance().execute(
						"REPLACE INTO layer (layerid, name, isvisible, color, opacity) VALUES ('"
								+ toBeSaved.get(i).getObjectId() + "','"
								+ toBeSaved.get(i).getName() + "','"
								+ isVisible + "','"
								+ toBeSaved.get(i).getColor().getRGB() + "','"
								+ toBeSaved.get(i).getOpacity() + "'); ");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			// save the Geometry/polygon from the current layer
			ppolygon.saveToDB(toBeSaved.get(i).getGeometries());

		}

	}

	/**
	 * retrieves the layers from the database
	 * 
	 * @return <{@link List}>{@link Ilayer} - the List of Layers from the
	 *         Database
	 */
	public List<ILayer> loadFromDB() {

		// initialize List for returning layers
		List<ILayer> layerList = new LinkedList<ILayer>();

		try {
			// execute SQL query
			ResultSet resultSet = DataManagerSQLiteSingleton
					.getInstance()
					.select("SELECT layerid, name, isvisible, color, opacity FROM layer;");

			// iterate over the Resultset
			while (resultSet.next()) {

				// creating a Layer object
				ILayer layer = new Layer(resultSet.getLong("layerid"));

				// check for global object-id
				if (resultSet.getLong("layerid") > appDelegate_.getUtil()
						.getId()) {
					appDelegate_.getUtil().setId(resultSet.getLong("layerid"));
				}

				// setting the default attributes
				layer.setName(resultSet.getString("name"));
				layer.setVisibility(resultSet.getBoolean("isvisible"));
				// debug
//				if (resultSet.getBoolean("isvisible")) {
//					System.out.println("Layer [" + layer.getName()
//							+ "] is visible");
//				}
				layer.setColor(new Color(resultSet.getInt("color")));
				layer.setOpacity(resultSet.getFloat("opacity"));
				// creating Persistance object for the polygons
				PPolygons ppolygons = new PPolygons(appDelegate_);
				// clear the list from last iteration (shouldnt be nedded)
				layer.getGeometries().clear();
				// adds the polygons from the database to the current layer
				layer.getGeometries().addAll(ppolygons.loadFromDB(layer));

				//add te current leayer to the layer return list
				layerList.add(layer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return layerList;

	}

}
