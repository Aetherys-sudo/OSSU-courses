
// represents an empty list of Person's buddies
class MTLoBuddy implements ILoBuddy {
    MTLoBuddy() {}

    public boolean isInList(Person that) {
    	return false;
    }
    
    public int countCommon(ILoBuddy other) {
    	return 0;
    }
    
    public boolean searchExtendedList(Person that) {
    	return false;
    }
    
    public int countTotalBuddies() {
    	return 0;
    }
}
