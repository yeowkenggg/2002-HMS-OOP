import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Initialize managers
        IInventoryManager inventoryManager = new MedicineInventoryManager();
        StaffManager staffManager = new StaffManager();
        PrescriptionManager prescriptionManager = new PrescriptionManager();

        // Initialize roles
        Administrator admin = new Administrator("A001", "adminPwd", "Admin", "Female", "Administrator", 45, inventoryManager, staffManager);
        Doctor doctor1 = new Doctor("D001", "doctorPwd", "Doctor", "Male", "Doctor", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pharmaPwd", "Pharmacist", "Male", "Pharmacist", 35, inventoryManager, prescriptionManager);
        Patient patient1 = new Patient("P001", "patientPwd", "Patient", "Male", LocalDate.of(1995, 5, 20), "O+", "12345678");

        // Add staff to staff manager
        staffManager.addStaff(admin);
        staffManager.addStaff(doctor1);
        staffManager.addStaff(pharmacist1);

        // Inventory and Prescription Setup
        inventoryManager.addMedicine("Panadol", 10, 20);
        inventoryManager.addMedicine("Ibuprofen", 5, 10);
        Medicine panadol = inventoryManager.findMedicineByName("Panadol");
        Medicine ibuprofen = inventoryManager.findMedicineByName("Ibuprofen");

        List<Medicine> prescriptionMeds = Arrays.asList(panadol, ibuprofen);
        Prescription prescription = new Prescription("PR001", prescriptionMeds, "Pending");
        prescriptionManager.addPrescription(prescription);

        TimeSlot slot1 = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        doctor1.setAvailability(slot1);

        // ========================== Patient Actions ==========================
        System.out.println("\n=== Patient Actions ===");

        // Test Case 1: View Medical Record
        System.out.println("Test Case 1: View Medical Record");
        patient1.viewMedicalRecord();

        // Test Case 2: Update Contact Information
        System.out.println("\nTest Case 2: Update Personal Information");
        patient1.updateContactInfo("98765432");

        // Test Case 3: View Available Appointment Slots
        System.out.println("\nTest Case 3: View Available Appointment Slots");
        patient1.viewAvailableSlots(doctor1);

        // Test Case 4: Schedule an Appointment
        System.out.println("\nTest Case 4: Schedule an Appointment");
        patient1.scheduleAppointment(doctor1, slot1);
        patient1.viewAppointments();

        // Test Case 5: Reschedule an Appointment
        System.out.println("\nTest Case 5: Reschedule an Appointment");
        TimeSlot newSlot = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(11, 0));
        doctor1.setAvailability(newSlot);
        patient1.rescheduleAppointment(patient1.getAppointments().get(0), newSlot, doctor1);
        patient1.viewAppointments();

        // Test Case 6: Cancel an Appointment
        System.out.println("\nTest Case 6: Cancel an Appointment");
        patient1.cancelAppointment(patient1.getAppointments().get(0), doctor1);
        patient1.viewAppointments();

        // Test Case 7: View Scheduled Appointments
        System.out.println("\nTest Case 7: View Scheduled Appointments");
        patient1.viewAppointments();

        // Test Case 8: View Past Appointment Outcome Records
        System.out.println("\nTest Case 8: View Past Appointment Outcome Records");
        patient1.viewAppointmentOutcome();

        // ========================== Doctor Actions ==========================
        System.out.println("\n=== Doctor Actions ===");

        // Test Case 9: View Patient Medical Records
        System.out.println("Test Case 9: View Patient Medical Records");
        doctor1.assignPatient(patient1.getUserId());
        doctor1.viewPatientRecord(patient1.getUserId());

        // Test Case 10: Update Patient Medical Records
        System.out.println("\nTest Case 10: Update Patient Medical Records");
        if (patient1.getAppointments().isEmpty()) {
            TimeSlot slot = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(9, 0));
            doctor1.setAvailability(slot);
            patient1.scheduleAppointment(doctor1, slot);
        }
        AppointmentOutcome outcome = new AppointmentOutcome(
            patient1.getAppointments().get(0), 
            "Routine check-up", 
            "No issues", 
            prescription, 
            LocalDate.now()
        );
        doctor1.updatePatientRecord(patient1.getUserId(), outcome);
        // Test Case 11: View Personal Schedule
        System.out.println("\nTest Case 11: View Personal Schedule");
        doctor1.viewAppointments();

        // Test Case 12: Set Availability for Appointments
        System.out.println("\nTest Case 12: Set Availability for Appointments");
        doctor1.setAvailability(new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(10, 0)));

        // Test Case 13: Accept or Decline Appointment Requests
        System.out.println("\nTest Case 13: Accept or Decline Appointment Requests");
        Appointment appointment1 = new Appointment("APT001", patient1.getUserId(), doctor1.getUserId(), slot1, "Pending");
        doctor1.addAppointment(appointment1);
        doctor1.acceptAppointment(appointment1);
        patient1.viewAppointments();

        // Test Case 14: View Upcoming Appointments
        System.out.println("\nTest Case 14: View Upcoming Appointments");
        doctor1.viewAppointments();

        // Test Case 15: Record Appointment Outcome
        System.out.println("\nTest Case 15: Record Appointment Outcome");
        doctor1.updatePatientRecord(patient1.getUserId(), new AppointmentOutcome(appointment1, "Follow-up", "Routine", prescription, LocalDate.now().plusDays(1)));

        // ========================== Pharmacist Actions ==========================
        System.out.println("\n=== Pharmacist Actions ===");

        // Test Case 16: View Appointment Outcome Record
        System.out.println("Test Case 16: View Appointment Outcome Record");
        pharmacist1.viewPrescriptionRecords();

        // Test Case 17: Update Prescription Status
        System.out.println("\nTest Case 17: Update Prescription Status");
        pharmacist1.updatePrescriptionStatus("PR001");
        pharmacist1.viewPrescriptionRecords();

        // Test Case 18: View Medication Inventory
        System.out.println("\nTest Case 18: View Medication Inventory");
        inventoryManager.viewMedicines();

        // Test Case 19: Submit Replenishment Request
        System.out.println("\nTest Case 19: Submit Replenishment Request");
        pharmacist1.replenishmentRequest("Panadol", 50);
        inventoryManager.viewMedicines();

        // ========================== Administrator Actions ==========================
        System.out.println("\n=== Administrator Actions ===");

        // Test Case 20: View and Manage Hospital Staff
        System.out.println("Test Case 20: View and Manage Hospital Staff");
        staffManager.viewAllStaff();

        // Test Case 21: View Appointments Details
        System.out.println("\nTest Case 21: View Appointments Details");
        admin.viewAppointment();

        // Test Case 22: View and Manage Medication Inventory
        System.out.println("\nTest Case 22: View and Manage Medication Inventory");
        inventoryManager.updateMedicineStock("Panadol", 30);
        inventoryManager.viewMedicines();

        // Test Case 23: Approve Replenishment Requests
        System.out.println("\nTest Case 23: Approve Replenishment Requests");
        admin.approveReplenishment("R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMHHmmss")));

        // ========================== Login System and Password Management ==========================
        System.out.println("\n=== Login System and Password Management ===");

        // Test Case 25: First-Time Login and Password Change
        System.out.println("Test Case 25: First-Time Login and Password Change");
        if (patient1.login("P001", "patientPwd")) {
            patient1.changePassword("newPatientPwd");
            patient1.logout();
        }

        // Test Case 26: Login with Incorrect Credentials
        System.out.println("\nTest Case 26: Login with Incorrect Credentials");
        if (!patient1.login("P001", "wrongPwd")) {
            System.out.println("Login failed with incorrect password.");
        }

        scanner.close();
        pharmacist1.displayMenu();
        doctor1.displayMenu();
        patient1.displayMenu();
        admin.displayMenu();
    }
}
