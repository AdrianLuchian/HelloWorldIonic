package cordova.plugin.helloWorld.tasks;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import io.realm.Realm;
import io.realm.RealmObject;

public class SaverTask extends AsyncTask<ArrayList<RealmObject>, Void, Void> {

	@Override
	protected Void doInBackground(ArrayList<RealmObject>... params) {
		ArrayList<RealmObject> data = params[0];

		if( data.size() == 0 )
			return null;

		Class<?> clazz = data.get(0).getClass();

		Realm realm = Realm.getInstance(cordova.plugin.helloWorld.disertatie.ListenerService.__instance.getBaseContext() );
		realm.beginTransaction();
		synchronized (data) {
			realm.copyToRealm( data );
			data.clear();
		}
		realm.commitTransaction();
		realm.close();

		return null;
	}

}
