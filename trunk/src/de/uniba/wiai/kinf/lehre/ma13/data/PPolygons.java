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

public class PPolygons {
	
	private IAppDelegate appDelegate_;
	
	public PPolygons(IAppDelegate appDelegate) {
		appDelegate_ = appDelegate;
	}

	public void saveToDB(List<IGeometry> toBeSaved) {

		PPoint ppoint = new PPoint();

		for (int i = 0; i < toBeSaved.size(); i++) {

			try {

				int isVisible = 0;
				if (toBeSaved.get(i).isVisible()) {
					isVisible = 1;
				}

				if (toBeSaved.get(i).getColor() == null) {
					
					DataManagerSQLiteSingleton
					.getInstance()
					.execute(
							"REPLACE INTO polygon (polygonid, layerid, name, isvisible, color, opacity) VALUES ('"
									+ toBeSaved.get(i).getObjectId()
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

					DataManagerSQLiteSingleton
							.getInstance()
							.execute(
									"REPLACE INTO polygon (polygonid, layerid, name, isvisible, color, opacity) VALUES ('"
											+ toBeSaved.get(i).getObjectId()
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// save the points
			ppoint.saveToDB(toBeSaved.get(i));

		}

	}

	public List<IGeometry> loadFromDB(ILayer layer) {

		List<IGeometry> polygonList = new LinkedList<IGeometry>();

		System.out
				.println("DEBUG: filename: "
						+ DataManagerSQLiteSingleton.getInstance()
								.getCurrentFilename());

		DataManagerSQLite dataManager = new DataManagerSQLite(
				DataManagerSQLiteSingleton.getInstance().getCurrentFilename());

		try {
			ResultSet resultSet = dataManager
					.select("SELECT polygonid, layerid, name, isvisible, color, opacity FROM polygon WHERE layerid = '"
							+ layer.getObjectId() + "';");

			while (resultSet.next()) {

				IGeometry polygon = new Polygon(resultSet.getLong("polygonid"));

				// check for global object-id
				if(resultSet.getLong("polygonid") > appDelegate_.getUtil().getId()) {
					appDelegate_.getUtil().setId(resultSet.getLong("polygonid"));
				}
				
				polygon.setParent(layer);
				polygon.setName(resultSet.getString("name"));
				polygon.setVisibility(resultSet.getBoolean("isvisible"));
				System.out.println("Color on Polygon: "+resultSet.getInt("color"));
				if(resultSet.getInt("color") < 0)
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
