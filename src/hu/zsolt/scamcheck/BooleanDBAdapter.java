package hu.zsolt.scamcheck;

public class BooleanDBAdapter {
	
	public int translateBooleanToIn(Boolean value){
		
		if(value) return 1;
		return 0;
		
	}
	
	public boolean translateIntToBoolean(int value){
	
	if(value == 1) return true;
	return false;
	
	}
	
	public String translateIntToString(int value){
		
		if(value==1)return "true";
		return "false";
		
	}

}
