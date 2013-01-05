package hu.zsolt.scamcheck;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ShowWorkdays extends Activity {

	public static DatabaseHandler db;
	public static long selectedRecord;
	ArrayList<TimeSpentWorking> tsw;
	int indexOfSelectedItem;
	MyArrayAdapter adapterMy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_show_workdays);
		db = new DatabaseHandler(this);
		ListView listView = (ListView) findViewById(R.id.mylist);

		tsw = db.getAllWorkday();

		adapterMy = new MyArrayAdapter(this, tsw);

		// Assign adapter to ListView
		listView.setAdapter(adapterMy);

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					int pos, long id) {
				
				
				TimeSpentWorking tsw = (TimeSpentWorking) v.getTag();
				selectedRecord = tsw.getStartedWork();
				showDialogForDelete();
				indexOfSelectedItem = pos;
				

				return false;
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					int pos, long id) {

				TimeSpentWorking tsw = (TimeSpentWorking) v.getTag();
				showToastInfo(tsw.getStartedWork(),tsw.getEndedWork());
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_show_workdays, menu);
		return true;
	}

	public void saveAndSendAction(View view) {

		ArrayList<TimeSpentWorking> tsw = new ArrayList<TimeSpentWorking>();
		tsw = db.getAllWorkday();

		StringBuilder sb = new StringBuilder();

		String columnString = "\"Date of Workday\";\"Time Worked\"";
		sb.append(columnString);
		sb.append("\n");

		for (TimeSpentWorking t : tsw) {

			String dataString = "\""
					+ t.getDateOfWorkday()
					+ "\";\""
					+ this.calculateWorktime(t.getEndedWork()
							- t.getStartedWork()) + "\"";
			sb.append(dataString);
			sb.append("\n");
		}

		String combinedString = sb.toString();

		File file = null;
		File root = Environment.getExternalStorageDirectory();
		if (root.canWrite()) {
			File dir = new File(root.getAbsolutePath() + "/WorkData");
			dir.mkdirs();
			file = new File(dir, "Workdata.csv");
			FileOutputStream out = null;

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

		Uri u1 = null;
		u1 = Uri.fromFile(file);

		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Worktime statisctics");
		sendIntent.putExtra(Intent.EXTRA_STREAM, u1);
		sendIntent.setType("text/html");
		startActivity(sendIntent);

	}

	private String calculateWorktime(long millis) {

		int secs = (int) (millis / 1000);

		int minutes = (int) ((secs / 60) - ((secs) / 60) / 60);
		int hours = (int) (((secs) / 60) / 60);
		int seconds = (int) (secs - (hours * 3600) - (minutes * 60));

		return hours + ":" + minutes + ":" + seconds;
	}

	DialogInterface.OnClickListener dialogClickListenerDeleteRecordFromDatabase = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:

				Log.i("yes", "yes");
				db.deleteRecord(selectedRecord);
				tsw.remove(indexOfSelectedItem);
				adapterMy.notifyDataSetChanged();
				
				break;

			case DialogInterface.BUTTON_NEGATIVE:

				Log.i("no", "no");

				break;
			}
		}
	};

	private void showDialogForDelete() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Delete this record from the database?")
				.setPositiveButton("YES",
						dialogClickListenerDeleteRecordFromDatabase)
				.setNegativeButton("NO",
						dialogClickListenerDeleteRecordFromDatabase).show();

	}
	
	private void showToastInfo(long started, long ended){
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		 
		Date startDate = new Date(started);
		Date endDate = new Date(ended);		
		
		Toast.makeText(this,"Worked from " + sdf.format(startDate) + " to " + sdf.format(endDate),
				Toast.LENGTH_LONG).show();
		
		
	}

}
