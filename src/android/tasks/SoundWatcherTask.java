package cordova.plugin.helloWorld.tasks;

import cordova.plugin.helloWorld.database.SoundData;
import cordova.plugin.helloWorld.disertatie.ListenerService;
import cordova.plugin.helloWorld.helpers.SoundMeter;

import android.os.AsyncTask;

public class SoundWatcherTask extends cordova.plugin.helloWorld.tasks.ListenerTask {

	private int time;
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Integer... params) {
		time = params[0];
		int elapsed_time = 0;
		long previous_timestamp = System.currentTimeMillis();
		SoundMeter sound = new SoundMeter();
		double level;
		do {
			try {
				sound.start();
				sound.getAmplitude();
				Thread.sleep(100);
				SoundData s = new SoundData();

				s.setTimestamp(System.currentTimeMillis());
				level = sound.getAmplitude();
				s.setValue( (float) level );
				addData(s);
				sound.stop();

				elapsed_time += System.currentTimeMillis() - previous_timestamp;
				previous_timestamp = System.currentTimeMillis();
				Thread.sleep( time - 100 );
			} catch( Exception e) {}

			if( elapsed_time > 10000 ) {
				cordova.plugin.helloWorld.tasks.SaverTask saver = new cordova.plugin.helloWorld.tasks.SaverTask();
				saver.executeOnExecutor( ListenerService.__instance.saverPool, sensorData );
				elapsed_time = 0;
			}
		} while( ListenerService.__instance != null && ListenerService.__instance.isRunning() );

		return null;
	}

}
