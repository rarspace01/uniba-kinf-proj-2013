package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public abstract class MouseAction implements MouseMotionListener, MouseListener
{	
	protected IAppDelegate appDelegate_;
	protected int oldX_ = -1;
	protected int oldY_ = -1;
	protected boolean mouseDragged_ = false;
	
	public MouseAction(IAppDelegate appDelegate)
	{
		appDelegate_ = appDelegate;
	}
	
	/**
	 * method called if mouse moved to position (x|y)
	 * position is relative to upper left corner of canvas
	 */
	public abstract void onmouseMoved(boolean dragged, int x, int y);
	
	/**
	 * method called if mouse was clicked
	 */
	public abstract void onmouseDown(int x, int y);

	/**
	 * method called if mouse was clicked
	 */
	public abstract void onmouseUp(int x, int y);
	
	/**
	 * method called if user doubleclicked
	 */
	public abstract void onmouseDoubleClick(int x, int y);
	
	/**
	 * MouseListener, mouse pressed
	 */
	public void mousePressed(MouseEvent e)
	{
		Point worldPt = appDelegate_.getUtil().toWorldCoordinates(e.getPoint());
		mouseDragged_ = true;
		oldX_ = worldPt.x;
		oldY_ = worldPt.y;
		onmouseDown(worldPt.x, worldPt.y);
	}

	/**
	 * MouseListener, mouse released after pressed
	 */
	public void mouseReleased(MouseEvent e)
	{	
		Point worldPt = appDelegate_.getUtil().toWorldCoordinates(e.getPoint());
		onmouseUp(worldPt.x, worldPt.y);
		mouseDragged_ = false;
	}
	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseDragged(MouseEvent e)
	{
		Point worldPt = appDelegate_.getUtil().toWorldCoordinates(e.getPoint());
		if(Math.abs(worldPt.x - oldX_) + Math.abs(worldPt.y - oldY_) > 5)
		{
			onmouseMoved(true, worldPt.x, worldPt.y);
			oldX_ = worldPt.x;
			oldY_ = worldPt.y;
		}
	}

	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseMoved(MouseEvent e)
	{
		Point worldPt = appDelegate_.getUtil().toWorldCoordinates(e.getPoint());
		if(Math.abs(worldPt.x - oldX_) + Math.abs(worldPt.y - oldY_) > 5)
		{
			onmouseMoved(false, worldPt.x, worldPt.y);
			oldX_ = worldPt.x;
			oldY_ = worldPt.y;
		}
	}
	
	public void mouseClicked(MouseEvent e)
	{
		if(e.getClickCount() > 1)
		{
			Point worldPt = appDelegate_.getUtil().toWorldCoordinates(e.getPoint());
			onmouseDoubleClick(worldPt.x, worldPt.y);
		}
	}
	
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}
