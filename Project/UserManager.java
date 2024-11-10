import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users;
    private DoctorManager doctorManager;
    private AppointmentManager appointmentManager;
    private MedicineManager medicineManager;
    private PrescriptionManager prescriptionManager;

    public UserManager(List<User> users, DoctorManager doctorManager, AppointmentManager appointmentManager, 
                       MedicineManager medicineManager, PrescriptionManager prescriptionManager) {
        this.users = users;
        this.doctorManager = doctorManager;
        this.appointmentManager = appointmentManager;
        this.medicineManager = medicineManager;
        this.prescriptionManager = prescriptionManager; 
    }

    public void setMedicineManager(MedicineManager mm) {
        this.medicineManager = mm;
    }
    public void setDoctorManager(DoctorManager dm){
        this.doctorManager = dm;
    }

    public void setAppointmentManager(AppointmentManager am){
        this.appointmentManager = am;
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter User ID (or 'E' to exit): ");
        String userId = scanner.nextLine();
        if (userId.equalsIgnoreCase("E")) {
            System.out.println("Exiting login.");
            return;
        }
    
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
    
        for (User user : users) {
            //used for debugging
            //System.out.println("Checking User ID: " + user.getUserId()); 
            //System.out.println("Stored Password: " + user.getPassword()); 
            if (user.getUserId().equals(userId) && user.checkPassword(password)) {
                user.setLoggedIn(true);
                System.out.println("Login successful for user: " + user.getName());
                
                //users must change password on their first login
                if (user.isFirstLogin()) {
                    System.out.println("This is your first login. You need to change your password.");
                    promptPasswordChange(user);
                }

                // display appropriate menu based on user type
                if (user instanceof Doctor) {
                    handleDoctorMenu((Doctor) user);
                } else if (user instanceof Administrator) {
                    handleAdminMenu((Administrator) user);
                } else if (user instanceof Pharmacist) {
                    handlePharmacistMenu((Pharmacist) user);
                } else if (user instanceof Patient) {
                    handlePatientMenu((Patient) user);
                }
                return; 
            }
        }
        System.out.println("Invalid credentials. Please try again.");
        loginUser(); // repeat logins if failed
    }

 
    private void handleDoctorMenu(Doctor doctor) {
        Scanner scanner = new Scanner(System.in);
        while (doctor.isLoggedIn()) {
            try {
                doctor.displayMenu();
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        List<String> patientIDs = doctor.getAssignedPatientIDs();
                        if (patientIDs.isEmpty()) {
                            System.out.println("No patients assigned.");
                            break;
                        }
                    
                        System.out.println("\n--- Assigned Patients ---");
                        for (int i = 0; i < patientIDs.size(); i++) {
                            String patientID = patientIDs.get(i);
                            MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
                            
                            if (record != null) {
                                System.out.println(i + ": Patient Name: " + record.getPatientName() + ", ID: " + patientID);
                            } else {
                                System.out.println(i + ": Patient ID: " + patientID + " (Record not found)");
                            }
                        }
                    
                        System.out.print("Choose patient index to view medical records: ");
                        int index = scanner.nextInt();
                        if (index >= 0 && index < patientIDs.size()) {
                            doctor.viewPatientMedicalRecord(patientIDs.get(index));
                        } else {
                            System.out.println("Invalid index.");
                        }
                        break;
                    case 2:
                        List<String> patientIDsForUpdate = doctor.getAssignedPatientIDs();
                        if (patientIDsForUpdate.isEmpty()) {
                            System.out.println("No patients assigned.");
                            break;
                        }
                        System.out.println("\n--- Assigned Patients ---");
                        for (int i = 0; i < patientIDsForUpdate.size(); i++) {
                            System.out.println(i + ": Patient ID: " + patientIDsForUpdate.get(i));
                        }
                        System.out.print("Choose patient index to update medical records: ");
                        int patientIndex = scanner.nextInt();
                        scanner.nextLine();
                    
                        if (patientIndex >= 0 && patientIndex < patientIDsForUpdate.size()) {
                            String selectedPatientID = patientIDsForUpdate.get(patientIndex);
                            
                            System.out.print("1. Add Diagnosis\n2. Add Treatment\n3. Add Prescription\nChoose an option: ");
                            int recordType = scanner.nextInt();
                            scanner.nextLine(); 
                            System.out.println("");
                            switch (recordType) {
                                case 1 -> {
                                    System.out.print("Enter Diagnosis ID: ");
                                    String diagnosisID = scanner.nextLine();
                                    System.out.print("Enter Diagnosis Details: ");
                                    String details = scanner.nextLine();
                                    doctorManager.addDiagnosis(selectedPatientID, diagnosisID, details);
                                }
                                case 2 -> {
                                    System.out.print("Enter Treatment ID: ");
                                    String treatmentID = scanner.nextLine();
                                    System.out.print("Enter Treatment Details: ");
                                    String details = scanner.nextLine();
                                    doctorManager.addTreatment(selectedPatientID, treatmentID, details);
                                }
                                case 3 -> {
                                    System.out.print("Enter Prescription ID: ");
                                    String prescriptionID = scanner.nextLine();
                                    doctorManager.addPrescription(selectedPatientID, prescriptionID, medicineManager);
                                }
                                default -> System.out.println("Invalid choice.");
                            }
                        } else {
                            System.out.println("Invalid index.");
                        }
                        break;
                    
                    case 3:
                        List<TimeSlot> availableSlots = doctor.getAvailability();
                        System.out.println("\n--- Available Timeslots ---");
                        if (availableSlots.isEmpty()) {
                            System.out.println("No available timeslots set.");
                        } else {
                            for (TimeSlot slot : availableSlots) {
                                System.out.println(slot);
                            }
                        }
                        break;
                    case 4:
                        System.out.print("Enter availability date (YYYY-MM-DD): ");
                        String dateInput = scanner.nextLine();
                    
                        LocalDate date;
                        try {
                            date = LocalDate.parse(dateInput);
                            LocalDate today = LocalDate.now();
                    
                            // we check if the date is before today, no point to have a
                            // available slot in the past
                            if (date.isBefore(today)) {
                                System.out.println("Invalid date. Please enter a date that is today or later.");
                                break;
                            }
                    
                        } catch (Exception e) {
                            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            break;
                        }
                    
                        System.out.print("Enter time (HH:MM): ");
                        String timeInput = scanner.nextLine();
                    
                        LocalTime time;
                        try {
                            time = LocalTime.parse(timeInput);
                        } catch (Exception e) {
                            System.out.println("Invalid time format. Please use HH:MM.");
                            break;
                        }
                    
                        TimeSlot newSlot = new TimeSlot(date, time);
                        doctor.addAvailability(newSlot);
                        System.out.println("New availability detected for Dr." + doctor.getName() + ": " + newSlot);
                        break;

                    case 5:
                        List<Appointment> appointmentReq = doctor.getAppointments().stream()
                        .filter(appointment -> "Pending".equalsIgnoreCase(appointment.getStatus()))
                        .toList();
            
                        if (appointmentReq.isEmpty()) {
                            System.out.println("No appointments to manage.");
                            break;
                        }
                
                        System.out.println("\n--- Appointment Requests ---");
                        for (int i = 0; i < appointmentReq.size(); i++) {
                            System.out.println(i + ": " + appointmentReq.get(i));
                        }
                
                        System.out.print("Select appointment index to manage: ");
                        int appIndex = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                
                        if (appIndex >= 0 && appIndex < appointmentReq.size()) {
                            Appointment appointment = appointmentReq.get(appIndex);
                            System.out.print("Accept or Decline (A/D): ");
                            String AD = scanner.nextLine();
                            if ("A".equalsIgnoreCase(AD)) {
                                appointmentManager.acceptAppointment(doctor, appointment);
                            } else if ("D".equalsIgnoreCase(AD)) {
                                appointmentManager.declineAppointment(doctor, appointment);
                            } else {
                                System.out.println("Invalid choice.");
                            }
                        } else {
                            System.out.println("Invalid index.");
                        }
                        break;
                    case 6:
                        doctor.viewUpcomingAppointments();
                        break;
                    case 7:
                        List<Appointment> confirmedAppointments = doctor.getAppointments().stream()
                            .filter(appointment -> "Confirmed".equalsIgnoreCase(appointment.getStatus()))
                            .toList();
                    
                        if (confirmedAppointments.isEmpty()) {
                            System.out.println("No confirmed appointments available for recording an outcome.");
                            break;
                        }
                    
                        System.out.println("\n--- Confirmed Appointments ---");
                        for (int i = 0; i < confirmedAppointments.size(); i++) {
                            System.out.println(i + ": " + confirmedAppointments.get(i));
                        }
                    
                        System.out.print("Select appointment index to record an outcome: ");
                        int appointmentIndex = scanner.nextInt();
                        scanner.nextLine(); 
                    
                        if (appointmentIndex >= 0 && appointmentIndex < confirmedAppointments.size()) {
                            Appointment selectedAppointment = confirmedAppointments.get(appointmentIndex);
                    
                            System.out.print("Enter services provided: ");
                            String services = scanner.nextLine();
                    
                            System.out.print("Enter any additional notes: ");
                            String notes = scanner.nextLine();
                    
                            System.out.print("Enter Prescription ID: ");
                            String prescriptionID = scanner.nextLine();
                    
                            List<Medicine> medicines = new ArrayList<>();
                            List<Integer> qty = new ArrayList<>();

                            while (true) {
                                medicineManager.displayInventory();
                    
                                System.out.print("Enter the index of the medicine to add (or -1 to finish): ");
                                int medicineIndex;
                                try {
                                    medicineIndex = scanner.nextInt();
                                    scanner.nextLine(); 
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid number.");
                                    scanner.nextLine();
                                    continue;
                                }
                    
                                if (medicineIndex == -1) {
                                    break; 
                                }
                    
                                List<Medicine> inventory = medicineManager.getInventory();
                                if (medicineIndex >= 0 && medicineIndex < inventory.size()) {
                                    Medicine selectedMedicine = inventory.get(medicineIndex);

                                    System.out.print("Enter quantity for " + selectedMedicine.getName() + ": ");
                                    int quantity;
                                    while (true) {
                                        System.out.print("Enter quantity for " + selectedMedicine.getName() + ": ");
                                        try {
                                            quantity = scanner.nextInt();
                                            scanner.nextLine();
                                            if (quantity > 0) { //if doctor select a medicine, atleast 1 must be dispensed
                                                break; 
                                            } else {
                                                System.out.println("Quantity must be greater than 0. Please try again.");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid quantity. Please enter a valid number.");
                                            scanner.nextLine();
                                        }
                                    }

                                    if (selectedMedicine.getStock() >= quantity) {

                                        medicines.add(selectedMedicine);
                                        qty.add(quantity);
                                        System.out.println("Added " + quantity + " units of " + selectedMedicine.getName() + " to prescription.");
                                    } else {
                                        System.out.println("Insufficient stock for " + selectedMedicine.getName() + ". Available: " + selectedMedicine.getStock());
                                    }
                                } else {
                                    System.out.println("Invalid index. Please select a valid medicine from the inventory.");
                                }
                            }

                            if (medicines.isEmpty()) {
                                System.out.println("No valid medicines selected. Prescription was not created.");
                            } else {
                                Prescription prescription = new Prescription(prescriptionID, medicines, qty, "Pending");

                                // add appointment outcome and add prescription
                                appointmentManager.recordAppointmentOutcome(
                                        doctor, selectedAppointment.getPatientID(), selectedAppointment.getAppointmentID(),
                                        services, notes, prescription
                                );

                                // add the prescription to PrescriptionManager
                                prescriptionManager.addPrescription(prescription);
                                selectedAppointment.setStatus("Completed");
                                System.out.println("Appointment outcome recorded successfully.");
                            }
                        } else {
                            System.out.println("Invalid appointment index. Please try again.");
                        }
                        break;
                    
                    case 8:
                        doctor.logout();
                        loginUser();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private void handleAdminMenu(Administrator admin) {
        Scanner scanner = new Scanner(System.in);
        while (admin.isLoggedIn()) {
            try {
                admin.displayMenu();
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        admin.manageHospitalStaff();
                        break;
                    case 2:
                        admin.manageMedicationInventory();
                        break;
                    case 3:
                        admin.approveReplenishmentRequests();
                        break;
                    case 4:
                        admin.viewAppointmentDetails();
                        break;
                    case 5:
                        admin.logout();   
                        loginUser(); 
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    

    private void handlePharmacistMenu(Pharmacist pharmacist) {
        Scanner scanner = new Scanner(System.in);
        while (pharmacist.isLoggedIn()) {
            try {
                pharmacist.displayMenu();
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        pharmacist.viewPrescriptionRecords();
                        break;
                    case 2:
                        pharmacist.viewPendingPrescriptionRecords();
                        break;
                    case 3:
                        List<Prescription> prescriptions = pharmacist.getPrescriptionManager().getAllPrescriptions().stream()
                        .filter(prescription -> "Pending".equalsIgnoreCase(prescription.getStatus()))
                        .toList();;
                        if (prescriptions.isEmpty()) {
                            System.out.println("No prescriptions available.");
                        } else {
                            System.out.println("\n--- Prescription Records ---");
                            for (int i = 0; i < prescriptions.size(); i++) {
                                System.out.println(i + ": " + prescriptions.get(i));
                            }
                            System.out.print("Enter the index of the prescription to update status: ");
                            int prescriptionIndex = getValidIntInput(scanner);

                            if (prescriptionIndex >= 0 && prescriptionIndex < prescriptions.size()) {
                                Prescription selectedPrescription = prescriptions.get(prescriptionIndex);
                                pharmacist.updatePrescriptionStatus(selectedPrescription.getPrescriptionID());
                            } else {
                                System.out.println("Invalid index. Please select a valid prescription index.");
                            }
                        }
                        break;
                    case 4:
                        pharmacist.viewInventory();
                        break;
                    case 5:
                        List<Medicine> inventory = medicineManager.getInventory();
                        if (inventory.isEmpty()) {
                            System.out.println("No medicines available for replenishment.");
                            break;
                        }
                    
                        System.out.println("\n--- Medicine Inventory ---");
                        for (int i = 0; i < inventory.size(); i++) {
                            System.out.println(i + ": " + inventory.get(i).getName() + " - Current Stock: " + inventory.get(i).getStock());
                        }
                    
                        System.out.print("Enter the index of the medicine for replenishment: ");
                        int medicineIndex = getValidIntInput(scanner); 
                    
                        if (medicineIndex >= 0 && medicineIndex < inventory.size()) {
                            Medicine selectedMedicine = inventory.get(medicineIndex);
                    
                            System.out.print("Enter amount to replenish for " + selectedMedicine.getName() + ": ");
                            int replenishAmount = getValidIntInput(scanner);
                    
                            pharmacist.replenishmentRequest(selectedMedicine.getName(), replenishAmount);
                        } else {
                            System.out.println("Invalid index. Please try again.");
                        }
                        break;
                    case 6:
                        pharmacist.viewReplenishmentRequests();
                        break;
                    case 7:
                        pharmacist.logout();     
                        loginUser();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }
    private void handlePatientMenu(Patient patient) {
        Scanner scanner = new Scanner(System.in);
        while (patient.isLoggedIn()) {
            patient.displayMenu();
            System.out.print("Choose an option: ");
    
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }
    
            switch (choice) {
                case 1:
                    patient.viewMedicalRecord();
                    break;
    
                case 2:
                    System.out.print("Enter new contact information: ");
                    String newContact = scanner.nextLine();
                    patient.updateContactInfo(newContact);
                    break;
    
                case 3: 
                    // view appointment by doctor
                    List<Doctor> allDoctors = doctorManager.getAllDoctors();
                    if (allDoctors.isEmpty()) {
                        System.out.println("No doctors available.");
                        return;
                    }

                    System.out.println("\n--- Available Doctors ---");
                    for (int i = 0; i < allDoctors.size(); i++) {
                        System.out.println(i + ": Dr. " + allDoctors.get(i).getName() + " (ID: " + allDoctors.get(i).getUserId() + ")");
                    }

                    System.out.print("Enter the index of the doctor to view available slots (or 'E' to exit): ");
                    String appointmentCheckInput = scanner.nextLine().trim();

                    if (appointmentCheckInput.equalsIgnoreCase("E")) {
                        System.out.println("Exiting to previous menu.");
                        return;
                    }

                    try {
                        int doctorIndex = Integer.parseInt(appointmentCheckInput);

                        if (doctorIndex >= 0 && doctorIndex < allDoctors.size()) {
                            Doctor selectedDoctor = allDoctors.get(doctorIndex);
                            List<TimeSlot> availableSlots = selectedDoctor.getAvailability();

                            if (availableSlots.isEmpty()) {
                                System.out.println("No available slots for Dr. " + selectedDoctor.getName());
                            } else {
                                System.out.println("\n--- Available Slots for Dr. " + selectedDoctor.getName() + " ---");
                                for (int i = 0; i < availableSlots.size(); i++) {
                                    System.out.println(i + ": " + availableSlots.get(i));
                                }
                            }
                        } else {
                            System.out.println("Invalid index. Returning to the previous menu.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Returning to the previous menu.");
                    }

                    break;
    
                case 4: 
                    // schedule appointment
                    List<Appointment> allAppointments = patient.getAppointments();
                    if (allAppointments.isEmpty()) {
                        System.out.println("No scheduled appointments found to cancel.");
                    } else {
                        System.out.println("\n--- Your Scheduled Appointments ---");
                        for (int i = 0; i < allAppointments.size(); i++) {
                            System.out.println(i + ": " + allAppointments.get(i));
                        }

                        System.out.print("Enter the index of the appointment to cancel: ");
                        
                        try {
                            int appointmentIdx = Integer.parseInt(scanner.nextLine().trim());  
                            if (appointmentIdx >= 0 && appointmentIdx < allAppointments.size()) {
                                Appointment appointmentToCancel = allAppointments.get(appointmentIdx);
                                patient.cancelAppointment(appointmentToCancel);
                                System.out.println("Appointment cancelled successfully.");
                            } else {
                                System.out.println("Invalid index. Please select a number from the list.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number or 'E' to exit.");
                        }
                    }

                    break;
    
                case 5:
                    // reschedule appointment
                    List<Appointment> appointments = patient.getAppointments().stream()
                    .filter(appointment -> "Pending".equalsIgnoreCase(appointment.getStatus()))
                    .toList();

                    if (appointments.isEmpty()) {
                        System.out.println("No appointments to reschedule.");
                    } else {
                        System.out.println("\n--- Your Scheduled Appointments ---");
                        for (int i = 0; i < appointments.size(); i++) {
                            System.out.println(i + ": " + appointments.get(i));
                        }
                        System.out.print("Enter the index of the appointment to reschedule: ");
                        int appointmentIndex = getValidIntInput(scanner);
                
                        if (appointmentIndex >= 0 && appointmentIndex < appointments.size()) {
                            Appointment appointmentToReschedule = appointments.get(appointmentIndex);
                            Doctor doctorForReschedule = doctorManager.findDoctorById(appointmentToReschedule.getDoctorID());
                
                            if (doctorForReschedule != null) {
                                List<TimeSlot> availableSlots = doctorForReschedule.getAvailability();
                                if (availableSlots.isEmpty()) {
                                    System.out.println("No available slots for Dr. " + doctorForReschedule.getName());
                                } else {
                                    System.out.println("\n--- Available Slots for Dr. " + doctorForReschedule.getName() + " ---");
                                    for (int i = 0; i < availableSlots.size(); i++) {
                                        System.out.println(i + ": " + availableSlots.get(i));
                                    }
                
                                    System.out.print("Enter the index of available time slot to reschedule: ");
                                    int newSlotIndex = getValidIntInput(scanner);
                
                                    if (newSlotIndex >= 0 && newSlotIndex < availableSlots.size()) {
                                        TimeSlot newTimeSlot = availableSlots.get(newSlotIndex);
                                        patient.rescheduleAppointment(appointmentToReschedule, newTimeSlot, doctorForReschedule);
                                        System.out.println("Rescheduled appointment to " + newTimeSlot);
                                    } else {
                                        System.out.println("Invalid slot index. Please try again.");
                                    }
                                }
                            } else {
                                System.out.println("Doctor not found.");
                            }
                        } else {
                            System.out.println("Invalid appointment index. Please try again.");
                        }
                    }
                    break;
            
    
                    case 6:
                    // cancel appt
                    List<Appointment> appointmentsToCancel = patient.getAppointments();
                    if (appointmentsToCancel.isEmpty()) {
                        System.out.println("No scheduled appointments found to cancel.");
                    } else {
                        System.out.println("\n--- Your Scheduled Appointments ---");
                        for (int i = 0; i < appointmentsToCancel.size(); i++) {
                            System.out.println(i + ": " + appointmentsToCancel.get(i));
                        }

                        System.out.print("Enter the index of the appointment to cancel: ");
                        
                        String appointmentCancelInput = scanner.nextLine().trim();  
                        
                        try {
                            int appointmentIdx = Integer.parseInt(appointmentCancelInput);  // Parse input as an integer if it's not 'E'
                            if (appointmentIdx >= 0 && appointmentIdx < appointmentsToCancel.size()) {
                                Appointment appointmentToCancel = appointmentsToCancel.get(appointmentIdx);
                                patient.cancelAppointment(appointmentToCancel);
                                System.out.println("Appointment cancelled successfully.");
                            } else {
                                System.out.println("Invalid index. Please select a number from the list.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                        }
                    }

                    break;
    
                case 7:
                    System.out.println("\n--- Your Scheduled Appointments ---");
                    patient.viewScheduledAppointments();
                    break;
    
                case 8:
                    System.out.println("\n--- Your Past Appointments ---");
                    patient.viewPastAppointmentOutcomes();
                    break;
    
                case 9:
                    patient.logout();
                    loginUser();
                    break;
    
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }
        }
    }
    public List<User> getUsers() {
        return users;
    }

    private void promptPasswordChange(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
    
        System.out.print("Confirm new password: ");
        String confirmPassword = scanner.nextLine();
    
        if (newPassword.equals(confirmPassword)) {
            user.changePassword(newPassword);
            user.setFirstLogin(false);
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Passwords do not match. Try again.");
            promptPasswordChange(user); 
        }
    }
    
    public int getValidIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }
    
}