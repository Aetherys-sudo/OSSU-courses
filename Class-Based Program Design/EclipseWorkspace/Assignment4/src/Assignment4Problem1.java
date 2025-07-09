import tester.*;

class Utils {
	Utils() {};
	
	boolean equals(double num1, double num2) {
		return (Math.abs(num1 - num2) < 0.001);
	}
	
	double checkProperRecipe(double val1, double val2, String msg) {
		if (new Utils().equals(val1, val2)) {
			return val1;
		} else {
			throw new IllegalArgumentException(msg);
		}
	}
}

class BagelRecipe {
	double flour;
	double water;
	double yeast;
	double malt;
	double salt;
	

	BagelRecipe(double flour, double water, double yeast, double malt, double salt) {

		this.flour = new Utils().checkProperRecipe(flour, water, "The quantities of flour and water (in ounces) are not the same.");
		this.water = this.flour;
		
		this.yeast = new Utils().checkProperRecipe(yeast, malt, "The quantities of yeast and malt (in ounces) are not the same.");
		this.malt = this.yeast;
		
		this.salt = new Utils().checkProperRecipe(salt, (flour / 20.0) - yeast, 
				"The quantities of salt and yeast are not 1/20th of the weight of the flour (in ounces) are not the same.");
				
	}
	
	BagelRecipe(double flour, double yeast) {
		this.flour = flour;
		this.water = flour;
		this.yeast = yeast;
		this.malt = yeast;
		this.salt = Math.abs((flour / 20.0) - yeast);
		
	}
	
	BagelRecipe(double flourVol, double yeastVol, double saltVol) {
		this.flour = flourVol * 4.25;
		this.water = this.flour;
		
		this.yeast = yeastVol * 5.0;
		this.malt = this.yeast;
		
		if (new Utils().equals(saltVol * 10.0 + this.yeast, this.flour / 20.0)) {
			this.salt = saltVol * 10.0;
		} else {
			throw new IllegalArgumentException("The salt and yeast volumes are not 1/20th of the volume of the flour.");
		}
	}
}

class ExamplesBagel {

	boolean testRecipe(Tester t) {
		return t.checkConstructorException(new IllegalArgumentException("The quantities of salt and yeast are not 1/20th of the weight of the flour (in ounces) are not the same."), "BagelRecipe", 10.0, 10.0, 10.0, 10.0, 10.0) &&
				t.checkConstructorException(new IllegalArgumentException("The quantities of flour and water (in ounces) are not the same."), "BagelRecipe", 10.0, 11.0, 10.0, 10.0, 10.0) &&
				t.checkConstructorException(new IllegalArgumentException("The quantities of yeast and malt (in ounces) are not the same."), "BagelRecipe", 20.0, 20.0, 10.0, 11.0, 10.0) &&
				t.checkConstructorException(new IllegalArgumentException("The quantities of salt and yeast are not 1/20th of the weight of the flour (in ounces) are not the same."), "BagelRecipe", 20.0, 20.0, 1.0, 1.0, 10.0);
	}
	
	BagelRecipe b1 = new BagelRecipe(100, 2);
	
	boolean testConstructor2(Tester t) {
		return t.checkInexact(b1.flour, b1.water, 0.00001) &&
				t.checkInexact(b1.yeast, b1.malt, 0.00001) &&
				t.checkInexact(b1.salt + b1.yeast, b1.flour / 20.0, 0.00001);
	}
	
	
	BagelRecipe b2 = new BagelRecipe(4, 0.15, 0.01);
	boolean testConstructor3(Tester t) {
		return t.checkConstructorException(new IllegalArgumentException("The salt and yeast volumes are not 1/20th of the volume of the flour."), "BagelRecipe", 10.0, 10.0, 10.0) &&
				t.checkInexact(b2.flour, b2.water, 0.00001) &&
				t.checkInexact(b2.yeast, b2.malt, 0.00001) &&
				t.checkInexact(b2.salt + b2.yeast, b2.flour / 20.0, 0.00001);
	}
	
	
}