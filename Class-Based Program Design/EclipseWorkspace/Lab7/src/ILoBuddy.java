
// represents a list of Person's buddies
interface ILoBuddy {
	boolean isInList(Person that);
	
	int countCommon(ILoBuddy other);
	
	boolean searchExtendedList(Person that);
	
	int countTotalBuddies();
}
