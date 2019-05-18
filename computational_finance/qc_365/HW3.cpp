
#include <iostream>
#include <cmath>
#include <vector>
using namespace std;
const static double tol =  1.0e-6;
void clearSpace(){
    printf("\n\n\n\n");
}
class Bond{

  public:
    Bond(double T, double F, std::vector<double> & c , int freq=2);
    ~Bond();

    //public methods
    int set_coupons(std::vector<double> & c);
    int set_discount_factors(std::vector<double> &df);
    int FV(double t0, double y,double &B)const;
    int bisection_yield(double B_target, double tol, int max_iter, double t0,
                    double & y, int & num_iter)const;
    int bisection_coupon(double B_target, double tol, int max_iter,
                    double & c, int & num_iter)const;
    double FairValue(double t0, double y) const;
    double FairValue_coupon(double c) const;

    double maturity()const {return T_maturity;}

  private:
    //data
    double Face;
    double T_maturity;
    int cpn_freq;
    int num_coupons;
    std::vector<double> coupons;
    std::vector<double> discount_factor;
};

Bond::Bond(double T, double F, std::vector<double> & c, int freq){
  if(F < 0)throw std::invalid_argument("Face value can't be negative");
  if(freq < 1) throw std::invalid_argument("coupond frequency can't be less than 1");
  Face = F;
  cpn_freq = freq;
  num_coupons = int(cpn_freq * T + tol);
  if(num_coupons < 0)
    throw std::invalid_argument("coupond frequency can't be less than 1");
  T_maturity = num_coupons / cpn_freq;

  if (num_coupons > 0) {
    set_coupons(c);
  }
}

Bond::~Bond(){
  coupons.clear();
  num_coupons = 0;
}

int Bond::set_coupons(std::vector<double> & c){
    coupons.clear();
    if (c.size() < num_coupons) return 1;
    for(int i = 0; i < num_coupons; i++){
      if(c[i] <= 0.0)coupons.push_back(0.0);
      else coupons.push_back(c[i]);
    }
    return 0;
}
int Bond::set_discount_factors(std::vector<double> &df){
  discount_factor.clear();
  if (df.size() < num_coupons) return 1;
  for(int i = 0; i < num_coupons; i++){
    if(df[i] <= 0.0)discount_factor.push_back(0.0);
    else discount_factor.push_back(df[i]);
  }
  return 0;
}
// int Bond::bisection_for_c(double B_target, double tol,
//                           int max_iter, double & c,
//                           int &num_iter)const{
//     return bisection(B_target,tol, max_iter,t0,y, num_iter, FairValue);
// }



//yield is percentage
int Bond::bisection_yield(double B_target, double tol, int max_iter, double t0,
                double & y, int & num_iter)const{
  y = 0;
  num_iter = 0;
  if(B_target <= 0.0 || num_coupons <= 0 || t0 >= T_maturity)return 1;
  double y_low = 0.0;
  double y_high = 100.0;
  double B_y_low = FairValue(t0, y_low);
  double diff_B_y_low = B_y_low - B_target;
  if (fabs(diff_B_y_low <= tol)){
    y = y_low;
    return 0;
  }
  double B_y_high = FairValue(t0, y_high);

  double diff_B_y_high = B_y_high - B_target;
  if (fabs(diff_B_y_high) <= tol){
    y = y_high;
    return 0;
  }

  if(diff_B_y_low * diff_B_y_high > 0){
    y = 0;
    return 1;
  }

  for(num_iter = 1; num_iter < max_iter; ++num_iter){
    y = (y_low + y_high)/2.0;
    double B = FairValue(t0, y);
    double diff_B = B - B_target;
    if(fabs(diff_B) <= tol)return 0;

    if (diff_B * diff_B_y_low > 0.0){
      y_low = y;
    }else{
      y_high = y;
    }
    if(fabs(y_high-y_low) <= tol)return 0;
  }
  y = 0;
  return 1;

}

//
int Bond::bisection_coupon(double B_target, double tol, int max_iter,
                double & c, int & num_iter)const{
  c = 0;
  num_iter = 0;
  if(B_target <= 0.0 || discount_factor.size() <= 0)return 1;
  double c_low = 0.0;
  double c_high = Face;
  double B_c_low = FairValue_coupon(c_low);
  double diff_B_c_low = B_c_low - B_target;
  if (fabs(diff_B_c_low)<= tol){
      cout << B_c_low<<endl;
    c = c_low;
    return 0;
  }
  double B_c_high = FairValue_coupon(c_high);

  double diff_B_c_high = B_c_high - B_target;
  if (fabs(diff_B_c_high) <= tol){
    c = c_high;
    return 0;
  }

  if(diff_B_c_low * diff_B_c_high > 0){
    c = 0;
    return 1;
  }

  for(num_iter = 1; num_iter < max_iter; ++num_iter){
    c = (c_low + c_high)/2.0;
    double B = FairValue_coupon(c);
    double diff_B = B - B_target;
    if(fabs(diff_B) <= tol)return 0;

    if (diff_B * diff_B_c_low > 0.0){
      c_low = c;
    }else{
      c_high = c;
    }
    if(fabs(c_high-c_low) <= tol)return 0;
  }
  c = 0;
  return 1;

}
double Bond::FairValue_coupon(double c) const{
    double B = 0;
    for(int i = 0; i < discount_factor.size(); i++){
        if(i == discount_factor.size() -1){
            B += ((Face + c / cpn_freq) * discount_factor[i]);
        }else B += (discount_factor[i] * c / cpn_freq);
    }
    return B;

}


double Bond::FairValue(double t0, double y) const{
  double B = 0;
  FV(t0, y, B);
  return B;
}

// y is percentage
int Bond::FV(double t0, double y, double & B)const{

  if(num_coupons <= 0 || t0 >= T_maturity)return 1;
  double for_bond_price = 0.0;
  for(int i = 1; i <= num_coupons; i++){
      double ti = double(i)/double(cpn_freq);
      if(ti >= t0 + tol){
        double numerator = coupons[i-1]/cpn_freq;
        double denominator = (1 + (0.01*y)/cpn_freq);
        denominator = pow(denominator, cpn_freq *(ti-t0));
        if(i == num_coupons){
          numerator += Face;
        }
        for_bond_price += numerator / denominator;
      }
  }
  B = for_bond_price;
  return 0;
}
//return -1 means failed
//return percentage
//input percentage
double cfr(double t, double ri, double rj,
           double ti, double tj, double t0){

       double lamda;
       //lamda = (t-ti) / (tj - ti);
       if(ti == tj)return -1.0;
       else lamda =  (t - ti)/(tj - ti);

       if(t == t0)return -1.0;

       return ((1-lamda)* ri * (ti - t0) + lamda * rj *(tj - t0)) / (t - t0);

}
//input percentage
//return decimal
double cfr_to_df(double cfr, double T, double t0){
    return exp(-1*cfr*0.01*(T- t0));
}


int main(){
    std::vector<double> df;
    double B = 0;
    double d_025 = cfr_to_df(0.99751,0.25,0);
    df.push_back(d_025);
    double d_050 =  cfr_to_df(0.99751,0.5,0);
    df.push_back(d_050);
    double d_075 = cfr_to_df(cfr(0.75,0.99751,1.61980,0.5,1.0,0),0.75,0);
    df.push_back(d_075);
    double d_100 = cfr_to_df(1.61980,1.0,0);
    df.push_back(d_100);
    double d_125 = cfr_to_df(cfr(1.25,1.61980,1.98461,1.0,1.5,0),1.25,0);
    df.push_back(d_125);
    double d_150 = cfr_to_df(1.98461,1.5,0);
    df.push_back(d_150);
    double d_175 = cfr_to_df(cfr(1.75,1.98461,2.24432,1.5,2.0,0),1.75,0);
    df.push_back(d_175);
    double d_200 = cfr_to_df(2.24432,2.0,0);
      df.push_back(d_200);
    B =   2.0/4*d_025 + 3.0/4*d_050 + 5.0/4*d_075 + 1.0/4*d_100 + 0.0/4 * d_125
        + 3.0/4*d_150 + 6.0/4*d_175 + (100+2.0/4)*d_200;
    cout << "******************* Question 1.1 *******************" << endl;
    cout << "the fair value of bond in eq(1.1) = " <<B <<endl;
    double coupons[] = {2.0,3.0,5.0,1.0,0.0,3.0,6.0,2.0};
    std::vector<double>c;
    for(int i = 0; i < 8; i++){
      c.push_back(coupons[i]);
    }
    Bond * my_bond = new Bond(2, 100, c , 4);
    my_bond -> set_discount_factors(df);
    double y = 0;
    int num_iter = 0;
    my_bond-> bisection_yield(B, tol, 100, 0,y,num_iter);
    clearSpace();
    cout << "******************* Question 1.2 *******************" << endl;
    cout << "the yield y of the bond in if the bond price is " <<  B <<endl;
    cout << "the answer = " << y<<endl;
    double cp = 0;
    my_bond-> bisection_coupon(B,tol,100,cp,num_iter);
    clearSpace();
    cout << "******************* Question 1.3 *******************" << endl;
    cout << "the value of c (based on this Bond Fair Value) = " << cp << endl;

    clearSpace();
    cout <<"--------------------Question 2 -----------------------"<<endl;
    cout << "******************* Question 2.1 (Case 1)*******************" << endl;
    cout << "Arbitrage strategy: " <<endl;
    cout << "1:Short one stock" << endl;
    cout << "2:Short one put option" <<endl;
    cout << "3:long one call" <<endl;
    cout << "profit (if either put or call got excercised) at time t" << endl;
    cout << "= (P + S - C)*e^r(t-t0) - K"<<endl;
    cout << "= (100.5)*e^r(t-t0) - 100 > 0"<<endl;
    cout << "it means that the profit for this strategy is always positive" <<endl;
    cout << "******************* Question 2.2 (Case 2)*******************" << endl;


    cout <<"--------------------Question 3 -----------------------"<<endl;
    cout << "******************* Question 3.1 *******************" << endl;

    cout << "proof: " <<endl;
    cout << "according to put call parity, "<<endl;
    cout << "c1 - p1 = S - K1*e^-r(T-t)"<<endl;
    cout << "2*c2 - 2*p2 = 2*S - 2*(K2*e^-r(T-t))"<<endl;
    cout << "c3 - p3 = S - K3*e^-r(T-t)"<<endl;
    cout << "by add equation 1 and 3 substracting equation3"<<endl;
    cout << "====>  c1 + c3 - p1 - p3 - 2*c2 + 2*p2  =  0"<<endl;
    cout << "====>  c1 - 2*c2 + c3                   =  p1 + p3 - 2p2"<<endl;
    cout << "******************* Question 3.2 *******************" << endl;



}
