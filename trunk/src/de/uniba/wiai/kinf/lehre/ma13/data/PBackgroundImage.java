package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.model.BackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;

/**
 * implements the persistance function of a given {@link IBackgroundImage}
 * object
 * 
 * @author denis
 * 
 */
public class PBackgroundImage {

	/**
	 * saves the given {@link IBackgroundImage} object
	 * 
	 * @param toBeSaved
	 */
	public void saveToDB(IBackgroundImage toBeSaved) {

		try {
			// prepare statement
			PreparedStatement prepStatement = DataManagerSQLiteSingleton
					.getInstance()
					.getConnection()
					.prepareStatement(
							"REPLACE INTO backgroundimage (imageid,scalex, scaley, x, y, image, opacity) VALUES ('1','1','1','"
									+ toBeSaved.getImageDimensions().width
									+ "','"
									+ toBeSaved.getImageDimensions().height
									+ "', ?,'"
									+ toBeSaved.getOpacity()
									+ "'); ");
			// set the binary data of the image the image byte[] data
			prepStatement.setBytes(1, toBeSaved.getData());
			// execute insert
			prepStatement.execute();
			prepStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * loads a {@link IBackgroundImage} object from the database
	 * 
	 * @return {@link IBackgroundImage} - background image to be retrieved
	 */
	public IBackgroundImage loadFromDB() {
		IBackgroundImage returnBackgroundImage = null;

		try {
			// retrieve Resultset for
			ResultSet resultSet = DataManagerSQLiteSingleton
					.getInstance()
					.select("SELECT imageid, scalex, scaley, x, y, image, opacity FROM backgroundimage;");

			while (resultSet.next()) {
				
				//create a BackgroudImage instance
				returnBackgroundImage = new BackgroundImage();
				returnBackgroundImage.setImageDimensions(new Dimension(
						resultSet.getInt("x"), resultSet.getInt("y")));
				// setting the data of the backgrond image
				returnBackgroundImage.setData(resultSet.getBytes("image"));
				returnBackgroundImage.setOpacity(resultSet.getFloat("opacity"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnBackgroundImage;
	}

}
