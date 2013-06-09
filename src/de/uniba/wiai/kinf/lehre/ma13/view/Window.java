package de.uniba.wiai.kinf.lehre.ma13.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
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

		// end application when closing the window
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setTitle("iRestore");
		
		// set border layout
		this.setLayout(new BorderLayout(5, 5));

		// initialise canvas area
		_canvas = new Canvas(_appDelegate);
		_canvas.setGeometryDrawer(new DrawPolygonRaw());
		
		add((JComponent)_canvas);
		
		// initialise layer view
		_layerView = new LayerView(_appDelegate);
		add(_layerView, BorderLayout.LINE_END);
		
		setSize(400, 400);
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
