import tester.*;


// runs tests for the buddies problem
public class ExamplesBuddies {
	Person ann = new Person("ann");
	Person bob = new Person("bob");
	Person cole = new Person("cole");
	Person ed = new Person("ed");
	Person hank = new Person("hank");
	Person dan = new Person("dan");
	Person fay = new Person("fay");
	Person gaby = new Person("gaby");
	Person jan = new Person("jan");
	Person kim = new Person("kim");
	Person len = new Person("len");
	
	void initData() {
		this.ann = new Person("ann");     
		this.bob = new Person("bob");     
		this.cole = new Person("cole");   
		this.ed = new Person("ed");       
		this.hank = new Person("hank");   
		this.dan = new Person("dan");     
		this.fay = new Person("fay");     
		this.gaby = new Person("gaby");   
		this.jan = new Person("jan");
		this.kim = new Person("kim");
		this.len = new Person("len");
		
		this.ann.addBuddy(this.bob);  
		this.ann.addBuddy(this.cole); 
		this.bob.addBuddy(this.ann);
		this.bob.addBuddy(this.ed); 
		this.bob.addBuddy(this.hank); 
		this.bob.addBuddy(this.gaby); 
		this.cole.addBuddy(this.dan);
		this.dan.addBuddy(this.cole);
		this.ed.addBuddy(this.fay); 
		this.fay.addBuddy(this.ed);
		this.fay.addBuddy(this.gaby);
		this.gaby.addBuddy(this.ed);
		this.gaby.addBuddy(this.fay);
		this.jan.addBuddy(this.kim);
		this.jan.addBuddy(this.len);
		this.kim.addBuddy(this.jan);
		this.kim.addBuddy(this.len);
		this.len.addBuddy(this.jan);
		this.len.addBuddy(this.kim);
		
	}
	
	void testBuddies(Tester t) {
		initData();
		t.checkExpect(ann.buddies, new ConsLoBuddy(this.cole, new ConsLoBuddy(this.bob, new MTLoBuddy())));
		t.checkExpect(hank.buddies, new MTLoBuddy());
		t.checkExpect(fay.buddies, new ConsLoBuddy(this.gaby, new ConsLoBuddy(this.ed, new MTLoBuddy())));
	}
	
	void testDirectBuddy(Tester t) {
		initData();
		
		t.checkExpect(ann.hasDirectBuddy(bob), true);
		t.checkExpect(hank.hasDirectBuddy(bob), false);
		t.checkExpect(fay.hasDirectBuddy(bob), false);
	}
	
	void testCommonBuddies(Tester t) {
		initData();
		
		t.checkExpect(ann.countCommonBuddies(bob), 0);
		t.checkExpect(hank.countCommonBuddies(bob), 0);
		t.checkExpect(bob.countCommonBuddies(fay), 2);
	}
	
	void testExtendedBuddies(Tester t) {
		initData();
		
		t.checkExpect(ann.hasExtendedBuddy(bob), true);
		t.checkExpect(ann.hasExtendedBuddy(hank), true);
		t.checkExpect(ann.hasExtendedBuddy(jan), false);
		t.checkExpect(ann.hasExtendedBuddy(gaby), true);
		t.checkExpect(cole.hasExtendedBuddy(dan), true);
		t.checkExpect(cole.hasExtendedBuddy(bob), false);
		t.checkExpect(dan.hasExtendedBuddy(cole), true);
		t.checkExpect(dan.hasExtendedBuddy(hank), false);
	}
	
	void testCountInvited(Tester t) {
		initData();
		t.checkExpect(ann.partyCount(), 8);
		initData();
		t.checkExpect(bob.partyCount(), 8);
		initData();
		t.checkExpect(hank.partyCount(), 1);
		initData();
		t.checkExpect(jan.partyCount(), 3);
		initData();
		t.checkExpect(kim.partyCount(), 3);
		initData();
		t.checkExpect(len.partyCount(), 3);
	}
}