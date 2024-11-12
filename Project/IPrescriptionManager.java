import java.util.List;

public interface IPrescriptionManager {



    void setMedicineManager(IMedicineManager medicineManager);

    void addPrescription(Prescription prescription);

    List<Prescription> getAllPrescriptions();

    List<Prescription> getPendingPrescriptions();

    boolean updatePrescriptionStatus(String prescriptionID);
}
