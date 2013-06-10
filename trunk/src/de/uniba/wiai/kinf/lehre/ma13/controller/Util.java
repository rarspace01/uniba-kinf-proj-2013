package de.uniba.wiai.kinf.lehre.ma13.controller;

import java.awt.Dimension;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public class Util
{
	private IAppDelegate appDelegate_;
	
	/** last ID that was allocated */
	private Long lastId_;
	
	private Util(IAppDelegate appDelegate)
	{
		lastId_ = 1337L;
		appDelegate_ = appDelegate;
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
		// TODO: offset!
		Dimension worldDimension = toWorldDimension(new Dimension(pt.x, pt.y));
		
		System.out.println("Converting " + pt + " to " + worldDimension);
		
		return new Point(worldDimension.width, worldDimension.height);
	}

	public Point toScreenCoordinates(Point pt) {
		// TODO: offset!
		Dimension screenDimension = toScreenDimension(new Dimension(pt.x, pt.y));
		return new Point(screenDimension.width, screenDimension.height);
	}
	
	public Dimension toWorldDimension(Dimension screen) {
		
		// TODO: zoom factor!
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();
		
		int worldWidth = Math.round((float)screen.width / ((float)canvasDimension.width / (float)originalDimension.width));
		int worldHeight = Math.round((float)screen.height / ((float)canvasDimension.height / (float)originalDimension.height));
		
		return new Dimension(worldWidth, worldHeight);
	}
	
	public Dimension toScreenDimension(Dimension world) {

		// TODO: zoom factor!
		Dimension originalDimension = appDelegate_.getLayerStore().getBackgroundImage().getImageDimensions();
		Dimension canvasDimension = appDelegate_.getWindow().getCanvas().getCanvasClippingBounds().getSize();
		
		int screenWidth = Math.round((float)world.width * ((float)canvasDimension.width / (float)originalDimension.width));
		int screenHeight = Math.round((float)world.height * ((float)canvasDimension.height / (float)originalDimension.height));
		
		return new Dimension(screenWidth, screenHeight);
	}
}
