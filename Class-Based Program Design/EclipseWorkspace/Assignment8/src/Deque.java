import tester.*;

interface IPredicate<T> {
	boolean apply(T data);
}

class LengthGreaterThanOne implements IPredicate<String> {
	public boolean apply(String data) {
		return data.length() > 1;
	}
}


class Deque<T> {
	Sentinel<T> header;
	
	Deque() {
		this.header = new Sentinel<T>();
	}
	
	Deque(Sentinel<T> header) {
		this.header = header;
	}
	
	int size() {
		return this.header.next.size();
	}
	
	void addAtHead(T data) {
		this.header.insertHead(new Node<T>(data));
	}
	
	void addAtTail(T data) {
		this.header.insertTail(new Node<T>(data));
	}
	
	ANode<T> removeFromHead() {
		return this.header.next.remove();
	}
	
	ANode<T> removeFromTail() {
		return this.header.prev.remove();
	}
	
	ANode<T> find(IPredicate<T> pred) {
		return this.header.next.find(pred);
	}
	
	void removeNode(ANode<T> node) {
		node.remove();
	}
}

abstract class ANode<T> {
	ANode<T> prev;
	ANode<T> next;
	
	abstract int size();
	
	abstract T getData();
	
	abstract ANode<T> remove();
	
	abstract ANode<T> find(IPredicate<T> pred);
}

class Sentinel<T> extends ANode<T> {
	
	Sentinel() {
		this.prev = this;
		this.next = this;
	}
	
	int size() {
		return 0;
	}
	
	void insertHead(Node<T> data) {
		data.next = this.next;
		data.prev = this;
		data.next.prev = data;
		this.next = data;	
	}
	
	void insertTail(Node<T> data) {
		data.next = this;
		data.prev = this.prev;
		this.prev = data;
		data.prev.next = data;
		
	}
	
	T getData() {
		return null;
	}
	
	ANode<T> remove() {
		return this;
	}
	
	ANode<T> find(IPredicate<T> pred) {
		return this;
	}
}

class Node<T> extends ANode<T> {
	T data;
	
	Node(T data) {
		this.data = data;
		this.prev = null;
		this.next = null;
	}
	
	Node(T data, ANode<T> prev, ANode<T> next) {
		if (next == null) {
			throw new IllegalArgumentException("The provided 'next' node cannot be null.");
		}
		
		if (prev == null) {
			throw new IllegalArgumentException("The provided 'prev' node cannot be null.");
		}
		
		this.data = data;
		this.next = next;
		this.prev = prev;
		
		this.next.prev = this;
		this.prev.next = this;
	}
	
	int size() {
		return 1 + this.next.size();
	}
	
	T getData() {
		return this.data;
	}
	
	Node<T> remove() {
		Node<T> old = new Node<T>(this.data);
		
		this.prev.next = this.next;
		this.next.prev = this.prev;
		
		return old;
	}
	
	ANode<T> find(IPredicate<T> pred) {
		if (pred.apply(this.data)) {
			return new Node<T>(this.data);
		} else {
			return this.next.find(pred);
		}
	}
	
}

class ExamplesDeque {
	Deque<String> deque1;
	Deque<String> deque2;
	Deque<String> deque3;
	
	Sentinel<String> sentinel2;
	Sentinel<String> sentinel3;
	
	Node<String> n1;
	Node<String> n2;
	Node<String> n3;
	Node<String> n4;
	
	Node<String> n11;
	Node<String> n12;
	Node<String> n13;
	Node<String> n14;
	Node<String> n15;
	Node<String> n16;
	Node<String> n17;
	Node<String> n18;
	
	void init() {
		deque1 = new Deque<String>();
		
		sentinel2 = new Sentinel<String>();
		n1 = new Node<String>("abc", sentinel2, sentinel2);
		n2 = new Node<String>("bcd", n1, sentinel2);
		n3 = new Node<String>("cde", n2, sentinel2);
		n4 = new Node<String>("def", n3, sentinel2);
		
		deque2 = new Deque<String>(sentinel2);
		
		
		sentinel3 = new Sentinel<String>();
		
		n11 = new Node<String>("1", sentinel3, sentinel3);
		n12 = new Node<String>("5", n11, sentinel3);
		n13 = new Node<String>("3", n12, sentinel3);
		n14 = new Node<String>("2", n13, sentinel3);
		n15 = new Node<String>("25", n14, sentinel3);
		n16 = new Node<String>("19", n15, sentinel3);
		n17 = new Node<String>("16", n16, sentinel3);
		n18 = new Node<String>("21", n17, sentinel3);
		
		
		deque3 = new Deque<String>(sentinel3);
		
	}
	
	void testInitLists(Tester t) {
		init();
		
		t.checkExpect(deque1.header, new Sentinel<String>());
		t.checkExpect(deque2.header.next, n1);
		t.checkExpect(deque2.header.prev, n4);
		
		t.checkExpect(deque3.header.next.next, n12);
		t.checkExpect(deque3.header.next, n11);
		t.checkExpect(deque3.header.prev, n18);
	}
	
	void testSize(Tester t) {
		init();
		
		t.checkExpect(deque1.size(), 0);
		t.checkExpect(deque2.size(), 4);
		t.checkExpect(deque3.size(), 8);
	}
	
	void testAddAtHead(Tester t) {
		init();
		
		deque1.addAtHead("a");
		t.checkExpect(deque1.size(), 1);
		t.checkExpect(deque1.header.next, new Node<String>("a", deque1.header, deque1.header));

				
		deque2.addAtHead("aaa");
		t.checkExpect(deque2.header.next.getData(), "aaa");
		t.checkExpect(deque2.header.next.next.getData(), "abc");
		t.checkExpect(deque2.header.prev.getData(), "def");
		
		deque2.addAtHead("bbb");
		t.checkExpect(deque2.header.next.getData(), "bbb");
		t.checkExpect(deque2.header.next.next.getData(), "aaa");
		t.checkExpect(deque2.header.next.next.next.getData(), "abc");
		t.checkExpect(deque2.header.prev.getData(), "def");
		t.checkExpect(deque2.size(), 6);
		
	}
	
	void testAddAtTail(Tester t) {
		init();
		
		deque1.addAtTail("b");
		t.checkExpect(deque1.size(), 1);
		t.checkExpect(deque1.header.next, new Node<String>("b", deque1.header, deque1.header));
		t.checkExpect(deque1.header.prev, new Node<String>("b", deque1.header, deque1.header));

				
		deque2.addAtTail("ccc");
		t.checkExpect(deque2.header.next.getData(), "abc");
		t.checkExpect(deque2.header.next.next.getData(), "bcd");
		t.checkExpect(deque2.header.prev.getData(), "ccc");
		t.checkExpect(deque2.header.prev.prev.getData(), "def");
		
		deque2.addAtTail("ddd");
		t.checkExpect(deque2.header.next.getData(), "abc");
		t.checkExpect(deque2.header.prev.getData(), "ddd");
		t.checkExpect(deque2.header.prev.prev.getData(), "ccc");
		t.checkExpect(deque2.header.prev.prev.prev.getData(), "def");
		t.checkExpect(deque2.size(), 6);
		
	}
	
	void testRemoveFromHead(Tester t) {
		init();
		
//		t.checkException("Cannot remove from an empty list.", 
//				new RuntimeException("Cannot remove from an empty list."),
//				this.deque1,
//				"removeFromHead");
		
		t.checkExpect(deque1.size(), 0);
		deque1.addAtHead("b");
		t.checkExpect(deque1.removeFromHead(), new Node<String>("b"));
		t.checkExpect(deque1.size(), 0);

				
		t.checkExpect(deque2.removeFromHead(), new Node<String>("abc"));
		t.checkExpect(deque2.removeFromHead(), new Node<String>("bcd"));
		t.checkExpect(deque2.removeFromHead(), new Node<String>("cde"));
		t.checkExpect(deque2.removeFromHead(), new Node<String>("def"));
		
//		t.checkException("Cannot remove from an empty list.", 
//				new RuntimeException("Cannot remove from an empty list."),
//				this.deque2,
//				"removeFromHead");
		t.checkExpect(deque2.header.next, deque2.header);

	}
	
	void testRemoveFromTail(Tester t) {
		init();
		
//		t.checkException("Cannot remove from an empty list.", 
//				new RuntimeException("Cannot remove from an empty list."),
//				this.deque1,
//				"removeFromTail");
		
		t.checkExpect(deque1.size(), 0);
		deque1.addAtHead("b");
		t.checkExpect(deque1.removeFromTail(), new Node<String>("b"));
		t.checkExpect(deque1.size(), 0);
		
		t.checkExpect(deque1.size(), 0);
		deque1.addAtTail("b");
		t.checkExpect(deque1.removeFromTail(), new Node<String>("b"));
		t.checkExpect(deque1.size(), 0);

				
		t.checkExpect(deque2.removeFromTail(), new Node<String>("def"));
		t.checkExpect(deque2.removeFromTail(), new Node<String>("cde"));
		t.checkExpect(deque2.removeFromTail(), new Node<String>("bcd"));
		t.checkExpect(deque2.removeFromTail(), new Node<String>("abc"));
		
//		t.checkException("Cannot remove from an empty list.", 
//				new RuntimeException("Cannot remove from an empty list."),
//				this.deque2,
//				"removeFromTail");
		t.checkExpect(deque2.header.next, deque2.header);

	}
	
	void testFind(Tester t) {
		init();
		deque1.removeNode(deque1.header);
		t.checkExpect(deque3.find(new LengthGreaterThanOne()), new Node<String>("25"));
		t.checkExpect(deque2.find(new LengthGreaterThanOne()), new Node<String>("abc"));
		t.checkExpect(deque1.find(new LengthGreaterThanOne()), deque1.header);

	}
	
	void testRemoveAny(Tester t) {
		init();
		
		deque2.removeNode(n2);
		t.checkExpect(deque2.size(), 3);
		deque2.removeNode(n3);
		t.checkExpect(deque2.size(), 2);
		
		deque1.removeNode(deque1.header);
		t.checkExpect(deque1.header, deque1.header);
		

	}
}

