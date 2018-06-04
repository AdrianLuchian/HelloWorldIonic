package cordova.plugin.helloWorld.tasks;

import cordova.plugin.helloWorld.disertatie.ListenerService;
import cordova.plugin.helloWorld.listeners.BatteryLevel;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

public class BatteryWatcherTask extends ListenerTask {

	@SuppressWarnings("unused")
	private int time;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Integer... params) {
		time = params[0];
		
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        BatteryLevel batteryLevel = new BatteryLevel( this );
		ListenerService.__instance.registerReceiver(batteryLevel , ifilter);
		
		while( ListenerService.__instance != null && ListenerService.__instance.isRunning() ) {
			try {
				Thread.sleep(10000);
			} catch( InterruptedException e ) {
				ListenerService.__instance.unregisterReceiver(batteryLevel);
				
			}
			
			SaverTask saver = new SaverTask();
			saver.executeOnExecutor( ListenerService.__instance.saverPool, sensorData );
		}
		
		ListenerService.__instance.unregisterReceiver(batteryLevel);
		
		return null;
	}
}
