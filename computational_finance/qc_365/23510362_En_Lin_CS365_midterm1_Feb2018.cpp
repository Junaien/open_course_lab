//******* you can also run this cpp file to see the answer ******


//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

//################################ Part 1 answers ################################
//1.1, Write the formula for d in terms of the input parameters:
//   d = F0 / F1;  (F1 != 0)

//1.2, Write the formula for d in terms of r and any other input parameters:
//  d =  e ^ -r(t1 - t0);

//1.3, Calculate the value of the discount factor d in eq(1.2):
// d = 0.9418;

//1.4 Use the number from 1.3 to calculate  the present value
// F0 = F1 * d1 = 113.01;



// delta y1 = 0.4702

// delta y1.5 = -0.0724

//2.1, write down the calues of y1 and y1.5 to four decimal
//y0 = 5.0%,  y1 = 5.4702%,  y1.5 = 4.9276%

//2.2 calculate the discount_factor d0.5, d1.0, d1.5
//2.3 calculate the compounded spot rates
// d-0.5: 0.9756    r-0.5: 4.94%;
// d-1.0: 0.9474    r-1.0: 5.40%;
// d-1.5: 0.9297    r-1.5: 4.86%;

//3.1, write a mathematical formula for CF0
// CF0 = CF0.5 * d0.5 + CF1.0 * d1.0 + CF1.5 * d1.5;

//3.2, calculate the numerical value of CF0 IN 3.1
// CF0 = 98.6754;

//4.1, fill in the table below with the values of B(y)
// B(0) = 106.0000
// B(1) = 104.4554
// B(2) = 102.9410
// B(3) = 101.4561
// B(4) = 100.0000
// B(5) = 98.5720
// B(6) = 97.1714
// B(7) = 95.7975
// B(8) = 94.4498
// B(9) = 93.1276

//4.2 find the smallest interval (ylow, yhigh)
//(y_low, y_high) => (4,5);

//4.3 find y_mid
// (y_mid) => 4.5;

//4.4 find B(ymid)
// B(4.5) = 99.2825;

//5.1 state value of y_low or y_high should be updated for next iteration
//(y_low, y_high) => (4.5, 5) because 98.5270 < 98.6754 < 99.2825;

//5.2
// new ymid = 4.75
// B(4.75) = 98.9264;


//6,  does the above implementations work correcty?
// No,  it is supposed to start from i = 0
// by setting for loop this way, programs only
// calculate n-1 cashflow
// moreover,  the discount_factor for last cashflow should be
// 1/(1+1/2*y)^ n  not 1/(1+1/2*y)^(n-1)
#include <iostream>
#include <cmath>
using namespace std;
// #############################  Part 2  ######################################
// ########################### helper function for calculate discount factor ############

double discount_factor(double t0, double t1, double r){
  double d = 0.0;
  double power = -(r * (t1 - t0));
  d = exp(power);
  return d;
}

//##############  helper function for question 2.8 ######################

// n is the array position of coupon discount_factor value
// for example if d[1] means d1.0 if coupon frequency is semianual
// yield is percentage here
double discount_factor_for_par(int n, double yield[], int frequency ,double face
                                  ,double* discount){


    if(n == 0){
      double c = (1.0/frequency) * yield[0];
      double numerator = face;
      double denominator = face + c;
      return discount[0] = numerator/denominator;

    }

    if(discount[n] != -1.0){
      return discount[n];
    }else{
      double numerator = face;
      for(int i = n - 1; i >= 0; i--){
        numerator -= (1.0/frequency) * yield[n] * discount_factor_for_par(i, yield, frequency, face, discount);
      }
      double denominator = face + (1.0/frequency)*yield[n];
      return discount[n] = numerator/ denominator;

    }

}

double* get_discount_factor_for_par(int n, double yield[], int capacity, int frequency ,double face){

    double* discount_memoization = new double[capacity];
    for(int i = 0; i < capacity; i++){
      discount_memoization[i] = -1.0;
    }

    if(n >= capacity){
      cout << "invalid parameter n"<<endl;
      return discount_memoization;
    }

    discount_factor_for_par(n, yield, frequency, face, discount_memoization);
    return discount_memoization;
}

//this returns percentage
double discount_to_spot_rate(double discount, double t){
    if(t == 0)return -1;
    else return -(log(discount)/t)*100.0;
}


//############################### helper function for question 4.0 #######################
double bond_price(double yield_rate, double coupond,int maturity_span, double face){
  double discount_factor = 1.0/(1.0 + yield_rate/2);
  double d = discount_factor;
  double bond_price = 0.0;

  for(int i = 0; i < maturity_span; i++){
    if(i == (maturity_span -1)){
        bond_price += ((coupond/2.0 + face) * d);
    }else{
        bond_price += ((coupond/2.0) * d);
    }

    d *= discount_factor;

  }
  return bond_price;
}






//#############################  Part 3  ######################################
int main(){
//#############################  test for question 1.3 ########################

cout <<endl<< "check comments section at the top of the file to see questions number"<<endl;
cout << "***** question 1.1 *****" << endl;
cout << "d = F0 / F1;  (F1 != 0)"<<endl<<endl;

cout << "***** question 1.2 *****" << endl;
cout << "d =  e ^ -r(t1 - t0);"<<endl<<endl;

cout << "***** question 1.3 *****" << endl;
double d1 = discount_factor(0.0, 1.5, 0.04);
printf("d = %.4f;\n\n", d1);

cout << "***** question 1.4 *****" << endl;
printf("F0 = %.2f;\n\n", 120.0 * d1);

cout << "***** question 2.1 *****" << endl;
printf("y0.5 = 5.0%%,  y1 = %.4f%%,  y1.5 = %.4f%%;\n\n", 5.0 + 0.4702, 5.0 - 0.0724);

cout << "***** question 2.2 and question 2.3 *****" << endl;
double yield [3] = {5.0, 5.4702,4.9276};
double* result = get_discount_factor_for_par(2, yield, 3, 2 ,100);
for(int i = 0; i < 3; i++){
   double index = (i+1.0)/2.0;
   printf("d-%2.1f: %4.4f    r-%2.1f: %4.2f%%;\n",index, result[i],index,
     discount_to_spot_rate(result[i], index));
}
cout << endl;
cout << "***** question 3.1 *****" << endl;
cout << "CF0 = CF0.5 * d0.5 + CF1.0 * d1.0 + CF1.5 * d1.5;"<<endl<<endl;

cout << "***** question 3.2 *****" << endl;
double d0_5 = 0.9756;
double d1_0 = 0.9474;
double d1_5 = 0.9297;
double CF0 = 2.0*d0_5 + 2.0*d1_0 + 102 * d1_5;
printf("CF0 = %.4f;\n\n", CF0);

cout << "***** question 4.1 *****" << endl;
for( int i = 0; i < 10 ; i++){
  printf("B(%d) = %.4f\n", i, bond_price(i/100.0,4,3,100));
}

cout << endl;
cout << "***** question 4.2 *****" << endl;
cout << "(y_low, y_high) => (4,5);"<<endl<<endl;

cout << "***** question 4.4 *****" << endl;
cout << "(y_mid) => 4.5;"<<endl<<endl;

cout << "***** question 4.4 *****" << endl;
printf("B(4.5) = %.4f;\n\n",bond_price(0.045,4,3,100));

cout << "***** question 5.1 *****" << endl;
cout << "(y_low, y_high) => (4.5, 5) because 98.5270 < 98.6754 < 99.2825;"<<endl<<endl;

cout << "***** question 5.2 *****" << endl;
cout << "new ymid = " << "4.75;" << endl;
printf("B(4.75) = %.4f;\n",bond_price(0.0475,4,3,100));

cout << endl;
cout << "***** question 6 *****"<<endl;
cout << "No,  it is supposed to start from i = 0;" <<endl;
cout << "first of all, by setting for loop this way, programs only calculate n-1 cashflows;"<<endl<<endl;
cout << "moreover,  the discount_factor for last cashflow \nshould be ";
cout << "1/(1+1/2*y)^ n  not 1/(1+1/2*y)^(n-1);"<<endl<<endl;

}
