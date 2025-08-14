
// Represents a credit line account
public class Credit extends Account{

    int creditLine;  // Maximum amount accessible
    double interest; // The interest rate charged
    
    public Credit(int accountNum, int balance, String name, int creditLine, double interest){
        super(accountNum, balance, name);
        this.creditLine = creditLine;
        this.interest = interest;
    }
    
    int withdraw(int amount) {
    	if (this.balance + amount > this.creditLine) {
    		throw new RuntimeException("You cannot withdraw more money as the maximum credit has been reached.");
    	} else {
    		this.balance = this.balance + amount;
    		return this.balance;
    	}
    }
    
    int deposit(int funds) {
    	if (this.balance - funds < 0) {
    		throw new RuntimeException("You cannot restock above the debt limit.");
    	} else {
    		this.balance = this.balance - funds;
        	return this.balance;
    	}
    }
}
