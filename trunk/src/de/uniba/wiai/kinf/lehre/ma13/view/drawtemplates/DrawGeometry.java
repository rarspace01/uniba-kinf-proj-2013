package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Graphics;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

/**
 * 
 * @author lukas
 * 
 */
public abstract class DrawGeometry
{
	protected IAppDelegate appDelegate_;
	
	public DrawGeometry(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
	}
	
	public abstract void draw(Graphics g, IGeometry geometry);
}
