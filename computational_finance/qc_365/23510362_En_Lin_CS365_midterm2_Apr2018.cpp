//******* you can also run this cpp file to see the answer ******


//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

//################################ Part 1 answers ################################
// ------------------------------------Question 1-----------------------------------------------
// ************************** question 1 **************************
// Q:Calculate the interpolated spot rate r cfr using constant forward rate interpolation for t = 0.66.
// State your answer as a percentage to 2 decimal places.
//
// r_0.5 = 5.2351
// r_1.0 = 5.0362
// r_0.66 = ((1-0.16/0.5)*r_0.5*0.5 + (0.16/0.5)*r_1.0)/0.66 = 5.14
//
//
//
//
//
// ************************** question 2 **************************
// Q:Prove that 5.0% ≤ r_cfr ≤ 6.0% for any student id
//
// Proof:
// according to equation r_cfr = [(1-lamda)*ri*(ti - t0) + (lamda)*rj*(tj - t0)]/(t- t0)
// we can see that no matter what value lamda has, r_cfr will increase as either ri or rj increases
// r_cfr decreases when either ri or rj decreases
// r_0.5_max = r_1.0_max = 5.9999
// r_0.5_min = r_1.0_min = 5.0000
//
// r_cfr_min = 5.0000[(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)
// r_cfr_max = 5.9999[(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)
//
// now I claim that [(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0) = 1
// proof: (1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)
//      = [(ti-t0)*(tj-t)/(tj-ti) + (t-ti)*(tj-t0)/(tj-ti)] /(t- t0)
//      = [(t-t0)(tj-ti)/(tj-ti)]/(t-t0) = 1
// that means r_cfr_min = 5.0000,  r_cfr_max = 5.9999
//
//
//
//
//
// ------------------------------------Question 2-----------------------------------------------
// ***************************** 2.1 European call ********************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:  (S0 - 1)e^(r(T-t0)) - ST  when  ST < K
// profit/loss:  (S0 - 1)e^(r(T-t0)) - K   when  ST >= K
// in all scenario when  (S0 - 1)e^(r(T-t0)) - K > 0  and you excercise the option this strategy makes profit
//
// Q:Find a scenario where this strategy leads to a loss
// in all scenario when (S0 - 1)e^(r(T-t0)) - K < 0 and ST > (S0 - 1)e^(r(T-t0)) in expiration date, we make a loss
//
//
//
//
//
// ***************************** 2.2 American call ********************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:  (S0 - 1)e^(r(t-t0)) - St  when  St < K
// profit/loss:  (S0 - 1)e^(r(t-t0)) - K   when  St >= K
// t is any time between  [t0, T]
// in all scenario when  (S0 - 1)e^(r(t-t0)) - K > 0  and you excercise the option this strategy makes profit
//
// Q:Find a scenario where this strategy leads to a loss
// during [t0, T], (S0 - 1)e^(r(t-t0)) - K < 0 and St > (S0 - 1)e^(r(t-t0))  is always true,
// then you make a loss
//
//
//
//
//
// ********************************  2.3 European put *****************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:  K - e^r(T-t0) - ST  when  ST < K
// loss:  e^r(T-t0)  when  ST >= K
// in all scenario when  ST < K - e^r(T-t0)  this strategy makes profit
//
// Q:Find a scenario where this strategy leads to a loss
//
// in all scenario when  ST > K - e^r(T-t0)  this strategy makes a loss
//
//
//
//
//
// ***************************** 2.4 American put ********************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:  K - e^r(t-t0) - St    when  St < K
// loss:  e^r(t-t0)                    when  St >= K
// t is any time between  [t0, T]
// in all scenario when  St < K - e^r(t-t0)  and you excercise the option, this strategy lead to profit
//
// Q:Find a scenario where this strategy leads to a loss
//
// during [t0,T], St > K - e^r(t-t0) is always true, then you make a loss
//
//
//
//
//
// ------------------------------------Question 3-----------------------------------------------
// ******************************* 3.1 European call *********************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:  ST + (3 - 98)e^(0.05) = ST - 99.87   when ST < 100
// profit:  100 + (3 - 98)e^(0.05) = 0.129  when ST >= 100
// when ST  > 99.87 this strategy leads to a profit
//
// Q:Find a scenario where this strategy leads to a loss
//
// when ST  < 99.87 this strategy leads to a loss
//
//
//
//
//
// ******************************** 3.2 European put *********************************
// Q:Find a scenario where this strategy leads to a profit
//
// profit/loss:   (p + S0)e^r - K =  6.178      when ST < 100
// profit:   (p + S0)e^r - ST = 106.178 -ST     when ST >= 100
// when ST  < 106.178 this strategy leads to a profit
//
// Q:Find a scenario where this strategy leads to a loss
//
// when ST  > 106.178 this strategy leads to a loss
//
//
//
//
//
// ------------------------------------Question 4-----------------------------------------------
// ******************************** 4.1 investor A ***********************************
// Q:Calculate (or state) the money paid/recieved by A every day,starting from day 0, until A's portfolio isclosed out
// A paid 100 on day 0, recieved 0.07 on day 3, recieved S5 on day 5
//
// Q:Calculate the total profit/loss for A after selling the stock
// A total profit/loss: S5-100 + 0.07
//
// Q:State on which day A makes that profit/loss
// A receive profit from dividend on day 3, and A has profit/loss S5-100 on day 5
//
//
//
//
// ******************************** 4.2 investor B ***********************************
// Calculate the money paid/received every day in B’s mark to market account, until B’s portfolio is closed out.
// day       +pay/-receive
//   0                   0
//   1                   0
//   2  F2-101.55(receive)
//   3      F3-F2(receive)
//   4      F4-F3(receive)
//   5      F5-F4(receive)
//
// Q:Calculate the total money paid by B after closing the futures contract
// B paid 101.55 in total
//
// Q:State what B receives in exchange for closeing the futures contract
// B receives a stock  on day 5
//
//
//
//
//
// ******************************** 4.3 investor C ***********************************
// Q:is it possible for C to lock in a guaranteed profit?
// If C knows up front the price at day 1 would be 101.55
// C can short the fututure at day 0 and long the future at day 1
// in reality, C does not know the price of future on day 1 up front
//
// Q:State all the trades performed
// on day 5 C deliver the stock and recieve cash S5
//
// Q:Calculate the total profit/loss for C
// total profit/loss for C = 103.25 - S5
//
// Q:State on which day C makes that profit/loss
// the profit/loss is calculate every day through mark to market account
//
//
//
//
// ******************************** 4.4 investor D ***********************************
// Q: Calculate paid/received every day in D's mark to market account
// day       +pay/-receive
//   1                   0
//   2  F2-101.55(receive)
//   3      F3-F2(receive)
//
// Q:Calculate the total profit/loss for D after selling the future
// total profit of D = F3-101.55
//
// Q:State on which day D makes that profit/loss
// the profit/loss is made on day 2, day 3 through mark to market account
//
// Q:State the trades (stock/cash/futures) which happen for D on day 5
// nothing happens for D, D has sold the future on day 3 to close out his position
//
//
//
//
// ------------------------------------Question 5-----------------------------------------------
// ************************************** 5.1 ***********************************************
// Q:State which position you will take if portfolio consists
// short one European call with strike 99
// long one European put with strike 99
// long one forward contract with F = 99
//
// I will take short position
//
// Q:Explain
// If I go short, I will get guarantee profit 1 * e^0.05
// I go short at time t0, save 1 to my bank account
// At T, suppose ST >= 99
// European call will excercise against me, I sell a stock ST at price 99
// European put expires worthless
// forward contract was settled, I totally paid 99 for a stock ST,
// I end up with profit e^0.05 = 1.05
//
// At T, suppose ST <99
// I excercise the European put, to settle forward contract, I will pay total 99 to buy stock ST
// I end up with profit e^0.05 = 1.05
//
//
//
//
//
// ******************************** 5.2 ***********************************
// Q:State which pisition you will take
// long one European call with strike 101
// short one European put with strike 101
// short one forward contract with F = 102
//
// If I take long position, The profit = 1 - (price of portfolio)*e^0.05
// when price of portfolio = 1
// the profit/loss of long position = 1 - 1.051 = -0.051
// This is a zero sum game,
//  I chose to go short this portfolio
//
// when price of portfolio = 0.9
// the profit/loss of long position = 1 - 0.946 = 0.0538
// I chose to go long this portfolio
//
// when price of portfolio = 1.1
// the profit/loss of long position = 1 - 1.156 = -0.156
// This is a zero sum game,
//  I chose to go short this portfolio
//
//
//
//
//
// ******************************** 5.3 ***********************************
// Q:State which pisition you will take if portfolio consist
// long one European call with strike 99.5
// long one European put with strike 100.5
//
// The profit for long position:
// 100.5 - ST - (price of portfolio) * e ^0.05   when ST < 99.5
// 1 - (price of portfolio) * e ^0.05            when 99.5<= ST <= 100.5
// ST - 99.5 - (price of portfolio) * e ^0.05    when ST > 100.5
//
// So now price of portfolio = 0.9
// 1 - (price of portfolio) * e ^0.05  = 0.054  > 0
// So I will take long position, I will always have positive profit
//
//
//
//
//
// Q:State which pisition you will take if portfolio consist
// long one American call with strike 99.5
// long one American put with strike 100.5
//
// I will take long position for this portfolio
//
// Explain:
// the minumum profit happens when when 99.5<= ST <= 100.5
// profit: 1 - (price of portfolio) * e ^0.05(t-t0)
//      >= 1 - (price of portfolio) * e ^0.05(T-t0)                = 0.0118
// So If I Go long for this portfolio, I have guarantee profit
// Beside, If excercise two options in the portfolio today right away, I get profit 0.06
//
// Q: If you buy the portfolio, state the trades you will perform with it and when you will execute those trades
// when I execute those trade, American options  that is in the money will be excercised
// American options  that is out the money will expire worthless
// I will excercise this portfolio right away
//
//
//
//
// Q:State which pisition you will take if portfolio consist
// long one American call with strike 99.5
// long one American put with strike 100.5
//
// the portfolio price = 0.98
// I will take long position for this portfolio
// Explain:
// If I excercise two options in the package right away, I will get profit 0.02
//
// Q: If you buy the portfolio, state the trades you will perform with it and when you will execute those trades
// when I execute those trade, American options  that is in the money will be excercised
// American options  that is out the money will expire worthless
// I will excercise this portfolio right away
//
//
//
//
// ******************************** 5.4  **********************************
// Q: State which position you will take if
// A stock index has a current value of 1000, risk-free interest rate is 5%,
// index gas a dvidend yield 1.5%
// An American call option on stock index hass strike 920 and price 65
//
// I will take long position
//
// Explain: I will long the call option on index and excercise the option right away
// In this way I will have (1000-920) - 65 = 15$ profit
//
// Q:state the trades you will  perform and when
// I buy the call option at price 65 and excercise it right away, it will be a cash settlement,
// I will get (1000-920) = 80 profit in my account
//
//
//
//
// Q: State which position you will take if
// A stock index has a current value of 1000, risk-free interest rate is 5%,
// index gas a dvidend yield 1.5%
// An American put option on stock index hass strike 1050 and price 45
//
// I will take long position
//
// Explain: I will long the put option on index and excercise the option right away
// In this way I will have (1050-1000) - 45 = 5$ profit
//
// Q: state the trades you will  perform and when
// I buy the put option at price 45 $ and excercise it right away, it will be a cash settlement,
// I will get (1050-1000) = 50 profit in my account
//
//
//
//
// ------------------------------------Question 6-----------------------------------------------
// ******************** diagram 1 ***********************
// V(S0, t0) = c(0) + p(40)
//
// ******************** diagram 2 ***********************
// V(S0, t0) = c(20) - p(20) + p(40)
//
// ******************** diagram 3 ***********************
// V(S0, t0) = c(20) + p(40) - c(80)
//
// ******************** diagram 4 ***********************
// V(S0, t0) = 1/2c(0) +  c(40) + 1/2p(40) - 3/2c(80)
//
// ******************** diagram 5 ***********************
// V(S0, t0) = c(0) + p(20) - c(40) + 3/2c(60)
//
//
//
//
// ------------------------------------Question 7-----------------------------------------------
// *********************************** American call ******************************
// This strategy leads to :
// profit/loss:         (98)e^r(t-t0) - St     when St <= 100
// loss:                (98)e^r(t-t0) - 100    when St >  100
//
// Q:Find the general solotion leads to profit
// Even though as time growing, the profit diagram will shift upward, there is always a possible loss
// So, whenever St < 98e^r(t-t0), you are in the gain position
//
// Q:Find the general solotion leads to loss
// whenever St > 98e^r(t-t0), you are in the loss position
//
//
//
//
//
// *************************** American put **************************************
// This strategy leads to :
// loss:                100 - (102)e^r(t-t0)     when St <= 100
// loss/profit:         St -  (102)e^r(t-t0)     when St >  100
//
// Q:Find the general solotion leads to profit
// whenever St > (102)e^r(t-t0), you are in the gain position
//
// Q:Find the general solotion leads to loss
// whenever St < (102)e^r(t-t0), you are in the loss position
//
//
//


#include <iostream>
#include <cmath>
using namespace std;
// #############################  Part 2  ######################################
// ########################### helper function for calculate discount factor ############

void clearSpace(){
    printf("\n\n\n\n");
}
//#############################  Part 3  ######################################
int main(){
//#############################  test for question  ########################

cout << "check comments section at the top of the file to see questions number"<<endl;
cout << "------------------------------------Question 1-----------------------------------------------"<<endl;
cout << "************************** question 1 **************************" << endl;
cout << "Q:Calculate the interpolated spot rate r cfr using constant forward rate interpolation for t = 0.66."<<endl;
cout << "State your answer as a percentage to 2 decimal places."<<endl<<endl;
double r_05 =  5.0 + (2351 * 0.0001);
double r_10 = 5.0 + (362 * 0.0001);
double lamda1 = 0.16/0.5;
cout << "r_0.5 = " << 5.0 + (2351 * 0.0001) <<endl;
cout << "r_1.0 = " << 5.0 + (362 * 0.0001) << endl;
cout << "r_0.66 = ((1-0.16/0.5)*r_0.5*0.5 + (0.16/0.5)*r_1.0)/0.66 = ";
printf("%.2f\n\n",((1-lamda1)*r_05*0.5 + (lamda1)*r_10)/0.66 );
clearSpace();
cout << "************************** question 2 **************************" << endl;
cout << "Q:Prove that 5.0% ≤ r_cfr ≤ 6.0% for any student id"<<endl<<endl;
cout << "Proof: "<<endl;
cout << "according to equation r_cfr = [(1-lamda)*ri*(ti - t0) + (lamda)*rj*(tj - t0)]/(t- t0)"<<endl;
cout << "we can see that no matter what value lamda has, r_cfr will increase as either ri or rj increases" <<endl;
cout << "r_cfr decreases when either ri or rj decreases" <<endl;
cout << "r_0.5_max = r_1.0_max = 5.9999"<<endl;
cout << "r_0.5_min = r_1.0_min = 5.0000" << endl<<endl;
cout << "r_cfr_min = 5.0000[(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)" << endl;
cout << "r_cfr_max = 5.9999[(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)" << endl<<endl;
cout << "now I claim that [(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0) = 1" << endl;
cout << "proof: " << "(1-lamda)*(ti - t0) + (lamda)*(tj - t0)]/(t- t0)" <<endl;
cout << "     = [(ti-t0)*(tj-t)/(tj-ti) + (t-ti)*(tj-t0)/(tj-ti)] /(t- t0)" <<endl;
cout << "     = [(t-t0)(tj-ti)/(tj-ti)]/(t-t0) = 1" <<endl;
cout << "that means r_cfr_min = 5.0000,  r_cfr_max = 5.9999"<<endl<<endl;
clearSpace();
cout << "------------------------------------Question 2-----------------------------------------------"<<endl;
cout << "***************************** 2.1 European call ********************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;
cout << "profit/loss:  (S0 - 1)e^(r(T-t0)) - ST  when  ST < K" <<endl;
cout << "profit/loss:  (S0 - 1)e^(r(T-t0)) - K   when  ST >= K" <<endl;
cout << "in all scenario when  (S0 - 1)e^(r(T-t0)) - K > 0  and you excercise the option this strategy makes profit"<< endl<<endl;

cout <<"Q:Find a scenario where this strategy leads to a loss"<<endl;
cout << "in all scenario when (S0 - 1)e^(r(T-t0)) - K < 0 and ST > (S0 - 1)e^(r(T-t0)) in expiration date, we make a loss"<<endl<<endl;
clearSpace();

cout << "***************************** 2.2 American call ********************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;

cout << "profit/loss:  (S0 - 1)e^(r(t-t0)) - St  when  St < K" <<endl;
cout << "profit/loss:  (S0 - 1)e^(r(t-t0)) - K   when  St >= K" <<endl;
cout << "t is any time between  [t0, T]" <<endl;
cout << "in all scenario when  (S0 - 1)e^(r(t-t0)) - K > 0  and you excercise the option this strategy makes profit"<< endl<<endl;

cout <<"Q:Find a scenario where this strategy leads to a loss"<<endl;
cout << "during [t0, T], (S0 - 1)e^(r(t-t0)) - K < 0 and St > (S0 - 1)e^(r(t-t0))  is always true," <<endl;
cout << "then you make a loss"<<endl<<endl;
clearSpace();

cout << "********************************  2.3 European put *****************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;

cout << "profit/loss:  K - e^r(T-t0) - ST  when  ST < K" <<endl;
cout << "loss:  e^r(T-t0)  when  ST >= K" <<endl;
cout << "in all scenario when  ST < K - e^r(T-t0)  this strategy makes profit"<< endl<<endl;

cout << "Q:Find a scenario where this strategy leads to a loss"<<endl<<endl;
cout << "in all scenario when  ST > K - e^r(T-t0)  this strategy makes a loss"<< endl<<endl;
clearSpace();

cout << "***************************** 2.4 American put ********************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;

cout << "profit/loss:  K - e^r(t-t0) - St    when  St < K" <<endl;
cout << "loss:  e^r(t-t0)                    when  St >= K" <<endl;
cout << "t is any time between  [t0, T]" <<endl;
cout << "in all scenario when  St < K - e^r(t-t0)  and you excercise the option, this strategy lead to profit"<< endl<<endl;

cout << "Q:Find a scenario where this strategy leads to a loss"<<endl<<endl;
cout << "during [t0,T], St > K - e^r(t-t0) is always true, then you make a loss"<< endl<<endl;
clearSpace();

cout << "------------------------------------Question 3-----------------------------------------------"<<endl;
cout << "******************************* 3.1 European call *********************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;
cout << "profit/loss:  ST + (3 - 98)e^(0.05) = ST - 99.87   when ST < 100" <<endl;
cout << "profit:  100 + (3 - 98)e^(0.05) = 0.129  when ST >= 100"<<endl;
cout << "when ST  > 99.87 this strategy leads to a profit" <<endl<<endl;

cout << "Q:Find a scenario where this strategy leads to a loss"<<endl<<endl;
cout << "when ST  < 99.87 this strategy leads to a loss" <<endl<<endl;
clearSpace();

cout << "******************************** 3.2 European put *********************************" << endl;
cout << "Q:Find a scenario where this strategy leads to a profit"<<endl<<endl;
cout << "profit/loss:   (p + S0)e^r - K =  6.178      when ST < 100" <<endl;
cout << "profit:   (p + S0)e^r - ST = 106.178 -ST     when ST >= 100"<<endl;
cout << "when ST  < 106.178 this strategy leads to a profit" <<endl<<endl;

cout << "Q:Find a scenario where this strategy leads to a loss"<<endl<<endl;
cout << "when ST  > 106.178 this strategy leads to a loss" <<endl<<endl;
clearSpace();
cout << "------------------------------------Question 4-----------------------------------------------"<<endl;
cout << "******************************** 4.1 investor A ***********************************"<<endl;
cout << "Q:Calculate (or state) the money paid/recieved by A every day,\
starting from day 0, until A's portfolio isclosed out" <<endl;
cout << "A paid 100 on day 0, recieved 0.07 on day 3, recieved S5 on day 5" <<endl<<endl;

cout << "Q:Calculate the total profit/loss for A after selling the stock"<<endl;
cout << "A total profit/loss: S5-100 + 0.07" <<endl<<endl;
cout << "Q:State on which day A makes that profit/loss" <<endl;
cout << "A receive profit from dividend on day 3, and A has profit/loss S5-100 on day 5"<<endl;
clearSpace();

cout << "******************************** 4.2 investor B ***********************************"<<endl;
cout << "Calculate the money paid/received every day in B’s mark to market account, until B’s \
portfolio is closed out." <<endl;
printf("%3s%20s\n","day", "+pay/-receive");
printf("%3s%20s\n","0", "0");
printf("%3s%20s\n","1", "0");
printf("%3s%20s\n","2", "F2-101.55(receive)");
printf("%3s%20s\n","3", "F3-F2(receive)");
printf("%3s%20s\n","4", "F4-F3(receive)");
printf("%3s%20s\n\n","5", "F5-F4(receive)");

cout << "Q:Calculate the total money paid by B after closing the futures contract"<<endl;
cout << "B paid 101.55 in total" <<endl<<endl;
cout << "Q:State what B receives in exchange for closeing the futures contract"<<endl;
cout << "B receives a stock  on day 5"<<endl<<endl;
clearSpace();

cout << "******************************** 4.3 investor C ***********************************"<<endl;
cout << "Q:is it possible for C to lock in a guaranteed profit?"<<endl;
cout << "If C knows up front the price at day 1 would be 101.55"<<endl;
cout << "C can short the fututure at day 0 and long the future at day 1" <<endl;
cout << "in reality, C does not know the price of future on day 1 up front" <<endl<<endl;
cout << "Q:State all the trades performed"<<endl;
cout << "on day 5 C deliver the stock and recieve cash S5"<<endl<<endl;
cout << "Q:Calculate the total profit/loss for C" <<endl;
cout << "total profit/loss for C = 103.25 - S5"<<endl<<endl;

cout << "Q:State on which day C makes that profit/loss"<<endl;
cout << "the profit/loss is calculate every day through mark to market account"<<endl;
clearSpace();

cout << "******************************** 4.4 investor D ***********************************"<<endl;
cout << "Q: Calculate paid/received every day in D's mark to market account"<<endl;
printf("%3s%20s\n","day", "+pay/-receive");
printf("%3s%20s\n","1", "0");
printf("%3s%20s\n","2", "F2-101.55(receive)");
printf("%3s%20s\n\n","3", "F3-F2(receive)");
cout << "Q:Calculate the total profit/loss for D after selling the future"<<endl;
cout << "total profit of D = F3-101.55" <<endl<<endl;
cout << "Q:State on which day D makes that profit/loss" << endl;
cout << "the profit/loss is made on day 2, day 3 through mark to market account"<<endl<<endl;
cout << "Q:State the trades (stock/cash/futures) which happen for D on day 5"<<endl;
cout << "nothing happens for D, D has sold the future on day 3 to close out his position"<<endl;
clearSpace();

cout << "------------------------------------Question 5-----------------------------------------------"<<endl;
cout << "************************************** 5.1 ***********************************************"<<endl;

cout << "Q:State which position you will take if portfolio consists"<<endl;
cout << "short one European call with strike 99"<<endl;
cout << "long one European put with strike 99"<<endl;
cout << "long one forward contract with F = 99"<<endl<<endl;

cout << "I will take short position"<<endl<<endl;
cout << "Q:Explain"<<endl;
cout << "If I go short, I will get guarantee profit 1 * e^0.05 "<<endl;

cout << "I go short at time t0, save 1 to my bank account"<<endl;
cout << "At T, suppose ST >= 99"<<endl;
cout << "European call will excercise against me, I sell a stock ST at price 99"<<endl;
cout << "European put expires worthless"<<endl;
cout << "forward contract was settled, I totally paid 99 for a stock ST,  "<<endl;
cout << "I end up with profit e^0.05 = 1.05" <<endl<<endl;
cout << "At T, suppose ST <99"<<endl;
cout << "I excercise the European put, to settle forward contract, I will pay total 99 to buy stock ST"<<endl;
cout << "I end up with profit e^0.05 = 1.05" <<endl<<endl;
clearSpace();

cout << "******************************** 5.2 ***********************************"<<endl;
cout << "Q:State which pisition you will take "<<endl;
cout << "long one European call with strike 101"<<endl;
cout << "short one European put with strike 101"<<endl;
cout << "short one forward contract with F = 102"<<endl<<endl;


cout << "If I take long position, The profit = 1 - (price of portfolio)*e^0.05" <<endl;
cout << "when price of portfolio = 1" << endl;
cout << "the profit/loss of long position = 1 - 1.051 = -0.051"<<endl;
cout << "This is a zero sum game,\n I chose to go short this portfolio"<<endl<<endl;
cout << "when price of portfolio = 0.9" << endl;
cout << "the profit/loss of long position = 1 - 0.946 = 0.0538"<<endl;
cout << "I chose to go long this portfolio"<<endl<<endl;
cout << "when price of portfolio = 1.1" << endl;
cout << "the profit/loss of long position = 1 - 1.156 = -0.156"<<endl;
cout << "This is a zero sum game,\n I chose to go short this portfolio"<<endl<<endl;
clearSpace();

cout << "******************************** 5.3 ***********************************"<<endl;
cout << "Q:State which pisition you will take if portfolio consist"<<endl;
cout << "long one European call with strike 99.5"<<endl;
cout << "long one European put with strike 100.5"<<endl<<endl;
cout << "The profit for long position: "<<endl;
cout << "100.5 - ST - (price of portfolio) * e ^0.05   when ST < 99.5"<<endl;
cout << "1 - (price of portfolio) * e ^0.05            when 99.5<= ST <= 100.5" <<endl;
cout << "ST - 99.5 - (price of portfolio) * e ^0.05    when ST > 100.5" <<endl<<endl;
cout << "So now price of portfolio = 0.9" << endl;
cout << "1 - (price of portfolio) * e ^0.05  = 0.054  > 0"<<endl;
cout << "So I will take long position, I will always have positive profit"<<endl<<endl;
clearSpace();
cout << "Q:State which pisition you will take if portfolio consist"<<endl;
cout << "long one American call with strike 99.5"<<endl;
cout << "long one American put with strike 100.5"<<endl<<endl;
cout << "I will take long position for this portfolio" <<endl<<endl;
cout << "Explain:"<<endl;
cout << "the minumum profit happens when when 99.5<= ST <= 100.5" <<endl;
cout << "profit: 1 - (price of portfolio) * e ^0.05(t-t0)"<<endl;
cout << "     >= 1 - (price of portfolio) * e ^0.05(T-t0) \
               = 0.0118"<<endl;
cout << "So If I Go long for this portfolio, I have guarantee profit"<<endl;
cout << "Beside, If excercise two options in the portfolio today right away, I get profit 0.06"<<endl<<endl;
cout << "Q: If you buy the portfolio, state the trades you will perform with it and when you will execute those trades"<<endl;
cout << "when I execute those trade, American options  that is in the money will be excercised"<<endl;
cout << "American options  that is out the money will expire worthless"<<endl;
cout << "I will excercise this portfolio right away"<<endl;
clearSpace();

cout << "Q:State which pisition you will take if portfolio consist"<<endl;
cout << "long one American call with strike 99.5"<<endl;
cout << "long one American put with strike 100.5"<<endl<<endl;
cout << "the portfolio price = 0.98" <<endl;
cout << "I will take long position for this portfolio" <<endl;
cout << "Explain:"<<endl;
cout << "If I excercise two options in the package right away, I will get profit 0.02" <<endl<<endl;
cout << "Q: If you buy the portfolio, state the trades you will perform with it and when you will execute those trades"<<endl;
cout << "when I execute those trade, American options  that is in the money will be excercised"<<endl;
cout << "American options  that is out the money will expire worthless"<<endl;
cout << "I will excercise this portfolio right away"<<endl;

clearSpace();

cout << "******************************** 5.4  **********************************"<<endl;
cout << "Q: State which position you will take if"<<endl;
cout << "A stock index has a current value of 1000, risk-free interest rate is 5%, \n" \
        "index gas a dvidend yield 1.5%\n" \
        "An American call option on stock index hass strike 920 and price 65"<<endl<<endl;
cout << "I will take long position"<<endl<<endl;
cout << "Explain: I will long the call option on index and excercise the option right away"<<endl;
cout << "In this way I will have (1000-920) - 65 = 15$ profit"<<endl<<endl;
cout << "Q:state the trades you will  perform and when" <<endl;
cout << "I buy the call option at price 65 and excercise it right away, it will be a cash settlement,"<< endl;
cout << "I will get (1000-920) = 80 profit in my account "<<endl;
clearSpace();

cout << "Q: State which position you will take if"<<endl;
cout << "A stock index has a current value of 1000, risk-free interest rate is 5%, \n" \
        "index gas a dvidend yield 1.5%\n" \
        "An American put option on stock index hass strike 1050 and price 45"<<endl<<endl;

cout << "I will take long position"<<endl<<endl;
cout << "Explain: I will long the put option on index and excercise the option right away"<<endl;
cout << "In this way I will have (1050-1000) - 45 = 5$ profit"<<endl<<endl;
cout << "Q: state the trades you will  perform and when" <<endl;
cout << "I buy the put option at price 45 $ and excercise it right away, it will be a cash settlement, "<< endl;
cout << "I will get (1050-1000) = 50 profit in my account "<<endl;

clearSpace();

cout << "------------------------------------Question 6-----------------------------------------------"<<endl;
cout << "******************** diagram 1 ***********************" << endl;
cout << "V(S0, t0) = c(0) + p(40)" << endl<<endl;

cout << "******************** diagram 2 ***********************" << endl;
cout << "V(S0, t0) = c(20) - p(20) + p(40)" << endl<<endl;

cout << "******************** diagram 3 ***********************"<<endl;
cout << "V(S0, t0) = c(20) + p(40) - c(80)" << endl<<endl;

cout << "******************** diagram 4 ***********************"<<endl;
cout << "V(S0, t0) = 1/2c(0) +  c(40) + 1/2p(40) - 3/2c(80)" << endl<<endl;

cout << "******************** diagram 5 ***********************"<<endl;
cout << "V(S0, t0) = c(0) + p(20) - c(40) + 3/2c(60)" << endl;
clearSpace();

cout << "------------------------------------Question 7-----------------------------------------------"<<endl;
cout << "*********************************** American call ******************************" << endl;
cout << "This strategy leads to :  "<<endl;
cout << "profit/loss:         (98)e^r(t-t0) - St     when St <= 100 "<<endl;
cout << "loss:                (98)e^r(t-t0) - 100    when St >  100 "<<endl<<endl;

cout << "Q:Find the general solotion leads to profit" <<endl;
cout << "Even though as time growing, the profit diagram will shift upward, there is always a possible loss" <<endl;
cout << "So, whenever St < 98e^r(t-t0), you are in the gain position"<<endl<<endl;
cout << "Q:Find the general solotion leads to loss" <<endl;
cout << "whenever St > 98e^r(t-t0), you are in the loss position"<<endl<<endl;
clearSpace();

cout << "*************************** American put **************************************" << endl;
cout << "This strategy leads to :  "<<endl;
cout << "loss:                100 - (102)e^r(t-t0)     when St <= 100 "<<endl;
cout << "loss/profit:         St -  (102)e^r(t-t0)     when St >  100 "<<endl<<endl;

cout << "Q:Find the general solotion leads to profit" <<endl;
cout << "whenever St > (102)e^r(t-t0), you are in the gain position"<<endl<<endl;
cout << "Q:Find the general solotion leads to loss" <<endl;
cout << "whenever St < (102)e^r(t-t0), you are in the loss position"<<endl<<endl;






}
