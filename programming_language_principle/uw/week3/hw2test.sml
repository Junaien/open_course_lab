(* Homework2 Simple Test *)
(* These are basic test cases. Passing these tests does not guarantee that your code will pass the actual homework grader *)
(* To run the test, add a new line to the top of this file: use "homeworkname.sml"; *)
(* All the tests should evaluate to true. For example, the REPL should say: val test1 = true : bool *)

val test1a = all_except_option ("string", ["string"]) = SOME []
val test1b = all_except_option("sss", ["string"]) = NONE
val test1c = all_except_option("string", ["string","s"]) = SOME ["s"]

val test2a = get_substitutions1 ([["foo"],["there"]], "foo") = []
val test2b = get_substitutions1([["Fred","Fredrick"],["Jeff","Jeffrey"],["Geoff","Jeff","Jeffrey"]],"Jeff") = ["Jeffrey","Geoff","Jeffrey"]
val test2c = get_substitutions1 ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]],"Fred") = ["Fredrick","Freddie","F"]

val test3 = get_substitutions2 ([["foo"],["there"]], "foo") = []

val test4 = similar_names ([["Fred","Fredrick"],["Elizabeth","Betty"],["Freddie","Fred","F"]], {first="Fred", middle="W", last="Smith"}) =
	    [{first="Fred", last="Smith", middle="W"}, {first="Fredrick", last="Smith", middle="W"},
	     {first="Freddie", last="Smith", middle="W"}, {first="F", last="Smith", middle="W"}]

val test5 = card_color (Clubs, Num 2) = Black

val test6 = card_value (Clubs, Num 2) = 2

val test7a = remove_card ([(Hearts, Ace)], (Hearts, Ace), IllegalMove) = []
val test7b = remove_card ([(Hearts, Ace),(Clubs,Num 3)], (Hearts, Ace), IllegalMove) = [(Clubs,Num 3)]
val test7c = remove_card ([(Clubs,Num 2)], (Hearts, Ace), IllegalMove) handle IllegalMove => []

val test8a = all_same_color [(Hearts, Ace), (Diamonds, Ace)] = true
val test8b = all_same_color [(Clubs, Ace), (Hearts, Ace)] = false
val test8c = all_same_color [(Clubs,Ace),(Spades,Ace),(Diamonds,Ace)] = false
val test8d = all_same_color [] = true
val test9a = sum_cards [(Clubs, Num 2),(Clubs, Num 2)] = 4
val test9b = sum_cards [(Clubs, Num 4)] = 4
val test9c = sum_cards [] = 0

val test10a = score ([(Hearts, Num 2),(Clubs, Num 4)],10) = 4
val test10b = score ([(Hearts, Num 4),(Clubs, Num 4),(Hearts, Num 4)],10) = 6
val test10c = score ([(Hearts, Num 3),(Diamonds, Num 4),(Diamonds, Num 4)],10)  = 1
val test10d = score ([],10)  = 5

val test11a = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw], 15) = 6
val test11b = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw,Draw], 15) = 9
val test11c = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[], 15) = 7
val test11d = officiate ([(Hearts, Num 2),(Clubs, Num 4)],[Draw,Draw,Draw], 15) = 9


(* val test12 = officiate ([(Clubs,Ace),(Spades,Ace),(Clubs,Ace),(Spades,Ace)],
                        [Draw,Draw,Draw,Draw,Draw],
                        42)
             = 3 *)

(* val test13 = ((officiate([(Clubs,Jack),(Spades,Num(8))],
                         [Draw,Discard(Hearts,Jack)],
                         42);
               false)
              handle IllegalMove => true) *)
