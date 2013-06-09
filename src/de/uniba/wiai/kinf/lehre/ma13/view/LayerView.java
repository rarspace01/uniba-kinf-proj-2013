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
		//List<LayerViewListItem> layerNames = new LinkedList<LayerViewListItem>();		
		for (ILayer layer: _appDelegate.getLayerStore().getAllLayers()) {
			
			
			listModel.addElement(new LayerViewListItem("layer.png", layer.getName()));
			
			
			for(IGeometry geometry: layer.getGeometries()) {
				listModel.addElement(new LayerViewListItem("polygon.png", geometry.getName()));
			}
		}
		
		//String[] layerNamesArray = new String[layerNames.size()];
		//layerNamesArray = layerNames.toArray(layerNamesArray);
		
		
		//setListData(layerNames.toArray(layerNamesArray));
		setModel(listModel);
		setCellRenderer(new LayerViewCellRenderer());
		
		//System.out.println("JList printed");
		
		this.setFixedCellWidth(230);
		
		_lockrepaint = false;
		super.repaint();
	}
}
