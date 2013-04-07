package com.group7.project;

import java.util.List;

import android.os.Bundle;
//import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBoundsCreator;
import com.group7.project.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class MainActivity extends MapActivity implements BuildingCoordinates
{	
	private MapView mapView;
	private MapController mapController;
	private LocationManager locationManager;
	private LocationListener locationListener;

	private String currentBuilding = "(none)";

/*
	//LIMIT TO CAMPUS - THIS IS HARD
	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		GeoPoint newPoint;
		
		final float minLong = -97.156992f;
		final float maxLong = -97.123303f;
		final float minLat = 49.805292f;
		final float maxLat = 49.813758f;
		
		int currLat;
		int currLong;
		
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			newPoint = mapView.getProjection().fromPixels((int)event.getX(), (int)event.getY());
			
			currLat = newPoint.getLatitudeE6();
			currLong = newPoint.getLongitudeE6();

			float temp = currLat - minLat;
			int minLatInt = (int)(minLat * 1E6);
			
			if ((currLat - minLatInt) < 0)
			{
				newPoint = new GeoPoint(minLatInt, currLong);
				//mapController.stopPanning();				
				//mapController.setCenter(newPoint);
				mapView.invalidate();
			}
		}
		
		return super.dispatchTouchEvent(event);
	}
*/	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		final float startLat = 49.808503f;
		final float startLong = -97.135824f;
		
		GeoPoint centerPoint = new GeoPoint((int)(startLat * 1E6), (int)(startLong * 1E6));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mapView = (MapView) findViewById(R.id.mapView);

		//settings on what to show on the map
		//mapView.setSatellite(true);
		//mapView.setTraffic(true);
		
		//GPS setup stuff
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		
		locationManager.addGpsStatusListener((Listener) locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 4, locationListener);	
		
		//mapView.setBuiltInZoomControls(true);	//turn on zoom controls

		mapController = mapView.getController();
		mapController.setZoom(20);	//set default zoom level
		mapController.setCenter(centerPoint);
		
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.ic_launcher);
		HelloItemizedOverlay itemizedoverlay = new HelloItemizedOverlay(drawable, this);
		
		GeoPoint point = new GeoPoint(
				(int) (E2_NORTHEAST.latitude * 1E6),
				(int) (E2_NORTHEAST.longitude * 1E6)
			);
		
		OverlayItem overlayitem = new OverlayItem(point, "E2", "NORTH EAST");
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
		
		GeoPoint point2 = new GeoPoint(
				(int) (E2_SOUTHWEST.latitude * 1E6),
				(int) (E2_SOUTHWEST.longitude * 1E6)
			);
		
		OverlayItem overlayitem2 = new OverlayItem(point2, "E2", "SOUTH WEST");
		itemizedoverlay.addOverlay(overlayitem2);
		mapOverlays.add(itemizedoverlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void onBackPressed()
	{
		//pressing the back button on the device will kill the app
		System.exit(0);
	}
	
	@Override
	protected boolean isRouteDisplayed()
	{
		//I should figure out what this is about
		return false;
	}
	
	private class GPSLocationListener implements LocationListener, GpsStatus.Listener
	{
		private int GPSEvent = GpsStatus.GPS_EVENT_STOPPED;
		/*
			Called any time the user's location changes. The Toast thing at the bottom shows that when on GPS,
			the user's location changes a ton even when they're not actually moving
			There's definitely a way to adjust this function to fit our needs.
			Such as setting a delta and choosing to only run stuff when the user's location has changed by a
			certain amount within our desired range of error
		*/
		@Override
		public void onLocationChanged(Location location) {

			// Run the very first time the GPS gets a signal and is able to fix the user's location
			if (location != null && GPSEvent == GpsStatus.GPS_EVENT_FIRST_FIX) {
				GeoPoint point = new GeoPoint(
					(int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6)
				);

				//mapController.animateTo(point);
				mapController.setZoom(20);
			
				/*
				  // add marker
				  MapOverlay mapOverlay = new MapOverlay();
				  mapOverlay.setPointToDraw(point);
				  List<Overlay> listOfOverlays = mapView.getOverlays();
				  listOfOverlays.clear();
				  listOfOverlays.add(mapOverlay);
				*/
				
				//invalidating it forces the map view to re-draw
				mapView.invalidate();
				
				LatLng currPoint = new LatLng(location.getLatitude(), location.getLongitude());
				
				if (HOUSE.getBounds().contains(currPoint))
				{
					if (!currentBuilding.equals(HOUSE.getName()))
					{
						Toast.makeText(getBaseContext(),
								"ENTERED " + HOUSE.getName(),
								Toast.LENGTH_SHORT).show();
						
						currentBuilding = HOUSE.getName();
					}
				}
				else
				{
					Toast.makeText(getBaseContext(),
							"NOT IN " + HOUSE.getName(),
							Toast.LENGTH_SHORT).show();
				}
				
				if (E1.getBounds().contains(currPoint))
				{
					Toast.makeText(getBaseContext(),
							"ENTERED " + E1.getName(),
							Toast.LENGTH_SHORT).show();
					
					currentBuilding = E1.getName();
					
					System.out.println(currentBuilding);
				}
			}
		}

		@Override
		public void onProviderDisabled(String provider)
		{
			//Toast.makeText(getBaseContext(), "Provider disabled (" + provider + ")", Toast.LENGTH_LONG).show();
		}
		
		private void onTap()
		{
			//Toast.makeText(getBaseContext(), "TAP!", Toast.LENGTH_SHORT).show();
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

		@Override
		public void onGpsStatusChanged(int event)
		{
			System.out.println("GPS Status: " + event);
			
			if (event != GpsStatus.GPS_EVENT_SATELLITE_STATUS)
			{
				GPSEvent = event;
			}
		}
	}
	
	private class MapOverlay extends Overlay
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
			super.draw(canvas, mapView, shadow);           
		
			// convert point to pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(pointToDraw, screenPts);
			
			// add marker
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y - 24, null);    
			return true;
		}
	} 
}
