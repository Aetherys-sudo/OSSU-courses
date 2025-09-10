import java.util.ArrayList;
import tester.*;

class Utils {
  <T> ArrayList<T> reverse(ArrayList<T> source) { return new ArrayList<T>(); }
}

class Runner{
	int age;
	String name;
 
	Runner(int age, String name){
		this.age = age;
		this.name = name;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Runner)) {
			return false;
		}
		
		Runner that = (Runner)o;
		return this.age == that.age &&
				this.name.equals(that.name);
		
	}
	
	public int hashCode() {
		return this.name.hashCode() * 10000 + this.age;
	}
}

class Stack<T> {
	Deque<T> contents;
	
	Stack() {
		this.contents = new Deque<T>();
	}
	
	void push(T item) {
		this.contents.addAtHead(item);
	}
	
	boolean isEmpty() {
		return this.contents.size() == 0;
	}
	
	T pop() {
		return this.contents.removeFromHead().getData();
	}
}

class StringCreator {
	String field;
	Stack<String> opList;
	
	StringCreator() {
		this.field = "";
		this.opList = new Stack<String>();
	}
	
	void add(Character c) {
		this.opList.push(this.field);
		this.field = this.field + c;
	}
	
	void remove() {
		this.opList.push(this.field);
		String sub = this.field.substring(0, this.field.length() - 1);
		this.field = sub;
	}
	
	String getString() {
		return this.field;
	}
	
	void undo() {
		this.field = this.opList.pop();
	}
}

class ExamplesStringCreator {
	StringCreator strc = new StringCreator();
	
	void initData() {
		strc.field = "";
	}
	
	void testAdd(Tester t) {
		initData();
		strc.add('a');
		t.checkExpect(strc.getString(), "a");
		strc.add('b');
		t.checkExpect(strc.getString(), "ab");
		strc.add('c');
		t.checkExpect(strc.getString(), "abc");
		strc.add('d');
		t.checkExpect(strc.getString(), "abcd");
	}
	
	
	
	void testRemove(Tester t) {
		initData();
		strc.add('a');
		t.checkExpect(strc.getString(), "a");
		strc.add('b');
		t.checkExpect(strc.getString(), "ab");
		strc.add('c');
		t.checkExpect(strc.getString(), "abc");
		strc.add('d');
		t.checkExpect(strc.getString(), "abcd");
		strc.remove();
		t.checkExpect(strc.getString(), "abc");
		strc.remove();
		t.checkExpect(strc.getString(), "ab");
		strc.add('z');
		t.checkExpect(strc.getString(), "abz");
		strc.add('x');
		t.checkExpect(strc.getString(), "abzx");
	}
	
	void testRun(Tester t) {
	    initData();
	    t.checkExpect(strc.getString(),"");
	    strc.add('c');
	    strc.add('d');
	    t.checkExpect(strc.getString(),"cd");
	    strc.add('e');
	    t.checkExpect(strc.getString(),"cde");
	    strc.remove();
	    strc.remove();
	    t.checkExpect(strc.getString(),"c");
	    strc.undo(); //undoes the removal of 'd'
	    t.checkExpect(strc.getString(),"cd");
	    strc.undo(); //undoes the removal of 'e'
	    strc.undo(); //undoes the addition of 'e'
	    t.checkExpect(strc.getString(),"cd");
	    strc.add('a');
	    t.checkExpect(strc.getString(),"cda");
	    strc.undo(); //undoes the addition of 'a'
	    strc.undo(); //undoes the addition of 'd'
	    strc.undo(); //undoes the addition of 'c'
	    t.checkExpect(strc.getString(),"");
	    strc.undo(); //no effect, there is nothing to undo
	}
}



class ListOfListIterator<T> implements java.util.Iterator<T> {
	ListOfLists<T> arr;
	int currMainIdx;
	int currListIdx;
	
	ListOfListIterator(ListOfLists<T> arr) {
		this.arr = arr;
		this.currMainIdx = 0;
		this.currListIdx = 0;
	}
	
	public boolean hasNext() {
		if (this.currMainIdx < this.arr.size()) {
			if (this.currListIdx < this.arr.get(this.currMainIdx).size()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public T next() {
		T answer = this.arr.get(this.currMainIdx).get(currListIdx);
		this.currListIdx = this.currListIdx + 1;
		if (this.currListIdx >= this.arr.get(currMainIdx).size()) {
			this.currMainIdx += 1;
		}
		return answer;
	}
	
	public void remove() {
		throw new UnsupportedOperationException("Don't do this!");
	}
}


class ListOfLists<T> implements java.lang.Iterable<T> {
	ArrayList<ArrayList<T>> arr;

	ListOfLists() {
		this.arr = new ArrayList<ArrayList<T>>();

	}
	
	void addNewList() {
		arr.add(new ArrayList<T>());
	}
	
	void add(int index, T object) {
		if (index < 0 || index >= this.arr.size()) {
			throw new IndexOutOfBoundsException();
		}
		this.arr.get(index).add(object);
	}
	
	ArrayList<T> get(int index) {
		if (index < 0 || index >= this.arr.size()) {
			throw new IndexOutOfBoundsException();
		}
		return this.arr.get(index);
	}
	
	int size() {
		return this.arr.size();
	}
	
	public java.util.Iterator<T> iterator() {
		return new ListOfListIterator<T>(this);
	}
}

class ExamplesListOfLists {
	
	void testListOfLists(Tester t) {
	    ListOfLists<Integer> lol = new ListOfLists<Integer>();
	    //add 3 lists
	    lol.addNewList();
	    lol.addNewList();
	    lol.addNewList();
	 
	    //add elements 1,2,3 in first list
	    lol.add(0,1);
	    lol.add(0,2);
	    lol.add(0,3);
	 
	    //add elements 4,5,6 in second list
	    lol.add(1,4);
	    lol.add(1,5);
	    lol.add(1,6);
	 
	    //add elements 7,8,9 in third list
	    lol.add(2,7);
	    lol.add(2,8);
	    lol.add(2,9);
	 
	    //iterator should return elements in order 1,2,3,4,5,6,7,8,9
	    int number = 1;
	    for (Integer num : lol) {
	        t.checkExpect(num,number);
	        number = number + 1;
	    }
	}
}
