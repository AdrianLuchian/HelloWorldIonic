package cordova.plugin.helloWorld.tasks;

import cordova.plugin.helloWorld.disertatie.ListenerService;
import cordova.plugin.helloWorld.listeners.CurrentLocationListener;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;

public class LocationListenerTask extends ListenerTask {

	int locationType;
	final static String[] locationTypes = { LocationManager.NETWORK_PROVIDER, LocationManager.GPS_PROVIDER };
	int time;
	
	LocationManager lm;
	CurrentLocationListener locationListener;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Integer... params) {
		lm = (LocationManager) ListenerService.__instance.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new CurrentLocationListener( this );
		try {
			locationType = params[0];
			time = params[1];
			
			// location updates must be requested from a UI thread 
			ListenerService.__instance.mHandler.post(new Runnable(){
			    public void run(){
			    	lm.requestLocationUpdates( locationTypes[locationType], time, 0, locationListener);
			    	
			    }
			 });
			
			while( ListenerService.__instance != null && ListenerService.__instance.isRunning() ) {
				Thread.sleep(10000);
				
				SaverTask saver = new SaverTask();
				saver.executeOnExecutor( ListenerService.__instance.saverPool, sensorData );
			}
			
			lm.removeUpdates( locationListener );
		} catch( Exception e ) {
			lm.removeUpdates( locationListener );
		}
		return null;
	}
	
	protected void onCancelled() {
		if( lm != null && locationListener != null ) {
			lm.removeUpdates( locationListener );
		}
	}
}
