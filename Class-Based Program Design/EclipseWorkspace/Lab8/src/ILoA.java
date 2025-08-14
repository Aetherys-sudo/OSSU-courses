
// Represents a List of Accounts
public interface ILoA{
    int maxIndex();
    
    int maxIndexHelp(int maxIdx);
    
    int deposit(int idx, int amount);
    
    int withdraw(int idx, int amount);
    
    ILoA removeAcc(int idx);
    
    ILoA removeAccHelper(int idx);
}

