import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize managers and services
        MedicineManagementService inventoryManager = new MedicineManagementService();
        StaffManagementService staffManager = new StaffManagementService();
        PrescriptionManager prescriptionManager = new PrescriptionManager();
        PatientService patientService = new PatientService(); 
        DoctorService doctorService = new DoctorService(patientService);

        // Initialize roles
        Administrator admin = new Administrator("A001", "adminPwd", "Admin", "Female", "Administrator", 45, staffManager,inventoryManager);
        Doctor doctor1 = new Doctor("D001", "doctorPwd", "Doctor", "Male", "Doctor", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pharmaPwd", "Pharmacist", "Male", "Pharmacist", 35, inventoryManager, prescriptionManager);
        Patient patient1 = new Patient("P001", "patientPwd", "Patient", "Male", LocalDate.of(1995, 5, 20), "O+", "12345678");

        // Add staff to staff manager
        staffManager.addStaff(admin);
        staffManager.addStaff(doctor1);
        staffManager.addStaff(pharmacist1);
        patientService.addPatient(patient1);

        // Inventory and Prescription Setup
        inventoryManager.addMedicine("Panadol", 10, 20);
        inventoryManager.addMedicine("Ibuprofen", 5, 10);
        Medicine panadol = inventoryManager.findMedicineByName("Panadol");
        Medicine ibuprofen = inventoryManager.findMedicineByName("Ibuprofen");

        List<Medicine> prescriptionMeds = Arrays.asList(panadol, ibuprofen);
        Prescription prescription = new Prescription("PR001", prescriptionMeds, "Pending");
        prescriptionManager.addPrescription(prescription);

        TimeSlot slot1 = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        doctorService.setAvailability(doctor1, slot1);

        // ========================== Patient Actions ==========================
        System.out.println("\n=== Patient Actions ===");

        // Test Case 1: View Medical Record
        System.out.println("Test Case 1: View Medical Record");
        patientService.viewMedicalRecord(patient1);

        // Test Case 2: Update Contact Information
        System.out.println("\nTest Case 2: Update Personal Information");
        patientService.updateContactInfo(patient1, "98765432");
        patientService.viewMedicalRecord(patient1);

        // Test Case 3: View Available Appointment Slots
        System.out.println("\nTest Case 3: View Available Appointment Slots");
        patientService.viewAvailableSlots(doctorService, doctor1);

        // Test Case 4: Schedule an Appointment
        System.out.println("\nTest Case 4: Schedule an Appointment");
        patientService.scheduleAppointment(patient1, doctorService, doctor1, slot1);
        patientService.scheduleAppointment(patient1, doctorService, doctor1, slot1);
        patientService.viewAppointments(patient1);

        // Test Case 5: Reschedule an Appointment
        System.out.println("\nTest Case 5: Reschedule an Appointment");
        TimeSlot newSlot = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(11, 0));
        doctorService.setAvailability(doctor1, newSlot);
        patientService.rescheduleAppointment(patient1, patient1.getAppointments().get(0), doctorService, newSlot, doctor1);
        patientService.viewAppointments(patient1);

        // Test Case 6: Cancel an Appointment
        System.out.println("\nTest Case 6: Cancel an Appointment");
        patientService.cancelAppointment(patient1, patient1.getAppointments().get(0), doctorService, doctor1);
        patientService.viewAppointments(patient1);

        // Test Case 7: View Scheduled Appointments
        System.out.println("\nTest Case 7: View Scheduled Appointments");
        // Simulate 2 more appointments before showing
        TimeSlot slot2 = new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(11, 0));
        TimeSlot slot3 = new TimeSlot(LocalDate.now().plusDays(4), LocalTime.of(9, 0));
        doctorService.setAvailability(doctor1, slot2);
        doctorService.setAvailability(doctor1, slot3);
        patientService.scheduleAppointment(patient1, doctorService, doctor1, newSlot);
        patientService.scheduleAppointment(patient1, doctorService, doctor1, slot3);
        patientService.viewAppointments(patient1);

        // Test Case 8: View Past Appointment Outcome Records
        System.out.println("\nTest Case 8: View Past Appointment Outcome Records");
        doctorService.assignPatient(doctor1, patient1.getUserId());

        AppointmentOutcome outcome1 = new AppointmentOutcome(
            patient1.getAppointments().get(0),
            "Testing outcome entry",
            "No issues",
            prescription,
            LocalDate.now()
        );
        doctorService.updatePatientRecord(doctor1, patient1.getUserId(), outcome1);
        patientService.viewAppointmentOutcome(patient1);

        // ========================== Doctor Actions ==========================
        System.out.println("\n=== Doctor Actions ===");

        // Test Case 9: View Patient Medical Records
        System.out.println("Test Case 9: View Patient Medical Records");
        doctorService.assignPatient(doctor1, patient1.getUserId());
        doctorService.viewPatientRecord(doctor1, patient1.getUserId());

        // Test Case 10: Update Patient Medical Records
        System.out.println("\nTest Case 10: Update Patient Medical Records");
        AppointmentOutcome outcome2 = new AppointmentOutcome(
            patient1.getAppointments().get(0),
            "Routine check-up",
            "No issues",
            prescription,
            LocalDate.now()
        );
        doctorService.updatePatientRecord(doctor1, patient1.getUserId(), outcome2);
        doctorService.viewPatientRecord(doctor1, patient1.getUserId());

        // Test Case 11: View Personal Schedule
        System.out.println("\nTest Case 11: View Personal Schedule");
        doctorService.viewAppointments(doctor1);

        // Test Case 12: Set Availability for Appointments
        System.out.println("\nTest Case 12: Set Availability for Appointments");
        doctorService.setAvailability(doctor1, new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(10, 0)));

        // Test Case 13: Accept or Decline Appointment Requests
        System.out.println("\nTest Case 13: Accept or Decline Appointment Requests");
        Appointment appointment1 = new Appointment("APT001", patient1.getUserId(), doctor1.getUserId(), slot1, "Pending");
        doctorService.addAppointment(doctor1, appointment1);
        doctorService.acceptAppointment(doctor1, appointment1);
        patientService.viewAppointments(patient1);

        // Test Case 14: View Upcoming Appointments
        System.out.println("\nTest Case 14: View Upcoming Appointments");
        doctorService.viewAppointments(doctor1);

        // Test Case 15: Record Appointment Outcome
        System.out.println("\nTest Case 15: Record Appointment Outcome");
        doctorService.updatePatientRecord(doctor1, patient1.getUserId(), new AppointmentOutcome(
            appointment1, "Follow-up", "Routine", prescription, LocalDate.now().plusDays(1)
        ));
        patientService.viewAppointmentOutcome(patient1);

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
        //patientService.viewAppointments();

        // Test Case 22: View and Manage Medication Inventory
        System.out.println("\nTest Case 22: View and Manage Medication Inventory");
        inventoryManager.updateMedicineStock("Panadol", 30);
        inventoryManager.viewMedicines();

        // Test Case 23: Approve Replenishment Requests
        System.out.println("\nTest Case 23: Approve Replenishment Requests");
        inventoryManager.approveReplenishment("R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMHHmmss")));
        inventoryManager.viewMedicines();

        // ========================== Login System and Password Management ==========================
        System.out.println("\n=== Login System and Password Management ===");

        // Test Case 25: First-Time Login and Password Change
        System.out.println("Test Case 25: First-Time Login and Password Change");
        if (patient1.login("P001", "patientPwd")) {
            patient1.changePassword("newPatientPwd");
            System.out.println(patient1.isLoggedIn());
            patient1.logout();
        }
        System.out.println(patient1.isLoggedIn());
        patient1.login("P001", "newPatientPwd");
        System.out.println(patient1.isLoggedIn());
        patient1.logout();

        // Test Case 26: Login with Incorrect Credentials
        System.out.println("\nTest Case 26: Login with Incorrect Credentials");
        if (!patient1.login("P001", "wrongPwd")) {
            System.out.println("Login failed with incorrect password.");
        }

        
        System.out.println("");
        scanner.close();
        pharmacist1.displayMenu();
        doctor1.displayMenu();
        patient1.displayMenu();
        admin.displayMenu();
    }
}
