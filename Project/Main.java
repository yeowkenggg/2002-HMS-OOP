import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
       
        Administrator admin = new Administrator("A001", "admin123", "ATest", "Male", "Administrator", 40);
        Pharmacist pharmacist1 = new Pharmacist("P0001", "pwd", "PTest", "M", "pharmacist", 40);
        Pharmacist pharmacist2 = new Pharmacist("P0002", "pwd", "P2Test", "M", "Pharmacist", 35);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        Medicine ibuprofen = new Medicine("Ibuprofen", 5, 10);

        pharmacist1.login("P0001", "pwd");
        pharmacist2.replenishmentRequest(panadol, 50);
        pharmacist1.replenishmentRequest(panadol, 50);
        pharmacist1.replenishmentRequest(ibuprofen, 50);

        System.out.println(panadol.getStock());
        admin.viewReplenishmentRequests();
        String requestID = "R25101621"; //Format in RddMMHHmm , seconds will be added in at the end to make sure its "unique"
        admin.approveReplenishment(requestID);
        System.out.println(panadol.getStock());

        
        admin.viewReplenishmentRequests();
        
    }
}
