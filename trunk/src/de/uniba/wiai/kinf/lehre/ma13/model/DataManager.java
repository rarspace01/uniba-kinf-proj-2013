package de.uniba.wiai.kinf.lehre.ma13.model;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public class DataManager
{
	private IAppDelegate appDelegate_;
	
	public DataManager(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
	}
	
	public void loadTestData()
	{
		Layer firstLayer = new Layer(appDelegate_.getId());
		firstLayer.setName("Layer 1");
		firstLayer.setVisibility(true);
		appDelegate_.getLayerStore().getAllLayers().add(firstLayer);
		
		//appDelegate_.getLayerStore().getBackgroundImage().setImagePath("res/default_image.jpg");
		
	}
}
