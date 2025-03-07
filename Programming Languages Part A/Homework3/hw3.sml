(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer;

datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern;

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu;

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end;

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string;

(**** you can put all your code here ****)

(* string list -> string list *)
(* retuns a list with only the capitalized strings *)
fun only_capitals(lst) =
    List.filter (fn x => Char.isUpper(String.sub(x, 0))) lst;

(* string list -> string *)
(* retuns the longest string in the list
   if there are multiple with the same length, return the leftmost one *)
fun longest_string1(lst) =
    List.foldl (fn (x, y) => if String.size x > String.size y then x else y) "" lst;

(* string list -> string *)
(* retuns the longest string in the list
   if there are multiple with the same length, return the rightmost one *)
fun longest_string2(lst) =
    List.foldl (fn (x, y) => if String.size x >= String.size y then x else y) "" lst;			       


(* (int * int -> bool) -> string list -> string *)
(* create a more general helper that takes in and returns a function *)

fun longest_string_helper(fct) =
    fn lst => List.foldl (fn (x, y) => if fct(String.size x, String.size y) then x else y) "" lst

val longest_string3 = longest_string_helper (fn (x, y) => x > y);
val longest_string4 = longest_string_helper (fn (x, y) => x >= y);


(* string list -> string *)
(* takes in a list and returns the longest uppercase string, or "" if none exist *)

val longest_capitalized = longest_string1 o only_capitals;

(* string -> string *)
(* returns the reversed string *)
val rev_string = implode o List.rev o explode;


(* ('a -> 'b option) -> 'a list -> 'b *)
(* take in a list and returns some el where el satisfied the given function the value of the first SOME found *)
fun first_answer f =
    let
	fun aux lst =
	    case lst of
	    [] => raise NoAnswer
	     | x :: xs' =>
	       case f x of
		   SOME y => y
		 | NONE => aux xs'
    in
	aux
    end;
		 
				

(* 'a -> 'b list option) -> 'a list -> 'b list option *)
(* takes in a predicate and applies it to the list, and returns the SOME list, or NONE if at least one element is NONE *)
fun all_answers f =
    fn lst =>
       let
	   fun aux(lst, acc) =
	       case lst of
		   [] => SOME acc
		 | x :: xs' =>
		   case f x of
		       NONE => NONE
		     | SOME y => aux(xs', y @ acc);
       in
	   aux(lst, [])
       end;

(*
datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern;

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu;

fun g f1 f2 p =
    let 
	val r = g f1 f2 
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end;

(**** for the challenge problem only ****)

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string;*)

(* pattern -> int *)
(* returns the number of wildcards in the pattern *)

fun count_wildcards(patt) =
    g (fn () => 1) (fn x => 0) patt;

(* pattern -> int *)
(* return the number of wildcards added to the length of the variable name lengths *)
fun count_wild_and_variable_lengths(patt) =
    g (fn () => 1) (fn x => String.size x) patt;

(* (string * pattern) -> int *)
(* checks how many times the given string is used as a variable name *)
fun count_some_var(str, patt) =
    g (fn () => 0) (fn x => if str = x then 1 else 0) patt;


(* pattern -> bool *)
(* takes in a pattern and checks if the variable names are unique *)
fun check_pat(patt) =
    let
	fun getVarNameList(patt) =
	    case patt of
	      Variable x => [x]
	      | ConstructorP (s,p) => getVarNameList(p)
	      | TupleP ps => List.foldl (fn (p, i) => getVarNameList(p) @ i) [] ps
	      | _ => [];

	fun noRepeats(los) =
	    case los of
		[] => true
	      | x :: xs' => if List.exists (fn (y) => x = y) xs'
			    then false
			    else noRepeats(xs')
				  
    in
	noRepeats(getVarNameList(patt))
    end;


(* (valu * pattern) -> (string * valu) list option *)
(* takes in a value - pattern pair, and returns NONE if the pattern does not match, and SOME lst (the list of bindings) if it does *)

fun match pair =
    case pair of
	(_, Wildcard) => SOME []
      | (v, Variable x) => SOME [(x, v)]
      | (Unit, UnitP) => SOME []
      | (Const i1, ConstP i2) => if i1 = i2 then SOME [] else NONE
      | (Constructor(s1, v), ConstructorP (s2, p)) => if s1 = s2 then match(v, p) else NONE
      | (Tuple vs, TupleP ps) =>
	let
	    val pairs = ListPair.zip(vs, ps)
	in
	    if length(vs) = length(ps)
	    then all_answers match pairs
	    else NONE
	end
      | _  => NONE;

(* ('a -> 'b option) -> 'a list -> 'b *)
(* (pattern -> (string * valu) list option option) -> pattern list -> (string * valu) list option *)
			 
(* valu -> pattern list -> (string * valu) list option *)
(* returns a list option of bindings for the first pattern that matches *)
fun first_match v =
    (first_answer (fn x => case match(v, x) of
			       SOME lst => SOME (SOME lst)
			     | NONE => NONE)) handle NoAnswer => NONE;



	    
