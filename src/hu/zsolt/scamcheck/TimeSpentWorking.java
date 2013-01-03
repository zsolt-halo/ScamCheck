package hu.zsolt.scamcheck;


public class TimeSpentWorking {
	
	private long startedWork;
	private long endedWork;
	private String dateOfWorkday;
	
	
	public long getStartedWork() {
		return startedWork;
	}
	public void setStartedWork(long enteredWork) {
		this.startedWork = enteredWork;
	}
	public long getEndedWork() {
		return endedWork;
	}
	public void setEndedWork(long endedWork) {
		this.endedWork = endedWork;
	}
	
	public String getDateOfWorkday() {
		return dateOfWorkday;
	}
	public void setDateOfWorkday(String dateOfWorkday) {
		this.dateOfWorkday = dateOfWorkday;
	}
	
	public TimeSpentWorking(long enteredWork, long endedWork, String dateOfWorkday) {
		super();
		this.startedWork = enteredWork;
		this.endedWork = endedWork;
		this.dateOfWorkday = dateOfWorkday;
	}
	
	public TimeSpentWorking() {
		
	}
	
	@Override
	public String toString() {
		
		long milliseconds = endedWork-startedWork;
		int secs = (int) (milliseconds/1000);
		
		
		int minutes = (int) ((secs / 60) - ((secs)/60)/60);
		int hours   = (int) (((secs)/60)/60);
		int seconds = (int) (secs - (hours*3600) - (minutes*60));
		
		return "Worked: " + dateOfWorkday + ": " + hours + ":" + minutes + ":" + seconds;
		
	}
			
}
