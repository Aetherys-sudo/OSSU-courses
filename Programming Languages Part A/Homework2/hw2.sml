(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2;

(* put your solutions for problem 1 here *)

(* string * string list -> string option *)
(* search the given string in the list and return SOME list (with it not included) if it is present, NONE otherwise *)
			  
fun all_except_option(str, lst) =
    let
	fun all_except(str, lst) = 
	    case lst of
		[] => []
	      | x :: xs' =>
		if same_string(x, str)
		then xs'
		else x :: all_except(str, xs');
	val rst = all_except(str, lst);
				    
    in
	if length(rst) < length(lst)
	then SOME rst
	else NONE
    end;


(* string list list * string -> string list *)
(* return a list with all the possible substitutions *)

fun get_substitutions1(lst, s) =
    case lst of
	[] => []
      | x :: xs' =>
	let
	    val res = all_except_option(s, x);
	in
	    case res of
		NONE => get_substitutions1(xs', s)
	      | SOME y => y @ get_substitutions1(xs', s)
	end;

(* string list list * string -> string list *)
(* return a list with all the possible substitutions *)

fun get_substitutions2(lst, s) =
	let
	    fun tail_rec(lst, s, acc) =
		case lst of
		    [] => acc
		  | x :: xs' =>
		    let
			val res = all_except_option(s, x)
		    in
			case res of
			    NONE => tail_rec(xs', s, acc)
			  | SOME y => tail_rec(xs', s, y @ acc)
		    end;
	in
	    tail_rec(lst, s, [])
	end;

			
(* string list list * (string * string * string) -> (string * string * string) list *)
(* returns the list of all the names you can produce by substituting the first name with any available substitutions *)

fun similar_names(lst, name) =
    let
	fun getSubs{first=x, middle=y, last=z} =
	    get_substitutions1(lst, x);
	
	val subList = getSubs(name)

	fun update_name({first=x, middle=y, last=z}, s) =
	    {first=s, middle=y, last=z}
	
	fun buildRes(subs, name) =
	    case subs of
		[] => []
	      | y :: ys' => update_name(name, y) :: buildRes(ys', name);
    in
	name :: buildRes(subList, name)
    end;
			      
		

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int 
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw 

exception IllegalMove;

(* put your solutions for problem 2 here *)

(* card -> color *)
(* takes in a card and returns it's color *)
fun card_color(suit, rank) =
    case suit of
	Spades => Black
      | Clubs   => Black
      | Diamonds => Red
      | Hearts => Red


(* card -> int *)
(* takes in a card and returns it's value (numbered cards have their number, aces are 11, everything else is 10 *)

fun card_value(suit, rank) =
    case rank of
	Num rank => rank
      | Ace  => 11		    
      | _ => 10;

 
(* card list * card * exn -> card list *)
(* removes a card from the hand *)

fun remove_card(cs, c, e) =
    let
	fun remove(c, cs) = 
	    case cs of
		[] => raise e
	      | x :: xs' =>
		if c = x
		then xs'
		else x :: remove(c, xs');			    
    in
	remove(c, cs)
    end;	 

(* card list -> bool *)
(* returns true if the cards all have the same color, false otherwise *)

fun all_same_color(cs) =
    case cs of
	[] => true
      | x :: [] => true
      | x :: y :: xs' =>
	card_color(x) = card_color(y) andalso all_same_color(y::xs');

(* card list -> int *)
(* returns the sum of the values from the given list of cards *)

fun sum_cards(cs) =
    case cs of
	[] => 0
      | x :: xs' => card_value(x) + sum_cards(xs');
					   

(* card list * int -> int *)
(* takes in the list of held cards and the goal and computes the score *)

fun score(lst, goal) =
    let
	val sum = sum_cards(lst);
    in
	if all_same_color(lst)
	then
	    if sum > goal
	    then 3 * (sum - goal) div 2
	    else (goal - sum) div 2
	else
	    if sum > goal
	    then 3 * (sum - goal)
	    else (goal - sum)
    end;
					    
							    
(* card list * move list * int -> int *)
(* runs a game and returns the score *)

fun officiate(clst, mlst, goal) =
    let
	fun gamestate(cardList, moveList, heldCards) =
	    case moveList of
		[] => heldCards
	      | x :: xs' =>
		case x of
		    Discard card => gamestate(cardList, xs', remove_card(heldCards, card, IllegalMove))
		 |  Draw =>
		    case cardList of
			[] => heldCards
		      | y :: ys' =>
			if sum_cards(heldCards) >= goal
			then heldCards
			else gamestate(ys', xs', y :: heldCards)
				      
    in
	score(gamestate(clst, mlst, []), goal)
    end;
			
(* card list * int -> int *)
(* takes a list of held cards and returns the best possible score *)

fun score_challenge(lst, goal) =
    let
	val same_color = all_same_color(lst);

	fun preliminary_score(sum, goal) =
	    if sum > goal
	    then 3 * (sum - goal)
	    else (goal - sum)
	
	fun compute_score(sum, goal) =
	    let
		val preliminary_score = preliminary_score(sum, goal);
	    in
		if same_color
		then preliminary_score div 2
		else preliminary_score
	    end;
		
			     
	fun sum_cards(lst, currSum) =
	    case lst of
		[] => currSum
	      | (x, y) :: xs' =>
		if y = Ace
		then
		    if (preliminary_score(currSum + 11, goal) > preliminary_score(currSum + 1, goal))
		    then sum_cards(xs', currSum + 1)
		    else sum_cards(xs', currSum + 11)		  
		else sum_cards(xs', currSum + card_value(x,y))
						     
	val sum = sum_cards(lst, 0);
    in
	compute_score(sum, goal)
    end;


(* card list * move list * int -> int *)
(* runs a game and returns the score *)

fun officiate_challenge(clst, mlst, goal) =
    let
	fun gamestate(cardList, moveList, heldCards) =	
	    case moveList of
		[] => heldCards
	      | x :: xs' =>
		case x of
		    Discard card => gamestate(cardList, xs', remove_card(heldCards, card, IllegalMove))
		 |  Draw =>
		    case cardList of
			[] => heldCards
		      | (x, y) :: ys' =>
			if y = Ace
			then
			    if Int.min(sum_cards(heldCards) + 1, sum_cards(heldCards) + 11) >= goal
			    then heldCards
			    else gamestate(ys', xs', (x,y) :: heldCards)
			else
			    if sum_cards(heldCards) >= goal
			    then heldCards
			    else gamestate(ys', xs', (x,y) :: heldCards)
    in
	score_challenge(gamestate(clst, mlst, []), goal)
    end;
