package de.uniba.wiai.kinf.lehre.ma13.model;

import java.util.LinkedList;
import java.util.List;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;

public class LayerStore implements ILayerStore
{
	private List<ILayer> _allLayers;
	private IAppDelegate _appDelegate;
	
	public LayerStore(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;
		_allLayers = new LinkedList<ILayer>();
	}
	
	@Override
	public List<ILayer> getAllLayers() {
		return _allLayers;
	}

	@Override
	public List<ILayer> getVisibleLayers() {
		List<ILayer> visibleLayers = new LinkedList<ILayer>();
		for(ILayer lay: _allLayers)
		{
			if(lay.isVisible())
				visibleLayers.add(lay);
		}
		return visibleLayers;
	}

	@Override
	public List<ILayer> getLayersInBoundingBox(float x1, float x2, float y1, float y2) {
		List<ILayer> boundingBoxLayers = new LinkedList<ILayer>();
		for(ILayer lay: _allLayers)
		{
			if(lay.isVisible() && lay.getGeometriesInBoundingBox(x1, x2, y1, y2).size() > 0)
				boundingBoxLayers.add(lay);
		}
		return boundingBoxLayers;
	}

}
