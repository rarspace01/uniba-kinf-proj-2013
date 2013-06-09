package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class DrawPolygonUnfinished extends DrawGeometry {
	
	@Override
	public void draw(Graphics g, IGeometry geometry)
	{
		Polygon polygon = (Polygon)geometry;
		
		g.setColor(Color.black);
		
		int i = 1;
		for(i = 1; i < polygon.getPoints().size(); i++)
		{
			Point2D srcWorld = polygon.getPoints().get(i - 1);
			Point2D tgtWorld = polygon.getPoints().get(i);
			
			g.drawLine((int)srcWorld.getX(), (int)srcWorld.getY(), (int)tgtWorld.getX(), (int)tgtWorld.getY());
		}
		/*Point2D srcWorld = polygon.getPoints().get(i-1);
		Point2D tgtWorld = polygon.getPoints().get(0);
		g.drawLine((int)srcWorld.getX(), (int)srcWorld.getY(), (int)tgtWorld.getX(), (int)tgtWorld.getY());*/
	}
}
