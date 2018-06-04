package cordova.plugin.helloWorld.database.interfaces;

public interface AxisData {
	public void setTimestamp( long timestamp );
	public void setX( float x );
	public void setY( float y );
	public void setZ( float z );
	public long getTimestamp();
	public float getX();
	public float getY();
	public float getZ();
}
