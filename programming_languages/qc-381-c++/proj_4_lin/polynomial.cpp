#include<iostream>
#include<fstream>
#include<cstdlib>
#include<cassert>
#include<map>
#include<vector>
using namespace std;

// Author En Lin
void print_poly_node(int coeff, int exp, ostream& os) {
  // we need to print sign
  if (coeff > 0) {
    os << "+";
  }

  // print coefficient only when appropriate
  if (exp == 0 || coeff != 1) {
    os << coeff;
  }

  // print exponential only when appropriate
  if (exp == 1) {
    os << "X";
  } else if(exp) {
    os << "X^" << exp;
  }
}

void print_poly(map<int, int> p, ostream& os) {
  std::map<int, int>::reverse_iterator it = p.rbegin();
  while(it != p.rend()) {
    print_poly_node(it->second, it->first, os);
    it++;
  }
  os << endl;
}

void print_poly(vector<vector<int>> p, ostream& os) {
  for(vector<int> pair : p) {
    print_poly_node(pair[1], pair[0], os);
  }
  os << endl;
}

void add(map<int, int> & p, int coeff, int exp) {
  if(coeff == 0 || exp < 0) {
    cout << "malformat" << endl;
    return;
  };

  map<int, int>::iterator itr;
  itr = p.find(exp);
  if(itr == p.end()) { p.insert({exp, coeff}); }
  else if(itr->second + coeff == 0) { p.erase(itr->first); }
  else { itr->second += coeff; }
}

vector<int> mult(int c1, int e1, int c2, int e2) {
  int c = c1 * c2;
  int e = e1 + e2;
  return vector<int>({c, e});
}

map<int, int> add(map<int, int> & p1, map<int, int> & p2) {
  map<int, int> rt;
  for (const auto& kv : p1) { add(rt, kv.second, kv.first); }
  for (const auto& kv : p2) { add(rt, kv.second, kv.first); }
  return rt;
}

map<int, int> sub(map<int, int> & p1, map<int, int> & p2) {
  map<int, int> rt;
  for (const auto& kv : p1) { add(rt, kv.second, kv.first); }
  for (const auto& kv : p2) { add(rt, -kv.second, kv.first); }
  return rt;
}

map<int, int> mult(map<int, int> & p1, map<int, int> & p2) {
  map<int, int> rt;
  for (const auto& kv1 : p1) { 
    for (const auto& kv2 : p2) { 
      vector<int> m = mult(kv1.second, kv1.first, kv2.second, kv2.first);
      add(rt, m[0], m[1]); 
    }
  }
  return rt;
}


int main() {
  map<int, int> ps_canonical[8];
  vector<vector<int>> ps[8];
  ifstream inFile;
  ofstream outfile;
  inFile.open("./input.txt");
  outfile.open ("./output.txt");
 
  // read in pair
  int coeff = 0, exp = 0;
  for (int i = 0; i < 8; i += 2) {
    do {
      inFile >> coeff >> exp;
      add(ps_canonical[i], coeff, exp);
      ps[i].push_back(vector<int>({exp, coeff}));
    } while (inFile.peek() != '\n' && inFile);
    do {
      inFile >> coeff >> exp;
      add(ps_canonical[i+1], coeff, exp);
      ps[i+1].push_back(vector<int>({exp, coeff}));
    } while (inFile.peek() != '\n' && inFile);
  }

  // print out both regular form and canonical form
  outfile << "------------ Printing the format of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i++) {
    outfile << "p-" << i << "-normal: ";
    print_poly(ps[i], outfile);
    outfile << "p-" << i << "-canonical: ";
    print_poly(ps_canonical[i], outfile);
    outfile << endl;
  }
  
  // print out summation
  outfile << "------------ Printing the summation of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " + " << "p-" << i + 1 << " = ";
    print_poly(add(ps_canonical[i], ps_canonical[i+1]), outfile);
    outfile << endl;
  }

  // print out subtraction
  outfile << "------------ Printing the subtraction of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " - " << "p-" << i + 1 << " = ";
    print_poly(sub(ps_canonical[i], ps_canonical[i+1]), outfile);
    outfile << endl;
  }

  // print out multiplication
  outfile << "------------ Printing the multiplication of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " * " << "p-" << i + 1 << " = ";
    print_poly(mult(ps_canonical[i], ps_canonical[i+1]), outfile);
    outfile << endl;
  }
  return 0;
}