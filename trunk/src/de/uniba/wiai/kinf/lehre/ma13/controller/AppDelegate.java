package de.uniba.wiai.kinf.lehre.ma13.controller;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.DummyMouseAction;
import de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions.MouseAction;
import de.uniba.wiai.kinf.lehre.ma13.data.DataManager;
import de.uniba.wiai.kinf.lehre.ma13.model.LayerStore;
import de.uniba.wiai.kinf.lehre.ma13.model.interfaces.ILayerStore;
import de.uniba.wiai.kinf.lehre.ma13.view.Window;
import de.uniba.wiai.kinf.lehre.ma13.view.interfaces.IWindow;

public class AppDelegate implements IAppDelegate {

	/*
	 * view
	 */
	/** the window which contains all views */
	private IWindow window_;

	/*
	 * model
	 */
	private ILayerStore layerStore_;

	/*
	 * controller
	 */
	private Util util_;
	private MouseAction mouseAction_;

	public static void main(String[] args) {
		new AppDelegate().init();
	}

	@Override
	public void init() {

		// set look and feel
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to
			// another look and feel.
		}

		setWindow(new Window(this));

		layerStore_ = new LayerStore(this);
		util_ = Util.instance(this);

		DataManager dm = new DataManager(this);
		dm.loadInitialData();

		setMouseAction(new DummyMouseAction(this));
		window_.refresh();
	}

	@Override
	public void init(String fileName) {
		init();
	}

	public IWindow getWindow() {
		return window_;
	}

	public void setWindow(IWindow window) {
		window_ = window;
	}

	public ILayerStore getLayerStore() {
		return layerStore_;
	}

	public void setMouseAction(MouseAction ma) {
		mouseAction_ = ma;
		window_.getCanvas().setMouseAction(mouseAction_);
	}

	public MouseAction getMouseAction() {
		return mouseAction_;
	}

	@Override
	public Util getUtil() {
		return util_;
	}

	@Override
	public Long getId() {
		return util_.getId();
	}

}
