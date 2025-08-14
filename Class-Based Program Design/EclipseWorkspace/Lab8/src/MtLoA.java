
// Represents the empty List of Accounts
public class MtLoA implements ILoA{
    MtLoA() { }
    
    public int maxIndex() {
    	return 1;
    }
    
    public int maxIndexHelp(int maxIdx) {
    	return maxIdx;
    }
    
    public int deposit(int accNum, int amount) {
    	return -1;
    }
    
    public int withdraw(int accNum, int amount) {
    	return -1;
    }
    
    public ILoA removeAcc(int idx) {
    	throw new RuntimeException("Cannot remove account, account not found");
    }
    
    public ILoA removeAccHelper(int idx) {
    	return this;
    }
}

