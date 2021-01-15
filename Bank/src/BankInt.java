import java.io.*;
import java.util.LinkedList;

public interface BankInt {

    //Method 9. Creates multiple objects and a single file in your PC, to save DLL.Customer objects.
    // Delete if this is too much or you are not familiar with it
    static void infoBackup(DLL.Customer startingFrom) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\JavaStuff\\Backup.txt"));
        try {
            while (true) {
                oos.writeObject(startingFrom);
                startingFrom = startingFrom.next;
            }
        } catch (Exception e) {
            System.out.println("All user info backed up.");
        }
        oos.close();
    }

    //Method 10. Retrieves information about Users from the backup file created by method 9.
    static void infoRetrieve() throws IOException {
        FileInputStream fIn = new FileInputStream("D:\\JavaStuff\\Backup.txt");
        try (ObjectInputStream oIn = new ObjectInputStream(fIn)) {
            while (true) {
                System.out.println(oIn.readObject());
            }
        } catch (Exception e) {
            System.out.println("All user info read.");
        }
    }


}
