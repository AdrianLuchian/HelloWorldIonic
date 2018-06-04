package cordova.plugin.helloWorld.disertatie;

import cordova.plugin.helloWorld.database.ContextData;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import io.realm.Realm;

public abstract class EventActivity extends Activity {
	
	abstract String getEventName( int id );
	
	public void saveEvent( View v ) {
		String text = getEventName( v.getId() );
		
		saveEventInDB( text );
		
		if( text.endsWith("-stop" ) ) {
			SharedPreferences settings = getSharedPreferences("SensorSettings", MODE_PRIVATE);
			final Editor editor = settings.edit();
			
			editor.putString("current-state", "" );
            editor.commit();
            
            finish();
		}
	}
	
	protected void saveEventInDB( String event ) {
		Realm realm = Realm.getInstance(this);
		ContextData c = new ContextData();
		c.setTimestamp(System.currentTimeMillis());
		c.setValue(event);
		realm.beginTransaction();
		realm.copyToRealm(c);
		realm.commitTransaction();
	}
}
