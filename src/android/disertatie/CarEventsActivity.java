package cordova.plugin.helloWorld.disertatie;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import io.ionic.starter.R;

public class CarEventsActivity extends EventActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_events);

	}
	
	public String getEventName( int buttonId ) {
		switch( buttonId ) {
			case R.id.buttonCarTrafficLight:
				return "car-traffic-light";
			case R.id.buttonCarStopSign:
				return "car-stop-sign";
			case R.id.buttonCarYeld:
				return "car-yeld";
			case R.id.buttonCarSpeedBumper:
				return "car-speed-bumper";
			case R.id.buttonCarPedestrianCrossing:
				return "car-pedestrian-crossing";
			case R.id.buttonCarRailCrossing:
				return "car-rail-crossing";
			case R.id.buttonCarObstacle:
				return "car-obstacle";
			case R.id.buttonCarPark:
				return "car-park";
			case R.id.buttonCarExit:
				return "car-stop";
		}
		return null;
	}
}
