import java.time.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Appointment> allAppointments = new ArrayList<>();
        List<User> sharedUserList = new ArrayList<>();
        List<Staff> initialStaffList = new ArrayList<>();
        // Initialize managers 
        IMedicineManager medicineManager = new MedicineManager();
        IPrescriptionManager prescriptionManager = new PrescriptionManager(null);
        IPatientManager patientManager = new PatientManager(null);
        IDoctorManager doctorManager = new DoctorManager(null, null);
        IPharmacistManager pharmacistManager = new PharmacistManager(null, null);
        IAppointmentManager appointmentManager = new AppointmentManager(null, null, null);
        UserManager userManager = new UserManager(sharedUserList, doctorManager, appointmentManager, medicineManager, prescriptionManager);
        IStaffManager staffManager = new StaffManager(initialStaffList, sharedUserList, userManager, medicineManager, prescriptionManager, doctorManager);

        TimeSlot slot1 = new TimeSlot(LocalDate.now().plusDays(1), LocalTime.of(10, 0));
        TimeSlot slot2 = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(10, 0));
        

        prescriptionManager.setMedicineManager(medicineManager);
        patientManager.setAppointmentManager(appointmentManager);
        pharmacistManager.setMedicineManager(medicineManager);
        pharmacistManager.setPrescriptionManager(prescriptionManager);
        appointmentManager.setAppList(allAppointments);
        appointmentManager.setDoctorManager(doctorManager);
        appointmentManager.setPatientManager(patientManager);
        doctorManager.setStaffManager(staffManager);
        doctorManager.setPrescriptionManager(prescriptionManager);



        String staffFilePath = "Project\\Staff_List.csv";  
        String patientFilePath = "Project\\Patient_List.csv";  
        String medicineFilePath = "Project\\Medicine_List.csv";  

        CSVImportManager.importStaffData(staffFilePath, staffManager, medicineManager, pharmacistManager, doctorManager, prescriptionManager);
        CSVImportManager.importPatientData(patientFilePath, patientManager, appointmentManager);
        CSVImportManager.importMedicineData(medicineFilePath, medicineManager);

        sharedUserList.addAll(staffManager.getAllStaff());
        sharedUserList.addAll(patientManager.getAllPatientsPrv());


        Doctor doctor1 = doctorManager.findDoctorById("D001");
        doctor1.addAvailability(slot1);
        doctor1.addAvailability(slot2);

        userManager.loginUser();  
    }
}

        /* 
        Scanner scanner = new Scanner(System.in);
        List<Appointment> allAppointments = new ArrayList<>();
        // Initialize managers and services
        MedicineManager inventoryManager = new MedicineManager();
        StaffManager staffManager = new StaffManager();
        PrescriptionManager prescriptionManager = new PrescriptionManager(inventoryManager);
        PatientManager patientService = new PatientManager();
        DoctorManager doctorService = new DoctorManager();
        DiagnosisManager diagnosisManager = new DiagnosisManager();
        TreatmentManager treatmentManager = new TreatmentManager();
        PharmacistManager pharmacistManager = new PharmacistManager(prescriptionManager, inventoryManager);
        AppointmentManager appointmentManager = new AppointmentManager(doctorService, patientService, allAppointments);
        // Initialize roles
        Administrator admin = new Administrator("A001", "adminPwd", "Admin", "Female", "Administrator", 45, staffManager,inventoryManager);
        Doctor doctor1 = new Doctor("D001", "doctorPwd", "Doctor", "Male", "Doctor", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pharmaPwd", "Pharmacist", "Male", "Pharmacist", 35, pharmacistManager);
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

        // Test Case 2: Add Diagnosis and Treatment to Patient Record
        System.out.println("\nTest Case 2.5: Add Diagnosis and Treatment to Patient Record");
        Diagnosis diagnosis1 = new Diagnosis("D001", "Diabetes", LocalDate.now());
        diagnosisManager.addDiagnosis(diagnosis1);
        patient1.getMedicalRecord().addDiagnosis(diagnosis1);

        Treatment treatment1 = new Treatment("T001", "Insulin Therapy", LocalDate.now());
        treatmentManager.addTreatment(treatment1);
        patient1.getMedicalRecord().addTreatment(treatment1);

        // View Medical Record After Adding Diagnosis and Treatment
        System.out.println("\nView Medical Record After Adding Diagnosis and Treatment");
        patientService.viewMedicalRecord(patient1);
        // Test Case 3: View Available Appointment Slots
        System.out.println("\nTest Case 3: View Available Appointment Slots");
        appointmentManager.viewAvailableSlots(doctor1);

        // Test Case 4: Schedule an Appointment
        System.out.println("\nTest Case 4: Schedule an Appointment");
        appointmentManager.scheduleAppointment(patient1, doctor1, slot1);
        System.out.println("\nTesting when we add two of the same appt");
        appointmentManager.scheduleAppointment(patient1, doctor1, slot1);
        appointmentManager.viewAppointments(patient1);

        // Test Case 5: Reschedule an Appointment
        System.out.println("\nTest Case 5: Reschedule an Appointment");
        TimeSlot newSlot = new TimeSlot(LocalDate.now().plusDays(2), LocalTime.of(11, 0));
        doctorService.setAvailability(doctor1, newSlot);
        
        System.out.println("\nChecking Appts before");
        appointmentManager.viewAppointments(patient1);
        appointmentManager.viewAppointments(doctor1);
        appointmentManager.viewAvailableSlots(doctor1);
        appointmentManager.rescheduleAppointment(patient1, patient1.getAppointments().get(0), newSlot, doctor1);
        System.out.println("\nAppts after");
        appointmentManager.viewAppointments(patient1);
        appointmentManager.viewAvailableSlots(doctor1);
        appointmentManager.viewAppointments(patient1);

        // Test Case 6: Cancel an Appointment
        System.out.println("\nTest Case 6: Cancel an Appointment");
        appointmentManager.cancelAppointment(doctor1, patient1, patient1.getAppointments().get(0));
        appointmentManager.viewAppointments(patient1);
        appointmentManager.viewAvailableSlots(doctor1);
        
        // Test Case 7: View Scheduled Appointments
        System.out.println("\nTest Case 7: View Scheduled Appointments");
        // Simulate 2 more appointments before showing
        TimeSlot slot2 = new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(11, 0));
        TimeSlot slot3 = new TimeSlot(LocalDate.now().plusDays(4), LocalTime.of(9, 0));
        
        doctorService.setAvailability(doctor1, slot2);
        doctorService.setAvailability(doctor1, slot3);
        appointmentManager.viewAvailableSlots(doctor1);
        appointmentManager.scheduleAppointment(patient1, doctor1, slot2);
        appointmentManager.scheduleAppointment(patient1, doctor1, slot3);
        appointmentManager.viewAppointments(patient1);
        
        appointmentManager.viewAvailableSlots(doctor1);

        // Test Case 8: View Past Appointment Outcome Records
        System.out.println("\nTest Case 8: View Past Appointment Outcome Records");
        doctorService.assignPatient(doctor1, patient1.getUserId());
        appointmentManager.recordAppointmentOutcome(
            doctor1,
            patient1.getUserId(),
            patient1.getAppointments().get(0).getAppointmentID(),
            "Follow-up Cons",
            "Test1.",
            prescription
        );
        appointmentManager.viewAppointmentOutcome(patient1);

        // ========================== Doctor Actions ==========================
        System.out.println("\n=== Doctor Actions ===");

        // Test Case 9: View Patient Medical Records
        System.out.println("Test Case 9: View Patient Medical Records");
        doctorService.assignPatient(doctor1, patient1.getUserId());
        doctorService.viewPatientRecord(doctor1, patient1.getUserId());

        // Test Case 10: Update Patient Medical Records
        System.out.println("\nTest Case 10: Update Patient Medical Records");

        appointmentManager.modifyAppointmentOutcome(
            doctor1,
            patient1.getAppointments().get(0).getAppointmentID(),
            "Follow-up Consultation",
            "Test2.",
            prescription
        );
        doctorService.viewPatientRecord(doctor1, patient1.getUserId());

        // Test Case 11: View Personal Schedule
        System.out.println("\nTest Case 11: View Personal Schedule");
        appointmentManager.viewAppointments(doctor1);

        // Test Case 12: Set Availability for Appointments
        System.out.println("\nTest Case 12: Set Availability for Appointments");
        System.out.println("\nCheck TestCase 7");
        doctorService.setAvailability(doctor1, new TimeSlot(LocalDate.now().plusDays(3), LocalTime.of(10, 0)));

        // Test Case 13: Accept or Decline Appointment Requests
        System.out.println("\nTest Case 13: Accept or Decline Appointment Requests");
        Appointment appointment1 = new Appointment("APT001", patient1.getUserId(), doctor1.getUserId(), slot1, "Pending");
        appointmentManager.acceptAppointment(doctor1, appointment1);
        appointmentManager.viewAppointments(patient1);
        appointmentManager.viewAppointments(doctor1);
        appointmentManager.viewAvailableSlots(doctor1);
        appointmentManager.cancelAppointment(doctor1, patient1, appointment1);
        appointmentManager.viewAppointments(patient1);
        appointmentManager.viewAppointments(doctor1);
        appointmentManager.viewAvailableSlots(doctor1);

        // Test Case 14: View Upcoming Appointments
        System.out.println("\nTest Case 14: View Upcoming Appointments");
        TimeSlot slot4 = new TimeSlot(LocalDate.now().minusDays(3), LocalTime.of(11, 0));
        doctorService.setAvailability(doctor1, slot4);
        appointmentManager.viewAvailableSlots(doctor1);
        appointmentManager.scheduleAppointment(patient1, doctor1, slot4);
        appointmentManager.viewAppointments(doctor1);
        appointmentManager.viewUpcomingAppointments(doctor1);
        appointmentManager.viewAvailableSlots(doctor1);

        // Test Case 15: Record Appointment Outcome
        System.out.println("\nTest Case 15: Record Appointment Outcome");
        appointmentManager.viewAppointmentOutcome(patient1);
        appointmentManager.recordAppointmentOutcome(A
            doctor1,
            patient1.getUserId(),
            appointment1.getAppointmentID(),
            "Follow-up Consultation",
            "Routine check-up, patient stable.",
            prescription
        );
        appointmentManager.viewAppointmentOutcome(patient1);

        // ========================== Pharmacist Actions ==========================
        System.out.println("\n=== Pharmacist Actions ===");

        // Test Case 16: View Appointment Outcome Record
        System.out.println("Test Case 16: View Appointment Outcome Record");
        appointmentManager.viewAllAppointmentOutcome(pharmacist1);

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
        inventoryManager.viewReplenishmentRequests();

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
*/