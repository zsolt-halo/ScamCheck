package hu.zsolt.scamcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowWorkdays extends Activity {
	
	public static DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_workdays);
		db = new DatabaseHandler(this);
		ListView listView = (ListView) findViewById(R.id.mylist);
		
		ArrayList<TimeSpentWorking> tsw = new ArrayList<TimeSpentWorking>();
		tsw = db.getAllWorkday();

		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data
		
		
		
		ArrayAdapter<TimeSpentWorking> adapter = new ArrayAdapter<TimeSpentWorking>(this,
		  android.R.layout.simple_list_item_1, android.R.id.text1, tsw);

		// Assign adapter to ListView
		listView.setAdapter(adapter); 
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_workdays, menu);
		return true;
	}
	
	
	public void saveAndSendAction(View view){
		
		ArrayList<TimeSpentWorking> tsw = new ArrayList<TimeSpentWorking>();
		tsw=db.getAllWorkday();
		
		StringBuilder sb = new StringBuilder();
		
		String columnString =   "\"Date of Workday\";\"Time Worked\"";
		sb.append(columnString);
		sb.append("\n");
		
		
		for(TimeSpentWorking t : tsw){
			
			String dataString   =   "\"" + t.getDateOfWorkday() +"\";\"" + this.calculateWorktime(t.getEndedWork()-t.getStartedWork()) + "\"";
			sb.append(dataString);
			sb.append("\n");
		}
		
		
		String combinedString = sb.toString();

		File file   = null;
		File root   = Environment.getExternalStorageDirectory();
		if (root.canWrite()){
		     File dir    =   new File (root.getAbsolutePath() + "/WorkData");
		     dir.mkdirs();
		     file   =   new File(dir, "Workdata.csv");
		     FileOutputStream out   =   null;
		     
		    try {
		        out = new FileOutputStream(file);
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        }
		        try {
		            out.write(combinedString.getBytes());
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        try {
		            out.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        
		    }
		
		Uri u1  =   null;
		u1  =   Uri.fromFile(file);

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Worktime statisctics");
		sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
		sendIntent.setType("text/html");
		startActivity(sendIntent);
		
	}
	
	private String calculateWorktime(long millis){
		
		
		int secs = (int) (millis/1000);
		
		
		int minutes = (int) ((secs / 60) - ((secs)/60)/60);
		int hours   = (int) (((secs)/60)/60);
		int seconds = (int) (secs - (hours*3600) - (minutes*60));
		
		return  hours + ":" + minutes + ":" + seconds;
	}

}
