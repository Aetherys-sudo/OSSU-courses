import tester.*;
interface LoIceCream {
	
}

class Flavor {
	String flavor;
	
	Flavor(String flavor) {
		this.flavor = flavor;
	}
}

class MtIceCream implements LoIceCream {
	
	
	MtIceCream() {
		// nothing to construct
		
	}
}

class ConsIceCream implements LoIceCream {
	Flavor first;
	LoIceCream next;
	
	ConsIceCream(Flavor first, LoIceCream next) {
		this.first = first;
		this.next = next;
	}
}

class ExamplesIceCream {
	LoIceCream cone = new MtIceCream();
	
	Flavor flavor1 = new Flavor("mint chip");
	Flavor flavor2 = new Flavor("chocolate");
	Flavor flavor3 = new Flavor("black raspberry");
	Flavor flavor4 = new Flavor("caramel swirl");
	
	
	Flavor flavor6 = new Flavor("vanilla");
	Flavor flavor7 = new Flavor("strawberry");
	
	LoIceCream ice1 = new ConsIceCream(flavor4,
							new ConsIceCream(flavor3, 
								new ConsIceCream(flavor2, 
									new ConsIceCream(flavor1,
										cone))));
	
	LoIceCream ice2 = new ConsIceCream(flavor7,
						new ConsIceCream(flavor6, 
							new ConsIceCream(flavor2, 
									cone)));

    
}