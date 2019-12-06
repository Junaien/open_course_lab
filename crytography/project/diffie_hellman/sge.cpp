#include <iostream>
#include <vector> 
#include <fstream>
#include <NTL/ZZ.h>
#include <NTL/mat_ZZ_p.h>
#include <NTL/vector.h>
#include <NTL/ZZ_p.h>
#include <NTL/RR.h>
#include <unordered_map>
#include <assert.h>
#include <queue> 
#include <list> 
#include <set>
#include <unordered_set> 
using namespace std;
using namespace NTL;
class ZZ_hash {
public: 
  size_t operator()(const ZZ& p) const {   
      std::hash<long> long_hash;
      return long_hash(conv<long>(p));
  } 
};

class Node {
public:
  int p;
  int f;
  Node(int position, int coefficient) {
    p = position;
    f = coefficient;
  }

  bool operator==(const Node & n) const {
    return n.p == p;
  }
};

class Node_hash {
public: 
  size_t operator()(const Node& n) const {   
    int result = 17; 
    result = 31 * result + n.p;
    result = 31 * result + n.f;
    return result;
  }
};

bool operator<(const Node& n1, const Node& n2)
{
    return n1.f > n2.f;
}

unordered_map<ZZ, int, ZZ_hash> read_fb(long B) {
  ifstream File;
  unordered_map<ZZ, int, ZZ_hash> prime_index;
  File.open("primes.txt");
  while(!File.eof()) {  
    ZZ temp;
    File >> temp;
    if (temp > B) break;
    prime_index[temp] = prime_index.size();
  }
  File.close();
  return prime_index;
}

int main() {
  ZZ tt;
  // global variable
  unordered_map<ZZ, int, ZZ_hash> prime_index = read_fb(328166);
  vector<unordered_set<Node, Node_hash>> rows;
  set<int> a_c_set;
  vec<int> a_c;

  // read pime
  int num_f = 10;
  ZZ p;
  int num_r[10];
  ifstream File;
  File.open("input_150b.txt");
  File >> p;
  File.close();

  // step 1: 
  {
    string prefix = "../c=100000/f";
    for (int i = 1; i <= num_f; i++) {
      long row = 0, col = 0;
      string path = (prefix + to_string(i) + ".txt");
      File.open(path);
      File >> row >> col;
      num_r[i-1] = row;
      File.close();
    }
  }

  // step 2: merge all relations
  {
    string prefix = "../c=100000/m";
    for (int n = 1; n <= num_f; n++) {
      string path = prefix + to_string(n) + ".txt";
      File.open(path);

      cout << "reading from " << path << endl;
      for (int i = 0; i < num_r[n-1]; i++) {
        unordered_set<Node, Node_hash> temp;
        ZZ residue, prime;
        long coeff;

        File >> residue;
        File >> prime;
        File >> coeff;
        while (!(prime == 0 && coeff == 0)) {
          if (prime_index.find(prime) == prime_index.end()) {
            prime_index[prime] = cols.size();
          }

          temp.insert(Node(prime_index[prime], coeff)); 
          a_c_set.insert(prime_index[prime]);
          // if (coeff != -1) {
          //   assert(residue % prime == 0);
          // } else {
          //   assert((residue + p) % prime == 0);
          // } 
          File >> prime >> coeff;
        }
        rows.push_back(temp); // insert row to original sparse matrix
      }
      File.close();
    }
    cout << "checking process passed......" << endl;
  }

  // step 3: structured gaussian elimination
  double ratio = 1.2;
  int ina_portion = 100;
  
step1: 
  // a) delete empty column with weight 1 until not possible
  bool flag = false;
  while (!flag) {
    flag = true;
    for (int i = 0; i < a_c.size(); i++) {
      if (ina_c.find(i) == ina_c.end() && cols[i].size() == 1) {
        cout << "removing" << endl;
        // a1) deleting corresponding row
        flag = false;
        Node entry = *(cols[i].begin());
        for (const auto elem: rows[entry.p]) {
          cols[elem.p].erase(Node(entry.p, -1));
          if (cols[elem.p].size() == 0) {a_c.erase(a_c.find(elem.p));}
        }
        rows[entry.p].clear();
        cols[i].clear();
      }
      goto step1;
    }
  }
cout << a_c.size()<<endl;
//   cout << "after step 1, " << a_r << ", " << a_c << endl;
//   // b) delete excessive rows
// step2: 
//   if ((a_c + ina_c) * ratio < (double)a_r) {
//     vector<Node> weights;
//     for (int i = 0; i < row_size; i++) {
//       weights.push_back(Node(i, rows[i].size()));
//     }
//     std::sort(weights.begin(), weights.end());
//     int excess = a_r - (int)((a_c + ina_c) * ratio) + 10;
//     for (int i = 0; i < excess; i++) {
//       Node entry = weights[i];
//       for (const auto elem: rows[entry.p]) {
//         cols[elem.p].erase(Node(entry.p, -1));
//       }
//       a_r--;
//     }
//     cout << "after step 2, " << a_r << ", " << a_c << endl;
//     goto step1;
//   }
//   cout << a_r << ", " << a_c << endl;
//   return 0;
}