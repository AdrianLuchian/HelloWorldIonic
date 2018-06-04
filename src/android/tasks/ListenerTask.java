package cordova.plugin.helloWorld.tasks;

import java.util.ArrayList;

import android.os.AsyncTask;
import io.realm.RealmObject;

abstract public class ListenerTask extends AsyncTask<Integer, Void, Void> {

	protected ArrayList<RealmObject> sensorData = new ArrayList<RealmObject>();
	
	@Override
	protected abstract Void doInBackground(Integer... params);
	
	public void addData ( RealmObject data ) {
		synchronized (sensorData) {
			sensorData.add(data);
		}
	}
}
