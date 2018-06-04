package cordova.plugin.helloWorld.tasks;

import cordova.plugin.helloWorld.database.ForegroundApplicationData;
import cordova.plugin.helloWorld.disertatie.ListenerService;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

public class ForegroundAppWatcher extends ListenerTask {

	private int time;
	private void getForegroudApp() {
		ActivityManager am = (ActivityManager) ListenerService.__instance.getSystemService(ListenerService.ACTIVITY_SERVICE);
		// The first in the list of RunningTasks is always the foreground task.
		@SuppressWarnings("deprecation")
		RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
		String foregroundTaskPackageName = foregroundTaskInfo.topActivity.getPackageName();
		PackageManager pm = ListenerService.__instance.getPackageManager();
		PackageInfo foregroundAppPackageInfo;
		try {
			foregroundAppPackageInfo = pm.getPackageInfo(foregroundTaskPackageName, 0);
			String foregroundTaskAppName = foregroundAppPackageInfo.applicationInfo.loadLabel(pm).toString();
			//System.out.println(foregroundTaskAppName);
			ForegroundApplicationData s = new ForegroundApplicationData();
			s.setTimestamp( System.currentTimeMillis() );
			s.setValue( foregroundTaskAppName );
			this.addData(s);
		} catch (NameNotFoundException e) {e.printStackTrace();}
	}
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Integer... params) {
		
		time = params[0];
		int elapsed_time = 0;
		long previous_timestamp = System.currentTimeMillis();
		
		do {
			try {
				Thread.sleep(time);
				getForegroudApp();
				elapsed_time += System.currentTimeMillis() - previous_timestamp;
				previous_timestamp = System.currentTimeMillis();
			} catch( Exception e) {}
			
			if( elapsed_time > 10000 ) {
				SaverTask saver = new SaverTask();
				saver.executeOnExecutor( ListenerService.__instance.saverPool, sensorData );
				elapsed_time = 0;
			}
			
		} while( ListenerService.__instance != null && ListenerService.__instance.isRunning() );
		
		return null;
	}
}
