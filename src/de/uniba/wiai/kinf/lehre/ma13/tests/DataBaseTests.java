package de.uniba.wiai.kinf.lehre.ma13.tests;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

import de.uniba.wiai.kinf.lehre.ma13.data.PersistanceManager;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IPersistanceManager;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class DataBaseTests {

	@Test
	public void testSave() {
		
		String filename="testSave.sqlite";
		
		//setupDB
		
		
		
		//create layerstore
		ILayerStore ls=new LayerStore(null);
		
		ILayer layer = new Layer(1L);
		layer.setName("Layer1");
		layer.setVisibility(true);
		layer.setColor(Color.CYAN);
		
		IGeometry polygon1 = new Polygon(1L);
		IGeometry polygon2 = new Polygon(2L);
		
		for(int i=0;i<10;i++){
			((Polygon) polygon1).getPoints().add(new Point(i, (10-i)));
			((Polygon) polygon2).getPoints().add(new Point(i, (10-i)));
		}
		
		
		polygon1.setColor(Color.GREEN);
		polygon2.setColor(Color.BLUE);
		
		polygon1.setParent(layer);
		polygon2.setParent(layer);
		
		layer.getGeometries().add(polygon1);
		layer.getGeometries().add(polygon2);
		
		ls.getAllLayers().add(layer);
		
		ls.getBackgroundImage().setImagePath("res/default_image.jpg");
		
		
		IPersistanceManager pm=new PersistanceManager(null);
		
		pm.save(ls, filename);		
		
	}
	
	@Test
	public void testLoad() {
		
		IPersistanceManager pm=new PersistanceManager(null);
		String filename="testSave.sqlite";
		
		ILayerStore ls=new LayerStore(null);
		
		ls=pm.load(filename);
		
		for(int i=0; i<ls.getAllLayers().size(); i++){
			System.out.println("Layer: ["+ls.getAllLayers().get(i).getName()+"]");
			System.out.println("Color: "+ls.getAllLayers().get(i).getColor().toString());
		}
		
		System.out.println("Image path: ["+ls.getBackgroundImage().getImagePath()+"]");
		
	}
	

}
