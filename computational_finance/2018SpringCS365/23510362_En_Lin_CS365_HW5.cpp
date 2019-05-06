//******* you can also run this cpp file to see the answer ******

//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

//################################ Part 1 answers ################################

// --------------5.1.1 Long American call option-------------------
// 1. You are long an American call option
// 2. The call option has a strike of 102.
// 3. The market price of the stock is 107 today.
// 4. You exercise the option. State what you pay/receive.
//    *****I pay 102, I get the stock
//
// 5.State what the option writer pays/receives.
//   *****the option writer recieves 102 and he deliver the stock
//
// --------------5.1.2 Long European call option--------------
//1.  You are long a European call option.
//2.  The call option has a strike of 101.5.
//3.  The market price of the stock is 106 today (= expiration day).
//4.  You exercise the option.  State what you pay/receive.
//    *****I receive  a stock I pay 101.5
//
//5.  State what the option writer pays/receives.
//    *****option writer deliver the stock and receive 101.5

// --------------5.1.3    Short American call option 1--------------
// 1.You are short an American call option.
// 2.The call option has a strike of 48.
// 3.The market price of the stock is 52 today.
// 4.The option holder exercises the option. State what the option holder pays/receives.
//   ***** the option holder pays 48, and receive stock
//
// 5.State what the option writer (= you) pays/receives.
//   ***** option writer receive 48 and deliver the stock
//
// -------------------- 5.1.4    Short American call option 2 ----------------------------
// 1.  An American call option has a strike of 55.
// 2.  You sell the option to investor A at a price of 1.1.
// 3.  Therefore you are short an American call option .
// 4.  The stock price is 48 (on the day of the sale to A).
// 5.  The stock goes up to 52 and investor A sells the option to investor B for a price of 1.2.
// 6.  The stock goes up to 56 and investor B sells the option to investor C for a price of 1.5.
// 7.  Explain what B pays/receives if B exercises the option instead.
//     ***** if B excercises it,  B will receive stock and pay 55
//
// 8.  The stock price goes up to 58 and C exercises the option.  State what C pays/receives.
//     ***** C will receive stock and pay 55
//
// 9. The option is exercised against you. State what the option writer (= you) pays/receives on the day of exercise.
//     ***** option writer deliver the stock and get 55
//
// 10. Calculate the profit of investor A. Ignore interest rate compounding to answer this question.
//     ***** profit of A is 1.2 - 1.1 = 0.1 (ignorre interest rate)
//
// 11. Calculate the profit of investor B. Ignore interest rate compounding to answer this question.
//     ***** profit of B is 1.5 - 1.2 = 0.3 (ignorre interest rate)
//
// 12. Suppose the stock price was 51 on the day A sold the option to B.
//     Explain how the new stock price affects the profit of investor A.
//     ***** if A still sells B the option for the price of 1.2
//     ***** ,the new stock price does not affects the profit of A.
//
// 13. Suppose the stock price was 56.1 on the day B sold the option to C.
//     Explain how the new stock price affects the profit of investor B.
//     ***** if B still sells C the option for the price of 1.5
//     ***** , the new stock price does not affects the profit of B.
//
// 14. Suppose B holds the option and the stock price was 56.6 and the option price was 1.5 .
//     C wishes to buy the option from B (at a price of 1.5 ).
// Describe the choices available to B and explain what B should do
//     ***** B can sell the option to C or excercises the option and get the stock at price 55, and sell it
//     ***** B really should excercises the option and get the stock at price 55, and sell it
//     ***** this would give B profit (56.6 - 55)- 1.2 =  0.4  > 0.3


// -----------------5.1.5  Short European call option 1-----------------------------------
// 1.  You are short a European call option .
// 2.  The option has a strike of 84.  The stock price is 89 today (= expiration date).
// 3. The option holder exercises the option. State what the option holder pays/receives.
//    ***** the option holder receives stock and pay 84
//
// 4. State what the option writer (= you) pays/receives.
//    ***** option writer recieves 84 and deliver the stock
//
//
// ----------------5.1.6    Short European call option 2 ----------------------
// 1.  You are short a European call option .
// 2.  The option has a strike of 77.  The stock price is 75 today (= expiration date).
// 3. The option holder does not exercise the option. State what the option holder pays/receives.
//    ***** at expiration date, the option holder pays/receives nothing
//
// 4. State what the option writer (= you) pays/receives.
//    ***** at expiration date, the option writer pays/receives nothing


// -----------------------5.1.7    Short European call option 3--------------------------------
// 1. A European call option has a strike of 55.
// 2. You sell the option to investor A at a price of 1.1.
// 3. Therefore you are short a European call option.
// 4. The stock price is 48 (on the day of sale).
// 5. The stock goes up to 52 and investor A sells the option to investor B for a price of 1.2.
// 6. The stock goes up to 57 and investor B sells the option to investor C for a price of 1.5.
// 7. Explain if B can execute an arbitrage strategy in this situation.
//    Describe the trades of the arbitrage strategy.
//    ***** B can't.  B doesnot know the price of call option will go up
//
// 8. The stock price goes up to 58 on the expiration date and C exercises the option.
//    State what C pays/receives.
//    ***** C pays 55 and get the stock
//
// 9. The option is exercised against you. State what the option writer (= you) pays/receives on the day of exercise.
//    ***** Option writer deliver the stock and recieves 55
//
// 10. Calculate the profit of investor A. Ignore interest rate compounding.
//     ***** profit of A = 1.2 -1.1 = 0.1
//
// 11. Calculate the profit of investor B. Ignore interest rate compounding.
//     ***** profit of B = 1.5 - 1.2 = 0.3
// 12. Suppose the stock price was 51 on the day A sold the option to B.
//     Explain how the new stock price affects the profit of investor A.
//     ***** if A still sells B the option for the price of 1.2
//     ***** the new stock price does not affects the profit of A.
//
// 13. Suppose the stock price was 56.1 on the day B sold the option to C.
//     Explain how the new stock price affects the profit of investor B.
//     ***** if B still sells C the option for the price of 1.5
//     ***** , the new stock price does not affects the profit of B.



// --------------- 5.2.1 Long American put option --------------------
// 1.  You are long an American put option.
// 2.  The put option has a strike of 97.
// 3.  The market price of the stock is 94 today.
// 4.  You exercise the option.  State what you pay/receive.
//     ****** I deliver the stock and receive 97
//
// 5.  State what the option writer pays/receives.
//     ***** the option writer get the stock and pay 97
//
// -----------------5.2.2 Long European put option-----------------
// 1.  You are long a European put option.
// 2.  The put option has a strike of 51.
// 3.  The market price of the stock is 47 today (= expiration day).
// 4.  You exercise the option.  State what you pay/receive.
//     ****** I deliver the stock and receive 51
//
// 5.  State what the option writer pays/receives.
//     ***** the option writer get the stock and pay 51
//

//  ---------------5.2.3    Short American put option 1-----------------
// 1.  You are short an American put option .
// 2.  The put option has a strike of 65.
// 3.  The market price of the stock is 58 today.
// 4.  The option holder exercises the option. State what the option holder pays/receives.
//     ***** option holder deliver the stock and receives 65
//
// 5.  State what the option writer (= you) pays/receives.
//     ***** option writer receives the stock and pay 65
//
//
// ----------------- 5.2.4    Short American put option 2 ----------------------
// 1.  An American put option has a strike of 45.
// 2.  You sell the option to investor A at a price of 1.1.
// 3.  Therefore you are short an American put option.
// 4.  The stock price is 48 (on the day of sale).
// 5.  The stock price changes to 47 and investor A sells the option to investor B for a price of 1.2.
// 6.  The stock price changes to 44 and investor B sells the option to investor C for a price of 1.5.
// 7.  Explain what B pays/receives if B exercises the option instead.
//     ***** B will deliver the stock and revieve 45
//
// 8.  The stock price goes down to 43 and C exercises the option.
//     State what C pays/receives.
//     ***** C deliver the stock and recieves 45
//
// 9.  The option is exercised against you.
//     State what the option writer (= you) pays/receives on the day of exercise.
//     ***** option writer gets the stock and pay 45
//
// 10. Calculate the profit of investor A. Ignore interest rate compounding.
//     ***** the profit of A is 1.2 - 1.1 = 0.1
//
// 11. Calculate the profit of investor B. Ignore interest rate compounding.
//     ***** the profit of B is 1.5 - 1.2 = 0.3
//
// 12. Suppose the stock price was 46 on the day A sold the option to B.
//     Explain how the new stock price affects the profit of investor A.
//     ***** if A still sell B the option for a price of 1.2
//     ***** the profit of A is not affected
//
// 13. Suppose the stock price was 44.4 on the day B sold the option to C.
//     Explain how the new stock price affects the profit of investor B.
//     ***** if B still sell C the option for a price of 1.5
//     ***** the profit of B is not affected
//
// 14. Suppose B holds the option and the stock price was 43.4 and the option price was 1.5 .
//     C wishes to buy the option from B (at a price of 1.5 ).
//     Describe the choices available to B and explain what B should do.
//     ***** B can excercies the option  or  sell it  to C
//     ***** B should excercise the option, first buy stock at prive 43.4 and deliver it and
//     ***** then recieve 45,  this gives (45- 43.4) > 1.5
//
// -------------------5.2.5  Short European put option 1-----------------------------
// 1.  You are short a European put option.
// 2.  The put option has a strike of 82.  The stock price is 79 today (= expiration date).
// 3.  The option holder exercises the option. State what the option holder pays/receives.
//     ***** the option holder deliver the stock and revieve 82
//
// 4.  State what the option writer (= you) pays/receives.
//     ***** the option writer revieves the stock and pay 82
//
//
//
// ----------------------5.2.6    Short European put option 2--------------------------
// 1.  You are short a European put option.
// 2.  The put option has a strike of 65.  The stock price is 68 today (= expiration date).
// 3.  The option holder does not exercise the option. State what the option holder pays/receives.
//     ***** option holder pays/receives nothing
//
// 4.  State what the option writer (= you) pays/receives.
//      ***** option writer pays/receives nothing
//
// ------------------------5.2.7    Short European put option 3----------------------------------
// 1.  A European put option has a strike of 45.
// 2.  You sell the option to investor A at a price of 1.1.
// 3.  Therefore you are short a European put option .
// 4.  The stock price is 48 (on the day of sale).
// 5.  The stock price changes to 47 and investor A sells the option to investor B for a price of 1.2.
// 6.  The stock price changes to 43 and investor B sells the option to investor C for a price of 1.5.
// 7.  Explain if B can execute an arbitrage strategy in this situation.
//     Describe the trades of the arbitrage strategy.
//     ***** no B can't, B doesnot know the option price will go up
//
// 8.  The stock price goes down to 42 on the expiration date and C exercises the option.
//     State what C pays/receives.
//     ***** C deliver the stock and revieve 45
//
// 9.  The option is exercised against you.
//     State what the option writer (= you) pays/receives on the day of exercise.
//     ***** option writer recieve the stock and pay 45
//
// 10. Calculate the profit of investor A. Ignore interest rate compounding.
//     ***** The profit of A is 1.2 - 1.1 = 0.1
//
// 11. Calculate the profit of investor B. Ignore interest rate compounding.
//     ***** The profit of B is 1.5 -1.2 = 0.3
//
// 12. Suppose the stock price was 49 on the day A sold the option to B.
//     Explain how the new stock price affects the profit of investor A.
//     ***** if A still sell B the option for a price of 1.2
//     ***** the profit of A is not affected
//
// 13. Suppose the stock price was 43.2 on the day B sold the option to C.
//     Explain how the new stock price affects the profit of investor B.
//     ***** if B still sell C the option for a price of 1.5
//     ***** the profit of B is not affected





// ----------------------5.3  Exercise of cash settled option positions---------------------
// • Not all options involve delivery of stock.
// • Options on stock indices are settled in cash.
// • All of the options below are on stock indices and the strike is 1000 index points.
// • All of the options below pay $100 per index point that the option is in the money.
//
// ----------------------5.3.1    Long cash-settled call option------------------------------
// 1.  You are long a call option (American or European) on a stock index .
// 2.  The value of the stock index is 1040 today (= expiration day).
// 3.  You exercise the option.  State what you pay/receive.
//     ***** I recieve (1040 - 1000) x 100
// 4.  State what the option writer pays/receives.
//     ***** option writer pays (1040 - 1000) x 100
//
// ------------------------5.3.2    Short cash-settled call option------------------------
// 1.  You are short a call option (American or European) on a stock index.
// 2.  The value of the stock index is 1070 today (= expiration day).
// 3.  The option holder exercises the option. State what the option holder pays/receives.
//     ***** option holder recieve (1070-1000) x 100
//
// 4.  State what the option writer (= you) pays/receives.
//     ***** option writer pays (1070-1000) x 100
//
// ------------------------5.3.3    Long cash-settled put option------------------------
// 1.  You are long a put option (American or European) on a stock index.
// 2.  The value of the stock index is 945 today (= expiration day).
// 3.  You exercise the option.  State what you pay/receive.
//     ***** I recieve (1000 - 945) x 100
//
// 4.  State what the option writer pays/receives.
//     ***** option writer pays (1000 - 945) x 100
//
// --------------------------5.3.4    Short cash-settled put option--------------------------
// 1.  You are short a put option (American or European) on a stock index.
// 2.  The value of the stock index is 920 today (= expiration day).
// 3.  The option holder exercises the option. State what the option holder pays/receives.
//     ***** option holder receives (1000-920)x100
//
// 4.  State what the option writer (= you) pays/receives.
//     ***** option writer pays (1000 - 920) x 100
