import java.util.List;

public class DoctorService {

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
            System.out.println("Patient record updated for Patient ID: " + patientID);
        } else {
            System.out.println("No medical record found for Patient ID: " + patientID);
        }
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

    public void cancelAppointment(Doctor doctor, Appointment appointment) {
        if (doctor.getAppointments().remove(appointment)) {
            doctor.getAvailability().add(appointment.getTimeSlot());
            System.out.println("Appointment canceled and slot " + appointment.getTimeSlot() + " returned to availability.");
        } else {
            System.out.println("Appointment not found in doctor’s schedule.");
        }
    }

    public void acceptAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId()) && doctor.getAppointments().contains(appointment)) {
            appointment.confirm();
            System.out.println("Appointment " + appointment.getAppointmentID() + " accepted.");
            
            // assign the doctor to the patient if there isnt one so he can access other functions
            if (!doctor.getAssignedPatientIDs().contains(appointment.getPatientID())) {
                assignPatient(doctor, appointment.getPatientID());
            }
        } else {
            System.out.println("Access Denied: You are not authorized to accept this appointment.");
        }
    }
    public void declineAppointment(Doctor doctor, Appointment appointment) {
        if (appointment.getDoctorID().equals(doctor.getUserId()) && doctor.getAppointments().contains(appointment)) {
            appointment.setStatus("Declined");
            System.out.println("Appointment " + appointment.getAppointmentID() + " declined.");
        } else {
            System.out.println("Access Denied: You are not authorized to decline this appointment.");
        }
    }

    public void viewAppointments(Doctor doctor) {
        System.out.println("Scheduled Appointments for Doctor ID: " + doctor.getUserId());
        for (Appointment appointment : doctor.getAppointments()) {
            System.out.println(appointment);
        }
    }
    public void addAvailability(Doctor doctor, TimeSlot timeSlot) {
        for (TimeSlot slot : doctor.getAvailability()) {
            if (slot.isSameTimeSlot(timeSlot)) {
                System.out.println("Time slot " + timeSlot + " is already in the availability list.");
                return;
            }
        }
        doctor.getAvailability().add(timeSlot);
        System.out.println("Availability added for Dr. " + doctor.getName() + ": " + timeSlot);
    }

    public void assignPatient(Doctor doctor, String patientID) {
        if (!doctor.getAssignedPatientIDs().contains(patientID)) {
            doctor.getAssignedPatientIDs().add(patientID);
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
            System.out.println("Appointment removed from Dr. " + doctor.getName() + "'s schedule.");
        } else {
            System.out.println("Appointment not found in Dr. " + doctor.getName() + "'s schedule.");
        }
    }

    public void setAvailability(Doctor doctor, TimeSlot timeSlot) {
        for (TimeSlot slot : doctor.getAvailability()) {
            if (slot.isSameTimeSlot(timeSlot)) {
                System.out.println("Time slot " + timeSlot + " is already in the availability list for Dr. " + doctor.getName());
                return;
            }
        }
        doctor.getAvailability().add(timeSlot);
        System.out.println("Availability added for Dr. " + doctor.getName() + ": " + timeSlot);
    }
}
