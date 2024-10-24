import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Pharmacist pharmacist = new Pharmacist("P0001", "pwd", "Test", "M", "pharmacist", 0);
        Medicine panadol = new Medicine("Panadol", 10, 20);
        pharmacist.login("P0001", "pwd");
        pharmacist.replenishmentRequest(panadol, 50);


        List<ReplenishmentRequest> reqs = pharmacist.getReplenishmentRequests();
        for(ReplenishmentRequest req : reqs){
            System.out.println(req.getRequestID());
        }
        pharmacist.displayMenu();
    }
}
