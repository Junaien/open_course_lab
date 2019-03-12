(* Coursera Programming Languages, Homework 3, Provided Code *)

exception NoAnswer


val only_capitals = List.filter (fn x => Char.isUpper(String.sub(x,0)))

fun compareLength1 (x, y) =
	case String.size(x) > String.size(y) of
	true  => x
| false => y

fun compareLength2 (x, y) =
	case String.size(x) >= String.size(y) of
	true  => x
| false => y

fun compareLength f (x, y) =
	case f(String.size(x),String.size(y)) of
	true  => x
| false => y

val longest_string1 = foldl compareLength1 ""
val longest_string2 = foldl compareLength2 ""

fun longest_string_helper f = foldl (compareLength f) ""
val longest_string3 = longest_string_helper (fn (x, y) => x > y)
val longest_string4 = longest_string_helper (fn (x ,y) => x >= y)

val longest_capitalized = longest_string1 o only_capitals

fun rev_string xs =
let val cl = String.explode xs
in
	String.implode (foldl (fn (x,y) => x::y) [] cl)
end

fun first_answer f xss  =
	case xss of
	[] => raise NoAnswer
| x::xs =>
						case f x of
            NONE => first_answer f xs
          | SOME v => v

fun all_answers f xss =
let
	fun all_answers_aux f acc xss =
		case xss of
		[] => SOME acc
	| x::xs =>  case f x of
							         NONE => NONE
										 | SOME c => all_answers_aux f (c @ acc) xs
in
	all_answers_aux f [] xss
end


datatype pattern = Wildcard
		 | Variable of string
		 | UnitP
		 | ConstP of int
		 | TupleP of pattern list
		 | ConstructorP of string * pattern

datatype valu = Const of int
	      | Unit
	      | Tuple of valu list
	      | Constructor of string * valu

fun g f1 f2 p =
    let
	val r = g f1 f2
    in
	case p of
	    Wildcard          => f1 ()
	  | Variable x        => f2 x
	  | TupleP ps         => List.foldl (fn (p,i) => (r p) + i) 0 ps
	  | ConstructorP(_,p) => r p
	  | _                 => 0
    end

val count_wildcards  = g (fn () => 1) (fn x => 0)
val count_wild_and_variable_lengths =  g (fn () => 1) (fn x => String.size x)

fun count_some_var (s,p) = g (fn () => 0) (fn x => case x = s of true => 1 | false => 0) p
fun check_pat p =
	let fun get_all_vname p =
		case p of
		    Wildcard => []
	    | Variable x => [x]
			| TupleP ps => List.foldl (fn(p,acc) => (get_all_vname p @ acc)) [] ps
			| ConstructorP(_,p) => get_all_vname p
			| _ => []

			fun distinct l =
				case l of
				[] => true
			| x::xs => (not (List.exists (fn k => k = x) xs)) andalso distinct xs
	in
		distinct (get_all_vname p)
	end

fun match (v,p) =
	case (p,v) of
	  (Wildcard,_)   => SOME []
	| (Variable x,_) => SOME [(x,v)]
	| (TupleP ps,Tuple vl)  => if length vl = length ps then
																let val x = ListPair.zip(vl,ps)
																in
																	all_answers match x
																end
															else NONE
	| (UnitP,Unit)     => SOME []
	| (ConstP x,Const n)   => if n = x then SOME [] else NONE
	| (ConstructorP(s1,p),Constructor(s2,k)) => if s1 = s2 then match(k,p) else NONE
	| _ => NONE

(**** for the challenge problem only ****)

fun first_match v pl =
	let fun m a =
		match (v,a)
	in
		SOME (first_answer m pl) handle NoAnswer => NONE
	end

datatype typ = Anything
	     | UnitT
	     | IntT
	     | TupleT of typ list
	     | Datatype of string

(**** you can put all your code here ****)
