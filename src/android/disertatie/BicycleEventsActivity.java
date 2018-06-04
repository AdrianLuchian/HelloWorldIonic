package cordova.plugin.helloWorld.disertatie;

import android.app.Activity;
import android.os.Bundle;
import io.ionic.starter.R;

public class BicycleEventsActivity extends EventActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bicycle);
	}
	
	public String getEventName( int buttonId ) {
		switch( buttonId ) {
			case R.id.buttonBicycleTrafficLight:
				return "bicycle-traffic-light";
			case R.id.buttonBicycleStopSign:
				return "bicycle-stop-sign";
			case R.id.buttonBicycleYeld:
				return "bicycle-yeld";
			case R.id.buttonBicycleSpeedBumper:
				return "bicycle-speed-bumper";
			case R.id.buttonBicyclePedestrianCrossing:
				return "bicycle-pedestrian-crossing";
			case R.id.buttonBicycleRailCrossing:
				return "bicycle-rail-crossing";
			case R.id.buttonBicycleObstacle:
				return "bicycle-obstacle";
			case R.id.buttonBicycleExit:
				return "bicycle-stop";
		}
		return null;
	}
}
