
// Represents a non-empty List of Accounts...
public class ConsLoA implements ILoA{

    Account first;
    ILoA rest;

    public ConsLoA(Account first, ILoA rest){
        this.first = first;
        this.rest = rest;
    }
    
    /* Template
     *  Fields:
     *    ... this.first ...         --- Account
     *    ... this.rest ...          --- ILoA
     *
     *  Methods:
     *
     *  Methods for Fields:
     *
     */
    public int maxIndex() {
    	return maxIndexHelp(0);
    }
    
    public int maxIndexHelp(int maxIdx) {
    	if (this.first.accountNum > maxIdx) {
    		return this.rest.maxIndexHelp(this.first.accountNum);
    	} else {
    		return this.rest.maxIndexHelp(maxIdx);
    	}
    }
    
    public int deposit(int accNum, int amount) {
    	if (this.first.accountNum == accNum) {
    		this.first.deposit(amount);
    		return this.first.accountNum;
    	} else {
    		return this.rest.deposit(accNum, amount);
    	}
    }
    
    public int withdraw(int accNum, int amount) {
    	if (this.first.accountNum == accNum) {
    		this.first.withdraw(amount);
    		return this.first.accountNum;
    	} else {
    		return this.rest.withdraw(accNum, amount);
    	}
    }
    
    public ILoA removeAcc(int idx) {
    	return this.removeAccHelper(idx);
    }
    
    public ILoA removeAccHelper(int idx) {
    	if (this.first.accountNum == idx) {
    		return this.rest.removeAccHelper(idx);
    	} else {
    		return new ConsLoA(this.first, this.rest.removeAccHelper(idx));
    	}
    }
}