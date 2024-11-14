import java.time.LocalDate;

/**
 * Treatment Class
 */
public class Treatment {
    private String treatmentID;
    private String description;
    private LocalDate date;

    /**
     * constructor for treatment class
     * @param treatmentID unique Id for the treatment
     * @param description description of the treatment
     * @param date date when the treatment was administered
     */
    public Treatment(String treatmentID, String description, LocalDate date) {
        this.treatmentID = treatmentID;
        this.description = description;
        this.date = date;
    }

    /**
     * get method to retrieve the treatment ID
     * @return ID of treatment
     */
    public String getTreatmentID() {
        return treatmentID;
    }

    /**
     * get method to retrieve the treatment description
     * @return description of treatment
     */
    public String getDescription() {
        return description;
    }

    /**
     * set method to update the treatment description
     * @param description new description for treatment
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * get method to retrieve the treatment date
     * @return date of treatment
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * set method to update treatment date
     * @param date new date for treatment
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * method to return string representation of treatment
     * @return string containing treatment ID and description
     */
    @Override
    public String toString() {
        return "Treatment ID: " + treatmentID + ", Description: " + description;
    }
}