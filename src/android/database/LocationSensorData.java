package cordova.plugin.helloWorld.database;
import cordova.plugin.helloWorld.database.interfaces.LocationData;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class LocationSensorData extends RealmObject implements LocationData {

	private long timestamp;
	private double latitude;
	private double longitude;
	private float accuracy;
	private String source;
	
	public double getLatitude() {
		return latitude;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public float getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
