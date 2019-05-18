
//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test



//########################## Part 1 answers ################################
//1.3 - 1 Fill in the table below with the values of B(y)
// B(y0) 108.000
// B(y2) 103.902
// B(y4) 100.000
// B(y6) 96.2829
// B(y8) 92.7402

//1.3 - 3 State which pair(y, y+ 2) gives a lower and upper bound for the true yield
// (y, y+2) => (2, 4)

//1.3 - 5 Calculate the bond priceB(ymid)
// B(ymid(2,4)):  101.93

//1.3 - 6,7 state next iteration and updated B(ymid)
// next iteration will be (y_low, y_high) => (3, 4)
// updated ymid = 3.5   updated B(ymid) = 100.96

//1.3 - 1 -> 1 Fill in the table below with the values of B(y)
// y1: 105.901
// y3: 101.855
// y5: 98.0029
// y7: 94.3325
// y9: 90.8339

//1.3 - 3 -> 3 State which pair(y, y+ 2) gives a lower and upper bound for the true yield
//(y, y+2) => (3, 5)

//1.3 - 5 -> 5 Calculate the bond priceB(ymid)
//ymid(1,3): 99.9485
//ymid(3,5): 92.5778
//ymid(5,7): 85.8244
//ymid(7,9): 79.6311
//ymid(9,11): 73.9464

//1.3 - 6,7 -> 6,7
// next iteration will be (y_low, y_high) => (3, 4)
// updated ymid = 3.5   updated B(ymid) = 100.875



//1.4 yield curve
// d- 0.5: 0.9804    r- 0.5: 3.96%
// d- 1.0: 0.9593    r- 1.0: 4.16%
// d- 1.5: 0.9409    r- 1.5: 4.06%

#include <iostream>
#include <cmath>
using namespace std;

// #############################  Part 2  ######################################
// #####################   function for HomeWork 1.1  : Future value  ##########
double future_value(double F0, double t0, double t1, double r){

  double r_decimal = 0.01 * r;
  double F1 = F0*exp(r_decimal*(t1-t0));
  return F1;
};

// ##############  function for HomeWork 1.2 : Discount factore and interest #####
int df_and_r(double F0, double F1, double t0, double t1, double& df, double& r){
  if (t1 == t0){
    df = 0; r = 0;
    return -1;
  }else if(F0 <= 0 || F1<=0){
    df = 0; r = 0;
  }

  df = F0 / F1;
  r = -( log(df) / (t1 - t0 ));

  return 0;
};

//##############  function for HomeWork 1.3 : Bond price and yield ############
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
//##############  function for HomeWork 1.3 : Bond price and yield ############
double bond_price(double yield_rate, double coupond [],int coupond_size,
                   int maturity_span, double face){

  if (coupond == NULL || coupond_size != maturity_span)return -1;
  double discount_factor = 1.0/(1.0 + yield_rate/2.0);
  double d = discount_factor;
  double bond_price = 0.0;
  for(int i = 0; i < maturity_span; i++){

    if(i == (maturity_span -1)){
        bond_price += ((coupond[i]/2.0 + face) * d);
    }else{
        bond_price += ((coupond[i]/2.0) * d);
    }

    d *= discount_factor;

  }
  return bond_price;
}

//##############  helper function for HomeWork 1.4 : yield curve ######################
double discount_factor_for_par(int n, double yield[], int frequency ,double face
                                  ,double* discount){
    // cout << n<<endl;
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
      // cout << discount_memoization[i] <<endl;
    }

    discount_factor_for_par(n, yield, frequency, face, discount_memoization);
    return discount_memoization;
}

double discount_to_spot_rate(double discount, double t){
    if(t == 0)return -1;
    else return -(log(discount)/t)*100;
}



//#############################  Part 3  ######################################
int main()
{
//################## test for HomeWork 1.1 ###############################
  cout << "Question 1.1"<<endl;
  cout << "The future value of 100 in 2 years is : "
       << future_value(100, 1, 3, 11)<<"  with interest 11%"<<endl<<endl;

//################## test for HomeWork 1.2 ###############################
  cout << "Question 1.2"<<endl;
  cout << "Present value: 100" <<endl;
  double df = 0.0;
  double r = 0.0;
  df_and_r(100, 200, 1, 3, df, r);
  cout << "future value in 2 years: 200" <<endl;
  cout << "discount factor = " << df<<endl;
  cout << "interest rate = " << r * 100 << "%"<<endl<<endl;
//################### test for HomeWork 1.3 #################################
 //question 1
 cout << "Question 1.3 - 1"<<endl;
 double B_with_y_0 = bond_price(0.0, 4, 4, 100);
 double B_with_y_2 = bond_price(0.02, 4, 4, 100);
 double B_with_y_4 = bond_price(0.04, 4, 4, 100);
 double B_with_y_6 = bond_price(0.06, 4, 4, 100);
 double B_with_y_8 = bond_price(0.08, 4, 4, 100);
 cout << B_with_y_0 << endl;
 cout << B_with_y_2 << endl;
 cout << B_with_y_4 << endl;
 cout << B_with_y_6 << endl;
 cout << B_with_y_8 << endl<<endl;

 //quetion 5
 cout <<"Question 1.3 - 5"<<endl;
 cout <<"ymid(0,2): "<< bond_price((0.0 + 0.02)/2, 4, 4, 100)<<endl;
 cout <<"ymid(2,4): "<< bond_price((0.02+ 0.04)/2, 4, 4, 100)<<endl;
 cout <<"ymid(4,6): "<< bond_price((0.04+ 0.06)/2, 4, 4, 100)<<endl;
 cout <<"ymid(6,8): "<< bond_price((0.06+0.08)/2, 4, 4, 100)<<endl;
 cout <<"ymid(8,10): "<< bond_price((0.08 + 0.1)/2, 4, 4, 100)<<endl<<endl;

 //question 7
 cout << "Question 1.3 - 7"<< endl;
 cout << "updated B(ymid): " << bond_price(0.035, 4, 4, 100)<<endl<<endl;

 //question 1 -> 1
 cout << "Question 1.3 - 1 -> 1 "<<endl;
 double c [4] = {1,3,5,7};
 int c_size = 4;
 cout <<"y1: "<< bond_price((0.01), c, c_size,4, 100)<<endl;
 cout <<"y3: "<< bond_price((0.03), c, c_size,4, 100)<<endl;
 cout <<"y5: "<< bond_price((0.05), c, c_size,4, 100)<<endl;
 cout <<"y7: "<< bond_price((0.07), c, c_size,4, 100)<<endl;
 cout <<"y9: "<< bond_price((0.09), c, c_size,4, 100)<<endl<<endl;


 //question 5 -> 5
 cout << "Question 1.3 - 5 -> 5"<<endl;
 cout <<"ymid(1,3): "<< bond_price((0.01 + 0.03)/2, c, c_size, 4, 100)<<endl;
 cout <<"ymid(3,5): "<< bond_price((0.03+ 0.05)/2, c, c_size, 4, 100)<<endl;
 cout <<"ymid(5,7): "<< bond_price((0.05+ 0.07)/2, c, c_size, 4, 100)<<endl;
 cout <<"ymid(7,9): "<< bond_price((0.07+0.09)/2, c, c_size, 4, 100)<<endl;
 cout <<"ymid(9,11): "<< bond_price((0.09 + 0.11)/2, c, c_size, 4, 100)<<endl<<endl;

 //question 6,7 -> 6,7
 cout << "Question 1.3 - 7 -> 7"<< endl;
 cout << "updated B(ymid): " << bond_price(0.035, c, c_size, 4, 100)<<endl<<endl;

 //################### test for HomeWork 1.4: yield curve #################################
 cout << "Question 1.4"<<endl;
 double yield [3] = {4.0, 4.2, 4.1};
 double* result= get_discount_factor_for_par(2, yield, 3, 2, 100);
 for(int i = 0; i < 3; i++){
   double index = (i+1.0)/2.0;
   printf("d-%4.1f: %4.4f    r-%4.1f: %4.4f\n",index, result[i],index,
     discount_to_spot_rate(result[i], index));
 }

}
