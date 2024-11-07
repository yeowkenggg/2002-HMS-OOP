import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff implements IUser {
    private List<TimeSlot> availability;
    private List<Appointment> appointments;
    private List<String> assignedPatientIDs = new ArrayList<>();
    private DoctorManager doctorManager;

    // constructor
    public Doctor(String userId, String password, String name, String gender, String role, int age, DoctorManager doctorManager) {
        super(userId, password, name, gender, role, age);
        this.availability = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.assignedPatientIDs = new ArrayList<>();
        this.doctorManager = doctorManager; 
    }

    // getters
    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public List<Appointment> getAppointments() {
        return this.appointments;
    }

    public List<String> getAssignedPatientIDs() {
        return assignedPatientIDs;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }
    
    public void viewPatientMedicalRecord(String patientID) {
        doctorManager.viewPatientRecord(this, patientID);
    }

    public void addAvailability(TimeSlot newSlot) {
        boolean alreadyExists = false;
        for (TimeSlot slot : availability) {
            if (slot.isSameTimeSlot(newSlot)) {
                alreadyExists = true;
                break;
            }
        }
        if (!alreadyExists) {
            availability.add(newSlot);
            System.out.println("New availability detected: " + newSlot);
        } else {
            System.out.println("Time slot already set.");
        }
    }
    
    public void removeAvailability(TimeSlot slot) {
        if (availability.remove(slot)) {
            System.out.println("Time slot removed from availability: " + slot);
        } else {
            System.out.println("Time slot not found in availability.");
        }
    }
    
    public boolean isAvailable(TimeSlot timeSlot) {
        return availability.contains(timeSlot);
    }
    

    public void viewUpcomingAppointments() {
        boolean hasUpcomingAppointments = false;
    
        for (Appointment appointment : appointments) {
            if (appointment.isUpcoming() && "Confirmed".equals(appointment.getStatus())) {
                System.out.println(appointment);
                hasUpcomingAppointments = true;
            }
        }
    
        if (!hasUpcomingAppointments) {
            System.out.println("There are no upcoming appointments.");
        }
    }

    public void addAssignedPatientID(String patientID) {
        if (!assignedPatientIDs.contains(patientID)) {
            assignedPatientIDs.add(patientID);
        }
    }
    
    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
        } else {
            System.out.println("ERROR. PLEASE LOG IN! (Doctor)");
        }
    }
}
