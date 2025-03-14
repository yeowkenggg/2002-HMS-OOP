import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The main logic of the entire application
 * Here, it will manage the different menu for different roles and providing the core application functionality 
 */
public class UserManager {
    private List<User> users;
    private IDoctorManager doctorManager;
    private IAppointmentManager appointmentManager;
    private IMedicineManager medicineManager;
    private IPrescriptionManager prescriptionManager;

    //delcaring a regex to detect email for contactInfo update
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Constructor for UserManager
     * @param users list of user
     * @param doctorManager manager responsible for doctor-related operations
     * @param appointmentManager manager responsible for appointment-related operations
     * @param medicineManager manager responsible for medicine-related operations
     * @param prescriptionManager manager responsible for prescription-related operations
     */
    public UserManager(List<User> users, IDoctorManager doctorManager, IAppointmentManager appointmentManager, 
                       IMedicineManager medicineManager, IPrescriptionManager prescriptionManager) {
        this.users = users;
        this.doctorManager = doctorManager;
        this.appointmentManager = appointmentManager;
        this.medicineManager = medicineManager;
        this.prescriptionManager = prescriptionManager; 
    }

    /**
     * set method to set medicine manager
     * @param mm the medicine manager
     */
    public void setMedicineManager(MedicineManager mm) {
        this.medicineManager = mm;
    }

    /**
     * set method to set doctor manager
     * @param dm the doctor manager
     */
    public void setDoctorManager(DoctorManager dm){
        this.doctorManager = dm;
    }

    /**
     * set method to set appointment manager
     * @param am the appointment manager
     */
    public void setAppointmentManager(AppointmentManager am){
        this.appointmentManager = am;
    }

    /**
     * prompts user for login userId and password and initiate the session based on the role of the credentials
     * it will require the user to change password upon the first logon
     * displaying the menu bsaed on the user role
     */
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

    /**
     * manages the doctor menu, providing options to navigate and its logic implementation
     * @param doctor the logged in doctor
     */
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
                            String patientID = patientIDsForUpdate.get(i);
                            MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
                            if (record != null) {
                                System.out.println(i + ": Patient Name: " + record.getPatientName() + ", ID: " + patientID);
                            }
                            
                            
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
                                    String diagnosisID = scanner.nextLine().trim();
                                    if (diagnosisID.isEmpty()) {
                                        System.out.println("Diagnosis ID cannot be empty. Returning to the previous menu.");
                                        return; 
                                    }
                            
                                    System.out.print("Enter Diagnosis Details: ");
                                    String details = scanner.nextLine().trim();
                                    if (details.isEmpty()) {
                                        System.out.println("Diagnosis Details cannot be empty. Returning to the previous menu.");
                                        return; 
                                    }
                            
                                    doctorManager.addDiagnosis(selectedPatientID, diagnosisID, details);
                                }
                                case 2 -> {
                                    System.out.print("Enter Treatment ID: ");
                                    String treatmentID = scanner.nextLine().trim();
                                    if (treatmentID.isEmpty()) {
                                        System.out.println("Treatment ID cannot be empty. Returning to the previous menu.");
                                        return; 
                                    }
                            
                                    System.out.print("Enter Treatment Details: ");
                                    String details = scanner.nextLine().trim();
                                    if (details.isEmpty()) {
                                        System.out.println("Treatment Details cannot be empty. Returning to the previous menu.");
                                        return; 
                                    }
                            
                                    doctorManager.addTreatment(selectedPatientID, treatmentID, details);
                                }
                                case 3 -> {
                                    String prescriptionID = "Pres" + System.currentTimeMillis();
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
                            System.out.println("No available timeslots.");
                        } else {
                            for (TimeSlot slot : availableSlots) {
                                System.out.println(slot);
                            }
                        }
                    
                        List<Appointment> upcomingAppointments = doctor.getAppointments().stream()
                            .filter(Appointment::isUpcoming)
                            .toList();
                    
                        System.out.println("\n--- Upcoming Appointments ---");
                        if (upcomingAppointments.isEmpty()) {
                            System.out.println("No upcoming appointments.");
                        } else {
                            for (Appointment appointment : upcomingAppointments) {
                                System.out.println("Appointment ID: " + appointment.getAppointmentID() +
                                                   ", Patient ID: " + appointment.getPatientID() +
                                                   ", Scheduled Time: " + appointment.getTimeSlot().getDate() +
                                                   " @ " + appointment.getTimeSlot().getTime()+
                                                   " Status: " +appointment.getStatus());
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
                        System.out.println("New availability added for Dr." + doctor.getName() + ": " + newSlot);
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
                        scanner.nextLine(); 
                
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
                    
                            System.out.print("Do you want to add a prescription? (Y/N): ");
                            String prescriptionYN = scanner.nextLine();
                            
                            if (prescriptionYN.equalsIgnoreCase("Y")) {
                                String prescriptionID = "Pres" + System.currentTimeMillis();
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
                                        int quantity;
                    
                                        while (true) {
                                            System.out.print("Enter quantity for " + selectedMedicine.getName() + ": ");
                                            try {
                                                quantity = scanner.nextInt();
                                                scanner.nextLine();
                                                if (quantity > 0) { 
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
                    
                                    appointmentManager.recordAppointmentOutcome(
                                        doctor, selectedAppointment.getPatientID(), selectedAppointment.getAppointmentID(),
                                        services, notes, prescription
                                    );
                    
                                    prescriptionManager.addPrescription(prescription);
                                    selectedAppointment.setStatus("Completed");
                                    System.out.println("Appointment outcome recorded successfully.");
                                }
                            } else if (prescriptionYN.equalsIgnoreCase("N")) {
                                appointmentManager.recordAppointmentOutcome(
                                    doctor, selectedAppointment.getPatientID(), selectedAppointment.getAppointmentID(),
                                    services, notes, null
                                );
                                selectedAppointment.setStatus("Completed");
                                System.out.println("Appointment outcome recorded successfully without a prescription.");
                            } else {
                                System.out.println("Invalid input. Returning to the previous menu.");
                                break; 
                            }
                        } else {
                            System.out.println("Invalid appointment index. Returning to the previous menu.");
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

    /**
     * manages the admin menu, providing options to navigate and its logic implementation
     * @param admin the logged in admin
     */
    private void handleAdminMenu(Administrator admin) {
        Scanner scanner = new Scanner(System.in);
        while (admin.isLoggedIn()) {
            try {
                admin.displayMenu();
                System.out.print("Choose an option: ");
                
                String input = scanner.nextLine().trim(); 

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue; 
                }
    
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
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); 
            }
        }
    }
    
    /**
     * manages the pharmacist menu, providing options to navigate and its logic implementation
     * @param pharmacist the logged in pharmacist
     */
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
                        .toList();
                        if (prescriptions.isEmpty()) {
                            System.out.println("No prescriptions available.");
                        } else {
                            System.out.println("\n--- Prescription Records ---");
                            for (int i = 0; i < prescriptions.size(); i++) {
                                System.out.println(i + ": " + prescriptions.get(i));
                            }
                        
                            while (true) {
                                System.out.print("Enter the index of the prescription to update status (or 'E' to exit): ");
                                String input = scanner.nextLine().trim();
                        
                                if (input.equalsIgnoreCase("E")) {
                                    System.out.println("Exiting to previous menu.");
                                    break; 
                                }
                                try {
                                    int prescriptionIndex = Integer.parseInt(input);
                        
                                    if (prescriptionIndex >= 0 && prescriptionIndex < prescriptions.size()) {
                                        Prescription selectedPrescription = prescriptions.get(prescriptionIndex);
                                        pharmacist.updatePrescriptionStatus(selectedPrescription.getPrescriptionID());
                                        System.out.println("Prescription status updated for ID: " + selectedPrescription.getPrescriptionID());
                                        break; 
                                    } else {
                                        System.out.println("Invalid index. Please select a valid prescription index.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid number or 'E' to exit.");
                                }
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
                        } else {
                            System.out.println("\n--- Medicine Inventory ---");
                            for (int i = 0; i < inventory.size(); i++) {
                                System.out.println(i + ": " + inventory.get(i).getName() + " - Current Stock: " + inventory.get(i).getStock());
                            }
                        
                            while (true) {
                                System.out.print("Enter the index of the medicine for replenishment (or 'E' to exit): ");
                                String input = scanner.nextLine().trim();
                        
                                if (input.equalsIgnoreCase("E")) {
                                    System.out.println("Exiting to previous menu.");
                                    break; 
                                }
                        
                                try {
                                    int medicineIndex = Integer.parseInt(input);
                        
                                    if (medicineIndex >= 0 && medicineIndex < inventory.size()) {
                                        Medicine selectedMedicine = inventory.get(medicineIndex);
                        
                                        while (true) {
                                            System.out.print("Enter amount to replenish for " + selectedMedicine.getName() + " (or 'E' to exit): ");
                                            input = scanner.nextLine().trim();
                        
                                            if (input.equalsIgnoreCase("E")) {
                                                System.out.println("Exiting to previous menu.");
                                                break; 
                                            }
                        
                                            try {
                                                int replenishAmount = Integer.parseInt(input);
                        
                                                if (replenishAmount > 0) {
                                                    pharmacist.replenishmentRequest(selectedMedicine.getName(), replenishAmount);
                                                    
                                                    break; 
                                                } else {
                                                    System.out.println("Please enter a positive amount to replenish.");
                                                }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Please enter a valid number or 'E' to exit.");
                                            }
                                        }
                                        break; 
                                    } else {
                                        System.out.println("Invalid index. Please select a valid medicine index.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a valid number or 'E' to exit.");
                                }
                            }
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

    /**
     * manages the patient menu, providing options to navigate and its logic implementation
     * @param patient the logged in patient
     */
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
                    System.out.print("Enter new contact information (email): (empty to keep current) ");
                    String newContact = scanner.nextLine();
                    if (newContact.isEmpty()) {
                        newContact = patient.getContactInfo();
                    } else if (!isValidEmail(newContact)) {
                        System.out.println("Invalid email format. Please enter a valid email address.");
                        break;
                    }
                
                    System.out.print("Enter new phone number: (empty to keep current) ");
                    String phone;
                    int newPhone;
                    try {
                        phone = scanner.nextLine();
                        if (!phone.isEmpty()) {
                            newPhone = Integer.parseInt(phone);
                        } else {
                            newPhone = patient.getPhoneNumber();
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        break;
                    }
                
                    patient.updateContactInfo(newContact, newPhone);
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

                    System.out.print("Enter the index of the doctor to view available slots: ");
                    String appointmentCheckInput = scanner.nextLine().trim();

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
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Returning to the previous menu.");
                        break;
                    }

                    break;
    
                case 4: 
                    // Schedule appointment
                    List<Doctor> doctors = doctorManager.getAllDoctors();
                    if (doctors.isEmpty()) {
                        System.out.println("No doctors available.");
                    } else {
                        System.out.println("\n--- Available Doctors ---");
                        for (int i = 0; i < doctors.size(); i++) {
                            System.out.println(i + ": Dr. " + doctors.get(i).getName() + " (ID: " + doctors.get(i).getUserId() + ")");
                        }
                
                        int doctorIdx;
                        boolean validDoctorSelected = false;
                
                        System.out.print("Enter the index of the doctor to schedule an appointment: ");
                        try {
                            doctorIdx = Integer.parseInt(scanner.nextLine().trim());
                            if (doctorIdx >= 0 && doctorIdx < doctors.size()) {
                                validDoctorSelected = true;
                            } else {
                                System.out.println("Invalid index. Returning to the previous menu.");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Returning to the previous menu.");
                            break;
                        }
                
                        if (validDoctorSelected) {
                            Doctor selectedDoctor = doctors.get(doctorIdx);
                            List<TimeSlot> slots = selectedDoctor.getAvailability();
                
                            if (slots.isEmpty()) {
                                System.out.println("No available slots for Dr. " + selectedDoctor.getName());
                            } else {
                                System.out.println("\n--- Available Slots for Dr. " + selectedDoctor.getName() + " ---");
                                for (int i = 0; i < slots.size(); i++) {
                                    System.out.println(i + ": " + slots.get(i));
                                }
                
                                int slotIndex;
                                System.out.print("Enter the index of available time slot to schedule: ");
                                try {
                                    slotIndex = Integer.parseInt(scanner.nextLine().trim());
                
                            
                                    if (slotIndex >= 0 && slotIndex < slots.size()) {
                                        TimeSlot selectedSlot = slots.get(slotIndex);
                                        patient.scheduleAppointment(selectedDoctor, selectedSlot);
                                        System.out.println("Scheduled appointment for " + patient.getName() + 
                                                           " with Dr. " + selectedDoctor.getName() + " at " + selectedSlot);
                                    } else {
                                        System.out.println("Invalid slot index. Returning to the previous menu.");
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Returning to the previous menu.");
                                }
                            }
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
                        break; 
                    }

                    System.out.println("\n--- Your Scheduled Appointments ---");
                    for (int i = 0; i < appointments.size(); i++) {
                        System.out.println(i + ": " + appointments.get(i));
                    }

                    System.out.print("Enter the index of the appointment to reschedule: ");
                    try {
                        int appointmentIndex = Integer.parseInt(scanner.nextLine().trim());
                        if (appointmentIndex < 0 || appointmentIndex >= appointments.size()) {
                            System.out.println("Invalid appointment index. Returning to the previous menu.");
                            break; 
                        }

                        Appointment appointmentToReschedule = appointments.get(appointmentIndex);
                        Doctor doctorForReschedule = doctorManager.findDoctorById(appointmentToReschedule.getDoctorID());

                        if (doctorForReschedule == null) {
                            System.out.println("Doctor not found. Returning to the previous menu.");
                            break; 
                        }

                        List<TimeSlot> availableSlots = doctorForReschedule.getAvailability();

                        if (availableSlots.isEmpty()) {
                            System.out.println("No available slots for Dr. " + doctorForReschedule.getName() + ". Returning to the previous menu.");
                            break; 
                        }

                        System.out.println("\n--- Available Slots for Dr. " + doctorForReschedule.getName() + " ---");
                        for (int i = 0; i < availableSlots.size(); i++) {
                            System.out.println(i + ": " + availableSlots.get(i));
                        }

                        System.out.print("Enter the index of available time slot to reschedule: ");
                        try {
                            int newSlotIndex = Integer.parseInt(scanner.nextLine().trim());
                            if (newSlotIndex < 0 || newSlotIndex >= availableSlots.size()) {
                                System.out.println("Invalid slot index. Returning to the previous menu.");
                                break; 
                            }

                            TimeSlot newTimeSlot = availableSlots.get(newSlotIndex);
                            patient.rescheduleAppointment(appointmentToReschedule, newTimeSlot, doctorForReschedule);
                            System.out.println("Scheduled appointment for " + patient.getName() +
                                            " with Dr. " + doctorForReschedule.getName() + " at " + newTimeSlot);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Returning to the previous menu.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Returning to the previous menu.");
                        break; 
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
                            int appointmentIdx = Integer.parseInt(appointmentCancelInput);  
                            if (appointmentIdx >= 0 && appointmentIdx < appointmentsToCancel.size()) {
                                Appointment appointmentToCancel = appointmentsToCancel.get(appointmentIdx);
                                patient.cancelAppointment(appointmentToCancel);
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

    /**
     * returns a list of all users
     * @return list of all users
     */
    public List<User> getUsers() {
        return users;
    }


    /**
     * prompts user for password change upon their first logon
     * @param user the user that require to change password
     */
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
    
    /**
     * retrieve an input from user and checking if its an integer
     * @param scanner the scanner that is declared for user input
     * @return a valid integer
     */
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
    
    /**
     * validate an email which is checked against a regex
     * @param email the email to checked/validated
     * @return boolean whether if the email is of correct format
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    
}