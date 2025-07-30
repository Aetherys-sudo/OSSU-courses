import tester.*;

//Represents functions of signature A -> R, for some argument type A and
//result type R
interface IFunc<A, R> {
	R apply(A input);
}

class GetYPair implements IFunc<Pair<String, JSON>, JSON> {
	public JSON apply(Pair<String, JSON> input) {
		return input.y;
	}
}

class FindInList implements IFunc2<JSON, JSON> {
	JSONFind finder;

	FindInList(JSONFind finder) {
		this.finder = finder;
	}

	public JSON apply(JSON input, JSON acc) {
		JSON result = input.accept(finder);
		if (!(result instanceof JSONBlank)) {
			return result;
		} else {
			return acc;
		}
	}
}

class FindInObject implements IFunc2<Pair<String, JSON>, JSON> {
	String key;

	FindInObject(String key) {
		this.key = key;
	}

	public JSON apply(Pair<String, JSON> pair, JSON acc) {
		if (pair.x.equals(this.key)) {
			return pair.y;
		} else {
			return acc;
		}
	}
}

interface JSONVisitor<T> extends IFunc<JSON, T> {
	T visitBlank(JSONBlank blank);
	T visitNumber(JSONNumber num);
	T visitBool(JSONBool bool);
	T visitString(JSONString str);
	T visitList(JSONList lst);
	T visitObject(JSONObject obj);
}

class JSONFind implements JSONVisitor<JSON> {
	String key;

	JSONFind(String key) {
		this.key = key;
	}

	public JSON apply(JSON arg) {
		return arg.accept(this);
	}

	public JSON visitBlank(JSONBlank blank) {
		return new JSONBlank();
	}

	public JSON visitNumber(JSONNumber num) {
		return new JSONBlank();
	}

	public JSON visitBool(JSONBool bool) {
		return new JSONBlank();
	}

	public JSON visitString(JSONString str) {
		return new JSONBlank();
	}

	public JSON visitList(JSONList lst) {
		return lst.values.foldr(new FindInList(this), new JSONBlank());
	}

	public JSON visitObject(JSONObject obj) {
		return obj.pairs.foldr(new FindInObject(this.key), new JSONBlank());
	}
}


class AddOneToVals implements JSONVisitor<JSON> {
	
	public JSON apply(JSON arg) {
		return arg.accept(this);
	}
	
	public JSON visitBlank(JSONBlank blank) {
		return blank;
	}
	
	public JSON visitNumber(JSONNumber num) {
		return new JSONNumber(num.number + 1);
	}
	
	public JSON visitBool(JSONBool bool) {
		if (bool.bool) {
			return new JSONNumber(2);
		} else {
			return new JSONNumber(1);
		}
	}
	
	public JSON visitString(JSONString str) {
		return new JSONNumber(str.str.length() + 1);
	}
	
	public JSON visitList(JSONList lst) {
		return new JSONNumber(lst.values.foldr(new SumJSONS(), 0));
	}
	
	public JSON visitObject(JSONObject obj) {
		return new JSONNumber(obj.pairs.map(new GetYPair()).foldr(new SumJSONS(), 0));
	}
}


class IsGreaterThan50 implements JSONVisitor<Boolean>  {
	
	public Boolean visitBlank(JSONBlank blank) {
		return false;
	}
	
	public Boolean visitNumber(JSONNumber num) {
		return num.number > 50;
	}
	
	public Boolean visitBool(JSONBool bool) {
		return true;
	}
	
	public Boolean visitString(JSONString str) {
		return str.str.length() > 50;
	}
	
	public Boolean visitList(JSONList lst) {
		return lst.values.foldr(new SumJSONS(), 0) > 50;
	}
	
	public Boolean visitObject(JSONObject obj) {
		return obj.pairs.map(new GetYPair()).foldr(new SumJSONS(), 0) > 50;
	}
	
	public Boolean apply(JSON arg) {
		return arg.accept(this);
	}
}


class JSONToNumber implements JSONVisitor<Integer> {
	
	public Integer apply(JSON arg) {
		return arg.accept(this);
	}
	
	public Integer visitBlank(JSONBlank blank) {
		return 0;
	}
	
	public Integer visitNumber(JSONNumber num) {
		return num.number;
	}
	
	public Integer visitBool(JSONBool bool) {
		if (bool.bool) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public Integer visitString(JSONString str) {
		return str.str.length();
	}
	
	public Integer visitList(JSONList lst) {
		return lst.values.foldr(new SumJSONS(), 0);
	}
	
	public Integer visitObject(JSONObject obj) {
		return obj.pairs.map(new GetYPair()).foldr(new SumJSONS(), 0);
	}
}




interface IFunc2<T, R> {
	R apply(T input1, R acc);
}

class SumNums implements IFunc2<Integer, Integer> {
	public Integer apply(Integer input, Integer acc) {
		return input + acc;
	}
}

class StringNums implements IFunc2<Integer, String> {
	public String apply(Integer input, String acc) {
		return acc + input;
	}
}

class SumJSONS implements IFunc2<JSON, Integer> {
	public Integer apply(JSON input, Integer acc) {
		return new JSONToNumber().apply(input) + acc;
	}
}




interface IPred<T> extends IFunc<T, Boolean> {
	Boolean apply(T input);
}

class JSONGreater50Pred implements IPred<JSON> {
	public Boolean apply(JSON input) {
		return input.accept(new IsGreaterThan50());
	}
}



//generic list
interface IList<T> {
	// map over a list, and produce a new list with a (possibly different)
	// element type
	<U> IList<U> map(IFunc<T, U> f);
	
	<R> R foldr(IFunc2<T, R> f, R acc);
	
	<U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup);
}

//empty generic list
class MtList<T> implements IList<T> {
	public <U> IList<U> map(IFunc<T, U> f) {
		return new MtList<U>();
	}
	
	public <R> R foldr(IFunc2<T, R> f, R acc) {
		return acc;
	}
	
	public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
		return backup;
	}
}

//non-empty generic list
class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;

	ConsList(T first, IList<T> rest) {
		 this.first = first;
		 this.rest = rest;
	}

	public <U> IList<U> map(IFunc<T, U> f) {
		return new ConsList<U>(f.apply(this.first), this.rest.map(f));
	}
	
	public <R> R foldr(IFunc2<T, R> f, R acc) {
		return this.rest.foldr(f, f.apply(this.first, acc));
	}
	
	public <U> U findSolutionOrElse(IFunc<T, U> convert, IPred<U> pred, U backup) {
		if (pred.apply(convert.apply(this.first))) {
			return convert.apply(this.first);
		} else {
			return this.rest.findSolutionOrElse(convert, pred, backup);
		}
	}
}




//a json value
interface JSON {
	<T> T accept(JSONVisitor<T> visitor);
}

//no value
class JSONBlank implements JSON {
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitBlank(this);
	}
}

//a number
class JSONNumber implements JSON {
	int number;
	
	JSONNumber(int number) {
		this.number = number;
	}
	
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitNumber(this);
	}
}

//a boolean
class JSONBool implements JSON {
	boolean bool;

	JSONBool(boolean bool) {
		this.bool = bool;
	}
	
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitBool(this);
	}
}

//a string
class JSONString implements JSON {
	String str;

	JSONString(String str) {
		this.str = str;
	}
	
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitString(this);
	}
}

//a list of JSON values
class JSONList implements JSON {
	IList<JSON> values;
	
	JSONList(IList<JSON> values) {
		this.values = values;
	}
	
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitList(this);
	}
}

//a list of JSON pairs
class JSONObject implements JSON {
	IList<Pair<String, JSON>> pairs;
	
	JSONObject(IList<Pair<String, JSON>> pairs) {
		this.pairs = pairs;
	}
	
	public <T> T accept(JSONVisitor<T> visitor) {
		return visitor.visitObject(this);
	}
}


//generic pairs
class Pair<X, Y> {
	X x;
	Y y;

	Pair(X x, Y y) {
		this.x = x;
		this.y = y;
	}
}


class ExamplesGenerics {
	IList<Integer> ints = new ConsList<Integer>(1,
							new ConsList<Integer>(2,
								new ConsList<Integer>(3,
										new ConsList<Integer>(4,
												new ConsList<Integer>(5,
														new ConsList<Integer>(6,
																new ConsList<Integer>(7,
																		new ConsList<Integer>(8, new MtList<Integer>()))))))));
	
	boolean testFoldr(Tester t) {
		return t.checkExpect(ints.foldr(new SumNums(), 0), 36) &&
				t.checkExpect(ints.foldr(new StringNums(), ""), "12345678");
	}
			
}


class ExamplesJSON {
	JSONBlank bl = new JSONBlank();
	JSONNumber num1 = new JSONNumber(3);
	JSONNumber num2 = new JSONNumber(30);
	JSONNumber num3 = new JSONNumber(300);
	JSONBool bool1 = new JSONBool(true);
	JSONBool bool2 = new JSONBool(false);
	JSONString string1 = new JSONString("");
	JSONString string2 = new JSONString("adv");
	JSONString string3 = new JSONString("dfghghgfjfghj45346");
	
	IList<JSON> jsonList = new ConsList<JSON>(bl, 
					new ConsList<JSON>(num1, 
					new ConsList<JSON>(num2, 
					new ConsList<JSON>(num3, 
					new ConsList<JSON>(bool1, 
					new ConsList<JSON>(bool2, 
					new ConsList<JSON>(string1, 
					new ConsList<JSON>(string2, 
					new ConsList<JSON>(string3, new MtList<JSON>())))))))));
	
	JSONList lst = new JSONList(jsonList);
							
	boolean testJSONVisitor(Tester t) {
		return t.checkExpect(new JSONToNumber().apply(bl) , 0) &&
				t.checkExpect(new JSONToNumber().apply(num1) , 3) &&
				t.checkExpect(new JSONToNumber().apply(num2) , 30) &&
				t.checkExpect(new JSONToNumber().apply(num3) , 300) &&
				t.checkExpect(new JSONToNumber().apply(bool1) , 1) &&
				t.checkExpect(new JSONToNumber().apply(bool2) , 0) &&
				t.checkExpect(new JSONToNumber().apply(string1) , 0) &&
				t.checkExpect(new JSONToNumber().apply(string2) , 3) &&
				t.checkExpect(new JSONToNumber().apply(string3) , 18) &&
				t.checkExpect(new JSONToNumber().apply(lst), 355);
	}
	
	boolean testJSONVisitorList(Tester t) {
		return t.checkExpect(jsonList.map(new JSONToNumber()), new ConsList<Integer>(0,
				new ConsList<Integer>(3,
						new ConsList<Integer>(30,
								new ConsList<Integer>(300,
										new ConsList<Integer>(1,
												new ConsList<Integer>(0,
														new ConsList<Integer>(0,
																new ConsList<Integer>(3,
																		new ConsList<Integer>(18, new MtList<Integer>()))))))))));
	}
	
	boolean testFindSolution(Tester t) {
		return t.checkExpect(jsonList.findSolutionOrElse(new AddOneToVals(), new JSONGreater50Pred(), new JSONBlank()), new JSONNumber(301));
	}
	
	JSONObject obj = new JSONObject(new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("1", lst), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("2", bl), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("3", num1), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("4", num2), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("5", num3), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("6", bool1), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("7", bool2), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("8", string1), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("9", string2), 
									new ConsList<Pair<String, JSON>>(new Pair<String, JSON>("10", string3), 
									new MtList<Pair<String, JSON>>())))))))))));
	boolean testObject(Tester t) {
		return t.checkExpect(jsonList.findSolutionOrElse(new AddOneToVals(), new JSONGreater50Pred(), new JSONBlank()), new JSONNumber(301));
	}
	
	boolean testJSONFind(Tester t) {
		JSON result = obj.accept(new JSONFind("3"));
		return t.checkExpect(result, num1);
	}

}

