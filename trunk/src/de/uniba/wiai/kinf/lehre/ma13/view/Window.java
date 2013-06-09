package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.CreatePolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.FreeHandPolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.view.drawtemplates.DrawPolygonRaw;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.ICanvas;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

public class Window extends JFrame implements IWindow
{
	private static final long serialVersionUID = 1L;
	private IAppDelegate _appDelegate;
	private ICanvas _canvas;
	private LayerView _layerView;
	
	public Window(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;

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
		_canvas = new Canvas(_appDelegate);
		_canvas.setGeometryDrawer(new DrawPolygonRaw());
		add((JComponent)_canvas, BorderLayout.CENTER);
		

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
		JButton tbPolygonButton = new JButton("Polygon");
		tbPolygonButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                _appDelegate.setMouseAction(new CreatePolygonMouseAction(_appDelegate));
            }
        });
		toolBar.add(tbPolygonButton);
		// button for free hand drawings (many implicit points in a polygon)
		JButton tbFreeHandButton = new JButton("Freehand");
		tbFreeHandButton.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e)
            {
                _appDelegate.setMouseAction(new FreeHandPolygonMouseAction(_appDelegate));
            }
        });
		toolBar.add(tbFreeHandButton);
		toolBar.setFloatable(false);
		
		/*
		 * layer view, showing the different layers
		 */
		_layerView = new LayerView(_appDelegate);
		
		nestedLayout.add(toolBar);
		nestedLayout.add(_layerView);
		add(nestedLayout, BorderLayout.LINE_END);
		
		setSize(750, 550);
		setVisible(true);
	}

	@Override
	public void refresh() {
		_canvas.repaint();
		_layerView.repaint();
	}

	/**
	 * returns the canvas
	 */
	public ICanvas getCanvas()
	{
		return _canvas;
	}
}
