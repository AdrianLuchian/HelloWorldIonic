package cordova.plugin.helloWorld.database.interfaces;

public interface ValueData {
	public void setTimestamp( long timestamp );
	public void setValue( float value );
	public long getTimestamp();
	public float getValue();
}
