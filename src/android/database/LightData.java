package cordova.plugin.helloWorld.database;
import cordova.plugin.helloWorld.database.interfaces.ValueData;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass 
public class LightData extends RealmObject implements ValueData {

	private long timestamp;
	private float value;
	
	public void setTimestamp( long timestamp ) {
		this.timestamp = timestamp;
	}
	public void setValue( float value ) {
		this.value = value;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public float getValue() {
		return value;
	}
}
