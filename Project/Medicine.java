import java.util.*;
import java.io.*;

public class Medicine {

	private String name;
	private int stock;
	private int alertLevel;
	private static List<Medicine> medicineList = new ArrayList<>();

	public Medicine(String name) {
		this(name, 0, 0); 
		medicineList.add(this);
	}

	public Medicine(String name, int stock, int alertLevel){
		this.name = name;
		this.stock = stock;
		this.alertLevel = alertLevel;
		medicineList.add(this);
	}

	/**
	 * 
	 * @param amount
	 */
	//replenish method
	public void replenish(int amount) {
		this.stock += amount;
		System.out.printf("Stock replenished. %d stocks added to %s. New amount: %d \n", amount, name, stock);
	}

	public void adjustAlert(int newAlertLevel) {
		this.alertLevel = newAlertLevel;
		System.out.println("Alert for " + name + " has been modified to " + alertLevel);
		
	}
	

	//get info
	public int getStock() {
		return stock;
	}
	
	//for updateMedicine stock
	public void setStock(int stock){
		this.stock = stock;
	}

	public String getName(){
		return name;
	}
	
	public void setAlertLevel(int alertLevel) {
        this.alertLevel = alertLevel;
    }

	public int getAlertLevel() {
        return alertLevel;
    }

	//to check if its below alert levels
	public boolean alertReplenishment(){
		return stock < alertLevel;
	}

	public static List<Medicine> getAllMedicines() {
        return medicineList;
    }

    public static Medicine findMedicineByName(String name) {
        for (Medicine medicine : medicineList) {
            if (medicine.getName().equalsIgnoreCase(name)) {
                return medicine;
            }
        }
        return null;  //if not found
    }
	
	//used when we change to dispensed and reducing of quantity
	public void deductStock(int quantity) {
        if (quantity <= stock) {
            stock -= quantity;
        } else {
            throw new IllegalArgumentException("Insufficient stock for " + name);
        }
    }

	@Override
    public String toString() {
		return name;
	}
}