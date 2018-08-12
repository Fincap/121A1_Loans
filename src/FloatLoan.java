public class FloatLoan extends Loan {

	private double offset;

	public FloatLoan(int loanID, String suburb, double balance, int term, double offset) {
		super(loanID, suburb, balance, term);
		this.offset = offset;
	}

	public void depositOffset(double amount) {
		offset += amount;
	}

	public void withdrawOffset(double amount) {
		offset -= amount;
	}

	public void addInterest() {
		balance += InterestRate.getFloatRate() * (balance - offset);
		term--;
	}

	// Makes a payment to the loan, and adding the offset amount to the payment
	public void makePayment(Payment payment) {
		balance -= payment.getAmount() + offset;
		offset = 0;
		lastPayment = payment;
	}

	public boolean hasOffsetAccount() {
		return true;
	}

	public void print() {
		System.out.printf("%d: %s | $%.2f | %d months | Floating rate: %.2f%% | Offset: $%.2f | Last Payment: (%s)\n", loanID, suburb, balance, term, InterestRate.getFloatRate() * 100, offset, lastPayment.toString());
	}

}
