package hu.zsolt.scamcheck;

public class Preferences {
	
		public boolean KEY_START_BUTTON_STATE;
		public boolean KEY_END_BUTTON_STATE;
		public boolean KEY_DELETE_BUTTON_STATE;
		public boolean KEY_STATS_BUTTON_STATE;
		public boolean KEY_CHRONOMETER_WAS_ACTIVE;
		public long KEY_CHRONOMETER_TIME;
		
		public Preferences(boolean kEY_START_BUTTON_STATE,
				boolean kEY_END_BUTTON_STATE, boolean kEY_DELETE_BUTTON_STATE,
				boolean kEY_STATS_BUTTON_STATE,
				boolean kEY_CHRONOMETER_WAS_ACTIVE, long kEY_CHRONOMETER_TIME) {
			super();
			KEY_START_BUTTON_STATE = kEY_START_BUTTON_STATE;
			KEY_END_BUTTON_STATE = kEY_END_BUTTON_STATE;
			KEY_DELETE_BUTTON_STATE = kEY_DELETE_BUTTON_STATE;
			KEY_STATS_BUTTON_STATE = kEY_STATS_BUTTON_STATE;
			KEY_CHRONOMETER_WAS_ACTIVE = kEY_CHRONOMETER_WAS_ACTIVE;
			KEY_CHRONOMETER_TIME = kEY_CHRONOMETER_TIME;
		}
		
		public Preferences()
		{
		super();			
		}

		public boolean isKEY_START_BUTTON_STATE() {
			return KEY_START_BUTTON_STATE;
		}

		public void setKEY_START_BUTTON_STATE(boolean kEY_START_BUTTON_STATE) {
			KEY_START_BUTTON_STATE = kEY_START_BUTTON_STATE;
		}

		public boolean isKEY_END_BUTTON_STATE() {
			return KEY_END_BUTTON_STATE;
		}

		public void setKEY_END_BUTTON_STATE(boolean kEY_END_BUTTON_STATE) {
			KEY_END_BUTTON_STATE = kEY_END_BUTTON_STATE;
		}

		public boolean isKEY_DELETE_BUTTON_STATE() {
			return KEY_DELETE_BUTTON_STATE;
		}

		public void setKEY_DELETE_BUTTON_STATE(boolean kEY_DELETE_BUTTON_STATE) {
			KEY_DELETE_BUTTON_STATE = kEY_DELETE_BUTTON_STATE;
		}

		public boolean isKEY_STATS_BUTTON_STATE() {
			return KEY_STATS_BUTTON_STATE;
		}

		public void setKEY_STATS_BUTTON_STATE(boolean kEY_STATS_BUTTON_STATE) {
			KEY_STATS_BUTTON_STATE = kEY_STATS_BUTTON_STATE;
		}

		public boolean isKEY_CHRONOMETER_WAS_ACTIVE() {
			return KEY_CHRONOMETER_WAS_ACTIVE;
		}

		public void setKEY_CHRONOMETER_WAS_ACTIVE(boolean kEY_CHRONOMETER_WAS_ACTIVE) {
			KEY_CHRONOMETER_WAS_ACTIVE = kEY_CHRONOMETER_WAS_ACTIVE;
		}

		public long getKEY_CHRONOMETER_TIME() {
			return KEY_CHRONOMETER_TIME;
		}

		public void setKEY_CHRONOMETER_TIME(long kEY_CHRONOMETER_TIME) {
			KEY_CHRONOMETER_TIME = kEY_CHRONOMETER_TIME;
		}
		
		
		
	    
	    

}