import tester.*;

interface GamePiece {
	int getValue();
	
	GamePiece merge(GamePiece other);
	
	boolean isValid();
	
	boolean isWellFormed();
	
}

class BaseTile implements GamePiece {
	int value;
	
	BaseTile(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public GamePiece merge(GamePiece other) {
		return new MergeTile(this, other);
	}
	
	public boolean isValid() {
		return this.isWellFormed();
	}
	
	
	public boolean isWellFormed() {
		return (int)(Math.log(this.value) / Math.log(2)) == (Math.log(this.value) / Math.log(2));
	}
}

class MergeTile implements GamePiece {
	GamePiece piece1;
	GamePiece piece2;
	
	MergeTile(GamePiece piece1, GamePiece piece2) {
		this.piece1 = piece1;
		this.piece2 = piece2;
	}
	
	public int getValue() {
		return this.piece1.getValue() + this.piece2.getValue();
	}
	
	public GamePiece merge(GamePiece other) {
		return new MergeTile(this, other);
	}
	
	public boolean isValid() {
		return ((this.isEqual()) && (this.isWellFormed()));
	}
	
	boolean isEqual() {
		return (this.piece1.getValue() == this.piece2.getValue());
	}
	
	public boolean isWellFormed() {
		return (this.piece1.isWellFormed() && this.piece2.isWellFormed());
	}
}

class ExamplesPieces {
	GamePiece piece1 = new BaseTile(2);
	GamePiece piece2 = new BaseTile(4);
	GamePiece piece3 = new BaseTile(8);
	GamePiece piece4 = new BaseTile(16);
	GamePiece piece5 = new BaseTile(48);
	GamePiece piece6 = new BaseTile(7);
	
	GamePiece combo1 = new MergeTile(piece1, piece1);
	GamePiece combo2 = new MergeTile(piece1, piece4);
	GamePiece combo3 = new MergeTile(piece2, piece3);
	GamePiece combo4 = new MergeTile(piece3, piece3);
	GamePiece combo5 = new MergeTile(piece4, combo4);
	GamePiece combo6 = new MergeTile(combo5, combo4);
	
	boolean testGetValue(Tester t) {
		return t.checkExpect(piece1.getValue(), 2) &&
		t.checkExpect(piece4.getValue(), 16) &&
		t.checkExpect(combo4.getValue(), 16) &&
		t.checkExpect(combo5.getValue(), 32) &&
		t.checkExpect(combo5.merge(combo4).getValue(), 48);
	}
	
	boolean testMerge(Tester t) {
		return t.checkExpect(piece1.merge(piece2), new MergeTile(piece1, piece2)) &&
				t.checkExpect(piece4.merge(piece3), new MergeTile(piece4, piece3)) &&
				t.checkExpect(combo1.merge(piece4), new MergeTile(combo1, piece4)) &&
				t.checkExpect(combo4.merge(combo3), new MergeTile(combo4, combo3)) &&
				t.checkExpect(combo5.merge(combo4), new MergeTile(combo5, combo4));
	}
	
	boolean testValidMerge(Tester t) {
		return t.checkExpect(piece1.isValid(), true) &&
				t.checkExpect(piece5.isValid(), false) &&
				t.checkExpect(piece6.isValid(), false) &&
				t.checkExpect(combo1.isValid(), true) &&
				t.checkExpect(combo4.isValid(), true) &&
				t.checkExpect(combo3.isValid(), false) &&
				t.checkExpect(combo5.isValid(), true) &&
				t.checkExpect(combo6.isValid(), false);
	}
	
}

