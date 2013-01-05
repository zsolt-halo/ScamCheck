package hu.zsolt.scamcheck;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

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

		((TextView)findViewById(R.id.workStateView)).setText(new TextFormatter().setTextToGREEN((getResources().getString(R.string.workStatusWorking))));
		((TextView)findViewById(R.id.workStateView)).setTextColor(Color.GREEN);
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
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You have chosen to STOP!\nARE YOU SURE?\nThis cannot be UNDONE!").setPositiveButton("YES", dialogClickListener)
		    .setNegativeButton("NO", dialogClickListener).show();

	}

	public void deleteButtonAction(View view) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("YOU ARE ABOUT TO DELETE EVERYTHING!\nARE YOU SURE?").setPositiveButton("YES", dialogClickListenerDeleteDatabase)
		    .setNegativeButton("NO", dialogClickListenerDeleteDatabase).show();
		

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
		
		if(pref.isKEY_START_BUTTON_STATE()){
			
			((TextView)findViewById(R.id.workStateView)).setText((new TextFormatter().setTextToRED(getResources().getString(R.string.workStatusNotWorking))));
			((TextView)findViewById(R.id.workStateView)).setTextColor(Color.RED);
			
		}
		else {
			
			((TextView)findViewById(R.id.workStateView)).setText((new TextFormatter().setTextToGREEN(getResources().getString(R.string.workStatusWorking))));
			((TextView)findViewById(R.id.workStateView)).setTextColor(Color.GREEN);
			
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
	
	
	/*DIALOG BOX*/
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	/*FINISHED WORK, FOR SURE*/
	        	((TextView)findViewById(R.id.workStateView)).setText((new TextFormatter().setTextToRED(getResources().getString(R.string.workStatusNotWorking))));
	        	((TextView)findViewById(R.id.workStateView)).setTextColor(Color.RED);
	        	((Chronometer) findViewById(R.id.chronoMeter)).stop();
	    		chronometerRunning = false;

	    		((Button) findViewById(R.id.startedWorkButton)).setEnabled(true);
	    		((Button) findViewById(R.id.endedWorkButton)).setEnabled(false);
	    		((Button) findViewById(R.id.deleteWorkdays)).setEnabled(true);
	    		((Button) findViewById(R.id.showStatsButton)).setEnabled(true);

	    		endTime = dateHandler.getTimeInMillis();

	    		db.updateWorkday(endTime);
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            /*NOTHING HAPPENS*/
	            break;
	        }
	    }
	};
	
	
	DialogInterface.OnClickListener dialogClickListenerDeleteDatabase = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	        	
	        	db.deleteTableContents();
	        	
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            
	            break;
	        }
	    }
	};
	
	
	

}
