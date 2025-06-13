class Automobile {
	String model;
	int price;
	double mileage;
	boolean used;
	
	Automobile(String model, int price, double mileage, boolean used) {
		this.model = model;
		this.price = price;
		this.mileage = mileage;
		this.used = used;
	}
}

/////////////////////////////////////////////////////

class Address {
	String city;
	String street;
	int addNum;
	
	Address(int addNum, String street, String city) {
		this.addNum = addNum;
		this.street = street;
		this.city = city;
	}
}

class House {
	String type;
	int roomNumber;
	Address address;
	double price;
	
	House(String type, int roomNumber, Address address, double price) {
		this.type = type;
		this.roomNumber = roomNumber;
		this.address = address;
		this.price = price;
	}
}

////////////////////////////////////////////////////////////

class Date {
	int year;
	String month;
	int day;
	
	Date(String month, int day, int year) {
		this.month = month;
		this.day = day;
		this.year = year;
	}
}

interface Account {
	
}

class CheckingAccount implements Account {
	int id;
	String customerName;
	double currentBalance;
	double minimumBalance;
	
	CheckingAccount(int id, String customerName, double currentBalance, double minimumBalance) {
		this.id = id;
		this.customerName = customerName;
		this.currentBalance = currentBalance;
		this.minimumBalance = minimumBalance;
	}
}

class SavingsAccount implements Account {
	int id;
	String customerName;
	double currentBalance;
	double interestRate;
	
	SavingsAccount(int id, String customerName, double currentBalance, double interestRate) {
		this.id = id;
		this.customerName = customerName;
		this.currentBalance = currentBalance;
		this.interestRate = interestRate;
	}
}

class CertificateOfDeposit implements Account {
	int id;
	String customerName;
	double currentBalance;
	double interestRate;
	Date maturityDate;
	
	CertificateOfDeposit(int id, String customerName, double currentBalance, double interestRate, Date maturityDate) {
		this.id = id;
		this.customerName = customerName;
		this.currentBalance = currentBalance;
		this.interestRate = interestRate;
		this.maturityDate = maturityDate;
	}
}

//////////////////////////////////////////

class Image {
	int width; // in pixels
	int height; // in pixels
	String source;
	Image(int width, int height, String source) {
		this.width = width;
		this.height = height;
		this.source = source;
	}
	
	void sizeString() {
		if (this.width * this.height <= 10000) {
			System.out.println("small");
		}
		
		if ((this.width * this.height > 10000) && (this.width * this.height <= 1000000)) {
			System.out.println("medium");
		}
		
		if (this.width * this.height > 1000000) {
			System.out.println("large");
		}
	}
}

///////////////////////////////////////
class TemperatureRange {
	int high;
	int low;
	
	TemperatureRange(int low, int high) {
		this.low = low;
		this.high = high;
	}
	
	boolean isBetween(TemperatureRange that) {
		return ((this.low > that.low) && (this.low < that.high))
		&& ((this.high > that.low) && (this.high < that.high));
	}
}

class WeatherRecord {
	Date d;
	TemperatureRange today;
	TemperatureRange normal;
	TemperatureRange record;
	
	WeatherRecord(Date d, TemperatureRange today, TemperatureRange normal, TemperatureRange record) {
		this.d = d;
		this.today = today;
		this.normal = normal;
		this.record = record;
	}
	
	boolean withinRange() {
		return this.today.isBetween(normal);
	}
	
	
}

//////////////////////////////////////////

interface GroceryItems {
	int unitPrice();
	
	boolean lowerUnitPrice(int amount);
	
	boolean cheaperThan(GroceryItems that);
	
}

class IceCream implements GroceryItems{
	String brandName;
	int weight;
	int price;
	String flavor;
	
	IceCream(String brandName, int weight, int price, String flavor) {
		this.brandName = brandName;
		this.weight = weight;
		this.price = price;
		this.flavor = flavor;
	}
	
	int compare(int x, int y) {
		if (x > y) {
			return 1;
		} else {
			if (x == y) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	public int unitPrice() {
		return this.price / this.weight;
	}
	
	public boolean lowerUnitPrice(int amount) {
		return this.unitPrice() < amount;
	}
	
	public boolean cheaperThan(GroceryItems that) {
		if (compare(this.unitPrice(), that.unitPrice()) >= 0) {
			return false;
		} else {
			return true;
		}
	}
}

class Coffee implements GroceryItems{
	String brandName;
	int weight;
	int price;
	boolean caffeine;
	
	Coffee(String brandName, int weight, int price, boolean caffeine) {
		this.brandName = brandName;
		this.weight = weight;
		this.price = price;
		this.caffeine = caffeine;
	}
	
	int compare(int x, int y) {
		if (x > y) {
			return 1;
		} else {
			if (x == y) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	public int unitPrice() {
		return this.price / this.weight;
	}
	
	public boolean lowerUnitPrice(int amount) {
		return this.unitPrice() < amount;
	}
	
	public boolean cheaperThan(GroceryItems that) {
		if (compare(this.unitPrice(), that.unitPrice()) >= 0) {
			return false;
		} else {
			return true;
		}
	}
}

class Juice implements GroceryItems{
	String brandName;
	int weight;
	int price;
	String flavor;
	String packaging;
	
	Juice(String brandName, int weight, int price, String flavor, String packaging) {
		this.brandName = brandName;
		this.weight = weight;
		this.price = price;
		this.flavor = flavor;
		this.packaging = packaging;
	}
	
	int compare(int x, int y) {
		if (x > y) {
			return 1;
		} else {
			if (x == y) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	
	public int unitPrice() {
		return this.price / this.weight;
	}
	
	public boolean lowerUnitPrice(int amount) {
		return this.unitPrice() < amount;
	}
	
	public boolean cheaperThan(GroceryItems that) {
		if (compare(this.unitPrice(), that.unitPrice()) >= 0) {
			return false;
		} else {
			return true;
		}
	}
}

class ExamplesProblems {
	Automobile a1 = new Automobile("Honda", 100, 10, false);
	Automobile a2 = new Automobile("Mercedes", 20545, 5.7, true);
	
	//////////////////////////

	Address add1 = new Address(9, "Grove St.", "Home");
	Address add2 = new Address(15, "Great St.", "Chihuahua");
	
	House h1 = new House("Flat", 3, add1, 354000);
	House h2 = new House("Villa", 15, add2, 1354000);
	
	/////////////////////////
	
	Date d1 = new Date("June", 1, 2005);
	
	CheckingAccount chk1 = new CheckingAccount(1729, "Earl Gray", 1250.0, 500.0);
	SavingsAccount sv1 = new SavingsAccount(2992, "Annie Proulx", 800.0, 3.5);
	CertificateOfDeposit cod1 = new CertificateOfDeposit(4104, "Ima Flatt", 10123.0, 4.0, d1);
	
	//////////////////////////////////
	Image img1 = new Image(1000, 1000, "/src");
	
	
}

