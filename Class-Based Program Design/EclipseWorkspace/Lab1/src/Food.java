interface MenuItem {}

class Soup implements MenuItem {
	String name;
	Integer price;
	Boolean vegetarian;
	
	Soup(String name, Integer price, Boolean vegetarian) {
		this.name = name;
		this.price = price;
		this.vegetarian = vegetarian;
	}
}

class Salad implements MenuItem {
	String name;
	Integer price;
	Boolean vegetarian;
	String dressing;
	
	Salad(String name, Integer price, Boolean vegetarian, String dressing) {
		this.name = name;
		this.price = price;
		this.vegetarian = vegetarian;
		this.dressing = dressing;
	}
}

class Sandwich implements MenuItem {
	String name;
	Integer price;
	String bread;
	String filling1;
	String filling2;
	
	Sandwich(String name, Integer price, String bread, String filling1, String filling2) {
		this.name = name;
		this.price = price;
		this.bread = bread;
		this.filling1 = filling1;
		this.filling2 = filling2;
	}
}

class ExamplesMenuItem {
	Soup soup1 = new Soup("Crema", 80, true);
	Soup soup2 = new Soup("Radauteana", 110, false);
	
	Salad salad1 = new Salad("Caesar", 20, false, "Ranch");
	Salad salad2 = new Salad("Simple", 10, true, "Olive Oil");
	
	Sandwich sand1 = new Sandwich("Sub", 20, "flat", "olives", "chives");
	Sandwich sand2 = new Sandwich("Wrap", 20, "bred", "chicken", "onion");

	
}

