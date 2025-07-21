import java.awt.Color;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;


class Utils {
	
	static int SCREENWIDTH = 500;
	static int SCREENHEIGHT = 300;
	static double TICKRATE = 1 / 60.0;
	
	static int BULLETSPEED = 8;
	static int BULLETRADIUS = 2;
	static int MAXBULLETRADIUS = 10;
	static int BULLETINCREASE = 2;
	static Color BULLETCOLOR = Color.BLACK;
	static OutlineMode BULLETFILL = OutlineMode.SOLID;
	
	static int SHIPRADIUS = (int)((1.0 / 20.0) * SCREENHEIGHT);
	static int SHIPSPEED = (int)(0.4 * BULLETSPEED);
	static Color SHIPCOLOR = Color.CYAN;
	static OutlineMode SHIPFILL = OutlineMode.SOLID;
	

}

class MyPosn extends Posn {
	 
	  // standard constructor
	  MyPosn(int x, int y) {
	    super(x, y);
	  }
	 
	  // constructor to convert from a Posn to a MyPosn
	  MyPosn(Posn p) {
	    this(p.x, p.y);
	  }
	  
	  MyPosn add(MyPosn other) {
		  return new MyPosn(this.x + other.x, this.y + other.y);
	  }
	  
	  boolean isOffscreen(int screenWidth, int screenHeight) {
		  return (this.x >= screenWidth || this.x <= 0) || (this.y >= screenHeight || this.y <= 0);
	  }
	  
	  int getX() {
		  return this.x;
	  }
	  
	  int getY() {
		  return this.y;
	  }
	  
	  double distanceTo(MyPosn other) {
		  return Math.hypot((other.x - this.x), (other.y - this.y));
	  }
}

interface ILoInt {
	boolean isElIn(int val);
}

class MtLoInt implements ILoInt {
	MtLoInt() { }
	
	public boolean isElIn(int val) {
		return false;
	}
}

class ConsLoInt implements ILoInt {
	int first;
	ILoInt next;
	
	ConsLoInt(int first, ILoInt next) {
		this.first = first;
		this.next = next;
	}	
	
	public boolean isElIn(int val) {
		if (compare(this.first, val)) {
			return true;
		} else {
			return this.next.isElIn(val);
		}
	}
	
	boolean compare(int one, int other) {
		return (((one - (2 * Utils.SHIPRADIUS)) <= other) && 
				 ((one + (2 * Utils.SHIPRADIUS)) >= other));
	}
}