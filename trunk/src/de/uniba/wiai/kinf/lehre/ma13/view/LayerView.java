package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.DummyMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;

public class LayerView extends JList<LayerViewListItem> {
	
	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private boolean lockrepaint_ = false;
	private MouseInputAdapter mouseListener_ = null;
	

	public LayerView(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
		
		mouseListener_ = new MouseInputAdapter() {
			
			private IOrderedObject draggedObject_;
			
			/**
			 * click at buttons behind the JList UI (not accessible otherwise)
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getX() >= 5 && arg0.getX() <= 55)
				{
					LayerViewListItem item = getModel().getElementAt(locationToIndex(arg0.getPoint()));
					item.getObject().setVisibility(!item.getObject().isVisible());
					appDelegate_.getWindow().refresh();
				}
				else if(arg0.getX() >= 85 && arg0.getClickCount() >= 2)
				{
					LayerViewListItem item = getModel().getElementAt(locationToIndex(arg0.getPoint()));
					String newObjectName = JOptionPane.showInputDialog("Rename the object:");
					item.getObject().setName(newObjectName);
					appDelegate_.getWindow().refresh();
				}
			}
			
			/**
			 * enable drag and drop
			 */
			@Override
			public void mousePressed(MouseEvent arg0) {
				LayerViewListItem item = getModel().getElementAt(locationToIndex(arg0.getPoint()));
				
				// do not drag the background image layer
				if(!(item.getObject() instanceof IBackgroundImage))
				{
					draggedObject_ = item.getObject();
				}
		    }
			
			
			@Override
		    public void mouseReleased(MouseEvent arg0) {
								
				LayerViewListItem item = getModel().getElementAt(locationToIndex(arg0.getPoint()));
				
				// do not drag the background image layer
				if(!(item.getObject() instanceof IBackgroundImage))
				{
					IOrderedObject insertObject = item.getObject();
					// the target is a layer
					if(insertObject instanceof ILayer)
					{
						// the dragged object is a layer - insert dragged object after the layer where it is dropped
						if(draggedObject_ instanceof ILayer)
						{
							// do nothing if dragged object equals dropped object
							if(!draggedObject_.equals(insertObject))
							{
								appDelegate_.getLayerStore().getAllLayers().remove(draggedObject_);
								int insertIndex = appDelegate_.getLayerStore().getAllLayers().indexOf(insertObject);
								appDelegate_.getLayerStore().getAllLayers().add(insertIndex+1, (ILayer)draggedObject_);
								appDelegate_.getWindow().refresh();
							}
						}
						// the dragged object is a geometry, insert dragged object in layer where it is dropped
						else if(draggedObject_ instanceof IGeometry)
						{
							// do nothing if layer equals parent
							if(!((IGeometry)draggedObject_).getParent().equals(insertObject))
							{
								((IGeometry)draggedObject_).getParent().getGeometries().remove(draggedObject_);
								((ILayer) insertObject).getGeometries().add((IGeometry) draggedObject_);
								((IGeometry)draggedObject_).setParent((ILayer) insertObject);
								appDelegate_.getWindow().refresh();
							}
						}
					}
					else
					{
						draggedObject_ = null;
					}
				}
		    }
		};
		this.addMouseListener(mouseListener_);
		this.addMouseMotionListener(mouseListener_);
				
		this.addListSelectionListener(new ListSelectionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(!e.getValueIsAdjusting())
				{
					appDelegate_.setMouseAction(new DummyMouseAction(appDelegate_));
					int newIndex = ((JList<LayerViewListItem>)e.getSource()).getMaxSelectionIndex();
					if(newIndex < 0)
					{
						System.out.println("Index invalid");
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is disabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(false);
						// "stop action" is disabled
						appDelegate_.getWindow().getToolBar().getComponent(4).setEnabled(false);
						return;
					}
					
					LayerViewListItem item = getModel().getElementAt(newIndex);
					
					if(item.getObject() instanceof IBackgroundImage)
					{
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is disabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(false);
						// "stop action" is disabled
						appDelegate_.getWindow().getToolBar().getComponent(4).setEnabled(false);
					}
					else if(item.getObject() instanceof ILayer)
					{
						appDelegate_.getLayerStore().setActiveLayer((ILayer)item.getObject());
						System.out.println("Layer selected");
						// moving polygon is disabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(false);
						// deleting object is enabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(true);
						// "stop action" is disabled
						appDelegate_.getWindow().getToolBar().getComponent(4).setEnabled(false);
					}
					else if(item.getObject() instanceof IGeometry)
					{
						System.out.println("Geometry selected");
						// moving polygon is enabled
						appDelegate_.getWindow().getToolBar().getComponent(2).setEnabled(true);
						// deleting object is enabled
						appDelegate_.getWindow().getToolBar().getComponent(3).setEnabled(true);
						// "stop action" is disabled
						appDelegate_.getWindow().getToolBar().getComponent(4).setEnabled(false);
					}
					
					// polygon selected, refresh canvas
					appDelegate_.getWindow().getCanvas().repaint();
				}
			}
		});
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    //this.setDragEnabled(true);
	    //this.setDropMode(DropMode.INSERT);
	    //this.setTransferHandler(new ListMoveTransferHandler());
	}
	
	public void repaint()
	{
		if(lockrepaint_)
			return;
		
		lockrepaint_ = true;
		if(appDelegate_ == null)
			return;
		
		DefaultListModel<LayerViewListItem> listModel = new DefaultListModel<LayerViewListItem>();	
		for (ILayer layer: appDelegate_.getLayerStore().getAllLayers()) {
			
			
			listModel.addElement(new LayerViewListItem(layer));
			
			
			for(IGeometry geometry: layer.getGeometries()) {
				listModel.addElement(new LayerViewListItem(geometry));
			}
		}
		
		listModel.addElement(new LayerViewListItem(appDelegate_.getLayerStore().getBackgroundImage()));
		
		setModel(listModel);
		setCellRenderer(new LayerViewCellRenderer(appDelegate_));
		
		this.setFixedCellWidth(230);
		
		lockrepaint_ = false;
		super.repaint();
		
		
	}
}
