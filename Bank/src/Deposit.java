public class Deposit extends Transaction {

    private double months;
    private double amount;
    private double rate;
    private double monthlyPayment;

    static Deposit minimal = new Deposit(3, 100000, 4);
    static Deposit standard = new Deposit(12, 250000, 6);
    static Deposit mostFrequent = new Deposit(36, 750000, 10.5);

    public Deposit(int months, double amount, double rate) {
        this.months = months;
        this.amount = amount;
        this.rate = rate;
    }

    public double getMonths() {
        return months;
    }

    public void setMonths(double months) {
        this.months = months;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getMonthlyPayment() {
        monthlyPayment = (amount * (rate / 100)) * (months / 12);
        return monthlyPayment;
    }

    @Override
    public String toString() {
        return "Months = " + months + "\nAmount = " + amount + "\nInterest = " + rate;
    }

}
