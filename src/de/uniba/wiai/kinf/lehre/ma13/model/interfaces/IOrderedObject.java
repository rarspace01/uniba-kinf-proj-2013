package de.uniba.wiai.kinf.lehre.ma13.model.interfaces;

import java.io.IOException;
import java.io.ObjectStreamException;

public interface IOrderedObject
{
	public void setVisibility(boolean visible);
	public boolean isVisible();
	
	/*
	 * object id - global (project specific) identifier within the whole application
	 */
	public void setObjectId(Long objectId);
	public Long getObjectId();
	
	/*
	 * object name
	 */
	public void setName(String name);
	public String getName();
	
	/*
	 * serialisation
	 */
	public void writeObject(java.io.ObjectOutputStream out) throws IOException;
	public void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException;
	public void readObjectNoData() throws ObjectStreamException;
}
