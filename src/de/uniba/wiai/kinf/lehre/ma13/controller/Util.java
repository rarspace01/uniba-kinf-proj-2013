package de.uniba.wiai.kinf.lehre.ma13.controller;

public class Util
{
	/** last ID that was allocated */
	private Long _lastId;
	
	private Util()
	{
		_lastId = 1337L;
	}
	
	public static Util instance()
	{
		return new Util();
	}
	
	public Long getId()
	{
		return _lastId++;
	}
	
	public void setId(Long lastId)
	{
		_lastId = lastId;
	}
}
