package de.uniba.wiai.kinf.lehre.ma13.controller;

import java.awt.Dimension;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public class Util
{
	private IAppDelegate appDelegate_;
	
	/** last ID that was allocated */
	private Long lastId_;
	
	private float zoomFactor_;
	private Point zoomOffset_;
	
	private Util(IAppDelegate appDelegate)
	{
		lastId_ = 1337L;
		appDelegate_ = appDelegate;
		
		zoomFactor_ = 1.0f;
		zoomOffset_ = new Point(0, 0);
	}
	
	public static Util instance(IAppDelegate appDelegate)
	{
		return new Util(appDelegate);
	}
	
	public Long getId()
	{
		return lastId_++;
	}
	
	public void setId(Long lastId)
	{
		lastId_ = lastId;
	}
	
	public Point toWorldCoordinates(Point pt) {

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();

		float ratio = Math.min(
				((float)canvasDimension.width / (float)originalDimension.width),
				((float)canvasDimension.height / (float)originalDimension.height)
				);
		
		float pointX = 0.0f;
		float pointY = 0.0f;
		
		// TODO: there can be an additional offset
		float offsetX = (((float)originalDimension.width * getZoom() * ratio) - (float)canvasDimension.width) / 2.0f;
		float offsetY = (((float)originalDimension.height * getZoom() * ratio) - (float)canvasDimension.height) / 2.0f;
			
		pointX = (pt.x + offsetX) / (ratio * getZoom());
		pointY = (pt.y + offsetY) / (ratio * getZoom());
		
		return new Point(Math.round(pointX), Math.round(pointY));
		
		//Dimension worldDimension = toWorldDimension(new Dimension(pt.x, pt.y));
		//return new Point(worldDimension.width, worldDimension.height);
	}

	public Point toScreenCoordinates(Point pt) {

		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();

		float ratio = Math.min(
				((float)canvasDimension.width / (float)originalDimension.width),
				((float)canvasDimension.height / (float)originalDimension.height)
				);
		
		float pointX = 0.0f;
		float pointY = 0.0f;
		
		// TODO: there can be an additional offset
		float offsetX = (((float)originalDimension.width * getZoom() * ratio) - (float)canvasDimension.width) / 2.0f;
		float offsetY = (((float)originalDimension.height * getZoom() * ratio) - (float)canvasDimension.height) / 2.0f;
			
		pointX = pt.x * (ratio * getZoom()) - offsetX;
		pointY = pt.y * (ratio * getZoom()) - offsetY;
		
		return new Point(Math.round(pointX), Math.round(pointY));
		
		//Dimension screenDimension = toScreenDimension(new Dimension(pt.x, pt.y));
		//return new Point(screenDimension.width, screenDimension.height);
	}
	
	public Dimension toWorldDimension(Dimension screen) {
		
		/*// TODO: zoom factor!
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();
		
		// get aspect ratio of image
		float aspectRatio = (float)originalDimension.width / (float)originalDimension.height;
		
		int worldWidth = Math.round((float)screen.width / ((float)canvasDimension.width / (float)originalDimension.width));
		//int worldHeight = Math.round((float)screen.height / ((float)canvasDimension.height / (float)originalDimension.height));
		int worldHeight = Math.round((float)worldWidth / aspectRatio);
		
		return new Dimension(worldWidth, worldHeight);*/
		
		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();
		// get aspect ratio of image
		float aspectRatio = (float)originalDimension.width / (float)originalDimension.height;

		float worldWidth = ((float)screen.width / ((float)canvasDimension.width / (float)originalDimension.width)) * zoomFactor_;
		float worldHeight = worldWidth / aspectRatio;
		
		return new Dimension(Math.round(worldWidth), Math.round(worldHeight));
	}
	
	/**
	 * translates a dimension from the "real world" to the screen.
	 * The image has an original dimension which is translated to the screen here
	 */
	public Dimension toScreenDimension(Dimension world) {
		
		// dimension of the background image
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		// dimensions of the canvas
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();

		float ratio = Math.min(
				((float)canvasDimension.width / (float)originalDimension.width),
				((float)canvasDimension.height / (float)originalDimension.height)
				);
		
		float screenWidth = ((float)world.width * ratio) * getZoom();
		float screenHeight = ((float)world.height * ratio) * getZoom();
		
		return new Dimension(Math.round(screenWidth), Math.round(screenHeight));
	}
	
	public float getZoom()
	{
		return zoomFactor_;
	}
	
	public void setZoom(float zoomFactor)
	{
		zoomFactor_ = zoomFactor;
	}
}
