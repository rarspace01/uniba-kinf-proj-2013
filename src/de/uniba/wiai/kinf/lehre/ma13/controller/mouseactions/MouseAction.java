package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

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
	 * MouseListener, mouse pressed
	 */
	public void mousePressed(MouseEvent e)
	{
		mouseDragged_ = true;
		oldX_ = e.getX();
		oldY_ = e.getY();
		onmouseDown(e.getX(), e.getY());
	}

	/**
	 * MouseListener, mouse released after pressed
	 */
	public void mouseReleased(MouseEvent e)
	{
		onmouseUp(e.getX(), e.getY());
		mouseDragged_ = false;
	}
	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseDragged(MouseEvent e)
	{
		if(Math.abs(e.getX() - oldX_) + Math.abs(e.getY() - oldY_) > 10)
		{
			onmouseMoved(true, e.getX(), e.getY());
			oldX_ = e.getX();
			oldY_ = e.getY();
		}
	}

	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseMoved(MouseEvent e)
	{
		if(Math.abs(e.getX() - oldX_) + Math.abs(e.getY() - oldY_) > 10)
		{
			onmouseMoved(false, e.getX(), e.getY());
			oldX_ = e.getX();
			oldY_ = e.getY();
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}
