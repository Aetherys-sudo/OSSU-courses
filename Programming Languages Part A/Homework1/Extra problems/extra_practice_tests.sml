use "extra_practice.sml";

val test1 = alternate([1,2,3,4]) = ~2;

val test2 = alternate([]) = 0;

val test3 = alternate([1]) = 1;

val test4 = min_max([]) = (0, 0);

val test5 = min_max([1,2]) = (1, 2);

val test6 = min_max([1]) = (1, 1);

val test7 = min_max([1, 7, 0, 3]) = (0, 7);

val test8 = cumsum([]) = [];

val test9 = cumsum([1]) = [1];

val test10 = cumsum([1,2]) = [1,3];

val test11 = cumsum([1,2,3]) = [1,3,6];

val test12 = cumsum([1,4,20]) = [1,5,25];

val test13 = greeting(NONE) = "Hello there, you!";

val test14 = greeting(SOME "Vlad") = "Hello there, Vlad!";

val test15 = repeat([], []) = [];

val test16 = repeat([1], [0]) = [];

val test17 = repeat([1], [2]) = [1,1];

val test18 = repeat([1,2,3], [4,0,3]) = [1,1,1,1,3,3,3];

val test19 = addOpt(NONE, SOME 3) = NONE;

val test20 = addOpt(SOME 3, NONE) = NONE;

val test21 = addOpt(NONE, NONE) = NONE;

val test22 = addOpt(SOME 2, SOME 4) = SOME 6;

val test23 = addAllOpt([]) = NONE;

val test24 = addAllOpt([SOME 1]) = SOME 1;

val test25 = addAllOpt([SOME 1, NONE]) = SOME 1;

val test26 = addAllOpt([NONE]) = NONE;

val test27 = addAllOpt([NONE,NONE]) = NONE;

val test28 = addAllOpt([SOME 1, NONE, SOME 3]) = SOME 4;

val test29 = any([]) = false;

val test30 = any([false]) = false;

val test31 = any([false, false, false]) = false;

val test32 = any([false, false, false, true]) = true;

val test33 = all([]) = true;

val test34 = all([true]) = true;

val test35 = all([false]) = false;

val test36 = all([true, true, true, false]) = false;

val test37 = all([true, true, true]) = true;

val test38 = zip([], []) = [];

val test39 = zip([1], []) = [];

val test40 = zip([], [1]) = [];

val test41 = zip([1],[1]) = [(1, 1)];

val test42 = zip([1, 2, 3], [4, 6]) = [(1, 4), (2, 6)];
			 
val test43 = zipRecycle([], []) = [];

val test44 = zipRecycle([1], []) = [];

val test45 = zipRecycle([], [1]) = [];

val test46 = zipRecycle([1], [1]) = [(1, 1)];

val test47 = zipRecycle([1,2], [1]) = [(1, 1)];

val test48 = zipRecycle([1,2], [1, 2]) = [(1, 1), (2, 2)];

val test49 = zipRecycle([1, 2, 3], [1, 2, 3, 4, 5, 6, 7]) = [(1, 1), (2, 2), (3, 3), (1, 4), (2, 5), (3, 6), (1, 7)];

val test50 = zipOpt([], []) = NONE;

val test51 = zipOpt([1], []) = NONE;

val test52 = zipOpt([], [1]) = NONE;

val test53 = zipOpt([1], [1]) = SOME [(1, 1)];

val test54 = zipOpt([1,2], [1]) = NONE;

val test55 = zipOpt([1,2], [1, 2]) = SOME [(1, 1), (2, 2)];

val test56 = zipOpt([1, 2, 3], [1, 2, 3, 4, 5, 6, 7]) = NONE;

val test57 = zipOpt([1, 2, 3], [1, 2, 3]) = SOME [(1, 1), (2, 2), (3, 3)];

val test58 = lookup([], "a") = NONE;

val test59 = lookup([("b", 1)], "a") = NONE;

val test60 = lookup([("a", 1), ("b", 2)], "b") = SOME 2;
		       
val test61 = splitup([]) = ([], []);

val test62 = splitup([1]) = ([1], []);

val test63 = splitup([~1]) = ([], [~1]);

val test64 = splitup([1, 2]) = ([1, 2], []);

val test65 = splitup([1, ~1, 2, ~2]) = ([1, 2], [~1, ~2]);

val test66 = splitAt([], 3) = ([], []);

val test67 = splitAt([1], 3) = ([], [1]);

val test68 = splitAt([~1], 3) = ([], [~1]);

val test69 = splitAt([1, 2], 3) = ([], [1, 2]);

val test70 = splitAt([1, 4, 2, 6, 3, 15], 3) = ([4, 6, 3, 15], [1, 2]);

val test71 = isSorted([]) = true;

val test72 = isSorted([1]) = true;

val test73 = isSorted([1, 2]) = true;

val test74 = isSorted([1,2,3,~1]) = false;

val test75 = isSorted([1,2,3,4,5]) = true;

val test76 = isAnySorted([]) = true;

val test77 = isAnySorted([1]) = true;

val test78 = isAnySorted([1, 2]) = true;

val test79 = isAnySorted([2, 1]) = true;

val test80 = isAnySorted([1,2,3,~1]) = false;

val test81 = isAnySorted([1,2,3,4,5]) = true;

val test82 = isAnySorted([5,4,3,2,1]) = true;

val test83 = sortedMerge([], []) = [];

val test84 = sortedMerge([1],[]) = [1];

val test85 = sortedMerge([],[1,2]) = [1,2];

val test855 = sortedMerge([2],[1]) = [1,2];

val test86 = sortedMerge([1, 4, 7], [5, 8, 9]) = [1, 4, 5, 7, 8, 9];

val test87 = qsort([]) = [];

val test88 = qsort([1]) = [1];

val test89 = qsort([1,2]) = [1,2];

val test90 = qsort([2,3,1,4,7,8,9]) = [1,2,3,4,7,8,9];

val test91 = divide([]) = ([], []);

val test92 = divide([1]) = ([1], []);

val test93 = divide([1,2]) = ([1], [2]);

val test94 = divide([1,2,3,4,5,6,7]) = ([1,3,5,7], [2,4,6]);

val test95 = not_so_quick_sort([]) = [];

val test96 = not_so_quick_sort([1]) = [1];

val test97 = not_so_quick_sort([1,2]) = [1,2];

val test98 = not_so_quick_sort([2,1]) = [1,2];

val test99 = not_so_quick_sort([2,3,1,4,7,8,9]) = [1,2,3,4,7,8,9];

val test100 = fullDivide(2, 40) = (3, 5);

val test101 = fullDivide(3, 10) = (0, 10);

val test102 = factorize(26) = [(2, 1), (13, 1)];

val test102 = factorize(20) = [(2, 2), (5, 1)];

val test103 = factorize(36) = [(2, 2), (3, 2)];

val test104 = factorize(1) = [];

val test105 = factorize(2) = [(2, 1)];

val test106 = factorize(3) = [(3, 1)];

val test107 = factorize(4) = [(2, 2)];

val test108 = multiply([]) = 1;

val test109 = multiply([(2, 2)]) = 4;

val test110 = multiply([(2,4), (5,2)]) = 400;

all_products([(2,2), (5,1)]);
val test111 = all_products([(2,2), (5,1)]) = [1,2,4,5,10,20];

all_products([(5,1), (7,1)]);
val test111 = all_products([(5,1), (7,1)]) = [1,5,7,35];

all_products([(2,3)]);
val test111 = all_products([(2,3)]) = [1,2,4,8];

all_products([(2,1),(3,1),(5,1)]);
val test111 = all_products([(2,1),(3,1),(5,1)]) = [1,2,3,5,6,10,15,30];

all_products([(2,2), (5,1),(7,1)]);
val test111 = all_products([(2,2), (5,1), (7,1)]) = [1,2,4,5,7,10,14,20,28,35,70,140];

