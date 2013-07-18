package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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
import de.uniba.wiai.kinf.lehre.ma13.data.DataManager;
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
	private JSlider opacitySlider_;
	private LayerView layerView_;
	private JScrollPane scrollPaneLayer_;
	private JMenu file;
	private JMenuItem fileOpen;
	private JMenuItem fileSave;
	private JMenuItem fileExit;
	private JMenuBar menuBar;
	private JMenu help;
	private JMenuItem helpOnline;
	private JPanel nestedLayout;
	private JMenuItem loadImage;
	
	public static final int BUTTON_CREATE_POLYGON = 0;
	public static final int BUTTON_CREATE_FREEHAND = 1;
	public static final int BUTTON_MOVE_POLYGON = 2;
	public static final int BUTTON_COLOR = 3;
	public static final int BUTTON_DELETE = 4;
	public static final int BUTTON_STOP_ACTION = 5;
	public static final int BUTTON_ADD_LAYER = 6;
	
	/**
	 * 
	 * @author lukas
	 * 
	 */
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
		menuBar = new JMenuBar();
		
		// file
		file = new JMenu("File");
		fileOpen = new JMenuItem("Open File...");
		
		fileOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

                String sFilename = "";
                JFileChooser fileChooser = new JFileChooser();

                // setting *.pdf filter for save dialog
                FileFilters filter = new FileFilters();
                filter.addExtension("sqlite");
                filter.setDescription("sqlite - Database");
                fileChooser.setFileFilter(filter);

                fileChooser.setSelectedFile(new File(sFilename));
                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        
                        DataManager pm=new DataManager(appDelegate_);
                        
                        System.out.println("opening file: ["+fileChooser.getSelectedFile().getPath().toLowerCase()+"]");
                        
                        pm.load(appDelegate_.getLayerStore(),fileChooser.getSelectedFile().getPath().toLowerCase());
                        
                        refresh();
                }
				
			}
		});
		
		file.add(fileOpen);
		fileSave = new JMenuItem("Save");
		
		fileSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//open File Save Dialog
				
                String sFilename = "";
                JFileChooser fileChooser = new JFileChooser();

                // setting *.pdf filter for save dialog
                FileFilters filterSQL = new FileFilters();
                filterSQL.addExtension("sqlite");
                filterSQL.setDescription("sqlite - Database");
                fileChooser.setFileFilter(filterSQL);

                fileChooser.setSelectedFile(new File(sFilename));
                if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        // if the user hasn't typed .pdf, we'll do it for him
                        if (!fileChooser.getSelectedFile().getPath().toLowerCase()
                                        .endsWith(".sqlite")) {
                                fileChooser.setSelectedFile(new File(fileChooser
                                                .getSelectedFile() + ".sqlite"));
                        }
                        
                        DataManager pm=new DataManager(appDelegate_);
                        
                        System.out.println("saving file to: ["+fileChooser.getSelectedFile().getPath().toLowerCase()+"]");
                        
                        pm.save(appDelegate_.getLayerStore(), fileChooser.getSelectedFile().getPath().toLowerCase());
                        

                }
                
			}
		});
		
		file.add(fileSave);
		
		loadImage = new JMenuItem("Load Image...");
		
		loadImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

                String sFilename = "";
                JFileChooser fileChooser = new JFileChooser();

                // setting *.pdf filter for save dialog
                FileFilters filterImages = new FileFilters();
                filterImages.addExtension("jpg");
                filterImages.addExtension("jpeg");
                filterImages.addExtension("gif");
                filterImages.addExtension("png");
                filterImages.setDescription("Images");
                
                PreviewPane previewPane = new PreviewPane();
                fileChooser.setAccessory(previewPane);
                fileChooser.addPropertyChangeListener(previewPane);
                
                fileChooser.setFileFilter(filterImages);

                fileChooser.setSelectedFile(new File(sFilename));
                if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
                        
                	//set background image
                	appDelegate_.getLayerStore().getBackgroundImage().setImagePath(fileChooser.getSelectedFile().getPath());
            	}
				
				refresh();
				
			}
		});
		
		file.add(loadImage);
		
		fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				System.exit(0);
				
			}
		});
		
		file.add(fileExit);
		menuBar.add(file);

		help = new JMenu("Help");
		helpOnline = new JMenuItem("Get online Help");
		
		helpOnline.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if(java.awt.Desktop.isDesktopSupported() ) {
			        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			 
			        if(desktop.isSupported(java.awt.Desktop.Action.BROWSE) ) {
			          java.net.URI uri;
					try {
						uri = new java.net.URI("https://code.google.com/p/uniba-kinf-proj-2013/w/list");
						desktop.browse(uri);
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        }
			      }
				
				
			}
		});
		
		help.add(helpOnline);
		menuBar.add(help);
		
		add(menuBar, BorderLayout.NORTH);		

		/*
		 * new JPanel for right side of the window
		 */
		nestedLayout = new JPanel();
		nestedLayout.setLayout(new BoxLayout(nestedLayout, BoxLayout.Y_AXIS));
		
		
		/*
		 * initialise the toolbar
		 */
		createToolBar();
		
		/*
		 * slider to manipulate the opacity of objects
		 */

		opacitySlider_ = new JSlider(JSlider.HORIZONTAL, 0, 100, 100);
		opacitySlider_.setMinorTickSpacing(5);
		opacitySlider_.setMajorTickSpacing(10);
		opacitySlider_.setPaintLabels(true);
		opacitySlider_.setPaintTicks(true);
		opacitySlider_.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
			    if (!source.getValueIsAdjusting()) {
			    	float currentValue = (float)source.getValue();
		        	appDelegate_.getWindow().getLayerView().getModel().getElementAt(
							appDelegate_.getWindow().getLayerView().getMaxSelectionIndex()
						).getObject().setOpacity(currentValue / 100);
			    	getCanvas().repaint();
			    }
			}
		});
		
		/*
		 * layer view, showing the different layers
		 */
		layerView_ = new LayerView(appDelegate_);
		// layerview is inside a scroll pane
		scrollPaneLayer_ = new JScrollPane(layerView_, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		/*
		 * zoom slider, zoom in or out of the image
		 */
		
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
		nestedLayout.add(opacitySlider_);
		nestedLayout.add(scrollPaneLayer_);
		nestedLayout.add(zoomSlider);
		
		add(nestedLayout, BorderLayout.LINE_END);
		
		setSize(750, 550);
		
		opacitySlider_.setEnabled(false);
		
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
	
	public JSlider getOpacitySlider()
	{
		return opacitySlider_;
	}
	
	private void createToolBar()
	{
		toolBar_ = new JToolBar(JToolBar.HORIZONTAL);

		// button for polygon, make lines between points
		JButton tbPolygonButton = new JButton();
		tbPolygonButton.setIcon(new ImageIcon(getClass().getResource("/res/polygon.png")));
		tbPolygonButton.setToolTipText("Polygon");
		tbPolygonButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	// enable "stop action" button
	        	getToolBar().getComponent(BUTTON_STOP_ACTION).setEnabled(true);
	            appDelegate_.setMouseAction(new CreatePolygonMouseAction(appDelegate_));
	        }
	    });
		toolBar_.add(tbPolygonButton);
		
		// button for free hand drawings (many implicit points in a polygon)
		JButton tbFreeHandButton = new JButton();
		tbFreeHandButton.setIcon(new ImageIcon(getClass().getResource("/res/freehand.png")));
		tbFreeHandButton.setToolTipText("Free Hand");
		tbFreeHandButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	// enable "stop action" button
	        	getToolBar().getComponent(BUTTON_STOP_ACTION).setEnabled(true);
	            appDelegate_.setMouseAction(new FreeHandPolygonMouseAction(appDelegate_));
	        }
	    });
		toolBar_.add(tbFreeHandButton);
		
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbMoveButton = new JButton();
		tbMoveButton.setIcon(new ImageIcon(getClass().getResource("/res/movepolygon.png")));
		tbMoveButton.setToolTipText("Move Polygon");
		tbMoveButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	// enable "stop action" button
	        	getToolBar().getComponent(BUTTON_STOP_ACTION).setEnabled(true);
	            appDelegate_.setMouseAction(new MovePolygonMouseAction(appDelegate_));
	        }
	    });
		tbMoveButton.setEnabled(false);
		toolBar_.add(tbMoveButton);
		
		// button to change to color of a layer or polygon
		JButton tbColorButton = new JButton();
		tbColorButton.setIcon(new ImageIcon("res/color.png"));
		tbColorButton.setToolTipText("Change Color");
		tbColorButton.addActionListener(new ActionListener() {
	
	        public void actionPerformed(ActionEvent e)
	        {
	        	IOrderedObject selectedObject = appDelegate_.getWindow().getLayerView().getModel().getElementAt(
						appDelegate_.getWindow().getLayerView().getMaxSelectionIndex()
					).getObject();
	        	
	        	Color newColor = JColorChooser.showDialog(
	                     Window.this,
	                     "Choose Color",
	                     selectedObject.getColor());
	        	
	        	if(newColor != null)
	        	{
	        		selectedObject.setColor(newColor);
	        		refresh();
	        	}
	        }
	    });
		tbColorButton.setEnabled(false);
		toolBar_.add(tbColorButton);
		
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbDeleteObject = new JButton();
		tbDeleteObject.setIcon(new ImageIcon(getClass().getResource("/res/delete.png")));
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
		tbStopactionButton.setIcon(new ImageIcon(getClass().getResource("/res/stop.png")));
		tbStopactionButton.setToolTipText("Stop Action");
		tbStopactionButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
    			appDelegate_.getWindow().getCanvas().clearTempGeometries();
    			appDelegate_.setMouseAction(new DummyMouseAction(appDelegate_));
    			canvas_.repaint();
	        	// disable "stop action" button
	        	getToolBar().getComponent(BUTTON_STOP_ACTION).setEnabled(false);
            }
        });
		tbStopactionButton.setEnabled(false);
		toolBar_.add(tbStopactionButton);

		// button to create a new layer
		JButton tbCreatelayerButton = new JButton();
		tbCreatelayerButton.setIcon(new ImageIcon(getClass().getResource("/res/addlayer.png")));
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
