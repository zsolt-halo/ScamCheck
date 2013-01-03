package hu.zsolt.scamcheck;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends Activity {

	public static DateHandler dateHandler = new DateHandler();
	public static DatabaseHandler db;

	public long startTime;
	public long endTime;
	public boolean chronometerRunning = false;
	public long baseTimeForChronometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.i(this.getClass().getName(),"onCreate()");
		((Button) findViewById(R.id.startedWorkButton)).setEnabled(true);
		((Button) findViewById(R.id.endedWorkButton)).setEnabled(false);

		db = new DatabaseHandler(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressWarnings({})
	public void startTime(View view) {

		baseTimeForChronometer = SystemClock
				.elapsedRealtime();
		((Chronometer) findViewById(R.id.chronoMeter)).setBase(baseTimeForChronometer);
		((Chronometer) findViewById(R.id.chronoMeter)).start();
		this.chronometerRunning = true;

		((Button) findViewById(R.id.startedWorkButton)).setEnabled(false);
		((Button) findViewById(R.id.endedWorkButton)).setEnabled(true);
		((Button) findViewById(R.id.deleteWorkdays)).setEnabled(false);
		((Button) findViewById(R.id.showStatsButton)).setEnabled(false);

		startTime = dateHandler.getTimeInMillis();

		TimeSpentWorking tsw = new TimeSpentWorking();
		tsw.setStartedWork(startTime);
		tsw.setDateOfWorkday(dateHandler.getDateString());

		db.addWorkday(tsw);

	}

	public void endTime(View view) {

		((Chronometer) findViewById(R.id.chronoMeter)).stop();
		this.chronometerRunning = false;

		((Button) findViewById(R.id.startedWorkButton)).setEnabled(true);
		((Button) findViewById(R.id.endedWorkButton)).setEnabled(false);
		((Button) findViewById(R.id.deleteWorkdays)).setEnabled(true);
		((Button) findViewById(R.id.showStatsButton)).setEnabled(true);

		endTime = dateHandler.getTimeInMillis();

		db.updateWorkday(endTime);

	}

	public void deleteButtonAction(View view) {

		db.deleteTableContents();

	}

	public void showStatsAction(View view) {

		Intent myIntent = new Intent(this, ShowWorkdays.class);
		startActivity(myIntent);

	}

	

	@Override
	protected void onDestroy() {
		Log.i(this.getClass().getName(),"onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.i(this.getClass().getName(),"onPause()");
		super.onPause();
		
		/*Loading all stuff from DB*/
		Preferences pref = new Preferences();
		
		pref.setKEY_START_BUTTON_STATE(((Button) findViewById(R.id.startedWorkButton)).isEnabled());
		pref.setKEY_END_BUTTON_STATE(((Button) findViewById(R.id.endedWorkButton)).isEnabled());
		pref.setKEY_DELETE_BUTTON_STATE(((Button) findViewById(R.id.deleteWorkdays)).isEnabled());
		pref.setKEY_STATS_BUTTON_STATE(((Button) findViewById(R.id.showStatsButton)).isEnabled());
		Log.w(this.getClass().getName(),"Stats button state:  " + pref.isKEY_STATS_BUTTON_STATE() + " | " + ((Button) findViewById(R.id.showStatsButton)).isEnabled());
		pref.setKEY_CHRONOMETER_WAS_ACTIVE(chronometerRunning);
		pref.setKEY_CHRONOMETER_TIME(baseTimeForChronometer);
		
		db.savePreferences(pref);
		
	}

	@Override
	protected void onRestart() {
		Log.i(this.getClass().getName(),"onRestart()");
		super.onRestart();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		/*Saving all stuff to Database*/
		Preferences pref = new Preferences();
		
		pref = db.getPreferences();
		
		if(pref != null){
		
		((Button) findViewById(R.id.startedWorkButton)).setEnabled(pref.isKEY_START_BUTTON_STATE());
		((Button) findViewById(R.id.endedWorkButton)).setEnabled(pref.isKEY_END_BUTTON_STATE());
		((Button) findViewById(R.id.deleteWorkdays)).setEnabled(pref.isKEY_DELETE_BUTTON_STATE());
		((Button) findViewById(R.id.showStatsButton)).setEnabled(pref.isKEY_STATS_BUTTON_STATE());
		
		if(pref.isKEY_CHRONOMETER_WAS_ACTIVE()){
			
			((Chronometer) findViewById(R.id.chronoMeter)).setBase(pref.getKEY_CHRONOMETER_TIME());
			((Chronometer) findViewById(R.id.chronoMeter)).start();
			
			}
		
		}
		
	}

	@Override
	protected void onStart() {
		Log.i(this.getClass().getName(),"onStart()");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(this.getClass().getName(),"onStop()");
		super.onStop();
	}
	
	
	
	
	

}
