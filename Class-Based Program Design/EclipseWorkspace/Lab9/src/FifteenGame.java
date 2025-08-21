import java.util.ArrayList;
import java.util.*;
import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;
import java.awt.Color;

interface Predicate<T> {
	boolean apply(T data);
}

class FindZero implements Predicate<Tile> {
	public boolean apply(Tile data) {
		return data.value == 0;
	}
}

class HasSameValue implements Predicate<Tile> {
	Tile toCheck;
	
	HasSameValue(Tile toCheck) {
		this.toCheck = toCheck;
	}
	public boolean apply(Tile data) {
		return data.value == this.toCheck.value;
	}
}

class Utils {
	static int WINDOWHEIGHT = 1024;
	static int WINDOWWIDTH = 1024;
	static int BOXWIDTH = WINDOWWIDTH / 4;
	static int BOXHEIGHT = WINDOWHEIGHT / 4;

	
	<T> ArrayList<T> filter(ArrayList<T> arr, Predicate<T> pred) {
		ArrayList<T> res = new ArrayList<T>();
		for (T data : arr) {
			if (pred.apply(data)) {
				res.add(data);
			} else {
				continue;
			}
		}
		
		return res;
	}
	
	<T> void removeExcept(ArrayList<T> arr, Predicate<T> pred) {
		ArrayList<T> bad = new ArrayList<T>();
		
		for (T data : arr) {
			if (pred.apply(data)) {
				continue;
			} else {
				bad.add(data);
			}
		}
		
		for(T data : bad) {
			arr.remove(data);
		}
	}
	
	<T> int find(ArrayList<T> arr, Predicate<T> pred) {
		for (int i = 0; i < arr.size(); i ++) {
			if (pred.apply(arr.get(i))) {
				return i;
			} else {
				continue;
			}
		}
		
		return -1;
	}
	
	void swap (ArrayList<ArrayList<Tile>> arr, int i1, int j1, int i2, int j2) {
		Tile val1 = arr.get(i1).get(j1);
		Tile val2 = arr.get(i2).get(j2);
		
		arr.get(i2).set(j2, val1);
		arr.get(i1).set(j1, val2);
	}
}

//Represents an individual tile
class Tile {
	// The number on the tile.  Use 0 to represent the hole
	int value;
	
	Tile(int value) {
		this.value = value;
	}
	
	
	// Draws this tile onto the background at the specified logical coordinates
	WorldImage drawAt(int row, int col, WorldImage background, boolean correctPlace) {
		WorldImage tileImg = new OverlayImage(new RectangleImage(Utils.BOXWIDTH, Utils.BOXHEIGHT, OutlineMode.OUTLINE, Color.black),
                new RectangleImage(Utils.BOXWIDTH, Utils.BOXHEIGHT, OutlineMode.SOLID, 
                    this.value == 0 ? Color.white : correctPlace ? Color.green : Color.orange));

		if (this.value != 0) {
			tileImg = new OverlayImage(new TextImage(Integer.toString(this.value), 24, Color.black), tileImg);
		}
		
		return new BesideImage(background, tileImg);				
	}
	

}

class FifteenGame extends World {
	// represents the rows of tiles
	ArrayList<ArrayList<Tile>> tiles;
	
	FifteenGame() {
		ArrayList<Tile> allTiles = new ArrayList<Tile>();
		for (int i = 3; i >= 0; i--) {
		    for (int j = 3; j >= 0; j--) {
		        allTiles.add(new Tile(4 * i + j));
		    }
		}
		
		Collections.shuffle(allTiles);
		
		// Reconstruct 2D tiles from flat list
		this.tiles = new ArrayList<ArrayList<Tile>>();
		for (int i = 0; i < 4; i++) {
		    ArrayList<Tile> row = new ArrayList<Tile>();
		    for (int j = 0; j < 4; j++) {
		        row.add(allTiles.get(i * 4 + j));
		    }
		    this.tiles.add(row);
		}
		
		
	}
	
	FifteenGame(ArrayList<ArrayList<Tile>> tiles) {
		this.tiles = tiles;
	}

	WorldImage getBackground() {
		WorldImage background = new EmptyImage();
		WorldImage rowImg = new EmptyImage();
		for (int i = 0; i < 4; i ++) {
			ArrayList<Tile> row = this.tiles.get(i);
			rowImg = new EmptyImage();
			for (int j = 0; j < 4; j ++) {
				if ((row.get(j).value - 1) == (4 * i + j)) {
					rowImg = row.get(j).drawAt(i, j, rowImg, true);
				} else {
					rowImg = row.get(j).drawAt(i, j, rowImg, false);
				}
				
			}
			
			background = new AboveImage(background, rowImg);
		}
		
		
		return background;
	}
	

	// draws the game
	public WorldScene makeScene() {
		WorldScene scene = getEmptyScene();
		scene.placeImageXY(getBackground(), Utils.WINDOWWIDTH / 2, Utils.WINDOWHEIGHT / 2);
		return scene;
	}
	
	void swapGap(ArrayList<ArrayList<Tile>> tiles, int rowOffset, int colOffset) {
		int i;
		int j;
		for (i = 0; i < tiles.size(); i ++) {
			j = new Utils().find(tiles.get(i), new FindZero());
			if (j != -1) {
				if (colOffset != 0) {
					if ((j + colOffset >= tiles.get(i).size()) || (j + colOffset < 0)) {
						return;
					} else {
						new Utils().swap(this.tiles, i, (j + colOffset), i, j);
						return;
					}
				} else {
					if ((i + rowOffset >= tiles.size()) || (i + rowOffset < 0)) {
						return;
					} else {
						new Utils().swap(this.tiles, (i + rowOffset), j, i, j);
						return;
					}
				}
				
			}
		}
	}
	
	// handles keystrokes
	public void onKeyEvent(String k) {
		if ((k.toLowerCase().compareTo("w") == 0) || (k.toLowerCase().compareTo("up") == 0)) {
			this.swapGap(this.tiles, -1, 0);
		} else if ((k.toLowerCase().compareTo("a") == 0) || (k.toLowerCase().compareTo("left") == 0)) {
			this.swapGap(this.tiles, 0, -1);
		} else if((k.toLowerCase().compareTo("s") == 0) || (k.toLowerCase().compareTo("down") == 0)) {
			this.swapGap(this.tiles, 1, 0);
		} else if ((k.toLowerCase().compareTo("d") == 0) || (k.toLowerCase().compareTo("right") == 0)) {
			this.swapGap(this.tiles, 0, 1);
		} else {
			return;
		}
	}
	
	
}


class ExampleFifteenGame {
	void testGame(Tester t) {
		FifteenGame g = new FifteenGame();
		g.bigBang(Utils.WINDOWWIDTH, Utils.WINDOWHEIGHT);
	}
}