(* Homework3 Simple Test*)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1a = only_capitals ["A","B","C"] = ["A","B","C"]
val test1b = only_capitals ["das","B","C"] = ["B","C"]

val test2a = longest_string1 ["A","bc","C"] = "bc"
val test2b = longest_string1 [] = ""
val test2c = longest_string1 ["A","bc","Cc"] = "bc"

val test3a = longest_string2 ["A","bc","C"] = "bc"
val test3b = longest_string2 ["A","bc","Cc"] = "Cc"

val test4a = longest_string3 ["A","bc","C"] = "bc"
val test4c = longest_string3 ["A","bc","Cc"] = "bc"
val test4d = longest_string4 ["A","bc","Cc"] = "Cc"

val test5 = longest_capitalized ["A","bc","C"] = "A"

val test6a = rev_string "abc" = "cba"
val test6b = rev_string "" = ""

val test7 = first_answer (fn x => if x > 3 then SOME x else NONE) [1,2,3,4,5] = 4

val test8a = all_answers (fn x => if x = 1 then SOME [x] else NONE) [2,3,4,5,6,7] = NONE
val test8b = all_answers (fn x => if x = 1 then SOME [x] else NONE) [1,1,1] = SOME [1,1,1]

val test9a = count_wildcards Wildcard = 1
val test9ab = (count_wildcards (TupleP [Wildcard,Wildcard])) = 2
val test9b = count_wild_and_variable_lengths (Variable("a")) = 1

val test9ca = count_some_var ("x", Variable("x")) = 1
val test9cb = count_some_var ("x", TupleP([Wildcard,Wildcard])) = 0

val test10a = check_pat (Variable("x")) = true
val test10b = check_pat (TupleP([Variable("x"),Variable("x")])) = false
val test10c = check_pat (TupleP([Variable("x"),Variable("z")])) = true
val test10d = check_pat Wildcard = true

val test11a = match (Const(1), UnitP) = NONE
val test11b = match (Const(1), ConstP(1)) = SOME []
val test11c = match (Const(1), ConstP(2)) = NONE

val test12a = first_match Unit [UnitP] = SOME []
val test12b = first_match Unit [ConstP 1] = NONE
val test12c = first_match (Const 1) [Variable "baober"] = SOME [("baober",Const 1)]
