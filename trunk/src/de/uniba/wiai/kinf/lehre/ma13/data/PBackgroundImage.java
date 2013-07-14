package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.model.BackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;

public class PBackgroundImage {

	public void saveToDB(IBackgroundImage toBeSaved) {

		try {

			DataManagerSQLite
					.getInstance()
					.execute(
							"REPLACE INTO backgroundimage (imageid,scalex, scaley, x, y, imagepath) VALUES ('1','1','1','"
									+ toBeSaved.getImageDimensions().getWidth()
									+ "','"+toBeSaved.getImageDimensions().getHeight()+"','"+toBeSaved.getImagePath()+"'); ");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public IBackgroundImage loadFromDB() {
		IBackgroundImage returnBackgroundImage = null;
		
		try {
			ResultSet resultSet= 		
			DataManagerSQLite
			.getInstance().select("SELECT imageid, scalex, scaley, x, y, imagepath FROM backgroundimage;");
			
			while(resultSet.next()){
				
				
				returnBackgroundImage = new BackgroundImage();
				returnBackgroundImage.setImageDimensions(new Dimension(resultSet.getInt("scalex"), resultSet.getInt("scaley")));
				returnBackgroundImage.setImagePath(resultSet.getString("imagepath"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnBackgroundImage;
	}

}
