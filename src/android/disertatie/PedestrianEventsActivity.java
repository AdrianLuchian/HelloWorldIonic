package cordova.plugin.helloWorld.disertatie;

import android.app.Activity;
import android.os.Bundle;
import io.ionic.starter.R;

public class PedestrianEventsActivity extends EventActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pedestrian);
	}
	
	public String getEventName( int buttonId ) {
		switch( buttonId ) {
			case R.id.buttonPedestrianTrafficLight:
				return "pedestrian-traffic-light";
			case R.id.buttonPedestrianPedestrianCrossing:
				return "pedestrian-pedestrian-crossing";
			case R.id.buttonPedestrianExit:
				return "pedestrian-stop";
		}
		return null;
	}
}
