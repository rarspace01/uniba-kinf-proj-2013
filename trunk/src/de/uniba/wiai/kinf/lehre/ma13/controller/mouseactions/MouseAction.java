package de.uniba.wiai.kinf.lehre.ma13.controller.mouseactions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import de.uniba.wiai.kinf.lehre.ma13.controller.interfaces.IAppDelegate;

public abstract class MouseAction implements MouseMotionListener, MouseListener
{	
	protected IAppDelegate _appDelegate;
	protected int oldX = -1;
	protected int oldY = -1;
	protected boolean mouseDragged = false;
	
	public MouseAction(IAppDelegate appDelegate)
	{
		_appDelegate = appDelegate;
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
		mouseDragged = true;
		oldX = e.getX();
		oldY = e.getY();
		onmouseDown(e.getX(), e.getY());
	}

	/**
	 * MouseListener, mouse released after pressed
	 */
	public void mouseReleased(MouseEvent e)
	{
		onmouseUp(e.getX(), e.getY());
		mouseDragged = false;
	}
	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseDragged(MouseEvent e)
	{
		if(Math.abs(e.getX() - oldX) > 10 || Math.abs(e.getY() - oldY) > 10)
		{
			onmouseMoved(true, e.getX(), e.getY());
			oldX = e.getX();
			oldY = e.getY();
		}
	}

	
	/**
	 * MouseMotionListener, mouse moved
	 */
	public void mouseMoved(MouseEvent e)
	{
		if(Math.abs(e.getX() - oldX) > 10 || Math.abs(e.getY() - oldY) > 10)
		{
			onmouseMoved(false, e.getX(), e.getY());
			oldX = e.getX();
			oldY = e.getY();
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
}
