import java.util.Calendar;
import java.util.GregorianCalendar;

public class Payment {

	private static int paymentNum = 0;

	private int paymentID;
	private int loanID;
	private double amount;
	private Calendar date;

	public Payment(int loanID, double amount, Calendar date) {
		// Generates payment id from total amount of payments, then increments
		this.paymentID = paymentNum;
		paymentNum++;
		this.loanID = loanID;
		this.amount = amount;
		this.date = date;
		checkLoan();
	}

	// Constructor used for "default" lastPayments for loans
	public Payment(int loanID) {
		this.paymentID = paymentNum;
		paymentNum++;
		this.loanID = loanID;
		this.amount = 0;
		this.date = new GregorianCalendar(1970, 0, 1); // Date of 1-Jan-1970 indicates "default" payment (Object counts months from 0)
	}

	// Checks if the loan ID given exists, and if it does instantly makes the payment to the loan
	private boolean checkLoan() {
		for (Loan loan : Loan.allLoans) {
			if (loanID == loan.getLoanID()) {
				loan.makePayment(this);
				return true;
			}
		}

		return false;
	}

	public double getAmount() {
		return amount;
	}

	public Calendar getDate() {
		return date;
	}

	public String toString() {
		return String.format("$%.2f | %d-%d-%d", amount, date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1, date.get(Calendar.DAY_OF_MONTH)); // Add one to month because January = 0
	}
}
