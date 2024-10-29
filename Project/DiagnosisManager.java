import java.util.ArrayList;
import java.util.List;

public class DiagnosisManager {
    private List<Diagnosis> diagnoses;

    public DiagnosisManager() {
        this.diagnoses = new ArrayList<>();
    }

    public void addDiagnosis(Diagnosis diagnosis) {
        diagnoses.add(diagnosis);
        System.out.println("Diagnosis added: " + diagnosis);
    }

    public void removeDiagnosis(String diagnosisID) {
        diagnoses.removeIf(diagnosis -> diagnosis.getDiagnosisID().equals(diagnosisID));
        System.out.println("Diagnosis with ID " + diagnosisID + " removed.");
    }

    public Diagnosis findDiagnosisById(String diagnosisID) {
        for (Diagnosis diagnosis : diagnoses) {
            if (diagnosis.getDiagnosisID().equals(diagnosisID)) {
                return diagnosis;
            }
        }
        return null;
    }

    public List<Diagnosis> getAllDiagnoses() {
        return new ArrayList<>(diagnoses);
    }
}
