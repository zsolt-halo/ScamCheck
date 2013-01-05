package hu.zsolt.scamcheck;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<TimeSpentWorking> {
	public MyArrayAdapter(Context context, ArrayList<TimeSpentWorking> workdayArray) {
		super(context, R.layout.worktimelayout, workdayArray);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View item = convertView;

		if (item == null) {

			LayoutInflater inflater = ((Activity) getContext())
					.getLayoutInflater();
			item = inflater.inflate(R.layout.worktimelayout, parent, false);
			

		}
		TextView v = ((TextView) item.findViewById(R.id.statsTextView));
		
		
		v.setText((getItem(position)).toString());
		item.setTag(getItem(position));		
		return item;
	}

}