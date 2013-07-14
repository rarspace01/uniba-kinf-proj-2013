package de.uniba.wiai.kinf.lehre.ma13.model;

import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class LayerStore implements ILayerStore
{
	private int activeLayer;
	private List<ILayer> allLayers_;
	private IBackgroundImage backgroundImage_;
	private IAppDelegate appDelegate_;
	
	public LayerStore(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
		
		allLayers_ = new LinkedList<ILayer>();
		backgroundImage_ = new BackgroundImage();
		backgroundImage_.setName("Structure");
		
		activeLayer = 0;
	}
	
	@Override
	public List<ILayer> getAllLayers() {
		return allLayers_;
	}

	@Override
	public List<ILayer> getVisibleLayers() {
		List<ILayer> visibleLayers = new LinkedList<ILayer>();
		for(ILayer lay: allLayers_)
		{
			if(lay.isVisible())
				visibleLayers.add(lay);
		}
		return visibleLayers;
	}

	@Override
	public List<ILayer> getLayersInBoundingBox(int x1, int x2, int y1, int y2) {
		List<ILayer> boundingBoxLayers = new LinkedList<ILayer>();
		for(ILayer lay: allLayers_)
		{
			if(lay.isVisible() && lay.getGeometriesInBoundingBox(x1, x2, y1, y2).size() > 0)
				boundingBoxLayers.add(lay);
		}
		return boundingBoxLayers;
	}

	@Override
	public IBackgroundImage getBackgroundImage() {
		return backgroundImage_;
	}
	
	@Override
	public ILayer getActiveLayer()
	{
		return allLayers_.get(activeLayer);
	}
	
	@Override
	public void setActiveLayer(ILayer active)
	{
		activeLayer = allLayers_.indexOf(active);
	}

	@Override
	public void setBackgroundImage(IBackgroundImage backgroundImage) {
		backgroundImage_ = backgroundImage;
	}

}
