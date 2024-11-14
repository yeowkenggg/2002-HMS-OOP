import java.util.ArrayList;
import java.util.List;

/**
 * TreatmentManager - logic implementation for treatment class
 */
public class TreatmentManager implements ITreatmentManager {
    private List<Treatment> treatments;

    /**
     * constructor for TreatmentManager class
     * initializes an empty list of treatments
     */
    public TreatmentManager() {
        this.treatments = new ArrayList<>();
    }

    /**
     * method to add treatment to the list
     * @param treatment treatment to add
     */
    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added: " + treatment);
    }

    /**
     * method to remove a treatment from the list by its ID
     * @param treatmentID Id of the treatment to remove
     */
    public void removeTreatment(String treatmentID) {
        treatments.removeIf(treatment -> treatment.getTreatmentID().equals(treatmentID));
        System.out.println("Treatment with ID " + treatmentID + " removed.");
    }

    /**
     * method to find a treatment in the list by its ID
     * @param treatmentID Id of the treatment to find
     * @return treatment if found, otherwise null
     */
    public Treatment findTreatmentById(String treatmentID) {
        for (Treatment treatment : treatments) {
            if (treatment.getTreatmentID().equals(treatmentID)) {
                return treatment;
            }
        }
        return null;
    }

    /**
     * get method to get all treatments in the list
     * @return list of all treatments
     */
    public List<Treatment> getAllTreatments() {
        return new ArrayList<>(treatments);
    }
}