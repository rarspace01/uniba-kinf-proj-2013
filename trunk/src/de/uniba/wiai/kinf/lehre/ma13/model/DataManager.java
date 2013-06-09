package de.uniba.wiai.kinf.lehre.ma13.model;

import java.awt.geom.Point2D;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public class DataManager
{
	private IAppDelegate _appDelegate;
	
	public DataManager(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;
	}
	
	public void loadTestData()
	{
		/*
		Polygon firstPolygon = new Polygon(_appDelegate.getId());
		firstPolygon.setName("Test Polygon");
		firstPolygon.getPoints().add(new Point2D.Double(100, 100));
		firstPolygon.getPoints().add(new Point2D.Double(100, 200));
		firstPolygon.getPoints().add(new Point2D.Double(200, 200));
		firstPolygon.getPoints().add(new Point2D.Double(200, 100));
		*/
		
		Layer firstLayer = new Layer(_appDelegate.getId());
		firstLayer.setName("Test Layer");
		//firstLayer.getGeometries().add(firstPolygon);
		_appDelegate.getLayerStore().getAllLayers().add(firstLayer);
	}
}
