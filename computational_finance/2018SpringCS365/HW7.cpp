//******* you can also run this cpp file to see the answer ******


//HomeWork answser separated to 3 part
//Part 1  Answers in C++ comments
//Part 2  C++ helper function
//Part 3  C++ Codes  main()  test

// #############################  Part 1  ######################################

#include <iostream>
#include <cmath>
using namespace std;
// #############################  Part 2  ######################################
// ########################### helper functions ############
double cum_norm(double x){
  const double root = sqrt(0.5);
  return 0.5*(1.0 + erf(x*root));
}
double d1(double S, double K, double r, double q, double v,double t0, double T){
    return (log(S/K) + (r-q)*(T-t0))/(v*sqrt(T-t0)) + 0.5 * v * sqrt(T-t0);
}
double d2(double S, double K, double r, double q, double v,double t0, double T){
    return d1(S,K,r,q,v,t0,T) - v * (sqrt(T-t0));
}

double fairValueEcall(double S, double  K, double r, double q, double v, double t0, double T){
    double nd1 = cum_norm(d1(S,K,r,q,v,t0,T));
    double nd2 = cum_norm(d2(S,K,r,q,v,t0,T));
    return S*exp(-1*q*(T-t0))*nd1 - K*exp(-1*r*(T-t0))*nd2;
}
double delta_c(double S, double K, double r, double q, double v,double t0, double T){
  return exp(-1*q*(T-t0)) * cum_norm(d1(S,K,r,q,v,t0,T));
}
double delta_p(double S, double K, double r, double q, double v,double t0, double T){
  return -1*exp(-1*q*(T-t0)) * cum_norm(-1*d1(S,K,r,q,v,t0,T));
}
double fairValueEput(double S, double  K, double r, double q, double v, double t0, double T){
    double n_negative_d1 = cum_norm(-1 * d1(S,K,r,q,v,t0,T));
    double n_negative_d2 = cum_norm(-1 * d2(S,K,r,q,v,t0,T));
    return K*exp(-1*r*(T-t0))*n_negative_d2 - S*exp(-1*q*(T-t0))*n_negative_d1;
}

//#############################  Part 3  ######################################
int main(){
//#############################  test for question 1.3 ########################
cout << "d1 = " << d1(100,100,0.1,0,0.5,0, 0.3) << endl;
cout << "d2 = " << d2(100,100,0.1,0,0.5,0, 0.3) << endl;
double cbsm = fairValueEcall(100,100,0.1,0,0.5,0, 0.3);
cout << "cbsm = " << cbsm <<endl;
double pbsm = fairValueEput(100,100,0.1,0,0.5,0, 0.3);
cout << "pbsm = " << pbsm <<endl;
double d_c = delta_c(100,100,0.1,0,0.5,0, 0.3);
cout << "delta_c = " << d_c <<endl;
double d_p = delta_p(100,100,0.1,0,0.5,0, 0.3);
cout << "delta_p = " << d_p<<endl;
cout << cbsm - pbsm << " = " << 100  - 100 * exp(-1*0.1 * 0.3)<<endl;
cout <<d_c - d_p <<" = "<< exp(0)<<endl<<endl;

cout << "d1 = " << d1(100,100,0.1,0.1,0.5,0,0.4) << endl;
cout << "d2 = " << d2(100,100,0.1,0.1,0.5,0,0.4) << endl;
cbsm = fairValueEcall(100,100,0.1,0.1,0.5,0,0.4);
cout << "cbsm = " << cbsm <<endl;
pbsm = fairValueEput(100,100,0.1,0.1,0.5,0,0.4);
cout << "pbsm = " << pbsm <<endl;
d_c = delta_c(100,100,0.1,0.1,0.5,0,0.4);
cout << "delta_c = " << d_c <<endl;
d_p = delta_p(100,100,0.1,0.1,0.5,0,0.4);
cout << "delta_p = " << d_p<<endl;
cout << cbsm - pbsm << " = " << 100*exp(-1*0.1*0.4)  - 100 * exp(-1*0.1 * 0.4)<<endl;
cout <<d_c - d_p <<" = "<< exp(-1*0.1*0.4)<<endl;

}
