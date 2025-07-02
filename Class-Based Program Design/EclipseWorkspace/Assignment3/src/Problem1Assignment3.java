// CS 2510, Assignment 3

import tester.*;


// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();
    
    ILoString sort();
    
    boolean isSorted();
    
    boolean isSortedHelp(String that);
    
    ILoString sortHelp(ILoString acc);
    
    ILoString insert(String el);
    
    ILoString interleave(ILoString otherList);
    
    ILoString interleaveHelp(ILoString otherList, int count);
    
    ILoString append(ILoString otherList);
    
    String toString();
    
    void print();
    
    ILoString merge(ILoString otherList);
    
    boolean isElementSmaller(String el);
    
    ILoString reverse();
    
    ILoString reverseHelp(ILoString acc);
    
    boolean isDoubledList();
    
    boolean isElementEqual(String el);
    
    boolean isPalindromeList();
    
    boolean isPalindromeListHelp(ILoString rev);
    
    ILoString getRest();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString() {}
    
    // combine all Strings in this list into one
    public String combine() {
       return "";
    }  
    
    public ILoString sort() {
    	return this;
    }
    
    public boolean isSorted() {
    	return true;
    }
    
    public boolean isSortedHelp(String that) {
    	return true;
    }
    
    public ILoString sortHelp(ILoString acc) {
    	return acc;
    }
    
    public ILoString insert(String el) {
    	return new ConsLoString(el, this);
    }
    
    public ILoString interleave(ILoString otherList) {
    	return otherList;
    }
    
    public ILoString interleaveHelp(ILoString otherList, int count) {
    	return otherList;
    }
    
    public ILoString append(ILoString otherList) {
    	return otherList;
    }
    
    public String toString() {
    	return "null)";
    }
    
    public void print() {
    	System.out.println(this.toString());
    }
    
    public ILoString merge(ILoString otherList) {
    	return otherList;
    }
    
    
    public boolean isElementSmaller(String el) {
    	return false;
    }
    
    public ILoString reverse() {
    	return this;
    }
    
    public ILoString reverseHelp(ILoString acc) {
    	return acc;
    }
    
    public boolean isDoubledList() {
    	return false;
    }
    
    public boolean isElementEqual(String el) {
    	return false;
    }

    public boolean isPalindromeList() {
    	return true;
    }
    
    public boolean isPalindromeListHelp(ILoString rev) {
    	return true;
    }
    
    public ILoString getRest() {
    	return new MtLoString();
    }
}

// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest){
        this.first = first;
        this.rest = rest;  
    }
    
    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString
     
     METHODS
     ... this.combine() ...     -- String
     
     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareTo(String) ...     -- int
     ... this.rest.combine() ...              -- String
     
     */
    
    // combine all Strings in this list into one
    public String combine() {
        return this.first.concat(this.rest.combine());
    }  
   
    public ILoString sort() {
    	if (this.isSorted()) {
    		return this;
    	} else {
    		return this.sortHelp(new MtLoString());
    	}
    }
    
    public ILoString sortHelp(ILoString acc) {
    	return this.rest.sortHelp(acc.insert(this.first));
    }
    
    public ILoString insert(String el) {
    	if (this.first.toLowerCase().compareTo(el) > 0) {
    		return new ConsLoString(el, this);
    	} else {
    		return new ConsLoString(this.first, this.rest.insert(el));
    	}
    }
    
    public boolean isSorted() {
    	return this.rest.isSortedHelp(this.first);
    }
    
    public boolean isSortedHelp(String that) {
    	if (that.toLowerCase().compareTo(this.first.toLowerCase()) > 0) {
    		return false;
    	} else {
    		return this.rest.isSortedHelp(this.first);
    	}
    }
    
    public ILoString interleave(ILoString otherList) {
    	return this.interleaveHelp(otherList, 0);
    }
    

    
    public ILoString interleaveHelp(ILoString otherList, int count) {
    	if (count == 0) {
    		// add from the current list 
    		 return new ConsLoString(this.first, this.rest.interleaveHelp(otherList, 1));
    	} else {
    		// add from the other list
    		return otherList.interleaveHelp(this, 0);
    	}
    }
    

    public ILoString append(ILoString otherList) {
    	return new ConsLoString(this.first, this.rest.append(otherList));
    }
    
    
    public String toString() {
    	return "(" + this.first + ", " + this.rest.toString();
    }
    
    public void print() {
    	System.out.println(this.toString());
    }
    
    public boolean isElementSmaller(String el) {
    	return (this.first.toLowerCase().compareTo(el) < 0);
    }
    
    public ILoString merge(ILoString otherList) {
    	if (otherList.isElementSmaller(this.first)) {
    		return otherList.merge(this);
    	} else {
    		return new ConsLoString(this.first, this.rest.merge(otherList));
    	}
    	
    }
    
    public ILoString reverse() {
    	return reverseHelp(new MtLoString());
    }
    
    public ILoString reverseHelp(ILoString acc) {
    	return this.rest.reverseHelp(new ConsLoString(this.first, acc));
    }
    
    public boolean isElementEqual(String el) {
    	return (this.first.toLowerCase().compareTo(el) == 0);
    }
    
    public boolean isDoubledList() {
    	if (this.rest.isElementEqual(this.first)) {
    		return true;
    	} else {
    		return this.rest.isDoubledList();
    	}
    }
    
    public boolean isPalindromeList() {
    	return isPalindromeListHelp(this.reverse());
    }
    
    public ILoString getRest() {
    	return this.rest;
    }
    
    public boolean isPalindromeListHelp(ILoString rev) {
    	if (rev.isElementEqual(this.first)) {
    		return this.rest.isPalindromeListHelp(rev.getRest());
    	} else {
    		return false;
    	}
    }
}

// to represent examples for lists of strings
class ExamplesStrings{
    
    ILoString mary = new ConsLoString("Mary ",
                    new ConsLoString("had ",
                        new ConsLoString("a ",
                            new ConsLoString("little ",
                                new ConsLoString("lamb.", new MtLoString())))));
    
    ILoString mary2 = new ConsLoString("mary", new MtLoString());
    ILoString empty = new ConsLoString("", new MtLoString());
    
    String mary2_string = "mary";
    String empty_string = "";
    
    ILoString testSort1 = new ConsLoString("a",
            new ConsLoString("had",
                    new ConsLoString("lamb.",
                        new ConsLoString("little",
                            new ConsLoString("Mary", new MtLoString())))));
    
    ILoString testSort2 = new ConsLoString("a",
            new ConsLoString("b",
                    new ConsLoString("c",
                        new ConsLoString("d",
                            new ConsLoString("e", new MtLoString())))));
    
    ILoString testSort3 = new ConsLoString("a",
            new ConsLoString("e",
                    new ConsLoString("i",
                        new ConsLoString("o",
                            new ConsLoString("u", new MtLoString())))));
    
    ILoString testResInterleave = new ConsLoString("a",
			new ConsLoString("a",
					new ConsLoString("had",
							new ConsLoString("b",
									new ConsLoString("lamb.",
											new ConsLoString("c",
													new ConsLoString("little",
															new ConsLoString("d",
																	new ConsLoString("Mary",
																				new ConsLoString("e", new MtLoString()))))))))));
    
    ILoString testDoubled = new ConsLoString("a",
					new ConsLoString("had",
							new ConsLoString("b",
									new ConsLoString("lamb.",
											new ConsLoString("c",
													new ConsLoString("little",
															new ConsLoString("d",
																	new ConsLoString("Mary",
																				new ConsLoString("e", 
																						new ConsLoString("e", new MtLoString()))))))))));
    
    ILoString testDoubled2 = new ConsLoString("a",
			new ConsLoString("had",
					new ConsLoString("b",
							new ConsLoString("lamb.",
									new ConsLoString("c",
											new ConsLoString("e", 
													new ConsLoString("e",
											new ConsLoString("little",
													new ConsLoString("d",
															new ConsLoString("d",
															new ConsLoString("Mary", new MtLoString())))))))))));
    
	ILoString testResInterleave2 = new ConsLoString("a",
			new ConsLoString("a",
					new ConsLoString("b",
						new ConsLoString("c",
							new ConsLoString("d",
									new ConsLoString("e",
									new ConsLoString("had",		
										new ConsLoString("lamb.",
									new ConsLoString("little",
											new ConsLoString("Mary", new MtLoString()))))))))));
	
	ILoString testPalindrome = new ConsLoString("a",
			new ConsLoString("a",
					new ConsLoString("b",
						new ConsLoString("c",
							new ConsLoString("d",
									new ConsLoString("c",
									new ConsLoString("b",		
										new ConsLoString("a",
									new ConsLoString("a", new MtLoString())))))))));
	
	ILoString testPalindrome2 = new ConsLoString("a",
			new ConsLoString("a",
					new ConsLoString("b",
						new ConsLoString("c",
							new ConsLoString("d",
									new ConsLoString("d",
									new ConsLoString("c",
									new ConsLoString("b",		
									new ConsLoString("a",
									new ConsLoString("a", new MtLoString()))))))))));
        
    // test the method combine for the lists of Strings
    boolean testCombine(Tester t){
        return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.");
    }
    
    
    boolean testIsSorted(Tester t){
        return t.checkExpect(this.mary.isSorted(), false) &&
        		t.checkExpect(this.mary2.isSorted(), true) &&
        		t.checkExpect(this.empty.isSorted(), true) &&
        		t.checkExpect(this.testSort1.isSorted(), true) &&
        		t.checkExpect(this.testSort2.isSorted(), true);
    }
    
    boolean testInsert(Tester t){
        return t.checkExpect(this.mary.insert("zzz"), new ConsLoString("Mary ",
									                    new ConsLoString("had ",
									                        new ConsLoString("a ",
									                            new ConsLoString("little ",
									                                new ConsLoString("lamb.", 
									                                		new ConsLoString("zzz", new MtLoString()))))))) &&
        		t.checkExpect(this.mary2.insert("zzz"), new ConsLoString("mary",
															new ConsLoString("zzz",
																	new MtLoString()))) &&
        		t.checkExpect(this.empty.insert("zzz"), new ConsLoString("",
        													new ConsLoString("zzz",
        															new MtLoString()))) &&
        		t.checkExpect(this.testSort1.insert("a"),  new ConsLoString("a", 
        														new ConsLoString("a",
										        	            new ConsLoString("had",
										        	                    new ConsLoString("lamb.",
										        	                        new ConsLoString("little",
										        	                            new ConsLoString("Mary", new MtLoString()))))))) &&
        		t.checkExpect(this.testSort2.insert("c"), new ConsLoString("a",
									        	            new ConsLoString("b",
									        	                    new ConsLoString("c",
									        	                    	new ConsLoString("c",
											        	                        new ConsLoString("d",
											        	                            new ConsLoString("e", new MtLoString())))))));
    }
    
    
    
    boolean testSort(Tester t) {
        return 
        		t.checkExpect(this.mary.sort(), new ConsLoString("a ",
								                    new ConsLoString("had ",
								                        new ConsLoString("lamb.",
								                            new ConsLoString("little ",
								                                new ConsLoString("Mary ", new MtLoString())))))) &&
        		t.checkExpect(this.mary2.sort(), mary2) &&
        		t.checkExpect(this.empty.sort(), empty) &&
        		t.checkExpect(this.testSort1.sort(), testSort1) &&
        		t.checkExpect(this.testSort2.sort(), testSort2);
    }
    
    boolean testInterleave(Tester t) {
        return 
        		t.checkExpect(this.mary.interleave(mary2), new ConsLoString("Mary ",
        				new ConsLoString("mary", 
                        	new ConsLoString("had ",
                                new ConsLoString("a ",
                                    new ConsLoString("little ",
                                        new ConsLoString("lamb.", new MtLoString()))))))) &&
        		t.checkExpect(this.mary2.interleave(mary2), new ConsLoString("mary", 
										        				new ConsLoString("mary", 
										        					new MtLoString()))) &&
        		t.checkExpect(this.empty.interleave(testSort2), new ConsLoString("", 
        															new ConsLoString("a",
											        	            new ConsLoString("b",
											        	                    new ConsLoString("c",
											        	                        new ConsLoString("d",
											        	                            new ConsLoString("e", new MtLoString()))))))) &&
        		t.checkExpect(this.testSort1.interleave(testSort2), testResInterleave);
    }
    
    boolean testMerge(Tester t) {
        return t.checkExpect(this.mary2.merge(mary2), new ConsLoString("mary", 
										        				new ConsLoString("mary", 
										        					new MtLoString()))) &&
        		t.checkExpect(this.empty.merge(testSort2), new ConsLoString("",
											        	            new ConsLoString("a",
											        	                    new ConsLoString("b",
											        	                        new ConsLoString("c",
											        	                            new ConsLoString("d", 
											        	                            		new ConsLoString("e", new MtLoString()))))))) &&
        		t.checkExpect(this.testSort1.merge(testSort2), testResInterleave2) &&
        		t.checkExpect(this.testSort1.merge(testSort3), new ConsLoString("a",
            			new ConsLoString("a",
            					new ConsLoString("e",
            							new ConsLoString("had",
            									new ConsLoString("i",
            											new ConsLoString("lamb.",
            													new ConsLoString("little",
            																	new ConsLoString("Mary",
            																			new ConsLoString("o",
            																				new ConsLoString("u", new MtLoString())))))))))));
        		
    }
    
    boolean testReverse(Tester t) {
        return t.checkExpect(this.mary2.reverse(),  new ConsLoString("mary", 
							        					new MtLoString())) &&
        		t.checkExpect(this.testSort1.reverse(), new ConsLoString("Mary",
        	            new ConsLoString("little",
        	                    new ConsLoString("lamb.",
        	                        new ConsLoString("had",
        	                            new ConsLoString("a", new MtLoString())))))) &&
        		t.checkExpect(this.testResInterleave.reverse(), new ConsLoString("e",
        				new ConsLoString("Mary",
        						new ConsLoString("d",
        								new ConsLoString("little",
        										new ConsLoString("c",
        												new ConsLoString("lamb.",
        														new ConsLoString("b",
        																new ConsLoString("had",
        																		new ConsLoString("a",
        																					new ConsLoString("a", new MtLoString())))))))))));
        		
    }
    
    boolean testDoubled(Tester t) {
        return t.checkExpect(this.mary.isDoubledList(), false) &&
        		t.checkExpect(this.mary2.isDoubledList(), false) &&
        		t.checkExpect(this.empty.isDoubledList(), false) &&
        		t.checkExpect(this.testResInterleave.isDoubledList(), true) &&
        		t.checkExpect(this.testDoubled.isDoubledList(), true) &&
        		t.checkExpect(this.testDoubled2.isDoubledList(), true);
    }
    
    boolean testPalindrome(Tester t) {
        return 
        		t.checkExpect(this.mary.isPalindromeList(), false) &&
        		t.checkExpect(this.mary2.isPalindromeList(), true) &&
        		t.checkExpect(this.empty.isPalindromeList(), true) &&
        		t.checkExpect(this.testResInterleave.isPalindromeList(), false) &&
        		t.checkExpect(this.testPalindrome.isPalindromeList(), true) &&
        		t.checkExpect(this.testPalindrome2.isPalindromeList(), true);
    }
    
    
    
    
   

    
}