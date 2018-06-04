package cordova.plugin.helloWorld.disertatie;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cordova.plugin.helloWorld.tasks.BatteryWatcherTask;
import cordova.plugin.helloWorld.tasks.ForegroundAppWatcher;
import cordova.plugin.helloWorld.tasks.ListenerTask;
import cordova.plugin.helloWorld.tasks.LocationListenerTask;
import cordova.plugin.helloWorld.tasks.SensorListenerTask;
import cordova.plugin.helloWorld.tasks.SoundWatcherTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class ListenerService extends Service {
    public Handler mHandler=new Handler();
    SensorManager mSensorManager;
    SharedPreferences settings;
    
    ArrayList<AsyncTask<?,?,?>> tasks;
    
    volatile boolean run;
    
    public static ListenerService __instance;
    public ExecutorService listenerPool;
    public ExecutorService saverPool;
    
    @Override
    public void onCreate() {
    	settings = getSharedPreferences("SensorSettings", MODE_PRIVATE);
    	
    	__instance = this;
        Toast.makeText(this, "Service has been created.", Toast.LENGTH_LONG).show(); //is shown        
        run = true;
        
        tasks = new ArrayList<AsyncTask<?,?,?>>();

        listenerPool = Executors.newFixedThreadPool(11);
        saverPool = Executors.newFixedThreadPool(11);
        
		initSensorsListener();
		initLocationListener();
		initForegroundAppListener();
		initBatteryListener();
		initSoundLevelListener();
        
    }

    @Override
    public void onDestroy() {
    	 
    	run = false;
    	
    	for( int i = 0; i < tasks.size(); i++ ) {
    		tasks.get(i).cancel(true);
    	}
		
		mHandler.post(new Runnable(){
		    public void run(){
		        Toast.makeText(ListenerService.this, "Service has been destroyed. Wait for 10 seconds before sending data to the server.", Toast.LENGTH_LONG).show();
		    }
		 });
    	 
    }

    @Override
    public void onStart(Intent intent, int startid) {


    }

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	void initSensorsListener() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		for( int i = 0; i < MainActivity.sensors.length; i++ ) {
			
			String setting = settings.getString( 
					"sensor-" + MainActivity.sensors[i], "");
			String enabled = settings.getString(
					"sensor-" + MainActivity.sensors[i] + "-enabled", "false");
		
			Sensor s = mSensorManager.getDefaultSensor(MainActivity.sensors[i]);
			
			if( setting != null  && s != null && ! setting.equals("") && enabled.equals("true") ) {
				int time = Integer.parseInt(setting);
				if( time != 0 ) {
					ListenerTask task = new SensorListenerTask();
					task.executeOnExecutor( listenerPool, i, time );
					tasks.add(task);
				}
			}
				
		}
	}
	
	void initLocationListener() {
		String setting = settings.getString( "location", "" );
		String enabled = settings.getString( "location-enabled", "false");
		if( setting != null && ! setting.equals( "" ) && enabled.equals("true") ) {
			int time = Integer.parseInt(setting);
			if( time != 0 ) {
				ListenerTask task;
				
				task = new LocationListenerTask();
				task.executeOnExecutor( listenerPool, 0, time );
				tasks.add(task);
				
				task = new LocationListenerTask();
				task.executeOnExecutor( listenerPool, 1, time );
				tasks.add(task);
			}
		}
	}
	
	void initForegroundAppListener() {
		String setting = settings.getString( "foregound-application", "" );
		String enabled = settings.getString( "foregound-application-enabled", "false");
		
		if( setting != null && ! setting.equals( "" ) && enabled.equals("true") ) {
			int time = Integer.parseInt(setting);
			if( time > 0 ) {
				ListenerTask task = new ForegroundAppWatcher();
				task.executeOnExecutor( listenerPool, time );
				tasks.add(task);
			}
		}
	}
	
	void initSoundLevelListener() {
		String setting = settings.getString( "sound-level", "" );
		String enabled = settings.getString( "sound-level-enabled", "false");
		if( setting != null && ! setting.equals( "" ) && enabled.equals("true") ) {
			int time = Integer.parseInt(setting);
			if( time > 0 ) {
				ListenerTask task = new SoundWatcherTask();
				task.executeOnExecutor( listenerPool, time );
				tasks.add(task);
			}
		}
	}
	
	void initBatteryListener() {
		BatteryWatcherTask batteryTask = new BatteryWatcherTask();
		batteryTask.executeOnExecutor( listenerPool, 0 );
		tasks.add(batteryTask);
	}
	
//	void initNetworkListener() {
//		IntentFilter filter = new IntentFilter();
//		wifi = new WifiListener();
//		registerReceiver( wifi, filter);
//		WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//		wifiManager.startScan();
//	}
//	
//	void stopNetworkListener() {
//		unregisterReceiver(wifi);
//	}
	
	public boolean isRunning() {
		return run;
	}
}