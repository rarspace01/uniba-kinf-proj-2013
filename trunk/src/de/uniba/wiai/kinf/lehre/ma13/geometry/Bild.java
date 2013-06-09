package de.uniba.wiai.kinf.lehre.ma13.geometry;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;

import de.uniba.wiai.kinf.lehre.ma13.ICanvas;

public class Bild 
	implements IGeometry
{
	private Image _image;
	
	public Bild(final Image image)
	{
		_image = image;
	}
	
	@Override
	public void draw(ICanvas canvas, Graphics graphics)
	{
		graphics.drawImage(_image, 0, 0, new ImageObserver() {
			
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y,
					int width, int height) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}
}
