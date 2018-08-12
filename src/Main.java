import com.sun.org.apache.bcel.internal.classfile.SourceFile;

import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Scanner;

public class Main {

	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		boolean running = true;

		// Main loop
		while (running) {
			System.out.println("\n----------------\nCHOOSE AN OPTION\n----------------");
			System.out.println("1. View all loans");
			System.out.println("2. Open a new loan");
			System.out.println("3. Make payment to loan");
			System.out.println("4. Manage offset accounts");
			System.out.println("5. Manage floating interest rate");
			System.out.println("6. Apply interest to all loans");
			System.out.println("7. Advanced options");
			System.out.println("0. Exit program");

			System.out.print("> ");

			int choice = in.nextInt();
			System.out.println();

			switch (choice) {
				case 1:
					Loan.printAll();
					break;

				case 2:
					openLoan();
					break;

				case 3:
					makePayment();
					break;

				case 4:
					manageOffset();
					break;

				case 5:
					manageInterest();
					break;

				case 6:
					Loan.addInterestAll();
					System.out.println("Applied interest to all loans!");
					break;

				case 0:
					running = false;
					System.out.println("Thank you for playing Wing Commander!");
					break;

				default:
					System.out.println("Invalid command!");
					break;
			}
		}
	}

	private static void makePayment() {
	}

	private static void openLoan() {
		System.out.println("Choose a loan type:\n1. Fixed Loan\n2. Floating Loan");
		int choice = in.nextInt();

		switch (choice) {
			case 1:
				System.out.print("Enter loan ID (unique integer) > ");
				int newLoanID = in.nextInt();

				System.out.print("Enter suburb > ");
				String newSuburb = new Scanner(System.in).nextLine(); // Hacky workaround. Can't use typical in.nextLine() before this input to flush scanner, because then it grabs the wrong nextLine.

				in.nextLine(); // Flushes the scanner's input buffer
				System.out.print("Enter amount > ");
				double newAmount = in.nextDouble();

				in.nextLine();
				System.out.print("Enter term > ");
				int newTerm = in.nextInt();

				in.nextLine();
				System.out.print("Enter rate (0 = 0.00%, 1 = 100.00%, 0.5 = 50.00%) > ");
				double newRate = in.nextDouble();

				Loan newLoan = new FixedLoan(newLoanID, newSuburb, newAmount, newTerm, newRate);
				System.out.println("\n\nNew loan:");
				newLoan.print();

				break;

			case 2:
				System.out.print("Enter loan ID (unique integer) > ");
				newLoanID = in.nextInt();

				System.out.print("Enter suburb > ");
				newSuburb = new Scanner(System.in).nextLine(); // Hacky workaround. Can't use typical in.nextLine() before this input to flush scanner, because then it grabs the wrong nextLine.

				in.nextLine(); // Flushes the scanner's input buffer
				System.out.print("Enter amount > ");
				newAmount = in.nextDouble();

				in.nextLine();
				System.out.print("Enter term > ");
				newTerm = in.nextInt();

				in.nextLine();
				System.out.print("Enter starting offset > ");
				double newOffset = in.nextDouble();

				newLoan = new FloatLoan(newLoanID, newSuburb, newAmount, newTerm, newOffset);
				System.out.println("\n\nNew loan:");
				newLoan.print();

				break;

			default:
				System.out.println("Invalid option, returning to menu...");
				break;
		}

	}

	private static void manageOffset() {
		System.out.println("1. Deposit into offset account\n2. Withdraw from offset account");
		int choice = in.nextInt();
		switch (choice) {
			case 1:
				// Print out all floating loans
				for (Loan loan : Loan.allLoans) {
					if (loan.hasOffsetAccount()) {
						loan.print();
					}
				}

				in.nextLine(); // Flush
				System.out.print("Enter loan ID > ");
				int selectedLoanID = in.nextInt();

				in.nextLine();
				System.out.print("Enter amount to deposit > ");
				double depositAmount = in.nextDouble();

				((FloatLoan) Loan.getLoan(selectedLoanID)).depositOffset(depositAmount); // Casts selected loan to FloatLoan, so that the depositOffset method may be used

				System.out.printf("$%.2f deposited into account %d", depositAmount, selectedLoanID);

				break;

			case 2:
				// Print out all floating loans
				for (Loan loan : Loan.allLoans) {
					if (loan.hasOffsetAccount()) {
						loan.print();
					}
				}

				in.nextLine(); // Flush
				System.out.print("Enter loan ID > ");
				selectedLoanID = in.nextInt();

				in.nextLine();
				System.out.print("Enter amount to withdraw > ");
				double withdrawAmount = in.nextDouble();

				((FloatLoan) Loan.getLoan(selectedLoanID)).withdrawOffset(withdrawAmount); // Casts selected loan to FloatLoan, so that the withdrawOffset method may be used

				System.out.printf("$%.2f withdrawn from account %d", withdrawAmount, selectedLoanID);

				break;

			default:
				System.out.println("Invalid option, returning to menu...");
				break;
		}
	}

	private static void manageInterest() {
		System.out.printf("Current interest rate: %.2f%%\n", InterestRate.getFloatRate() * 100);
		System.out.println("1. Set new interest rate\n2. Return");
		int choice = in.nextInt();
		switch (choice) {
			case 1:
				in.nextLine(); // Flush
				System.out.println("(0 = 0.00%, 1 = 100.00%, 0.5 = 50.00%)");
				System.out.print("> ");
				double newRate = in.nextDouble();
				InterestRate.setFloatRate(newRate);

				break;

			case 2:
				break;

			default:
				System.out.println("Invalid option, returning to menu...");
				break;
		}
	}

	public static void batchTestStandard() {
		InterestRate.setFloatRate(0.1);

		FixedLoan loan1 = new FixedLoan(0, "Stanwell Park", 700, 6, 0.5);
		FloatLoan loan2 = new FloatLoan(1, "Otford", 340, 2, 0);
		loan1.print();
		loan2.print();

		System.out.println("\nAfter interest");

		Loan.addInterestAll();
		loan1.print();
		loan2.print();

		System.out.println("\nAfter payment + offset");

		new Payment(loan1.getLoanID(), 100, new GregorianCalendar(2018, 6, 10));
		loan2.depositOffset(100);

		loan1.print();
		loan2.print();

		Loan.addInterestAll();

		System.out.println("\nAfter interest again");

		loan1.print();
		loan2.print();

		System.out.println("\nAfter paid off");
		new Payment(loan1.getLoanID(), 1425, new GregorianCalendar());

		loan1.print();
		loan2.print();

		System.out.println("\nLoans total");
		double loanTotal = 0;
		for (Loan loan : Loan.allLoans) {
			loanTotal += loan.getBalance();
		}
		System.out.println(loanTotal);
	}

}
