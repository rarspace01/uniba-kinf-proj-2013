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

			PreparedStatement prepStatement = DataManagerSQLiteSingleton.getInstance().getConnection().prepareStatement("REPLACE INTO backgroundimage (imageid,scalex, scaley, x, y, image) VALUES ('1','1','1','"
					+ toBeSaved.getImageDimensions().getWidth()
					+ "','"+toBeSaved.getImageDimensions().getHeight()+"', ?); ");
			
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
			.getInstance().select("SELECT imageid, scalex, scaley, x, y, image FROM backgroundimage;");
			
			while(resultSet.next()){
				
				
				returnBackgroundImage = new BackgroundImage();
				returnBackgroundImage.setImageDimensions(new Dimension(resultSet.getInt("scalex"), resultSet.getInt("scaley")));
				returnBackgroundImage.setData(resultSet.getBytes("image"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnBackgroundImage;
	}

}
