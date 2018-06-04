package cordova.plugin.helloWorld.database.interfaces;

public interface LocationData {
	public double getLatitude();
	public void setLatitude(double latitude);
	public long getTimestamp();
	public void setTimestamp(long timestamp);
	public double getLongitude();
	public void setLongitude(double longitude);
	public float getAccuracy();
	public void setAccuracy(float accuracy);
	public String getSource();
	public void setSource(String source);
}
