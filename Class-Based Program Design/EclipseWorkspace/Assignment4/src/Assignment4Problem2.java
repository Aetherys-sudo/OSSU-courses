import tester.*;

interface IEntertainment {
    //compute the total price of this Entertainment
    double totalPrice();
    //computes the minutes of entertainment of this IEntertainment
    int duration();
    //produce a String that shows the name and price of this IEntertainment
    String format();
    //is this IEntertainment the same as that one?
    boolean sameEntertainment(IEntertainment that);
    
    public boolean sameMagazine(Magazine that);
    
    public boolean sameTVSeries(TVSeries that);
    
    public boolean samePodcast(Podcast that);
}

abstract class AEntertainment implements IEntertainment {
	String name;
	double price;
	int installments;
	
	AEntertainment(String name, double price, int installments) {
		this.name = name;
		this.price = price;
		this.installments = installments;
	}
	
    public double totalPrice() {
        return this.price * this.installments;
    }
    
    //computes the minutes of entertainment of this Podcast
    public int duration() {
        return this.installments * 50;
    }
    
    public boolean sameMagazine(Magazine that) {
        return false;
    }
    
    public boolean sameTVSeries(TVSeries that) {
        return false;
    }
    
    public boolean samePodcast(Podcast that) {
        return false;
    }
    
    //produce a String that shows the name and price of this Magazine
    public String format() {
        return this.name + ", " + this.price + "$.";
    }
}

class Magazine extends AEntertainment {
    String genre;
    int pages;
    
    Magazine(String name, double price, String genre, int pages, int installments) {
        super(name, price, installments);
        this.genre = genre;
        this.pages = pages;
    }
    
    //computes the price of a yearly subscription to this Magazine

    
    //computes the minutes of entertainment of this Magazine, (includes all installments)
    public int duration() {
        return (this.pages * 5) * this.installments;
    }
    
    //is this Magazine the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
        return that.sameMagazine(this);
    }
    
    public boolean sameMagazine(Magazine that) {
        return (this.name == that.name) &&
        		(this.price == that.price) &&
        		(this.genre == that.genre) &&
        		(this.pages == that.pages) &&
        		(this.installments == that.installments);
    }
    
    

}

class TVSeries extends AEntertainment {
    String corporation;
    
    TVSeries(String name, double price, int installments, String corporation) {
    	super(name, price, installments);
        this.corporation = corporation;
    }
    
    
    
    //is this TVSeries the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
    	return that.sameTVSeries(this);
    }
    
    public boolean sameTVSeries(TVSeries that) {
        return (this.name == that.name) &&
        		(this.price == that.price) &&
        		(this.installments == that.installments) &&
        		(this.corporation == that.corporation);
    }
    
}

class Podcast extends AEntertainment {
    
    Podcast(String name, double price, int installments) {
    	super(name, price, installments);
    }
    
    
    //is this Podcast the same as that IEntertainment?
    public boolean sameEntertainment(IEntertainment that) {
    	return that.samePodcast(this);
    }
    
    
    public boolean samePodcast(Podcast that) {
        return (this.name == that.name) &&
        		(this.price == that.price) &&
        		(this.installments == that.installments);
    }
    

}

class ExamplesEntertainment {
    IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
    IEntertainment theGuardian = new Magazine("The Guardian", 5.50, "News", 24, 33);
    IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
    IEntertainment witcher = new TVSeries("The Witcher", 0.0, 3, "Netflix");
    IEntertainment serial = new Podcast("Serial", 0.0, 8);
    IEntertainment paralell = new Podcast("Paralell", 100.0, 10);
    
    //testing total price method
    boolean testTotalPrice(Tester t) {
        return t.checkInexact(this.rollingStone.totalPrice(), 2.55*12, .0001) 
        && t.checkInexact(this.theGuardian.totalPrice(), 5.50*33, .0001) 
        && t.checkInexact(this.houseOfCards.totalPrice(), 5.25*13, .0001)
        && t.checkInexact(this.witcher.totalPrice(), 0.0*13, .0001) 
        && t.checkInexact(this.serial.totalPrice(), 0.0*8, .0001)
        && t.checkInexact(this.paralell.totalPrice(), 100.0*10, .0001);
    }
    
    boolean testDuration(Tester t) {
        return t.checkExpect(this.rollingStone.duration(), 60 * 5 * 12) 
        && t.checkExpect(this.theGuardian.duration(), 24 * 5 * 33) 
        && t.checkExpect(this.houseOfCards.duration(), 13 * 50)
        && t.checkExpect(this.witcher.duration(), 3 * 50) 
        && t.checkExpect(this.serial.duration(), 8 * 50)
        && t.checkExpect(this.paralell.duration(), 10 * 50);
    }
    
    boolean testFormat(Tester t) {
        return t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55$.") 
        && t.checkExpect(this.theGuardian.format(), "The Guardian, 5.5$.") 
        && t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25$.")
        && t.checkExpect(this.witcher.format(), "The Witcher, 0.0$.") 
        && t.checkExpect(this.serial.format(), "Serial, 0.0$.")
        && t.checkExpect(this.paralell.format(), "Paralell, 100.0$.");
    }
    
    boolean testSameMedia(Tester t) {
        return t.checkExpect(this.rollingStone.sameEntertainment(rollingStone), true) 
        && t.checkExpect(this.theGuardian.sameEntertainment(rollingStone), false) 
        && t.checkExpect(this.houseOfCards.sameEntertainment(houseOfCards), true)
        && t.checkExpect(this.witcher.sameEntertainment(houseOfCards), false) 
        && t.checkExpect(this.serial.sameEntertainment(paralell), false)
        && t.checkExpect(this.paralell.sameEntertainment(paralell), true);
    }
    
}