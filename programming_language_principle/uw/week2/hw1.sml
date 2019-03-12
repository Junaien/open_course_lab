fun is_older (date1: int*int*int, date2: int*int*int) =
    if(#1 date1 < #1 date2)
    then true
    else if (#1 date1 > #1 date2)
    then false
    else
      if(#2 date1 < #2 date2)
      then true
      else if (#2 date1 > #2 date2)
      then false
      else
        if(#3 date1 < #3 date2)
        then true
        else if (#3 date1 > #3 date2)
        then false
        else
          false

fun number_in_month (dates: (int*int*int) list,month: int) =
  if null dates
  then 0
  else
    let val num = number_in_month(tl dates, month)
    in
      if #2 (hd dates) = month
      then num + 1
      else
      num
    end

fun number_in_months (dates: (int*int*int) list,months: int list) =
  if null dates orelse null months
  then 0
  else
    number_in_month(dates, hd months) + number_in_months(dates, tl months)
fun dates_in_month (dates: (int*int*int) list, month: int) =
  if null dates
  then []
  else
    let val d = dates_in_month(tl dates, month)
    in
      if #2 (hd dates) = month
      then (hd dates) :: d
      else
        d
    end

fun dates_in_months (dates: (int*int*int) list,months: int list) =
  if null dates orelse null months
  then []
  else
    dates_in_month(dates, hd months) @ dates_in_months(dates, tl months)

fun get_nth (xs : string list, n:int) =
  if null xs orelse n <= 0
  then ""
  else if n = 1
  then hd xs
  else
    get_nth(tl xs,n-1)
fun date_to_string (date:int*int*int) =
  let
    val dates = [ "January", "February", "March", "April",
                "May", "June", "July", "August", "September", "October", "November", "Decembe"]
  in
    get_nth(dates,(#2 date)) ^ " " ^ Int.toString(#3 date) ^ ", " ^ Int.toString(#1 date)
  end
fun number_before_reaching_sum (sum:int, l:int list) =
  if null l orelse (hd l) >= sum
  then 0
  else
    number_before_reaching_sum(sum - (hd l),tl l) + 1

fun what_month(n:int) =
  let
    val date_num = [ 31, 28, 31, 30,
                31, 30,31,31, 30, 31,30,31]
  in
    number_before_reaching_sum(n,date_num) + 1
  end

fun month_range(day1:int, day2:int) =
if day1 > day2
then []
else
  what_month(day1) :: month_range(day1 +1 , day2);

fun oldest (date: (int*int*int) list) =
if null date
then NONE
else
  let
    fun oldest_non_empty(date: (int*int*int) list) =
      if null (tl date)
      then hd date
      else
        let val answer =  oldest_non_empty(tl date)
        in
          if is_older(hd date,answer)
          then hd date
          else answer
        end
  in
    SOME (oldest_non_empty(date))
  end
