package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

/**
 * 
 * @author lukas
 * 
 */
public class DummyMouseAction extends MouseAction {

	public DummyMouseAction(IAppDelegate appDelegate) {
		super(appDelegate);
	}

	@Override
	public void onmouseMoved(boolean dragged, int x, int y) { }

	@Override
	public void onmouseDown(int x, int y) { }

	@Override
	public void onmouseUp(int x, int y) { }

	@Override
	public void onmouseDoubleClick(int x, int y) { }

}
