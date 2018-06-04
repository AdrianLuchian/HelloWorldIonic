package cordova.plugin.helloWorld.listeners;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

public class WifiListener extends BroadcastReceiver {
	public void onReceive(Context c, Intent intent) {
		StringBuilder sb = new StringBuilder();
		final WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
         List<ScanResult> scanList = wifiManager.getScanResults();
         sb.append("\n  Number Of Wifi connections :" + " " +scanList.size()+"\n\n");    
         for(int i = 0; i < scanList.size(); i++){
             sb.append(new Integer(i+1).toString() + ". ");
             sb.append((scanList.get(i)).toString());
             sb.append("\n\n");
         }
         System.out.println(sb);
	}
}
