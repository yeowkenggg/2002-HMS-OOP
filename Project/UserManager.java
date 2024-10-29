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
            if (user.getUserId().equals(userId) && user.checkPassword(password)) {
                // Successful login, set logged in status
                user.setLoggedIn(true);
                System.out.println("Login successful for user: " + user.getName());

                // Determine the type of user and display the appropriate menu
                if (user instanceof Doctor) {
                    handleDoctorMenu((Doctor) user);
                } else if (user instanceof Administrator) {
                    handleAdminMenu((Administrator) user);
                } else if (user instanceof Pharmacist) {
                    handlePharmacistMenu((Pharmacist) user);
                } else if (user instanceof Patient) {
                    handlePatientMenu((Patient) user);
                }
                return; // Exit after successful login and menu display
            }
            
        }

        // If we complete the loop without finding the user, it means login failed
        System.out.println("Invalid credentials. Please try again.");
        loginUser();
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
                        break;
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
                        admin.viewAppointmentsDetail();
                        break;
                    case 5:
                        admin.logout();
                        break;
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
                        break;
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

    private void handlePatientMenu(Patient patient) {
        Scanner scanner = new Scanner(System.in);
        while (patient.isLoggedIn()) {
            patient.displayMenu();
            System.out.print("Choose an option: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
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
                    System.out.print("Enter Doctor ID to view available appointment slots: ");
                    String doctorId = scanner.nextLine();
                    Doctor doctor = doctorManager.findDoctorById(doctorId);
                    if (doctor != null) {
                        List<TimeSlot> availableSlots = doctor.getAvailability();
                        if (availableSlots.isEmpty()) {
                            System.out.println("No available slots for this doctor.");
                        } else {
                            System.out.println("Available Slots for Dr. " + doctor.getName() + ":");
                            for (int i = 0; i < availableSlots.size(); i++) {
                                System.out.println((i + 1) + ". " + availableSlots.get(i));
                            }
                        }
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
    
                case 4:
                    // Schedule an appointment by choosing an available time slot
                    System.out.print("Enter Doctor ID: ");
                    String doctorIdForAppointment = scanner.nextLine();
                    Doctor doctorForAppointment = doctorManager.findDoctorById(doctorIdForAppointment);
                    if (doctorForAppointment != null) {
                        List<TimeSlot> availableSlots = doctorForAppointment.getAvailability();
                        if (availableSlots.isEmpty()) {
                            System.out.println("No available slots for this doctor.");
                        } else {
                            System.out.println("Available Slots for Dr. " + doctorForAppointment.getName() + ":");
                            for (int i = 0; i < availableSlots.size(); i++) {
                                System.out.println((i + 1) + ". " + availableSlots.get(i));
                            }
                            System.out.print("Choose an available slot by entering the corresponding number: ");
                            int slotIndex;
                            try {
                                slotIndex = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a number.");
                                scanner.nextLine(); // Clear the invalid input
                                break;
                            }
    
                            if (slotIndex < 1 || slotIndex > availableSlots.size()) {
                                System.out.println("Invalid slot selection.");
                            } else {
                                TimeSlot chosenSlot = availableSlots.get(slotIndex - 1);
                                patient.scheduleAppointment(doctorForAppointment, chosenSlot);
                            }
                        }
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
    
                case 5:
                    // Reschedule an appointment
                    System.out.print("Enter Appointment ID to reschedule: ");
                    String appointmentId = scanner.nextLine();
                    Appointment appointment = appointmentManager.findAppointmentById(appointmentId);
                    if (appointment != null) {
                        System.out.print("Enter Doctor ID for new appointment: ");
                        String doctorIdForReschedule = scanner.nextLine();
                        Doctor doctorForReschedule = doctorManager.findDoctorById(doctorIdForReschedule);
                        if (doctorForReschedule != null) {
                            List<TimeSlot> availableSlots = doctorForReschedule.getAvailability();
                            if (availableSlots.isEmpty()) {
                                System.out.println("No available slots for this doctor.");
                            } else {
                                System.out.println("Available Slots for Dr. " + doctorForReschedule.getName() + ":");
                                for (int i = 0; i < availableSlots.size(); i++) {
                                    System.out.println((i + 1) + ". " + availableSlots.get(i));
                                }
                                System.out.print("Choose an available slot by entering the corresponding number: ");
                                int rescheduleSlotIndex;
                                try {
                                    rescheduleSlotIndex = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a number.");
                                    scanner.nextLine(); // Clear the invalid input
                                    break;
                                }
    
                                if (rescheduleSlotIndex < 1 || rescheduleSlotIndex > availableSlots.size()) {
                                    System.out.println("Invalid slot selection.");
                                } else {
                                    TimeSlot newSlot = availableSlots.get(rescheduleSlotIndex - 1);
                                    patient.rescheduleAppointment(appointment, newSlot, doctorForReschedule);
                                }
                            }
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
    
                case 6:
                    System.out.print("Enter Appointment ID to cancel: ");
                    String appointmentIdToCancel = scanner.nextLine();
                    Appointment appointmentToCancel = appointmentManager.findAppointmentById(appointmentIdToCancel);
                    if (appointmentToCancel != null) {
                        System.out.print("Enter Doctor ID: ");
                        String doctorIdForCancel = scanner.nextLine();
                        Doctor doctorForCancel = doctorManager.findDoctorById(doctorIdForCancel);
                        if (doctorForCancel != null) {
                            patient.cancelAppointment(appointmentToCancel, doctorForCancel);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Appointment not found.");
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
                    break;
            }
        }
    }
    
}