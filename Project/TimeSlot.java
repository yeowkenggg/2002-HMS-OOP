import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

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
		

	public static TimeSlot parseTimeSlot(String timeSlotString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(timeSlotString, formatter);
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();
            return new TimeSlot(date, time);
        } catch (Exception e) {
            System.out.println("Invalid time slot format. Please use yyyy-MM-dd HH:mm");
            return null;
        }
    }


	@Override
	public String toString() {
		return date.toString() + " @ " + time.toString();
	}

}