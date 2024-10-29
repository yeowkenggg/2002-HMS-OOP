import java.util.List;
import java.util.function.Predicate;

public interface IStaffManager {

    void addStaff(Staff staff);
    
    void updateStaff();
    
    void removeStaff();
    
    void viewAllStaff();

    void filterStaffMenu();
    
    Staff findStaffById(String userId);

    void filterStaff(Predicate<Staff> criteria, String filterDescription);

    void addStaffMenu();

    void displayStaffManagementMenu();
}
