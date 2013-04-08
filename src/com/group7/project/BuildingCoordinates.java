package com.group7.project;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public interface BuildingCoordinates
{
	final int NUM_BUILDINGS = 5;
	static Building AllBuildings[] = new Building[NUM_BUILDINGS];
	int i=0;
	// Used this site to get coordinates: http://itouchmap.com/latlong.html
	
	// EITC-E1
	final LatLng E1_NORTHEAST = new LatLng(49.808600, -97.133249);
	final LatLng E1_SOUTHWEST = new LatLng(49.808032, -97.133834);
	final LatLngBounds E1_BOUNDS = new LatLngBounds(E1_SOUTHWEST, E1_NORTHEAST);
	static Building E1 = new Building("EITC-E1", E1_BOUNDS);

	// EITC-E2
	final LatLng E2_NORTHEAST = new LatLng(49.80895, -97.133426);
	final LatLng E2_SOUTHWEST = new LatLng(49.808399, -97.134247);
	final LatLngBounds E2_BOUNDS = new LatLngBounds(E2_SOUTHWEST, E2_NORTHEAST);
	static Building E2 = new Building("EITC-E2", E2_BOUNDS);
	
	// EITC-E3
	final LatLng E3_NORTHEAST = new LatLng(49.808652, -97.13444);
	final LatLng E3_SOUTHWEST = new LatLng(49.808116, -97.134939);
	final LatLngBounds E3_BOUNDS = new LatLngBounds(E3_SOUTHWEST, E3_NORTHEAST);
	static Building E3 = new Building("EITC-E3", E3_BOUNDS); 
	
	// UNIVERSITY CENTRE
	final LatLng UCENTRE_NORTHEAST = new LatLng(49.809742, -97.134097);
	final LatLng UCENTRE_SOUTHWEST = new LatLng(49.808399, -97.134247);
	final LatLngBounds UCENTRE_BOUNDS = new LatLngBounds(UCENTRE_SOUTHWEST, UCENTRE_NORTHEAST);
	static Building UCENTRE = new Building("University Centre", UCENTRE_BOUNDS);
	
	// MY HOUSE
	final LatLng HOUSE_NORTHEAST = new LatLng(49.923167, -97.20252);
	final LatLng HOUSE_SOUTHWEST = new LatLng(49.922528, -97.203818);
	final LatLngBounds HOUSE_BOUNDS = new LatLngBounds(HOUSE_SOUTHWEST, HOUSE_NORTHEAST);
	static Building HOUSE = new Building("HOUSE", HOUSE_BOUNDS);
}
