import tester.*;

// to represent a geometric shape

interface IShape { 
	double area();
	
	double distanceToOrigin();
	
	IShape grow(int inc);
	
	boolean isBiggerThan(IShape that);
	
	boolean contains(Point point);
}

interface Point {
	double distanceToOrigin();
	
	boolean withinCircle(Circle that);
	
	boolean withinSquare(Square that);
	
}

class PolarPt implements Point {
	double r;
	double theta;
	
	PolarPt(double r, double theta) {
		this.r = r;
		this.theta = theta;
	}
	
	public double distanceToOrigin() {
		return this.r;
	}
	
	public boolean withinCircle(Circle that) {
		return that.contains(convertToCartesian(this));
	}
	
	public boolean withinSquare(Square that) {
		return that.contains(convertToCartesian(this));
	}
	
	CartPt convertToCartesian(PolarPt pp) {
		return new CartPt(this.r * Math.cos(this.theta), this.r * Math.sin(this.theta));
	}
}

class CartPt implements Point{
	double x;
	double y;
	
	CartPt(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double distanceToOrigin() {
		return (Math.sqrt(this.x * this.x + this.y * this.y));
	}
	
	public boolean withinCircle(Circle that) {
		return ((this.x - that.center.x) * (this.x - that.center.x)) +
				((this.y - that.center.y) * (this.y - that.center.y)) < (that.rad * that.rad);
	}
	
	public boolean withinSquare(Square that) {
		return ((that.topLeft.x < this.x) && (that.topLeft.x + that.side > this.x)) &&
				((that.topLeft.y > this.y) && (that.topLeft.y - that.side < this.y));
	}			
}

// to represent a circle
class Circle implements IShape {
    CartPt center;
    int rad;
    String color;

    Circle(CartPt center, int rad, String color) {
    	this.center = center;
        this.rad = rad;
        this.color = color;
    }
    
    public double area() {
    	return Math.PI * this.rad * this.rad;
    }
    
    public double distanceToOrigin() {
    	return Math.abs(this.center.distanceToOrigin() - this.rad);
    }
    
    public IShape grow(int inc) {
    	return new Circle(center, this.rad + inc, this.color);
    }
    
    public boolean isBiggerThan(IShape that) {
    	return this.area() > that.area();
    }
    
    public boolean contains(Point point) {
    	return point.withinCircle(this);
    }
}

// to represent a rectangle
class Square implements IShape {
    CartPt topLeft;
    int side;
    String color;

    Square(CartPt topLeft, int side, String color) {
        this.topLeft = topLeft;
        this.side = side;
        this.color = color;
    }
    
    public double area() {
    	return this.side * this.side;
    }
    
    public double distanceToOrigin() {
    	return topLeft.distanceToOrigin();
    }
    
    public IShape grow(int inc) {
    	return new Square(topLeft, this.side + inc, this.color);
    }
    
    public boolean isBiggerThan(IShape that) {
    	return this.area() > that.area();
    }
    
    public boolean contains(Point point) {
    	return point.withinSquare(this);
    }
}

class Combo implements IShape {
	IShape shape1;
	IShape shape2;
	
	Combo(IShape shape1, IShape shape2) {
		this.shape1 = shape1;
		this.shape2 = shape2;
	}
	
	public double area() {
		return this.shape1.area() + this.shape2.area();
	}
	
	public double distanceToOrigin() {
		return Math.min(this.shape1.distanceToOrigin(), this.shape2.distanceToOrigin());
	}
	
	public IShape grow(int inc) {
		return new Combo(this.shape1.grow(inc), this.shape2.grow(inc));
	}
	
	public boolean isBiggerThan(IShape that) {
		return this.area() > that.area();
	}
	
	public boolean contains(Point point) {
		return this.shape1.contains(point) || this.shape2.contains(point);
	}
}

// to represent a combined shape
//class Combo implements IShape {
//    IShape top;
//    IShape bot;
//
//    Combo(IShape top, IShape bot) {
//        this.top = top;
//        this.bot = bot;
//    }
//}


// to represent examples and tests for shapes
class ExamplesShapes {

	CartPt cp0 = new CartPt(-5, -5);
	
	CartPt cp1 = new CartPt(50,50);
	CartPt cp2 = new CartPt(20,20);
	
	CartPt cp3 = new CartPt(49, 49);
	CartPt cp4 = new CartPt(21, 17);

	
    IShape c1 = new Circle(cp1, 50, "red");
    IShape s1 = new Square(cp2, 20, "purple");

    
    boolean testIShapeArea(Tester t) {
	    return
	    t.checkInexact(this.c1.area(), 7853.98, 0.01) &&
	    t.checkInexact(this.s1.area(), 400.0, 0.01);
    }
    
    boolean testIShapeDistanceToOrigin(Tester t) {
        return
        t.checkInexact(this.c1.distanceToOrigin(), 50.0 * Math.sqrt(2.0) - 50.0, 0.01) &&
        t.checkInexact(this.s1.distanceToOrigin(), 20.0 * Math.sqrt(2.0), 0.01);
    }
    
    boolean testIShapeIsBiggerThan(Tester t) {
        return t.checkExpect(this.c1.isBiggerThan(this.s1), true);
    }
    
    boolean testIShapeContains(Tester t) {
    	return  t.checkExpect(this.c1.contains(cp0), false) &&
    			t.checkExpect(this.c1.contains(cp3), true) &&
    			t.checkExpect(this.s1.contains(cp0), false) &&
    			t.checkExpect(this.s1.contains(cp4), true);
    	
    }
//    IShape addMouth = new Combo(this.rBot, this.circle);
//    IShape addLeftEye = new Combo(this.rleft, this.addMouth);
//    IShape face = new Combo(new Rect(60, 20, 20, 20), this.addLeftEye); 
}