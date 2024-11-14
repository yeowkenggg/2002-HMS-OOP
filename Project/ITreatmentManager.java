import java.util.List;

/**
 * An interface class which manage the treatment related operations
 */
public interface ITreatmentManager {

    /**
     * Adds a new treatment
     * @param treatment the treatment to be added
     */
    void addTreatment(Treatment treatment);

    /**
     * Removes a treatment from the system by its ID.
     * @param treatmentID the unique identifier of the treatment to be removed
     */
    void removeTreatment(String treatmentID);

    /**
     * Finds a treatment by its ID.
     * @param treatmentID the unique identifier of the treatment to find
     * @return the treatment with the ID
     */
    Treatment findTreatmentById(String treatmentID);

    /**
     * Retrieves a list of all treatments
     * @return a list of all treatments
     */
    List<Treatment> getAllTreatments();
}
