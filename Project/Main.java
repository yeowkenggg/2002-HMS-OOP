import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // create instances
        Administrator admin = new Administrator("A001", "pwd", "A1", "Female", "Administrator", 40);
        
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pwd", "PTest", "M", "Pharmacist", 40);
        Pharmacist pharmacist2 = new Pharmacist("P0002", "pwd", "P2Test", "M", "Pharmacist", 35);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        Medicine ibuprofen = new Medicine("Ibuprofen", 5, 10);

        Patient patient1 = new Patient("P1001", "pwd", "P1", "Female");
        Patient patient2 = new Patient("P1002", "pwd", "P2", "Male");

        //timeslot test
        Doctor doctor1 = new Doctor("D001", "pwd", "D1", "Male", "Doctor", 45);
        TimeSlot slot1 = new TimeSlot(LocalDate.of(2024, 10, 26), LocalTime.of(10, 0));

        //to avoid going through the entire menu 
        admin.addStaffObj(doctor1);
        admin.addStaffObj(pharmacist1);
        admin.addStaffObj(pharmacist2);
        doctor1.setAvailability(slot1);

        //schedule test
        System.out.println("Schedule Test");
        System.out.println("\nP1 appointment D1");
        patient1.scheduleAppointment(doctor1, slot1);  

        System.out.println("\nP1 appts");
        patient1.viewAppointments(); 

        System.out.println("\nD1 appts");
        doctor1.viewAppointments();  
        Appointment patient1Appointment = doctor1.getAppointments().get(0);  
        doctor1.acceptAppointment(patient1Appointment);
        

        //replenishment test
        System.out.println("\nReplenish Test");
        pharmacist1.replenishmentRequest(panadol, 50);
        pharmacist1.replenishmentRequest(ibuprofen, 50);

        //prescription test
        System.out.println("\nPrescription Test");
        List<Medicine> prescriptionMeds1 = new ArrayList<>();
        prescriptionMeds1.add(panadol);
        prescriptionMeds1.add(ibuprofen);
        Prescription prescription1 = new Prescription("PR001", prescriptionMeds1, "Pending");

        List<Medicine> prescriptionMeds2 = new ArrayList<>();
        prescriptionMeds2.add(ibuprofen);
        Prescription prescription2 = new Prescription("PR002", prescriptionMeds2, "Dispensed");

        pharmacist1.updatePrescriptionStatus("PR001");

        //appt outcome
        System.out.println("\nOutcome Test");
        System.out.println("Patient POV");
        AppointmentOutcome outcome = new AppointmentOutcome(patient1Appointment, "Consultation", "Routine check-up", prescription1, LocalDate.now());
        patient1Appointment.setOutcome(outcome);
        patient1.viewAppointmentOutcome();
        patient2.viewAppointmentOutcome();

        System.out.println("Staff POV");
        for (Appointment appointment : Appointment.getAllAppointments()) {
            System.out.println(appointment);  // Display appointment details
            if (appointment.getOutcome() != null) {
                System.out.println("Outcome:\n" + appointment.getOutcome());  // Format outcome with updated toString
            }
        }


        // Start login process
        boolean loginSuccess = false;
        System.out.println("HMS SC2002");
        while (!loginSuccess) {
            System.out.print("Enter User ID: ");
            String inputUserId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            if (admin.login(inputUserId, inputPassword)) {
                loginSuccess = true;
                boolean isRunning = true;
                while (isRunning) {
                    admin.displayMenu();
                    if (!admin.isLoggedIn()) {
                        System.out.println("Session ended.");
                        isRunning = false;
                    }
                }
            } else {
                System.out.println("Invalid credentials. Please try again.");
                System.out.print("Do you want to try again? (y/n): ");
                String retry = scanner.nextLine();
                if (retry.equalsIgnoreCase("n")) {
                    System.out.println("Exiting system.");
                    break;
                }
            }
        }

        scanner.close();
    }
}