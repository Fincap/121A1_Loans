public class FixedLoan extends Loan {

	private double rate;

	public FixedLoan(int loanID, String suburb, double balance, int term, double rate) {
		super(loanID, suburb, balance, term);
		this.rate = rate;
	}

	public void addInterest() {
		balance += rate * balance;
		term--;
	}

	public void print() {
		System.out.printf("%d: %s | $%.2f | %d months | %.2f%% | Last Payment: (%s)", loanID, suburb, balance, term, rate * 100, lastPayment.toString());
		if (isPaidOff()) System.out.printf(" | PAID OFF\n");
		else System.out.printf("\n");
	}
}
