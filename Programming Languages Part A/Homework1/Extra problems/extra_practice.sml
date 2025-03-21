(* int list -> int *)
(* takes in a list of numbers and adds them with alternating sign *)
fun alternate(lst: int list) =
    if null lst
    then 0
    else
	let
	    fun change_sign(lst1: int list, alt: int) =
		       if null lst1
		       then 0
		       else
			   if alt mod 2 = 0
			   then (hd lst1) + change_sign((tl lst1), alt + 1)
			   else ~(hd lst1) + change_sign((tl lst1), alt + 1)
	in
	    change_sign(lst, 0)
	end;


(* int list -> int * int *)
(* returns the (min, max) pair for the given list *)

fun min_max(lst: int list) =
    if null lst
    then (0, 0)
    else
	let
	    fun find_min(lst: int list, min: int) =
		if null lst
		then min
		else
		    if (hd lst) < min
		    then find_min((tl lst), (hd lst))
		    else find_min((tl lst), min);

	    fun find_max(lst: int list, max: int) =
		if null lst
		then max
		else
		    if (hd lst) > max
		    then find_max((tl lst), (hd lst))
		    else find_max((tl lst), max);
	    
				 
	in
	    (find_min((tl lst), (hd lst)), find_max((tl lst), (hd lst)))
	end;


(* int list -> int list *)
(* takes in a list and returns the list of partial sums *)

fun cumsum(lst: int list) =
    if null lst
    then []
    else
	let
	    fun keep_sum(lst: int list, sum: int) =
		if null lst
		then [sum]
		else
		    sum::keep_sum((tl lst), sum + (hd lst))
	in
	    keep_sum((tl lst), (hd lst))
	end;


(* string option -> string *)
(* given SOME name, returns the string "Hello there, ...", or a "you" if NONE is given *)

fun greeting(greeting: string option) =
    if isSome(greeting)
    then "Hello there, " ^ valOf(greeting) ^ "!"
    else "Hello there, you!";



(* int list, int list -> int list *)
(* returns a list with the first elements repeated according to the numbers from the second list *)
(* ASSUME: the lists have the same length *)

fun repeat(nums: int list, repeats: int list) =
    if null nums
    then []
    else
	let
	    fun repeat_num(num: int, num_repeats: int) =
	    if num_repeats = 0
	    then []
	    else num::repeat_num(num, num_repeats - 1)
	in
	    repeat_num((hd nums), (hd repeats)) @ repeat((tl nums), (tl repeats))
	end;

	     
(* int option * int option -> int option *)
(* takes in two "possible" ints and returns either their sum, or NONE if at least one of them is missing *)

fun addOpt(opt1: int option, opt2: int option) =
    if isSome(opt1) andalso isSome(opt2)	    
    then SOME(valOf(opt1) + valOf(opt2))
    else NONE;

	  
	     
(* int option list -> int option *)
(* takes in a list of "possible" integers and returns their sum, or NONE if there are no elements in the list *)

fun addAllOpt(lst: int option list) =
    if null lst
    then NONE
    else
	let
	    fun addOpts(opt1: int option, opt2: int option) =
		if isSome(opt1)
		then
		    if isSome(opt2)
		    then SOME (valOf(opt1) + valOf(opt2))
		    else opt1
		else
		    if isSome(opt2)
		    then opt2
		    else NONE
			     
	    fun add_nums(lst: int option list, sum: int option) =
		if null lst
		then sum
		else if isSome(hd lst)
		then add_nums((tl lst), addOpts((hd lst), sum))
		else add_nums((tl lst), sum)
	in
	    add_nums(lst, NONE)
	end;


(* bool list -> bool *)
(* returns true if at least one true value is found, false otherwise *)

fun any(lst: bool list) =
    if null lst
    then false
    else
	if (hd lst)
	then true
	else any((tl lst));


(* bool list -> bool *)
(* returns true if all the values in the list are true *)

fun all(lst: bool list) =
    if null lst
    then true
    else
	if (hd lst) = false
	then false
	else all((tl lst));


(* int list, int list -> int * int *)
(* creates consecutive pairs until one of the lists is empty *)

fun zip(lst1: int list, lst2: int list) =
    if null lst1 orelse null lst2
    then []
    else
	((hd lst1), (hd lst2))::zip((tl lst1), (tl lst2));


(* int list, int list -> int list *)
(* as the function above, but instead recycles the elements from the first list and creates pairs until the second list is empty *)

fun zipRecycle(lst1: int list, lst2: int list) =
    if null lst1 orelse null lst2
    then []
    else
	let
	    fun getRemaining(lst2: int list, num: int) =
		if num > length(lst2)
		then []
		else
		    if num = 0
		    then lst2
		    else
			getRemaining((tl lst2), num - 1)
	in  
	    zip(lst1, lst2) @ zipRecycle(lst1, getRemaining(lst2, length(lst1)))
	end;
							
		      
(* int list, int list -> (int * int) list option *)
(* returns SOME when the lists have the same length, NONE otherwise *)
				   
fun zipOpt(lst1: int list, lst2: int list) =
    if (null lst1 orelse null lst2) orelse (length(lst1) <> (length lst2))
    then NONE
    else
	let
	    fun build_list(lst1: int list, lst2: int list) =
		if null lst1 orelse null lst2
		then []
		else
		    ((hd lst1), (hd lst2))::build_list((tl lst1), (tl lst2))
	in
	    SOME (build_list(lst1, lst2))
	end;


(* (string * int) list, string -> int option *)
(* takes in a list of pairs and a string and returns the value from the pair, or NONE if it's not found *)

fun lookup(lst: (string * int) list, str: string) =
    if null lst
    then NONE
    else
	if (#1 (hd lst)) = str
	then SOME (#2 (hd lst))
	else lookup((tl lst), str);

	      
(* int list -> int list * int list *)
(* returs 2 lists, one that contains the positive numbers, and another that contains the negative numbers *)

fun splitup(lst: int list) =
    if null lst
    then ([], [])
    else
	let
	    fun split(lst1: int list, neg: int list, pos: int list) =
		if null lst1
		then (rev(pos), rev(neg))
		else
		    if (hd lst1) >= 0
		    then split((tl lst1), neg, (hd lst1)::pos)
		    else split((tl lst1), (hd lst1)::neg, pos)	      		   	
	in
	    split(lst, [], [])	       
	end;


(* int list, int -> int list * int list *)
(* returns two split lists, the first having the elements larger than the given int, the other the elements smaller than the given int *)

fun splitAt(lst: int list, el: int) =
    if null lst
    then ([], [])
    else
	let
	    fun split(lst1: int list, neg: int list, pos: int list) =
		if null lst1
		then (rev(pos), rev(neg))
		else
		    if (hd lst1) >= el
		    then split((tl lst1), neg, (hd lst1)::pos)
		    else split((tl lst1), (hd lst1)::neg, pos)	      		   	
	in
	    split(lst, [], [])	       
	end;
		   

(* int list -> bool *)
(* returns true if the list is sorted in ascending order, false otherwise *)

fun isSorted(lst: int list) =
    if null lst
    then true
    else
	let
	    fun aux(lst1: int list, prev: int) =
		if null lst1
		then true
		else
		    if (hd lst1) < prev
		    then false
		    else aux((tl lst1), (hd lst1))
	in
	    aux((tl lst), (hd lst))
	end;


(* int list -> bool *)
(* checks if the list is sorted in any order and returns true if it is, false if not *)

fun isAnySorted(lst: int list) =
    if null lst
    then true
    else
	let
	    fun aux(lst1: int list, prev: int) =
		if null lst1
		then true
		else
		    if (hd lst1) < prev
		    then false
		    else aux((tl lst1), (hd lst1));
	    
	    fun aux1(lst1: int list, prev: int) =
		if null lst1
		then true
		else
		    if (hd lst1) > prev
		    then false
		    else aux1((tl lst1), (hd lst1));
	in
	    aux((tl lst), (hd lst)) orelse aux1((tl lst), (hd lst))
	end;


(* int list, int list -> int list *)
(* takes in two sorted lists and returns the merged sorted list *)
(* ASSUME: the two given lists are properly sorted *)

fun sortedMerge(lst1: int list, lst2: int list) =
    if null lst1
    then lst2
    else
	if null lst2
	then lst1
	else
	    if isSorted([(hd lst1), (hd lst2)])
	    then (hd lst1)::sortedMerge((tl lst1), lst2)
	    else (hd lst2)::sortedMerge(lst1, (tl lst2));
				       
		
		
(* int list -> int list *)
(* sorts the list in increasing order *)

fun qsort(lst: int list) =
    if null lst
    then []
    else
	let
	    val splits = splitAt((tl lst), (hd lst))
	in
	    qsort((#2 splits)) @ [hd lst] @ qsort((#1 splits))
	end;


(* int list -> int list * int list *)
(* takes in a list an returns two lists by alternating the elements of the first list *)

fun divide(lst: int list) =
    if null lst
    then ([], [])
    else
	let
	    fun divCounter(lst: int list, count: int, lst1: int list, lst2: int list) =
		if null lst
		then (rev(lst1), rev(lst2))
		else
		    if count mod 2 = 0
		    then divCounter((tl lst), count + 1, (hd lst)::lst1, lst2)
		    else divCounter((tl lst), count + 1, lst1, (hd lst)::lst2)
	in
	    divCounter(lst, 0, [], [])
	end;


(* int list -> int list *)
(* splits the list in two and recursively sorts those two lists, and merges them using sortedMerge *)

fun not_so_quick_sort(lst: int list) =
    if null lst
    then []
    else
	let
	    val divided = divide(lst);
	in
	    if length(#1 divided) = 1
	    then sortedMerge((#1 divided), not_so_quick_sort(#2 divided))
	    else sortedMerge(not_so_quick_sort(#1 divided), not_so_quick_sort(#2 divided))
	end;

	     
(* int, int -> int * int *)
(* takes in pair (k, n) and attempts to n / k as many times as possible, and returns (n2, n), where n2 is the number of times n / k doesn't have any remainder, and n is what remains after finishing those operations *)

fun fullDivide(k: int, n: int) =
    if n mod k <> 0
    then (0, n)
    else
	let
	    fun aux(k: int, n: int, count: int) =
		if n mod k <> 0
		then (count, n)
		else aux(k, n div k, count + 1)
	in
	    aux(k, n, 0)
	end;

(* int -> (int * int) list *)
(* given an integer n, the function returns a list of pairs (d, k), where d is a prime number dividing n k times *)

		    
fun factorize(n: int) =
    if n <= 1
    then []
    else
	let
	    fun runFactors(n: int, divisor: int) =
		if Math.sqrt(Real.fromInt n) < Real.fromInt divisor
		then if n = 1
		     then []
		     else [(n, 1)]     
		else
		    let
			val divide = fullDivide(divisor, n)
                        val count = (#1 divide)
                        val remainder = (#2 divide)
		    in
			if count > 0
			then (divisor, count)::runFactors(remainder, divisor + 1)
							 else runFactors(remainder, divisor + 1)
		    end
	in
             runFactors(n, 2)
	end;


(* (int * int) list -> int *)
(* takes in a list of prime factors and their count and returns the resulting number *)

fun multiply(lst: (int * int) list) =
    if null lst
    then 1
    else
	let
	    fun pow(pair: int * int) =
		if (#2 pair) = 1
		then (#1 pair)
		else (#1 pair) * pow((#1 pair), (#2 pair) - 1)
	in
	    pow((hd lst)) * multiply((tl lst))
	end;
			 
(* (int * int) list -> int list *)
(* given a list of factors (n, k), return all the possible numbers that can be computed by multiplying them with each other, but for a maximum of k uses *)

fun all_products(lst: (int * int) list) =
    if null lst
    then [1]
    else
	let
	    (* raises first element to the power of the second element *)
	    fun pow(pair: int * int) =
		if (#2 pair) = 1
		then (#1 pair)
		else (#1 pair) * pow((#1 pair), (#2 pair) - 1)

	    (* returns the list of powers for a pair of (prime, exp) *)		 	    
	    fun compute_powers(pair: int * int, res: int list) =
		if #2 pair = 0
		then res
		else compute_powers(((#1 pair), (#2 pair) - 1), pow(pair)::res)

	    (* multiply the list with the given element *)
	    fun multiply_list(lst: int list, el: int) =
		if null lst
		then []
		else el * (hd lst)::multiply_list((tl lst), el)				 

	    (* takes in two lists and multiplies all elements in the first list with all the elements in the second list *)
	    fun flatten_list(lst1: int list, lst2: int list) =
		if null lst1
		then []
		else multiply_list(lst2, (hd lst1)) @ flatten_list((tl lst1), lst2)
								  		 		   		 
	    (* takes in a pair of (factor, exp) and a list and computes the expanded list (multiplies all powers of the pair with each element of the list) *)
	    fun expand_factors(pair: int * int, rst: int list) =
		flatten_list(compute_powers(pair, [1]), rst);
	in
	    qsort(expand_factors((hd lst), all_products((tl lst))))
	end;
	    
	
