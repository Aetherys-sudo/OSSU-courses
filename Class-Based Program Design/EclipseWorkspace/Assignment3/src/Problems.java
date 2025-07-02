import tester.*;

////////// Problem 18.1
class Schedule {
	Schedule() { }
}

class Route {
	Route() { }
}

class Stops {
	Stops() { }
}

abstract class Train {
	Schedule s;
	Route r;
	
	Train(Schedule s, Route r) {
		this.s = s;
		this.r = r;
	}	
}

class ExpressTrain extends Train {
	Stops st;
	String name;
	
	ExpressTrain(Schedule s, Route r, Stops st, String name) {
		super(s, r);
		this.st = st;
		this.name = name;
	}
}

class Place {
	Place() { }
}

abstract class Restaurant {
	String name;
	String price;
	Place place;
	
	Restaurant(String name, String price, Place place) {
		this.name = name;
		this.price = price;
		this.place = place;
	}
}

class ChineseRestaurant extends Restaurant {
	boolean usesMSG;
	
	ChineseRestaurant(String name, String price, Place place, boolean usesMSG) {
		super(name, price, place);
		this.usesMSG = usesMSG;
	}
}

abstract class Vehicle {
	int mileage;
	int price;
	
	Vehicle(int mileage, int price) {
		this.mileage = mileage;
		this.price = price;
	}
}

class Sedan extends Vehicle {
	
	Sedan(int mileage, int price) {
		super(mileage, price);
	}
}

////////// Problem 18.2
interface IZooAnimal {
	
}

abstract class Animal implements IZooAnimal {
	String name;
	int weight;
	
	Animal(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}
}

class Lion extends Animal {
	int meat;
	
	Lion(int meat, String name, int weight) {
		super(name, weight);
		this.meat = meat;
	}
}

class Snake extends Animal {
	int length;
	
	Snake(int length, String name, int weight) {
		super(name, weight);
		this.length = length;
	}
}

class Monkey extends Animal {
	String food;
	
	Monkey(String food, String name, int weight) {
		super(name, weight);
		this.food = food;
	}
}

////////// Problem 18.3
interface ITaxiVehicle {
	
}

abstract class IVehicle implements ITaxiVehicle {
	int idNum;
	int passengers;
	int pricePerMile;
	
	IVehicle(int idNum, int passengers, int pricePerMile) {
		this.idNum = idNum;
		this.passengers = passengers;
		this.pricePerMile = pricePerMile;
	}
}

class Cab extends IVehicle {
	
	Cab(int idNum, int passengers, int pricePerMile) {
		super(idNum, passengers, pricePerMile);
	}
}

class Limo extends IVehicle {
	int minRental;

	
	Limo(int minRental, int idNum, int passengers, int pricePerMile) {
		super(idNum, passengers, pricePerMile);
		this.minRental = minRental;
		
	}
}

class Van extends IVehicle {
	boolean access;

	
	Van(boolean access, int idNum, int passengers, int pricePerMile) {
		super(idNum, passengers, pricePerMile);
		this.access = access;
	}
}

/////////////// Problem 18.4
interface ISalesItem {}

abstract class Discount implements ISalesItem {
	int originalPrice;
	
	Discount(int originalPrice) {
		this.originalPrice = originalPrice;
	}
}
class DeepDiscount extends Discount {
	int originalPrice;

	DeepDiscount(int originalPrice) {
		super(originalPrice);
	}
}
class RegularDiscount extends Discount {
	int originalPrice;
	int discountPercentage;

	RegularDiscount(int originalPrice, int discountPercentage) {
		super(originalPrice);
		this.discountPercentage = discountPercentage;
	}
}


////////////// Problem 18.5
interface IVVehicle {
// compute the cost of refueling this vehicle,
// given the current price (cp) of fuel
	double cost(double cp);
}

abstract class AVehicle implements IVVehicle {
	double tank; // gallons
	
	AVehicle(double tank) {
		this.tank = tank;
	}

	public double cost(double cp) {
		return this.tank * cp;
	}
}

class Car extends AVehicle {
	
	Car(double tank) {
		super(tank);
	}
	
}

class Truck extends AVehicle {
	Truck(double tank) {
		super(tank);
	}
}

class Bus extends AVehicle {
	Bus(double tank) {
		super(tank);
	}
}

class ExamplesAVehicle {
	IVVehicle v1 = new Car(2.2);
	IVVehicle v2 = new Truck(1.4);
	IVVehicle v3 = new Bus(5.5);
	
	boolean testCost(Tester t) {
		return t.checkInexact(v1.cost(3), 6.6, 0.001) &&
				t.checkInexact(v2.cost(2), 2.8, 0.001) &&
				t.checkInexact(v3.cost(2), 11.0, 0.001);
				
	}
}

//////////////// Problem 19.4
interface ILin {
	int howMany(int i);
}

class Cin implements ILin {
	int head;
	ILin tail;
	
	Cin(int head, ILin tail) {
		this.head = head;
		this.tail = tail;
	}
	
	public int howMany(int i) {
		if (this.head == i) {
			return 1 + this.tail.howMany(i);
		} else {
			return this.tail.howMany(i);
		}
	}
}

class MTin implements ILin {
	MTin() {};
	
	public int howMany(int i) {
		return 0;
	}
}

class Set {
	
	ILin elements;
	
	Set(ILin elements) {
		this.elements = elements;
	}

	// add i to this set
	// unless it is already in there
	Set add(int i) {
		if (this.in(i)) {
			return this; }
		else {
			return new Set(new Cin(i,this.elements));
		}
	}
	
	boolean in(int i) {
		return this.elements.howMany(i) > 0;
	}
	
}

class Bag {
	ILin elements;
	Bag(ILin elements) {
		this.elements = elements;
	}
	// add i to this bag
	Bag add(int i) {
		return new Bag(new Cin(i,this.elements));
	}
	// is i a member of this bag?
	boolean in(int i) {
		return this.elements.howMany(i) > 0;
	}
	// how often is i in this bag?
	int howMany(int i) {
		return this.elements.howMany(i);
	}
}

class ExamplesCin {
	Set set1 = new Set(new Cin(1, new Cin(2, new Cin(3, new MTin()))));
	Set set2 = new Set(new Cin(1, new Cin(2, new Cin(1, new MTin()))));
	Bag bag1 = new Bag(new Cin(1, new Cin(2, new Cin(3, new Cin(1, new MTin())))));
	Bag bag2 = new Bag(new Cin(1, new Cin(2, new Cin(3, new MTin()))));
	
	boolean testSetBagAdd(Tester t) {
		return t.checkExpect(set1.add(4), new Cin(4, new Cin(1, new Cin(2, new Cin(3, new MTin()))))) &&
				t.checkExpect(set1.add(1), new Cin(1, new Cin(2, new Cin(3, new MTin())))) &&
				t.checkExpect(bag1.add(4), new Cin(4, new Cin(1, new Cin(2, new Cin(3, new Cin(1, new MTin())))))) &&
				t.checkExpect(bag2.add(1), new Cin(1, new Cin(1, new Cin(2, new Cin(3, new MTin())))));
				
	}
	
	boolean testSetBagIn(Tester t) {
		return t.checkExpect(set1.in(4), false) &&
				t.checkExpect(set1.in(1), true) &&
				t.checkExpect(bag1.in(4), false) &&
				t.checkExpect(bag2.in(1), true);
				
	}
	
	boolean testBagHowMany(Tester t) {
		return t.checkExpect(bag1.howMany(4), 0) &&
				t.checkExpect(bag1.howMany(1), 2);
	}
	
	
}




