public class Loan extends Transaction {

    private double months;
    private double amount;
    private double rate;

    static Loan minimalLoan = new Loan(6, 200000, 24);
    static Loan standardLoan = new Loan(36, 2000000, 22);
    static Loan maximalLoan = new Loan(10, 5000000, 21);

    public Loan(int months, double amount, double rate) {
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

    @Override
    public String toString() {
        return "Months = " + months + "\nAmount = " + amount + "\nInterest = " + rate;
    }

    public double getMonthlyPayment() {
        double monthlyInterest = getRate() / 1200;
        double coefficient = (monthlyInterest * Math.pow(1 + monthlyInterest, getMonths())) / (Math.pow(1 + monthlyInterest, getMonths()) - 1);
        double monthlyPayment = coefficient * getAmount();
        if (monthlyPayment > 0) {
            return monthlyPayment;
        }
        return 0;
    }


}
