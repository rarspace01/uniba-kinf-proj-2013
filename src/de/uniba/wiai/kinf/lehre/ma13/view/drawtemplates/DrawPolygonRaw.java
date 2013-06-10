package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;

public class DrawPolygonRaw extends DrawGeometry {
	
	public DrawPolygonRaw(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void draw(Graphics g, IGeometry geometry)
	{
		Polygon polygon = (Polygon)geometry;
		
		g.setColor(new Color(
				geometry.getColor().getRed(),
				geometry.getColor().getGreen(),
				geometry.getColor().getBlue(),
				Math.round(255 * geometry.getOpacity())));
		
		int i = 1;
		for(i = 1; i <= polygon.getPoints().size(); i++)
		{
			Point srcWorld = polygon.getPoints().get(i - 1);
			Point tgtWorld = polygon.getPoints().get( (i % polygon.getPoints().size()) );

			Point srcScreen = appDelegate_.getUtil().toScreenCoordinates(srcWorld);
			Point tgtScreen = appDelegate_.getUtil().toScreenCoordinates(tgtWorld);

			g.drawLine(srcScreen.x, srcScreen.y, tgtScreen.x, tgtScreen.y);
		}
	}
}
