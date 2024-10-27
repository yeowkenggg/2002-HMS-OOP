import java.util.*;
import java.io.*;
import java.time.*;

public class TimeSlot {

	private LocalDate date;
	private LocalTime time;

	//constructor
	public TimeSlot(LocalDate date, LocalTime time){
		this.date = date;
		this.time = time;
	}

	//getset date
	public LocalDate getDate(){
		return date;
	}
	public void setDate(LocalDate date){
		this.date = date;
	}

	//getset time
	public LocalTime getTime(){
		return time;
	}

	public void setTime(LocalTime time){
		this.time = time;
	}

	//make sure that no 2 same timeslot clashes
	public boolean isSameTimeSlot(TimeSlot other) {
		return this.date.equals(other.date) && this.time.equals(other.time);
	}	

	@Override
	public String toString() {
		return date.toString() + " @ " + time.toString();
	}

}