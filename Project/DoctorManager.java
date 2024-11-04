import java.util.ArrayList;
import java.util.List;

public class DoctorManager implements IDoctorManager {

    private StaffManager staffManager;

    public DoctorManager(StaffManager staffManager) {
        this.staffManager = staffManager;
    }

    public void addDoctor(Doctor doctor) {
        staffManager.addStaff(doctor); 
    }

    public void setStaffManager(StaffManager sm){
        this.staffManager = sm;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        for (Staff staff : staffManager.getAllStaff()) {
            if (staff instanceof Doctor) {
                doctorList.add((Doctor) staff);
            }
        }
        return doctorList;  // Returning a copy to prevent direct modification
    }
    
    public void viewPatientRecord(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            System.out.println("Access denied. Patient is not under your care.");
            return;
        }
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            System.out.println("Medical Record for Patient ID: " + patientID);
            record.viewMedicalRecord();
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
    }

    public void updatePatientRecord(Doctor doctor, String patientID, AppointmentOutcome outcome) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            System.out.println("Access denied. Patient is not under your care.");
            return;
        }
        MedicalRecord record = MedicalRecord.getRecordByPatientID(patientID);
        if (record != null) {
            record.addAppointmentOutcome(outcome);
            System.out.println("Appointment outcome added to the medical record for Patient ID: " + patientID);
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
    }

    public Doctor findDoctorById(String doctorId) {
        for (Doctor doctor : getAllDoctors()) {  
            if (doctor.getUserId().equalsIgnoreCase(doctorId)) {
                return doctor;
            }
        }
        System.out.println("Doctor with ID " + doctorId + " not found.");
        return null;
    }
    
    public boolean isAvailable(Doctor doctor, TimeSlot timeSlot) {
        if (!doctor.getAvailability().contains(timeSlot)) {
            return false;
        }
        for (Appointment appointment : doctor.getAppointments()) {
            if (appointment.getTimeSlot().isSameTimeSlot(timeSlot)) {
                return false;
            }
        }
        return true;
    }

    public void setAvailability(Doctor doctor, TimeSlot timeSlot) {
        if (doctor.getAvailability().contains(timeSlot)) {
            System.out.println("Time slot " + timeSlot + " is already in the availability list for Dr. " + doctor.getName());
            return;
        }
        doctor.getAvailability().add(timeSlot);
        System.out.println("Time slot " + timeSlot + " added to the availability list for Dr. " + doctor.getName());
    }

    public List<TimeSlot> getAvailability(Doctor doctor) {
        return new ArrayList<>(doctor.getAvailability()); 
    }

    public void addAvailability(Doctor doctor, TimeSlot timeSlot) {
        if (doctor.getAvailability().contains(timeSlot)) {
            System.out.println("Time slot " + timeSlot + " is already in the availability list.");
            return;
        }
        doctor.getAvailability().add(timeSlot);
        System.out.println("Availability added for Dr. " + doctor.getName() + ": " + timeSlot);
    }

    public void removeAvailability(Doctor doctor, TimeSlot timeSlot) {
        if (doctor.getAvailability().remove(timeSlot)) {
            
        } else {
            System.out.println("Time slot " + timeSlot + " not found in Dr. " + doctor.getName() + "'s availability.");
        }
    }

    public void assignPatient(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            doctor.getAssignedPatientIDs().add(patientID);
            System.out.println("Patient ID " + patientID + " assigned to Dr. " + doctor.getName());
        } else {
            System.out.println("Patient ID " + patientID + " is already assigned to Dr. " + doctor.getName());
        }
    }

    public void addAppointment(Doctor doctor, Appointment appointment) {
        if (!doctor.getAppointments().contains(appointment)) {
            doctor.getAppointments().add(appointment);
            System.out.println("Appointment added to Dr. " + doctor.getName() + "'s schedule.");
        } else {
            System.out.println("This appointment already exists in the schedule.");
        }
    }

    public void removeAppointment(Doctor doctor, Appointment appointment) {
        if (doctor.getAppointments().remove(appointment)) {
            
        } else {
            System.out.println("Appointment not found in Dr. " + doctor.getName() + "'s schedule.");
        }
    }

    public List<String> getAssignedPatientIDs(Doctor doctor) {
        return new ArrayList<>(doctor.getAssignedPatientIDs()); 
    }
}
