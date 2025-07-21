import java.awt.Color;

import javalib.funworld.*;
import javalib.worldimages.*;
import tester.Tester; 

class Game extends World {
	ILoShip shipsOnScreen;
	ILoBullet bulletsOnScreen;
	int frameCount;
	int bulletsLeft;
	int score;
	
	Game(ILoShip shipsOnScreen, ILoBullet bulletsOnScreen, int frameCount, int bulletsLeft, int score) {
		this.shipsOnScreen = shipsOnScreen;
		this.bulletsOnScreen = bulletsOnScreen;
		this.frameCount = frameCount;
		this.bulletsLeft = bulletsLeft;
		this.score = score;
	}
	
	Game(int bulletsLeft) {
		this.shipsOnScreen = new MtLoShip();
		this.bulletsOnScreen = new MtLoBullet();
		this.frameCount = 0;
		this.bulletsLeft = bulletsLeft;
		this.score = 0;
	}
	
	Game(ILoShip shipsOnScreen, ILoBullet bulletsOnScreen) {
		this.shipsOnScreen = shipsOnScreen;
		this.bulletsOnScreen = bulletsOnScreen;
		this.frameCount = 0;
		this.bulletsLeft = 10;
		this.score = 0;
	}
	
	public WorldScene makeScene() {
		return this.shipsOnScreen.placeAll(
				this.bulletsOnScreen.placeAll(addScoreScreen()));
	}
	
	public WorldScene addScoreScreen() {
		return getEmptyScene().placeImageXY(new OverlayImage(new TextImage("SCORE: " + this.score, 24, Color.BLACK),
													  new RectangleImage(120, 30, OutlineMode.OUTLINE, Color.BLACK)), Utils.SCREENWIDTH - 74, 24);
	}
	
	public Game spawnShips() {
		return new Game(this.shipsOnScreen.spawn(frameCount + 1), 
				this.bulletsOnScreen,
				this.frameCount + 1, 
				this.bulletsLeft, 
				this.score);
	}
	
	public Game moveAllPieces() {
		return new Game(this.shipsOnScreen.moveAll(), 
				this.bulletsOnScreen.moveAll(),
				this.frameCount, 
				this.bulletsLeft, 
				this.score);
	}
	
	public Game checkCollisions() {
		return new Game(this.shipsOnScreen.checkCollisions(this.bulletsOnScreen), 
				this.bulletsOnScreen.checkCollisions(this.shipsOnScreen),
				this.frameCount, 
				this.bulletsLeft, 
				updateScore());
	}
	
	public Game removeOffscreen() {
		return new Game(this.shipsOnScreen.removeOffscreen(Utils.SCREENWIDTH, Utils.SCREENHEIGHT), 
				this.bulletsOnScreen.removeOffscreen(Utils.SCREENWIDTH, Utils.SCREENHEIGHT),
				this.frameCount, 
				this.bulletsLeft, 
				this.score);
	}
	
	public Game onTick() {
		return this.spawnShips()
				.moveAllPieces()
				.checkCollisions()
				.removeOffscreen();
	}
	
	public Game onKeyEvent(String key) {
		if (key.compareTo(" ") == 0) {
			return new Game(this.shipsOnScreen, 
							new ConsLoBullet(new Bullet(new MyPosn(Utils.SCREENWIDTH / 2, Utils.SCREENHEIGHT - Utils.BULLETRADIUS),
														new MyPosn(0, -Utils.BULLETSPEED),
														Utils.BULLETRADIUS), 
											this.bulletsOnScreen), 
							this.frameCount,
							this.bulletsLeft - 1,
							this.score);
		} else {
			return this;
		}
	}
	
	public WorldEnd worldEnds() {
		if (this.bulletsLeft <= 0 && !this.bulletsOnScreen.isNull()) {
			return new WorldEnd(true, getEndScreen());
		} else {
			return new WorldEnd(false, this.makeScene());
		}
	} 
	
	WorldScene getEndScreen() {
		return getEmptyScene().placeImageXY(new AboveImage(new TextImage("GAME OVER", 24, Color.BLACK),
															new TextImage("SCORE: " + this.score, 24, Color.BLACK)), Utils.SCREENWIDTH / 2, Utils.SCREENHEIGHT / 2);
	}
	
	int updateScore() {
		return this.score + 50 * this.shipsOnScreen.countCollisions(this.bulletsOnScreen);
	}
	
}

class ExamplesNBullets {

	boolean testBigBang(Tester t) {
		Game game = new Game(1000);
		return game.bigBang(Utils.SCREENWIDTH, Utils.SCREENHEIGHT, Utils.TICKRATE);
	}	
}