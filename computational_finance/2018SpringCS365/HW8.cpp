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
int binomial(double S,        double K, double r,   double q,
             double sigma,    double T, double t0,  bool call,
             bool   American, int    n, double & V){

    if (n < 1 || S <= 0 || T <= t0 || sigma <= 0.0)return 1;
    double dt = (T - t0) /double(n);
    double df = exp(-r * dt);
    double growth = exp((r-q) * dt);
    double u = exp(sigma*sqrt(dt));
    double d= 1.0 / u;
    double p_prob = (growth -d)/(u-d);
    double q_prob = 1.0 - p_prob;

    if(p_prob < 0.0 || p_prob > 1.0)return 1;
    double **stock_nodes = new double*[n+1];
    double **option_nodes = new double*[n+1];

    double* S_tmp;
    double* V_tmp;
    for(int i = 0; i <= n; i++){
      stock_nodes[i] = new double[n+1];
      option_nodes[i] = new double[n+1];
      S_tmp = stock_nodes[i];
      V_tmp = option_nodes[i];
      for(int j = 0; j <= n; j++){
        S_tmp[j] = 0;
        V_tmp[j] = 0;
      }
    }
    //filling stock_nodes
    S_tmp = stock_nodes[0];
    S_tmp[0] = S;
    for(int i = 1; i <= n; i++){
      double * prev = stock_nodes[i-1];
      S_tmp = stock_nodes[i];
      S_tmp[0] = prev[0] * d;
      for(int j = 1; j <= n; j++){
        S_tmp[j] = S_tmp[j-1]*u*u;
      }
    }

    //terminal paid off
    int i = n;
    S_tmp = stock_nodes[i];
    V_tmp = option_nodes[i];
    for(int j = 0; j <= n; j++){
      double intrinsic = 0;
      if(call){
        if(S_tmp[j] < K)intrinsic = 0.0;
        else intrinsic = S_tmp[j] - K;
      }else{
        if(S_tmp[j] < K)intrinsic = K - S_tmp[j];
        else intrinsic = 0.0;
      }
      V_tmp[j] = intrinsic;
    }

    //main loop
    for(int i = n-1; i >=0; i--){
      S_tmp = stock_nodes[i];
      V_tmp = option_nodes[i];
      double * V_next = option_nodes[i+1];

      for(int j = 0; j <= i; j++){
        V_tmp[j] = df*(p_prob*V_next[j+1] + q_prob*V_next[j]);
        //check instrinsic inequality
        if(American){
            double intrinsic = 0 ;
            if(call){
              if(S_tmp[j] < K)intrinsic = 0.0;
              else intrinsic = S_tmp[j] - K;
            }else{
              if(S_tmp[j] < K)intrinsic = K - S_tmp[j];
              else intrinsic = 0.0;
            }

            if(V_tmp[j] < intrinsic){
              V_tmp[j] = intrinsic;
            }
        }
      }
    }

    i = 0;
    V_tmp = option_nodes[i];
    V = V_tmp[0];


    for (int i = 0; i <= n; i++) {
      delete [] stock_nodes[i];
      delete [] option_nodes[i];
    }
    delete [] stock_nodes;
    delete [] option_nodes;


    return 0;

}
//#############################  Part 3  ######################################
int main(){
//#############################  test for question 1.3 ########################
double V = -1.0;
binomial(100, 100, 0.1, 0.0, 0.5, 0.3, 0.0, true, false,3,V);
cout << "c~= " << V<<endl;
binomial(100, 100, 0.1, 0.0, 0.5, 0.3, 0.0, false, false,3,V);
cout << "p~= " << V<<endl;
binomial(100, 100, 0.1, 0.0, 0.5, 0.3, 0.0, true, true,3,V);
cout << "C~= " << V<<endl;
binomial(100, 100, 0.1, 0.0, 0.5, 0.3, 0.0, false, true,3,V);
cout << "P~= " << V<<endl<<endl;


binomial(100, 100, 0.1, 0.1, 0.5, 1, 0.0, true, false,100,V);
cout << "c~= " << V<<endl;
binomial(100, 100, 0.1, 0.1, 0.5, 1, 0.0, false, false,100,V);
cout << "p~= " << V<<endl;
binomial(100, 100, 0.1, 0.1, 0.5, 1, 0.0, true, true,100,V);
cout << "C~= " << V<<endl;
binomial(100, 100, 0.1, 0.1, 0.5, 1, 0.0, false, true,100,V);
cout << "P~= " << V<<endl<<endl;

binomial(150, 150, 0.05, 0.05, 0.2, 5, 0.0, true, false,100,V);
cout << "c~= " << V<<endl;
binomial(150, 150, 0.05, 0.05, 0.2, 5, 0.0, false, false,100,V);
cout << "p~= " << V<<endl;
binomial(150, 150, 0.05, 0.05, 0.2, 5, 0.0, true, true,100,V);
cout << "C~= " << V<<endl;
binomial(150, 150, 0.05, 0.05, 0.2, 5, 0.0, false, true,100,V);
cout << "P~= " << V<<endl<<endl;
}
