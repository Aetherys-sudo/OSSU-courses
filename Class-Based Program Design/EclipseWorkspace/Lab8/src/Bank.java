
// Represents a Bank with list of accounts
public class Bank {
    
    String name;
    ILoA accounts;
    
    public Bank(String name){
        this.name = name;

        // Each bank starts with no accounts
        this.accounts = new MtLoA();
    }
    
    void openAcct(String type) {
    	int idx;
    	idx = this.accounts.maxIndex();
    	if (type.toLowerCase().compareTo("savings") == 0) {
    		this.add(new Savings(idx, 0, "Savings Account", 2.5));
    	} else if (type.toLowerCase().compareTo("credit") == 0) {
    		this.add(new Credit(idx, 0, "Credit Account", 1000, 2.5));
    	} else if (type.toLowerCase().compareTo("checking") == 0) {
    		this.add(new Checking(idx, 0, "Checking Account", 20));
    	} else {
    		throw new RuntimeException("Please specify the account type.");
    	}
    }
    
    void add(Account acct) {
    	this.accounts = new ConsLoA(acct, this.accounts);
    }
    
    void deposit(int accNum, int amount) {
    	if (this.accounts.deposit(accNum, amount) == -1) {
    		throw new RuntimeException("Account not found");
    	} else {
    		return;
    	}
    }
    
    void withdraw(int accNum, int amount) {
    	if (this.accounts.withdraw(accNum, amount) == -1) {
    		throw new RuntimeException("Account not found");
    	} else {
    		return;
    	}
    }
    
    void removeAcc(int idx) {
    	this.accounts = this.accounts.removeAcc(idx);
    }


}
