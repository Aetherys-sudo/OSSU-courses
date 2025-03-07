(* ('b -> 'c option) -> ('a -> b' option) -> 'a -> 'c option *)
(* composes 2 functions with optional values *)
fun compose_opt f1 f2 a =
    case f2 a of
	NONE => NONE
      | SOME y => case f1 y of
		      NONE => NONE
		    | SOME z => SOME z;


(* ('a -> 'a) -> ('a -> bool) -> 'a -> 'a *)
(* apply f to x and then the results until p is false (f p x) *)
fun do_until next done x =
    if done x
    then x
    else do_until next done (next x);


val fact = (fn x =>
	       (#2 (do_until
		    (fn (x, res) => (x - 1, res * x))
		    (fn (x, y) => x = 1) (x, 1))));

(* (''a -> ''a) -> ''a -> ''a *)
(* given f and x applies f to x until f x = x *)
fun fixed_point next num =
    do_until next (fn x => next x = x) num;
	

(* ('a -> 'b) -> 'a * 'a -> 'b * 'b *)
(* takes in a function and a pair and returns the corresponding pair after applying the function *)

fun map2 f (a1, a2) =
    (f a1, f a2);


(* ('b -> 'c list) -> ('a -> b' list) -> a -> 'c list *)
(* applies f g x andr concatenates results in a single list *)
fun app_all f g x =
    let
	val lst = g x;

	fun iterate(lst) =
	    case lst of
		[] => []
	      | x :: xs' => f x @ iterate(xs') 
    in	
	iterate lst
    end;

(* ('a * 'b -> 'b) -> 'b -> 'a list *)

fun fold f acc lst =
    case lst  of
	[] => acc
      | x :: xs' => f (x, fold f acc xs');

(* ('a -> bool) -> 'a list -> 'a list * 'a list *)
(* separates the elements from the list (in the left we have the ones that satisfy pred, and in the right the others *)
fun partition f lst =
    let
	fun tail(lst, (acc1, acc2)) =
	    case lst of
		[] => (acc1, acc2)
	      | x :: xs' => if f x
			    then tail(xs', (x :: acc1, acc2))
			    else tail(xs', (acc1, x :: acc2))
    in
	tail(lst, ([], []))
    end;


(* ('a -> ('b * 'a) option) -> 'a -> 'b list *)
(* produces a list of 'b values given a seed 'a *)
fun unfold f a =
    case f a of
	SOME (b, a) => b :: unfold f a
      | NONE => [];


fun fact_unfold y =
    let
	val lst = unfold (fn x => if x = 0
				  then NONE
				  else SOME (x, x - 1)) y;
    in
	foldl (fn (x, y) => x * y) 1 lst
    end;
				  

fun map_foldr f lst =
    foldr (fn (x, y) => f x :: y) [] lst;

fun filter_foldr f lst =
    foldr (fn (x, y) => if f x
			then x :: y
			else y) [] lst;


datatype 'a bst = Leaf | Node of ('a * 'a bst * 'a bst);

fun map_tree f tr =
    case tr of
	Leaf => Leaf
      | Node (x, ltr, rtr) => Node (f x, map_tree f ltr, map_tree f rtr);

fun fold_tree f acc tr =
    case tr of
	Leaf => acc
      | Node (x, ltr, rtr) =>
	let
	    val lrst = fold_tree f (f x acc) ltr;
	    val rrst = fold_tree f (f x acc) ltr;
	in
	    fold_tree f (f lrst rrst) tr
	end;

