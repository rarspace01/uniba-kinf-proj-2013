package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

/**
 * Persistance class of Polygon - handles the saving and loading of multiple
 * Polygon Objects
 * 
 * @author denis
 * 
 */
public class PPolygons {

	private IAppDelegate appDelegate_;

	public PPolygons(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}

	/**
	 * saves a given List of {@link IGeometry} Objects of a {@link Polygon}
	 * instance to the database
	 * 
	 * @param toBeSaved
	 */
	public void saveToDB(List<IGeometry> toBeSaved) {

		// instance of PPoint for saving the actual points
		PPoint ppoint = new PPoint();

		// iterate over the given list of IGeometry
		for (int i = 0; i < toBeSaved.size(); i++) {

			// check if given object is of instance Polygon
			if (toBeSaved.get(i) instanceof Polygon) {

				try {

					// fix for saving a boolean in sqlite without using prepared
					// statement
					int isVisible = 0;
					if (toBeSaved.get(i).isVisible()) {
						isVisible = 1;
					}

					if (toBeSaved.get(i).getColor() == null) {

						// execute the sql command with null color
						DataManagerSQLiteSingleton
								.getInstance()
								.execute(
										"REPLACE INTO polygon (polygonid, layerid, name, isvisible, color, opacity) VALUES ('"
												+ toBeSaved.get(i)
														.getObjectId()
												+ "','"
												+ toBeSaved.get(i).getParent()
														.getObjectId()
												+ "','"
												+ toBeSaved.get(i).getName()
												+ "','"
												+ isVisible
												+ "','"
												+ "0"
												+ "','"
												+ toBeSaved.get(i).getOpacity()
												+ "'); ");

					} else {
						// execute the sql command
						DataManagerSQLiteSingleton
								.getInstance()
								.execute(
										"REPLACE INTO polygon (polygonid, layerid, name, isvisible, color, opacity) VALUES ('"
												+ toBeSaved.get(i)
														.getObjectId()
												+ "','"
												+ toBeSaved.get(i).getParent()
														.getObjectId()
												+ "','"
												+ toBeSaved.get(i).getName()
												+ "','"
												+ isVisible
												+ "','"
												+ toBeSaved.get(i).getColor()
														.getRGB()
												+ "','"
												+ toBeSaved.get(i).getOpacity()
												+ "'); ");

					}

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				System.out
						.println("given IGeometry Object is not an instance of Polygon - dont save the Object");
			}

			// save the points
			ppoint.saveToDB(toBeSaved.get(i));

		}

	}

	/**
	 * loads the polygons of a given {@link ILayer} object from the database
	 * 
	 * @param layer
	 *            - {@link ILayer} the polygins should be retrieved from
	 * @return
	 */
	public List<IGeometry> loadFromDB(ILayer layer) {

		// build IGeometry List
		List<IGeometry> polygonList = new LinkedList<IGeometry>();

		// create a second instance of the SQLite Datamanger to work recursivly
		// with a subquery without using the same connection
		DataManagerSQLite dataManager = new DataManagerSQLite(
				DataManagerSQLiteSingleton.getInstance().getCurrentFilename());

		try {
			ResultSet resultSet = dataManager
					.select("SELECT polygonid, layerid, name, isvisible, color, opacity FROM polygon WHERE layerid = '"
							+ layer.getObjectId() + "';");

			while (resultSet.next()) {

				IGeometry polygon = new Polygon(resultSet.getLong("polygonid"));

				// check for global object-id
				if (resultSet.getLong("polygonid") > appDelegate_.getUtil()
						.getId()) {
					appDelegate_.getUtil()
							.setId(resultSet.getLong("polygonid"));
				}

				polygon.setParent(layer);
				polygon.setName(resultSet.getString("name"));
				polygon.setVisibility(resultSet.getBoolean("isvisible"));
				System.out.println("Color on Polygon: "
						+ resultSet.getInt("color"));
				if (resultSet.getInt("color") < 0)
					polygon.setColor(new Color(resultSet.getInt("color")));
				polygon.setOpacity(resultSet.getFloat("opacity"));

				PPoint pPoint = new PPoint();
				// pPoint.

				if (polygon instanceof Polygon) {
					((Polygon) polygon).getPoints().clear();
					((Polygon) polygon).getPoints().addAll(
							pPoint.loadFromDB(polygon));
				}

				polygonList.add(polygon);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dataManager.dispose();

		return polygonList;
	}

}
