package hu.zsolt.scamcheck;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

 
public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "workDatabase";
 
    // Contacts table name
    private static final String TABLE_WORKTIME = "worktime";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_DATE = "date";
   
    // Contacts table name
    private static final String TABLE_PREFERENCES = "preferences";
    
 // Contacts Table Columns names
    private static final String KEY_START_BUTTON_STATE = "start_button";
    private static final String KEY_END_BUTTON_STATE = "stop_button";
    private static final String KEY_DELETE_BUTTON_STATE = "delete_button";
    private static final String KEY_STATS_BUTTON_STATE = "stats_button";
    private static final String KEY_CHRONOMETER_WAS_ACTIVE = "chronometer_was_active";
    private static final String KEY_CHRONOMETER_TIME = "chronometer_time";
    
    
    
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WORK_TABLE = "CREATE TABLE " + TABLE_WORKTIME
        		+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        		+ KEY_START_TIME + " BIGINT,"
                + KEY_END_TIME + " BIGINT,"
        		+  KEY_DATE + " TEXT"
                + ")";
        
        String CREATE_PREFERENCES_TABLE = "CREATE TABLE " + TABLE_PREFERENCES
        		+ "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        		+ KEY_START_BUTTON_STATE + " INT,"
        		+ KEY_END_BUTTON_STATE + " INT,"
        		+ KEY_DELETE_BUTTON_STATE + " INT,"
        		+ KEY_STATS_BUTTON_STATE + " INT,"
        		+ KEY_CHRONOMETER_WAS_ACTIVE + " INT,"
        		+ KEY_CHRONOMETER_TIME + " BIGINT"
                +")";
        
        
        db.execSQL(CREATE_WORK_TABLE);
        db.execSQL(CREATE_PREFERENCES_TABLE);
        Log.i(this.getClass().getName(), "Creating Database!");
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
 
        // Create tables again
        onCreate(db);
        Log.i(this.getClass().getName(), "Updated Database!");
    }
 
   
    
    
     
 
    // Adding new contact
    void addWorkday(TimeSpentWorking tsw) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_START_TIME, tsw.getStartedWork()); // Work Started in millis as String
        values.put(KEY_DATE, tsw.getDateOfWorkday()); // Work DAY date as String
 
        // Inserting Row
        Log.i(this.getClass().getName(),String.valueOf(db.insert(TABLE_WORKTIME, null, values)));
        db.close(); // Closing database connection
        
        
       
    }
 
    
    // Getting All WorkDays
    public ArrayList<TimeSpentWorking> getAllWorkday() {
    	Log.i(this.getClass().getName(), "Getting al workdays! "); 
        ArrayList<TimeSpentWorking> workDayList = new ArrayList<TimeSpentWorking>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WORKTIME;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TimeSpentWorking tsw = new TimeSpentWorking(Long.parseLong(cursor.getString(1)),Long.parseLong(cursor.getString(2)),cursor.getString(3));
                // Adding contact to list
                Log.i(this.getClass().getName(),"Record: " + tsw.getStartedWork() + " | " + tsw.getEndedWork() + " | " + tsw.getDateOfWorkday());
                workDayList.add(tsw);
            } while (cursor.moveToNext());
        }
        
        else{
        	
        	Log.w(this.getClass().getName(),">> NO RECORDS FETCHED <<");
        	
        }
        
        cursor.close();
        db.close();
        // return contact list
        for(TimeSpentWorking tsw : workDayList){
        	
        	Log.i("Listing workdays: ",tsw.getDateOfWorkday() + " - " + tsw.getStartedWork() + " - " + tsw.getEndedWork() + " - " + tsw.toString());
        	
        }
        
        
        return workDayList;
    }
 
    // adding endTime to existing workday
    public int updateWorkday(long endedWork) {
    	
    	Log.i(this.getClass().getName(), "Updating workday! "); 
        
 
        ContentValues values = new ContentValues();
        values.put(KEY_END_TIME, endedWork);
 
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        
        
        int affected = db.update(TABLE_WORKTIME, values,"id=?",new String[] {String.valueOf(this.getLastId())});
        db.close();
        Log.i(this.getClass().getName(),"Affected: " + affected);
        return affected;
    }
  
    public int getStoredWorkdaysCount() {
    	
    	String query = "SELECT count(id) from worktime";
		Cursor cursor = this.getReadableDatabase().rawQuery(query, null);
		cursor.moveToFirst();
		int numberOfDashboardPages = cursor.getInt(0);
		cursor.close();
    	         
        Log.i(this.getClass().getName(), "All workdays: " + numberOfDashboardPages);  
        return numberOfDashboardPages;
    }
    
    public long getLastId(){
    	
    	String query = "SELECT " + KEY_ID + " from " + TABLE_WORKTIME + " order by " + KEY_ID + " DESC limit 1";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(query,null);
    	if (c != null && c.moveToFirst()) {
    	    long lastId = c.getLong(0);
    	    Log.i(this.getClass().getName(),"LAST ID is: " + lastId);
    	    c.close();
    	    return lastId;
    	}
    	
    	c.close();
    	Log.i(this.getClass().getName(),"ERROR getting last ID");
    	return -1;
        
    }
    
    
    public void deleteTableContents(){
    	   	 
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKTIME, null, null);
        Log.i(this.getClass().getName(),"Table Deleted!");
        db.close();
    }
    
    
   
    /*////////////////////////////////////////////////////////////////////////////*/
    /*                    ******                    *****                         */
    /*                   **   ******             *********                        */
    /*                   *   *     *            **       **                       */
    /*                  **   **    *            *   ***   **                      */
    /*                  **   **    *            *   ***   **                      */
    /*                   ***      **            ***      **                       */
    /*                     ***   **     ***       ********                        */
    /*                       *****        **                                      */
    /*                   *                 **                                     */
    /*                   *                 **                 *                   */
    /*                   **               **                 **                   */
    /*                    **                               ***                    */
    /*                     **                            ***                      */
    /*                      **                         ***                        */
    /*                       ****                    ***                          */
    /*                          ****             *****                            */
    /*                             ***************                                */
    /*                                                                            */
    /*                            PREFERENCES METHODS                             */
    /*////////////////////////////////////////////////////////////////////////////*/
   
    
    public void savePreferences(Preferences prefs){
    	
    	Log.i(this.getClass().getName(),"Saving UI to DB");
		
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKTIME, null, null);
    	
    	 
        ContentValues values = new ContentValues();
        
        values.put(KEY_START_BUTTON_STATE, prefs.isKEY_START_BUTTON_STATE());
        values.put(KEY_END_BUTTON_STATE, prefs.isKEY_END_BUTTON_STATE());
        values.put(KEY_DELETE_BUTTON_STATE, prefs.isKEY_DELETE_BUTTON_STATE());
        values.put(KEY_STATS_BUTTON_STATE, prefs.isKEY_STATS_BUTTON_STATE());
        values.put(KEY_CHRONOMETER_WAS_ACTIVE, prefs.isKEY_CHRONOMETER_WAS_ACTIVE());
        values.put(KEY_CHRONOMETER_TIME, prefs.getKEY_CHRONOMETER_TIME());
 
        // Inserting Row
        db.insert(TABLE_PREFERENCES, null, values);
        db.close(); // Closing database connection
        
        this.getStoredWorkdaysCount();
    	  	
    }
    
    
    public Preferences getPreferences(){
    	
    	Log.i(this.getClass().getName(),"Loading UI from DB");
    	
    	BooleanDBAdapter dbAdapter = new BooleanDBAdapter();
    	Preferences pref = new Preferences();
    	
    	String selectQuery = "SELECT  * FROM " + TABLE_PREFERENCES;
    	 
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Preferences tmpPrefs = new Preferences();
                
                tmpPrefs.setKEY_START_BUTTON_STATE(Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(1))));
                tmpPrefs.setKEY_END_BUTTON_STATE(Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(2))));
                tmpPrefs.setKEY_DELETE_BUTTON_STATE(Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(3))));
                tmpPrefs.setKEY_STATS_BUTTON_STATE(Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(4))));
                tmpPrefs.setKEY_CHRONOMETER_WAS_ACTIVE(Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(5))));
                tmpPrefs.setKEY_CHRONOMETER_TIME(Long.parseLong(cursor.getString(6)));  
                
                Log.w(this.getClass().getName(),"DB prefs: " +
                		  Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(1))) + "\n"
                		+ Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(2))) + "\n"
                		+ Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(3))) + "\n"
                		+ Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(4))) + "\n"
                		+ Boolean.parseBoolean(dbAdapter.translateIntToString(cursor.getInt(5))) + "\n");
                
                
                pref = tmpPrefs;
                
            } while (cursor.moveToNext());
        }
        
        else {
        	Log.e(this.getClass().getName(), "No Preferences found in database");
        	pref = null;
        }
        
        cursor.close();
    	    	
    	return pref;
    }
    
    
    
     
}