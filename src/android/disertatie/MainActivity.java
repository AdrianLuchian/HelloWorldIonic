package cordova.plugin.helloWorld.disertatie;

import cordova.plugin.helloWorld.database.AccelerometerData;
import cordova.plugin.helloWorld.database.BatteryData;
import cordova.plugin.helloWorld.database.ContextData;
import cordova.plugin.helloWorld.database.ForegroundApplicationData;
import cordova.plugin.helloWorld.database.GyroscopeData;
import cordova.plugin.helloWorld.database.LightData;
import cordova.plugin.helloWorld.database.LinearAccelerationData;
import cordova.plugin.helloWorld.database.LocationSensorData;
import cordova.plugin.helloWorld.database.MagneticFieldData;
import cordova.plugin.helloWorld.database.PressureData;
import cordova.plugin.helloWorld.database.ProximityData;
import cordova.plugin.helloWorld.database.SoundData;
import cordova.plugin.helloWorld.tasks.SenderTask;
import io.ionic.starter.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import io.realm.Realm;

public class MainActivity extends Activity {
	public static SharedPreferences settings;
	public static final int[] sensors = {
			Sensor.TYPE_ACCELEROMETER,
			Sensor.TYPE_GYROSCOPE,
			Sensor.TYPE_LIGHT,
			Sensor.TYPE_LINEAR_ACCELERATION,
			Sensor.TYPE_MAGNETIC_FIELD,
			Sensor.TYPE_PRESSURE,
			Sensor.TYPE_PROXIMITY
	};
	public static final Class<?>[] sensorClasses = {
		AccelerometerData.class,
		GyroscopeData.class,
		LightData.class,
		LinearAccelerationData.class,
		MagneticFieldData.class,
		PressureData.class,
		ProximityData.class,
		BatteryData.class,
		ForegroundApplicationData.class,
		LocationSensorData.class,
		SoundData.class,
		ContextData.class
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		settings = getSharedPreferences("SensorSettings", MODE_PRIVATE);

		LinearLayout l;
		l = (LinearLayout) findViewById(R.id.linear_layout);

		final Button startButton = new Button(this);
		startButton.setText("Start");
		final Button stopButton = new Button(this);
        stopButton.setText("Stop");
        final Button eventsButton = new Button(this);
        eventsButton.setText("Events");
        final Button settingsButton = new Button(this);
		settingsButton.setText("Settings");
        final Button sendButton = new Button(this);
		sendButton.setText("Send data");
        l.addView(startButton);
        l.addView(eventsButton);
        l.addView(stopButton);
        l.addView(settingsButton);
        l.addView(sendButton);
        eventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent;
            	String sett = settings.getString("current-state", "");
            	intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.EventsActivity.class);
            	if( sett.equals("car") )
            		intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.CarEventsActivity.class );
            	if( sett.equals("transport") )
        			intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.TransportEventsActivity.class);
        		if( sett.equals("bicycle") )
        			intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.BicycleEventsActivity.class);
        		if( sett.equals("pedestrian") )
        			intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.PedestrianEventsActivity.class);

                startActivity(intent);
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startService(new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.ListenerService.class));
                stopButton.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.VISIBLE);
    			startButton.setVisibility(View.GONE);
    			//sendButton.setVisibility(View.INVISIBLE);

    			Intent intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.EventsActivity.class);
                startActivity(intent);
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                stopService(new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.ListenerService.class));
                startButton.setVisibility(View.VISIBLE);
                eventsButton.setVisibility(View.GONE);
    			stopButton.setVisibility(View.GONE);
    			//sendButton.setVisibility(View.VISIBLE);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(MainActivity.this, cordova.plugin.helloWorld.disertatie.SettingsActivity.class);
                startActivity(intent);
            }
        });



        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            	NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            	if( activeNetworkInfo != null && activeNetworkInfo.isConnected() ) {
            		SenderTask sender = new SenderTask(MainActivity.this);
            		sender.executeOnExecutor( AsyncTask.THREAD_POOL_EXECUTOR, MainActivity.this );
            	} else {
            		AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                	alertDialog.setTitle("No internet connection");
                	alertDialog.setMessage("Please activate your internet connecton to send the data to the server.");
                	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
        		    new DialogInterface.OnClickListener() {
        		        public void onClick(DialogInterface dialog, int which) {
        		            dialog.dismiss();
        		        }
        		    });
            		alertDialog.show();
            	}
            }
        });
		if( ! this.isMyServiceRunning(cordova.plugin.helloWorld.disertatie.ListenerService.class) ) {
			startButton.setVisibility(View.VISIBLE);
			eventsButton.setVisibility(View.GONE);
			stopButton.setVisibility(View.GONE);
			//sendButton.setVisibility(View.INVISIBLE);
		} else {
			stopButton.setVisibility(View.VISIBLE);
			eventsButton.setVisibility(View.VISIBLE);
			startButton.setVisibility(View.GONE);
			//sendButton.setVisibility(View.VISIBLE);
		}

		createContextField( "Current context", 103, "context", l );
	}

	void createContextField( String name, int textId, final String settingName, LinearLayout l ) {
		final TextView text = new TextView(this);
		text.setEms(10);
		text.setText( name );
		text.setId( textId );
		final EditText field = new EditText(this);
		field.setEms(10);


		Button button = new Button(this);
		button.setText("Send");

		button.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CharSequence s = field.getText();
				String value = s.toString();

				Realm realm = Realm.getInstance(MainActivity.this);
				ContextData c = new ContextData();
				c.setTimestamp(System.currentTimeMillis());
				c.setValue(value);
				realm.beginTransaction();
				realm.copyToRealm(c);
				realm.commitTransaction();

				Toast toast = Toast.makeText( getApplicationContext(), "Context stored", Toast.LENGTH_SHORT);
				toast.show();
			}
		});

		LinearLayout ls = new LinearLayout(this);
		l.addView(text);
		ls.addView(field);
		ls.addView(button);

		l.addView(ls);
	}

	private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (serviceClass.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
}
