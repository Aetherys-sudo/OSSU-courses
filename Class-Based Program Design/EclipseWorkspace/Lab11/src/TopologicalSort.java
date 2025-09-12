import java.util.ArrayList;
import tester.*;

class Curriculum {
	ArrayList<Course> courses;
	
	Curriculum() { 
		this.courses = new ArrayList<Course>(); 
	
	}
	// EFFECT: adds another course to the set of known courses
	void addCourse(Course c) { 
		this.courses.add(c); 
	}
	
	// add methods here...
	boolean comesAfterPrereqs(ArrayList<Course> schedule, Course c) {
		return c.comesAfterChildren(schedule) && comeAfterTheirChildren(schedule, c.prereqs);
	}
	
	boolean comeAfterTheirChildren(ArrayList<Course> schedule, ArrayList<Course> prereqs) {
		for (int i = 0; i < prereqs.size(); i ++) {
			if (comesAfterPrereqs(schedule, prereqs.get(i))) {
				continue;
			} else {
				return false;
			}
			
		}
		
		return true;
	}
	
	ArrayList<Course> topSort(ArrayList<Course> input) {
		ArrayList<Course> result = new ArrayList<Course>();
		for (int i = 0; i < input.size(); i ++) {
			input.get(i).process(result);
		}
		
		return result;
	}
	
	boolean validSchedule(ArrayList<Course> input) {
		for (int i = 0; i < input.size(); i ++) {
			if (comesAfterPrereqs(topSort(input), input.get(i))) {
				continue;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	void print(ArrayList<Course> input) {
		for (int i = 0; i < input.size(); i ++) {
			System.out.println(Integer.toString(i + 1) + ": " + input.get(i).name);
			
		}
	}
}

class Course {
	String name;
	ArrayList<Course> prereqs;
	
	Course(String name) { 
		this.name = name; this.prereqs = new ArrayList<Course>(); 
	}
	
	// EFFECT: adds a course as a prereq to this one
	void addPrereq(Course c) { 
		this.prereqs.add(c); 
	}
	// add methods here
	boolean comesAfterChildren(ArrayList<Course> schedule) {
		
		int idx = schedule.indexOf(this);
		ArrayList<Course> first = new ArrayList<Course>();
		ArrayList<Course> rest = new ArrayList<Course>();
		
		if (idx == -1) {
			return false;
		}
		
		for (int i = 0; i < idx; i ++) {
			first.add(schedule.get(i));
		}
		
		for (int i = idx; i < schedule.size(); i ++) {
			rest.add(schedule.get(i));
		}
		
		for (int i = 0; i < this.prereqs.size(); i ++) {
			if (first.contains(this.prereqs.get(i)) && !rest.contains(this.prereqs.get(i))) {
				continue;
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	void process(ArrayList<Course> processed) {
		if (processed.contains(this)) {
			return;
		} else {
			processPrereqs(this.prereqs, processed);
			processed.add(this);
		}
	}
	
	void processPrereqs(ArrayList<Course> prereqs, ArrayList<Course> processed) {
		for (int i = 0; i < prereqs.size(); i ++) {
			prereqs.get(i).process(processed);
		}
		
		return;
	}
}

class ExamplesCurriculum {
	Curriculum curr = new Curriculum();
	Curriculum possibleRes = new Curriculum();
	Curriculum wrongRes1 = new Curriculum();
	Curriculum wrongRes2 = new Curriculum();
	
	Course c1 = new Course("Algorithms and Data");
	Course c2 = new Course("Fundamentals 2");
	Course c3 = new Course("Compilers");
	Course c4 = new Course("Programming Languages");
	Course c5 = new Course("Computer Systems");
	Course c6 = new Course("Database Design");
	Course c7 = new Course("Fundamentals 1");
	Course c8 = new Course("Large-Scale Parallel Data Processing");
	Course c9 = new Course("Object-Oriented Design");
	Course c10 = new Course("Theory of Computation");
	
	void initData() {
		curr = new Curriculum();
		possibleRes = new Curriculum();
		wrongRes1 = new Curriculum();
		wrongRes2 = new Curriculum();
		
		c1.addPrereq(c2);
		
		c3.addPrereq(c4);
		
		c5.addPrereq(c2);
		
		c6.addPrereq(c7);
		
		c2.addPrereq(c7);
		
		c8.addPrereq(c1);
		c8.addPrereq(c5);
		
		c9.addPrereq(c2);
		
		c4.addPrereq(c10);
		c4.addPrereq(c9);
		
		c10.addPrereq(c2);
		

		possibleRes.addCourse(c7);
		possibleRes.addCourse(c2);
		possibleRes.addCourse(c1);
		possibleRes.addCourse(c3);
		possibleRes.addCourse(c10);
		possibleRes.addCourse(c9);
		possibleRes.addCourse(c4);
		possibleRes.addCourse(c5);
		possibleRes.addCourse(c6);
		possibleRes.addCourse(c8);
		
		wrongRes1.addCourse(c7);
		wrongRes1.addCourse(c1);
		wrongRes1.addCourse(c1);
		wrongRes1.addCourse(c5);
		wrongRes1.addCourse(c6);
		wrongRes1.addCourse(c8);
		wrongRes1.addCourse(c9);
		wrongRes1.addCourse(c10);
		wrongRes1.addCourse(c4);
		wrongRes1.addCourse(c3);
		
		wrongRes2.addCourse(c7);
		wrongRes2.addCourse(c4);
		wrongRes2.addCourse(c1);
		wrongRes2.addCourse(c5);
		wrongRes2.addCourse(c6);
		wrongRes2.addCourse(c8);
		wrongRes2.addCourse(c9);
		wrongRes2.addCourse(c10);
		wrongRes2.addCourse(c2);
		wrongRes2.addCourse(c3);
		
	}
	
	
	void testPrereqBefore(Tester t) {
		initData();
		t.checkExpect(curr.comesAfterPrereqs(possibleRes.courses, c7), true);
		t.checkExpect(curr.comesAfterPrereqs(possibleRes.courses, c4), true);
		t.checkExpect(curr.comesAfterPrereqs(possibleRes.courses, c9), true);
		t.checkExpect(curr.comesAfterPrereqs(wrongRes1.courses, c2), false);
		t.checkExpect(curr.comesAfterPrereqs(wrongRes1.courses, c4), false);
		t.checkExpect(curr.comesAfterPrereqs(wrongRes2.courses, c4), false);
		t.checkExpect(curr.comesAfterPrereqs(wrongRes2.courses, c2), true);
	}
	
	void testTopSort(Tester t) {
		initData();
		t.checkExpect(new Curriculum().validSchedule(possibleRes.courses), true);
		t.checkExpect(new Curriculum().validSchedule(wrongRes1.courses), true);
		t.checkExpect(new Curriculum().validSchedule(wrongRes2.courses), true);
		
		possibleRes.print(possibleRes.topSort(possibleRes.courses));
		wrongRes1.print(wrongRes1.topSort(wrongRes1.courses));
		wrongRes2.print(wrongRes2.topSort(wrongRes2.courses));
	}
}