val test1 = is_older ((1,2,3),(2,3,4)) = true
val test1p1 = is_older ((12,2,3),(2,3,4)) = false

val test2 = number_in_month ([(2012,2,28),(2013,12,1)],2) = 1
val test2p1 = number_in_month ([],2) = 0

val test3 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = 3
val test3p1 = number_in_months ([],[]) = 0
val test3p2 = number_in_months ([],[2,3,4]) = 0
val test3p3 = number_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = 0

val test4 = dates_in_month ([(2012,2,28),(2013,12,1)],2) = [(2012,2,28)]
val test4p1 = dates_in_month ([],2) = []

val test5 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[2,3,4]) = [(2012,2,28),(2011,3,31),(2011,4,28)]
val test5p1 = dates_in_months ([],[]) = []
val test5p2 = dates_in_months ([],[2,3,4]) = []
val test5p3 = dates_in_months ([(2012,2,28),(2013,12,1),(2011,3,31),(2011,4,28)],[]) = []

val test6 = get_nth (["hi", "there", "how", "are", "you"], 2) = "there"

val test7 = date_to_string (2013, 6, 1) = "June 1, 2013"

val test8 = number_before_reaching_sum (10, [1,2,3,4,5]) = 3
val test8p1 = number_before_reaching_sum (10, []) = 0

val test9 = what_month 70 = 3

val test10 = month_range (31, 34) = [1,2,2,2]

val test11 = oldest([(2012,2,28),(2011,3,31),(2011,4,28)]) = SOME (2011,3,31)
