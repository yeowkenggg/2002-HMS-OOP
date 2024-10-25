import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
       
        Administrator admin = new Administrator("A001", "admin123", "ATest", "Male", "Administrator", 40);
        Pharmacist pharmacist = new Pharmacist("P0001", "pwd", "PTest", "M", "pharmacist", 40);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        pharmacist.login("P0001", "pwd");
        pharmacist.replenishmentRequest(panadol, 50);
        admin.viewReplenishmentRequests();
        admin.approveReplenishment("R25101551");
       

        pharmacist.displayMenu();
    }
}
