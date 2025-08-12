import tester.*;

interface IArith {
	<R> R accept(IArithVisitor<R> visitor);
}

interface Function<T, U> {
	U apply(T obj);
}

class Neg implements Function<Double, Double> {
	public Double apply(Double obj) {
		return -obj;
	}
}

class Sqr implements Function<Double, Double> {
	public Double apply(Double obj) {
		return obj * obj;
	}
}

interface BiFunction<T, V, U> {
	U apply(T arg1, V arg2);
}

class Plus implements BiFunction<Double, Double, Double> {
	public Double apply(Double obj1, Double obj2) {
		return obj1 + obj2;
	}
}

class Minus implements BiFunction<Double, Double, Double> {
	public Double apply(Double obj1, Double obj2) {
		return obj1 - obj2;
	}
}

class Mul implements BiFunction<Double, Double, Double> {
	public Double apply(Double obj1, Double obj2) {
		return obj1 * obj2;
	}
}

class Div implements BiFunction<Double, Double, Double> {
	public Double apply(Double obj1, Double obj2) {
		return obj1 / obj2;
	}
}

interface IArithVisitor<R> {
	R apply(IArith obj);
	
	R visitConst(Const obj);
	R visitUnary(UnaryFormula obj);
	R visitBinary(BinaryFormula obj);
}

class EvalVisitor implements IArithVisitor<Double> {
	public Double apply(IArith obj) {
		return obj.accept(this);
	}
	
	public Double visitConst(Const obj) {
		return obj.num;
	}
	
	public Double visitUnary(UnaryFormula obj) {
		return obj.func.apply(obj.child.accept(this));
	}
	
	public Double visitBinary(BinaryFormula obj) {
		return obj.func.apply(obj.left.accept(this), obj.right.accept(this));
	}
}

class PrintVisitor implements IArithVisitor<String> {
	public String apply(IArith obj) {
		return obj.accept(this);
	}
	
	public String visitConst(Const obj) {
		return Double.toString(obj.num);
	}
	
	public String visitUnary(UnaryFormula obj) {
		return "(" + obj.name + " " + obj.child.accept(this) + ")";
	}
	
	public String visitBinary(BinaryFormula obj) {
		return "(" + obj.name + " " + obj.left.accept(this) + " " + obj.right.accept(this) + ")";
	}
}

class DoublerVisitor implements IArithVisitor<IArith> {
	public IArith apply(IArith obj) {
		return obj.accept(this);
	}
	
	public IArith visitConst(Const obj) {
		return new Const(obj.num * 2.0);
	}
	
	public IArith visitUnary(UnaryFormula obj) {
		return new UnaryFormula(obj.func, obj.name, obj.child.accept(this));
	}
	
	public IArith visitBinary(BinaryFormula obj) {
		return new BinaryFormula(obj.func, obj.name, obj.left.accept(this), obj.right.accept(this));
	}
}

class NoNegativeResults implements IArithVisitor<Boolean> {
	public Boolean apply(IArith obj) {
		return obj.accept(this);
	}
	
	public Boolean visitConst(Const obj) {
		return obj.num >= 0.0;
	}
	
	public Boolean visitUnary(UnaryFormula obj) {
		return obj.name.compareTo("neg") != 0 && obj.child.accept(this);
	}
	
	public Boolean visitBinary(BinaryFormula obj) {
		if (obj.name.compareTo("minus") == 0) {
			if (obj.left.accept(new EvalVisitor()) >= obj.right.accept(new EvalVisitor())) {
				return obj.left.accept(this) && obj.right.accept(this);
			} else {
				return false;
			}
		} else {
			return obj.left.accept(this) && obj.right.accept(this);
		}
	}
}

class Const implements IArith {
	double num;
	
	Const(double num) {
		this.num = num;
	}
	
	public <R> R accept(IArithVisitor<R> visitor) { return visitor.visitConst(this); }
}

class UnaryFormula implements IArith {
	Function<Double, Double> func;
	String name;
	IArith child;
	
	UnaryFormula(Function<Double, Double> func, String name, IArith child) {
		this.func = func;
		this.name = name;
		this.child = child;
	}
	
	public <R> R accept(IArithVisitor<R> visitor) { return visitor.visitUnary(this); }
}

class BinaryFormula implements IArith {
	BiFunction<Double, Double, Double> func;
	String name;
	IArith left;
	IArith right;
	
	BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
		this.func = func;
		this.name = name;
		this.left = left;
		this.right = right;
	}
	
	public <R> R accept(IArithVisitor<R> visitor) { return visitor.visitBinary(this); }
}


class ExamplesIArith {
	IArith c1 = new Const(2.0);
	IArith c2 = new Const(0.0);
	IArith c3 = new Const(1.0);
	IArith c4 = new Const(5.6);
	IArith c5 = new Const(-5.6);
	
	IArith u1 = new UnaryFormula(new Neg(), "neg", c1);
	IArith u2 = new UnaryFormula(new Sqr(), "sqr", c1);
	IArith u3 = new UnaryFormula(new Neg(), "neg", u1);
	IArith u4 = new UnaryFormula(new Sqr(), "sqr", u3);
	IArith u5 = new UnaryFormula(new Sqr(), "sqr", u1);
	IArith u6 = new UnaryFormula(new Sqr(), "sqr", u4);
	IArith u7 = new UnaryFormula(new Neg(), "neg", u6);
	
	IArith b1 = new BinaryFormula(new Plus(), "plus", c1, c2);
	IArith b2 = new BinaryFormula(new Plus(), "plus", c1, c3);
	IArith b3 = new BinaryFormula(new Plus(), "plus", c1, c1);
	IArith b4 = new BinaryFormula(new Plus(), "plus", c4, c1);
	IArith b5 = new BinaryFormula(new Plus(), "plus", c5, c1);
	
	IArith b11 = new BinaryFormula(new Minus(), "minus", c1, c2);
	IArith b12 = new BinaryFormula(new Minus(), "minus", c1, c3);
	IArith b13 = new BinaryFormula(new Minus(), "minus", c1, c1);
	IArith b14 = new BinaryFormula(new Minus(), "minus", c4, c1);
	IArith b15 = new BinaryFormula(new Minus(), "minus", c1, c4);
	
	IArith b21 = new BinaryFormula(new Mul(), "mul", c1, c2);
	IArith b22 = new BinaryFormula(new Mul(), "mul", c1, c3);
	IArith b23 = new BinaryFormula(new Mul(), "mul", c1, c1);
	IArith b24 = new BinaryFormula(new Mul(), "mul", c4, c1);
	
	IArith b31 = new BinaryFormula(new Div(), "div", c1, c2);
	IArith b32 = new BinaryFormula(new Div(), "div", c1, c3);
	IArith b33 = new BinaryFormula(new Div(), "div", c1, c1);
	IArith b34 = new BinaryFormula(new Div(), "div", c4, c1);
	IArith b35 = new BinaryFormula(new Div(), "div", c2, c1);
	
	IArith bc1 = new BinaryFormula(new Plus(), "plus", u7, u7);
	IArith bc2 = new BinaryFormula(new Minus(), "minus", u7, u7);
	IArith bc3 = new BinaryFormula(new Mul(), "mul", u7, u7);
	IArith bc4 = new BinaryFormula(new Div(), "div", u7, u7);
	IArith bc5 = new BinaryFormula(new Plus(), "plus", u7, c4);
	IArith bc6 = new BinaryFormula(new Minus(), "minus", u7, c4);
	IArith bc7 = new BinaryFormula(new Mul(), "mul", u7, c4);
	IArith bc8 = new BinaryFormula(new Div(), "div", u7, c4);
	
	IArith bc11 = new BinaryFormula(new Plus(), "plus", bc1, u7);
	IArith bc12 = new BinaryFormula(new Minus(), "minus", bc1, u7);
	IArith bc13 = new BinaryFormula(new Mul(), "mul", bc1, u7);
	IArith bc14 = new BinaryFormula(new Div(), "div", bc1, u7);
	IArith bc15 = new BinaryFormula(new Plus(), "plus", bc8, bc6);
	IArith bc16 = new BinaryFormula(new Minus(), "minus", bc5, bc2);
	IArith bc17 = new BinaryFormula(new Mul(), "mul", bc4, c4);
	IArith bc18 = new BinaryFormula(new Div(), "div", bc3, bc1);
	
	IArith bc21 = new BinaryFormula(new Plus(), "plus", bc11, bc16);
	IArith bc22 = new BinaryFormula(new Minus(), "minus", bc15, bc16);
	IArith bc23 = new BinaryFormula(new Mul(), "mul", bc17, bc11);
	IArith bc24 = new BinaryFormula(new Div(), "div", bc17, bc18);
	
	IArith bc25 = new BinaryFormula(new Minus(), "minus", b4, new BinaryFormula(new Minus(), "minus", u2, c4));
	IArith bc26 = new BinaryFormula(new Minus(), "minus", b4, new BinaryFormula(new Minus(), "minus", c4, u2));
	
	void testEval(Tester t) {
		t.checkInexact(c1.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(c2.accept(new EvalVisitor()), 0.0, 0.0001);
		
		t.checkInexact(u1.accept(new EvalVisitor()), -2.0, 0.0001);
		t.checkInexact(u2.accept(new EvalVisitor()), 4.0, 0.0001);
		t.checkInexact(u3.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(u4.accept(new EvalVisitor()), 4.0, 0.0001);
		t.checkInexact(u5.accept(new EvalVisitor()), 4.0, 0.0001);
		t.checkInexact(u6.accept(new EvalVisitor()), 16.0, 0.0001);
		t.checkInexact(u7.accept(new EvalVisitor()), -16.0, 0.0001);
		
		t.checkInexact(b1.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(b2.accept(new EvalVisitor()), 3.0, 0.0001);
		t.checkInexact(b3.accept(new EvalVisitor()), 4.0, 0.0001);
		t.checkInexact(b4.accept(new EvalVisitor()), 7.6, 0.0001);
		
		t.checkInexact(b11.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(b12.accept(new EvalVisitor()), 1.0, 0.0001);
		t.checkInexact(b13.accept(new EvalVisitor()), 0.0, 0.0001);
		t.checkInexact(b14.accept(new EvalVisitor()), 3.6, 0.0001);
		
		t.checkInexact(b21.accept(new EvalVisitor()), 0.0, 0.0001);
		t.checkInexact(b22.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(b23.accept(new EvalVisitor()), 4.0, 0.0001);
		t.checkInexact(b24.accept(new EvalVisitor()), 11.2, 0.0001);
		
		t.checkExpect(b31.accept(new EvalVisitor()).isInfinite(), true);
		t.checkInexact(b32.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(b33.accept(new EvalVisitor()), 1.0, 0.0001);
		t.checkInexact(b34.accept(new EvalVisitor()), 2.8, 0.0001);
		t.checkInexact(b35.accept(new EvalVisitor()), 0.0, 0.0001);
		
		t.checkInexact(bc1.accept(new EvalVisitor()), -32.0, 0.0001);
		t.checkInexact(bc2.accept(new EvalVisitor()), 0.0, 0.0001);
		t.checkInexact(bc3.accept(new EvalVisitor()), 256.0, 0.0001);
		t.checkInexact(bc4.accept(new EvalVisitor()), 1.0, 0.0001);
		t.checkInexact(bc5.accept(new EvalVisitor()), -10.4, 0.0001);
		t.checkInexact(bc6.accept(new EvalVisitor()), -21.6, 0.0001);
		t.checkInexact(bc7.accept(new EvalVisitor()), -89.6, 0.0001);
		t.checkInexact(bc8.accept(new EvalVisitor()), -2.857, 0.0001);
		
		t.checkInexact(bc11.accept(new EvalVisitor()), -48.0, 0.0001);
		t.checkInexact(bc12.accept(new EvalVisitor()), -16.0, 0.0001);
		t.checkInexact(bc13.accept(new EvalVisitor()), 512.0, 0.0001);
		t.checkInexact(bc14.accept(new EvalVisitor()), 2.0, 0.0001);
		t.checkInexact(bc15.accept(new EvalVisitor()), -24.457, 0.0001);
		t.checkInexact(bc16.accept(new EvalVisitor()), -10.4, 0.0001);
		t.checkInexact(bc17.accept(new EvalVisitor()), 5.6, 0.0001);
		t.checkInexact(bc18.accept(new EvalVisitor()), -8.0, 0.0001);
		
		t.checkInexact(bc21.accept(new EvalVisitor()), -58.4, 0.0001);
		t.checkInexact(bc22.accept(new EvalVisitor()), -14.057, 0.0001);
		t.checkInexact(bc23.accept(new EvalVisitor()), -268.8, 0.0001);
		t.checkInexact(bc24.accept(new EvalVisitor()), -0.7, 0.0001);
		
	}
	
	void testPrint(Tester t) {
		t.checkExpect(c1.accept(new PrintVisitor()), "2.0");
		t.checkExpect(u1.accept(new PrintVisitor()), "(neg 2.0)");
		t.checkExpect(u7.accept(new PrintVisitor()), "(neg (sqr (sqr (neg (neg 2.0)))))");
		t.checkExpect(b1.accept(new PrintVisitor()), "(plus 2.0 0.0)");
		t.checkExpect(bc1.accept(new PrintVisitor()), "(plus (neg (sqr (sqr (neg (neg 2.0))))) (neg (sqr (sqr (neg (neg 2.0))))))");
		t.checkExpect(bc11.accept(new PrintVisitor()), "(plus (plus (neg (sqr (sqr (neg (neg 2.0))))) (neg (sqr (sqr (neg (neg 2.0)))))) (neg (sqr (sqr (neg (neg 2.0))))))");
		t.checkExpect(bc24.accept(new PrintVisitor()), "(div (mul (div (neg (sqr (sqr (neg (neg 2.0))))) (neg (sqr (sqr (neg (neg 2.0)))))) 5.6) (div (mul (neg (sqr (sqr (neg (neg 2.0))))) (neg (sqr (sqr (neg (neg 2.0)))))) (plus (neg (sqr (sqr (neg (neg 2.0))))) (neg (sqr (sqr (neg (neg 2.0))))))))");
	}
	
	void testDouble(Tester t) {
		t.checkExpect(c1.accept(new DoublerVisitor()), new Const(4.0));
		t.checkExpect(u1.accept(new DoublerVisitor()), new UnaryFormula(new Neg(), "neg", new Const(4.0)));
		t.checkExpect(u7.accept(new DoublerVisitor()), new UnaryFormula(new Neg(), "neg", new UnaryFormula(new Sqr(), "sqr", new UnaryFormula(new Sqr(), "sqr", new UnaryFormula(new Neg(), "neg", new UnaryFormula(new Neg(), "neg", new Const(4.0)))))));
		t.checkExpect(b1.accept(new DoublerVisitor()), new BinaryFormula(new Plus(), "plus", new Const(4.0), new Const(0.0)));
	}
	
	void testOnlyPositive(Tester t) {
		t.checkExpect(c1.accept(new NoNegativeResults()), true);
		t.checkExpect(c5.accept(new NoNegativeResults()), false);
		t.checkExpect(u1.accept(new NoNegativeResults()), false);
		t.checkExpect(u2.accept(new NoNegativeResults()), true);
		t.checkExpect(u3.accept(new NoNegativeResults()), false);
		t.checkExpect(u4.accept(new NoNegativeResults()), false);
		t.checkExpect(u7.accept(new NoNegativeResults()), false);
		t.checkExpect(b1.accept(new NoNegativeResults()), true);
		t.checkExpect(b5.accept(new NoNegativeResults()), false);
		t.checkExpect(b11.accept(new NoNegativeResults()), true);
		t.checkExpect(b15.accept(new NoNegativeResults()), false);
		t.checkExpect(b13.accept(new NoNegativeResults()), true);
		t.checkExpect(bc1.accept(new NoNegativeResults()), false);
		t.checkExpect(bc2.accept(new NoNegativeResults()), false);
		t.checkExpect(bc3.accept(new NoNegativeResults()), false);
		t.checkExpect(bc25.accept(new NoNegativeResults()), false);
		t.checkExpect(bc26.accept(new NoNegativeResults()), true);
	}
	
}

