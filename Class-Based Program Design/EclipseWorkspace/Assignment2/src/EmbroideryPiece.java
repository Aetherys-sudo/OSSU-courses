import tester.*;
//////////
interface Motif {
	double averageDiffCalc();
	
	String getString();
}

interface ILoMotifs {
	double computeAverageDifficulty();
	
	int listLen();
	
	double computeTotalDiff();
	
	String getListStrings();

	boolean isMt();
}

class MtLoMotifs implements ILoMotifs {
	MtLoMotifs() { }
	
	public double computeAverageDifficulty() {
		return 0.0;
	}
	
	public double computeTotalDiff() {
		return 0.0;
	}
	
	public int listLen() { return 0; }
	
	public String getListStrings() {
		return "";
	}
	
	public boolean isMt() {
		return true;
	}
	
}

class ConsLoMotifs implements ILoMotifs {
	Motif current;
	ILoMotifs next;
	
	ConsLoMotifs(Motif current, ILoMotifs next) {
		this.current = current;
		this.next = next;
	}
	
	public double computeAverageDifficulty() {
		return this.computeTotalDiff() / this.listLen();
	}
	
	public double computeTotalDiff() {
		return (this.current.averageDiffCalc() + this.next.computeTotalDiff());
	}
	
	public int listLen() {
		return 1 + this.next.listLen();
	}
	
	public String getListStrings() {
		if (this.next.isMt()) {
			return this.current.getString();
		} else {
			return this.current.getString() + ", " + this.next.getListStrings();
		}	
	}
	
	public boolean isMt() {
		return false;
	}
	

}

class CrossStitchMotif implements Motif {
	String description;
	double difficulty;
	
	CrossStitchMotif(String description, double difficulty) {
		this.description = description;
		this.difficulty = difficulty;
	}
	
	public double averageDiffCalc() {
		return this.difficulty;
	}
	
	public String getString() {
		return description + " (cross stitch)";
	}
}

class ChainStitchMotif implements Motif {
	String description;
	double difficulty;
	
	ChainStitchMotif(String description, double difficulty) {
		this.description = description;
		this.difficulty = difficulty;
	}
	
	public double averageDiffCalc() {
		return this.difficulty;
	}
	
	public String getString() {
		return description + " (chain stitch)";
	}
}

class GroupMotif implements Motif {
	String description;
	ILoMotifs motifs;
	
	GroupMotif(String description, ILoMotifs motifs) {
		this.description = description;
		this.motifs = motifs;
	}
	
	public double averageDiffCalc() {
		return this.motifs.computeAverageDifficulty();
	}
	
	public String getString() {
		return motifs.getListStrings();
	}
}

class EmbroideryPiece {
	String name;
	Motif motif;
	
	EmbroideryPiece(String name, Motif motif) {
		this.name = name;
		this.motif = motif;
	}
	
	double averageDifficulty() {
		return this.motif.averageDiffCalc();
	}
	
	String embroideryInfo() {
		return name + ": " + motif.getString() + ".";
	}
}

class ExamplesEmbroidery {
	

	EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover", new GroupMotif("nature", new ConsLoMotifs(new CrossStitchMotif("bird", 4.5), 
																								new ConsLoMotifs(new ChainStitchMotif("tree", 3.0), 
																										new ConsLoMotifs(new GroupMotif("flowers", new ConsLoMotifs(new CrossStitchMotif("rose", 5.0),
																																					new ConsLoMotifs(new ChainStitchMotif("poppy", 4.75),
																																					 new ConsLoMotifs(new CrossStitchMotif("daisy", 3.2), 
																																					  new MtLoMotifs())))), new MtLoMotifs())))));
	
	EmbroideryPiece pillowCover1 = new EmbroideryPiece("Pillow Cover", new CrossStitchMotif("rose", 5.0));
			
	EmbroideryPiece pillowCover2 = new EmbroideryPiece("Pillow Cover", new ChainStitchMotif("poppy", 4.75));
	
	EmbroideryPiece pillowCover3 = new EmbroideryPiece("Pillow Cover", new GroupMotif("nature", new ConsLoMotifs(new CrossStitchMotif("bird", 4.5), 
			new ConsLoMotifs(new ChainStitchMotif("tree", 3.0), new MtLoMotifs()))));
	
	boolean testAverageDifficulty(Tester t) {
		return t.checkInexact(pillowCover.averageDifficulty(), 3.93, 0.01) &&
				t.checkInexact(pillowCover1.averageDifficulty(), 5.0, 0.01) &&
				t.checkInexact(pillowCover2.averageDifficulty(), 4.75, 0.01) &&
				t.checkInexact(pillowCover3.averageDifficulty(), 3.75, 0.01);
	}
	
	boolean testEmbroideryInfo(Tester t) {
		return t.checkExpect(pillowCover.embroideryInfo(), "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).") &&
				t.checkExpect(pillowCover1.embroideryInfo(), "Pillow Cover: rose (cross stitch).") &&
				t.checkExpect(pillowCover2.embroideryInfo(), "Pillow Cover: poppy (chain stitch).") &&
				t.checkExpect(pillowCover3.embroideryInfo(), "Pillow Cover: bird (cross stitch), tree (chain stitch).");
	}
	
}



