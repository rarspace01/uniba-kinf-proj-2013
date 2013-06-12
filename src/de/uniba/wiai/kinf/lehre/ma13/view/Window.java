package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.CreatePolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.FreeHandPolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.Layer;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayer;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawBackgroundImage;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonRaw;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.ICanvas;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

public class Window extends JFrame implements IWindow
{
	private static final long serialVersionUID = 1L;
	private IAppDelegate appDelegate_;
	private ICanvas canvas_;
	private LayerView layerView_;
	
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
		 * new JPanel for right side of the window
		 */
		JPanel nestedLayout = new JPanel();
		nestedLayout.setLayout(new BoxLayout(nestedLayout, BoxLayout.Y_AXIS));
		
		
		/*
		 * initialise the toolbar
		 */
		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		
		// button for polygon, make lines between points
		JButton tbPolygonButton = new JButton();
		tbPolygonButton.setIcon(new ImageIcon("res/polygon.png"));
		tbPolygonButton.setToolTipText("Polygon");
		tbPolygonButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                appDelegate_.setMouseAction(new CreatePolygonMouseAction(appDelegate_));
            }
        });
		toolBar.add(tbPolygonButton);
		// button for free hand drawings (many implicit points in a polygon)
		JButton tbFreeHandButton = new JButton();
		tbFreeHandButton.setIcon(new ImageIcon("res/freehand.png"));
		tbFreeHandButton.setToolTipText("Free Hand");
		tbFreeHandButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                appDelegate_.setMouseAction(new FreeHandPolygonMouseAction(appDelegate_));
            }
        });
		toolBar.add(tbFreeHandButton);
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbMoveButton = new JButton();
		tbMoveButton.setIcon(new ImageIcon("res/movepolygon.png"));
		tbMoveButton.setToolTipText("Move Polygon");
		tbMoveButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //appDelegate_.setMouseAction(new FreeHandPolygonMouseAction(appDelegate_));
            }
        });
		tbMoveButton.setEnabled(false);
		toolBar.add(tbMoveButton);
		// button to move a polygon (only enabled when polygon is selected in JList)
		JButton tbDeleteObject = new JButton();
		tbDeleteObject.setIcon(new ImageIcon("res/delete.png"));
		tbDeleteObject.setToolTipText("Delete Object");
		tbDeleteObject.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                //appDelegate_.setMouseAction(new FreeHandPolygonMouseAction(appDelegate_));
            }
        });
		tbDeleteObject.setEnabled(false);
		toolBar.add(tbDeleteObject);
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
		toolBar.add(tbCreatelayerButton);
		toolBar.setFloatable(false);
		
		/*
		 * layer view, showing the different layers
		 */
		layerView_ = new LayerView(appDelegate_);
		
		nestedLayout.add(toolBar);
		nestedLayout.add(layerView_);
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
}
