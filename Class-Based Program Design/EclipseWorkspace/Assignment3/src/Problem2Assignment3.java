import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.worldcanvas.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
 
interface ITree { 
	WorldImage draw();
	
	double getOffset();
	
	boolean isDrooping();
	
	ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree);
	
	double getWidth();
}

class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it
  
  Leaf(int size, Color color) {
	  this.size = size;
	  this.color = color;
  }
  
  public WorldImage draw() {
	  return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }
  
  public double getOffset() {
	  return this.size;
  }
  
  public boolean isDrooping() {
	  return false;
  }
  
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree) {
	  return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, otherTree);
  }
  
  public double getWidth() {
	  return this.size * 2;
  }
}
 
class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;
  
  Stem(int length, double theta, ITree tree) {
	  this.length = length;
	  this.theta = theta % 360;
	  this.tree = tree;
  }
  
  public WorldImage draw() {
	    int x = (int) ((this.length + tree.getOffset()) * Math.cos(Math.toRadians(this.theta)));
	    int y = (int) ((this.length + tree.getOffset()) * Math.sin(Math.toRadians(this.theta)));
	    WorldImage line = new LineImage(new Posn(x, -y), Color.BLACK).movePinhole(x / 2.0, -y / 2.0);
	    return new OverlayImage(this.tree.draw(), line).movePinhole(-x, y);
	  }

  
  public double getOffset() {
	  return this.length + tree.getOffset();
  }
  
  public boolean isDrooping() {
	  return (this.theta > 180 || this.tree.isDrooping());
  }
  
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree) {
	  return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, otherTree);
  }
  
  public double getWidth() {
	  return this.tree.getWidth() + this.length;
  }
  
}
 
class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;
  
  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right) {
	  this.leftLength = leftLength;
	  this.rightLength = rightLength;
	  this.leftTheta = leftTheta % 360;
	  this.rightTheta = rightTheta % 360;
	  this.left = left;
	  this.right = right;
  }
  
  public WorldImage draw() {
	    return new OverlayImage(new Stem(leftLength, leftTheta, left).draw(),
	        new Stem(rightLength, rightTheta, right).draw());
	  }
  
  
  public double getOffset() {
	  return this.leftLength + left.getOffset();
  }
  
  public boolean isDrooping() {
	  return (this.leftTheta > 180 || this.rightTheta > 180) || (this.left.isDrooping() || this.right.isDrooping());
  }
  
  public ITree combine(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree otherTree) {
	  return new Branch(leftLength, rightLength, leftTheta, rightTheta, this, otherTree);
  }
  
  public double getWidth() {
	  return this.left.getWidth() + this.right.getWidth() + this.leftLength + this.rightLength;
  }
}

class ExamplesDrawTree {
	ITree leaf1 = new Leaf(150, Color.RED);
	ITree leaf2 = new Leaf(150, Color.BLUE);
	ITree leaf3 = new Leaf(100, Color.GREEN);
	
	ITree stem1 = new Stem(143, 170, leaf1);
	ITree stem2 = new Stem(200, 30, leaf3);
	ITree stem3 = new Stem(123, 190, stem2);
	
	ITree tree1 = new Branch(130, 56, 190, 40, new Leaf(50, Color.RED), new Leaf(75, Color.BLUE));

	ITree tree2 = new Branch(130, 70, 115, 65, new Leaf(100, Color.GREEN), new Leaf(55, Color.ORANGE));

	ITree tree3 = new Stem(90, 90, tree1);

	ITree tree4 = new Stem(50, 90, tree2);

	ITree tree5 = new Branch(79, 124, 150, 30, tree1, tree2);

	
	boolean testDrawTree(Tester t) {
		  WorldCanvas c = new WorldCanvas(1000, 1000);
		  WorldScene s = new WorldScene(1000, 1000);
		  return c.drawScene(s.placeImageXY(tree1.draw(), 500, 500))
		      && c.show();
		} 
	
	boolean testDrooping(Tester t) {
		return t.checkExpect(leaf1.isDrooping(), false) &&
				t.checkExpect(tree1.isDrooping(), true) &&
				t.checkExpect(tree2.isDrooping(), false) &&
				t.checkExpect(stem1.isDrooping(), false) &&
				t.checkExpect(stem3.isDrooping(), true);
		
	}
	
	boolean testCombine(Tester t) {
		return t.checkExpect(tree1.combine(40, 50, 150, 30, tree2), new Branch(40, 50, 150, 30, tree1, tree2));
		
	}
	
	boolean testWidth(Tester t) {
		return t.checkExpect(leaf1.getWidth(), 300.0) &&
				t.checkExpect(stem2.getWidth(), 400.0) &&
				t.checkExpect(stem3.getWidth(), 523.0) &&
				t.checkExpect(tree1.getWidth(), 436.0) &&
				t.checkExpect(tree5.getWidth(), 1149.0);
		
	}
}
