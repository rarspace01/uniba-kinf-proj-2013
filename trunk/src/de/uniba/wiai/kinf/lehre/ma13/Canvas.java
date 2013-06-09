package de.uniba.wiai.kinf.lehre.ma13;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import de.uniba.wiai.kinf.lehre.ma13.geometry.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.geometry.IGeometryStore;

public class Canvas 
	extends JComponent
	implements ICanvas
{
	private IGeometryStore _geomStore;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1014777371648522104L;
	
	@Override
	public void paint(Graphics g) {
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.white);
		for (IGeometry geom: getGeometryStore().getGeometries()) {
			geom.draw(this, g);
		}
	}

	@Override
	public void setGeometryStore(IGeometryStore geomStore) {
		_geomStore = geomStore;
	}

	@Override
	public IGeometryStore getGeometryStore() {
		return _geomStore;
	}
	

	@Override
	public Point2D toWorldCoordinates(Point pt) {
		// TODO Auto-generated method stub
		return new Point2D.Double(pt.getX(), pt.getY());
	}

	@Override	
	public Point toScreenCoordinates(Point2D pt) {
		return new Point(
				(int)Math.round(pt.getX()),
				(int)Math.round(pt.getY())
		);
	}
}
