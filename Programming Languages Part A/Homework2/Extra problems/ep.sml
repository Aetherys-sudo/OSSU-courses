(* PROBLEMS 1-4 *)
type student_id = int;
type grade = int (* must be in 0 to 100 range *);
type final_grade = { id : student_id, grade : grade option };
datatype pass_fail = pass | fail;

(* final_grade -> pass_fail *)
(* takes in a grade and some identification for a student and returns pass if their grade is >= 75, fail otherwise *)
fun pass_or_fail{grade=grade, id=id} =
    case grade of
	NONE => fail
      | SOME y => if y >= 75
		  then pass
		  else fail;

(* final_grade -> bool *)
(* takes in a grade and some identification for a student and returns true if their grade is >= 75, false otherwise *)
fun has_passed{grade=grade, id=id} =
    pass_or_fail{grade=grade, id=id} = pass;
	
	     
(* final_grade list -> int *)
(* takes in a list of final grades and returns the number of passing grades *)
fun number_passed(lst) =
    case lst of
	[] => 0
      | x :: xs' => if has_passed(x)
		    then 1 + number_passed(xs')
		    else number_passed(xs');

				      
(* (pass_fail * final_grade) list -> int *)
(* take in a list of pairs (i.e. (pass, x)) and counts it if has_passed(x) is false *)
fun number_misgraded(lst) =
    case lst of
	[] => 0
      | (x, y) :: xs' => 
	case has_passed(y) of
	    true => if x = fail
		    then 1 + number_misgraded(xs')
		    else number_misgraded(xs')
	  | false => if x = pass
		     then 1 + number_misgraded(xs')
		     else number_misgraded(xs');
					  

(* PROBLEMS 5-7 *)
datatype 'a tree = leaf 
                 | node of { value : 'a, left : 'a tree, right : 'a tree };
datatype flag = leave_me_alone | prune_me;

(* 'a tree -> int *)
(* computes the height of the tree *)
fun tree_height(tree) =
    case tree of
	leaf => 0
      | node {value=_, left=ltr, right=rtr} => Int.max(1 + tree_height(ltr), 1 + tree_height(rtr));


(* 'a tree -> int *)
(* takes a tree as an argument and returns the sum of all the values found in nodes *)
fun sum_tree(tree) =
    case tree of
	leaf => 0
      | node {value=x, left = ltr, right = rtr} => x + sum_tree(ltr) + sum_tree(rtr);


(* flag tree -> flag tree *)
(* return a trimmed version of the tree *)
fun gardener(ftree) =
    case ftree of
	leaf => leaf
      | node {value=x, left=ltr, right=rtr} =>
	if x = prune_me
	then leaf
	else node {value=x, left=gardener(ltr), right=gardener(rtr)};

							   
(* PROBLEM 8 *)

(* rewriting some basic list / option functions *)
fun last(lst) =
    case lst of
	[] => raise Empty
      | x :: [] => x
      | x :: xs' => last(xs');

fun take(lst, i) =
    if i > length(lst) orelse i < 0
    then raise Subscript
    else
	case lst of
	    [] => []
	  | x :: xs' =>
	    if i > 0
	    then x :: take(xs', i - 1)
	    else [];
		     
fun drop(lst, i) =
    if i > length(lst) orelse i < 0
    then raise Subscript
    else
	case lst of
	    [] => []
	  | x :: xs' =>
	    if i > 0
	    then drop(xs', i - 1)
	    else x :: xs';

fun concat(lst) =
    case lst of
	[] => []
      | x :: xs' => x @ concat(xs')
			      
fun getOpt(opt, a) =
    case opt of
	NONE => a
      | SOME x => x;


(* PROBLEMS 9-16 *)
datatype nat = ZERO | SUCC of nat;

(* nat -> bool *)
(* check if the provided nat is positive and return true, false otherwise *)
fun is_positive(nat) =
    case nat of
	ZERO => false
     |  SUCC nat => true;

(* nat -> nat *)
(* return the predecessor of the given natural number *)
exception Negative;
fun pred(nat) =
    case nat of
	ZERO => raise Negative
      | SUCC x => x;


(* nat -> int *)
(* return the int corresponding to the given nat *)
fun nat_to_int(nat) =
    case nat of
	ZERO => 0
      | SUCC x => 1 + nat_to_int(x);


(* int -> nat *)
(* return the nat corresponding to the given int *)
fun int_to_nat(int) =
    if int < 0
    then raise Negative	       
    else if int = 0
    then ZERO
    else SUCC (int_to_nat(int - 1));

				
(* nat nat -> nat *)
(* perform addition *)
fun add(nat1, nat2) =
    case nat1 of
	ZERO => nat2
      | SUCC x => add(x, SUCC nat2);

(* nat nat -> nat *)
(* perform subtraction
 ASSUME the first number is larger than the second one *)
fun sub(nat1, nat2) =
    case nat2 of
	ZERO => nat1
      | SUCC x => sub(pred(nat1), pred(nat2));


(* nat nat -> nat *)
(* perform multiplication *)
fun mult(nat1, nat2) =
    case nat1 of
	ZERO => ZERO
      | SUCC ZERO => nat2
      | SUCC x => add (nat2, mult(x, nat2));


(* nat nat -> bool *)
(* check if nat1 is less than nat2 and return true if it is *)
fun less_than(nat1, nat2) =
    case nat2 of
	ZERO => false
      | SUCC x => case nat1 of
		      ZERO => true
		    | SUCC y => less_than(y,x); 
		   


			       
