interface Accomodation {
	
}

interface Transportation {
	
}

class Hut implements Accomodation {
	int capacity;
	int population;
	
	Hut(int capacity, int population) {
		this.capacity = capacity;
		this.population = population;
	}
}

class Inn implements Accomodation {
	String name;
	int capacity;
	int population;
	int stalls;
	
	Inn(String name, int capacity, int population, int stalls) {
		this.name = name;
		this.capacity = capacity;
		this.population = population;
		this.stalls = stalls;
	}
}

class Castle implements Accomodation {
	String name;
	String familyName;
	int population;
	int carriages;
	
	Castle(String name, String familyName, int population, int carriages) {
		this.name = name;
		this.familyName = familyName;
		this.population = population;
		this.carriages = carriages;
	}
}

class Horse implements Transportation {
	Accomodation from;
	Accomodation to;
	String name;
	String color;
	
	Horse(Accomodation from, Accomodation to, String name, String color) {
		this.from = from;
		this.to = to;
		this.name = name;
		this.color = color;
	}
}

class Carriage implements Transportation {
	Accomodation from;
	Accomodation to;
	int tonnage;
	
	Carriage(Accomodation from, Accomodation to, int tonnage) {
		this.from = from;
		this.to = to;
		this.tonnage = tonnage;
	}
	
}

class ExamplesTravel {
	Accomodation hovel = new Hut(5,1);
	Accomodation winterfell = new Castle("Winterfell", "Stark", 500, 6);
	Accomodation crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);
	
	Transportation horse1 = new Horse(winterfell, crossroads, "Brego", "black");
	Transportation carriage1 = new Carriage(crossroads, winterfell, 12);
}