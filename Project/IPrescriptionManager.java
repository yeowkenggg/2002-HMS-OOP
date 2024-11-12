import java.util.List;

public interface IPrescriptionManager {

    void addPrescription(Prescription prescription);

    List<Prescription> getAllPrescriptions();

    List<Prescription> getPendingPrescriptions();

    boolean updatePrescriptionStatus(String prescriptionID);

    Prescription findPrescriptionById(String prescriptionID);

    void removePrescription(String prescriptionID);

    void setMedicineManager(IMedicineManager medicineManager);
}
