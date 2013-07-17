package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;

/**
 * ordered object interface, contains all the methods for the generic attributes
 * 
 * @author denis
 * 
 */
public interface IOrderedObject {
	/**
	 * sets the visiblity for rendering
	 * 
	 * @param visible
	 */
	public void setVisibility(boolean visible);

	/**
	 * retrieve the visibility
	 * 
	 * @return {@link Boolean}
	 */
	public boolean isVisible();

	/**
	 * retrieve the objectid<br/>
	 * object id - global (project specific) identifier within the whole
	 * application
	 * 
	 * @param objectId
	 */
	public void setObjectId(Long objectId);

	/**
	 * returns the objectid<br/>
	 * object id - global (project specific) identifier within the whole
	 * application
	 * 
	 * @return {@link Long}
	 */
	public Long getObjectId();

	/**
	 * sets the object name
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * retrieves the object name
	 * 
	 * @return {@link String}
	 */
	public String getName();

	/**
	 * sets the color of the object
	 * 
	 * @param color
	 */
	public void setColor(Color color);

	/**
	 * retrieve the color of the object
	 * 
	 * @return {@link Color}
	 */
	public Color getColor();

	/**
	 * sets the opacity of the current object
	 * 
	 * @param opacity
	 */
	public void setOpacity(float opacity);

	/**
	 * retrieves the opacity of the object
	 * 
	 * @return {@link Float}
	 */
	public float getOpacity();

	/**
	 * serialization - writes the object to a given {@link ObjectOutputStream}
	 * 
	 * @param out
	 * @throws IOException
	 * 
	 * @deprecated  - shouldn't be implemented. isn't used anywhere
	 */
	public void writeObject(java.io.ObjectOutputStream out) throws IOException;

	/**
	 * retrieves the object from a given {@link ObjectInputStream}
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * 
	 * @deprecated  - shouldn't be implemented. isn't used anywhere
	 */
	public void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException;

	/**
	 * reads a dummy object
	 * 
	 * @throws ObjectStreamException
	 * 
	 * @deprecated  - shouldn't be implemented. isn't used anywhere
	 */
	public void readObjectNoData() throws ObjectStreamException;
}
