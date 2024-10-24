import java.util.*;
import java.io.*;
import java.time.*;

public class TimeSlot {

	private LocalDate date;
	private LocalTime time;

	public TimeSlot(LocalDate date, LocalTime time){
		this.date = date;
		this.time = time;
	}

	public LocalDate getDate(){
		return date;
	}
	public void setDate(LocalDate date){
		this.date = date;
	}

	public LocalTime getTime(){
		return time;
	}

	public void setTime(LocalTime time){
		this.time = time;
	}

	public boolean isSameTimeSlot(TimeSlot other){
		 return this.date.equals(other.date) && this.time.equals(other.time);
	}

	public String toString(){
		return "Date: " +date.toString() + "Time: " + time.toString();
	}

}