package com.group7.project;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group7.project.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainActivity extends MapActivity implements BuildingCoordinates
{	
	private MapView mapView;					// The map view used for higher level functions
	private EditText editText;
	private MapController mapController;		// But most processing will happen with the map controller
	private LocationManager locationManager;	// Location manager deals with things to do with current device location (GPS)
	private LocationListener locationListener;	// The listener that checks for all events to do with location (GPS turned on/off, location change, etc.
	private boolean trackingUser = false;		// Boolean value keeps track of whether or not we are currently locked onto and tracking the user's location
	static int markerLoc = -1;					// Used to keep track of the user's location marker in the mapView list of overlays
	ToggleButton toggleTrackingButton;			// The button used to toggle turning user lock-on tracking on and off
	GeoPoint userPoint;							// The user's coordinates
//	List<Building> listOfBuildings;				// Listing of all buildings in BuildingCoordinates.java
	private float distanceToBuilding;			// Calculates distance between user and center point of building
	
	private String currentBuilding = "(none)";	// String value to keep track of the building we are currently in
	private Activity activity = this;
	private Paint circlePainter;
    private Point screenCurrentPoint;
    private MapOverlay mapOverLay;
//	private GoogleMap mMap;
	/****************
	 * toggleUserTracking
	 * 
	 * @param v - The view that called this function
	 * 
	 * Called on click of toggleTrackingButton.
	 * Will turn lock-on tracking on and off, and update variables and buttons accordingly
	 * 
	 */
	public void toggleUserTracking(View v)
	{
		//checks if a user point is set yet, or if the GPS is not turned on
		if (userPoint == null || !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Toast.makeText(getBaseContext(), "Location not set by GPS", Toast.LENGTH_SHORT).show();
			toggleTrackingButton.setChecked(false);
			trackingUser = false;	//redundant code for just in case situations
		}
		else
		{
			trackingUser = !trackingUser;						//toggle the lock-on
			toggleTrackingButton.setChecked(trackingUser);		//turn button on/off
			
			if (trackingUser)
			{
				mapController.animateTo(userPoint);				//instantly focus on user's current location
			}
		}
		
	}
	
	public void searchButtonClicked(View v)
	{
		String searchCriteria = editText.getText().toString();
		Building searchBuilding = null;
		GeoPoint p = null;
		for(Building b: AllBuildings)
		{
			if(b.getName().toLowerCase().contains(searchCriteria.toLowerCase()))
			{
				searchBuilding = b;
				break;
			}
		}
		if(searchBuilding != null)
		{
			p = new GeoPoint( (int) (searchBuilding.getCenter().latitude * 1E6), 
                    (int) (searchBuilding.getCenter().longitude * 1E6));

			mapController.animateTo(p);
			mapController.setZoom(20);
		
		    //listOfOverlays.add(mapOverlay);
		
		    mapView.invalidate();
		    editText.setText("");
		    mapView.requestFocus();
		}
		else
		{
			AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
            builder2.setTitle("Google Map");
            builder2.setMessage("Please Provide better search criteria");
            builder2.setCancelable(true);
            
            builder2.show();
		}
	}
	
	/****************
	 * dispatchTouchEvent
	 * 
	 * @param event - The event that called this function
	 * 
	 * dispatchTouchEvent can handle many, MANY things. Right now all we need it for is to check
	 * 	when the user has panned the screen (MotionEvent.ACTION_MOVE).
	 * 	If the user has moved the screen around, then we should turn off the lock-on tracking of their location
	 *  But they will have needed to have moved more than 70... units?
	 *  This was to fix issues with pressing the toggle button and it also registering as movement, meaning the toggle
	 *  would want to turn tracking off, but this function would do it first, so then the toggle would just turn it back
	 *  on again
	 * 
	 * Could use this function later to check on keeping the user within our required boundaries with use of LatLngBounds
	 * 
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			float startX = event.getX();
			float startY = event.getY();
			float distanceSum = 0;
			final int historySize = event.getHistorySize();
			
			for (int h = 0; h < historySize; h++)
			{
				// historical point
				float hx = event.getHistoricalX(0, h);
				float hy = event.getHistoricalY(0, h);
				// distance between startX,startY and historical point
				float dx = (hx-startX);
				float dy = (hy-startY);
				distanceSum += Math.sqrt(dx*dx+dy*dy);
				// make historical point the start point for next loop iteration
				startX = hx;
				startY = hy;
			}
			
		    // add distance from last historical point to event's point
		    float dx = (event.getX(0)-startX);
		    float dy = (event.getY(0)-startY);
		    distanceSum += Math.sqrt(dx*dx+dy*dy);
		    
		    if (distanceSum > 70.0)
		    {
				trackingUser = false;
				toggleTrackingButton.setChecked(false);
		    }
		}
		
		return super.dispatchTouchEvent(event);
	}
	
	/****************
	 * onCreate
	 *  
	 * @param savedInstanceState - Holds any dynamic data held in onSaveInstanceState (we don't need to worry about this)
	 * 
	 * The initial set up of everything that needs to be done when the view is first created
	 * 	ie. when the app is first run
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		//Get a listing of all the buildings and save them so we can
		//		access them easier later
		AllBuildings.add(E1);
		AllBuildings.add(E2);
		AllBuildings.add(E3);
		AllBuildings.add(UCENTRE);
		AllBuildings.add(Fletcher_Argue);
		AllBuildings.add(Buller);
		AllBuildings.add(Elizabeth_Dafoe_Library);
		AllBuildings.add(Helen_Glass);
		AllBuildings.add(Parkade);
		AllBuildings.add(Russell);
		AllBuildings.add(Education);
		AllBuildings.add(St_Pauls_College);
		AllBuildings.add(St_Johns_College);
		AllBuildings.add(Q_Lot);
		
		//starting latitude and longitude. currently E1
		final double startLat = AllBuildings.get(0).getCenter().latitude;
		final double startLong = AllBuildings.get(0).getCenter().longitude;
		
		super.onCreate(savedInstanceState);		//run the parent's onCreate, where I imagine the UI stuff gets taken care of
		setContentView(R.layout.activity_main);	//set our main activity to be the default view
		
		GeoPoint centerPoint = new GeoPoint((int)(startLat * 1E6), (int)(startLong * 1E6));	//create a GeoPoint based on those values
		
		mapView = (MapView) findViewById(R.id.mapView);	//get the MapView object from activity_main
		mapView.requestFocus();
		editText  = (EditText)findViewById(R.id.editText);
		toggleTrackingButton = (ToggleButton) findViewById(R.id.toggleTrackingButton);	//get the toggleTrackingButton from activity_main

		//settings on what to show on the map
		//mapView.setSatellite(true);
		//mapView.setTraffic(true);
		
		//GPS setup stuff
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();		
		locationManager.addGpsStatusListener((Listener) locationListener);
		
		//This next line here sets our listener to check for changes to the GPS
		//The first integer value is the minimum time to wait before taking another update (in milliseconds)
		//So entering 500 here means it will only listen and run it's functions every 0.5 seconds
		//The second integer value is the minimum change in distance required before the functions will be called (in metres)
		//So entering 4 here means that unless the user has moved 4 metres from the last time we did an update, nothing will be called
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
		
		//mapView.setBuiltInZoomControls(true);	//turn on zoom controls
		
		mapController = mapView.getController();
		mapController.setZoom(17);					//set default zoom level
		mapController.setCenter(centerPoint);		//move center of map
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		mapOverLay = new MapOverlay();
		mapOverlays.add(mapOverLay);
		
		Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
		
		for(Building b:AllBuildings)
		{
			GeoPoint point = new GeoPoint(
					(int) (b.getCenter().latitude * 1E6),
					(int) (b.getCenter().longitude * 1E6)
				);
			
			OverlayItem overlayitem = new OverlayItem(point, b.getName(), b.getInfo());
			itemizedoverlay.addOverlay(overlayitem);
			mapOverlays.add(itemizedoverlay);	
		}
		
		//mapView.invalidate();
	}

	/****************
	 * onCreateOptionsMenu
	 * 
	 * @param menu - The menu we're gonna create? Not too sure about this function
	 * 
	 * I imagine this could come in handy later if we want to do some menu stuff.
	 * Would need to read up on it more
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/****************
	 * onBackPressed
	 * 
	 * Called when the user presses the back button on the device
	 * 
	 */
	public void onBackPressed()
	{
		//pressing the back button on the device will kill the app
		System.exit(0);
	}
	
	/****************
	 * isRouteDisplayed
	 * 
	 * Used if we want to display routing information
	 */
	@Override
	protected boolean isRouteDisplayed()
	{
		//I should figure out what this is about
		return true;
	}
	
	
	
	
	private class GPSLocationListener implements LocationListener, GpsStatus.Listener
	{
		private int GPSEvent = GpsStatus.GPS_EVENT_STOPPED;		// Keeps track of the current status of the GPS
		
		/****************
		 * onLocationChanged
		 * 
		 * @param location - User's changed location
		 * 
		 * Called any time the user's location changes
		 * So far, we're using it to update the location of the user's icon, but can later be used to check
		 * if a user is within the range of a building
		 * 
		 */
		@Override
		public void onLocationChanged(Location location) {
						
			// Run the very first time the GPS gets a signal and is able to fix the user's location
			if (location != null && GPSEvent == GpsStatus.GPS_EVENT_FIRST_FIX) {
				userPoint = new GeoPoint(
					(int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6)
				);
				
				if (trackingUser)
				{
					toggleTrackingButton.setChecked(true);
					mapController.animateTo(userPoint);
					mapController.setZoom(20);
				}

				// add marker
				mapOverLay.setPointToDraw(userPoint);
				List<Overlay> listOfOverlays = mapView.getOverlays();
				
				if (markerLoc != -1)	// markerLoc == -1 if no location had ever been previously set
				{
					listOfOverlays.remove(markerLoc);
				}
				
				listOfOverlays.add(mapOverLay);			//add the marker to the mapView's overlays
				markerLoc = listOfOverlays.size()-1;	//keep track of where it's stored so we can update it later
				
				//invalidating it forces the map view to re-draw
				mapView.invalidate();
				
				LatLng currPoint = new LatLng(location.getLatitude(), location.getLongitude());
				
				// TEST CODE - to see if it could detect that I was inside my house
				// This kind of code is the basis of what we could do to detect when the user is in range of a building
				// This is more for when they enter a building. In terms of figuring out how far they are from a
				// building, I don't think this code will work. LatLngBounds has a .getCenter() function in JavaScript
				// which would have been AWESOME to have here. Unfortunately the Android API doesn't have anything like
				// that. We could probably calculate our own if we really wanted to...
				
				float minDistance = 1000;
				Building minBuilding = null;
				
				for(Building b: AllBuildings)
				{
					if (b.getBounds().contains(currPoint))
					{
						if (!currentBuilding.equals(b.getName()))
						{
							Toast.makeText(getBaseContext(),
									"ENTERED " + b.getName(),
									Toast.LENGTH_SHORT).show();
							
							currentBuilding = b.getName();
						}
					}
					else
					{
						distanceToBuilding = b.getCentreLocation().distanceTo(location);
						if(distanceToBuilding < minDistance)
						{
							minDistance = distanceToBuilding;
							minBuilding = b;
						}
					}
				}
	 			
				/*
				if(minDistance == 70 && minBuilding != null)//tell user they are near a building only once when they get about 70 metres close to it 
				{					 //this prevents multiple alerts as they get closer
					AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
		            builder1.setMessage("You are near " + minBuilding.getName());
		            builder1.setCancelable(true);
		            
		            AlertDialog alert11 = builder1.create();
		            alert11.show();
				}
				*/				
			}
		}

		/****************
		 * onProviderDisabled
		 * 
		 * @param provider - Name of the location provider that has been disabled
		 * 
		 * We only use this right now to keep all our tracking information consistent when the user
		 * decides to turn off the GPS
		 */
		@Override
		public void onProviderDisabled(String provider)
		{
			if (provider.equals(LocationManager.GPS_PROVIDER))
			{
				trackingUser = false;
				toggleTrackingButton.setChecked(false);
			}
		}
		
		@Override
		public void onProviderEnabled(String provider)
		{
			//Toast.makeText(getBaseContext(), "Provider enabled (" + provider + ")", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)
		{
			//Toast.makeText(getBaseContext(), "Status changed: (" + provider + " - " + status + ")", Toast.LENGTH_LONG).show();
		}

		/****************
		 * onGpsStatusChanged
		 * 
		 * @param event - The integer value of the event that has occurred
		 * 
		 * Used to update the GPSEvent variable so that we know what's going on with the GPS
		 * 
		 * Called when the GPS' status has changed
		 * 		1 = GPS_EVENT_STARTED			(GPS turned on)
		 *  	2 = GPS_EVENT_STOPPED			(GPS turned off)
		 *  	3 = GPS_EVENT_FIRST_FIX			(Got fix on GPS satellite after having nothing and searching)
		 *  	4 = GPS_EVENT_SATELLITE_STATUS	(Event sent periodically to report GPS satellite status, we don't care about this)	
		 */
		@Override
		public void onGpsStatusChanged(int event)
		{
			//System.out.println("GPS Status: " + event);

			if (event != GpsStatus.GPS_EVENT_SATELLITE_STATUS)
			{
				GPSEvent = event;
			}
		}
	}
	
	public class MapOverlay extends Overlay
	{
		private GeoPoint pointToDraw;
		
		public void setPointToDraw(GeoPoint point)
		{
			pointToDraw = point;
		}

		/*
		public GeoPoint getPointToDraw()
		{
			return pointToDraw;
		}
		*/
		
		@Override
		public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
		{
			circlePainter = new Paint();
	        circlePainter.setAntiAlias(true);
	        circlePainter.setStrokeWidth(2.0f);
	        circlePainter.setColor(0xff6666ff);
	        circlePainter.setStyle(Style.STROKE);
	        circlePainter.setAlpha(70);
	        for(Building b: AllBuildings)
			{
				if(b!=null)
				{
					GeoPoint point = new GeoPoint(
					(int) (b.getCenter().latitude * 1E6),
					(int) (b.getCenter().longitude * 1E6)
					);
					// Get projection from the mapView. 
			        Projection projection = mapView.getProjection();
			        screenCurrentPoint = new Point();
			        // Project the gps coordinate to screen coordinate
			        projection.toPixels(point, screenCurrentPoint);
	
			        int radius = 200;
			        // draw the blue circle
			        canvas.drawCircle(screenCurrentPoint.x, screenCurrentPoint.y, radius, circlePainter);
				}
			}
			super.draw(canvas, mapView, shadow);           
				
			// convert point to pixels
			Point screenPts = new Point();
			if(pointToDraw!=null)mapView.getProjection().toPixels(pointToDraw, screenPts);
			
			// add marker
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 24, null);    
			return true;
		}
	} 
}
