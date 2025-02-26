(* Homework2 Simple Test *)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

use "hw2.sml";

val test1 = all_except_option ("string", ["string"]) = SOME [];

val test11 = all_except_option ("a", []) = NONE;

val test12 = all_except_option ("a", ["a", "b", "b", "c"]) = SOME ["b", "b", "c"];

val test13 = all_except_option ("a", ["b", "b", "c", "a"]) = SOME ["b", "b", "c"];

val test14 = all_except_option ("a", ["b", "b", "c"]) = NONE;


val test2 = get_substitutions1 ([["foo"],["there"]], "foo") = [];

val test21 = get_substitutions1 ([["foo"],["foo"]], "foo") = [];

val test22 = get_substitutions1 ([], "foo") = [];

val test23 = get_substitutions1 ([["foo", "foobar"],["there"]], "foo") = ["foobar"];

val test24 = get_substitutions1([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Fred") = ["Fredrick","Freddie","F"];


val test3 = get_substitutions2 ([["foo"],["there"]], "foo") = [];

val test31 = get_substitutions2 ([["foo"],["foo"]], "foo") = [];

val test32 = get_substitutions2 ([], "foo") = [];

val test33 = get_substitutions2 ([["foo", "foobar"],["there"]], "foo") = ["foobar"];

val test34 = get_substitutions2([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Fred") = ["Freddie","F","Fredrick"];

val test35 = get_substitutions2([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], "Elizabeth") = ["Betty"];


val test4 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Guy", middle="W", last="Smith"}) =
	    [{first="Guy", last="Smith", middle="W"}];

val test41 = similar_names ([], {first="Fred", middle="W", last="Smith"}) =
	    [{first="Fred", last="Smith", middle="W"}];

val test42 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Elizabeth", middle="W", last="Smith"}) =
	    [{first="Elizabeth", last="Smith", middle="W"}, {first="Betty", last="Smith", middle="W"}];

val test43 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Fred", middle="W", last="Smith"}) =
	    [{first="Fred", last="Smith", middle="W"}, {first="Fredrick", last="Smith", middle="W"},
	     {first="Freddie", last="Smith", middle="W"}, {first="F", last="Smith", middle="W"}];


val test5 = card_color(Clubs, Num 2) = Black;

val test51 = card_color(Spades, Ace) = Black;

val test52 = card_color(Hearts, Jack) = Red;

val test53 = card_color(Diamonds, Queen) = Red;


val test6 = card_value(Clubs, Num 2) = 2;

val test61 = card_value(Clubs, Num 10) = 10;

val test62 = card_value(Clubs, Ace) = 11;

val test63 = card_value(Clubs, Jack) = 10;

val test64 = card_value(Clubs, Queen) = 10;

val test65 = card_value(Clubs, King) = 10;


val test7 = remove_card ([(Hearts, Ace)], (Hearts, Ace), IllegalMove) = [];

val test71 = remove_card ([(Hearts, Ace), (Spades, Num 2), (Diamonds, Queen), (Hearts, Ace)], (Hearts, Ace), IllegalMove) = [(Spades, Num 2), (Diamonds, Queen), (Hearts, Ace)];

val test72 = ((remove_card ([(Hearts, Ace), (Spades, Num 2), (Diamonds, Queen), (Hearts, Num 4)], (Spades, Ace), IllegalMove); false) handle IllegalMove => true)

val test73 = ((remove_card ([], (Hearts, Ace), IllegalMove); false) handle IllegalMove => true);


val test8 = all_same_color [(Hearts, Ace), (Hearts, Ace)] = true;

val test81 = all_same_color [(Hearts, Ace)] = true;

val test82 = all_same_color [] = true;

val test83 = all_same_color [(Hearts, Ace), (Hearts, Ace), (Diamonds, Ace), (Hearts, Num 2)] = true;

val test84 = all_same_color [(Hearts, Ace), (Hearts, Ace), (Clubs, Ace), (Hearts, Num 2)] = false;


val test9 = sum_cards [] = 0;

val test91 = sum_cards [(Clubs, Num 2)] = 2;

val test92 = sum_cards [(Clubs, Num 2),(Clubs, Num 2), (Clubs, Ace), (Clubs, Jack)] = 25;


val test10 = score ([(Hearts, Num 2),(Hearts, Num 4)],10) = 2;

val test101 = score ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4;

val test102 = score ([],10) = 5;

val test103 = score ([(Hearts, Ace),(Clubs, Ace)],10) = 36;

val test104 = score ([(Hearts, Num 6),(Clubs, Num 4)],10) = 0;


val test11 = officiate ([],[Draw], 15) = 7;

val test111 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [],
                        42)
             = 21;

val test112 = ((officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Hearts,Jack)],
                         42);
               false) 
               handle IllegalMove => true);

val test113 = officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Clubs, Jack)],
                         42) = 21;

val test114 = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6;

val test115 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        42)
              = 3;

val test116 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        19)
              = 4;

val test117 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        11)
             = 0;


val test12 = score_challenge ([(Hearts, Num 2),(Hearts, Num 4)],10) = 2;

val test121 = score_challenge ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4;

val test122 = score_challenge ([],10) = 5;

val test123 = score_challenge ([(Hearts, Ace),(Clubs, Ace)],10) = 6;

val test124 = score_challenge ([(Hearts, Ace),(Hearts, Ace)],12) = 0;

val test125 = score_challenge ([(Hearts, Ace),(Hearts, Ace)],10) = 3;

val test126 = score_challenge ([(Hearts, Num 6),(Clubs, Num 4)],10) = 0;            
             
val test127 = score_challenge([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)], 19) = 2;


val test13 = officiate_challenge ([],[Draw], 15) = 7;

val test131 = officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [],
                        42)
             = 21;

val test132 = ((officiate_challenge([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Hearts,Jack)],
                         42);
               false) 
               handle IllegalMove => true);

val test133 = officiate_challenge([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Clubs, Jack)],
                         42) = 21;

val test134 = officiate_challenge ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6;

val test135 = officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        42)
              = 3;

 officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        19);
val test136 = officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        19)
              = 2;

officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        11);
val test137 = officiate_challenge ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        11)
             = 0;
