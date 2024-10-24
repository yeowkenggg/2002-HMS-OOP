public class ReplenishmentRequest {

	private String requestID;
	private Medicine medicine;
	private int requestedAmount;
	private String pharmacistID;
	private boolean isApproved;

	public ReplenishmentRequest(String requestID, Medicine medicine, int requestedAmount, String pharmacistID) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.requestedAmount = requestedAmount;
        this.pharmacistID = pharmacistID;
        this.isApproved = false;  // default
    }

	public void approve() {
		// TODO - implement ReplenishmentRequest.approve
		this.isApproved = true;
		medicine.replenish(requestedAmount);
		throw new UnsupportedOperationException();
	}

	public String getRequestID() {
		return requestID;
	}

	public boolean isApproved() {
		// TODO - implement ReplenishmentRequest.isApproved
		return isApproved;
	}
 
}