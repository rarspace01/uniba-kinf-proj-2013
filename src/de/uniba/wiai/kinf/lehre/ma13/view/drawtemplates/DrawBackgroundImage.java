package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class DrawBackgroundImage extends DrawGeometry {

	private Image backgroundImage_;

	public DrawBackgroundImage(IAppDelegate appDelegate) {
		super(appDelegate);
	}
	
	@Override
	public void draw(Graphics g, IGeometry geometry) {

		IBackgroundImage backgroundGeometry = (IBackgroundImage)geometry;
	
		backgroundImage_ = backgroundGeometry.getImage();
		
		Point imageOrigin = appDelegate_.getUtil().toScreenCoordinates(new Point(0, 0));
		Dimension imageDimension = appDelegate_.getUtil().toScreenDimension(backgroundGeometry.getImageDimensions());
		/*Point imageDimension = appDelegate_.getUtil().toScreenCoordinates(
				new Point(
						backgroundGeometry.getImageDimensions().width,
						backgroundGeometry.getImageDimensions().height)
				);
		System.out.println("Origin: " + imageOrigin);
		System.out.println("Dimension: " + imageDimension);*/
		if(backgroundGeometry.getData() != null){
			System.out.println("Imagesize to draw: "+backgroundGeometry.getData().length);
		}
		
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

}
