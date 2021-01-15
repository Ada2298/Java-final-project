import java.util.ArrayList;
import java.util.Comparator;

public class Compare implements Comparator<DLL.Customer> {

    @Override
    public int compare(DLL.Customer o1, DLL.Customer o2) {
        if (o1.getLoan() != null && o2.getLoan() != null) {
            if (o1.getLoan().getAmount() > o2.getLoan().getAmount()) {
                return -1;
            } else if (o1.getLoan().getAmount() == o2.getLoan().getAmount()) {
                return 0;
            } else return 1;
        }
        return 0;
    }
}
