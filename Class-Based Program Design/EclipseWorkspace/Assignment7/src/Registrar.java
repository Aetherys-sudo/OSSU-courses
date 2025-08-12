import tester.*;

interface IPred<T> {
	Boolean apply(T arg);
}

class HasStudent implements IPred<Course> {
	Student s;
	
	HasStudent(Student s) {
		this.s = s;
	}
	
	public Boolean apply(Course c) {
		return c.students.contains(this.s);
	}
}

interface IList<T> {
	boolean contains(T data);
	
	boolean hasCommonElements(IList<T> lst2);
	
	<U> IList<T> filter(IPred<T> func);
	
	int len();
}

class MtList<T> implements IList<T> {
	MtList() { }
	
	public boolean contains(T data) {
		return false;
	}
	
	public boolean hasCommonElements(IList<T> lst2) {
		return false;
	}
	
	public int len() {
		return 0;
	}
	
	public <U> IList<T> filter(IPred<T> func) {
		return new MtList<T>();
	}
}

class ConsList<T> implements IList<T> {
	T first;
	IList<T> rest;
	
	ConsList(T first, IList<T> rest) {
		this.first = first;
		this.rest = rest;
	}
	
	public boolean contains(T data) {
		return this.first.equals(data) || this.rest.contains(data);
	}
	
	public boolean hasCommonElements(IList<T> lst2) {
		return lst2.contains(this.first) || this.rest.hasCommonElements(lst2);
	}
	
	public int len() {
		return 1 + this.rest.len();
	}
	
	public <U> IList<T> filter(IPred<T> func) {
		if (func.apply(this.first)) {
			return new ConsList<T>(this.first, this.rest.filter(func));
		} else {
			return this.rest.filter(func);
		}
	}
}


class Course {
	String name;
	Instructor prof;
	IList<Student> students;
	
	Course(String name, Instructor prof) {
		if (prof.name.compareTo("") == 0) {
			throw new IllegalArgumentException("Cannot have a blank name for an instructor.");
		} else {
			this.prof = prof;
			this.prof.addCourse(this);
		}
		this.name = name;
		this.students = new MtList<Student>();
	}
	
	void enroll(Student s) {
		if (this.students.contains(s)) {
			return;
		} else {
			this.students = new ConsList<Student>(s, this.students);
		}
		
	}
}

class Instructor {
	String name;
	IList<Course> courses;
	
	Instructor(String name) {
		this.name = name;
		this.courses = new MtList<Course>();
	}
	
	void addCourse(Course c) {
		if (this.courses.contains(c)) {
			return;
		} else {
			this.courses = new ConsList<Course>(c, this.courses);
		}
	}
	
	boolean dejavu(Student s) {
		return this.courses.filter(new HasStudent(s)).len() > 1;
	}
}

class Student {
	String name;
	int id;
	IList<Course> courses;
	
	Student(String name, int id) {
		this.name = name;
		this.id = id;
		this.courses = new MtList<Course>();
	}
	
	void enroll(Course c) {
		if (this.courses.contains(c)) {
			return;
		} else {
			this.courses = new ConsList<Course>(c, this.courses);
			c.enroll(this);
		}
	}
	
	boolean classmates(Student s) {
		return this.courses.hasCommonElements(s.courses);
	}
	
	
	
}

class ExamplesRegistrar {
	Student s1 = new Student("luca", 1);
	Student s2 = new Student("timi", 2);
	Student s3 = new Student("sekiro", 3);
	Student s4 = new Student("onix", 4);
	Student s5 = new Student("hogheras", 5);
	
	Instructor i1 = new Instructor("Master Patto");
	Instructor i2 = new Instructor("Sensei Plic");
	
	Course c1 = new Course("Pisiceala", i1);
	Course c2 = new Course("Mataiala", i1);
	Course c3 = new Course("Mieuneala", i2);
	Course c4 = new Course("Pliculeala", i2);
	Course c5 = new Course("No enrollment", i2);
	
	void initData() {
		s1 = new Student("luca", 1);
		s2 = new Student("timi", 2);
		s3 = new Student("sekiro", 3);
		s4 = new Student("onix", 4);
		s5 = new Student("hogheras", 5);
		
		i1 = new Instructor("Master Patto");
		i2 = new Instructor("Sensei Plic");
		
		c1 = new Course("Pisiceala", i1);
		c2 = new Course("Mataiala", i1);
		c3 = new Course("Mieuneala", i2);
		c4 = new Course("Pliculeala", i2);
		c5 = new Course("No enrollment", i2);
		
		s2.enroll(c1);
		s2.enroll(c2);
		
		s3.enroll(c1);
		s3.enroll(c2);
		s3.enroll(c3);
		s3.enroll(c4);
		
		s4.enroll(c3);
		
		s5.enroll(c3);
		s5.enroll(c4);
		
	}
	
	void testEnroll(Tester t) {
		initData();
		
		t.checkExpect(s3.courses, new ConsList<Course>(c4, new ConsList<Course>(c3, new ConsList<Course>(c2, new ConsList<Course>(c1, new MtList<Course>())))));
		t.checkExpect(s1.courses, new MtList<Course>());
		t.checkExpect(s2.courses, new ConsList<Course>(c2, new ConsList<Course>(c1, new MtList<Course>())));
		
		t.checkExpect(c1.students, new ConsList<Student>(s3, new ConsList<Student>(s2, new MtList<Student>())));
		t.checkExpect(c4.students, new ConsList<Student>(s5, new ConsList<Student>(s3, new MtList<Student>())));
		t.checkExpect(c5.students, new MtList<Student>());
		
		t.checkExpect(i1.courses, new ConsList<Course>(c2, new ConsList<Course>(c1, new MtList<Course>())));
		t.checkExpect(i2.courses, new ConsList<Course>(c5, new ConsList<Course>(c4, new ConsList<Course>(c3, new MtList<Course>()))));
		
	}
	
	void testClassmates(Tester t) {
		initData();
		
		t.checkExpect(s1.classmates(s2), false);
		t.checkExpect(s2.classmates(s1), false);
		t.checkExpect(s2.classmates(s4), false);
		t.checkExpect(s2.classmates(s5), false);
		t.checkExpect(s2.classmates(s3), true);
		t.checkExpect(s3.classmates(s2), true);
		t.checkExpect(s4.classmates(s5), true);
	}
	
	void testDejavu(Tester t) {
		initData();
		
		t.checkExpect(i1.dejavu(s2), true);
		t.checkExpect(i1.dejavu(s1), false);
		t.checkExpect(i1.dejavu(s3), true);
		t.checkExpect(i2.dejavu(s5), true);
		t.checkExpect(i2.dejavu(s4), false);
	}
	

}