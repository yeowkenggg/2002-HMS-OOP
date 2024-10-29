import java.util.ArrayList;
import java.util.List;

public class TreatmentManager implements ITreatmentManager {
    private List<Treatment> treatments;

    public TreatmentManager() {
        this.treatments = new ArrayList<>();
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
        System.out.println("Treatment added: " + treatment);
    }

    public void removeTreatment(String treatmentID) {
        treatments.removeIf(treatment -> treatment.getTreatmentID().equals(treatmentID));
        System.out.println("Treatment with ID " + treatmentID + " removed.");
    }

    public Treatment findTreatmentById(String treatmentID) {
        for (Treatment treatment : treatments) {
            if (treatment.getTreatmentID().equals(treatmentID)) {
                return treatment;
            }
        }
        return null;
    }

    public List<Treatment> getAllTreatments() {
        return new ArrayList<>(treatments);
    }
}