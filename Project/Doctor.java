import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff implements IUser {
    private List<TimeSlot> availability;
    private List<Appointment> appointments;
    private List<String> assignedPatientIDs = new ArrayList<>();

    // constructor
    public Doctor(String userId, String password, String name, String gender, String role, int age) {
        super(userId, password, name, gender, role, age);
        this.availability = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.assignedPatientIDs = new ArrayList<>();
    }

    // getters
    public List<TimeSlot> getAvailability() {
        return availability;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public List<String> getAssignedPatientIDs() {
        return assignedPatientIDs;
    }

   

    @Override
    public void displayMenu() {
        if (isLoggedIn()) {
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
        } else {
            System.out.println("ERROR. PLEASE LOG IN! (Doctor)");
        }
    }
}
