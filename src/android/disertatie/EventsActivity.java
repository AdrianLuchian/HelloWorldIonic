package cordova.plugin.helloWorld.disertatie;

import cordova.plugin.helloWorld.database.ContextData;
import io.ionic.starter.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import io.realm.Realm;

public class EventsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);

		SharedPreferences settings = getSharedPreferences("SensorSettings", MODE_PRIVATE);
		final Editor editor = settings.edit();

		ImageButton carEventsButton = (ImageButton) findViewById(R.id.buttonCar);
		carEventsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	        	editor.putString("current-state", "car" );
	            editor.commit();

	            EventsActivity.this.saveEventInDB( "start-car" );

	        	Intent intent = new Intent(EventsActivity.this, cordova.plugin.helloWorld.disertatie.CarEventsActivity.class);
	            startActivity(intent);


	        }
	    });

		ImageButton transportEventsButton = (ImageButton) findViewById(R.id.buttonTransport);
		transportEventsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	        	editor.putString("current-state", "transport" );
	            editor.commit();

	            EventsActivity.this.saveEventInDB( "start-transport" );

	        	Intent intent = new Intent(EventsActivity.this, cordova.plugin.helloWorld.disertatie.TransportEventsActivity.class);
	            startActivity(intent);
	        }
	    });

		ImageButton bicycleEventsButton = (ImageButton) findViewById(R.id.buttonBicycle);
		bicycleEventsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	        	editor.putString("current-state", "bicycle" );
	            editor.commit();

	            EventsActivity.this.saveEventInDB( "start-bicycle" );

	        	Intent intent = new Intent(EventsActivity.this, cordova.plugin.helloWorld.disertatie.BicycleEventsActivity.class);
	            startActivity(intent);
	        }
	    });

		ImageButton pedestrianEventsButton = (ImageButton) findViewById(R.id.buttonPedestrian);
		pedestrianEventsButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	        	editor.putString("current-state", "pedestrian" );
	            editor.commit();

	            EventsActivity.this.saveEventInDB( "start-pedestrian" );

	        	Intent intent = new Intent(EventsActivity.this, cordova.plugin.helloWorld.disertatie.PedestrianEventsActivity.class);
	            startActivity(intent);
	        }
	    });
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
