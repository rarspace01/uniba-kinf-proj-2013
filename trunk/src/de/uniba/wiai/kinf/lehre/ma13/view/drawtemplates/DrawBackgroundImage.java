package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class DrawBackgroundImage extends DrawGeometry {

	private Image backgroundImage_;
	private String backgroundImagePath_;

	public DrawBackgroundImage(IAppDelegate appDelegate) {
		super(appDelegate);
	}
	
	@Override
	public void draw(Graphics g, IGeometry geometry) {

		IBackgroundImage backgroundGeometry = (IBackgroundImage)geometry;
	
		try {
			if(backgroundImage_ == null || !backgroundGeometry.getImagePath().equals(backgroundImagePath_)) {
				backgroundImagePath_ = backgroundGeometry.getImagePath();
				backgroundImage_ = ImageIO.read(new File(backgroundGeometry.getImagePath()));
			}
			
			Point imageOrigin = appDelegate_.getUtil().toScreenCoordinates(new Point(0, 0));
			Dimension imageDimension = appDelegate_.getUtil().toScreenDimension(backgroundGeometry.getImageDimensions());
			/*Point imageDimension = appDelegate_.getUtil().toScreenCoordinates(
					new Point(
							backgroundGeometry.getImageDimensions().width,
							backgroundGeometry.getImageDimensions().height)
					);
			System.out.println("Origin: " + imageOrigin);
			System.out.println("Dimension: " + imageDimension);*/
			
			g.drawImage(backgroundImage_,
					imageOrigin.x,
					imageOrigin.y,
					imageDimension.width,
					imageDimension.height,
					new ImageObserver() {
				
				@Override
				public boolean imageUpdate(Image img, int infoflags, int x, int y,
						int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
		catch(IOException e) { }
	}

}
