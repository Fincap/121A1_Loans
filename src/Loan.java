import java.util.ArrayList;
import java.util.GregorianCalendar;

abstract class Loan {

	public static ArrayList<Loan> allLoans = new ArrayList<>(); // A static list containing all loans

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
		this.lastPayment = new Payment(this.loanID); // Creates payment using the Payment "default" constructor that only takes a loanID.

		allLoans.add(this); // Upon creation, a loan is added to the static list of all loans
	}

	// Processes a new payment to the loan
	public void makePayment(Payment payment) {
		balance -= payment.getAmount();
		lastPayment = payment;
	}

	// Abstract method that all children must have a version of
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

	public Payment getLastPayment() {
		return lastPayment;
	}

	// Method used to determine if loan has an offset account
	public boolean hasOffsetAccount() {
		return false;
	}

	public boolean isPaidOff() {
		return balance <= 0;
	}

	// Returns the loan that has an ID matching the parameter. Returns null if not found
	public static Loan getLoan(int loanID) {
		for (Loan loan : allLoans) {
			if (loan.getLoanID() == loanID) return loan;
		}
		return null;
	}

	public abstract void print();

	public static void printAll() {
		for (Loan loan : allLoans) {
			loan.print();
		}
	}

}
