import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // initialize objects
        Administrator admin = new Administrator("A001", "adminPwd", "Admin", "Female", "Administrator", 45);
        Doctor doctor1 = new Doctor("D001", "doctorPwd", "Doctor", "Male", "Doctor", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pharmaPwd", "Pharmacist", "Male", "Pharmacist", 35);
        Patient patient1 = new Patient("P001", "patientPwd", "Patient", "Male", LocalDate.of(1995, 5, 20), "O+", "12345678");

        Medicine panadol = new Medicine("Panadol", 10, 20);
        Medicine ibuprofen = new Medicine("Ibuprofen", 5, 10);
        List<Medicine> prescriptionMeds = Arrays.asList(panadol, ibuprofen);
        Prescription prescription = new Prescription("PR001", prescriptionMeds, "Pending");

        TimeSlot slot1 = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(10, 0));

        // Patient Test Cases
        System.out.println("=== Patient Test Cases ===");

        // Test Case 1: View Medical Record
        System.out.println("Test Case 1: View Medical Record");
        patient1.viewMedicalRecord();

        // Test Case 2: Update Personal Information
        System.out.println("\nTest Case 2: Update Personal Information");
        //patient1.updateContactInfo();
        patient1.viewMedicalRecord();  // Verifying update

        // Test Case 3: View Available Appointment Slots
        System.out.println("\nTest Case 3: View Available Appointment Slots");
        doctor1.setAvailability(slot1);
        patient1.viewAvailableSlots(doctor1);

        // Test Case 4: Schedule an Appointment
        System.out.println("\nTest Case 4: Schedule an Appointment");
        patient1.scheduleAppointment(doctor1, slot1);  // Trying to book
        patient1.viewAppointments();

        // Test Case 5: Reschedule an Appointment
        System.out.println("\nTest Case 5: Reschedule an Appointment");
        TimeSlot newSlot = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(11, 0));
        doctor1.setAvailability(newSlot);
        patient1.rescheduleAppointment(patient1.getAppointments().get(0), newSlot);
        patient1.viewAppointments();  // Verifying update

        // Test Case 6: Cancel an Appointment
        System.out.println("\nTest Case 6: Cancel an Appointment");
        patient1.cancelAppointment(patient1.getAppointments().get(0));
        patient1.viewAppointments();  // Verifying cancellation
        admin.viewAppointment();

        // Test Case 7: View Scheduled Appointments
        System.out.println("\nTest Case 7: View Scheduled Appointments");
        patient1.viewAvailableSlots(doctor1);
        patient1.viewAppointments();

        // Test Case 8: View Past Appointment Outcome Records
        System.out.println("\nTest Case 8: View Past Appointment Outcome Records");
        TimeSlot newSlotForOutcome = new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(9, 0));
        doctor1.setAvailability(newSlotForOutcome);
        patient1.scheduleAppointment(doctor1, newSlotForOutcome);
        AppointmentOutcome outcome = new AppointmentOutcome(patient1.getAppointments().get(0), "Consultation", "Routine check-up", prescription, LocalDate.now());
        patient1.getAppointments().get(0).setOutcome(outcome);
        patient1.viewAppointmentOutcome();

        // Doctor Test Cases
        System.out.println("\n=== Doctor Test Cases ===");

        // Test Case 9: View Patient Medical Records
        System.out.println("Test Case 9: View Patient Medical Records");
        doctor1.assignPatient(patient1.getUserId());
        doctor1.viewPatientRecord(patient1.getUserId());

        // Test Case 10: Update Patient Medical Records
        System.out.println("\nTest Case 10: Update Patient Medical Records");
        AppointmentOutcome newOutcome = new AppointmentOutcome(
            patient1.getAppointments().get(0), 
            "Follow-up Consultation",         
            "Additional treatment plan",       
            prescription,                     
            LocalDate.now().plusDays(1)        
        );

        // Doctor updates patient record with the new outcome
        doctor1.updatePatientRecord(patient1.getUserId(), newOutcome);

        // Verify the update in the medical record
        doctor1.viewPatientRecord(patient1.getUserId());

        // Test Case 11: View Personal Schedule
        System.out.println("\nTest Case 11: View Personal Schedule");
        doctor1.viewAppointments();

        // Test Case 12: Set Availability for Appointments
        System.out.println("\nTest Case 12: Set Availability for Appointments");
        doctor1.setAvailability(new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(9, 0)));
        patient1.viewAvailableSlots(doctor1);  // Confirm patient sees new slot

        // Test Case 13: Accept or Decline Appointment Requests
        System.out.println("\nTest Case 13: Accept or Decline Appointment Requests");
        Appointment appointment = new Appointment("APT001", patient1.getUserId(), doctor1.getUserId(), slot1, "Pending");
        doctor1.addAppointment(appointment);
        doctor1.acceptAppointment(appointment);
        patient1.viewAppointments();  // Verifying status update

        // Test Case 14: View Upcoming Appointments
        System.out.println("\nTest Case 14: View Upcoming Appointments");
        doctor1.viewAppointments();

        // Test Case 15: Record Appointment Outcome
        System.out.println("\nTest Case 15: Record Appointment Outcome");
        doctor1.updatePatientRecord(patient1.getUserId(), new AppointmentOutcome(appointment, "Follow-up", "Continued care", prescription, LocalDate.now().plusDays(3)));
        patient1.viewAppointmentOutcome();

        // Pharmacist Test Cases
        System.out.println("\n=== Pharmacist Test Cases ===");

        // Test Case 16: View Appointment Outcome Record
        System.out.println("Test Case 16: View Appointment Outcome Record");
        pharmacist1.viewPrescriptionRecords();

        // Test Case 17: Update Prescription Status
        System.out.println("\nTest Case 17: Update Prescription Status");
        pharmacist1.updatePrescriptionStatus("PR001");
        pharmacist1.viewPrescriptionRecords();  

        // Test Case 18: View Medication Inventory
        System.out.println("\nTest Case 18: View Medication Inventory");
        admin.viewMedicines();

        // Test Case 19: Submit Replenishment Request
        System.out.println("\nTest Case 19: Submit Replenishment Request");
        pharmacist1.replenishmentRequest(panadol, 50);
        admin.viewReplenishmentRequests();  

        // Administrator Test Cases
        System.out.println("=== Administrator Test Cases ===");

        // Test Case 20: View and Manage Hospital Staff
        System.out.println("Test Case 20: View and Manage Hospital Staff");
        new Doctor("D002", "docPwd", "Dr. Jane", "Female", "Doctor", 39);
        admin.viewAllStaff();

        // Test Case 21: View Appointments Details
        System.out.println("\nTest Case 21: View Appointments Details");
        admin.viewAppointment();

        // Test Case 22: View and Manage Medication Inventory
        System.out.println("\nTest Case 22: View and Manage Medication Inventory");
        // Test Case 22: View and Manage Medication Inventory
        System.out.println("=== Test Case 22: View and Manage Medication Inventory ===");

        // 1. View All Medicines in Inventory
        System.out.println("\nStep 1: Viewing Current Medication Inventory:");
        admin.viewMedicines();  // Should display an empty list if no medicines are added

        // 2. Add New Medicines
        System.out.println("\nStep 2: Adding New Medicines to Inventory:");
        admin.addMedicine();  // Add a new medicine by user input (simulate with names like "Panadol")
        admin.addMedicine();  // Add another medicine to test adding functionality
        System.out.println("\nUpdated Medication Inventory after Adding:");
        admin.viewMedicines();  // View updated list to confirm addition

        // 3. Update Stock of an Existing Medicine
        System.out.println("\nStep 3: Updating Stock for Existing Medicine:");
        admin.updateMedicineStock();  // Simulate updating stock for an existing medicine
        System.out.println("\nUpdated Medication Inventory after Stock Update:");
        admin.viewMedicines();  // Confirm the updated stock levels

        // 4. Update Stock Alert Level
        System.out.println("\nStep 4: Updating Stock Alert Level for a Medicine:");
        admin.updateStockAlertLevel();  // Simulate updating alert level for an existing medicine
        System.out.println("\nUpdated Medication Inventory after Alert Level Update:");
        admin.viewMedicines();  // Confirm the updated alert levels

        // 5. Remove a Medicine
        System.out.println("\nStep 5: Removing a Medicine from Inventory:");
        admin.removeMedicine();  // Simulate removal of a medicine
        System.out.println("\nUpdated Medication Inventory after Removal:");
        admin.viewMedicines();  // Confirm the inventory is updated and the medicine is removed


        // Test Case 23: Approve Replenishment Requests
        System.out.println("\nTest Case 23: Approve Replenishment Requests");
        admin.approveReplenishment("R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMHHmmss")));
        admin.viewMedicines();

        // Login and Password Management Test Cases
        System.out.println("\n=== Login and Password Management Test Cases ===");

        // Test Case 25: First-Time Login and Password Change
        System.out.println("Test Case 25: First-Time Login and Password Change");
        if (patient1.login("P001", "patientPwd")) {
            patient1.changePassword("newPatientPwd");
            System.out.println("Password changed successfully for patient.");
        }

        // Test Case 26: Login with Incorrect Credentials
        System.out.println("\nTest Case 26: Login with Incorrect Credentials");
        boolean loginResult = patient1.login("P001", "wrongPwd");
        if (!loginResult) {
            System.out.println("Login failed with incorrect password.");
        }
        
        scanner.close();
    }
}



        /*
        Scanner scanner = new Scanner(System.in);

        // create instances
        Administrator admin = new Administrator("A001", "pwd", "A1", "Female", "Administrator", 40);
        
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pwd", "PTest", "M", "Pharmacist", 40);
        Pharmacist pharmacist2 = new Pharmacist("P0002", "pwd", "P2Test", "M", "Pharmacist", 35);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        Medicine ibuprofen = new Medicine("Ibuprofen", 5, 10);

        Patient patient1 = new Patient("P001", "pwd", "P1", "Female", LocalDate.of(1995, 5, 20), "O+", "123");
        Patient patient2 = new Patient("P002", "pwd", "P2", "Female", LocalDate.of(1995, 5, 20), "O+", "345");
       
        Doctor doctor1 = new Doctor("D001", "pwd", "D1", "Male", "Doctor", 45);
        TimeSlot slot1 = new TimeSlot(LocalDate.of(2024, 10, 26), LocalTime.of(10, 0));

        //medicalrecord test
        patient1.setName("P3");
        patient1.updateGender("Male");  
        //patient1.updateContactInfo();
        patient1.viewMedicalRecord();

        doctor1.setAvailability(slot1);

        //timeslot test
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

        patient1.viewAppointments();
        doctor1.viewAppointments();

        // Start login process
        boolean loginSuccess = false;
        System.out.println("HMS SC2002");
        while (!loginSuccess) {
            System.out.println("\nAdmin Login");
            admin.loginMenu();  // Replace with admin instance-specific login menu
            if (admin.isLoggedIn()) {
                loginSuccess = true;
                admin.displayMenu();  // Now, admin can display the admin-specific menu
            } else {
                System.out.println("Exiting...");
                break;
            }
        }
        scanner.close();*/
