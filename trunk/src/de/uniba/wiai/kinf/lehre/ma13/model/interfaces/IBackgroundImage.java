package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.awt.Dimension;

public interface IBackgroundImage extends IGeometry
{
	/**
	 * returns the path for the current background image (base layer)
	 */
	public String getImagePath();
	
	/**
	 * set the path to the background-image (base layer)
	 */
	public void setImagePath(String imagePath);
	
	/**
	 * get the original dimensions of the (scaled) image. The image will be
	 * fit into the window, thus if the image has a width of 1000 pixels
	 * and the window is 400px wide, the image will be scaled again.
	 */
	public Dimension getImageDimensions();
}
