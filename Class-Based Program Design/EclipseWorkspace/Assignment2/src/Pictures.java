import tester.*;

interface Picture {
	int getWidth();
	
	int countShape();
	
	int countDepth();
	
	Picture mirror();
	
	String pictureRecipe(int depth);
}

class Shape implements Picture {
	String kind;
	int size;
	
	Shape(String kind, int size) {
		this.kind = kind;
		this.size = size;
	}
	
	public int getWidth() {
		return this.size;
	}
	
	public int countShape() {
		return 1;
	}
	
	public int countDepth() {
		return 1;
	}
	
	public Picture mirror() {
		return this;
	}
	
	public String pictureRecipe(int depth) {
		return this.kind;
	}
}

class Combo implements Picture {
	String name;
	Picture operation;
	
	Combo(String name, Picture operation) {
		this.name = name;
		this.operation = operation;
	}
	
	public int getWidth() {
		return this.operation.getWidth();
	}
	
	public int countShape() {
		return this.operation.countShape();
	}
	
	public int countDepth() {
		return this.operation.countDepth();
	}
	
	public Picture mirror() {
		return new Combo(this.name, this.operation.mirror());
	}
	
	public String pictureRecipe(int depth) {
		if (depth <= 0) {
			return this.name;
		} else {
			return operation.pictureRecipe(depth - 1);
		}
	}
}


class Scale implements Picture {
	Picture pic;
	
	Scale(Picture pic) {
		this.pic = pic;
	}
	
	public int getWidth() {
		return 2 * this.pic.getWidth();
	}
	
	public int countShape() {
		return pic.countShape();
	}
	
	public int countDepth() {
		return 1 + this.pic.countDepth();
	}
	
	public Picture mirror() {
		return new Scale(this.pic.mirror());
	}
	
	public String pictureRecipe(int depth) {
		return "scale(" + pic.pictureRecipe(depth) + ")";
		
	}
}


class Beside implements Picture {
	Picture pic1;
	Picture pic2;
	
	Beside(Picture pic1, Picture pic2) {
		this.pic1 = pic1;
		this.pic2 = pic2;
	}
	
	public int getWidth() {
		return this.pic1.getWidth() + this.pic2.getWidth();
	}
	
	public int countShape() {
		return pic1.countShape() + pic2.countShape();
	}
	
	public int countDepth() {
		return 1 + Math.max(this.pic1.countDepth(), this.pic2.countDepth());
	}
	
	public Picture mirror() {
		return new Beside(this.pic2.mirror(), this.pic1.mirror());
	}
	
	public String pictureRecipe(int depth) {
		return "beside(" + pic1.pictureRecipe(depth) + ", " + pic2.pictureRecipe(depth) + ")";
	}
}

class Overlay implements Picture {
	Picture pic1;
	Picture pic2;
	
	Overlay(Picture pic1, Picture pic2) {
		this.pic1 = pic1;
		this.pic2 = pic2;
	}
	
	public int getWidth() {
		return Math.max(this.pic1.getWidth(), this.pic2.getWidth());
	}
	
	public int countShape() {
		return pic1.countShape() + pic2.countShape();
	}
	
	public int countDepth() {
		return 1 + Math.max(this.pic1.countDepth(), this.pic2.countDepth());
	}
	
	public Picture mirror() {
		return new Overlay(this.pic1.mirror(), this.pic2.mirror());
	}
	
	public String pictureRecipe(int depth) {
		return "overlay(" + pic1.pictureRecipe(depth) + ", " + pic2.pictureRecipe(depth) + ")";
	}
}



class ExamplesPicture {
	////////////
	Picture circle = new Shape("circle", 20);
	Picture square = new Shape("square", 30);
	Picture bigCircle = new Combo("bigCircle", new Scale(circle));
	Picture squareOnCircle = new Combo("squareOnCircle", new Overlay(circle, square));
	Picture squareOnBigCircle = new Combo("squareOnBigCircle", new Overlay(bigCircle, square));
	Picture doubleSquareOnCircle = new Combo("doubleSquareOnCircle", new Beside(squareOnCircle, squareOnCircle));
	Picture doubleSquareOnBigCircle = new Combo("doubleSquareOnBigCircle", new Beside(squareOnBigCircle, squareOnBigCircle));
	
	///////////
	Picture circle2 = new Shape("circle2", 40);
	Picture square2 = new Shape("square2", 50);
	Picture bigSquare = new Combo("bigSquare", new Scale(square2));
	Picture bigSquareOnCircle = new Overlay(circle2, bigSquare);
	Picture based = new Combo("based", new Scale(new Overlay(bigSquare, new Beside(squareOnCircle, squareOnCircle))));
	Picture beside2 = new Combo("beside2", new Scale(new Beside(new Beside(circle, square), new Beside(circle2, square2))));
	
	boolean testGetWidth(Tester t) {
		return t.checkExpect(circle.getWidth(), 20) && 
			   t.checkExpect(square.getWidth(), 30) &&
			   t.checkExpect(bigCircle.getWidth(), 40) &&
			   t.checkExpect(squareOnCircle.getWidth(), 30) &&
			   t.checkExpect(doubleSquareOnCircle.getWidth(), 60);
	}
	
	boolean testCountShape(Tester t) {
		return t.checkExpect(circle.countShape(), 1) &&
				t.checkExpect(bigCircle.countShape(), 1) &&
				t.checkExpect(squareOnCircle.countShape(), 2) &&
				t.checkExpect(doubleSquareOnCircle.countShape(), 4) &&
				t.checkExpect(based.countShape(), 5);
	}
	
	boolean testDepthCounter(Tester t) {
		return t.checkExpect(circle.countDepth(), 1) &&
				t.checkExpect(bigCircle.countDepth(), 2) &&
				t.checkExpect(squareOnCircle.countDepth(), 2) &&
				t.checkExpect(squareOnBigCircle.countDepth(), 3) &&
				t.checkExpect(doubleSquareOnCircle.countDepth(), 3) &&
				t.checkExpect(based.countDepth(), 5);
	}
	
	boolean testMirror(Tester t) {
		return t.checkExpect(circle.mirror(), circle) && 
				t.checkExpect(bigCircle.mirror(), bigCircle) &&
				t.checkExpect(squareOnCircle.mirror(), squareOnCircle) &&
				t.checkExpect(squareOnBigCircle.mirror(), squareOnBigCircle) &&
				t.checkExpect(doubleSquareOnCircle.mirror(), new Combo("doubleSquareOnCircle", new Beside(squareOnCircle, squareOnCircle))) &&
				t.checkExpect(beside2.mirror(), new Combo("beside2", new Scale(new Beside(new Beside(square2, circle2), new Beside(square, circle)))));
	}
	
	boolean testPictureRecipe(Tester t) {
		return t.checkExpect(circle.pictureRecipe(0), "circle") && 
			   t.checkExpect(circle.pictureRecipe(5), "circle") && 
			   t.checkExpect(bigCircle.pictureRecipe(0), "bigCircle") && 
			   t.checkExpect(bigCircle.pictureRecipe(1), "scale(circle)") && 
			   t.checkExpect(bigCircle.pictureRecipe(5), "scale(circle)") && 
			   t.checkExpect(doubleSquareOnCircle.pictureRecipe(0), "doubleSquareOnCircle") && 
			   t.checkExpect(doubleSquareOnCircle.pictureRecipe(1), "beside(squareOnCircle, squareOnCircle)") && 
//			   t.checkExpect(doubleSquareOnCircle.pictureRecipe(2), "beside(overlay(square, circle), overlay(square, circle))") && 
//			   t.checkExpect(doubleSquareOnCircle.pictureRecipe(3), "beside(overlay(square, circle), overlay(square, circle))") && 
//			   t.checkExpect(doubleSquareOnCircle.pictureRecipe(5), "beside(overlay(square, circle), overlay(square, circle))") &&
			   t.checkExpect(doubleSquareOnBigCircle.pictureRecipe(0), "doubleSquareOnBigCircle") && 
			   t.checkExpect(doubleSquareOnBigCircle.pictureRecipe(1), "beside(squareOnBigCircle, squareOnBigCircle)") && 
			   t.checkExpect(doubleSquareOnBigCircle.pictureRecipe(2), "beside(overlay(bigCircle, square), overlay(bigCircle, square))") && 
			   t.checkExpect(doubleSquareOnBigCircle.pictureRecipe(3), "beside(overlay(scale(circle), square), overlay(scale(circle), square))") && 
			   t.checkExpect(doubleSquareOnBigCircle.pictureRecipe(5), "beside(overlay(scale(circle), square), overlay(scale(circle), square))");
			   
			   
	}
	
	
	
}
