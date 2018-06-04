package cordova.plugin.helloWorld.listeners;

import cordova.plugin.helloWorld.database.LocationSensorData;
import cordova.plugin.helloWorld.tasks.ListenerTask;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class CurrentLocationListener implements LocationListener {

	private ListenerTask task;
	
	public CurrentLocationListener( ListenerTask task) {
		this.task = task;
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// Save position
		
		LocationSensorData s = new LocationSensorData();
		s.setTimestamp( arg0.getTime() );
		s.setLatitude(arg0.getLatitude());
		s.setLongitude(arg0.getLongitude());
		s.setAccuracy(arg0.getAccuracy());
		s.setSource(arg0.getProvider());
		
		task.addData(s);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
