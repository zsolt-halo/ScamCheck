package hu.zsolt.scamcheck;

import java.util.Calendar;

public class DateHandler {
	
	public DateHandler(){
		
		super();
		
	}
	
	
	public String getDateString(){
		
		String result = "";
		
		Calendar calendar = Calendar.getInstance();
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(calendar.get(Calendar.YEAR));
		sb.append(".");
		sb.append(calendar.get(Calendar.MONTH)+1);
		sb.append(".");
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		
		result = sb.toString();
		
		return result;
		
	}
	
	
	public long minutesWorked(long startTime, long endTime){
		
		long minutesWorked = 0;
		
		minutesWorked = (((endTime - startTime)/1000)/60);
		
		return minutesWorked;
	}
	
	
	public long getTimeInMillis(){
		
		Calendar calendar = Calendar.getInstance();
		
		return calendar.getTimeInMillis(); 
		
	}

}
