package de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

public class DrawPolygonRaw extends DrawGeometry {
	
	public DrawPolygonRaw(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void draw(Graphics g, IGeometry geometry)
	{
		Polygon polygon = (Polygon)geometry;

		// default color settings
		((Graphics2D)g).setStroke(new BasicStroke(3));
		g.setColor(new Color(
				geometry.getColor().getRed(),
				geometry.getColor().getGreen(),
				geometry.getColor().getBlue(),
				Math.round(255 * geometry.getOpacity())));
		
		// special treatment for selected geometries
		int listSelection = appDelegate_.getWindow().getLayerView().getMaxSelectionIndex();
		if(listSelection >= 0)
		{
			IOrderedObject selectedObject = appDelegate_.getWindow().getLayerView().getModel().getElementAt(
						appDelegate_.getWindow().getLayerView().getMaxSelectionIndex()
					).getObject();
			
			if(selectedObject instanceof IGeometry && geometry.equals(selectedObject))
			{
				((Graphics2D)g).setStroke(new BasicStroke(6));
				g.setColor(new Color(
						geometry.getColor().getRed(),
						geometry.getColor().getGreen(),
						geometry.getColor().getBlue(),
						Math.round(255 * geometry.getOpacity())));	
			}
		}
		
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
