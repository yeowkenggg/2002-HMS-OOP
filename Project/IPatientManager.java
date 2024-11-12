import java.time.LocalDate;
import java.util.Collection;

public interface IPatientManager {

    void viewMedicalRecord(Patient patient);

    void updateContactInfo(Patient patient, String newContactInfo);

    void updateName(Patient patient, String newName);

    void updateGender(Patient patient, String newGender);

    void updateDateOfBirth(Patient patient, LocalDate newDateOfBirth);

    void setAppointmentManager(IAppointmentManager appointmentManager);

    Collection<? extends User> getAllPatientsPrv();

    Patient findPatientById(String patientID);

    void addPatient(Patient newPatient);
}
