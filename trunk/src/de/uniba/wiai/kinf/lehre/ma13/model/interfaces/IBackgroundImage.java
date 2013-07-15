package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.awt.Dimension;
import java.awt.Image;

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
	
	/**
	 * sets the dimension of the BG Image
	 * @param dimension
	 */
	public void setImageDimensions(Dimension dimension);
	
	/**
	 * gets the current Image
	 * @return Image
	 */
	public Image getImage();
	
	/**
	 * gets the data of the Image
	 * @return byte[]
	 */
	public byte[] getData();
	
	/**
	 * sets the data of the Image
	 * @param imageData
	 */
	public void setData(byte[] imageData);
	
}
