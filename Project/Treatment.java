import java.time.LocalDate;

public class Treatment {
    private String treatmentID;
    private String description;
    private LocalDate date;

    public Treatment(String treatmentID, String description, LocalDate date) {
        this.treatmentID = treatmentID;
        this.description = description;
        this.date = date;
    }

    public String getTreatmentID() {
        return treatmentID;
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
        return "Treatment ID: " + treatmentID + ", Description: " + description + ", Date: " + date;
    }
}