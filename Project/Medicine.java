import java.util.*;
import java.io.*;

public class Medicine {

	private String name;
	private int stock;
	private int alertLine;

	public Medicine(String name, int stock, int alertLine){
		this.name = name;
		this.stock = stock;
		this.alertLine = alertLine;
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

	public void adjustAlert(int newAlert) {
		this.alertLine = newAlert;
		System.out.printf("Alert for %s has been modified to %d\n", name, alertLine);
		throw new UnsupportedOperationException();
	}

	//get info
	public int getStock() {
		return stock;
	}
	
	public String getName(){
		return this.name;
	}

	//to check if its below alert levels
	public boolean alertReplenishment(){
		return stock < alertLine;
	}
}