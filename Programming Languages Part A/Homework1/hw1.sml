(* PROBLEM 1 *)

(* (int * int * int) (int * int * int) -> bool *)
(* takes in two dates and returns true if the first one is an older date than the second one *)

fun is_older (d1 : int * int * int, d2 : int * int * int) =
    let
	(* if first year is smaller than the second *)
	val c1 = (#1 d1 < #1 d2);
        (* if years are equal but first month is smaller than second month *)
	val c2 = (#1 d1 = #1 d2 andalso #2 d1 < #2 d2);
	(* if years and months are equal and first day is smaller than second day *)
	val c3 = (#1 d1 = #1 d2 andalso #2 d1 = #2 d2 andalso #3 d1 < #3 d2);

	(* only 3 cases for first date to be smaller *)
    in
	c1 orelse c2 orelse c3
    end


(* (int * int * int) list, int -> int *)
(* returns the number of dates that have the given month *)
fun number_in_month (lst : (int * int * int) list, month : int) =
    if null lst
    then 0
    else
	
	let
	    val rec_call = number_in_month((tl lst), month);
	in 
	    if (#2 (hd lst)) = month
	    then 1 + rec_call
	    else rec_call
	end;


(* (int * int * int) list, int list -> int *)
(* returns the number of dates that have any of the months in the second list given as an argument *)
(* this version will return 0 if either (or both) of the lists are empty, or if there are no dates found *)

fun number_in_months(lst1: (int * int * int) list, lst2: int list) =
    if null lst1 orelse null lst2
    then 0
    else
	number_in_month(lst1, (hd lst2)) + number_in_months(lst1, (tl lst2));


(* (int * int * int) list, int -> (int * int * int) list *)
(* returns the list of dates that have the month equal to the one given as an argument *)

fun dates_in_month (lst : (int * int * int) list, month : int) =
    if null lst
    then []
    else
	
	let
	    val rec_call = dates_in_month((tl lst), month);
	in 
	    if (#2 (hd lst)) = month
	    then (hd lst)::rec_call
	    else rec_call
	end;

	 
(* (int * int * int) list, int list -> int *)
(* returns the number of dates that have any of the months in the second list given as an argument *)
(* this version will return 0 if either (or both) of the lists are empty, or if there are no dates found *)

fun dates_in_months(lst1: (int * int * int) list, lst2: int list) =
    if null lst1 orelse null lst2
    then []
    else
	dates_in_month(lst1, (hd lst2)) @ dates_in_months(lst1, (tl lst2));


(* string list, int -> string *)
(* given a list of strings and an int, returns the n'th element of the list *)

fun get_nth(lst: string list, n: int) =
    if null lst orelse n < 1
    then ""
    else
	if n = 1
	then (hd lst)
	else get_nth((tl lst), n - 1);


(* (int * int * int) -> string *)
(* takes in a date and returns it as a string *)

fun date_to_string(date: (int * int * int)) =
    let
	val months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    in
	get_nth(months, #2 date) ^ " " ^ Int.toString(#3 date) ^ ", " ^ Int.toString(#1 date)
    end;
    

(* int, int list -> int *)
(* takes in a positive number and a list of all positive numbers (sum of all elements is guaranteed greater than the first number), and returns the count of numbers that are smaller than the first argument *)										
fun number_before_reaching_sum(sum: int, lst: int list) =
    let
	fun el_sum_and_count(total: int, count: int, lst1: int list) =
	    if sum <= total
	    then count
	    else el_sum_and_count((hd lst1) + total, count + 1, (tl lst1));

    in
	el_sum_and_count(0, 0, lst) - 1
    end;


(* int -> int *)
(* takes in a day [1,365] and returns the month (as a number) that day is in *)

fun what_month(day: int) =
    let
	val days_in_month = [31,28,31,30,31,30,31,31,30,31,30,31];
    in
	number_before_reaching_sum(day, days_in_month) + 1
    end;


(* int, int -> int list *)
(* returns the list of months for each of the days between day1 and day2, the given parameters *)

fun month_range(day1: int, day2: int) =
    if day1 > day2
    then []
    else
	what_month(day1)::month_range(day1 + 1, day2);
		
	   
(* (int * int * int) list -> (int * int * int) *)
(* takes in a list of dates and returns NONE if the list if empty, or SOME option, which holds the oldest date in the list *)

fun oldest (dlist: (int * int * int) list) =
    if null dlist
    then NONE
    else
	let
	    fun get_oldest(lst: (int * int * int) list, oldest: (int * int * int)) =
		if null lst
		then oldest
		else
		    if is_older((hd lst), oldest)
		    then get_oldest((tl lst), (hd lst))
		    else get_oldest((tl lst), oldest);

	    val date = get_oldest(dlist, (hd dlist));		
				   
	in
	    SOME date
	end;


				
(* CHALLENGE PROBLEMS *)

(* (int * int * int) list, int list -> int *)
(* returns the number of dates that have any of the months in the second list given as an argument *)
(* this version will return 0 if either (or both) of the lists are empty, or if there are no dates found *)
(* CHALLENGE: Having duplicate months doesn't cause issues *)

fun number_in_months_challenge(lst1: (int * int * int) list, lst2: int list) =
    let
	(* int, int list -> bool *)
	(* check if the element is in the list and return true if it is, false if it isn't *)

	fun is_in_list(num: int, lst: int list) =
	    if null lst
	    then false
	    else
		if (hd lst) = num
		then true
		else is_in_list(num, (tl lst))
			       
			       
	(* int list, int list -> int list *)
	(* takes an integer list as an argument and returns a copy of the list with no duplicates *)
	fun no_dupes(lst: int list, rst: int list) =
	    if null lst
	    then rst
	    else
		if is_in_list((hd lst), rst)
		then no_dupes((tl lst), rst)
		else no_dupes((tl lst), (hd lst)::rst)		 
			     
	val filtered_months = no_dupes(lst2, [])
    in
	if null lst1 orelse null filtered_months
	then 0
	else number_in_month(lst1, (hd filtered_months)) + number_in_months(lst1, (tl filtered_months))
    end;


(* (int * int * int) list, int list -> int *)
(* returns the list of dates that have any of the months in the second list given as an argument *)
(* this version will return an empty list if either (or both) of the lists are empty, or if there are no dates found *)
(* CHALLENGE: Having duplicate months doesn't cause issues *)

fun dates_in_months_challenge(lst1: (int * int * int) list, lst2: int list) =
    let
	(* int, int list -> bool *)
	(* check if the element is in the list and return true if it is, false if it isn't *)

	fun is_in_list(num: int, lst: int list) =
	    if null lst
	    then false
	    else
		if (hd lst) = num
		then true
		else is_in_list(num, (tl lst))
			       
			       
	(* int list, int list -> int list *)
	(* takes an integer list as an argument and returns a copy of the list with no duplicates *)
	fun no_dupes(lst: int list, rst: int list) =
	    if null lst
	    then rst
	    else
		if is_in_list((hd lst), rst)
		then no_dupes((tl lst), rst)
		else no_dupes((tl lst), (hd lst)::rst)		 
			     
	val filtered_months = no_dupes(lst2, [])
    in
	if null lst1 orelse null filtered_months
	then []
	else rev(dates_in_month(lst1, (hd filtered_months)) @ dates_in_months(lst1, (tl filtered_months)))
    end;


(* (int * int * int) -> bool *)
(* returns true if the date provided is a reasonable date, false if it isn't *)
(* the function will take leap years into consideration *)
(* the date is valid if: year > 0, 1 <= month <= 12, 1 <= day <= (corresponding max for each month) *)

fun reasonable_date(date: (int * int * int)) =
    let
	val days_in_months = [31,28,31,30,31,30,31,31,30,31,30,31];
	val days_in_months_leap = [31,29,31,30,31,30,31,31,30,31,30,31];
	val v_y = #1 date > 0;
	val v_m = (#2 date >= 1 andalso #2 date <= 12);
	val is_leap = (#1 date mod 400 = 0 orelse (#1 date mod 4 = 0 andalso #1 date mod 100 <> 0));

	fun nth(lst: int list, pos: int) =
	    if pos = 0
	    then (hd lst)
	    else nth((tl lst), pos - 1)		 
    in
	if v_y
	then
	    if v_m
	    then
		if is_leap
		then
		    if (#3 date >= 1 andalso #3 date <= nth(days_in_months_leap, #2 date - 1))
			then true
			else false	 
		else
		    if (#3 date >= 1 andalso #3 date <= nth(days_in_months, #2 date - 1))
			then true
			else false
	    else false
	else false
    end;
	    
		
	
				       
				   

	
  
		    
		  
	
	    
							
		     
		     
	
    
