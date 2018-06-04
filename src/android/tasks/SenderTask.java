package cordova.plugin.helloWorld.tasks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cordova.plugin.helloWorld.database.interfaces.AxisData;
import cordova.plugin.helloWorld.database.interfaces.LocationData;
import cordova.plugin.helloWorld.database.interfaces.StringValueData;
import cordova.plugin.helloWorld.database.interfaces.ValueData;
import cordova.plugin.helloWorld.disertatie.MainActivity;
import cordova.plugin.helloWorld.helpers.HttpRequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings.Secure;
import io.realm.Realm;
import io.realm.RealmObject;

public class SenderTask extends AsyncTask<Context, Void, Boolean> {
	private ProgressDialog dialog;
	
	static final int AXISDATA = 1;
	static final int VALUEDATA = 2;
	static final int STRINGVALUEDATA = 3;
	static final int LOCATIONDATA = 4;
	static final String[] serverTables = {
			"accelerometer_data",
			"gyroscope_data",
			"light_level_data",
			"linear_acceleration_data",
			"magnetic_field_data",
			"pressure_data",
			"proximity_data",
		    "battery_level_data",
		    "foreground_application_data",
		    "location_data",
		    "sound_level_data",
		    "contexts"
	};
	Context context;
	public static final int batchSize = 10000;
	boolean singleBatch;
	Class<?> table;
	
	public SenderTask(MainActivity activity ) {
		super();
		singleBatch = false;
        dialog = new ProgressDialog(activity);
    }
	
	public SenderTask( Class<?> table ) {
		super();
        singleBatch = true;
        this.table = table;
    }
	
	protected void onPreExecute() {
		if( dialog != null ) {
			dialog.setMessage("Sending data to the server.");
			dialog.show();
		}
    }
	
	@Override
	protected Boolean doInBackground(final Context... params) {
		Realm realm = Realm.getInstance( params[0] );
		SharedPreferences settings = params[0].getSharedPreferences( "SensorSettings", Context.MODE_PRIVATE );
		String data; 
		String androidID = Secure.getString( params[0].getContentResolver(), Secure.ANDROID_ID);
		
		
		int i,j,k;
		
		Class<?>[] sensorClasses;
		if( singleBatch ) {
			sensorClasses =  new Class[1];
			sensorClasses[0] = table;
		} else {
			sensorClasses = MainActivity.sensorClasses;
		}
		
		for( k = 0; k < sensorClasses.length; k++ ) {
			int type = getType(sensorClasses[k] );
			
			@SuppressWarnings("unchecked")
			Class<RealmObject> clazz = (Class<RealmObject>) sensorClasses[k];
			List<RealmObject> sensorData = realm.where( clazz ).findAll();
			
			while ( sensorData.size() > 0 ) {
				JSONArray dataArray = new JSONArray();
				RealmObject dataToBeSent[] = new RealmObject[batchSize];
	
				for( i = 0; i < batchSize && i < sensorData.size(); i++ ) {
					
					switch( type ) {
						case AXISDATA:
							AxisData axisData = (AxisData) sensorData.get(i);
							try {
								dataArray.put( new JSONObject().put( "timestamp", axisData.getTimestamp() )
										.put("x", axisData.getX()).put("y", axisData.getY()).put("z", axisData.getZ()) );
							} catch (JSONException e) {
								e.printStackTrace();
							}
							dataToBeSent[i] = (RealmObject) axisData;
							break;
						case VALUEDATA:
							ValueData valueData = (ValueData) sensorData.get(i);
							try {
								dataArray.put( new JSONObject().put( "timestamp", valueData.getTimestamp() )
										.put("value", valueData.getValue()) );
							} catch (JSONException e) {
								e.printStackTrace();
							}
							dataToBeSent[i] = (RealmObject) valueData;
							break;
						case STRINGVALUEDATA:
							StringValueData stringValueData = (StringValueData) sensorData.get(i);
							try {
								dataArray.put( new JSONObject().put( "timestamp", stringValueData.getTimestamp() )
										.put("value", stringValueData.getValue()) );
							} catch (JSONException e) {
								e.printStackTrace();
							}
							dataToBeSent[i] = (RealmObject) stringValueData;
							break;
						case LOCATIONDATA:
							LocationData locationData = (LocationData) sensorData.get(i);
							try {
								dataArray.put( new JSONObject().put( "timestamp", locationData.getTimestamp() )
										.put("latitude", locationData.getLatitude()).put("longitude", locationData.getLongitude())
										.put("accuracy", locationData.getAccuracy()).put("source", locationData.getSource()) );
							} catch (JSONException e) {
								e.printStackTrace();
							}
							dataToBeSent[i] = (RealmObject) locationData;
							break;
					}	
				}
				
				String name = settings.getString( "name", "");
				
				data = "table=" + serverTables[k] + "&device_id=" + androidID + "&name=" + name + "&data=" + dataArray.toString() + "";
				
				if( sendData(data) ) {
					realm.beginTransaction();
					for(j=0;j<dataToBeSent.length;j++) {
						if(dataToBeSent[j] != null )
							dataToBeSent[j].removeFromRealm();
					}
					realm.commitTransaction();
					
				} else {
					break;
				}
				
				if( singleBatch ) 
					break;
				
			}
			
		}
		realm.close();
		context = params[0];
		return true;
	}
	
	protected void onPostExecute( Boolean ignore ) {
		if( ! singleBatch ) {
	        if ( dialog != null && dialog.isShowing()) {
	            dialog.dismiss();
	        }
	        if( context != null ) {
	        	AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
	        	alertDialog.setTitle("Done");
	        	alertDialog.setMessage("Everything has been sent to the server.");
	        	alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			            dialog.dismiss();
			        }
			    });
	    		alertDialog.show();
			}
		}
    }

	protected int getType( Class<?> clazz ) {
		if( clazz.getInterfaces()[0] == AxisData.class )
			return AXISDATA;
		else if( clazz.getInterfaces()[0] == ValueData.class )
			return VALUEDATA;
		else if( clazz.getInterfaces()[0] == StringValueData.class )
			return STRINGVALUEDATA;
		else if( clazz.getInterfaces()[0] == LocationData.class )
			return LOCATIONDATA;
		else 
			return 0;
	}
	
	protected boolean sendData( String dataString ) {
		String response = "";
        try {
            response = new HttpRequest("http://disertatie.dreamproduction.com/").preparePost()
            		.withData(dataString).sendAndReadString();
            if( response.equals("1") )
            	return true;
           
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}
}