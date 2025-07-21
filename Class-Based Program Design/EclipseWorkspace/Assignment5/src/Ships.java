import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import java.util.Random;
import javalib.funworld.*;      // the abstract World class and the big-bang library


interface ILoShip {
	ILoShip moveAll();
	
	ILoShip removeOffscreen(int screenWidth, int screenHeight);
	
	WorldScene placeAll(WorldScene screen);
	
	ILoShip spawn(int frameCount);
	
	ILoShip spawnShips(int count, ILoInt randList);

	ILoShip checkCollisions(ILoBullet bullets);
	
	boolean intersects(Bullet bullet);
	
	public int countCollisions(ILoBullet bullets);
}

class MtLoShip implements ILoShip {
	MtLoShip() {};
	
	public ILoShip moveAll() {
		return this;
	}
	
	public ILoShip removeOffscreen(int screenWidth, int screenHeight) {
		return this;
	}
	
	public WorldScene placeAll(WorldScene screen) {
		return screen;
	}
	
	public ILoShip spawn(int frameCount) {
		if (frameCount % (int)Math.floor(1.0 / Utils.TICKRATE) == 0) {
			return this.spawnShips(new Random().nextInt(3), new MtLoInt());
		} else {
			return this;
		}
	}
	
	public ILoShip spawnShips(int count, ILoInt randList) {
		int randX = new Random().nextInt(2); //0 represents the left side spawn, 1 represents the right side spawn
		
		int randY = this.getValidRandomVal(randList);
		
		if (count <= 0) {
			if (randX == 0) {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SHIPRADIUS, randY), new MyPosn(Utils.SHIPSPEED, 0)), this);
			} else {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SCREENWIDTH - Utils.SHIPRADIUS, randY), new MyPosn(-Utils.SHIPSPEED, 0)), this);
			}		
		} else {
			if (randX == 0) {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SHIPRADIUS, randY), new MyPosn(Utils.SHIPSPEED, 0)), this.spawnShips((count - 1), new ConsLoInt(randY, randList)));
			} else {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SCREENWIDTH - Utils.SHIPRADIUS, randY), new MyPosn(-Utils.SHIPSPEED, 0)), this.spawnShips((count - 1), new ConsLoInt(randY, randList)));
			}		
		}
	}
	
	int getValidRandomVal(ILoInt randList) {
		int randY = (int)Math.floor((new Random().nextInt((int)(Utils.SCREENHEIGHT - ((2.0 / 7.0) * Utils.SCREENHEIGHT))) 
				+ ((1.0 / 7.0) * Utils.SCREENHEIGHT)));
		
		if (randList.isElIn(randY)) {
			return getValidRandomVal(randList);
		} else {
			return randY;
		}
	}
	
	public ILoShip checkCollisions(ILoBullet bullets) {
		return new MtLoShip();
	}
	
	public boolean intersects(Bullet bullet) {
		return false;
	}
	
	public int countCollisions(ILoBullet bullets) {
		return 0;
	}
}

class ConsLoShip implements ILoShip {
	Ship first;
	ILoShip next;
	
	ConsLoShip(Ship first, ILoShip next) {
		this.first = first;
		this.next = next;
	}
	
	public ILoShip moveAll() {
		return new ConsLoShip(this.first.move(), this.next.moveAll());
	}
	
	public ILoShip removeOffscreen(int screenWidth, int screenHeight) {
		if (this.first.isOffscreen(screenWidth, screenHeight)) {
			return this.next.removeOffscreen(screenWidth, screenHeight);
		} else {
			return new ConsLoShip(this.first, this.next.removeOffscreen(screenWidth, screenHeight));
		}
	}

	
	public WorldScene placeAll(WorldScene screen) {
		return this.next.placeAll(this.first.place(screen));
	}
	
	public ILoShip spawn(int frameCount) {
		if (frameCount % (int)Math.floor((1.0 / Utils.TICKRATE) / 2.0) == 0) {
			return this.spawnShips(new Random().nextInt(3), new MtLoInt()); // spawn between 1 and 3 ships
		} else {
			return this;
		}
	}
	
	public ILoShip spawnShips(int count, ILoInt randList) {
		int randX = new Random().nextInt(2); //0 represents the left side spawn, 1 represents the right side spawn
		
		int randY = this.getValidRandomVal(randList);
		
		if (count <= 0) {
			if (randX == 0) {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SHIPRADIUS, randY), new MyPosn(Utils.SHIPSPEED, 0)), this);
			} else {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SCREENWIDTH - Utils.SHIPRADIUS, randY), new MyPosn(-Utils.SHIPSPEED, 0)), this);
			}		
		} else {
			if (randX == 0) {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SHIPRADIUS, randY), new MyPosn(Utils.SHIPSPEED, 0)), this.spawnShips((count - 1), new ConsLoInt(randY, randList)));
			} else {
				return new ConsLoShip(new Ship(new MyPosn(Utils.SCREENWIDTH - Utils.SHIPRADIUS, randY), new MyPosn(-Utils.SHIPSPEED, 0)), this.spawnShips((count - 1), new ConsLoInt(randY, randList)));
			}		
		}
	}
	
	int getValidRandomVal(ILoInt randList) {
		int randY = (int)Math.floor((new Random().nextInt((int)(Utils.SCREENHEIGHT - ((2.0 / 7.0) * Utils.SCREENHEIGHT))) 
				+ ((1.0 / 7.0) * Utils.SCREENHEIGHT)));
		
		if (randList.isElIn(randY)) {
			return getValidRandomVal(randList);
		} else {
			return randY;
		}                                                                                                         
	}
	
	public boolean intersects(Bullet bullet) {
		if (this.first.collides(bullet)) {
			return true;
		} else {
			return this.next.intersects(bullet);
		}
	}
	
	public int countCollisions(ILoBullet bullets) {
		if (bullets.intersects(this.first)) {
			return 1 + this.next.countCollisions(bullets);
		} else {
			return this.next.countCollisions(bullets);
		}
	}
	
	public ILoShip checkCollisions(ILoBullet bullets) {
		if (bullets.intersects(this.first)) {
			return this.next.checkCollisions(bullets);
		} else {
			return new ConsLoShip(this.first, this.next.checkCollisions(bullets));
		}
	}
	
}

class Ship {
	  MyPosn position; // in pixels
	  MyPosn velocity; // in pixels/tick
	  
	  Ship(MyPosn position, MyPosn velocity) {
		  this.position = position;
		  this.velocity = velocity;
	  }
	  
	  Ship move() {
		  return new Ship(this.position.add(velocity), velocity);
	  }
	  
	  boolean isOffscreen(int screenWidth, int screenHeight) {
		  return this.position.isOffscreen(screenWidth, screenHeight);
	  }
	  
	  WorldImage draw() {
		  return new CircleImage(Utils.SHIPRADIUS, Utils.SHIPFILL, Utils.SHIPCOLOR);
	  }
	  
	  WorldScene place(WorldScene screen) {
		  return screen.placeImageXY(this.draw(), this.position.getX(), this.position.getY());
	  }
	  
	  boolean collides(Bullet bullet) {
		  return this.position.distanceTo(bullet.position) <= (Utils.SHIPRADIUS + Utils.BULLETRADIUS);
	  }
	  
}