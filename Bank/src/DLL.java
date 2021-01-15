import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class DLL {
    static Customer head;

    public static void printListForwards() {
        System.out.println(head.toString());
        Customer current = head;
        while (current.next != null) {
            current = current.next;
            System.out.println(current.toString());
        }
        System.out.println("All Customer info was printed");
    }

    public static void printListBackwards() throws IOException {
        Customer tail = Customer.getTail();
        System.out.println(tail.toString());
        Customer current = tail;
        while (current.prev != null) {
            current = current.prev;
            System.out.println(current.toString());
        }
        System.out.println();
        System.out.println("All Customer info was printed");
    }

    public static class Customer implements Serializable {
        private final int id;
        private final String name;
        private final String lastName;
        private String email;
        private double balance;
        private boolean overdraft;
        private Loan loan;
        private Deposit deposit;
        Customer prev;
        Customer next;

        //Static variables and methods... Call using DLL.Customer.'your choice' (This is explained in detail below).
        static ArrayList<Integer> idList = new ArrayList<>();
        static Random r1 = new Random();
        static int idNew = 0;

        //Every time a method is used or a new DLL.Customer is created this variable gets +1 to its value.
        //When workDone % 10 == 0. the program realizes that a year has passed and pays interest and collects loans from the customers.
        static int workDone = 0;

        //Constructor for DLL.Customer.
        public Customer(int id, String name, String lastName, String email, double balance, boolean overdraft) throws IOException {
            this.id = id;
            this.name = name;
            this.lastName = lastName;
            this.email = email;
            this.balance = balance;
            this.overdraft = overdraft;
            workDone++;
            Bank.auto();
        }

        //Getters and setters.
        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public boolean canOverdraft() {
            return overdraft;
        }

        public Loan getLoan() {
            return loan;
        }

        public void setLoan(Loan loan) throws IOException {
            this.loan = loan;
            Bank.sorted.add(this);
            Bank.sorted.sort(Bank.comp);
            workDone++;
            Bank.auto();
        }

        public Deposit getDeposit() {
            return deposit;
        }

        public void setDeposit(Deposit deposit) throws IOException {
            this.deposit = deposit;
            workDone++;
            Bank.auto();
        }

        //Notice how this setter is private. This is done so one cannot directly use it from the 'Bank' class, so
        //you have to use 'DLL.Customer.setOverdraft' method instead. Find 'DLL.Customer.setOverdraft' lower...
        private void overdraftSet(boolean overdraft) {
            this.overdraft = overdraft;
        }

        //To string method, so that you don't get a message which make 0 sense...
        @Override
        public String toString() {
            return "\n\nFull name: " + name + " " + lastName +
                    "\nID: " + id +
                    "\neMail: " + email +
                    "\nBalance: " + balance +
                    "\nOverdraft: " + overdraft +
                    "\nLoan: " + loan +
                    "\nDeposit: " + deposit;
        }

        //Here are DLL methods for the customer class
        public static void add(Customer c1) throws IOException {
            workDone++;
            c1.next = head;
            c1.prev = null;
            if (head != null) {
                head.prev = c1;
            }
            head = c1;
            Bank.auto();
        }

        public static void addNext(Customer prevCustomer, Customer c1) throws IOException {
            workDone++;
            if (prevCustomer == null) {
                System.out.println("The given previous customer cannot be null ");
                return;
            }
            c1.next = prevCustomer.next;
            prevCustomer.next = c1;
            c1.prev = prevCustomer;
            if (c1.next != null) {
                c1.next.prev = c1;
            }
            Bank.auto();
        }

        public static void addLast(Customer prevCustomer, Customer c1) throws IOException {
            workDone++;
            Customer last = head;
            c1.next = null;
            if (head == null) {
                c1.prev = null;
                head = c1;
                return;
            }
            while (last.next != null) {
                last = last.next;
            }
            last.next = c1;
            c1.prev = last;
            Bank.auto();
        }

        private static DLL.Customer getTail() throws IOException {
            workDone++;
            Customer tail = null;
            if (head != null) {
                tail = head;
            }
            while (tail.next != null) {
                tail = tail.next;
            }
            Bank.auto();
            return tail;
        }

        //Method 1. Random 8-digit password generator. (Call this method in the 'Bank' class using
        // 'DLL.Customer.idGen', use this expression in DLL.Customer constructor)
        static int idGen() {
            if (idList.contains(idNew) || idNew == 0) {
                do {
                    idNew = r1.nextInt(89999999) + 10000000;
                    idList.add(idNew);
                } while (!idList.contains(idNew));
            }
            return idNew;
        }

        //Method 2. Modified and awesome version of the dull setOverdraft version (Call using DLL.Customer.setOverdraft)
        public void setOverdraft(boolean overdraft) throws IOException {
            workDone++;
            if (canOverdraft() && overdraft) {
                System.out.println("Overdraft already activated.");
            } else if (canOverdraft() && !overdraft) {
                overdraftSet(false);
                System.out.println("Overdraft successfully deactivated.");
            } else if (!canOverdraft() && !overdraft) {
                System.out.println("Overdraft already deactivated.");
            } else if (!canOverdraft() && overdraft) {
                overdraftSet(true);
                setBalance(getBalance() - 100);
                System.out.println("Overdraft successfully activated.");
            } else {
                System.out.println("Unknown error occurred.");
            }
            Bank.auto();
        }

        //Method 4. Credit. Used to add funds to a user's balance.
        public void credit(double amount) throws IOException {
            workDone++;
            setBalance(getBalance() + amount);
            System.out.println("Transaction successful. Current balance " + getBalance());
            Bank.auto();
        }

        //Method 5. Debit. Used to deduct funds from a user's balance.
        public void debit(double amount) throws IOException {
            workDone++;
            if (getBalance() >= amount) {
                setBalance(getBalance() - amount);
                System.out.println("Transaction successful. Current balance " + getBalance());
            } else {
                System.out.println("Not enough funds. Transaction can not be completed.");
            }
            Bank.auto();
        }

        //Method 6. Interest. Used to add funds to a user's balance (using rate of interest(change if necessary))
        public void payInterest() throws IOException {
            if (getDeposit() != null) {
                setBalance(getBalance() + getDeposit().getMonthlyPayment());
                getDeposit().setMonths(getDeposit().getMonths() - 1);
                System.out.println("Dear Ms or Mrs,\n" + "Your deposit in our bank has produced " + getDeposit().getMonthlyPayment());
                if (getDeposit().getMonths() == 0) {
                    this.deposit = null;
                }
            } else {
                System.out.println("No deposit found.");
            }
        }

        //Method 7. forceSetOverdraft. Only used in method 'payLoan' when the DLL.Customer doesn't have enough "Minimum amount"
        //to pay the loan AND doesn't have overdraft activated already. Minimum amount is "Loan size * 10%"
        //No workDone++ or Bank.auto(); method call, because this is an internal method, which has little meaning to the user.
        private void forceSetOverdraft() {
            if (canOverdraft()) {
                return;
            }
            overdraftSet(true);
            setBalance(getBalance() - 100);
        }

        //Method 8. Loan payment... uses 'forceSetOverdraft' method if minimum funds are insufficient.
        ////Method Bank.auto(); is not applicable here, because this method manually collects a loan payment from the user.
        public void payLoan(double amount) throws IOException {
            workDone++;
            if (getLoan() != null) {
                if (amount < getLoan().getMonthlyPayment()) {
                    amount = getLoan().getMonthlyPayment();
                }
                if (getLoan().getAmount() == 0) {
                    System.out.println("Your loan is fully paid.");
                    return;
                }
                if (getLoan().getAmount() > 0 && amount == 0) {
                    if (getBalance() < getLoan().getMonthlyPayment()) {
                        forceSetOverdraft();
                    }
                    System.out.println("Deducting minimal funds");
                    setBalance(getBalance() - (getLoan().getMonthlyPayment()));
                    System.out.println("Minimal funds deducted: " + getLoan().getMonthlyPayment());
                    System.out.println("Current balance is: " + getBalance());
                    getLoan().setMonths(getLoan().getMonths() - 1);
                    System.out.println("Only " + getLoan().getMonths() + " months left to pay.");
                    getLoan().setAmount(getLoan().getAmount() - 0.888 * amount);
                    return;
                }
                if (getLoan().getAmount() > 0 && amount > 0) {
                    if (getBalance() < amount && canOverdraft()) {
                        System.out.println("Deducting specified amount");
                        setBalance(getBalance() - amount);
                        System.out.println("Funds deducted. Current balance is: " + getBalance());
                        getLoan().setMonths(getLoan().getMonths() - 1);
                        System.out.println("Only " + getLoan().getMonths() + " months left to pay.");
                        getLoan().setAmount(getLoan().getAmount() - 0.888 * amount);
                        return;
                    }
                    if (getBalance() < amount && !canOverdraft()) {
                        System.out.println("Not enough funds.");
                        return;
                    }
                    if (getBalance() > amount) {
                        System.out.println("Deducting specified amount");
                        setBalance(getBalance() - amount);
                        getLoan().setMonths(getLoan().getMonths() - 1);
                        System.out.println("Only " + getLoan().getMonths() + " months left to pay.");
                        getLoan().setAmount(getLoan().getAmount() - 0.888 * amount);
                        return;
                    }
                }
                System.out.println("Sad day. Unknown error occurred");
            }
            System.out.println("No loan found. You can apply for a new load at any of our branches. Have a nice day.");
            Bank.auto();
        }

        //This method prints top 3 customers sorted by the total amount of their loan
        public static void printTopCustomers(ArrayList<DLL.Customer> sorted) {
            System.out.println("Here is the information about the top 3 customers.");
            System.out.println(sorted.get(0));
            System.out.println(sorted.get(1));
            System.out.println(sorted.get(2));
        }
    }
}
