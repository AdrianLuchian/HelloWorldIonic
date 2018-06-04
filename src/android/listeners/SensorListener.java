package cordova.plugin.helloWorld.listeners;

import cordova.plugin.helloWorld.database.interfaces.AxisData;
import cordova.plugin.helloWorld.database.interfaces.ValueData;
import cordova.plugin.helloWorld.tasks.SensorListenerTask;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import io.realm.RealmObject;

public class SensorListener implements SensorEventListener {

	SensorListenerTask task;
	Class<?> table;
	
	private long initialTime;
	private long initialTimestamp;
	
	public SensorListener( SensorListenerTask task, Class<?> table ) {
		this.task = task;
		this.table = table;
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		RealmObject data = (RealmObject) createDataObject( table, arg0 );
		if( data != null )
			task.addData(data);
	}
	
	private Object createDataObject( Class<?> clazz, SensorEvent sensorEvent ) {
		long timestamp;
		timestamp = getTimestamp( sensorEvent.timestamp );

		if( clazz.getInterfaces()[0] == AxisData.class ) {
			AxisData obj;
			try {
				obj = (AxisData) clazz.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			obj.setTimestamp( timestamp );
			obj.setX( sensorEvent.values[0] );
			obj.setY( sensorEvent.values[1] );
			obj.setZ( sensorEvent.values[2] );
			return obj;
		} else if ( clazz.getInterfaces()[0] == ValueData.class ) {
			ValueData obj;
			try {
				obj = (ValueData) clazz.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			obj.setTimestamp( timestamp );
			obj.setValue( sensorEvent.values[0] );
			return obj;
		}
		return null;
	}

	private long getTimestamp(long timestamp) {
		if( initialTimestamp != 0 ) {
			return initialTime + ( timestamp/100000L - initialTimestamp );
		} else {
			initialTime = System.currentTimeMillis();
			initialTimestamp = timestamp/100000L;
			return initialTime;
		}
	}
}
