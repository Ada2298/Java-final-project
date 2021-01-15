import java.io.IOException;
import java.util.*;

public class Bank implements BankInt {

    //Sorted List of Customers (By loan size)
    static ArrayList<DLL.Customer> sorted = new ArrayList<>();

    //Creating a comparator object
    static Compare comp = new Compare();

    //Method 11.
    //This is the method that pays interests and collects loans when workDone % 12 == 0
    //It is already implemented in every other method, so that paying and collecting is more automated
    //This method also calls infoBackup (Method 9) every time it is used, so user info is backed up more frequently
    //Notice that when trying to collect loaned money from the user, the default amount is set to 0.
    //This means that every trimester, customers that have loans are forced to pay a minimal amount even if they have
    //already payed during the trimester.
    //Lastly... Because this method calls the infoBackup method, it MUST be able to throw and IOException.
    //And all of the methods which call Bank.auto(); MUST also be able to do so.
    static void auto() throws IOException {
        if (DLL.head != null) {
            if (DLL.Customer.workDone % 12 == 0 ) {
                DLL.Customer current = DLL.head;
                while (current.next != null) {
                    if (current.getDeposit() != null) {
                        current.payInterest();
                    }
                    if (current.getLoan() != null) {
                        current.payLoan(0);
                    }
                    current = current.next;
                }
                DLL.Customer.workDone = 0;
            }
            BankInt.infoBackup(DLL.head);
        }
        System.out.println(12 - DLL.Customer.workDone + " tasks remaining until the end of the trimester.");
    }

    public static void main(String[] args) throws IOException {

        //Creating a new user requires giving them ID. The method idGen (in DLL.Customer class) will provide a unique 8 digit id every time you use it.
        System.out.println("Creating users will increment DLL.Customer.workDone by 1, We will learn about this later.");
        DLL.Customer u1 = new DLL.Customer(DLL.Customer.idGen(), "Adelina", "Avagimyan", "avagimyan98@inbox.ru", 520000, true);
        DLL.Customer u2 = new DLL.Customer(DLL.Customer.idGen(), "Arevik", "Lalayan", "arevik@Gmail.com", 480000, false);
        DLL.Customer u3 = new DLL.Customer(DLL.Customer.idGen(), "George", "Clooney", "g.clooney@rodina.kz", 244550, false);
        DLL.Customer u4 = new DLL.Customer(DLL.Customer.idGen(), "Yenok", "Hakobyan", "yenok.hakobyan@gmail.com", 700000, true);
        DLL.Customer u5 = new DLL.Customer(DLL.Customer.idGen(), "Peter", "Griffin", "roadhouse@birdistheword.frt", 888888888, true);
        DLL.Customer u6 = new DLL.Customer(DLL.Customer.idGen(), "Felix", "Kjelberg", "howsit@going.bros", 780000, false);
        DLL.Customer u7 = new DLL.Customer(DLL.Customer.idGen(), "Mister", "Bean", "mrbean@bigben.tea", 400000, false);
        DLL.Customer u8 = new DLL.Customer(DLL.Customer.idGen(), "Nikolai", "Valuyev", "imdumbaf@retarded.gov", 111.1, true);
        DLL.Customer u9 = new DLL.Customer(DLL.Customer.idGen(), "Barack", "Obama", "thanksobama@whitehouse.am", 150000, true);
        DLL.Customer u10 = new DLL.Customer(DLL.Customer.idGen(), "James", "Bond", "mynameisbondjamesbond@jamesbond.bond", 45128745, false);

        //Adding customers to the doublyLinkedList
        System.out.println("Adding customers to the doublyLinkedList");
        DLL.Customer.add(u1);
        DLL.Customer.addLast(u1, u2);
        DLL.Customer.addNext(u2, u3);
        DLL.Customer.addNext(u3, u4);
        DLL.Customer.addLast(u4, u5);
        DLL.Customer.addLast(u5, u6);
        DLL.Customer.addLast(u6, u7);
        DLL.Customer.addLast(u7, u8);
        DLL.Customer.addNext(u8, u9);
        DLL.Customer.addLast(u9, u10);

        //Setting some Loans
        System.out.println("Setting loans for some of the customers");
        u1.setLoan(Loan.minimalLoan);
        u2.setLoan(Loan.standardLoan);
        u3.setLoan(Loan.maximalLoan);
        u4.setLoan(new Loan(36, 5200000, 21));
        u7.setLoan(new Loan(18, 1500000, 21.5));
        u10.setLoan(new Loan(120, 80000000, 19.8));

        //Setting some deposits
        System.out.println("Setting deposits for some of the customers");
        u2.setDeposit(new Deposit(12, 120000, 8.5));
        u3.setDeposit(Deposit.minimal);
        u4.setDeposit(Deposit.mostFrequent);
        u6.setDeposit(Deposit.standard);
        u9.setDeposit(Deposit.mostFrequent);
        u10.setDeposit(Deposit.mostFrequent);

        //Transfer some funds between customers
        System.out.println("Transferring some funds between customers.");
        Transaction.transfer(u3, u7, 200000);
        Transaction.transfer(u8, u5, 60000);

        //Show how setOverdraft method works
        System.out.println("Show the setOverride method with some twist.");
        u1.setOverdraft(true);
        u1.setOverdraft(false);
        u1.setOverdraft(true);

        //Credit and debit
        System.out.println("Adding and removing funds from customer balances");
        u4.credit(150000);
        u2.debit(60000);
        u8.debit(20000);

        //Trying to manually pay interest to a deposit
        System.out.println("Trying to manually pay interest to a deposit");
        u1.payInterest();
        u2.payInterest();

        //Trying to manually collect loan
        System.out.println("Trying to manually collect loan");
        u3.payLoan(20000);
        u4.payLoan(50000);
        u5.payLoan(100);

        //Top 3 customers sorted by their loan size
        DLL.Customer.printTopCustomers(sorted);

        //Printing doubly linked list forwards
        System.out.println("DLL read forwards");
        DLL.printListForwards();

        //Printing doubly linked list backwards
        System.out.println("DLL read backwards");
        DLL.printListBackwards();

        System.out.println("Thank you. I do hope I get a 20 on this one :)");
    }
}
