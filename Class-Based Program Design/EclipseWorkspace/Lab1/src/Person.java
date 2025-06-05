class Address {
	String city;
	String state;
	
	Address(String city, String state) {
		this.city = city;
		this.state = state;
	}
}

class Person {
	String name;
	Integer age;
	String gender;
	Address address;
	
	Person(String name, Integer age, String gender, Address address) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.address = address;
	}
	
}

class ExamplesPerson {
	
	Address add1 = new Address("Boston", "MA");
	Address add2 =  new Address("Warwick", "RI");
	Address add3 =  new Address("Nashua", "NH");
	
	Person tim = new Person("Tim", 23, "Male", add1);
	Person kate = new Person("Kate", 22, "Female", add2);
	Person rebecca = new Person("Rebecca", 31, "Female", add3);
	Person luca = new Person("Luca", 24, "Male", add1);
	Person bog = new Person("Bog", 24, "Male", add2);
	
	
}