public class ReplenishmentRequest {

	private String requestID;
	private Medicine medicine;
	private int requestedAmount;
	private String pharmacistID;
	private boolean isApproved;

	//constructor
	public ReplenishmentRequest(String requestID, Medicine medicine, int requestedAmount, String pharmacistID) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.requestedAmount = requestedAmount;
        this.pharmacistID = pharmacistID;
        this.isApproved = false;  // default
    }

	//approve and call replenish method to add qty into the medicine
	public void approve() {
		// TODO - implement ReplenishmentRequest.approve
		this.isApproved = true;
		medicine.replenish(requestedAmount);
		throw new UnsupportedOperationException();
	}

	//get requestID
	public String getRequestID() {
		return requestID;
	}

	
	//check of status
	public boolean isApproved() {
		// TODO - implement ReplenishmentRequest.isApproved
		return isApproved;
	}
 
}