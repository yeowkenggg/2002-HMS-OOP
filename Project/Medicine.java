import java.util.*;
import java.io.*;

public class Medicine {

	private String name;
	private int stock;
	private int alertLine;

	/**
	 * 
	 * @param amount
	 */
	public void replenish(int amount) {
		this.stock += amount;
		System.out.printf("Stock replenished. %d stocks added to %s. New amount: %d \n", amount, name, stock);
		throw new UnsupportedOperationException();
	}

	public void adjustAlert(int newAlert) {
		this.alertLine = newAlert;
		System.out.printf("Alert for %s has been modified to %d\n", name, alertLine);
		throw new UnsupportedOperationException();
	}

	public int getStock() {
		return this.stock;
	}
	
	public String getName(){
		return this.name;
	}

	public boolean alertReplenishment(){
		return stock < alertLine;
	}
}