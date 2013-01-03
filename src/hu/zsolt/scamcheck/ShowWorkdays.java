package hu.zsolt.scamcheck;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
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

}
