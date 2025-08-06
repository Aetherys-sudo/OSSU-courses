import tester.*;

/////////////////////////////////// EXERCISE 31.8

interface IComp {
	boolean lessThan(IComp other);
}
class Apple implements IComp {
	int size;
	int weight;
	String ripeness;
	
	Apple(int size, int weight, String ripeness) {
		this.size = size;
		this.weight = weight;
		this.ripeness = ripeness;
	}
	
	public boolean lessThan(IComp other) {
		return !(other instanceof Orange);
	}
	
}

class Orange implements IComp {
	int weight;
	String ripeness;
	String juicyness;
	
	Orange(int weight, String ripeness, String juicyness) {
		this.weight = weight;
		this.ripeness = ripeness;
		this.juicyness = juicyness;
	}
	
	public boolean lessThan(IComp other) {
		return !(other instanceof Apple);
	}
}

class ExamplesCompare {
	Apple apple = new Apple(10, 20, "ripe af");
	Orange orange = new Orange(10, "kinda ripe af", "juicyyyyy");
	
	Apple apple1 = new Apple(20, 30, "not ripe");
	Orange orange1 = new Orange(10, "green", "not juicy");
	
	boolean testLessThan(Tester t) {
		return t.checkExpect(apple.lessThan(orange), false) &&
				t.checkExpect(orange.lessThan(apple), false) &&
				t.checkExpect(apple.lessThan(apple1), true) &&
				t.checkExpect(apple1.lessThan(apple), true) &&
				t.checkExpect(orange.lessThan(orange1), true) &&
				t.checkExpect(orange1.lessThan(orange), true) &&
				t.checkExpect(orange.lessThan(apple1), false) &&
				t.checkExpect(orange1.lessThan(apple1), false);
	}
}

///////////////////////////////////// ABST

//generic list

interface IFunc<A, R> {
	R apply(A input);
}

interface IFunc2<T, R> {
	R apply(T input1, R acc);
}

interface IList<T> {
	// map over a list, and produce a new list with a (possibly different)
	// element type
	<U> IList<U> map(IFunc<T, U> f);
	
	<R> R foldr(IFunc2<T, R> f, R acc);
	
}

//empty generic list
class MtList<T> implements IList<T> {
	public <U> IList<U> map(IFunc<T, U> f) {
		return new MtList<U>();
	}
	
	public <R> R foldr(IFunc2<T, R> f, R acc) {
		return acc;
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
	
}


abstract class ABST<T> {
	Comparator<T> order;
	
	ABST(Comparator<T> order) {
		this.order = order;
	}
	
	ABST<T> insert(T item) {
		return insertHelp(item, order);
	}
	
	ABST<T> insertHelp(T item, Comparator<T> order) {
		return new Node<T>(order, item, new Leaf<T>(order), new Leaf<T>(order));
	}
	
	boolean present(T item) {
		return false;
	}
	
	abstract ABST<T> getLeftmost();
	
	abstract ABST<T> getLeftmostOrSelf(ABST<T> fallback);
	
	
	ABST<T> getRight() {
		throw new RuntimeException("No right of an empty tree");
	}

	ABST<T> prune(ABST<T> el) {
		return this;
	}
	
	ABST<T> pruneHelper(ABST<T> el, ABST<T> acc, IList<ABST<T>> todo) {
		if (todo instanceof ConsList<ABST<T>>) {
			return ((ConsList<ABST<T>>) todo).first.pruneHelper(el, acc, ((ConsList<ABST<T>>) todo).rest);
		} else {
			return acc;
		}
	}
	
	boolean equals(ABST<T> other) {
		return other.equals(this);
	}
	
	boolean equalsLeaf(Leaf<T> other) {
		return true;
	}
	
	boolean equalsNode(Node<T> other) {
		return false;
	}
	
	boolean sameData(ABST<T> other) {
		return this.sameData(other);
	}
	
	IList<T> buildList() {
	    return this.buildListHelper(new MtList<T>());
	}

	// helper for in-order traversal
	abstract IList<T> buildListHelper(IList<T> acc);

}

class Leaf<T> extends ABST<T> {
	Leaf(Comparator<T> order) {
		super(order);
	}
	
	boolean equals(ABST<T> other) {
		return other.equalsLeaf(this);
	}
	
	boolean sameData(ABST<T> other) {
		return true;
	}
	
    ABST<T> getLeftmost() {
        throw new RuntimeException("No leftmost item of an empty tree");
    }
    
    ABST<T> getLeftmostOrSelf(ABST<T> fallback) {
        return fallback;
    }
    
	IList<T> buildListHelper(IList<T> acc) {
	    return acc;
	}
		
}

class Node<T> extends ABST<T> {
	T data;
	ABST<T> left;
	ABST<T> right;
	
	Node(Comparator<T> order, T data, ABST<T> left, ABST<T> right) {
		super(order);
		this.data = data;
		this.left = left;
		this.right = right;
	}
	
	ABST<T> insertHelp(T item, Comparator<T> order) {
		if (order.compare(item, this.data) >= 0) {
			return new Node<T>(order, data, left, right.insertHelp(item, this.order));
		} else {
			return new Node<T>(order, data, left.insertHelp(item, this.order), right);
		}
	}
	
	boolean present(T item) {
		if (order.compare(item, this.data) == 0) {
			return true;
		} else {
			if (order.compare(item, this.data) < 0) {
				return this.left.present(item);
			} else {
				return this.right.present(item);
			}
		}
	}
	
    ABST<T> getLeftmost() {
        return this.left.getLeftmostOrSelf(this);
    }

    ABST<T> getLeftmostOrSelf(ABST<T> fallback) {
        return this.left.getLeftmostOrSelf(this);
    }
	
	ABST<T> getRight() {
		return this.prune(this.getLeftmost());
	}
	
	ABST<T> prune(ABST<T> el) {
		return this.pruneHelper(el, new Leaf<T>(this.order), new MtList<ABST<T>>());
	}
	
	ABST<T> pruneHelper(ABST<T> el, ABST<T> acc, IList<ABST<T>> todo) {
		if (this.equals(el)) {
			return this.left.pruneHelper(el, acc, new ConsList<ABST<T>>(this.right, todo));
		} else {
			return this.left.pruneHelper(el, acc.insert(this.data), new ConsList<ABST<T>>(this.right, todo));
		}
	}
	
	boolean equals(ABST<T> other) {
		return other.equalsNode(this);
	}
	
	boolean equalsLeaf(Leaf<T> other) {
		return false;
	}
	
	boolean equalsNode(Node<T> other) {
		return (this.order.compare(this.data, other.data) == 0) &&
				this.left.equals(other.left) &&
				this.right.equals(other.right);
	}
	
	boolean sameData(ABST<T> other) {
		if (other.present(this.data)) {
			return this.left.sameData(other) && this.right.sameData(other);
		} else {
			return false;
		}
	}
	
	
	IList<T> buildListHelper(IList<T> acc) {
	    IList<T> rightAcc = this.right.buildListHelper(acc);                 // traverse right
	    IList<T> withCurrent = new ConsList<T>(this.data, rightAcc);         // add current
	    return this.left.buildListHelper(withCurrent);                       // traverse left
	}

}

interface Comparator<T> {
	int compare(T t1, T t2);
}

class BooksByTitle implements Comparator<Book> {
	public int compare(Book t1, Book t2) {
		return t1.title.toLowerCase().compareTo(t2.title.toLowerCase());
	}
}

class BooksByAuthor implements Comparator<Book> {
	public int compare(Book t1, Book t2) {
		return t1.author.toLowerCase().compareTo(t2.author.toLowerCase());
	}
}

class BooksByPrice implements Comparator<Book> {
	public int compare(Book t1, Book t2) {
		return t1.price - t2.price;
	}
}





class Book {
	String title;
	String author;
	int price;
	
	Book(String title, String author, int price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}
}


class ExamplesABST {
	Book b1 = new Book("coitz", "luca", 10);
	Book b2 = new Book("oyee", "timi", 130);
	Book b3 = new Book("pliculet", "sekiro", 100);
	Book b4 = new Book("monkee", "onix", 13);
	Book b5 = new Book("hearthstone", "calutu", 210);
	Book b6 = new Book("tarkov", "bogdan", 300);
	
	ABST<Book> mttprice = new Leaf<Book>(new BooksByPrice());
	ABST<Book> t1price = new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice);
	ABST<Book> t2price = new Node<Book>(new BooksByPrice(), b2, 
									new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
									mttprice);
	ABST<Book> t3price = new Node<Book>(new BooksByPrice(), b3, 
			new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
			new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice));

	ABST<Book> mttauthor = new Leaf<Book>(new BooksByAuthor());
	ABST<Book> t1author = new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor);
	ABST<Book> t2author = new Node<Book>(new BooksByAuthor(), b2, 
									new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor), 
									mttauthor);
	ABST<Book> t3author = new Node<Book>(new BooksByAuthor(), b3, 
			new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor), 
			new Node<Book>(new BooksByAuthor(), b2, mttauthor, mttauthor));
	
	
	ABST<Book> bstA = new Node<Book>(new BooksByPrice(), b3, 
			new Node<Book>(new BooksByPrice(), b4, 
					new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), mttprice), 
			new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice));
	
	ABST<Book> bstB = new Node<Book>(new BooksByPrice(), b3, 
			new Node<Book>(new BooksByPrice(), b4, 
					new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), mttprice), 
			new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice));
	
	ABST<Book> bstC = new Node<Book>(new BooksByPrice(), b2, 
			new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
			new Node<Book>(new BooksByPrice(), b6, new Node<Book>(new BooksByPrice(), b5, mttprice, mttprice), mttprice));
			
	
	ABST<Book> bstD = new Node<Book>(new BooksByPrice(), b4, 
			new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
			new Node<Book>(new BooksByPrice(), b3, mttprice, new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice)));
	
	boolean testInsertByPrice(Tester t) {
		return t.checkExpect(t3price.insert(b4), new Node<Book>(new BooksByPrice(), b3, 
				new Node<Book>(new BooksByPrice(), b1, mttprice, new Node<Book>(new BooksByPrice(), b4, mttprice, mttprice)), 
				new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice))) &&
				t.checkExpect(mttprice.insert(b4), new Node<Book>(new BooksByPrice(), b4, mttprice, mttprice)) &&
				t.checkExpect(t3price.insert(b5), new Node<Book>(new BooksByPrice(), b3, 
						new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
						new Node<Book>(new BooksByPrice(), b2, mttprice, new Node<Book>(new BooksByPrice(), b5, mttprice, mttprice)))) &&
				t.checkExpect(t3price.insert(b6), new Node<Book>(new BooksByPrice(), b3, 
						new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice), 
						new Node<Book>(new BooksByPrice(), b2, mttprice, new Node<Book>(new BooksByPrice(), b6, mttprice, mttprice))));

	}
	
	boolean testInsertByAuthor(Tester t) {
		return t.checkExpect(t3author.insert(b4), new Node<Book>(new BooksByAuthor(), b3, 
				new Node<Book>(new BooksByAuthor(), b1, mttauthor, new Node<Book>(new BooksByAuthor(), b4, mttauthor, mttauthor)), 
				new Node<Book>(new BooksByAuthor(), b2, mttauthor, mttauthor))) &&
				t.checkExpect(mttauthor.insert(b4), new Node<Book>(new BooksByAuthor(), b4, mttauthor, mttauthor)) &&
				t.checkExpect(t3author.insert(b5), new Node<Book>(new BooksByAuthor(), b3, 
						new Node<Book>(new BooksByAuthor(), b1, new Node<Book>(new BooksByAuthor(), b5, mttauthor, mttauthor), mttauthor), 
						new Node<Book>(new BooksByAuthor(), b2, mttauthor, mttauthor))) &&
				t.checkExpect(t3author.insert(b6), new Node<Book>(new BooksByAuthor(), b3, 
						new Node<Book>(new BooksByAuthor(), b1, new Node<Book>(new BooksByAuthor(), b6, mttauthor, mttauthor), mttauthor), 
						new Node<Book>(new BooksByAuthor(), b2, mttauthor, mttauthor)));

	}
	
	boolean testPresent(Tester t) {
		return t.checkExpect(t3author.present(b4), false) &&
				t.checkExpect(mttauthor.present(b4), false) &&
				t.checkExpect(t3author.present(b3), true) &&
				t.checkExpect(t3author.present(b1), true);
	}
	
	boolean testLeftmost(Tester t) {
		return t.checkExpect(t1author.getLeftmost(), new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor)) &&
				t.checkException(new RuntimeException("No leftmost item of an empty tree"), mttauthor, "getLeftmost") &&
				t.checkExpect(t2author.getLeftmost(), new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor)) &&
				t.checkExpect(t3author.getLeftmost(), new Node<Book>(new BooksByAuthor(), b1, mttauthor, mttauthor)) &&
				t.checkExpect(bstB.getLeftmost(), new Node<Book>(new BooksByPrice(), b1, mttprice, mttprice));
	}
	
	boolean testEquals(Tester t) {
		return t.checkExpect(bstA.equals(bstB), true) && 
				t.checkExpect(bstA.equals(bstC), false) && 
				t.checkExpect(bstB.equals(bstA), true) && 
				t.checkExpect(bstC.equals(bstD), false);
	}
	
	boolean testSameData(Tester t) {
		return t.checkExpect(bstA.sameData(bstB), true) && 
				t.checkExpect(bstA.sameData(bstC), false) && 
				t.checkExpect(bstB.sameData(bstA), true) && 
				t.checkExpect(bstA.sameData(bstD), true);
	}
	
	boolean testGetRight(Tester t) {
		return t.checkExpect(bstA.getRight(), new Node<Book>(new BooksByPrice(), b3, 
				new Node<Book>(new BooksByPrice(), b4, mttprice, mttprice),
				new Node<Book>(new BooksByPrice(), b2, 
						mttprice, mttprice) 
				)) && 
				t.checkExpect(bstC.getRight(), new Node<Book>(new BooksByPrice(), b2, 
						mttprice, new Node<Book>(new BooksByPrice(), b6, new Node<Book>(new BooksByPrice(), b5, mttprice, mttprice), mttprice)
						)) && 
				t.checkExpect(bstD.getRight(), new Node<Book>(new BooksByPrice(), b4, 
						mttprice,
						new Node<Book>(new BooksByPrice(), b3, mttprice, new Node<Book>(new BooksByPrice(), b2, mttprice, mttprice))));
	}
	
	boolean testBuildList(Tester t) {
		return t.checkExpect(t3price.buildList(), new ConsList<Book>(b1, new ConsList<Book>(b3, new ConsList<Book>(b2, new MtList<Book>())))) && 
				t.checkExpect(mttprice.buildList(), new MtList<Book>()) &&
				t.checkExpect(bstA.buildList(), new ConsList<Book>(b1, new ConsList<Book>(b4, new ConsList<Book>(b3, new ConsList<Book>(b2, new MtList<Book>())))));
	}
	
}

