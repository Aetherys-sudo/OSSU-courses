import tester.*;

interface MoT {
	boolean isMoreFuelEfficientThan(int mpg);
}

class Person {
	String name;
	MoT mot;
	
	Person(String name, MoT mot) {
		this.name = name;
		this.mot = mot;
	}
	
	boolean motMeetsFuelEfficiency(int mpg) {
		  return this.mot.isMoreFuelEfficientThan(mpg);
	}
	
}

class Bicycle implements MoT {
	String brand;
	
	Bicycle(String brand) {
		this.brand = brand;
	}
	
	public boolean isMoreFuelEfficientThan(int mpg) {
		  return true;
	}
}

class Car implements MoT {
	String make;
	int mpg;
	
	Car(String make, int mpg) {
		this.make = make;
		this.mpg = mpg;
	}
	
	public boolean isMoreFuelEfficientThan(int mpg) {
		  return this.mpg >= mpg;
	}
}

class ExamplesVehicles {
	MoT diamondback = new Bicycle("Diamondback");
	MoT toyota = new Car("Toyota", 30);
	MoT lamborghini = new Car("Lamborghini", 17);
		
	Person bob = new Person("Bob", diamondback);
	Person ben = new Person("Ben", toyota);
	Person becca = new Person("Becca", lamborghini);
	
	boolean testIsMoreFuelEfficient(Tester t) {
	    return
	    t.checkExpect(this.bob.motMeetsFuelEfficiency(15), true) &&
	    t.checkExpect(this.ben.motMeetsFuelEfficiency(15), true) &&
	    t.checkExpect(this.becca.motMeetsFuelEfficiency(15), true) && 
	    t.checkExpect(this.diamondback.isMoreFuelEfficientThan(15), true) &&
	    t.checkExpect(this.toyota.isMoreFuelEfficientThan(15), true) &&
	    t.checkExpect(this.lamborghini.isMoreFuelEfficientThan(15), true) &&
	    t.checkExpect(this.bob.motMeetsFuelEfficiency(25), true) &&
	    t.checkExpect(this.ben.motMeetsFuelEfficiency(25), true) &&
	    t.checkExpect(this.becca.motMeetsFuelEfficiency(25), false) && 
	    t.checkExpect(this.diamondback.isMoreFuelEfficientThan(25), true) &&
	    t.checkExpect(this.toyota.isMoreFuelEfficientThan(25), true) &&
	    t.checkExpect(this.lamborghini.isMoreFuelEfficientThan(25), false);
    } 
}

///////////////////////////

// to represent a pet owner
class Person2 {
    String name;
    IPet pet;
    int age;
 
    Person2(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }
    
    boolean isOlder(Person2 other) {
    	return other.isBiggerAge(this.age);
    }
    
    private boolean isBiggerAge(int age) {
    	return this.age > age;
    }
    
    boolean sameNamePet(String name) {
    	return this.pet.isSameName(name);
    }
    
    String perished() {
    	return this.pet.isNoPet();
    }
}

// to represent a pet
interface IPet { 
	boolean isSameName(String name);
	boolean isNoPet();
}
 
// to represent a pet cat
class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;
 
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    public boolean isSameName(String name) {
    	return (this.name == name);
    }
    
    public boolean isNoPet() {
    	return false;
    }
}
 
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
 
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    
    public boolean isSameName(String name) {
    	return (this.name == name);
    }
    
    public boolean isNoPet() {
    	return false;
    }
}

class NoPet implements IPet {
	
	NoPet() { };
	
    public boolean isSameName(String name) {
    	return false;
    }
    
    public boolean isNoPet() {
    	return true;
    }
}

class ExamplesPets {
	
	IPet onix = new Cat("Onix", "Proasta", false);
	IPet sekiro = new Dog("Sekiro", "Bou", true);
	IPet noPet = new NoPet();
	
	Person2 bob = new Person2("Bob", sekiro, 18);
	Person2 luca = new Person2("Luca", onix, 24);
	Person2 timi = new Person2("Timi", onix, 25);
	Person2 nancy = new Person2("Nancy", sekiro, 33);
	Person2 ohno = new Person2("OhNo", noPet, 22);
	
	boolean testIsOlderThan(Tester t) {
		return t.checkExpect(bob.isOlder(luca), false) &&
		t.checkExpect(luca.isOlder(bob), true) && 
		t.checkExpect(luca.sameNamePet("Onix"), true) && 
		t.checkExpect(luca.perished(), false) &&
		t.checkExpect(ohno.perished(), true);
	}
}











