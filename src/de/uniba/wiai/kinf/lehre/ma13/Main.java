package de.uniba.wiai.kinf.lehre.ma13;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.uniba.wiai.kinf.lehre.ma13.geometry.Bild;
import de.uniba.wiai.kinf.lehre.ma13.geometry.GeometryStore;
import de.uniba.wiai.kinf.lehre.ma13.geometry.IGeometryStore;
import de.uniba.wiai.kinf.lehre.ma13.geometry.Polygon;

public class Main {
	public static void main(String[] args) throws IOException {
		JFrame frame = new JFrame();
		frame.setSize(400, 400);
		
		IGeometryStore geomStore = new GeometryStore();
		
		Polygon p = new Polygon();
		p.addPoint(new Point2D.Double(100, 100));
		p.addPoint(new Point2D.Double(100, 200));
		p.addPoint(new Point2D.Double(200, 200));
		p.addPoint(new Point2D.Double(200, 100));
		geomStore.add(p);
		
		//BufferedImage bi = ImageIO.read(new File("bild.png"));
		//Bild b = new Bild(bi);
		//geomStore.add(b);
		
		Canvas c = new Canvas();
		c.setGeometryStore(geomStore);
		
		frame.add(c);
		
		frame.setVisible(true);

	}
}
