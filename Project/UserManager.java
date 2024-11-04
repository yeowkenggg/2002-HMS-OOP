import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserManager {
    private List<User> users;
    private DoctorManager doctorManager;
    private AppointmentManager appointmentManager;

    public UserManager(List<User> users, DoctorManager doctorManager, AppointmentManager appointmentManager) {
        this.users = users;
        this.doctorManager = doctorManager;
        this.appointmentManager = appointmentManager;
    }

    public void setDoctorManager(DoctorManager dm){
        this.doctorManager = dm;
    }

    public void setAppointmentManager(AppointmentManager am){
        this.appointmentManager = appointmentManager;
    }

    public void loginUser() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
    
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
    
        for (User user : users) {
            //used for debugging
            //System.out.println("Checking User ID: " + user.getUserId()); 
            //System.out.println("Stored Password: " + user.getPassword()); 
            if (user.getUserId().equals(userId) && user.checkPassword(password)) {
                user.setLoggedIn(true);
                System.out.println("Login successful for user: " + user.getName());
                // Display appropriate menu based on user type
                if (user instanceof Doctor) {
                    handleDoctorMenu((Doctor) user);
                } else if (user instanceof Administrator) {
                    handleAdminMenu((Administrator) user);
                } else if (user instanceof Pharmacist) {
                    handlePharmacistMenu((Pharmacist) user);
                } else if (user instanceof Patient) {
                    handlePatientMenu((Patient) user);
                }
                return; // Exit after successful login
            }
        }
        System.out.println("Invalid credentials. Please try again.");
        loginUser(); // Retry login
    }

    public List<User> getUsers() {
        return users;
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
                        doctor.viewPatientMedicalRecords();
                        break;
                    case 2:
                        doctor.updatePatientMedicalRecords();
                        break;
                    case 3:
                        doctor.viewPersonalSchedule();
                        break;
                    case 4:
                        doctor.setAvailability();
                        break;
                    case 5:
                        doctor.acceptOrDeclineAppointmentRequests();
                        break;
                    case 6:
                        doctor.viewUpcomingAppointments();
                        break;
                    case 7:
                        doctor.recordAppointmentOutcome();
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
                        System.out.print("Enter ID to update status: ");
                        String prescriptionID = scanner.nextLine();
                        pharmacist.updatePrescriptionStatus(prescriptionID);
                        break;
                    case 4:
                        pharmacist.viewInventory();
                        break;
                    case 5:
                        System.out.print("Enter Medicine Name for Replenishment: ");
                        String medicineName = scanner.nextLine();
                        System.out.print("Enter Amount to Replenish: ");
                        int amt = scanner.nextInt();
                        pharmacist.replenishmentRequest(medicineName, amt);
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
                scanner.nextLine();  // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the incorrect input
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
                    // View available appointment slots for a selected doctor
                    List<Doctor> allDoctors = doctorManager.getAllDoctors();
                    if (allDoctors.isEmpty()) {
                        System.out.println("No doctors available.");
                    } else {
                        System.out.println("\n--- Available Doctors ---");
                        for (int i = 0; i < allDoctors.size(); i++) {
                            System.out.println(i + ": Dr. " + allDoctors.get(i).getName() + " (ID: " + allDoctors.get(i).getUserId() + ")");
                        }
    
                        System.out.print("Enter the index of the doctor to view available slots: ");
                        int doctorIndex = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
    
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
                            System.out.println("Invalid doctor index. Please try again.");
                        }
                    }
                    break;
    
                case 4: 
                    // Schedule an appointment
                    List<Doctor> doctors = doctorManager.getAllDoctors();
                    if (doctors.isEmpty()) {
                        System.out.println("No doctors available.");
                    } else {
                        System.out.println("\n--- Available Doctors ---");
                        for (int i = 0; i < doctors.size(); i++) {
                            System.out.println(i + ": Dr. " + doctors.get(i).getName() + " (ID: " + doctors.get(i).getUserId() + ")");
                        }
    
                        System.out.print("Enter the index of the doctor to schedule an appointment: ");
                        int doctorIdx = scanner.nextInt();
                        scanner.nextLine();  // Consume newline
    
                        if (doctorIdx >= 0 && doctorIdx < doctors.size()) {
                            Doctor selectedDoctor = doctors.get(doctorIdx);
                            List<TimeSlot> slots = selectedDoctor.getAvailability();
                            if (slots.isEmpty()) {
                                System.out.println("No available slots for Dr. " + selectedDoctor.getName());
                            } else {
                                System.out.println("\n--- Available Slots for Dr. " + selectedDoctor.getName() + " ---");
                                for (int i = 0; i < slots.size(); i++) {
                                    System.out.println(i + ": " + slots.get(i));
                                }
    
                                System.out.print("Enter the index of available time slot to schedule: ");
                                int slotIndex = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
    
                                if (slotIndex >= 0 && slotIndex < slots.size()) {
                                    TimeSlot selectedSlot = slots.get(slotIndex);
                                    patient.scheduleAppointment(selectedDoctor, selectedSlot);
                                } else {
                                    System.out.println("Invalid index. Please try again.");
                                }
                            }
                        } else {
                            System.out.println("Invalid doctor index. Please try again.");
                        }
                    }
                    break;
    
                case 5:
                    // Reschedule an appointment by selecting from a list of appointments
                    List<Appointment> appointments = patient.getAppointments();
                    if (appointments.isEmpty()) {
                        System.out.println("No appointments to reschedule.");
                    } else {
                        System.out.println("\n--- Your Scheduled Appointments ---");
                        for (int i = 0; i < appointments.size(); i++) {
                            System.out.println(i + ": " + appointments.get(i));
                        }
                        System.out.print("Enter the index of the appointment to reschedule: ");
                        int appointmentIndex = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
    
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
                                    int newSlotIndex = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
    
                                    if (newSlotIndex >= 0 && newSlotIndex < availableSlots.size()) {
                                        TimeSlot newTimeSlot = availableSlots.get(newSlotIndex);
                                        patient.rescheduleAppointment(appointmentToReschedule, newTimeSlot, doctorForReschedule);
                                    } else {
                                        System.out.println("Invalid slot index.");
                                    }
                                }
                            } else {
                                System.out.println("Doctor not found.");
                            }
                        } else {
                            System.out.println("Invalid appointment index.");
                        }
                    }
                    break;
    
                case 6:
                    // Cancel an appointment by selecting from a list of appointments
                    List<Appointment> allAppointments = patient.getAppointments();
                    if (allAppointments.isEmpty()) {
                        System.out.println("No scheduled appointments found to cancel.");
                    } else {
                        System.out.println("\n--- Your Scheduled Appointments ---");
                        for (int i = 0; i < allAppointments.size(); i++) {
                            System.out.println(i + ": " + allAppointments.get(i));
                        }
                        System.out.print("Enter the index of the appointment to cancel: ");
                        int appointmentIdx = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
    
                        if (appointmentIdx >= 0 && appointmentIdx < allAppointments.size()) {
                            Appointment appointmentToCancel = allAppointments.get(appointmentIdx);
                            patient.cancelAppointment(appointmentToCancel);
                        } else {
                            System.out.println("Invalid index. Please try again.");
                        }
                    }
                    break;
    
                case 7:
                    patient.viewScheduledAppointments();
                    break;
    
                case 8:
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
    
}