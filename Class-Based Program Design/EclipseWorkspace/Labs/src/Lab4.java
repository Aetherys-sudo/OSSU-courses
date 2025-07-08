import tester.*;

//interface IBook {
//	int daysOverdue(int today);
//	
//	boolean isOverdue(int today);
//	
//	int computeFine(int today);
//}
//
//abstract class ABook implements IBook {
//	String title;
//	int dayTaken;
//	
//	ABook(String title, int dayTaken) {
//		this.title = title;
//		this.dayTaken = dayTaken;
//	}
//	
//	public int daysOverdue(int today) {
//		return this.dayTaken + 14 - today;
//	}
//	
//	public boolean isOverdue(int today) {
//		return (this.daysOverdue(today) < 0);
//	}
//	
//	public int computeFine(int today) {
//		if (this.isOverdue(today)) {
//			return Math.abs(this.daysOverdue(today)) * 10;
//		} else {
//			return 0;
//		}
//	}
//	
//}
//
//class Book extends ABook {
//	String author;
//	
//	Book(String title, int dayTaken, String author) {
//		super(title, dayTaken);
//		this.author = author;
//	}
//	
//}
//
//class RefBook extends ABook {
//	RefBook(String title, int dayTaken) {
//		super(title, dayTaken);
//	}
//	
//	public int daysOverdue(int today) {
//		return this.dayTaken + 2 - today;
//	}
//	
//}
//
//class AudioBook extends ABook {
//	String author;
//	
//	AudioBook(String title, int dayTaken, String author) {
//		super(title, dayTaken);
//		this.author = author;
//		
//	}
//	
//	public int computeFine(int today) {
//		if (this.isOverdue(today)) {
//			return Math.abs(this.daysOverdue(today)) * 20;
//		} else {
//			return 0;
//		}
//	}
//}
//
//class ExamplesLibrary {
//	IBook b1 = new Book("b1", 10, "a1");
//	IBook b2 = new RefBook("b2", 10);
//	IBook b3 = new AudioBook("b3", 10, "a2");
//	IBook b4 = new Book("b4", 30, "a3");
//	IBook b5 = new RefBook("b5", 30);
//	IBook b6 = new AudioBook("b6", 30, "a4");
//	
//	boolean testDaysOverdue(Tester t) {
//		return t.checkExpect(b1.daysOverdue(14), 10) &&
//				t.checkExpect(b3.daysOverdue(24), 0) &&
//				t.checkExpect(b1.daysOverdue(25), -1) &&
//				t.checkExpect(b2.daysOverdue(14), -2) &&
//				t.checkExpect(b2.daysOverdue(11), 1) &&
//				t.checkExpect(b4.daysOverdue(11), 33);
//				
//	}
//	
//	boolean testIsOverdue(Tester t) {
//		return t.checkExpect(b1.isOverdue(14), false) &&
//				t.checkExpect(b3.isOverdue(24), false) &&
//				t.checkExpect(b1.isOverdue(25), true) &&
//				t.checkExpect(b2.isOverdue(14), true) &&
//				t.checkExpect(b2.isOverdue(11), false) &&
//				t.checkExpect(b4.isOverdue(11), false);		
//	}
//	
//	boolean testComputeFine(Tester t) {
//		return t.checkExpect(b1.computeFine(14), 0) &&
//				t.checkExpect(b3.computeFine(24), 0) &&
//				t.checkExpect(b1.computeFine(25), 10) &&
//				t.checkExpect(b2.computeFine(14), 20) &&
//				t.checkExpect(b2.computeFine(11), 0) &&
//				t.checkExpect(b4.computeFine(11), 0);
//	}
//	
//	
//}
//
//
////---------------------------------------
//
//interface IMaybeInt {
//	
//}
//
//class HasInt implements IMaybeInt {
//	int value;
//	
//	HasInt(int value) {
//		this.value = value;
//	}
//	
//}
//
//class NoValue implements IMaybeInt {
//	NoValue() { };
//}
//
//class ExamplesMaybeInt {
//	IMaybeInt i1 = new HasInt(3);
//	IMaybeInt i2 = new HasInt(10);
//	IMaybeInt i3 = new HasInt(20);
//	IMaybeInt mt1 = new NoValue();
//	IMaybeInt mt2 = new NoValue();
//}
//
////------------------------------------
//
//interface ILoInt {
//	IMaybeInt longestSublist();
//	
//	IMaybeInt countInts(int counter, int topValue, int maxCount, int maxVal);
//}
//
//class ConsLoInt implements ILoInt {
//	int first;
//	ILoInt next;
//	
//	ConsLoInt(int first, ILoInt next) {
//		this.first = first;
//		this.next = next;
//	}
//	
//	public IMaybeInt longestSublist() {
//		return this.next.countInts(1, this.first, 1, this.first);
//	}
//	
//	public IMaybeInt countInts(int counter, int value, int maxCount, int maxVal) {
//		if (this.first == value) {
//			if (counter + 1 > maxCount) {
//				return this.next.countInts(counter + 1, value, counter + 1, value);
//			} else {
//				return this.next.countInts(counter + 1, value, maxCount, maxVal);
//			}
//		} else {
//			
//			return this.next.countInts(1, this.first, maxCount, maxVal);
//		}
//	}
//}
//
//class MtLoInt implements ILoInt {
//	MtLoInt() { };
//	
//	public IMaybeInt longestSublist() {
//		return new NoValue();
//	}
//	
//	public IMaybeInt countInts(int counter, int topValue, int maxCount, int maxVal) {
//		return new HasInt(maxVal);
//	}
//}
//
//class ExamplesLoInt {
//	ILoInt l1 = new MtLoInt();
//	ILoInt l2 = new ConsLoInt(5, new MtLoInt());
//	ILoInt l3 = new ConsLoInt(5, new ConsLoInt(6, new MtLoInt()));
//	ILoInt l4 = new ConsLoInt(5, new ConsLoInt(5, new MtLoInt()));
//	ILoInt l5 = new ConsLoInt(5, new ConsLoInt(6, new ConsLoInt(6, new ConsLoInt(5, new ConsLoInt(5, new ConsLoInt(3, new ConsLoInt(5, new MtLoInt())))))));
//	ILoInt l6 = new ConsLoInt(1, new ConsLoInt(2, new ConsLoInt(3, new ConsLoInt(4, new ConsLoInt(5, new ConsLoInt(6, new ConsLoInt(7, new MtLoInt())))))));
//	ILoInt l7 = new ConsLoInt(1, new ConsLoInt(2, new ConsLoInt(3, new ConsLoInt(4, new ConsLoInt(5, new ConsLoInt(7, new ConsLoInt(7, new MtLoInt())))))));
//	ILoInt l8 = new ConsLoInt(1, new ConsLoInt(1, new ConsLoInt(1, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(2, new MtLoInt())))))));
//	ILoInt l9 = new ConsLoInt(1, new ConsLoInt(1, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(3, new ConsLoInt(3, new ConsLoInt(3, new MtLoInt())))))));
//	ILoInt l10 = new ConsLoInt(1, new ConsLoInt(1, new ConsLoInt(1, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(2, new ConsLoInt(3, new ConsLoInt(3, new ConsLoInt(3, new ConsLoInt(3, new ConsLoInt(3, new MtLoInt()))))))))))));
//	
//	boolean testCountInts(Tester t) {
//		return 
//				t.checkExpect(l1.longestSublist(), new NoValue()) &&
//				t.checkExpect(l2.longestSublist(), new HasInt(5)) &&
//				t.checkExpect(l3.longestSublist(), new HasInt(5)) &&
//				t.checkExpect(l4.longestSublist(), new HasInt(5)) &&
//				t.checkExpect(l5.longestSublist(), new HasInt(6)) &&
//				t.checkExpect(l6.longestSublist(), new HasInt(1)) &&
//				t.checkExpect(l7.longestSublist(), new HasInt(7)) &&
//				t.checkExpect(l8.longestSublist(), new HasInt(2)) &&
//				t.checkExpect(l9.longestSublist(), new HasInt(3)) &&
//				t.checkExpect(l10.longestSublist(), new HasInt(3));
//				
//	}
//	
//}


//-----------------------------------

interface ILoTasks {
	ILoTasks getDoneTasks();
	
	ILoTasks getDoneTasksHelper(ILoTasks done);
	
	boolean hasEl(Task id);
}

class MtLoTask implements ILoTasks {
	MtLoTask() { };
	
	public ILoTasks getDoneTasks() {
		return new MtLoTask();
	}
	
	public ILoTasks getDoneTasksHelper(ILoTasks done) {
		return done;
	}
	
	public boolean hasEl(Task id) {
		return false;
	}
}

class ConsLoTask implements ILoTasks {
	Task first;
	ILoTasks next;
	
	ConsLoTask(Task first, ILoTasks next) {
		this.first = first;
		this.next = next;
	}
	
	public ILoTasks getDoneTasks() {
		return this.getDoneTasksHelper(new MtLoTask());
	}
	
	public ILoTasks getDoneTasksHelper(ILoTasks done) {
		return this.next.getDoneTasksHelper(this.first.getDoneTasksHelper(done));
	}
	
	public boolean hasEl(Task id) {
		if (this.first.equals(id)) {
			return true;
		} else {
			return this.next.hasEl(id);
		}
	}
}

class Task {
	int id;
	ILoTasks prerequisites;
	
	Task(int id, ILoTasks prerequisites) {
		this.id = id;
		this.prerequisites = prerequisites;
	}
	
	public ILoTasks getDoneTasks() {
		return this.getDoneTasksHelper(new MtLoTask());
	}
	
	public ILoTasks getDoneTasksHelper(ILoTasks done) {
		if (this.isCompletable()) {
			return this.prerequisites.getDoneTasksHelper(new ConsLoTask(this, done));
		} else {
			return this.prerequisites.getDoneTasksHelper(done);
		}
	}
	
	boolean isCompletable() {
		return !this.prerequisites.hasEl(this);
	}
	
	boolean equals(Task el) {
		return this.id == el.id;
	}

}

class ExamplesTasks {
	Task A = new Task(1, new MtLoTask());
	Task B = new Task(2, new MtLoTask());
	Task C = new Task(3, new MtLoTask());
	Task D = new Task(4, new MtLoTask());
	
	Task A1 = new Task(11, new ConsLoTask(A, new MtLoTask()));
	Task A2 = new Task(12, new ConsLoTask(A, new ConsLoTask(B, new MtLoTask())));
	Task A3 = new Task(13, new ConsLoTask(A, new ConsLoTask(B, new ConsLoTask(C, new ConsLoTask(D, new MtLoTask())))));
	Task A4 = new Task(14, new ConsLoTask(A, new ConsLoTask(A, new MtLoTask())));
	
	ILoTasks l1 = new MtLoTask();
	ILoTasks l2 = new ConsLoTask(A, new MtLoTask());
	ILoTasks l3 = new ConsLoTask(A, new ConsLoTask(B, new MtLoTask()));
	ILoTasks l4 = new ConsLoTask(A, new ConsLoTask(A3, new MtLoTask()));
	
	Task A5 = new Task(3, new ConsLoTask(A, new ConsLoTask(C, new MtLoTask())));
	

	boolean testCompletableTasks(Tester t) {
		return t.checkExpect(A.getDoneTasks(), new ConsLoTask(A, new MtLoTask())) &&
				t.checkExpect(B.getDoneTasks(), new ConsLoTask(B, new MtLoTask())) &&
				t.checkExpect(A1.getDoneTasks(), new ConsLoTask(A, new ConsLoTask(A1, new MtLoTask()))) &&
				t.checkExpect(A2.getDoneTasks(), new ConsLoTask(B, new ConsLoTask(A, new ConsLoTask(A2, new MtLoTask())))) &&
				t.checkExpect(A3.getDoneTasks(), new ConsLoTask(D, new ConsLoTask(C, new ConsLoTask(B, new ConsLoTask(A, new ConsLoTask(A3, new MtLoTask()))))));
		
	}
	
	boolean testCompletableLoTasks(Tester t) {
		return 
				t.checkExpect(l1.getDoneTasks(), new MtLoTask()) &&
				t.checkExpect(l2.getDoneTasks(), new ConsLoTask(A, new MtLoTask())) &&
				t.checkExpect(l3.getDoneTasks(), new ConsLoTask(B, new ConsLoTask(A, new MtLoTask()))) &&
				t.checkExpect(l4.getDoneTasks(), new ConsLoTask(D, new ConsLoTask(C, new ConsLoTask(B, new ConsLoTask(A, new ConsLoTask(A3, new ConsLoTask(A, new MtLoTask())))))));
	}
	
}