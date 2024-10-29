import java.util.List;

public interface ITreatmentManager {

    void addTreatment(Treatment treatment);

    void removeTreatment(String treatmentID);

    Treatment findTreatmentById(String treatmentID);

    List<Treatment> getAllTreatments();
}
