package cordova.plugin.helloWorld.disertatie;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import io.ionic.starter.R;

public class SettingsActivity extends Activity {
	public static SharedPreferences settings;
	public Hashtable<String,cordova.plugin.helloWorld.helpers.Setting> settingValues = new Hashtable<String, cordova.plugin.helloWorld.helpers.Setting>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setTitle("Settings");

		final LinearLayout l;
		l = (LinearLayout) findViewById(R.id.settings_linear_layout);

		SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		settings = getSharedPreferences("SensorSettings", MODE_PRIVATE);

		/* Set default values for sensors if they are empty */
		String[] values = {"100","100","1000","100","100","1000","1000"};
		for(int i = 0; i< cordova.plugin.helloWorld.disertatie.MainActivity.sensors.length; i++) {
			Sensor s = mSensorManager.getDefaultSensor(cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i]);
			if( s == null )
				continue;
			String val = settings.getString("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i], "");
			if( val == null || val.equals("") ) {
				Editor editor = settings.edit();
			    editor.putString("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i], values[i]);
			    editor.commit();
			}
		}
		String val = settings.getString("foregound-application", "");
		if( val == null || val.equals("") ) {
			Editor editor = settings.edit();
		    editor.putString("foregound-application", "2000");
		    editor.commit();
		}
		val = settings.getString("location", "");
		if( val == null || val.equals("") ) {
			Editor editor = settings.edit();
		    editor.putString("location", "5000");
		    editor.commit();
		}
		val = settings.getString("sound-level", "");
		if( val == null || val.equals("") ) {
			Editor editor = settings.edit();
		    editor.putString("sound-level", "1000");
		    editor.commit();
		}

		/* Create setting field for the name */
		TextView nameSettingLabel = new TextView(this);
		nameSettingLabel.setTextSize(18);
		nameSettingLabel.setTypeface(null,Typeface.BOLD);
		nameSettingLabel.setText("Name");
		final EditText nameSettingField = new EditText(this);
		nameSettingField.setTextSize(16);
		String value = settings.getString("name", "");
		nameSettingField.setText(value);
		l.addView(nameSettingLabel);
		l.addView(nameSettingField);

		/* Create sensor labels*/
		TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
		TableLayout tableLayout = new TableLayout(this);
		tableLayout.setLayoutParams(tableParams);
		TableRow tableRow = new TableRow(this);
		tableRow.setLayoutParams(tableParams);

		TextView label = new TextView(this);
		label.setTextSize(14);
		label.setTypeface(null,Typeface.BOLD);
		label.setText("Sampling rate(ms)");
		label.setLayoutParams(rowParams);
		tableRow.addView(label);
		label = new TextView(this);
		label.setTextSize(14);
		label.setTypeface(null,Typeface.BOLD);
		label.setText("Enable");
		label.setLayoutParams(rowParams);
		tableRow.addView(label);
		label = new TextView(this);
		label.setTextSize(14);
		label.setTypeface(null,Typeface.BOLD);
		label.setText("Display");
		label.setLayoutParams(rowParams);
		tableRow.addView(label);
		tableLayout.addView(tableRow);

		/* Create setting fields for the sensors */
		for(int i = 0; i< cordova.plugin.helloWorld.disertatie.MainActivity.sensors.length; i++) {
			Sensor s = mSensorManager.getDefaultSensor(cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i]);
			if( s == null )
				continue;
			createSettingField( s.getName(), "sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i], tableLayout, tableParams, i + 1 );
		}

		createSettingField( "Foreground application", "foregound-application", tableLayout, tableParams, 20 );
		createSettingField( "Location",  "location", tableLayout, tableParams, 21 );
		createSettingField( "Sound level (over 200)", "sound-level", tableLayout, tableParams, 22 );

		l.addView(tableLayout);

		/* Create the save button */
		Button saveButton = new Button(this);
		saveButton.setText("Save options");
		saveButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/* Save the name setting */
				CharSequence s = nameSettingField.getText();
				String value = s.toString();
				Editor editor = settings.edit();
			    editor.putString("name", value);


			    /* Save the sensor settings */
			   for(int i = 0; i< cordova.plugin.helloWorld.disertatie.MainActivity.sensors.length; i++) {
			    	cordova.plugin.helloWorld.helpers.Setting setting = settingValues.get("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i]);
			    	if( setting != null ) {
				    	editor.putString("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i], setting.getSamplingRate(l) );
				    	editor.putString("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i] + "-enabled", setting.isEnabled(l) );
				    	editor.putString("sensor-" + cordova.plugin.helloWorld.disertatie.MainActivity.sensors[i] + "-displayed", setting.isDisplayed(l) );
			    	}
			    }

			    cordova.plugin.helloWorld.helpers.Setting setting = settingValues.get("foregound-application");
		    	editor.putString("foregound-application", setting.getSamplingRate(l));
		    	editor.putString("foregound-application-enabled", setting.isEnabled(l) );
		    	editor.putString("foregound-application-displayed", setting.isDisplayed(l) );

		    	setting = settingValues.get("location");
		    	editor.putString("location", setting.getSamplingRate(l));
		    	editor.putString("location-enabled", setting.isEnabled(l) );
		    	editor.putString("location-displayed", setting.isDisplayed(l) );

		    	setting = settingValues.get("sound-level");
		    	editor.putString("sound-level", setting.getSamplingRate(l));
		    	editor.putString("sound-level-enabled", setting.isEnabled(l) );
		    	editor.putString("sound-level-displayed", setting.isDisplayed(l) );

		    	editor.commit();

			    Toast.makeText(getApplicationContext(), "Options saved", Toast.LENGTH_LONG).show();
			}
		});
		l.addView(saveButton);
	}

	void createSettingField( String name, final String settingName,TableLayout tableLayout, TableLayout.LayoutParams tableParams, int index ) {
		TableRow tableRow = new TableRow(this);
		tableRow.setLayoutParams(tableParams);
		TextView label = new TextView(this);
		label.setText(name);
		tableRow.addView(label);
		tableLayout.addView(tableRow);

		tableRow = new TableRow(this);
		tableRow.setLayoutParams(tableParams);

		final EditText settingField = new EditText(this);
		settingField.setTextSize(18);
		settingField.setId(index * 100 + 1);
		String value = settings.getString(settingName, "");
		settingField.setText( value );
		tableRow.addView(settingField);

		final CheckBox enable = new CheckBox(this);
		enable.setId(index * 100 + 2);
		value = settings.getString(settingName + "-enabled", "false");
		if( value.equals("true") ) {
			enable.setChecked(true);
		}
		tableRow.addView(enable);

		final CheckBox display = new CheckBox(this);
		display.setId(index * 100 + 3);
		value = settings.getString(settingName + "-displayed", "false");
		if( value.equals("true") ) {
			display.setChecked(true);
		}

		cordova.plugin.helloWorld.helpers.Setting setting = new cordova.plugin.helloWorld.helpers.Setting( settingName, index * 100 + 1, index * 100 + 2, index * 100 + 3 );
		settingValues.put(settingName, setting);

		tableRow.addView(display);
		tableLayout.addView(tableRow);
	}
}
