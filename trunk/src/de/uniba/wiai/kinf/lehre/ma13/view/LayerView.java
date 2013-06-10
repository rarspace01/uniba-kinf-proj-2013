package de.uniba.wiai.kinf.lehre.ma13.view;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;

public class LayerView extends JList<LayerViewListItem> {
	
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
		
		DefaultListModel<LayerViewListItem> listModel = new DefaultListModel<LayerViewListItem>();	
		for (ILayer layer: _appDelegate.getLayerStore().getAllLayers()) {
			
			
			listModel.addElement(new LayerViewListItem("res/layer.png", layer.getName()));
			
			
			for(IGeometry geometry: layer.getGeometries()) {
				listModel.addElement(new LayerViewListItem("res/polygon.png", geometry.getName()));
			}
		}
		
		setModel(listModel);
		setCellRenderer(new LayerViewCellRenderer());
		
		this.setFixedCellWidth(230);
		
		_lockrepaint = false;
		super.repaint();
	}
}
