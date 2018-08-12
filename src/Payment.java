import java.util.Calendar;

public class Payment {

	private int paymentID;
	private int loanID;
	private double amount;
	private Calendar date;

	public Payment(int paymentID, int loanID, double amount, Calendar date) {
		this.paymentID = paymentID;
		this.loanID = loanID;
		this.amount = amount;
		this.date = date;
		checkLoan();
	}

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
