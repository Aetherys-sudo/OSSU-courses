import tester.*;

// Bank Account Examples and Tests
public class Examples {

    public Examples(){ reset(); }
    
    Account check1;
    Account check2;
    Account check3;
    Account check4;
    Account check5;
    
    Account savings1;
    Account savings2;
    Account savings3;

    Account credit1;
    Account credit2;
    Account credit3;
    Account credit4;
    Account credit5;
    
    Bank bank1;
    Bank bank2;
    Bank bank3;
    
    // Initializes accounts to use for testing with effects.
    // We place inside reset() so we can "reuse" the accounts
    public void reset(){
        
        // Initialize the account examples
        check1 = new Checking(1, 100, "First Checking Account", 20);
        check2 = new Checking(2, 1000, "Second Checking Account", 20);
        check3 = new Checking(3, 10000, "Third Checking Account", 20);
        check4 = new Checking(31, 1020, "Fourth Checking Account", 20);
        check5 = new Checking(32, 1019, "Fifth Checking Account", 20);
        
        savings1 = new Savings(4, 100, "First Savings Account", 2.5);
        savings2 = new Savings(5, 1000, "Second Savings Account", 2.5);
        savings3 = new Savings(6, 2000, "Third Savings Account", 2.5);
        
        credit1 = new Credit(7, 100, "First Credit Account", 1000, 2.5);
        credit2 = new Credit(8, 1000, "Second Credit Account", 1000, 2.5);
        credit3 = new Credit(9, 2000, "Third Credit Account", 10000, 2.5);
        credit4 = new Credit(10, 1000, "Fourth Credit Account", 2000, 2.5);
        credit5 = new Credit(11, 1000, "Fifth Credit Account", 1999, 2.5);
        
        
        bank1 = new Bank("Coitz");
        
        bank2 = new Bank("Ovarutz");
        bank2.accounts = new ConsLoA(check1, new MtLoA());
        
        bank3 = new Bank("Based");
        bank3.accounts = new ConsLoA(check3, new ConsLoA(check2, new ConsLoA(check1, new MtLoA())));
    }
    
    // Tests the exceptions we expect to be thrown when
    //   performing an "illegal" action.
    public void testExceptions(Tester t){
        reset();
        t.checkException("You cannot withdraw more money.",
                         new RuntimeException("You cannot withdraw more money."),
                         this.check1,
                         "withdraw",
                         1000);
        t.checkException("You cannot withdraw more money.",
                new RuntimeException("You cannot withdraw more money."),
                this.check2,
                "withdraw",
                1000);
        t.checkException("You cannot withdraw more money.",
                new RuntimeException("You cannot withdraw more money."),
                this.check5,
                "withdraw",
                1000);
        
        t.checkException("You cannot withdraw more than your current balance.",
                new RuntimeException("You cannot withdraw more than your current balance."),
                this.savings1,
                "withdraw",
                1000);
        
        t.checkException("You cannot withdraw more than your current balance.",
                new RuntimeException("You cannot withdraw more than your current balance."),
                this.savings1,
                "withdraw",
                1000);
        
        t.checkException("You cannot withdraw more money as the maximum credit has been reached.",
                new RuntimeException("You cannot withdraw more money as the maximum credit has been reached."),
                this.credit1,
                "withdraw",
                1000);
        
        t.checkException("You cannot withdraw more money as the maximum credit has been reached.",
                new RuntimeException("You cannot withdraw more money as the maximum credit has been reached."),
                this.credit2,
                "withdraw",
                1000);
        
        t.checkException("You cannot withdraw more money as the maximum credit has been reached.",
                new RuntimeException("You cannot withdraw more money as the maximum credit has been reached."),
                this.credit5,
                "withdraw",
                1000);
        t.checkException("You cannot restock above the debt limit.",
                new RuntimeException("You cannot restock above the debt limit."),
                this.credit1,
                "deposit",
                150);
        
        t.checkException("Account not found",
                new RuntimeException("Account not found"),
                this.bank1,
                "deposit",
                1, 150);
        
        t.checkException("Account not found",
                new RuntimeException("Account not found"),
                this.bank2,
                "deposit",
                10, 150);
        
        t.checkException("You cannot withdraw more money.",
                new RuntimeException("You cannot withdraw more money."),
                this.bank3,
                "withdraw",
                1, 1000);
        
        bank3.add(savings1);
        t.checkException("You cannot withdraw more than your current balance.",
                new RuntimeException("You cannot withdraw more than your current balance."),
                this.bank3,
                "withdraw",
                4, 1000);
        
        t.checkException("Cannot remove account, account not found",
                new RuntimeException("Cannot remove account, account not found"),
                this.bank1,
                "removeAcc",
                4);
        
    }
    
    public void testWithdraw(Tester t) {
    	reset();
    	t.checkExpect(this.check3.withdraw(1000), 9000);
    	t.checkExpect(this.check4.withdraw(1000), 20);
    	t.checkExpect(this.savings2.withdraw(1000), 0);
    	t.checkExpect(this.savings3.withdraw(1000), 1000);
    	t.checkExpect(this.credit3.withdraw(1000), 3000);
    	t.checkExpect(this.credit4.withdraw(1000), 2000);
    }
    
    // Test the deposit method(s)
    public void testDeposit(Tester t){
        reset();
        t.checkExpect(this.check1.deposit(150), 250);
        t.checkExpect(this.check1.withdraw(75), 175);
        t.checkExpect(this.check1.deposit(1000), 1175);
        
        t.checkExpect(this.savings1.deposit(150), 250);
        t.checkExpect(this.savings1.withdraw(75), 175);
        t.checkExpect(this.savings1.deposit(1000), 1175);
        
        
        t.checkExpect(this.credit2.deposit(1000), 0);
        t.checkExpect(this.credit2.withdraw(500), 500);
        t.checkExpect(this.credit2.deposit(499), 1);
    }
    
    public void testAdd(Tester t) {
    	reset();
    	
    	bank1.add(check2);
    	t.checkExpect(bank1.accounts, new ConsLoA(check2, new MtLoA()));
    	
    	bank2.add(credit1);
    	t.checkExpect(bank2.accounts, new ConsLoA(credit1, new ConsLoA(check1, new MtLoA())));
    	
    	bank3.add(savings1);
    	bank3.add(savings2);
    	t.checkExpect(bank3.accounts, new ConsLoA(savings2, new ConsLoA(savings1, new ConsLoA(check3, new ConsLoA(check2, new ConsLoA(check1, new MtLoA()))))));
    	
    }
    
    public void testDepositBank(Tester t) {
    	reset();
    	
    	bank1.add(check2);
    	bank1.deposit(2, 100);
    	t.checkExpect(check2.balance, 1100);
    	
    	bank2.deposit(1, 100);
    	bank2.deposit(1, 100);
    	t.checkExpect(check1.balance, 300);
    	
    	bank3.add(savings1);
    	bank3.add(savings2);
    	
    	bank3.deposit(1, 1000);
    	bank3.deposit(2, 1000);
    	bank3.deposit(3, 1000);
    	bank3.deposit(4, 1000);
    	bank3.deposit(5, 1000);
    	t.checkExpect(check1.balance, 1300);
    	t.checkExpect(check2.balance, 2100);
    	t.checkExpect(check3.balance, 11000);
    	t.checkExpect(savings1.balance, 1100);
    	t.checkExpect(savings2.balance, 2000);
    	
    }
    
    public void testWithdrawBank(Tester t) {
    	reset();
    	
    	bank1.add(check2);
    	bank1.withdraw(2, 100);
    	t.checkExpect(check2.balance, 900);
    	
    	bank2.withdraw(1, 50);
    	bank2.withdraw(1, 30);
    	t.checkExpect(check1.balance, 20);
    	
    	bank3.add(savings1);
    	bank3.add(savings2);
    	
    	bank3.withdraw(2, 880);
    	bank3.withdraw(3, 1000);
    	bank3.withdraw(5, 1000);
    	t.checkExpect(check2.balance, 20);
    	t.checkExpect(check3.balance, 9000);
    	t.checkExpect(savings2.balance, 0);
    	
    }
    
    public void testRemove(Tester t) {
    	reset();
    	
        t.checkException("Cannot remove account, account not found",
                new RuntimeException("Cannot remove account, account not found"),
                this.bank1,
                "removeAcc",
                1);
        
        bank1.add(check2);
        bank1.removeAcc(2);
        t.checkExpect(bank1.accounts, new MtLoA());
        
        t.checkException("Cannot remove account, account not found",
                new RuntimeException("Cannot remove account, account not found"),
                this.bank1,
                "removeAcc",
                2);
        
    	bank3.add(savings1);
    	bank3.add(savings2);
        
        t.checkExpect(bank3.accounts, new ConsLoA(savings2, new ConsLoA(savings1, new ConsLoA(check3, new ConsLoA(check2, new ConsLoA(check1, new MtLoA()))))));
        bank3.removeAcc(1);
        t.checkExpect(bank3.accounts, new ConsLoA(savings2, new ConsLoA(savings1, new ConsLoA(check3, new ConsLoA(check2, new MtLoA())))));
        bank3.removeAcc(5);
        t.checkExpect(bank3.accounts, new ConsLoA(savings1, new ConsLoA(check3, new ConsLoA(check2, new MtLoA()))));
        bank3.removeAcc(3);
        t.checkExpect(bank3.accounts, new ConsLoA(savings1, new ConsLoA(check2, new MtLoA())));
    }
}
