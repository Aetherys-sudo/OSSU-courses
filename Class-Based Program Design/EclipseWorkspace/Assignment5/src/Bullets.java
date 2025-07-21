import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
                             // and predefined colors (Color.RED, Color.GRAY, etc.)

interface ILoBullet {
	ILoBullet moveAll();
	
	ILoBullet removeOffscreen(int screenWidth, int screenHeight);
	
	WorldScene placeAll(WorldScene screen);
	
	boolean isNull();
	
	boolean intersects(Ship ship);
	
	ILoBullet checkCollisions(ILoShip ships);
	
	ILoBullet append(ILoBullet other);
	
}

class MtLoBullet implements ILoBullet {
	MtLoBullet() {};
	
	public ILoBullet moveAll() {
		return this;
	}
	
	public ILoBullet removeOffscreen(int screenWidth, int screenHeight) {
		return this;
	}
	
	public WorldScene placeAll(WorldScene screen) {
		return screen;
	}
	
	public boolean isNull() {
		return true;
	}
	
	public boolean intersects(Ship ship) {
		return false;
	}
	
	public ILoBullet checkCollisions(ILoShip ships) {
		return this;
	}
	
	public ILoBullet append(ILoBullet other) {
		return other;
	}
}

class ConsLoBullet implements ILoBullet {
	Bullet first;
	ILoBullet next;
	
	ConsLoBullet(Bullet first, ILoBullet next) {
		this.first = first;
		this.next = next;
	}
	
	public ILoBullet moveAll() {
		return new ConsLoBullet(this.first.move(), this.next.moveAll());
	}
	
	public ILoBullet removeOffscreen(int screenWidth, int screenHeight) {
		if (this.first.isOffscreen(screenWidth, screenHeight)) {
			return this.next.removeOffscreen(screenWidth, screenHeight);
		} else {
			return new ConsLoBullet(this.first, this.next.removeOffscreen(screenWidth, screenHeight));
		}
	}

	public WorldScene placeAll(WorldScene screen) {
		return this.next.placeAll(this.first.place(screen));
	}
	
	public boolean isNull() {
		return false;
	}
	
	public boolean intersects(Ship ship) {
		if (this.first.collides(ship)) {
			return true;
		} else {
			return this.next.intersects(ship);
		}
	}
	
	public ILoBullet checkCollisions(ILoShip ships) {
		if (ships.intersects(this.first)) {
			return this.first.addNewBullets().append(this.next.checkCollisions(ships));
		} else {
			return new ConsLoBullet(this.first, this.next.checkCollisions(ships));
		}
	}
	
	public ILoBullet append(ILoBullet other) {
		return new ConsLoBullet(this.first, this.next.append(other));
	}
	
}

class Bullet {
	MyPosn position;
	MyPosn velocity;
	int size;
	int newBullets;
	
	Bullet(MyPosn position, MyPosn velocity, int size, int newBullets) {
		this.position = position;
		this.velocity = velocity;
		this.size = size;
		this.newBullets = newBullets;
	}
	
	Bullet(MyPosn position, MyPosn velocity, int size) {
		this.position = position;
		this.velocity = velocity;
		this.size = size;
		this.newBullets = 2;
	}
	
	Bullet move() {
		return new Bullet(this.position.add(velocity), velocity, size, newBullets);
	}
	  
	boolean isOffscreen(int screenWidth, int screenHeight) {
		return this.position.isOffscreen(screenWidth, screenHeight);
	}
  
	WorldImage draw() {
		return new CircleImage(Utils.BULLETRADIUS, Utils.BULLETFILL, Utils.BULLETCOLOR);
	}
	  
	WorldScene place(WorldScene screen) {
		return screen.placeImageXY(this.draw(), this.position.getX(), this.position.getY());
	}
	
	boolean collides(Ship ship) {
		return this.position.distanceTo(ship.position) <= (Utils.SHIPRADIUS + Utils.BULLETRADIUS);
	}
	
	ILoBullet addNewBullets() {
		return this.addNBullets(0, this.newBullets);
	}
	
	ILoBullet addNBullets(int count, int n) {
		if (count >= n) {
			return new MtLoBullet();
		} else {
			return new ConsLoBullet(new Bullet(this.position,
											   new MyPosn((int)Math.floor(Utils.BULLETSPEED * Math.cos((double)count * (360.0 / (double)(n + 1.0)))), (int)Math.floor(Utils.BULLETSPEED * -Math.sin((double)count * (double)(360.0 / (n + 1.0))))),
											   Math.min(this.size + Utils.BULLETINCREASE, Utils.MAXBULLETRADIUS),
											   this.newBullets + 1), this.addNBullets(count + 1, n));
		}
	}
	
}