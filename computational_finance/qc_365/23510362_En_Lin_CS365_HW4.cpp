#include <iostream>
#include <cmath>
using namespace std;
//Homework 4
//******* you can also run this cpp file to see the answer ******


//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

//################################ Part 1 answers ################################

// *********** HomeWork 4a **********
// *********** question 4.1 **********
// What is the open volume on day 0 ?
//  0
//
// *********** question 4.2 **********
// A: long(1000), B:short(1000) ---day1
//
// open volume: 1000 on ---day 1
//
// they just started the contract, both have profit 0 ---day1
//
// *********** question 4.3 **********
// A: close out, B:short(1000), C:long(1000) ---day 2
//
// open volume: 1000 on ---day 2
//
// profit-----A: -4.9 x 1000,  B: 4.9 x 1000, C: 0 ---day 2
//
// *********** question 4.4 **********
// A: close out, B:close out, C:close out ---day 3
//
// open volume: 1000 on ---day 3
//
// profit-----A: 0,  B: 0.4 x 1000, C: -0.4 x 1000 ---day 3
//
// *********** question 4.5 **********
// A: close out, B:close out, C:close out ---day 4
//
// open volume: 0 on ---day 4
//
// profit-----A: 0,  B: 0, C: 0 ---day 4
//
// *********** question 4.6 **********
// profit sum A + B + C =
// 1 x 1000 + (-1) x 1000 + 0.4 x 1000 + (-0.4) x 1000) = 0
//
// *********** question 4.7 ********************************************************************************************************************************************
// i     Si     Fi                                            long/short position                                          profit/loss (A,B,C,D)  open volume
// 0  120.0  130.0                               F:s(10000), A:l(5000), B:l(5000)                                                  F:0, A:0, B:0        10000
// 1  117.6  128.2             F:s(10000), A:c, B:s(15000), C:l(5000), D:l(20000)                F:1.8x10000, A:-1.8x5000, B:-1.8x5000, C:0, D:0        25000
// 2  123.4  131.1       F:s(10000), A:l(5000), B:s(13000), C:l(3000), D:l(15000)       F:-2.9x10000, A:0, B:-2.9x15000, C:2.9x5000, D:2.9x20000        23000
// 3  128.5  131.1         F:s(10000), A:l(6000), B:s(3000), C:l(2000), D:l(5000)                                        F:0, A:0, B:0, C:0, D:0        13000
// 4  127.4  134.6               F:s(10000), A:l(4000), B:c, C:l(1000), D:l(5000)  F:-3.5x10000, A:3.5x6000, B:-3.5x3000, C:3.5x2000, D:3.5x5000        10000
// 5  132.4  132.4               F:s(10000), A:l(4000), B:c, C:l(1000), D:l(5000)        F:2.2x10000, A:-2.2x4000, B:0, C:-2.2x1000, D:-2.2x5000        10000
// *********************************************************************************************************************************************************************

// At expiration, The farmer deliver 4000 units underlying assets to A, 1000 to C, 5000 to D
//
// A, C, D all pay each unit of assets at price 132.4 to to farmer
//
// The farmer recieved 10000 x 130.0 (total include mark to market) = 1.3e+06
//
// A paid total 4000 x 132.4  + 2.2 x 4000 (on day 5) = 538400
//
// B paid the farmer 0 (on day 5)
//
// C paid the farmer 1000 x 132.4 + 2.2 x 1000(on day 5) = 134600
//
// D paid the farmer 5000 x 132.4 + 2.2 x 5000(on day 5) = 673000
//
// *********** question 4.8 ********************************************************************************************************************************************
// i     Si   D     Fi                                            long/short position                                          profit/loss (A,B,C,D)  open volume
// 0  120.0 0.0  130.0                               X:s(10000), A:l(5000), B:l(5000)                                                  X:0, A:0, B:0        10000
// 1  117.6 0.0  128.2             X:s(10000), A:c, B:s(20000), C:l(5000), D:l(25000)                X:1.8x10000, A:-1.8x5000, B:-1.8x5000, C:0, D:0        30000
// 2  123.4 0.0  131.1       X:s(10000), A:l(8000), B:s(17000), C:l(2000), D:l(17000)        X-2.9x10000, A:0, B:-2.9x20000, C:2.9x5000, D:2.9x25000        27000
// 3  128.5 0.1  131.1         X:s(10000), A:l(9000), B:s(7000), C:l(1000), D:l(7000)                                        X:0, A:0, B:0, C:0, D:0        17000
// 4  127.4 0.0  134.6                X:s(10000), A:l(2500), B:s, C:l(500), D:l(7000)  X:-3.5x10000, A:3.5x9000, B:-3.5x7000, C:3.5x1000, D:3.5x7000        10000
// 5  132.4 0.0  132.4                X:s(10000), A:l(2500), B:s, C:l(500), D:l(7000)         X:2.2x10000, A:-2.2x2500, B:0, C:-2.2x500, D:-2.2x7000        10000
// *********************************************************************************************************************************************************************

// At expiration, The investor deliver stock shares 2500 to A, 7000 to B, 500 to C and 7000 to D
//
// A, B, C, D all pay each share of stock at price/share 132.4 to to X
//
// A,B,C,D paid X total10000 x 130.0 = 1.3e+06
//
// X recieved 1.8 x 10000 (on day 1) = 18000
//
// X recieved -2.9 x 10000 (on day 2) = -29000
//
// X recieved 0.1 x 10000 (on day 3) = 1000
//
// X recieved -3.5 x 10000 (on day 4) = -35000
//
// X recieved 2.2 x 10000  + 10000 x 132.4 (on day 5) = 1.346e+06
//
// X recieved in total 131000
//
// We can calculate total amount money at day 0
//
// *********** question 4.9 **********
// I will buy low sell high,
// I will go long in the futures contract in price F0 = 130,
// I will go short in the forwards contract in price Fy = 131.2,
// On expiration date, I get the stock and sell it
// I will be left with profit 1000 x 1.2 = 1200
//
// *********** question 5.0 **********
// I will buy low sell high,
// I will go short in the futures contract in price F0 = 130,
// I will go long in the forwards contract in price Fy = 129.5,
// On expiration date, I get the stock and sell it
// I will be left with profit 1000 x 0.5 = 500

//#############################  Part 2  helper function ######################################

//#############################  Part 3  Main ######################################
int main(){

cout << "*********** HomeWork 4a **********" << endl;
cout << "*********** question 4.1 **********" << endl;
cout << "What is the open volume on day 0 ?" << endl;
cout << " 0 " << endl<<endl;

cout << "*********** question 4.2 **********" << endl;
cout << "A: long(1000), B:short(1000) ---day1"<< endl<<endl;
cout << "open volume: 1000 on ---day 1" << endl<<endl;
cout << "they just started the contract, both have profit 0 ---day1" << endl<<endl;

cout << "*********** question 4.3 **********" << endl;
cout << "A: close out, B:short(1000), C:long(1000) ---day 2" << endl<<endl;
cout << "open volume: 1000 on ---day 2" << endl<<endl;
cout << "profit-----A: 1 x 1000,  B: -1 x 1000, C: 0 ---day 2" << endl<<endl;

cout << "*********** question 4.4 **********" << endl;
cout << "A: close out, B:close out, C:close out ---day 3" << endl<<endl;
cout << "open volume: 1000 on ---day 3" << endl<<endl;
cout << "profit-----A: 0,  B: 0.4 x 1000, C: -0.4 x 1000 ---day 3" << endl<<endl;

cout << "*********** question 4.5 **********" << endl;
cout << "A: close out, B:close out, C:close out ---day 4" << endl<<endl;
cout << "open volume: 0 on ---day 4" << endl<<endl;
cout << "profit-----A: 0,  B: 0, C: 0 ---day 4" << endl<<endl;

cout << "*********** question 4.6 **********" << endl;
cout << "profit sum A + B + C = "<< endl;
cout << "1 x 1000 + (-1) x 1000 + 0.4 x 1000 + (-0.4) x 1000) = 0" <<endl << endl;

cout << "*********** question 4.7 ************************************************************************************************************************************************" << endl;
printf("i     Si     Fi                                            long/short position                                          profit/loss (A,B,C,D)  open volume\n");
printf("%1d%7.1f%7.1f%63s%63s%13d\n",0,120.0,130.0,"F:s(10000), A:l(5000), B:l(5000)","F:0, A:0, B:0", 10000 );
printf("%1d%7.1f%7.1f%63s%63s%13d\n",1,117.6,128.2,"F:s(10000), A:c, B:s(15000), C:l(5000), D:l(20000)","F:1.8x10000, A:-1.8x5000, B:-1.8x5000, C:0, D:0", 25000 );
printf("%1d%7.1f%7.1f%63s%63s%13d\n",2,123.4,131.1,"F:s(10000), A:l(5000), B:s(13000), C:l(3000), D:l(15000)","F:-2.9x10000, A:0, B:-2.9x15000, C:2.9x5000, D:2.9x20000", 23000 );
printf("%1d%7.1f%7.1f%63s%63s%13d\n",3,128.5,131.1,"F:s(10000), A:l(6000), B:s(3000), C:l(2000), D:l(5000)","F:0, A:0, B:0, C:0, D:0", 13000);
printf("%1d%7.1f%7.1f%63s%63s%13d\n",4,127.4,134.6,"F:s(10000), A:l(4000), B:c, C:l(1000), D:l(5000)","F:-3.5x10000, A:3.5x6000, B:-3.5x3000, C:3.5x2000, D:3.5x5000", 10000);
printf("%1d%7.1f%7.1f%63s%63s%13d\n\n",5,132.4,132.4,"F:s(10000), A:l(4000), B:c, C:l(1000), D:l(5000)","F:2.2x10000, A:-2.2x4000, B:0, C:-2.2x1000, D:-2.2x5000", 10000);
cout << "*************************************************************************************************************************************************************************" << endl;

cout << "At expiration, The farmer deliver 4000 units underlying assets to A, 1000 to C, 5000 to D" << endl<<endl;
cout << "A, C, D all pay each unit of assets at price 132.4 to to farmer" << endl<<endl;

cout << "The farmer recieved " << "10000 x 130.0 (total include mark to market) = " << 10000 * 130.0<<endl<<endl;

cout << "A paid total " << "4000 x 132.4  + 2.2 x 4000 (on day 5) = "<< 4000 * 132.4 + 2.2 * 4000<<endl <<endl;

cout << "B paid the farmer " << "0 (on day 5)" <<endl <<endl;

cout << "C paid the farmer " << "1000 x 132.4 + 2.2 x 1000(on day 5) = " << 1000 * 132.4 + 2.2*1000<<endl <<endl;

cout << "D paid the farmer " << "5000 x 132.4 + 2.2 x 5000(on day 5) = " <<5000 * 132.4 +2.2*5000<<endl <<endl;


cout << "*********** question 4.8 *******************************************************************************************************************************************************" << endl;
printf("i     Si   D     Fi                                            long/short position                                          profit/loss (A,B,C,D)  open volume\n");
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n",0,120.0,0.0,130.0,"X:s(10000), A:l(5000), B:l(5000)","X:0, A:0, B:0", 10000 );
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n",1,117.6,0.0,128.2,"X:s(10000), A:c, B:s(20000), C:l(5000), D:l(25000)","X:1.8x10000, A:-1.8x5000, B:-1.8x5000, C:0, D:0", 30000 );
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n",2,123.4,0.0,131.1,"X:s(10000), A:l(8000), B:s(17000), C:l(2000), D:l(17000)","X-2.9x10000, A:0, B:-2.9x20000, C:2.9x5000, D:2.9x25000", 27000 );
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n",3,128.5,0.1,131.1,"X:s(10000), A:l(9000), B:s(7000), C:l(1000), D:l(7000)","X:0, A:0, B:0, C:0, D:0", 17000);
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n",4,127.4,0.0,134.6,"X:s(10000), A:l(2500), B:s, C:l(500), D:l(7000)","X:-3.5x10000, A:3.5x9000, B:-3.5x7000, C:3.5x1000, D:3.5x7000", 10000);
printf("%1d%7.1f%4.1f%7.1f%63s%63s%13d\n\n",5,132.4,0.0,132.4,"X:s(10000), A:l(2500), B:s, C:l(500), D:l(7000)","X:2.2x10000, A:-2.2x2500, B:0, C:-2.2x500, D:-2.2x7000", 10000);
cout << "********************************************************************************************************************************************************************************" << endl;

cout << "At expiration, The investor deliver stock shares 2500 to A, 7000 to B, 500 to C and 7000 to D" << endl<<endl;
cout << "A, B, C, D all pay each share of stock at price/share 132.4 to to X" << endl<<endl;

cout << "A,B,C,D paid X total" << "10000 x 130.0 = "<< 10000 * 130.0 << endl <<endl;

cout << "X recieved " << "1.8 x 10000 (on day 1) = " << 1.8 * 10000 <<endl <<endl;

cout << "X recieved " << "-2.9 x 10000 (on day 2) = " << -29000 << endl <<endl;

cout << "X recieved " << "0.1 x 10000 (on day 3) = " << 0.1 * 10000 << endl <<endl;

cout << "X recieved " << "-3.5 x 10000 (on day 4) = " << -3.5 * 10000 << endl <<endl;

cout << "X recieved " << "2.2 x 10000  + 10000 x 132.4 (on day 5) = " << 2.2 * 10000 + 10000 * 132.4 << endl <<endl;

cout << "X recieved in total " << 131000 << endl << endl;

cout <<  "We can calculate total amount money at day 0"<< endl<<endl;


cout << "*********** question 4.9 **********" << endl;
cout << "I will buy low sell high, " << endl;
cout << "I will go long in the futures contract in price F0 = 130, " << endl;
cout << "I will go short in the forwards contract in price Fy = 131.2, " << endl;
cout << "On expiration date, I get the stock and sell it"<<endl;
cout << "I will be left with profit 1000 x 1.2 = "<< 1200 <<endl<<endl;

cout << "*********** question 5.0 **********" << endl;
cout << "I will buy low sell high, " << endl;
cout << "I will go short in the futures contract in price F0 = 130, " << endl;
cout << "I will go long in the forwards contract in price Fy = 129.5, " << endl;
cout << "On expiration date, I get the stock and sell it"<<endl;
cout << "I will be left with profit 1000 x 0.5 = "<< 500 <<endl;


}
