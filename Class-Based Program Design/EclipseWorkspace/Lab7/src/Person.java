
// represents a Person with a user name and a list of buddies
class Person {

    String username;
    ILoBuddy buddies;
    boolean visited;

    Person(String username) {
        this.username = username;
        this.buddies = new MTLoBuddy();
        visited = false;
    }

    // returns the number of people who will show up at the party 
    // given by this person
    int partyCount(){
    	if (this.visited == true) {
    		return 0;
    	} else {
    		this.visited = true;
            return 1 + this.buddies.countTotalBuddies();
    	}
    	
    }

    // returns the number of people that are direct buddies 
    // of both this and that person
    int countCommonBuddies(Person that) {
        return this.buddies.countCommon(that.buddies);
    }

    // will the given person be invited to a party 
    // organized by this person?
    boolean hasExtendedBuddy(Person that) {
    	return (this.hasDirectBuddy(that)) || this.buddies.searchExtendedList(that);
    }
    
	 // EFFECT:
	 // Change this person's buddy list so that it includes the given person
    void addBuddy(Person buddy) {
    	this.buddies = new ConsLoBuddy(buddy, this.buddies);
    }
    
    // returns true if this Person has that as a direct buddy
    boolean hasDirectBuddy(Person that) {
    	return this.buddies.isInList(that);
    }

}
