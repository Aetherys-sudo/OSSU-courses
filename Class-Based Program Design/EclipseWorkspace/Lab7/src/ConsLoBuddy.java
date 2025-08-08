// represents a list of Person's buddies
class ConsLoBuddy implements ILoBuddy {

    Person first;
    ILoBuddy rest;

    ConsLoBuddy(Person first, ILoBuddy rest) {
        this.first = first;
        this.rest = rest;
    }
    
    public boolean isInList(Person that) {
    	return this.first == that || this.rest.isInList(that);
    }

    public int countCommon(ILoBuddy other) {
    	if (other.isInList(this.first)) {
    		return 1 + this.rest.countCommon(other);
    	} else {
    		return this.rest.countCommon(other);
    	}
    }
    
    public boolean searchExtendedList(Person that) {
    	if (this.first.hasDirectBuddy(that)) {
    		return true;
    	} else {
    		return this.rest.searchExtendedList(that);
    	}
    }
    
    public int countTotalBuddies() {
    	return this.first.partyCount() + this.rest.countTotalBuddies();
    }
}
