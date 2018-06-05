package cordova.plugin.helloWorld.disertatie;

import android.app.Activity;
import android.os.Bundle;
import io.ionic.starter.R;

public class TransportEventsActivity extends cordova.plugin.helloWorld.disertatie.EventActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transport_events);
	}

	public String getEventName( int buttonId ) {
		switch( buttonId ) {
			case R.id.buttonTransportTrafficLight:
				return "transport-traffic-light";
			case R.id.buttonTransportStopSign:
				return "transport-stop-sign";
			case R.id.buttonTransportYeld:
				return "transport-yeld";
			case R.id.buttonTransportSpeedBumper:
				return "transport-speed-bumper";
			case R.id.buttonTransportPedestrianCrossing:
				return "transport-pedestrian-crossing";
			case R.id.buttonTransportRailCrossing:
				return "transport-rail-crossing";
			case R.id.buttonTransportObstacle:
				return "transport-obstacle";
			case R.id.buttonTransportStation:
				return "transport-station";
			case R.id.buttonTransportExit:
				return "transport-stop";
		}
		return null;
	}
}
