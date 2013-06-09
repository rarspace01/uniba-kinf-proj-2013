package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Graphics;

import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public abstract class DrawGeometry
{	
	public abstract void draw(Graphics g, IGeometry geometry);
}
