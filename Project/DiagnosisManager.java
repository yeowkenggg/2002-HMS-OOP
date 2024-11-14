import java.util.ArrayList;
import java.util.List;

/**
 * DiagnosisManager class for logic for Diagnosis
 */
public class DiagnosisManager {
    private List<Diagnosis> diagnoses;

    /**
     * Constructor for DiagnosisManager
     * There may be more than one diagnosis, so a list is used
     */
    public DiagnosisManager() {
        this.diagnoses = new ArrayList<>();
    }

    /**
     * method to add diagnosis information into the list 
     * @param diagnosis the diagnosis to be added
     */
    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added: " + diagnosis);
    }

    /**
     * method to find the diagnosis based on the ID
     * @param diagnosisID the id specified to find the diagnosis
     * @return the diagnosis
     */
    public Diagnosis findDiagnosisById(String diagnosisID) {
        for (Diagnosis diagnosis : diagnoses) {
            if (diagnosis.getDiagnosisID().equals(diagnosisID)) {
                return diagnosis;
            }
        }
        return null;
    }

    /**
     * a method to get all diagnosis inside the list
     * @return the list of diagnosis
     */
    public List<Diagnosis> getAllDiagnoses() {
        return new ArrayList<>(diagnoses);
    }
}
