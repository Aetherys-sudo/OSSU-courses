import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)

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
}

interface ILoCircle {
	ILoCircle moveAll();
	
	ILoCircle removeOffscreen(int screenWidth, int screenHeight);
	
	WorldScene placeAll(WorldScene screen);
	
	int countRemovedCircles(int screenWidth, int screenHeight);
}

class MtLoCircle implements ILoCircle {
	MtLoCircle() {};
	
	public ILoCircle moveAll() {
		return this;
	}
	
	public ILoCircle removeOffscreen(int screenWidth, int screenHeight) {
		return this;
	}
	
	public int countRemovedCircles(int screenWidth, int screenHeight) {
		return 0;
	}
	
	public WorldScene placeAll(WorldScene screen) {
		return screen;
	}
}

class ConsLoCircle implements ILoCircle {
	MyCircle first;
	ILoCircle next;
	
	ConsLoCircle(MyCircle first, ILoCircle next) {
		this.first = first;
		this.next = next;
	}
	
	public ILoCircle moveAll() {
		return new ConsLoCircle(this.first.move(), this.next.moveAll());
	}
	
	public ILoCircle removeOffscreen(int screenWidth, int screenHeight) {
		if (this.first.isOffscreen(screenWidth, screenHeight)) {
			return this.next.removeOffscreen(screenWidth, screenHeight);
		} else {
			return new ConsLoCircle(this.first, this.next.removeOffscreen(screenWidth, screenHeight));
		}
	}
	
	public int countRemovedCircles(int screenWidth, int screenHeight) {
		if (this.first.isOffscreen(screenWidth, screenHeight)) {
			return 1 + this.next.countRemovedCircles(screenWidth, screenHeight);
		} else {
			return this.next.countRemovedCircles(screenWidth, screenHeight);
		}
	}
	
	public WorldScene placeAll(WorldScene screen) {
		return this.next.placeAll(this.first.place(screen));
	}
}

class MyCircle {
	  MyPosn position; // in pixels
	  MyPosn velocity; // in pixels/tick
	  
	  MyCircle(MyPosn position, MyPosn velocity) {
		  this.position = position;
		  this.velocity = velocity;
	  }
	  
	  MyCircle move() {
		  return new MyCircle(this.position.add(velocity), velocity);
	  }
	  
	  boolean isOffscreen(int screenWidth, int screenHeight) {
		  return this.position.isOffscreen(screenWidth, screenHeight);
	  }
	  
	  WorldImage draw() {
		  return new CircleImage(20, OutlineMode.SOLID, Color.RED);
	  }
	  
	  WorldScene place(WorldScene screen) {
		  return screen.placeImageXY(this.draw(), this.position.getX(), this.position.getY());
	  }
	  
}

class MyWorld extends World {
	ILoCircle gameCircles;
	int circleLimit;
	
	MyWorld(ILoCircle gameCircles, int circleLimit) {
		this.gameCircles = gameCircles;
		this.circleLimit = circleLimit;
	}
	
	MyWorld(int circleLimit) {
		this.gameCircles = new MtLoCircle();
		this.circleLimit = circleLimit;
	}
	
	public WorldScene makeScene() {
		return this.gameCircles.placeAll(getEmptyScene());
	}
	
	public MyWorld onTick() {
		ILoCircle currList = this.gameCircles.moveAll();
		int removedCount = currList.countRemovedCircles(1024, 1024);
		currList = currList.removeOffscreen(1024, 1024);
		return new MyWorld(currList, this.circleLimit - removedCount);	
	}
	
	
	public MyWorld onMousePressed(Posn pos) {
		return new MyWorld(new ConsLoCircle(new MyCircle(new MyPosn(pos.x, pos.y), new MyPosn(0, -10)), this.gameCircles), this.circleLimit);
	}
	
	public WorldEnd worldEnds() {
	  if (this.circleLimit <= 0) {
	    return new WorldEnd(true, this.makeAFinalScene());
	  } else {
	    return new WorldEnd(false, this.makeScene());
	  }
	}
	
	public WorldScene makeAFinalScene() {
		return getEmptyScene().placeImageXY(new TextImage("Game Over", 24, Color.BLACK), 512, 512);
	}
}

class ExamplesCircleGame {
	MyCircle c1 = new MyCircle(new MyPosn(50, 50), new MyPosn(0, -10));
	MyCircle c2 = new MyCircle(new MyPosn(200, 50), new MyPosn(0, -20));
	MyCircle c3 = new MyCircle(new MyPosn(100, 200), new MyPosn(0, -2));
	MyCircle c4 = new MyCircle(new MyPosn(500, 500), new MyPosn(0, -20));
	MyCircle c5 = new MyCircle(new MyPosn(400, 350), new MyPosn(0, -15));
	
	ILoCircle circleList = new ConsLoCircle(c1, 
							new ConsLoCircle(c2, 
							new ConsLoCircle(c3, 
							new ConsLoCircle(c4, 
							new ConsLoCircle(c5,
									new MtLoCircle())))));

	
	  boolean testBigBang(Tester t) {
		MyWorld game = new MyWorld(new MtLoCircle(), 10);
	    int worldWidth = 1024;
	    int worldHeight = 1024;
	    double tickRate = 1.0 / 60.0;
	    return game.bigBang(worldWidth, worldHeight, tickRate);
	  }
		
	
}