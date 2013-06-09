package de.uniba.wiai.kinf.lehre.ma13.geometry;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.ICanvas;

public class Polygon implements IGeometry {
	private final List<Point2D> _points = new ArrayList<>();

	public List<Point2D> getPoints() {
		return _points;
	}

	public void addPoint(Point2D pt) {
		_points.add(pt);
	}

	@Override
	public void draw(ICanvas canvas, Graphics g) {
		if (_points.size() > 1) {
			for (int i = 1; i < _points.size(); ++i) {
				Point2D srcWorld = _points.get(i - 1);
				Point2D tgtWorld = _points.get(i);
				Point src = canvas.toScreenCoordinates(srcWorld);
				Point tgt = canvas.toScreenCoordinates(tgtWorld);
				g.drawLine(src.x, src.y, tgt.x, tgt.y);
			}
		}
	}
}
