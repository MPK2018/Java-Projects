/**
* Account.java
*/
/**
 * @author {Mugdha Phadke}
 *
 *The Checking Account and SavingsAccount classes inherits the Accounts class
 */

// BalanceInterface makes this class to have print balances method
// I did not add deposit and withdrawal because it was making it to be public
// I did not think those methods should be public.

class Account implements BalanceInterface {

	private int accountNo; // Account number auto generated by useAccount class
	private String type; // Type of account
	protected double balance; // Balance

	// Constructor takes the account number which is created in UserAccout 
	//and checking or savings type 
	Account(int accountNo, String type) {
		this.accountNo = accountNo;
		this.type = type; // "C for checking" or "S for savings"
		balance = 1000.00; // initial balance
	}

	// getter and setter methods
	double getTransactionFee() { // This is implemented on checking account
		return 0;
	}

	int getAccountNO() {
		return this.accountNo;
	}

	void setBalance(double amount) {
		this.balance = amount;
	}

	double getBalance() {
		return this.balance;
	}

	String getType() {
		return this.type;
	}

	// Withdraw amount part of the functionality is in child classes
	boolean Withdrawal(double amount) { // returns 0 if insufficient funds through child classes
		double availableBalance = this.balance;
		if (this.type == "C")
			availableBalance = this.balance - this.getTransactionFee();
		if (availableBalance >= amount) {
			this.balance -= amount;
			return true;
		} else
			return false;
	}

	void Deposits(double amount) { // Deposit amount part of the functionality is in child classes
		this.balance += amount;
	}

	@Override
	public void PrintBalance(String prompt) {
		// @param balance and 1 for printing in currency format
		System.out.println(prompt + FormatedPrinting.formatObject(this.balance, 1));
	}

	@Override
	public void PrintFormattedFees() {
	} // print the corresponding formatted fees

}

class CheckingAccount extends Account {

	private double transctionFee; // transaction fee

	CheckingAccount(int accountNo, double transactionFee) {
		super(accountNo, "C");
		this.transctionFee = (transactionFee
				* (-1.00)); /* acts as transaction flag and fee set to negative initially and
							 * gets updated to positive when   
							 * the first checking account transaction takes place
							 */
	}

	// setter and getter methods
	void setTransactionfee(double fee) {
		this.transctionFee = fee;
	}

	// @Override
	double getTransactionFee() {
		return this.transctionFee;
	}

	double getAvailabelBalance() {
		return (this.balance - this.transctionFee);
	}

	/*
	 * this method checks if their is sufficient balance for withdrawal and
	 * transaction fee in case of insufficient balance it prints the message and
	 * returns 0
	 * 
	 * @see Account#Withdrawal(double)
	 */

	boolean Withdrawal(double amount) {
		/*
		 * transaction fee is set to negative initially to denote that no transactions
		 * have done from the checking account If this is the first transaction in the
		 * checking account set it to positive so the transaction fee is calculated at
		 * the end
		 */
		if (this.transctionFee < 0)
			this.transctionFee *= (-1.00);
		return super.Withdrawal(amount);

	}

	/*
	 * This methods sets the transaction fee to positive if this is a first
	 * transaction in a checking account so that the this fee will be deducted from
	 * the account ata the end
	 * 
	 */
	void Deposits(double amount) {
		/*
		 * transaction fee is set to negative initially to denote that no transactions
		 * have done from the checking account If this is the first transaction in the
		 * checking account set it to positive so the transaction fee is calculated at
		 * the end
		 */
		if (this.transctionFee < 0)
			this.transctionFee *= (-1.00);
		super.Deposits(amount);

	}

	// Print the formatted transaction Fees
	public void PrintFormattedFees() {
		if (this.transctionFee > 0) {
			this.balance -= this.transctionFee;
			System.out.println("Checking fee: " + FormatedPrinting.formatObject(this.transctionFee, 1));
		} else {
			System.out.println("No checking account transaction ");
			System.out.println("Checking fee: $0.00");
		}
	}

}

class SavingsAccount extends Account {

	private int interestRate; // interest rate

	SavingsAccount(int accountNo, int interestRate) {
		super(accountNo, "S");
		this.interestRate = interestRate;
	}

	// getter and setter methods
	void setInterestRate(int fee) {
		this.interestRate = fee;
	}

	double getInterestRate() {
		return this.interestRate;
	}

	double getAvailabelBalance() {
		return (this.balance);
	}

	// printing interest in percent format
	public void PrintFormattedFees() {
		double interest = (this.balance * (this.interestRate)) / 100;
		this.balance += interest;
		System.out.println("Savings interest payment: " + FormatedPrinting.formatObject(interest, 1));
	}

}
