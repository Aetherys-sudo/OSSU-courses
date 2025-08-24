import tester.*;
import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;
import java.util.*;

//Represents a single square of the game area
class Cell {
	// In logical coordinates, with the origin at the top-left corner of the screen
	int x;
	int y;
	Color color;
	boolean flooded;
	// the four adjacent cells to this one
	Cell left;
	Cell top;
	Cell right;
	Cell bottom;
	
	Cell(int x, int y, Color color, boolean flooded) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.flooded = flooded;
		this.left = null;
		this.top = null;
		this.right = null;
		this.bottom = null;
	}
	
	WorldImage drawAt(int x, int y, WorldImage background) {
		int abs_x = x * FloodItWorld.CELL_SIZE + FloodItWorld.CELL_SIZE / 2;
		int abs_y = y * FloodItWorld.CELL_SIZE + FloodItWorld.CELL_SIZE / 2;
		WorldImage square = new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE, OutlineMode.SOLID, this.color);
		
		return new OverlayOffsetImage(square, abs_x - FloodItWorld.BOARD_ABS_SIZE / 2, abs_y - FloodItWorld.BOARD_ABS_SIZE / 2, background);
	}
}

class FloodItWorld extends World {
	  static int BOARD_SIZE = 22;
	  static int CELL_SIZE = 15;
	  static int BOARD_ABS_SIZE = BOARD_SIZE * CELL_SIZE;
	  // All the cells of the game
	  ArrayList<Cell> board;
	  ArrayList<Color> colorMap;
	  
	  FloodItWorld() {
		  Random rand = new Random();
		  this.colorMap = new ArrayList<Color>();
		  
		  colorMap.add(Color.red);
		  colorMap.add(Color.blue);
		  colorMap.add(Color.green);
		  colorMap.add(Color.yellow);
		  colorMap.add(Color.pink);
		  colorMap.add(Color.orange);
		  colorMap.add(Color.cyan);
		  colorMap.add(Color.magenta);
		  
		  this.board = new ArrayList<Cell>();
		  for (int i = BOARD_SIZE - 1; i >= 0; i --) {
			  for (int j = BOARD_SIZE - 1; j >= 0; j --) {
				  this.board.add(new Cell(j, i, colorMap.get(rand.nextInt(8)), false));
			  }
		  }
		  
		  configureCellConnections();
		  
	  }
	  
	  void configureCellConnections() {
		  for (int i = 0; i < BOARD_SIZE; i ++) {
			  for (int j = 0; j < BOARD_SIZE; j ++) {
				  if (j - 1 >= 0) {
					  this.board.get(BOARD_SIZE * i + j).left = this.board.get(BOARD_SIZE * i + j - 1);
				  }
				  
				  if (j + 1 < BOARD_SIZE) {
					  this.board.get(BOARD_SIZE * i + j).right = this.board.get(BOARD_SIZE * i + j + 1);
				  }
				  
				  if (i - 1 >= 0) {
					  this.board.get(BOARD_SIZE * i + j).top = this.board.get(BOARD_SIZE * (i - 1) + j);
				  }
				  
				  if (i + 1 < BOARD_SIZE) {
					  this.board.get(BOARD_SIZE * i + j).bottom = this.board.get(BOARD_SIZE * (i + 1) + j);
				  }
			  }
		  }
	  }
	  
	  WorldImage getBackground() {
		  WorldImage background = new RectangleImage(BOARD_ABS_SIZE, BOARD_ABS_SIZE, OutlineMode.SOLID, Color.white);
		  
		  
		  for (int i = BOARD_SIZE - 1; i >= 0; i --) {
			  for (int j = BOARD_SIZE - 1; j >= 0; j --) {
				  background = this.board.get(BOARD_SIZE * i + j).drawAt(j, i, background);
			  }
		  }
		  
		  return background;
	  }
	  
	  public WorldScene makeScene() {
		  WorldScene scene = getEmptyScene();
		  scene.placeImageXY(getBackground(), BOARD_ABS_SIZE / 2, BOARD_ABS_SIZE / 2);
		  return scene;
	  }
}

class ExamplesFlood {
	void testGame(Tester t) {
		FloodItWorld g = new FloodItWorld();
		t.checkExpect(g.board.get(0).left, null);
		t.checkExpect(g.board.get(0).right, g.board.get(1));
		t.checkExpect(g.board.get(0).top, null);
		t.checkExpect(g.board.get(0).bottom, g.board.get(FloodItWorld.BOARD_SIZE));
		
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE - 1).left, g.board.get(FloodItWorld.BOARD_SIZE - 2));
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE - 1).right, null);
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE - 1).top, null);
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE - 1).bottom, g.board.get(FloodItWorld.BOARD_SIZE + (FloodItWorld.BOARD_SIZE - 1)));
		
		
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE * (FloodItWorld.BOARD_SIZE - 1)).left, null);
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE * (FloodItWorld.BOARD_SIZE - 1)).right, g.board.get(FloodItWorld.BOARD_SIZE * (FloodItWorld.BOARD_SIZE - 1) + 1));
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE * (FloodItWorld.BOARD_SIZE - 1)).top, g.board.get(FloodItWorld.BOARD_SIZE* (FloodItWorld.BOARD_SIZE - 2)));
		t.checkExpect(g.board.get(FloodItWorld.BOARD_SIZE * (FloodItWorld.BOARD_SIZE - 1)).bottom, null);
		g.bigBang(FloodItWorld.BOARD_ABS_SIZE, FloodItWorld.BOARD_ABS_SIZE);
		
	}
	
}