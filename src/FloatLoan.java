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
		offset -= InterestRate.getFloatRate() * offset;
		term--;
	}

	public boolean hasOffsetAccount() {
		return true;
	}

	public void print() {
		System.out.printf("%d: %s | $%.2f | %d months | Floating rate: %.2f%% | Offset: $%.2f | Last Payment: (%s)\n", loanID, suburb, balance, term, InterestRate.getFloatRate() * 100, offset, lastPayment.toString());
	}

}
