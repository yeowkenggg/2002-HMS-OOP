import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface IStaffManager {

    void addStaff(Staff staff);
    
    void updateStaff();
    
    void removeStaff(UserManager userManager);
    
    void viewAllStaff();

    void filterStaffMenu();
    
    Staff findStaffById(String userId);

    void filterStaff(Predicate<Staff> criteria, String filterDescription);

    void addStaffMenu();

    void displayStaffManagementMenu();

    List<Staff> getAllStaff();

}
