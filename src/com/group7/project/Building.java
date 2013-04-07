package com.group7.project;

import com.google.android.gms.maps.model.LatLngBounds;

public class Building
{
	String name;
	LatLngBounds bounds;
	
	public Building(String name, LatLngBounds bounds)
	{
		this.name = name;
		this.bounds = bounds;
	}
	
	public LatLngBounds getBounds()
	{
		return bounds;
	}
	
	public String getName()
	{
		return name;
	}
}
