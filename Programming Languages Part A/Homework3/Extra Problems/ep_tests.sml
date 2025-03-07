use "ep.sml";

val test1 = compose_opt (fn x => if x > 5
				 then SOME x
				 else NONE)
			(fn st => if String.size(st) > 5
				  then SOME (String.size(st))
				  else NONE)
			"values" = SOME 6;

val test2 = do_until (fn x => x div 2) (fn x => x mod 2 = 1) 48 = 3;

val test3 = fact 5 = 120;

val test4 = fixed_point (fn x => if x = 3
				 then x
				 else x - 1)
			4 = 3;

val test5 = map2 (fn x => x * 2) (2, 4) = (4, 8);

val test6 = app_all (fn x => [x * 2, x * 4]) (fn x => [1, x, x + 1]) 2 = [2, 4, 4, 8, 6, 12]; 

val test7 = fold (fn (x, y) => x + y) 0 [1,2,3,4] = 10;

val test8 = partition (fn x => x > 1) [1,2,3,4,1,1,1,2] = ([2,4,3,2],[1,1,1,1]);

val test9 = unfold (fn n => if n = 0 then NONE else SOME(n, n-1)) 5 = [5, 4, 3, 2, 1];

val test10 = fact_unfold 5 = 120;

val test11 = map_foldr (fn x => x + 3) [1,2,3,4] = [4,5,6,7];

val test12 = filter_foldr (fn x => x >= 3) [1,2,3,4] = [3,4];

val test13 = map_tree (fn x => x + 3) (Node (4, Leaf, Leaf)) = Node (7, Leaf, Leaf);

val test14 = map_tree (fn x => x + 3) (Node (4, Node (7, Leaf, (Node (3, Leaf, Leaf))), (Node (13, (Node (16, Leaf, Leaf)), Leaf)))) = (Node (7, Node (10, Leaf, (Node (6, Leaf, Leaf))), (Node (16, (Node (19, Leaf, Leaf)), Leaf))));
