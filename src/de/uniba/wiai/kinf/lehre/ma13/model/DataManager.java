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
		Layer firstLayer = new Layer(_appDelegate.getId());
		firstLayer.setName("Layer 1");
		firstLayer.setVisibility(true);
		_appDelegate.getLayerStore().getAllLayers().add(firstLayer);
	}
}
