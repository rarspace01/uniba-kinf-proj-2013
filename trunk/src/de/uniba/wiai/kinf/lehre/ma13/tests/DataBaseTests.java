package de.uniba.wiai.kinf.lehre.ma13.tests;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

import de.uniba.wiai.kinf.lehre.ma13.controller.AppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.data.DataManager;
import de.uniba.wiai.kinf.lehre.ma13.data.interfaces.IDataManager;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.Polygon;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

/**
 * just some basic save & load test for the DB
 * @author denis
 *
 */
public class DataBaseTests {

	@Test
	public void testSave() {

		String filename = "testSave.sqlite";

		// setupDB

		// create layerstore
		ILayerStore ls = new LayerStore(null);

		ILayer layer = new Layer(1L);
		layer.setName("Layer1");
		layer.setVisibility(true);
		layer.setColor(Color.CYAN);

		IGeometry polygon1 = new Polygon(1L);
		IGeometry polygon2 = new Polygon(2L);

		for (int i = 0; i < 10; i++) {
			((Polygon) polygon1).getPoints().add(new Point(i, (10 - i)));
			((Polygon) polygon2).getPoints().add(new Point(i, (10 - i)));
		}

		polygon1.setColor(Color.GREEN);
		polygon2.setColor(Color.BLUE);

		polygon1.setParent(layer);
		polygon2.setParent(layer);

		layer.getGeometries().add(polygon1);
		layer.getGeometries().add(polygon2);

		ls.getAllLayers().add(layer);

		ls.getBackgroundImage().setImagePath("C:/Users/denis/Pictures/Kirche-Gaustadt-03.JPG");
		

		IDataManager dm = new DataManager(null);

		dm.save(ls, filename);

	}

	@Test
	public void testLoad() {
		IAppDelegate appD=new AppDelegate();
		appD.init();

		IDataManager dm = new DataManager(appD);
		String filename = "testSave.sqlite";

		
		ILayerStore ls = new LayerStore(appD);

	    try{
	        dm.load(ls,filename);
	    }catch (Error e){
	    	e.printStackTrace();
	    }
		

		for (int i = 0; i < ls.getAllLayers().size(); i++) {
			System.out.println("Layer: [" + ls.getAllLayers().get(i).getName()
					+ "]");
			System.out.println("Color: "
					+ ls.getAllLayers().get(i).getColor().toString());

			System.out.println("Number of Geometrys on Layer: "
					+ ls.getAllLayers().get(i).getGeometries().size());

			for (int j = 0; j < ls.getAllLayers().get(i).getGeometries().size(); j++) {
				System.out.println("Polyid: "
						+ ls.getAllLayers().get(i).getGeometries().get(j)
								.getObjectId()
						+ " - Color: "
						+ ls.getAllLayers().get(i).getGeometries().get(j)
								.getColor().toString()+" Polygon has Points: "+((Polygon) ls.getAllLayers().get(i).getGeometries().get(j)).getPoints().size());
			}

		}

		System.out.println("Image path: ["
				+ ls.getBackgroundImage().getImagePath() + "]");

	}

}
