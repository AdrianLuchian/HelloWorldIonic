package cordova.plugin.helloWorld.tasks;

import cordova.plugin.helloWorld.disertatie.ListenerService;
import cordova.plugin.helloWorld.disertatie.MainActivity;
import cordova.plugin.helloWorld.listeners.SensorListener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;

public class SensorListenerTask extends ListenerTask {

	private int sensor;
	private int time;
	
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Integer... params) {
		sensor = params[0];
		time = params[1];

		SensorManager mSensorManager = (SensorManager) ListenerService.__instance.getSystemService(Context.SENSOR_SERVICE);
		Sensor s = mSensorManager.getDefaultSensor(MainActivity.sensors[sensor]);

		SensorEventListener listener = new SensorListener( this, MainActivity.sensorClasses[sensor] );
		mSensorManager.registerListener(listener, s, time * 1000);
		
		while( ListenerService.__instance != null && ListenerService.__instance.isRunning() ) {
			try {
				Thread.sleep(10000);
			} catch( InterruptedException e ) {
				mSensorManager.unregisterListener(listener);
				
			}
			
			SaverTask saver = new SaverTask();
			saver.executeOnExecutor( ListenerService.__instance.saverPool, sensorData );
		}
		
		mSensorManager.unregisterListener(listener);
		return null;
	}
}
