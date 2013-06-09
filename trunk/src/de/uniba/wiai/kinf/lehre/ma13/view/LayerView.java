package de.uniba.wiai.kinf.lehre.ma13.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class LayerView extends JList<String> {
	
	private static final long serialVersionUID = 1L;
	private IAppDelegate _appDelegate;
	
	private boolean _lockrepaint = false;
	

	public LayerView(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;
	}
	
	public void repaint()
	{
		if(_lockrepaint)
			return;
		
		_lockrepaint = true;
		if(_appDelegate == null)
			return;
		
		
		List<String> layerNames = new LinkedList<String>();		
		for (ILayer layer: _appDelegate.getLayerStore().getAllLayers()) {
			
			layerNames.add(layer.getName());
			
			for(IGeometry geometry: layer.getGeometries()) {
				layerNames.add(geometry.getName());
			}
		}
		
		String[] layerNamesArray = new String[layerNames.size()];
		layerNamesArray = layerNames.toArray(layerNamesArray);
		
		setListData(layerNames.toArray(layerNamesArray));
		
		//System.out.println("JList printed");
		
		_lockrepaint = false;
		this.repaint(0);	
	}
}
