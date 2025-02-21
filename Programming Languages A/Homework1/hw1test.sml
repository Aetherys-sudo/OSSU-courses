(* Homework1 Simple Test *)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)
use "hw1.sml";

val test1 = is_older ((1,2,3),(2,3,4)) = true;

val test11 = is_older ((1,2,3),(1,3,4)) = true;

val test12 = is_older ((1,2,3),(1,2,4)) = true;

val test13 = is_older ((1,2,3),(1,2,3)) = false;

val test14 = is_older ((2,3,4),(1,2,3)) = false;

val test15 = is_older ((2,3,4),(2,1,5)) = false;

val test16 = is_older ((2,3,4),(2,3,2)) = false;					     

val test2 = number_in_month ([(2012,2,28),(2013,12,1)],2) = 1;

val test21 = number_in_month ([], 2) = 0;

val test21 = number_in_month ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)], 15) = 0;

val test22 = number_in_month ([(2120,14,14),(2120,13,14),(2120,14,14),(2120,14,14)], 14) = 3;

val test3 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = 0;

val test31 = number_in_months ([],[2,3,4]) = 0;

val test32 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2]) = 1;

val test33 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[44,55]) = 0;

val test34 = number_in_months ([],[]) = 0;

val test4 = dates_in_month ([(2012,2,28),(2013,12,1)],2) = [(2012,2,28)];

val test41 = dates_in_month ([],2) = [];

val test42 = dates_in_month ([(2012,2,28),(2013,12,1)],3) = [];

val test43 = dates_in_month ([(2012,2,28),(2013,12,1),(2014,2,25),(2000,2,13)],2) = [(2012,2,28),(2014,2,25),(2000,2,13)];
							       						   

val test5 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = [(2012,2,28),(2011,3,31),(2011,4,28)];

val test51 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[100]) = [];

val test52 = dates_in_months ([],[2,3,4]) = [];

val test53 = dates_in_months ([],[]) = [];

val test54 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = [];

val test55 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2]) = [(2012,2,28)];


val test6 = get_nth (["hi", "there", "how", "are", "you"], 100) = "";

val test61 = get_nth (["hi", "there", "how", "are", "you"], 2) = "there";

val test62 = get_nth ([], 2) = "";

val test63 = get_nth (["hi", "there", "how", "are", "you"], ~1) = "";

val test64 = get_nth (["hi", "there", "how", "are", "you"], 0) = "";

val test65 = get_nth (["hi", "there", "how", "are", "you"], 5) = "you";
								    
val test7 = date_to_string (2013, 6, 1) = "June 1, 2013";

val test71 = date_to_string (0, 0, 0) = " 0, 0";

val test72 = date_to_string (2013, 13, 1) = " 1, 2013";

val test73 = date_to_string (2013, 6, 1) = "June 1, 2013";

val test74 = date_to_string (2013, 1, 1) = "January 1, 2013";

val test75 = date_to_string (2013, 12, 1) = "December 1, 2013";



val test8 = number_before_reaching_sum (10, [1,2,3,4,5]) = 3;

val test81 = number_before_reaching_sum (11, [1,2,3,4,5]) = 4;

val test82 = number_before_reaching_sum (1, [1,2,3,4,5]) = 0;

val test83 = number_before_reaching_sum (14, [1,2,3,4,5]) = 4;

val test9 = what_month 70 = 3;

val test91 = what_month 1 = 1;

val test92 = what_month 365 = 12;

val test10 = month_range (31, 34) = [1,2,2,2];

val test101 = month_range (1, 1) = [1];

val test102 = month_range (365, 365) = [12];


val test11 = oldest([(2012,2,28),(2011,3,31),(2011,4,28)]) = SOME (2011,3,31);

val test111 = oldest([]) = NONE;

val test112 = oldest([(2012,2,28)]) = SOME (2012,2,28);

val test113 = oldest([(2012,2,28),(2012,2,31),(2012,2,28)]) = SOME (2012,2,28);

val print = oldest([(5,12,15),(5,12,10),(5,12,1),(5,12,0)]);

val test114 = oldest([(5,12,15),(5,12,10),(5,12,1)]) = SOME (5,12,1);

val test114 = oldest([(5,12,15),(5,12,10),(5,12,1),(5,12,0)]) = SOME (5,12,0);




val test3330 = number_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = 0;

val test3331 = number_in_months_challenge ([],[2,3,4]) = 0;

val test3332 = number_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2]) = 1;

val test3333 = number_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[44,55]) = 0;

val test331 = number_in_months_challenge ([],[2,3,4]) = 0;

val test332 = number_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,2,5]) = 1;

val test333 = number_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,2,3,3]) = 2;

val test334 = number_in_months_challenge ([],[]) = 0;


val test5 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = [(2012,2,28),(2011,3,31),(2011,4,28)];

val test511 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[100]) = [];

val test512 = dates_in_months_challenge ([],[2,3,4]) = [];

val test513 = dates_in_months_challenge ([],[]) = [];

val test514 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = [];

val test515 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2]) = [(2012,2,28)];


val test516 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,2,2,3,3,4]) = [(2012,2,28),(2011,3,31),(2011,4,28)];

val test517 = dates_in_months_challenge ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4,3,4,2,12]) = [(2012,2,28),(2011,3,31),(2011,4,28),(2013,12,1)];

val test000 = reasonable_date((2012, 12, 12)) = true;

val test001 = reasonable_date((2024, 2, 29)) = true;

val test002 = reasonable_date((2024, 2, 30)) = false;

val test003 = reasonable_date((2023, 2, 29)) = false;

val test004 = reasonable_date((0, 12, 12)) = false;

val test005 = reasonable_date((2012, 0, 12)) = false;

val test006 = reasonable_date((2012, 12, 0)) = false;

val test007 = reasonable_date((2012, 4, 31)) = false;

val test008 = reasonable_date((2012, 8, 1)) = true;

val test009 = reasonable_date((2014, 1, 1)) = true;

val test010 = reasonable_date((2014, 14, 1)) = false;

val test010 = reasonable_date((2014, 1, 33)) = false;
