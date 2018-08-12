import java.util.ArrayList;
import java.util.GregorianCalendar;

abstract class Loan {

	public static ArrayList<Loan> allLoans = new ArrayList<>();

	protected int loanID;
	protected String suburb;
	protected double balance;
	protected int term;
	protected Payment lastPayment;

	public Loan(int loanID, String suburb, double balance, int term) {
		this.loanID = loanID;
		this.suburb = suburb;
		this.balance = balance;
		this.term = term;
		this.lastPayment = new Payment(-1, this.loanID, 0, new GregorianCalendar(1970, 0, 1)); // New GregorianCalendar object with Jan 1st 1970 as default. ID less than 0 indicates a "default" lastPayment

		allLoans.add(this);
	}

	public void makePayment(Payment payment) {
		balance -= payment.getAmount();
		lastPayment = payment;
	}

	public abstract void addInterest();

	public static void addInterestAll() {
		for (Loan loan : allLoans) {
			loan.addInterest();
		}
	}

	public int getLoanID() {
		return loanID;
	}

	public double getBalance() {
		return balance;
	}

	public boolean hasOffsetAccount() {
		return false;
	}

	public boolean isPaidOff() {
		return balance <= 0;
	}

	public abstract void print();

	public static void printAll() {
		for (Loan loan : allLoans) {
			loan.print();
		}
	}

}
