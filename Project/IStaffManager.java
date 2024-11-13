import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface IStaffManager {

    

    List<Staff> getAllStaff();

    void displayStaffManagementMenu();

    void addStaff(Staff newStaff);

}
