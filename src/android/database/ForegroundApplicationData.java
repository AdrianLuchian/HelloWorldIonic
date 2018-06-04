package cordova.plugin.helloWorld.database;
import cordova.plugin.helloWorld.database.interfaces.StringValueData;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class ForegroundApplicationData extends RealmObject implements StringValueData {

	private long timestamp;
	private String value;
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
