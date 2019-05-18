//this cpp file contains two parts
//part 1   Codes for Question 4, 5, 6 , 7
//part 2   Codes for Question 1, 2, 3  if you want to look at


#include <iostream>
#include <cmath>
#include <iomanip>
#include <fstream>
#include <vector>
using namespace std;
const static double tol =  1.0e-6;


//-------------------------------- Part 1 Question 4, 5 , 6 , 7 ----------------------------------
class Database
{
public:
  Database(){r = 0; q = 0;}
  ~Database(){}

  //data
  double r;
  double q;
};

class TreeNode
{
public:
  TreeNode(){S = 0; V= 0; t = 0;}
  ~TreeNode(){}

  //data
  double S;
  double V;
  double t;
};

class Derivative
{
public:
  virtual ~Derivative(){}
  virtual double TerminalPayoff(double S) const {return 0;}
  virtual int ValuationTests(TreeNode & node)const {return 0;}

  //data
  double T;
protected:
  Derivative(){ T= 0;}
};

// ------------------------------ options -------------------
class Option :public Derivative
{
public:
  Option() {K = 0; isCall = false; isAmerican = false;}
  virtual ~Option(){}
  virtual double TerminalPayoff(double S) const;
  virtual int ValuationTests(TreeNode & node) const;

  double K;
  bool isCall;
  bool isAmerican;
};
double Option::TerminalPayoff(double S) const
{
  if(isCall){
    if(S < K)return 0.0;
    else return S - K;
  }else{
    if(S < K)return K - S;
    else return 0.0;
  }
}
int Option::ValuationTests(TreeNode & node)const
{
  if(isAmerican){
      double intrinsic = 0.0;
      if(isCall){
        if(node.S < K)intrinsic = 0.0;
        else intrinsic = node.S - K;
      }else{
        if(node.S < K)intrinsic = K - node.S;
        else intrinsic = 0.0;
      }

      if(node.V < intrinsic){
        node.V = intrinsic;
      }
      return -1;
  }

  return 0;
}


// ------------------------------ straddle --------------------
class Straddle :public Derivative{

public:
  double K;
  bool isAmerican;
  Straddle(){K = 0;isAmerican = false;}
  virtual ~Straddle(){}
  virtual double TerminalPayoff(double S) const;
  virtual int ValuationTests(TreeNode & node) const;
};
double Straddle::TerminalPayoff(double S)const{
    if(S > K){return S-K;}
    else {return K - S;}
}
int Straddle::ValuationTests(TreeNode & node)const{
  if(isAmerican){
      double intrinsic = 0.0;
      if(node.S > K)intrinsic = node.S - K;
      else intrinsic = K - node.S;

      if(node.V < intrinsic){
        node.V = intrinsic;
      }
      return 1;
      //return 1 indicate Derivative value has been replaced by intrinsic value
  }
  return 0;
}


// ------------------------------ binary options --------------------
class BinaryOption :public Derivative{

public:
  double K;
  bool isAmerican;
  bool isCall;
  BinaryOption() {K = 0;isAmerican = false; isCall = false;}
  virtual ~BinaryOption(){}
  virtual double TerminalPayoff(double S) const;
  virtual int ValuationTests(TreeNode & node) const;
};
double BinaryOption::TerminalPayoff(double S) const
{
  if(isCall){
    if(S < K)return 0.0;
    else return 1;
  }else{
    if(S < K)return 1;
    else return 0.0;
  }
}
int BinaryOption::ValuationTests(TreeNode & node)const
{
  if(isAmerican){
      double intrinsic = 0.0;
      if(isCall){
        if(node.S < K)intrinsic = 0.0;
        else intrinsic = 1;
      }else{
        if(node.S < K)intrinsic = 1;
        else intrinsic = 0.0;
      }
      if(node.V < intrinsic){
        node.V = intrinsic;
      }
      return 1;
      //return 1 indicate Derivative value has been replaced by intrinsic value
  }
  return 0;
}

// ------------------------------ convertible bond --------------------
class ConvertibleBond :public Derivative{

public:
  ConvertibleBond() {K = 0;isAmerican = true;B = 0;}
  virtual ~ConvertibleBond(){}
  virtual double TerminalPayoff(double S) const;
  virtual int ValuationTests(TreeNode & node) const;

  double K;
  bool isAmerican;
  double B;
};
double ConvertibleBond::TerminalPayoff(double S) const
{

  if(S < K)return K;
  else return S;

}
int ConvertibleBond::ValuationTests(TreeNode & node)const
{
  if(isAmerican){

      //knock out
      if(node.S >= B){
        node.V = node.S;
        return 2;
        //return 2 means knock out
      }
      double intrinsic = node.S;
      if(node.V < intrinsic){
        node.V = intrinsic;
      }
      //return 1 indicate Derivative value has been replaced by intrinsic value
  }
  return 0;
}


class BinomialTree
{
public:
  BinomialTree(int n);
  ~BinomialTree();
  int FairValue(int n, const Derivative * p_derivative, const Database * p_db,
                double S, double sigma, double t0, double &FV);
  int ImpliedVolatility(int n, const Derivative * p_derivative, const Database * p_db,
                        double S, double t0, double B_target,
                        double & v, int & num_iter);
  private:
    //methods
    void Clear();
    int Allocate(int n);
    //data
    int n_tree;
    TreeNode ** tree_nodes;

};
BinomialTree::BinomialTree(int n)
{

  n_tree = 0;
  tree_nodes = 0;
  Allocate(n);

}
BinomialTree::~BinomialTree()
{
  Clear();
}
void BinomialTree::Clear()
{
  for (int i = 0; i <= n_tree; i++) {
    delete [] tree_nodes[i];
  }
  delete [] tree_nodes;
}
int BinomialTree::Allocate(int n)
{
  if(n <= n_tree)return 0;
  if(n_tree != 0)Clear();
  //allocate
  tree_nodes = new TreeNode* [n+1];
  for(int i = 0; i <= n; i++){
    tree_nodes[i] = new TreeNode[i+1];
  }

  n_tree = n;
  return 0;
}

int BinomialTree::ImpliedVolatility(int n, const Derivative * p_derivative, const Database * p_db,
                      double S, double t0, double B_target,
                      double & v, int & num_iter){

          int max_iter = 100;
          v = 0;
          num_iter = 0;
          if(B_target <= 0.0)return 1;
          double v_low = 0.01;
          double v_high = 2.0;
          double B_v_low = 0;
          FairValue(n, p_derivative, p_db, S, v_low,t0 ,B_v_low);
          double diff_B_v_low = B_v_low - B_target;
          if (fabs(diff_B_v_low) <= tol){
            v = v_low;
            return 0;
          }
          double B_v_high = 0;
          FairValue(n, p_derivative, p_db, S, v_high,t0 ,B_v_high);

          double diff_B_v_high = B_v_high - B_target;
          if (fabs(diff_B_v_high) <= tol){
            v = v_high;
            return 0;
          }

          if(diff_B_v_low * diff_B_v_high > 0){
            v = 0;
            return 1;
          }

          for(num_iter = 1; num_iter < max_iter; ++num_iter){
            v = (v_low + v_high)/2.0;
            double B = 0;
            FairValue(n, p_derivative, p_db, S, v,t0 ,B);
            double diff_B = B - B_target;
            if(fabs(diff_B) <= tol)return 0;

            if (diff_B * diff_B_v_low > 0.0){
              v_low = v;
            }else{
              v_high = v;
            }
            if(fabs(v_high-v_low) <= tol)return 0;
          }
          v = 0;
          return 1;


}
int BinomialTree::FairValue(int n, const Derivative * p_derivative, const Database * p_db,
                double S, double sigma, double t0, double &FV){

                //validation checks
                if (n < 1 || S <= 0 || p_derivative == 0 || p_db == 0
                  ||p_derivative->T <= t0 || sigma <= 0.0)return 1;

                //declaration of local variables, calculate parameters
                FV = 0;
                double dt = (p_derivative -> T - t0)/double(n);
                double df = exp(-p_db->r * dt);
                double growth = exp((p_db->r - p_db->q) * dt);
                double u = exp(sigma * sqrt(dt));
                double d = 1.0 / u;

                double p_prob = (growth - d) / (u - d);
                double q_prob = 1.0 - p_prob;

                //more validation checks
                if( p_prob < 0.0 || p_prob > 1.0)return 1;

                Allocate(n);

                //set up stock prices and times in tree nodes
                TreeNode * node_tmp = tree_nodes[0];
                node_tmp[0].S = S;
                node_tmp[0].t = t0;
                for(int i = 1; i <= n; i++){
                  double t = t0 + i*dt;
                  TreeNode * prev = tree_nodes[i-1];
                  node_tmp = tree_nodes[i];
                  node_tmp[0].S = prev[0].S * d;
                  node_tmp[0].t = t;

                  for(int j = 1; j <= i; j++){
                    node_tmp[j].S = node_tmp[j-1].S*u*u;
                    node_tmp[j].t = t;
                  }
                }

                //terminal payoff
                int i = n;
                node_tmp = tree_nodes[i];
                for(int j = 0; j <= n; j++){
                  node_tmp[j].V = p_derivative->TerminalPayoff(node_tmp[j].S);
                }
                //valuation loop
                for( int i = n-1; i>=0; i--){
                  node_tmp = tree_nodes[i];
                  TreeNode * node_next = tree_nodes[i+1];
                  for(int j = 0; j <= i; j ++){
                    node_tmp[j].V = df*(p_prob*node_next[j+1].V + q_prob * node_next[j].V);
                    p_derivative-> ValuationTests(node_tmp[j]);
                  }
                }

                // Derivative fair value
                node_tmp = tree_nodes[0];
                FV = node_tmp[0].V;
                return 0;
}

double cum_norm(double x){
  const double root = sqrt(0.5);
  return 0.5*(1.0 + erf(x*root));
}
//r is decimal not percentage
double d1(double S, double K, double r, double q, double v,double t0, double T){
    return (log(S/K) + (r-q)*(T-t0))/(v*sqrt(T-t0)) + 0.5 * v * sqrt(T-t0);
}
double d2(double S, double K, double r, double q, double v,double t0, double T){
    return d1(S,K,r,q,v,t0,T) - v * (sqrt(T-t0));
}
double BSMfairValueBput(double S, double  K, double r, double q, double v, double t0, double T){
    double n_negative_d2 = cum_norm(-1 * d2(S,K,r,q,v,t0,T));
    return exp(-1*r*(T-t0))*n_negative_d2;
}
double BSMfairValueBcall(double S, double  K, double r, double q, double v, double t0, double T){
    double n_d2 = cum_norm(d2(S,K,r,q,v,t0,T));
    return exp(-1*r*(T-t0))*n_d2;
}



//--------------------------------- end of codes for question 4, 5, 6 7----------------------------










//-------------------------------- for Question  1, 2, 3  --------------------------------

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

//set discount_factor for each cash flow for this Bond
//return 0 succeeds,  1 failed
int Bond::set_discount_factors(std::vector<double> &df){
  discount_factor.clear();
  if (df.size() < num_coupons) return 1;
  for(int i = 0; i < num_coupons; i++){
    if(df[i] <= 0.0)discount_factor.push_back(0.0);
    else discount_factor.push_back(df[i]);
  }
  return 0;
}



//the output for yield is percentage
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

//given discount_factor and target bond price calculate constant couponds
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

// input y (yield) is percentage
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
//the return interpolated rate is percentage
//the input of ri rj is percentage
double cfr(double t, double ri, double rj,
           double ti, double tj, double t0){

       double lamda;
       if(ti == tj)return -1.0;
       else lamda =  (t - ti)/(tj - ti);

       if(t == t0)return -1.0;
       return ((1-lamda)* ri * (ti - t0) + lamda * rj *(tj - t0)) / (t - t0);

}

// spot_rate is enter as percentage
double spot_rate_to_df(double spot_rate, double T, double t0){

    return exp(-1*spot_rate*0.01*(T- t0));
}

//----------------------- end of code for Question 1 , 2 , 3 ------------------------










int main(){


  cout << "-------------------          Question 1          --------------------"<<endl<<endl;
  cout << "****************************   Q1.1   *********************************" << endl;
  cout << "Q:Calculate the fair value of the bond in eq(1.1)？" <<endl;
  std::vector<double> df;
  double B = 0;
  double d_025 = spot_rate_to_df(0.99751,0.25,0);
  df.push_back(d_025);
  double d_050 =  spot_rate_to_df(0.99751,0.5,0);
  df.push_back(d_050);
  double d_075 = spot_rate_to_df(cfr(0.75,0.99751,1.61980,0.5,1.0,0),0.75,0);
  df.push_back(d_075);
  double d_100 = spot_rate_to_df(1.61980,1.0,0);
  df.push_back(d_100);
  double d_125 = spot_rate_to_df(cfr(1.25,1.61980,1.98461,1.0,1.5,0),1.25,0);
  df.push_back(d_125);
  double d_150 = spot_rate_to_df(1.98461,1.5,0);
  df.push_back(d_150);
  double d_175 = spot_rate_to_df(cfr(1.75,1.98461,2.24432,1.5,2.0,0),1.75,0);
  df.push_back(d_175);
  double d_200 = spot_rate_to_df(2.24432,2.0,0);
  df.push_back(d_200);
  B =   2.0/4*d_025 + 3.0/4*d_050 + 5.0/4*d_075 + 1.0/4*d_100 + 0.0/4 * d_125
      + 3.0/4*d_150 + 6.0/4*d_175 + (100+2.0/4)*d_200;
  printf ("Answer:  BFV = %.2f\n\n",B);


  cout << "******************************* Q1.2 **********************************" << endl;
  cout << "Q:Calculate the yield of the bond in eq(1.1) if the bond market price is Bfv?"<<endl;
  double coupons[] = {2.0,3.0,5.0,1.0,0.0,3.0,6.0,2.0};
  std::vector<double>c;
  for(int i = 0; i < 8; i++){
    c.push_back(coupons[i]);
  }
  Bond * my_bond = new Bond(2, 100, c , 4);
  my_bond -> set_discount_factors(df);
  double y = 0;
  int num_iter = 0;
  my_bond-> bisection_yield(B, tol, 100, 0, y, num_iter);
  printf("Answer:  yield = %.2f%%\n\n",y);

  cout << "******************************* Q1.3 **********************************" << endl;
  cout << "Q:find the value of c?"<<endl;
  double replaced_c = 0;
  my_bond-> bisection_coupon(B, tol, 100, replaced_c,  num_iter);
  printf("Answer:  c = %.2f\n",replaced_c);
  clearSpace();

  cout <<"---------------------           Question 2         -------------------"<<endl;
  cout << "**************************** Q2.1 (Case 1) ****************************" << endl;
  cout << "Q:Formulate an arbitrage strategy: " <<endl;
  cout << "Answer: "<<endl;
  cout << "        1:Short one stock" << endl;
  cout << "        2:Short one American put  option" <<endl;
  cout << "        3:long  one American call option" <<endl;
  cout << "        profit (when we close out all position)" <<endl;
  cout << "        = (P + S - C)*e^r(t-t0) - K"<<endl;
  cout << "        = (  100.5  )*e^r(t-t0) - 100 > 0"<<endl;
  cout << "        it means that the profit for this strategy is always positive" <<endl<<endl;

  cout << "*************************** Q2.2 (Case 2) *****************************" << endl;
  cout << "Q:Formulate an arbitrage strategy: " <<endl;
  cout << "Answer: "<<endl;
  cout << "        1:Short C1" << endl;
  cout << "        2:Long  C2" << endl;
  cout << "        profit (when we close out all position) at time t" << endl;
  cout << "        = (C1 - C2)*e^r(t-t0)                where St < K1"<<endl;
  cout << "         -(St - K1) + (C1 - C2)*e^r(t-t0)    where K1 <= St <= K2"<<endl;
  cout << "         -(K2 - K1) + (C1 - C2)*e^r(t-t0)    where St > K2"<<endl;
  cout << "        > 0 for all scenario(When C1 - C2 > K2 - K1)" <<endl;
  cout << "        it means that the profit for this strategy is always positive" <<endl;
  cout << "        "<<endl<<endl;

  cout << "*************************** Q2.3 (Case 3) *****************************" << endl;
  cout << "Q:Formulate an arbitrage strategy: " <<endl;
  cout << "Answer: "<<endl;
  cout << "        1:Short P2" << endl;
  cout << "        2:Long  P1" <<endl;
  cout << "        profit (when we close out all position) at time t" << endl;
  cout << "        = -(K2 - K1) + (P2 - P1)*e^r(t-t0)    where St < K1"<<endl;
  cout << "           (St - K2) + (P2 - P1)*e^r(t-t0)    where K1 <= St <= K2"<<endl;
  cout << "           (P2 - P1)*e^r(t-t0)                where St > K2"<<endl;
  cout << "        > 0 for all scenario(When P2 - P1 > K2 - K1)" <<endl;
  cout << "        it means that the profit for this strategy is always positive" <<endl;
  cout << "        "<<endl<<endl;





  cout <<"----------------------        Question 3       -----------------------"<<endl;
  cout << "******************************* Q3.1 **********************************" << endl;
  cout << "Q:Use eq (3.1) to prove relation"<<endl;
  cout << "Proof: " <<endl;
  cout << "       according to put call parity, "<<endl;
  cout << "       c1 - p1 = S - K1*e^-r(T-t)"<<endl;
  cout << "       2*c2 - 2*p2 = 2*S - 2*(K2*e^-r(T-t))"<<endl;
  cout << "       c3 - p3 = S - K3*e^-r(T-t)"<<endl;
  cout << "       ====>  c1 + c3 - p1 - p3 - 2*c2 + 2*p2  =  0"<<endl;
  cout << "       ====>  c1 - 2*c2 + c3                   =  p1 + p3 - 2p2"<<endl<<endl;

  cout << "************************ Q3.21 American Call **************************" << endl;
  cout << "Q:Show that Bcall(S,t) < 0 if the following equations holds"<<endl;
  cout << "C2(S, t) > (C1(S, t) + C3(S, t))/2"<<endl;
  cout << "Answer:   "<<endl;
  cout << "       Bcall(S,t) = C1(S,t) + C3(S,t) - 2C2(S,t)" <<endl;
  cout << "       if    C2(S,t) > (C1(S,t) + C3(S,t)) / 2"<<endl;
  cout << "       ====> (C1(S,t) + C3(S,t)) - 2C2(S,t) < 0" <<endl;
  cout << "       ====> Bcall(S,t) < 0"<<endl<<endl;

  cout << "****************************** Q3.22 **********************************" << endl;
  cout << "Q:Formulate an arbitrage trade if C2(S, t) > (C1(S, t) + C3(S, t))/2."<<endl;
  cout << "Answer: "<<endl;
  cout << "        We form arbitrage by long the butterfly spread"<<endl;
  cout << "        We pay C1, get 2C2, pay C3"<<endl;
  cout << "        in the end we have cash (2C2(S,t) - C1(S,t) - C3(S,t)) in the bank"<<endl;
  cout << "        the terminal diagram show that the payoff >= 0 in all scenario"<<endl;
  cout << "        plus we have positive amount of money in the bank"<<endl;
  cout << "        Thus this trades always yield positive profit"<<endl<<endl;

  cout << "******************************* Q3.23 **********************************" << endl;
  cout << "Q:Therefore deduce the following inquality must be true at any time t <= T"<<endl;
  cout << "C2(S, t) <= (C1(S, t) + C3(S, t))/2"<<endl;
  cout << "Answer: "<<endl;
  cout << "        if C2(S,t) > (C1(S,t) + C3(S,t)) / 2"<<endl;
  cout << "        We can form arbitrage by the trade described above"<<endl<<endl;


  cout << "*********************** Q3.31 American puts ***************************" << endl;
  cout << "Q:Show that Bput(S,t) < 0 if the following equations holds"<<endl;
  cout << "P2(S, t) > (P1(S, t) + P3(S, t))/2"<<endl;
  cout << "Answer:   "<<endl;
  cout << "       Bput(S,t) = P1(S,t) + P3(S,t) - 2P2(S,t)" <<endl;
  cout << "       if    P2(S,t) > (P1(S,t) + P3(S,t)) / 2"<<endl;
  cout << "       ====> (P1(S,t) + P3(S,t)) - 2P2(S,t) < 0" <<endl;
  cout << "       ====> Bput(S,t) < 0"<<endl<<endl;

  cout << "******************************* Q3.32 **********************************" << endl;
  cout << "Q:Formulate an arbitrage trade if P2(S, t) > (P1(S, t) + P3(S, t))/2."<<endl;
  cout << "Answer: "<<endl;
  cout << "        We form arbitrage by long the butterfly spread"<<endl;
  cout << "        We pay P1, get 2P2, pay P3"<<endl;
  cout << "        in the end we have cash (2P2(S,t) - P1(S,t) - P3(S,t)) in the bank"<<endl;
  cout << "        the terminal diagram show that the payoff >= 0 in all scenario"<<endl;
  cout << "        plus we have positive amount of money in the bank"<<endl;
  cout << "        Thus this trades always yield positive profit"<<endl<<endl;

  cout << "******************************* Q3.33 *********************************" << endl;
  cout << "Q:Therefore deduce the following inquality must be true at any time t <= T"<<endl;
  cout << "P2(S, t) <= (P1(S, t) + P3(S, t))/2"<<endl;
  cout << "Answer: "<<endl;
  cout << "        if P2(S,t) > (P1(S,t) + P3(S,t)) / 2"<<endl;
  cout << "        We can form arbitrage by the trade described above"<<endl<<endl;
  cout << "***********************************************************************" << endl;




  // ---------------------  my test for Question 4 for binomial model -----------
  double S,K,r,q,sigma,T,t0;
  int n;
  Database db;
  S = 100; K = 100; r = 0.1; q = 0.0;
  sigma = 0.5; T =0.3; t0 = 0.0; n =3;
  db.r = r;
  db.q = q;
  Option Eur_put;               Option Am_put;
  Eur_put.K = K;                Am_put.K = K;
  Eur_put.T = T;                Am_put.T = T;
  Eur_put.isCall = false;       Am_put.isCall = false;
  Eur_put.isAmerican = false;   Am_put.isAmerican = true;

  Option Eur_call;              Option Am_call;
  Eur_call.K = K;               Am_call.K = K;
  Eur_call.T = T;               Am_call.T = T;
  Eur_call.isCall = true;       Am_call.isCall = true;
  Eur_call.isAmerican = false;  Am_call.isAmerican = true;
  double FV;
  BinomialTree bi(n);
  bi.FairValue(n, &Eur_call, &db, S, sigma, t0,FV);
  cout << "my Test for Question 4 for binomial model" <<endl;
  cout << "c = " << FV<<endl;
  bi.FairValue(n, &Eur_put, &db, S, sigma, t0,FV);
  cout << "p = " << FV<<endl;
  bi.FairValue(n, &Am_call, &db, S, sigma, t0,FV);
  cout << "C = " << FV<<endl;
  bi.FairValue(n, &Am_put, &db, S, sigma, t0,FV);
  cout << "P = " << FV<<endl;
  cout << "end of test code for binomial model"<<endl<<endl;
  cout << "***********************************************************************" << endl<<endl;


  // -------------  my output for Question 6 (output to file commented out) -----------
  // std::ofstream ofs("output5.csv");
  // ofs.precision(6);
  r = 0.022399;
  q = 0.02;
  T = 1;
  t0 = 0;
  db.r = r;
  db.q = q;
  S = 90;
  K = 100;
  sigma = 0.0;
  BinaryOption B_euro_call;        BinaryOption B_euro_put;
  B_euro_call.K = K;               B_euro_put.K = K;
  B_euro_call.T = T;               B_euro_put.T = T;
  B_euro_call.isCall = true;       B_euro_put.isCall =false;
  B_euro_call.isAmerican = false;  B_euro_put.isAmerican = false;
  double FV_B_euro_call = 0;       double FV_B_euro_put = 0;
  n = 1000;
  BinomialTree binom(n);
  int imax = 100;
  for(int i = 1; i <=imax; i++){
      sigma = i * 0.01;
      binom.FairValue(n, &B_euro_call, &db, S, sigma, t0, FV_B_euro_call);
      double fvc = BSMfairValueBcall(S, K, r, q, sigma, 0, T);
      binom.FairValue(n, &B_euro_put, &db, S, sigma, t0, FV_B_euro_put);
      double fvp = BSMfairValueBput(S, K, r, q, sigma, 0, T);
      // ofs << sigma <<","<< fvp << std::endl;
 }
 // -------------  end of Question 6 (output to file commented out) -----------




 cout << "----------------------------- Question 7 -------------------------------"<<endl;
 cout << "my Test for implied volatility calculation" <<endl;
 db.r = 0.05; db.q = 0.02; double vol = 0;
 BinomialTree b(100);
 Am_put.K = 100; Am_put.T = 0.5;
 b.ImpliedVolatility(1000, &Am_put,&db,105,0, 9,vol,  num_iter);
 cout << "impliedV1 = "<<vol<<endl;
 cout << "end of test code for implied volatility"<<endl<<endl;

 cout << "******************************   Q7.1   ********************************"<<endl;
 cout << "Q:Prove that V >= PV(K) for a zero coupon convertible bond" <<endl;
 cout << "A:"<<endl;
 cout << "      If V < PV(K), we can form a Arbitrage by going long the convertible bond"<<endl;
 cout << "      Case 1:, St >= B > K for t0 < t < T" <<endl;
 cout << "      then our profit = St - Ve^r(t-t0) > St - K > 0"<<endl;
 cout << "      Case 2:, if St < B for t0 < t < T, then at expiration date"<<endl;
 cout << "      we get profit max(ST, K) > K > V*e^r(T-t0) > 0,  we also get profit" <<endl;
 cout << "      hence it is arbitrage" << endl<<endl;

 db.r = 0.05;
 db.q = 0;
 ConvertibleBond cB;
 cB.T = 5.0;
 cB.K = 100;
 cB.B = 130;
 cB.isAmerican = true;
 double FV_cB;
 for(int i = 1; i <= 1; i++){
   binom.FairValue(n, &cB, &db, i, 0.5, t0, FV_cB);
   // ofs << i <<","<< FV_cB << std::endl;
 }
  cout << "******************************   Bonus   *******************************"<<endl;
  cout << "Q:Explain why the fair value at S -> 0 is the same in both graphs"<<endl;
  cout << "A:"<<endl;
  cout << "    Because S0 --> 0, the volatility doesn't impact the stock price "<<endl;
  cout << "    in the binomial tree nodes in a big way, So the calculated fair value becomes"<<endl;
  cout << "    relatively independent of volatility"<<endl<<endl;

  cout << "******************************   Q7.3   ********************************"<<endl;
  double S0 = 60;
  double M0 = 90;
  double dS1 = 0.2351;
  double dS2 = -0.0362;
  double v = 0.0;
  num_iter = 0;
  cout << "delta S1 = " << dS1 <<endl;
  cout << "delta S2 = " << dS2 <<endl;
  cout << "-------------------------------- day 0 ----------------------------------"<<endl;

  binom.ImpliedVolatility(1000, &cB, &db,S0, 0, M0,v, num_iter);
  printf("implied volatility is %.4f\n", v);
  double U_S0_P1 = 0;
  binom.FairValue(n, &cB, &db, S0 +1 , v, t0, U_S0_P1);
  double U_S0_N1 = 0;
  binom.FairValue(n, &cB, &db, S0 -1, v, t0, U_S0_N1);
  double delta0 = (U_S0_P1 - U_S0_N1)/2.0;
  cout << "delta 0 = " << delta0 << endl;
  double moneyy0 = delta0 * S0 - M0;
  printf("money_y0 =  %.2f\n\n",moneyy0);

  cout << "-------------------------------- day 1 ----------------------------------"<<endl;

  double S1 = S0 + dS1;
  double M1 = 90.2;

  binom.ImpliedVolatility(1000, &cB, &db,S1, 0.01, M1,v, num_iter);
  printf("implied volatility is %.4f\n", v);
  binom.FairValue(n, &cB, &db, S1 + 1, v, 0.01, U_S0_P1);
  binom.FairValue(n, &cB, &db, S1 - 1, v, 0.01, U_S0_N1);
  double delta1 = (U_S0_P1 - U_S0_N1)/2.0;
  cout << "delta1 = " << delta1<<endl;
  double moneyy1 = moneyy0 + (delta1 - delta0) *S1;
  printf("money_y1 =  %.2f\n\n",moneyy1);

  cout << "-------------------------------- day 2 ----------------------------------"<<endl;
  double S2 = S1 + dS2;
  double M2 = 90.15;

  binom.ImpliedVolatility(1000, &cB, &db,S2, 0.02, M2,v, num_iter);
  printf("implied volatility is %.4f\n", v);
  binom.FairValue(n, &cB, &db, S2 + 1, v, 0.02,U_S0_P1);
  binom.FairValue(n, &cB, &db, S2 - 1, v, 0.02,U_S0_N1);
  double delta2 = (U_S0_P1 - U_S0_N1)/2.0;
  cout << "delta2 = " << delta2 << endl;
  double moneyy2 = moneyy1 + (delta2 - delta1) *S2;
  printf("money_y2 =  %.2f\n\n",moneyy2);

  cout << "-------------------------------- end of day 2 ----------------------------------"<<endl;

  double profit = moneyy2 + M2 - delta2 * S2;
  printf("my profit is %.4f\n", profit);

}
