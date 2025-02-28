use "ep.sml";

val test1 = pass_or_fail{grade=NONE, id="luca"} = fail;

val test2 = pass_or_fail{grade=SOME 74, id="me"} = fail;

val test3 = pass_or_fail{grade=SOME 75, id=1} = pass;

val test4 = pass_or_fail{grade=SOME 76, id=44} = pass;


val test5 = has_passed{grade=NONE, id="luca"} = false;

val test6 = has_passed{grade=SOME 74, id="me"} = false;

val test7 = has_passed{grade=SOME 75, id=1} = true;

val test8 = has_passed{grade=SOME 76, id=44} = true;


val test9 = number_passed([]) = 0;

val test10 = number_passed([{grade=NONE, id=4}]) = 0;

val test11 = number_passed([{grade=SOME 75, id=1}]) = 1;

val test12 = number_passed([{grade=NONE, id=2},{grade=SOME 74, id=3},{grade=SOME 75, id=1},{grade=SOME 76, id=44}]) = 2;


val test13 = number_misgraded([]) = 0;

val test14 = number_misgraded([(pass, {grade=NONE, id=1})]) = 1;

val test15 = number_misgraded([(fail, {grade=NONE, id=1})]) = 0;

val test16 = number_misgraded([(pass, {grade=NONE, id=1}), (pass, {grade=SOME 74, id=1}), (pass, {grade=SOME 75, id=1})]) = 2;

val test17 = number_misgraded([(fail, {grade=NONE, id=1}), (fail, {grade=SOME 74, id=1}), (fail, {grade=SOME 75, id=1})]) = 1;


val test18 = tree_height(leaf) = 0;

val test19 = tree_height(node {value=3, left=leaf, right=leaf}) = 1;

val test20 = tree_height(node {value=3, left=node {value=2, left=leaf, right=leaf}, right=leaf}) = 2;

val test21 = tree_height(node {value=3, left=node {value=15, left=node {value=4, left = node {value=10, left=leaf, right=leaf}, right=leaf}, right=leaf}, right=leaf}) = 4;


val test22 = sum_tree(leaf) = 0;

val test23 = sum_tree(node {value=3, left=leaf, right=leaf}) = 3;

val test24 = sum_tree(node {value=3, left=node {value=2, left=leaf, right=leaf}, right=leaf}) = 5;

val test25 = sum_tree(node {value=3, left=node {value=15, left=node {value=4, left = node {value=10, left=leaf, right=leaf}, right=leaf}, right=leaf}, right=leaf}) = 32;


val test26 = gardener(leaf) = leaf;

val test27 = gardener(node {value=prune_me, left=leaf, right=leaf}) = leaf;

val test28 = gardener(node {value=leave_me_alone, left=node {value=prune_me, left=leaf, right=leaf}, right=leaf}) = node {value=leave_me_alone, left=leaf, right=leaf}

val test29 = gardener(node {value=leave_me_alone, left=node {value=prune_me, left=node {value=leave_me_alone, left = node {value=leave_me_alone, left=leaf, right=leaf}, right=leaf}, right=leaf}, right=leaf}) = node {value=leave_me_alone, left=leaf, right=leaf};

val test30 = gardener(node {value=leave_me_alone, left=node {value=leave_me_alone, left=node {value=leave_me_alone, left = node {value=leave_me_alone, left=leaf, right=leaf}, right=leaf}, right=leaf}, right=leaf}) = node {value=leave_me_alone, left=node {value=leave_me_alone, left=node {value=leave_me_alone, left = node {value=leave_me_alone, left=leaf, right=leaf}, right=leaf}, right=leaf}, right=leaf};


val test31 = ((last([]); false) handle Empty => true);

val test32 = last(["a"]) = "a";

val test33 = last(["a", "b", "c"]) = "c";

val test34 = last(["a", "q", "w", "i"]) = "i";

val test35 = ((take([], 3); false) handle Subscript => true);

val test36 = take(["a"], 1) = ["a"];

val test37 = ((take(["a"], 3); false) handle Subscript => true);

val test38 = take(["a","b","c"], 1) = ["a"];

val test38 = take(["a","b","c"], 2) = ["a", "b"];

val test39 = ((drop([], 3); false) handle Subscript => true);

val test40 = drop(["a"], 1) = [];

val test41 = ((drop(["a"], 3); false) handle Subscript => true);

val test42 = drop(["a","b","c"], 1) = ["b", "c"];

val test43 = drop(["a","b","c"], 2) = ["c"];

val test44 = concat([[], []]) = [];

val test45 = concat([[], [1]]) = [1];

val test46 = concat([[1], [2]]) = [1, 2];

val test47 = concat([[1, 2, 3], [2], [1,2,4]]) = [1, 2, 3, 2, 1, 2, 4];

val test48 = getOpt(NONE, 1) = 1;

val test49 = getOpt(SOME 1, 3) = 1;

val test50 = is_positive(ZERO) = false;

val test51 = is_positive(SUCC ZERO) = true;

val test52 = is_positive(SUCC (SUCC ZERO)) = true;

val test53 = ((pred(ZERO); false) handle Negative => true);

val test54 = pred(SUCC ZERO) = ZERO;

val test55 = pred(SUCC (SUCC ZERO)) = SUCC ZERO;

val test56 = nat_to_int(ZERO) = 0;

val test57 = nat_to_int(SUCC (SUCC ZERO)) = 2;

val test58 = nat_to_int(SUCC (SUCC (SUCC (SUCC (SUCC ZERO))))) = 5;

val test59 = ((int_to_nat(~1); false) handle Negative => true);

val test60 = int_to_nat(0) = ZERO;

val test61 = int_to_nat(2) = SUCC (SUCC ZERO);

val test62 = int_to_nat(5) = SUCC (SUCC (SUCC (SUCC (SUCC ZERO))));

val test63 = add(ZERO, ZERO) = ZERO;

val test64 = add(ZERO, SUCC (SUCC ZERO)) = SUCC (SUCC ZERO);

val test65 = add(SUCC (SUCC ZERO), SUCC (SUCC (SUCC ZERO))) = SUCC (SUCC (SUCC (SUCC (SUCC ZERO))));

val test66 = sub(ZERO, ZERO) = ZERO;

val test67 = sub(SUCC (SUCC ZERO), ZERO) = SUCC (SUCC ZERO);

val test68 = sub(SUCC (SUCC (SUCC ZERO)), SUCC (SUCC (SUCC ZERO))) = ZERO;

val test69 = sub(SUCC (SUCC (SUCC (SUCC ZERO))), SUCC (SUCC (SUCC ZERO))) = SUCC ZERO;

val test70 = mult(ZERO, ZERO) = ZERO;

val test71 = mult(SUCC (SUCC ZERO), ZERO) = ZERO;

val test72 = mult((SUCC ZERO), SUCC (SUCC (SUCC ZERO))) = SUCC (SUCC (SUCC ZERO));

val test73 = mult(SUCC (SUCC (SUCC (SUCC ZERO))),(SUCC ZERO)) = SUCC (SUCC (SUCC (SUCC ZERO)));

val test74 = mult(SUCC (SUCC (SUCC ZERO)), SUCC (SUCC ZERO)) = SUCC (SUCC (SUCC (SUCC (SUCC (SUCC ZERO)))));

val test75 = less_than(ZERO, ZERO) = false;

val test76 = less_than(SUCC (SUCC ZERO), ZERO) = false;

val test77 = less_than((SUCC ZERO), SUCC (SUCC (SUCC ZERO))) = true;

val test78 = less_than(SUCC (SUCC (SUCC (SUCC ZERO))),(SUCC ZERO)) = false;

val test79 = less_than(SUCC (SUCC (SUCC ZERO)), SUCC (SUCC ZERO)) = false;

val test80 = less_than(SUCC (SUCC (SUCC ZERO)), SUCC (SUCC (SUCC (SUCC ZERO)))) = true;
		 


