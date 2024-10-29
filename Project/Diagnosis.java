import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Diagnosis {
    private String diagnosisID;
    private String description;
    private LocalDate date;

    public Diagnosis(String diagnosisID, String description, LocalDate date) {
        this.diagnosisID = diagnosisID;
        this.description = description;
        this.date = date;
    }

    public String getDiagnosisID() {
        return diagnosisID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Diagnosis ID: " + diagnosisID + ", Description: " + description + ", Date: " + date;
    }
}