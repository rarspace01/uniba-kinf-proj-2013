package de.uniba.wiai.kinf.lehre.ma13.data;

import java.awt.Dimension;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.uniba.wiai.kinf.lehre.ma13.model.BackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;

public class PBackgroundImage {

	public void saveToDB(IBackgroundImage toBeSaved) {

		try {

			PreparedStatement prepStatement = DataManagerSQLiteSingleton.getInstance().getConnection().prepareStatement("REPLACE INTO backgroundimage (imageid,scalex, scaley, x, y, image, opacity) VALUES ('1','1','1','"
					+ toBeSaved.getImageDimensions().width
					+ "','"+toBeSaved.getImageDimensions().height+"', ?,'"+toBeSaved.getOpacity()+"'); ");

			prepStatement.setBytes(1, toBeSaved.getData());
			
			prepStatement.execute();
			
			prepStatement.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public IBackgroundImage loadFromDB() {
		IBackgroundImage returnBackgroundImage = null;
		
		try {
			ResultSet resultSet= 		
			DataManagerSQLiteSingleton
			.getInstance().select("SELECT imageid, scalex, scaley, x, y, image, opacity FROM backgroundimage;");
			
			while(resultSet.next()){
				
				
				returnBackgroundImage = new BackgroundImage();
				returnBackgroundImage.setImageDimensions(new Dimension(resultSet.getInt("x"), resultSet.getInt("y")));
				returnBackgroundImage.setData(resultSet.getBytes("image"));
				returnBackgroundImage.setOpacity(resultSet.getFloat("opacity"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnBackgroundImage;
	}

}
