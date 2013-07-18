package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

/**
 * Persistence class of Point - handles the saving and loading of the Points
 * from a Polygon Object
 * 
 * @author denis
 * 
 */
public class PPoint {

	/**
	 * saves the Points of a given {@link IGeometry} Object
	 * 
	 * @param toBeSaved
	 *            - Polygon to be saved
	 */
	public void saveToDB(IGeometry toBeSaved) {

		// check if given object is an instance of Polygon
		if (toBeSaved instanceof Polygon) {

			// retrieve the points of the given Polygon
			List<Point> pointList = ((Polygon) toBeSaved).getPoints();

			// iterate over the points of the polygon
			for (int i = 0; i < pointList.size(); i++) {

				try {
					// execute the insert for the specific point
					DataManagerSQLiteSingleton.getInstance().execute(
							"REPLACE INTO point (polygonid, x, y) VALUES ('"
									+ toBeSaved.getObjectId() + "','"
									+ pointList.get(i).x + "','"
									+ pointList.get(i).y + "'); ");

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/**
	 * method to load the list of points from a given polygon {@link IGeometry}
	 * 
	 * @param polygon
	 * @return
	 */
	public List<Point> loadFromDB(IGeometry polygon) {

		// initate pointList for the polygon
		List<Point> pointList = new LinkedList<Point>();

		// get a second Datamanager, as we are workign in a sub qry on a level
		// above
		DataManagerSQLite dataManager = new DataManagerSQLite(
				DataManagerSQLiteSingleton.getInstance().getCurrentFilename());

		try {
			// execute the SQL query
			ResultSet resultSet = dataManager
					.select("SELECT pointid, polygonid, x, y FROM point WHERE polygonid = '"
							+ polygon.getObjectId() + "';");

			// iterate over the resultset
			while (resultSet.next()) {
				// create a point and fill the values
				Point point = new Point();
				point.x = resultSet.getInt("x");
				point.y = resultSet.getInt("y");
				// add the point to the given pointList
				pointList.add(point);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// close the Database manager to free resources and prevent memory leak
		dataManager.dispose();

		return pointList;
	}

}
