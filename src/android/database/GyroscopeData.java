package cordova.plugin.helloWorld.database;
import cordova.plugin.helloWorld.database.interfaces.AxisData;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass 
public class GyroscopeData extends RealmObject implements AxisData {

	private long timestamp;
	private float x;
	private float y;
	private float z;
	
	public void setTimestamp( long timestamp ) {
		this.timestamp = timestamp;
	}
	public void setX( float x ) {
		this.x = x;
	}
	public void setY( float y ) {
		this.y = y;
	}
	public void setZ( float z ) {
		this.z = z;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getZ() {
		return z;
	}
}
