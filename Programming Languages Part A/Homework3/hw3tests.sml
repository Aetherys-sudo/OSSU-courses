use "hw3.sml";
(* Homework3 Simple Test*)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1 = only_capitals ["A","B","C"] = ["A","B","C"];

val test11 = only_capitals ["Aaa","Bbb","Ccc"] = ["Aaa","Bbb","Ccc"];

val test12 = only_capitals [] = [];

val test13 = only_capitals ["A","bbb","C"] = ["A","C"];

val test14 = only_capitals ["aa","bbb","cc"] = [];

val test2 = longest_string1 [] = "";

val test21 = longest_string1 ["A","bc","C"] = "bc";

val test22 = longest_string1 ["Aaaaa","bc","C"] = "Aaaaa";

val test23 = longest_string1 ["A","bccc","Cccc"] = "bccc";

val test24 = longest_string1 ["A","bccc","Ccccc"] = "Ccccc";

val test3 = longest_string2 [] = "";

val test31 = longest_string2 ["A","bc","C"] = "bc";

val test32 = longest_string2 ["Aaaaa","bc","C"] = "Aaaaa";

val test33 = longest_string2 ["A","bccc","Cccc"] = "Cccc";

val test34 = longest_string2 ["A","bccc","Ccccc"] = "Ccccc";


val test3a = longest_string3 [] = "";

val test3a1 = longest_string3 ["A","bc","C"] = "bc";

val test3a2 = longest_string3 ["Aaaaa","bc","C"] = "Aaaaa";

val test3a3 = longest_string3 ["A","bccc","Cccc"] = "bccc";

val test3a4 = longest_string3 ["A","bccc","Ccccc"] = "Ccccc";


val test3b = longest_string4 [] = "";

val test3b1 = longest_string4 ["A","bc","C"] = "bc";

val test3b2 = longest_string4 ["Aaaaa","bc","C"] = "Aaaaa";

val test3b3 = longest_string4 ["A","bccc","Cccc"] = "Cccc";

val test3b4 = longest_string4 ["A","bccc","Ccccc"] = "Ccccc";


val test51 = longest_capitalized [] = "";

val test52 = longest_capitalized ["A","bc","Ca"] = "Ca";

val test53 = longest_capitalized ["A","bccc","C"] = "A";

val test54 = longest_capitalized ["a","bc","c"] = "";

val test55 = longest_capitalized ["A","Bc","C"] = "Bc";


val test6 = rev_string "abc" = "cba";

val test61 = rev_string "" = "";

val test62 = rev_string "ABC" = "CBA";

val test63 = rev_string "abcdef12" = "21fedcba";


val test7 = first_answer (fn x => if x > 3 then SOME x else NONE) [1,2,3,4,5] = 4;

val test71 = ((first_answer(fn x => if x > 3 then SOME x else NONE) [1,2,3]; false) handle NoAnswer => true);

val test72 = first_answer (fn x => if x > 4 then SOME x else NONE) [1,2,3,4,5] = 5;

val test73 = first_answer (fn x => if x > 0 then SOME x else NONE) [1,2,3,4,5] = 1;

val test74 = ((first_answer (fn x => if x > 3 then SOME x else NONE) []; false) handle NoAnswer => true);


val test8 = all_answers (fn x => if x = 1 then SOME [x] else NONE) [2,3,4,5,6,7] = NONE;

val test81 = all_answers (fn x => if x = 1 then SOME [x] else NONE) [1,1,1,1] = SOME [1, 1, 1, 1];

val test82 = all_answers (fn x => if x > 3 then SOME [x] else NONE) [2,3,4,5,6,7] = NONE;

val test83 = all_answers (fn x => if x < 3 then SOME [x] else NONE) [0,1,2] = SOME [2, 1, 0];


val test9a = count_wildcards Wildcard = 1;

val test9a1 = count_wildcards (Variable "abc") = 0;

val test9a2 = count_wildcards (TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "xy"), ConstructorP ("ach", Wildcard)]) = 2;

val test9a3 = count_wildcards (ConstructorP("abv", Variable "dasd")) = 0;

val test9a4 = count_wildcards (ConstructorP("abv", Wildcard)) = 1;
			      

val test9b = count_wild_and_variable_lengths Wildcard = 1;

val test9b1 = count_wild_and_variable_lengths (Variable "abc") = 3;

val test9b2 = count_wild_and_variable_lengths (TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "xy"), ConstructorP ("ach", Wildcard)]) = 7;

val test9b3 = count_wild_and_variable_lengths (ConstructorP("abv", Variable "dasd")) = 4;

val test9b4 = count_wild_and_variable_lengths (ConstructorP("abv", Wildcard)) = 1;


val test9c = count_some_var ("x", Variable("x")) = 1;

val test9c1 = count_some_var ("ac", Wildcard) = 0;

val test9c2 = count_some_var ("xy", Variable("x")) = 0;

val test9c3 = count_some_var ("abc", TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "xy"), ConstructorP ("ach", Wildcard)]) = 1;

val test9c4 = count_some_var ("abc", TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "abc"), ConstructorP ("ach", Wildcard)]) = 2;

val test9c5 = count_some_var ("abc", ConstructorP("abv", Variable "dasd")) = 0;

val test9c6 = count_some_var ("dasd", ConstructorP("abv", Variable "dasd")) = 1;


val test10 = check_pat (Wildcard) = true;

val test101 = check_pat (TupleP [Variable("x"), Variable("x")]) = false;

val test102 = check_pat (TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "bce"), ConstructorP ("ach", Variable "ghf")]) = true;

val test103 = check_pat (TupleP [Wildcard, Variable "abc", ConstructorP ("ach", Variable "abc"), ConstructorP ("ach", Variable "ghf")]) = false;

val test104 = check_pat (TupleP [TupleP [TupleP [Variable "abc"]], Variable "abc", ConstructorP ("ach", Variable "abc"), ConstructorP ("ach", Variable "ghf")])= false;

val test105 = check_pat (TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("ach", Variable "abc"), ConstructorP ("ach", Variable "ghf")])= false;

val test106 = check_pat (TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("ach", Variable "abcip"), ConstructorP ("ach", Variable "ghf")])= true;

val test107 = check_pat (ConstructorP("abv", Variable "dasd")) = true;

val test108 = check_pat (ConstructorP("abv", ConstructorP ("gdf", Variable "asd"))) = true;


val test11 = match (Const(1), UnitP) = NONE;

val test111 = match (Const(1), ConstP(4)) = NONE;

val test112 = match (Const(1), ConstP(1)) = SOME [];

val test113 = match (Const(1), Wildcard) = SOME [];

val test114 = match (Unit, Wildcard) = SOME [];

val test115 = match (Unit, UnitP) = SOME [];

val test116 = match (Constructor ("a", Const(13)), UnitP) = NONE;

val test117 = match (Constructor ("a", Const(13)), ConstructorP("b", ConstP(13))) = NONE;

val test118 = match (Constructor ("a", Const(13)), ConstructorP("a", ConstP(13))) = SOME [];

val test119 = match (Constructor ("a", Const(13)), ConstructorP("a", Variable "acb")) = SOME [("acb", Const(13))];

val test1110 = match (Constructor ("a", Const(13)), TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("ach", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]) = NONE;

val test1111 = match (Tuple [Tuple [Tuple [Const(13)]]], TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("ach", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]) = NONE;

val test1112 = match (Tuple [Tuple [Tuple [Const(13)]], Unit, Constructor("abc", Const(15)), Constructor("ach", Const(20))], TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("ach", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]) = NONE;

val test1113 = match (Tuple [Tuple [Tuple [Const(13)]], Unit, Constructor("abc", Const(15)), Constructor("ach", Const(20))], TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("abc", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]) = SOME (List.rev [("gjfghj", Const(13)), ("abc", Unit), ("abcip", Const(15)), ("ghf", Const(20))]);


val test12 = first_match Unit [UnitP] = SOME [];

val test121 = first_match Unit [Wildcard] = SOME [];

val test122 = first_match Unit [UnitP, Wildcard] = SOME [];

val test123 = ((first_match (Const 13) [UnitP]; false) handle NoAnswer => true);

val test124 = first_match (Const 13) [ConstP(13)] = SOME [];

val test125 = ((first_match (Constructor("ab", Const 15)) [ConstructorP("a", Variable "abc"), ConstructorP("abg", Variable "abc")]; false) handle NoAnswer => true);

val test126 = first_match (Constructor("ab", Const 15)) [ConstructorP("ab", Variable "abc"), ConstructorP("abg", Variable "abc")] = SOME [("abc", Const(15))];

val test127 = ((first_match (Const 15) [TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("abc", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]]; false) handle NoAnswer => true);

val test128 = first_match (Tuple [Tuple [Tuple [Const(13)]], Unit, Constructor("abc", Const(15)), Constructor("ach", Const(20))]) [TupleP [TupleP [TupleP [Variable "gjfghj"]], Variable "abc", ConstructorP ("abc", Variable "abcip"), ConstructorP ("ach", Variable "ghf")]] = SOME (List.rev [("gjfghj", Const(13)), ("abc", Unit), ("abcip", Const(15)), ("ghf", Const(20))]);

val test129 = (((first_match (Tuple [Tuple [Tuple [Const(13)]], Unit, Constructor("abc", Const(15)), Constructor("ach", Const(20))]) []); false) handle NoAnswer => true);

val test1210 = first_match (Tuple[Const 17,
				 Unit,
				 Const 4,
				 Constructor ("egg",Const 4),
				 Constructor ("egg",
					      Constructor ("egg",Const 4)),
				 Tuple[Const 17,
				       Unit,
				       Const 4
				       ,Constructor ("egg",Const 4),
				       Constructor ("egg",
						    Constructor ("egg",Const 4))],
				 Tuple[Unit,Unit],
				 Tuple[Const 17,Const 4],
				 Tuple[Constructor ("egg",Const 4),
				       Constructor ("egg",Const 4)]])

			  [ConstP 17,
			   ConstP 4,
			   ConstructorP ("egg",ConstP 4),
			   ConstructorP ("egg",
					 ConstructorP ("egg",ConstP 4)),
			   TupleP[ConstP 17,
				  Wildcard,
				  ConstP 4,
				  ConstructorP ("egg",ConstP 4),
				  ConstructorP ("egg",
						ConstructorP ("egg",ConstP 4))],
			   TupleP[Wildcard,Wildcard],
			   TupleP[ConstP 17,ConstP 4],
			   TupleP[ConstructorP ("egg",ConstP 4),
				  ConstructorP ("egg",ConstP 4)],
			   TupleP[ConstP 17,
				  Wildcard,
				  ConstP 4,
				  ConstructorP ("egg",ConstP 4),
				  ConstructorP ("egg",
						ConstructorP ("egg",ConstP 4)),
				  TupleP[ConstP 17,
					 Wildcard,
					 ConstP 4,
					 ConstructorP ("egg",ConstP 4),
					 ConstructorP ("egg",
						       ConstructorP ("egg",ConstP 4))],
				  TupleP[Wildcard,Wildcard],
				  TupleP[ConstP 17,ConstP 4],
				  TupleP[ConstructorP ("egg",ConstP 4),
					 ConstructorP ("egg",ConstP 4)]]] = SOME [];


val test131 = typecheck_patterns([], []) = SOME [];

val test132 = typecheck_patterns([], [Wildcard]) = SOME [Anything];

val test133 = typecheck_patterns([], [Variable "abc"]) = [Anything];

val test134 = typecheck_patterns([], [UnitP]) = [UnitT];

val test135 = typecheck_patterns([], [ConstP 13]) = [IntT];

val test136 = typecheck_patterns([], [TupleP[Wildcard]]) = [TupleT[Anything]];

val test137 = typecheck_patterns([], [TupleP[Wildcard, Variable "acb"]]) = [TupleT[Anything, Anything]];

val test138 = typecheck_patterns([], [TupleP[Wildcard], ConstP 13, UnitP]) = [TupleT[Anything], IntT, UnitT];

val test139 = typecheck_patterns([], [TupleP[TupleP[ConstP 13, UnitP, TupleP[Wildcard, UnitP]], Variable "cab"], ConstP 13, UnitP]) = [TupleT[TupleT[IntT, UnitT, TupleT[Anything, UnitT]], Anything], IntT, UnitT];
