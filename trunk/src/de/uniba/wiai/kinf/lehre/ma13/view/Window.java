package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.CreatePolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.DummyMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.FreeHandPolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MovePolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IGeometry;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.IOrderedObject;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonRaw;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.ICanvas;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

public class Window extends JFrame implements IWindow
{
	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private ICanvas canvas_;
	private JToolBar toolBar_;
	private LayerView layerView_;
	private JScrollPane scrollPaneLayer_;
	
	public Window(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;

		/*
		 * default initialisation
		 */
		// end application when closing the window
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("iRestore");
		// set border layout
		setLayout(new BorderLayout(5, 5));

		
		/*
		 * initialise the canvas
		 */
		// initialise canvas area
		canvas_ = new Canvas(appDelegate_);
		canvas_.setGeometryDrawer(new DrawPolygonRaw(appDelegate_));
		canvas_.setBackgroundDrawer(new DrawBackgroundImage(appDelegate_));
		add((JComponent)canvas_, BorderLayout.CENTER);		
		
		/*
		 * JMenuBars
		 */
		JMenuBar menuBar = new JMenuBar();
		
		// file
		JMenu file = new JMenu("File");
		JMenuItem fileOpen = new JMenuItem("Open");
		file.add(fileOpen);
		JMenuItem fileSave = new JMenuItem("Save");
		file.add(fileSave);
		JMenuItem fileExit = new JMenuItem("Exit");
		file.add(fileExit);
		menuBar.add(file);

		JMenu help = new JMenu("Help");
		JMenuItem helpHello = new JMenuItem("Hello 1337!");
		help.add(helpHello);
		menuBar.add(help);
		
		add(menuBar, BorderLayout.NORTH);		

		/*
		 * new JPanel for right side of the window
		 */
		JPanel nestedLayout = new JPanel();
		nestedLayout.setLayout(new BoxLayout(nestedLayout, BoxLayout.Y_AXIS));
		
		
		/*
		 * initialise the toolbar
		 */
		createToolBar();
		
		/*
		 * layer view, showing the different layers
		 */
		layerView_ = new LayerView(appDelegate_);
		
		scrollPaneLayer_ = new JScrollPane(layerView_, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		

		JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 50, 250, 100);
		zoomSlider.setMinorTickSpacing(10);
		zoomSlider.setMajorTickSpacing(50);
		zoomSlider.setPaintLabels(true);
		zoomSlider.setPaintTicks(true);
		zoomSlider.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			    	//System.out.println("Slider Value: " + source.getValue());
			    	
			    	float currentValue = (float)source.getValue();
			    	appDelegate_.getUtil().setZoom(currentValue / 100);
			    	refresh();
			    }
			}
		});
		
		nestedLayout.add(toolBar_);
		nestedLayout.add(scrollPaneLayer_);
		nestedLayout.add(zoomSlider);
		
		add(nestedLayout, BorderLayout.LINE_END);
		
		setSize(750, 550);
		setVisible(true);
	}
	

	@Override
	public void refresh() {
		canvas_.repaint();
		layerView_.repaint();
	}

	/**
	 * returns the canvas
	 */
	public ICanvas getCanvas()
	{
		return canvas_;
	}
	
	public JToolBar getToolBar()
	{
		return toolBar_;
	}
	
	public LayerView getLayerView()
	{
		return layerView_;
	}
	
	private void createToolBar()
	{
		toolBar_ = new JToolBar(JToolBar.HORIZONTAL);

		// button for polygon, make lines between points
		JButton tbPolygonButton = new JButton();
		tbPolygonButton.setIcon(new ImageIcon("res/polygon.png"));
		tbPolygonButton.setToolTipText("Polygon");
		tbPolygonButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	getToolBar().getComponent(4).setEnabled(true);
	            appDelegate_.setMouseAction(new CreatePolygonMouseAction(appDelegate_));
	        }
	    });
		toolBar_.add(tbPolygonButton);
		
		// button for free hand drawings (many implicit points in a polygon)
		JButton tbFreeHandButton = new JButton();
		tbFreeHandButton.setIcon(new ImageIcon("res/freehand.png"));
		tbFreeHandButton.setToolTipText("Free Hand");
		tbFreeHandButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	getToolBar().getComponent(4).setEnabled(true);
	            appDelegate_.setMouseAction(new FreeHandPolygonMouseAction(appDelegate_));
	        }
	    });
		toolBar_.add(tbFreeHandButton);
		
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbMoveButton = new JButton();
		tbMoveButton.setIcon(new ImageIcon("res/movepolygon.png"));
		tbMoveButton.setToolTipText("Move Polygon");
		tbMoveButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	            appDelegate_.setMouseAction(new MovePolygonMouseAction(appDelegate_));
	        }
	    });
		tbMoveButton.setEnabled(false);
		toolBar_.add(tbMoveButton);
		
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbDeleteObject = new JButton();
		tbDeleteObject.setIcon(new ImageIcon("res/delete.png"));
		tbDeleteObject.setToolTipText("Delete Object");
		tbDeleteObject.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	IOrderedObject selectedObject = appDelegate_.getWindow().getLayerView().getModel().getElementAt(
						appDelegate_.getWindow().getLayerView().getMaxSelectionIndex()
					).getObject();
	        	
	        	if(selectedObject instanceof ILayer && appDelegate_.getLayerStore().getAllLayers().size() > 1)
	        	{
	        		appDelegate_.getLayerStore().getAllLayers().remove(selectedObject);
	        	}
	        	else if(selectedObject instanceof IGeometry)
	        	{
	        		((IGeometry)selectedObject).getParent().getGeometries().remove(selectedObject);
	        	}
	        	refresh();
	        }
	    });
		tbDeleteObject.setEnabled(false);
		toolBar_.add(tbDeleteObject);
		
		// button to stop current action
		JButton tbStopactionButton = new JButton();
		tbStopactionButton.setIcon(new ImageIcon("res/stop.png"));
		tbStopactionButton.setToolTipText("Stop Action");
		tbStopactionButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
    			appDelegate_.getWindow().getCanvas().clearTempGeometries();
    			appDelegate_.setMouseAction(new DummyMouseAction(appDelegate_));
    			canvas_.repaint();
    			getToolBar().getComponent(4).setEnabled(false);
            }
        });
		tbStopactionButton.setEnabled(false);
		toolBar_.add(tbStopactionButton);

		// button to create a new layer
		JButton tbCreatelayerButton = new JButton();
		tbCreatelayerButton.setIcon(new ImageIcon("res/addlayer.png"));
		tbCreatelayerButton.setToolTipText("Add Layer");
		tbCreatelayerButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
            	ILayer newLayer = new Layer(appDelegate_.getId());
            	newLayer.setName("Layer " + (appDelegate_.getLayerStore().getAllLayers().size()+1));
            	newLayer.setVisibility(true);
                appDelegate_.getLayerStore().getAllLayers().add(newLayer);
                layerView_.repaint();
            }
        });
		toolBar_.add(tbCreatelayerButton);
		
		toolBar_.setFloatable(false);
	}
}
