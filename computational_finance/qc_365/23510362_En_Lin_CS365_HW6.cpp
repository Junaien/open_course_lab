//******* you can also run this cpp file to see the answer ******


//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

//################################ Part 1 answers ################################
// ------------------------------------6.2 Put-call parity-----------------------------------------------
// ***** question 1 *****
// Q:Calculate the price of a European put option with the same strike and expirationas the call
//
// according to put call parity proof: c(S,t) - p(S,t) = S -PV(k)
// p(S,t) = c(S,t) - S + PV(k) = 4.07417
//
// ***** question 2 *****
// Q:Calculate the price of a European call option with the same strike and expirationas the put.
//
// c(S,t) = p(S,t) + S*e^-q(T-t) - PV(k) = 8.07303
//
// ***** question 3 *****
// Q:Calculate the value of the strike price of the options
//
// K = (p(S,t) - c(S,t) + S)*e^r(T-t) = 106.178
//
// ------------------------------------6.3 Put-call parity & option pricing bounds----------------------------------
// ***** question 1 *****
// Q:Calculate the present value of the strike price of the options.
//
// pv(K) = k*e^-0.08 = 19.3854
//
// ***** question 2 *****
// Q:Calculate the minimum fair value the call option must have, to be consistent with the above data and the inequalities in Lecture 7.
//
// in this case (S*e^(r(T-t0)) - k ) / e^(r(T-t0)) = 0.614557  > 0
// So I think c(S,t) >= 0.614557 must hold
// To express my thought further
// I claim that c(S,t) >=  (S*e^(r(T-t0)) - k ) / e^(r(T-t0)) must hold
// Assume in t0  c(S,t) < (S*e^(r(T-t0)) - k ) / e^(r(T-t0))
// 1) I can short sell a stock in price S , and get cash  S
// 2) I also long the call option for the price c
// In time T ,
// 3)the cash compounded to  (S-c)*e^(r(T-t0))  >= 0  because c <= S
// 4)and I also owe a Stock ST
// So If St > K
// 5)I will excercise the option and pay cash K, i get the stock to cover St I owed
// So I will left with money (S-c)*e^(r(T-t0)) - K > S*e^(r(T-t0)) - (S*e^(r(T-t0)) - K ) -K = 0
// positive profit
// If St <= K
// 6) I will not excercise option and I will buy stock at St to cover
// So I end up with  (S-c)*e^(r(T-t0)) - St > S*e^(r(T-t0)) - (S*e^(r(T-t0)) - k ) - St
// = K-St >=0
// also positive profit
//
// ***** question 3 *****
// Q:Calculate the maximum fair value of the put option, to be consistent with the above data and the inequalities in Lecture 7.  Calculate the corresponding fair value of the call option, when the put option has its maximum value.
//
// p(S,t) <= pv(K)
// So the maximum fair value of the put option is 19.3854
//
// ***** question 4 *****
// Q: Calculate the maximum fair value of the call option,  to be consistent with the above data and the inequalities in Lecture 7.  Calculate the corresponding fair value of the put option, when the call option has its maximum value.
//
// c(S,t) <= S = 20
// so maximum of the call = 20
//
// ------------------------------------6.5 Rational option pricing #1--------------------------------------
// ***** question 1 *****
// Q:How much cash do we have in the bank at time t0?
// S0 - S0 + 1 = 1
//
// ***** question 2 *****
// Q:Calculate the profit/loss of the above trading strategy.
// we need to cover  the short position before, so we need to long St at T
//  we end up with profit 1- St = 0.5
//
// ***** question 3 *****
// Q:Calculate the profit/loss of the trading strategy if the option is exercised
//
// we receive the stock and cover the short position before, and pay K
// the loss then will be K - 1 = 99
//
// ***** question 4 *****
// Calculate the profit/loss of the trading strategy if the option is not exercised.
// then we have to buy back the stock and cover the short position
// the loss will be  1 - ST = 101
//
// Summary:
// profit/loss = 1 - ST   when we did not excercise option
// loss = 99   when ST > K, and you decide to excercise
// ------------------------------------6.6 Rational option pricing #2--------------------------------------
// ***** question 1 *****
// Q:Explain why we must have C(S0,t0)≥ S0−Kfor an Americal call option.
// if this does not hold we can long C(S0, T0) and excercise it immediately
// this will give guaranttee profit
//
// ***** question 2 *****
// Q:How much cash do we have in the bank at time t0?
// We  have S0 - S0 + 1.5 = 1.5 in bank
//
// ***** question 3 *****
// Q:Calculate the profit/loss of the above trading strategy.
// We have to long St to cover the short position before
// So we end up with profit = 1.5 - 0.8 = 0.7
//
// ***** question 4 *****
// Q:Calculate the profit/loss of the trading strategy if the option is exercised.
// we end up with loss 1.5 - 100 = 98.5
//
// ***** question 5 *****
// Calculate the profit/loss of the trading strategy if the option is exercised early
// if we excercise early, we receive the stock and pay K
// our profit = 1.5 - K = -98.5 It means we always lose 98.5
//
// Summary:
// profit/loss = 1 - St   when  we did not excercise the American option
// loss = 98.5   when S(t) > K and we did exercise the American option
//
//
//
//
#include <iostream>
#include <cmath>
using namespace std;
// #############################  Part 2  ######################################
// ########################### helper function for calculate discount factor ############

double cum_norm(double x)
{
  const double root = sqrt(0.5);
  return 0.5*(1.0 + erf(x*root));
}
double calculateD(double S, double K, double T, double t, double r, double v){
  return (log(S/K) + r*(T-t))/(v*sqrt(T-t)) +  0.5 * v * sqrt(T-t);
}

//#############################  Part 3  ######################################
int main(){
//#############################  test for question  ########################

cout << "check comments section at the top of the file to see questions number"<<endl;
cout << "------------------------------------6.2 Put-call parity-----------------------------------------------"<<endl;
cout << "***** question 1 *****" << endl;
cout << "Q:Calculate the price of a European put option with the same strike and expirationas the call" << endl<<endl;
cout << "according to put call parity proof: c(S,t) - p(S,t) = S -PV(k)"<<endl;
cout << "p(S,t) = c(S,t) - S + PV(k) = "<< 8 - 100 + 101*exp(-0.1*(0.5))<<endl<<endl;


cout << "***** question 2 *****" << endl;
cout << "Q:Calculate the price of a European call option with the same strike and expirationas the put." << endl<<endl;
cout << "c(S,t) = p(S,t) + S*e^-q(T-t) - PV(k) = "<< 4 + 100*exp(-0.03*0.75) - 101*exp(-0.1*(0.75))<<endl<<endl;


cout << "***** question 3 *****" << endl;
cout << "Q:Calculate the value of the strike price of the options" << endl<<endl;
cout << "K = (p(S,t) - c(S,t) + S)*e^r(T-t) = "<< (7 - 6 + 100)*exp(0.05)<<endl<<endl;

cout << "------------------------------------6.3 Put-call parity & option pricing bounds----------------------------------"<<endl;

cout << "***** question 1 *****" << endl;
cout << "Q:Calculate the present value of the strike price of the options." << endl<<endl;
cout << "pv(K) = k*e^-0.08 = " << 21*exp(-0.08*1)<< endl<<endl;


cout << "***** question 2 *****" << endl;
cout << "Q:Calculate the minimum fair value the call option must have, to be consistent with the above data and the inequalities in Lecture 7." << endl<<endl;


cout << "in this case (S*e^(r(T-t0)) - k ) / e^(r(T-t0)) = 0.614557  > 0 "<<endl;
cout << "So I think c(S,t) >= 0.614557 must hold"<<endl;;
cout << "To express my thought further"<<endl;;
cout << "I claim that c(S,t) >=  (S*e^(r(T-t0)) - k ) / e^(r(T-t0)) must hold"<<endl;;
cout << "Assume in t0  c(S,t) < (S*e^(r(T-t0)) - k ) / e^(r(T-t0))"<<endl;;
cout << "1) I can short sell a stock in price S , and get cash  S"<<endl;;
cout << "2) I also long the call option for the price c"<<endl;;
cout << "In time T ,"<<endl;;
cout << "3)the cash compounded to  (S-c)*e^(r(T-t0))  >= 0  because c <= S"<<endl;;
cout << "4)and I also owe a Stock ST"<<endl;;
cout << "So If St > K"<<endl;;
cout << "5)I will excercise the option and pay cash K, i get the stock to cover St I owed"<<endl;;
cout << "So I will left with money (S-c)*e^(r(T-t0)) - K > S*e^(r(T-t0)) - (S*e^(r(T-t0)) - K ) -K = 0"<<endl;
cout << "positive profit"<<endl;;
cout << "If St <= K"<<endl;;
cout << "6) I will not excercise option and I will buy stock at St to cover"<<endl;;
cout << "So I end up with  (S-c)*e^(r(T-t0)) - St > S*e^(r(T-t0)) - (S*e^(r(T-t0)) - k ) - St"<<endl;;
cout << "= K-St >=0"<<endl;;
cout << "also positive profit" << endl<<endl;

cout << "***** question 3 *****" << endl;
cout << "Q:Calculate the maximum fair value of the put option, to be consistent with the \
above data and the inequalities in Lecture 7.  Calculate the corresponding fair \
value of the call option, when the put option has its maximum value." << endl<<endl;

cout<<"p(S,t) <= pv(K)"<<endl;
cout<<"So the maximum fair value of the put option is "<<21 *exp(-0.08) <<endl<<endl;

cout << "***** question 4 *****" << endl;
cout << "Q: Calculate the maximum fair value of the call option,  to be consistent with the \
above data and the inequalities in Lecture 7.  Calculate the corresponding fair \
value of the put option, when the call option has its maximum value." <<endl<<endl;

cout << "c(S,t) <= S = "<< 20 <<endl;
cout << "so maximum of the call = 20 "<<endl<<endl;



cout << "------------------------------------6.5 Rational option pricing #1--------------------------------------"<<endl;
cout << "***** question 1 *****" << endl;
cout << "Q:How much cash do we have in the bank at time t0?"<<endl;
cout <<"S0 - S0 + 1 = 1"<<endl<<endl;

cout << "***** question 2 *****" << endl;
cout << "Q:Calculate the profit/loss of the above trading strategy."<<endl;
cout << "we need to cover  the short position before, so we need to long St at T"<<endl;
cout <<" we end up with profit 1- St = 0.5" << endl<<endl;

cout << "***** question 3 *****" << endl;
cout <<"Q:Calculate the profit/loss of the trading strategy if the option is exercised"<<endl<<endl;
cout <<"we receive the stock and cover the short position before, and pay K"<<endl;
cout << "the loss then will be K - 1 = 99"  << endl<<endl;

cout << "***** question 4 *****" << endl;
cout <<"Calculate the profit/loss of the trading strategy if the option is not exercised."<<endl;
cout <<"then we have to buy back the stock and cover the short position" << endl;
cout << "the loss will be  1 - ST = 101"<<endl<<endl;


cout <<"Summary: " <<endl;
cout << "profit/loss = 1 - ST   when we did not excercise option"<<endl;
cout << "loss = 99   when ST > K, and you decide to excercise"<<endl;

cout << "------------------------------------6.6 Rational option pricing #2--------------------------------------"<<endl;
cout << "***** question 1 *****" << endl;
cout << "Q:Explain why we must have C(S0,t0)≥ S0−Kfor an Americal call option."<<endl;
cout << "if this does not hold we can long C(S0, T0) and excercise it immediately" <<endl;
cout <<"this will give guaranttee profit" <<endl<<endl;

cout << "***** question 2 *****" << endl;
cout << "Q:How much cash do we have in the bank at time t0?" << endl;
cout << "We  have S0 - S0 + 1.5 = 1.5 in bank"<<endl<<endl;

cout << "***** question 3 *****" << endl;
cout << "Q:Calculate the profit/loss of the above trading strategy." << endl;
cout << "We have to long St to cover the short position before" <<endl;
cout << "So we end up with profit = 1.5 - 0.8 = 0.7" <<endl<<endl;

cout << "***** question 4 *****" << endl;
cout << "Q:Calculate the profit/loss of the trading strategy if the option is exercised." << endl;
cout << "we end up with loss 1.5 - 100 = 98.5" <<endl<<endl;

cout << "***** question 5 *****" << endl;
cout << "Calculate the profit/loss of the trading strategy if the option is exercised early" << endl;
cout << "if we excercise early, we receive the stock and pay K" <<endl;
cout << "our profit = 1.5 - K = -98.5 It means we always lose 98.5" << endl<<endl;


cout <<"Summary: " <<endl;
cout << "profit/loss = 1 - St   when  we did not excercise the American option"<<endl;
cout << "loss = 98.5   when S(t) > K and we did exercise the American option" <<endl;








}
