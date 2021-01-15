import java.io.IOException;
import java.io.Serializable;

public abstract class Transaction implements Serializable {

    abstract double getMonthlyPayment();

    //Method 3. Used to transfer funds from 1 user to another(Checks if if the DLL.Customer has enough money, or has overdraft activated.
    //Generates unique String for the situation... Expression "Sad day. Unknown error occurred" will never be printed)
    static void transfer(DLL.Customer from, DLL.Customer to, double amount) throws IOException {
        DLL.Customer.workDone++;

        if (from.getBalance() > amount) {
            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);
            System.out.println("Transfer completed successfully!\nYour current balance is " + from.getBalance() + " ");
            return;
        }
        if (from.getBalance() < amount && from.canOverdraft()) {
            from.setBalance(from.getBalance() - (amount * 1.05));
            to.setBalance(to.getBalance() + amount);
            System.out.println("Overdraft used. Commission - 5%.");
            System.out.println("Transfer completed successfully!\nYour current balance is " + from.getBalance() + " ");
            return;
        }

        if (from.getBalance() < amount && !from.canOverdraft()) {
            System.out.println("Insufficient funds. Your current balance is " + from.getBalance() + "." + "\nCan not transfer " + amount + ".");
            return;
        }
        System.out.println("Sad day. Unknown error occurred");
        Bank.auto();
    }


}
