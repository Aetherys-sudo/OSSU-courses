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
