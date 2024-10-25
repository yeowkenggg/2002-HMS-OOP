import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        Administrator admin = new Administrator("A001", "admin123", "AdminUser", "Male", "Administrator", 40);
        Doctor doctor = new Doctor("D001", "doctor123", "DTest", "Male", "Doctor", 45);
        Patient patient = new Patient("P1001", "patient123", "PTest", "Female");

        // doctor sets availability for a specific time slot
        TimeSlot availableSlot = new TimeSlot(LocalDate.of(2024, 10, 26), LocalTime.of(10, 0));  
        doctor.setAvailability(availableSlot);

        System.out.println("\nPatient POV");
        patient.scheduleAppointment(doctor, availableSlot);

        System.out.println("\nDoctor POV");
        doctor.viewAppointments();  
        Appointment patientAppointment = doctor.getAppointments().get(0);  
        doctor.acceptAppointment(patientAppointment);

        // patient views their scheduled appointments
        System.out.println("\nPatient POV");
        patient.viewAppointments();  

        // patient schedule same time slot 
        System.out.println("\nPatient POV attempt same slot");
        patient.scheduleAppointment(doctor, availableSlot);  

        // doctor declines a different time slot (for testing)
        System.out.println("\nDoctor POV (decline)");
        TimeSlot unavailableSlot = new TimeSlot(LocalDate.of(2024, 10, 27), LocalTime.of(11, 0));  // Unavailable slot
        Appointment newAppointment = new Appointment("A002", patient.getUserId(), doctor.getUserId(), unavailableSlot, "Pending");
        doctor.declineAppointment(newAppointment);

        // administrator views all appointments 
        //System.out.println("\nAdministrator POV");
        //admin.viewAppointment();
    }











       /* test for replenishment 

        Administrator admin = new Administrator("A001", "admin123", "ATest", "Male", "Administrator", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pwd", "PTest", "M", "pharmacist", 40);
        Pharmacist pharmacist2 = new Pharmacist("P0002", "pwd", "P2Test", "M", "Pharmacist", 35);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        Medicine ibuprofen = new Medicine("Ibuprofen", 5, 10);

        pharmacist1.login("P0001", "pwd");
        pharmacist2.replenishmentRequest(panadol, 50);
        pharmacist1.replenishmentRequest(panadol, 50);
        pharmacist1.replenishmentRequest(ibuprofen, 50);

        System.out.println(panadol.getStock());
        admin.viewReplenishmentRequests();
        String requestID = "R25101621"; //Format in RddMMHHmm , seconds will be added in at the end to make sure its "unique"
        admin.approveReplenishment(requestID);
        System.out.println(panadol.getStock());

        
        admin.viewReplenishmentRequests();
        
        */
        
}

