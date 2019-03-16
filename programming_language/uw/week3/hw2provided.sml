(* Dan Grossman, Coursera PL, HW2 Provided Code *)

(* if you use this function to compare two strings (returns true if the same
   string), then you avoid several of the functions in problem 1 having
   polymorphic types that may be confusing *)
fun same_string(s1 : string, s2 : string) =
    s1 = s2

(* put your solutions for problem 1 here *)

(* you may assume that Num is always used with values 2, 3, ..., 10
   though it will not really come up *)
datatype suit = Clubs | Diamonds | Hearts | Spades
datatype rank = Jack | Queen | King | Ace | Num of int
type card = suit * rank

datatype color = Red | Black
datatype move = Discard of card | Draw

exception IllegalMove

(* put your solutions for problem 2 here *)
fun all_except_option (s, sl) =
  case sl of
    [] => NONE
  | x::xs =>
          case (same_string(s,x), all_except_option(s,xs)) of
            (true,x) => SOME xs
          | (false,NONE) => NONE
          | (false,SOME y) => SOME (x::y)
fun get_substitutions1 (sll, s) =
  case sll of
    [] => []
  | (x::xs) =>
          case (all_except_option(s,x)) of
            (NONE) => get_substitutions1(xs,s)
          | (SOME x) => x @ get_substitutions1(xs,s)

fun get_substitutions2 (sll,s) =
let fun aux(sll,s,acc) =
  case sll of
    [] => acc
  | (x::xs) =>
          case (all_except_option(s,x)) of
            (NONE) => aux(xs,s,acc)
          | (SOME x) => aux(xs,s,x @ acc)
in
  aux(sll,s,[])
end

fun similar_names (sll,{first=f,middle=m,last=l}) =
  let val x = get_substitutions1(sll,f)
  in
    let fun get_all_except_names(x,{first=f,middle=m,last=l}) =
      case x of
      [] => []
     | x::xs => {first = x, middle = m, last = l} ::get_all_except_names(xs,{first=f,middle=m,last=l})
    in
      {first=f,middle=m,last=l}::get_all_except_names(x,{first=f,middle=m,last=l})
    end
  end

fun card_color card =
case card of
   (Clubs,_) => Black
 | (Spades,_) => Black
 | _ => Red

fun card_value card =
case card of
  (_,Ace) => 11
| (_,Num x) => x
| _ => 10


fun remove_card (cs,c,e) =
  let fun aux(cs,c,e,acc) =
   case cs of
   [] => raise e
   | x::xs => case x = c of
               true => acc @ xs
              |false => aux(xs,c,e, x::acc)
  in
  aux(cs,c,e,[])
  end

fun all_same_color c =
  case c of
  [] => true
  | x::[] => true
  | x::y::xs =>
    card_color(x) = card_color(y)  andalso all_same_color (y::xs)

fun sum_cards cl =
let fun aux (cl,acc)  =
  case cl of
    [] => acc
  | x::xs => aux(xs,acc + (card_value x))
in
  aux(cl,0)
end

fun score (cl,goal) =
let val sum = sum_cards cl
in
  case (sum > goal, all_same_color cl) of
    (true,true) =>  (3*(sum - goal)) div 2
  | (false,true) => (goal - sum) div 2
  | (true,false) => (3*(sum - goal))
  | (false,false)=> goal - sum
end

fun officiate (cl,mv,goal) =
let fun next_move (hl,mv,goal,cl) =
case sum_cards hl > goal of
  true => score(hl,goal)
|false =>
  case (mv,cl) of
    ([],_)               => score(hl,goal)
  | ((Discard c)::xs, _) => next_move(remove_card (hl,c,IllegalMove),xs,goal,cl)
  | (Draw::xs,[])        => score(hl,goal)
  | (Draw::xs,x::xs')    => next_move(x::hl,xs,goal,xs')
in
  next_move ([],mv,goal,cl)
end
