package de.uniba.wiai.kinf.lehre.ma13.controller;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.CreatePolygonMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MouseAction;
import de.uniba.wiai.kinf.lehre.ma13.model.DataManager;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;
import de.uniba.wiai.kinf.lehre.ma13.view.Window;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

public class AppDelegate implements IAppDelegate {
	
	/*
	 * view
	 */
	/** the window which contains all views */
	private IWindow _window;
	
	/*
	 * model
	 */
	private ILayerStore _layerStore;
	
	/*
	 * controller
	 */
	private Util _util;
	private MouseAction _mouseAction;
	
	
	public static void main(String[] args)
	{
		new AppDelegate().init();
	}
	
	@Override
	public void init() {
		setWindow(new Window(this));
		
		_layerStore = new LayerStore(this);
		_util = Util.instance();
		
		DataManager dm = new DataManager(this);
		dm.loadTestData();
		
		setMouseAction(new CreatePolygonMouseAction(this));
		_window.refresh();
	}

	@Override
	public void init(String fileName) {
		init();		
	}

	public IWindow getWindow() {
		return _window;
	}
	public void setWindow(IWindow window) {
		_window = window;
	}
	
	public ILayerStore getLayerStore() {
		return _layerStore;
	}
	
	public void setMouseAction(MouseAction ma)
	{
		_mouseAction = ma;
		_window.getCanvas().setMouseAction(_mouseAction);
	}
	
	public MouseAction getMouseAction()
	{
		return _mouseAction;
	}

	@Override
	public Util getUtil() {
		return _util;
	}

	@Override
	public Long getId() {
		return _util.getId();
	}

}
