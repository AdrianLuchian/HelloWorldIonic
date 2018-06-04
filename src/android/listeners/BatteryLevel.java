package cordova.plugin.helloWorld.listeners;

import cordova.plugin.helloWorld.database.BatteryData;
import cordova.plugin.helloWorld.tasks.ListenerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryLevel extends BroadcastReceiver {
	
	ListenerTask task;
	
	public BatteryLevel( ListenerTask task ) {
		this.task = task;
	}
	
    @Override
    public void onReceive(Context context, Intent intent) {
    	int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        
        BatteryData s = new BatteryData();
        s.setTimestamp( System.currentTimeMillis() );
        s.setValue( (float)level/scale*100 );
        task.addData(s);
    }
}
