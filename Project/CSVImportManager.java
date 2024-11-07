import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;

public class CSVImportManager {

    public static void importStaffData(String filePath, StaffManager staffManager, MedicineManager medicineManager, PharmacistManager pharmacistManager, DoctorManager doctorManager, PrescriptionManager prescriptionManager) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length >= 6) {
                    String userId = data[0];
                    String name = data[1];
                    String password = data[2];
                    String role = data[3];
                    String gender = data[4];
                    int age = Integer.parseInt(data[5]);

                    Staff newStaff;
                    if ("Doctor".equalsIgnoreCase(role)) {
                        newStaff = new Doctor(userId, password, name, gender, role, age, doctorManager);
                    } else if ("Pharmacist".equalsIgnoreCase(role)) {
                        newStaff = new Pharmacist(userId, password, name, gender, role, age, pharmacistManager, prescriptionManager);
                    } else  {
                        newStaff = new Administrator(userId, password, name, gender, role, age, staffManager, medicineManager);
                    }
                    staffManager.addStaff(newStaff);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

    public static void importPatientData(String filePath, PatientManager patientManager, AppointmentManager appointmentManager) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length >= 6) {
                    String userId = data[0];
                    String password = data[1];
                    String name = data[2];
                    LocalDate dateOfBirth = LocalDate.parse(data[3]); 
                    String gender = data[4];
                    String bloodType = data[5];
                    String contactInfo = data[6];

                    Patient newPatient;
                    newPatient = new Patient(userId, password, name, gender, dateOfBirth, bloodType, contactInfo, patientManager, appointmentManager);

                    patientManager.addPatient(newPatient);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

    public static void importMedicineData(String filePath, MedicineManager medicineManager) {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length >= 3) {
                    String name = data[0];
                    int stock = Integer.parseInt(data[1]);
                    int alertLevel = Integer.parseInt(data[2]);

                    // Add medicine to the manager
                    medicineManager.addMedicine(name, stock, alertLevel);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid data format in the file: " + filePath);
            e.printStackTrace();
        }
    }
}
