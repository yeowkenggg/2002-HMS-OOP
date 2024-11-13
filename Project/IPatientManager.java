import java.time.LocalDate;
import java.util.Collection;

public interface IPatientManager {

    
    void addPatient(Patient newPatient);

    Patient findPatientById(String patientID);

    void setAppointmentManager(IAppointmentManager appointmentManager);

    Collection<? extends User> getAllPatientsPrv();

    void updateContactInfo(Patient patient, String newContactInfo);

    void viewMedicalRecord(Patient patient);
}
