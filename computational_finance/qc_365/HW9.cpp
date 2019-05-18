#include <iostream>
#include <cmath>
#include <iomanip>
#include <fstream>
using namespace std;
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


class BinomialTree
{
public:
  BinomialTree(int n);
  ~BinomialTree();
  int FairValue(int n, const Derivative * p_derivative, const Database * p_db,
                double S, double sigma, double t0, double &FV);

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

int BinomialTree::FairValue(int n, const Derivative * p_derivative, const Database * p_db,
              double S, double sigma, double t0, double &FV){

                if (n < 1 || S <= 0 || p_derivative == 0 || p_db == 0
                  ||p_derivative->T <= t0 || sigma <= 0.0)return 1;

                  double dt = (p_derivative -> T - t0)/double(n);
                  double df = exp(-p_db->r * dt);
                  double growth = exp((p_db->r - p_db->q) * dt);
                  double u = exp(sigma * sqrt(dt));
                  double d = 1.0 / u;

                  double p_prob = (growth - d) / (u - d);
                  double q_prob = 1.0 - p_prob;

                  if( p_prob < 0.0 || p_prob > 1.0)return 1;
                  Allocate(n);
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

                  int i = n;
                  node_tmp = tree_nodes[i];
                  for(int j = 0; j <= n; j++){
                    node_tmp[j].V = p_derivative->TerminalPayoff(node_tmp[j].S);
                  }

                  for( int i = n-1; i>=0; i--){
                    node_tmp = tree_nodes[i];
                    TreeNode * node_next = tree_nodes[i+1];
                    for(int j = 0; j <= i; j ++){
                      node_tmp[j].V = df*(p_prob*node_next[j+1].V + q_prob * node_next[j].V);
                      p_derivative-> ValuationTests(node_tmp[j]);
                    }
                  }

                  node_tmp = tree_nodes[0];
                  FV = node_tmp[0].V;
                  return 0;
              }
int main(){



          Database d;
          d.r = 0.1;
          d.q = 0.0;

          double sigm = 0.5;
          Option Eur_calls;
          Eur_calls.K = 100;
          Eur_calls.T = 0.3;
          Eur_calls.isCall = true;
          Eur_calls.isAmerican = false;
          double FV = 0;
          BinomialTree bi(3);
          bi.FairValue(3, &Eur_calls, &d, 100, sigm, 0.0, FV);
          cout <<"nimeide = " <<FV <<endl;

  // int rc = 0;
  //         std::ofstream ofs("output.txt");
  //         ofs.precision(6);
  //         double r = 0.05;
  //         double q = 0.02;
  //         double T = 1.0;
  //         double t0 = 0;
  //         Database db;
  //         db.r = r;
  //         db.q = q;
  //         double S = 100;
  //         double K = 100;
  //         double sigma = 0.5;
  //         Option Eur_put;
  //         Eur_put.K = K;
  //         Eur_put.T = T;
  //         Eur_put.isCall = false;
  //         Eur_put.isAmerican = false;
  //         Option Am_put;
  //         Am_put.K = K;
  //         Am_put.T = T;
  //         Am_put.isCall = false;
  //         Am_put.isAmerican = true;
  //         Option Eur_call;
  //         Eur_call.K = K;
  //         Eur_call.T = T;
  //         Eur_call.isCall = true;
  //         Eur_call.isAmerican = false;
  //         Option Am_call;
  //         Am_call.K = K;
  //         Am_call.T = T;
  //
  //         Am_call.isCall = true;
  //         Am_call.isAmerican = true;
  //         double FV_Am_put = 0;
  //         double FV_Eur_put = 0;
  //         double FV_Am_call = 0;
  //         double FV_Eur_call = 0;
  //         int n = 100;
  //         BinomialTree binom(n);
  //         double dS = 0.1;
  //         int imax = 2000;
  //         for (int i = 1; i <= imax; ++i) {
  //               S = i*dS;
  //               rc = binom.FairValue(n, &Am_put, &db, S, sigma, t0, FV_Am_put);
  //               rc = binom.FairValue(n, &Eur_put, &db, S, sigma, t0, FV_Eur_put);
  //               rc = binom.FairValue(n, &Am_call, &db, S, sigma, t0, FV_Am_call);
  //               rc = binom.FairValue(n, &Eur_call, &db, S, sigma, t0, FV_Eur_call);
  //               if (n > 100)
  //               n = 99;
  //               else
  //               n = 101;
  //               ofs << std::setw(6) << i << "  ";
  //               ofs << std::setw(16) << S << "  ";
  //               ofs << std::setw(16) << FV_Am_put << "  ";
  //               ofs << std::setw(16) << FV_Eur_put << "  ";
  //               ofs << std::setw(16) << FV_Am_call << "  ";
  //               ofs << std::setw(16) << FV_Eur_call << "  ";
  //               ofs << std::endl;
  //        }
  //        return 0;
      }
