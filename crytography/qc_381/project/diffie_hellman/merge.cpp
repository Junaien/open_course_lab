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

int main() {
  set<ZZ> primes; // used prime
  unordered_map<ZZ, int, ZZ_hash> prime_index;
  long row = 0;
  ifstream File;
  ofstream oFile;

  // read input from user
	string root;
  int p = 0;
	int n = 0;
	cout << "root: ";
	cin >> root;
	cout << "num: ";
	cin >> n;  
  cout << "primitive root position: ";
  cin >> p;
  // read used primes
  {
    for (int i = 1; i <= n; i++) {
      File.open(root + "m" + to_string(i) + ".txt");
      while (!File.eof()) {
        ZZ residue, prime;
        long coeff;
        File >> residue;
        if (File.eof()) {break;};
        File >> prime;
        File >> coeff;
        while (!(prime == 0 && coeff == 0)) {
          primes.insert(prime);
          File >> prime >> coeff;
        }
        row++;
      }
      File.close();
    }
  }

	// factor base
	// for (int i = 1; i <= n; i++) {
	// 	File.open(root + "f" + to_string(i) + ".txt");
	// 	int r, c;
	// 	File >> r >> c; 
 //    row += r;
	// 	for (int j = 0; j < c; j++) {
 //      ZZ p, index;
 //      File >> p >> index;
 //      primes.insert(p);
	// 	}
	// 	File.close();
	// }

	// oFile.open(root + "master.txt");
	// oFile << row << " " << primes.size() << "\n";

  // setup columns
  for (auto e : primes) {
  	prime_index[e] = prime_index.size();
  	// oFile << e << "\n";
  }

  // set up rows, 1 based indexing
  oFile.open(root + "master.mtx");
  long num_relation = 1;
  long num_entry = 0;
  oFile << row + 1 << " " << primes.size() << "\n";
	for (int i = 1; i <= n; i++) {
		File.open(root + "m" + to_string(i) + ".txt");
    while (!File.eof()) {
      ZZ residue, prime;
      long coeff;
      File >> residue;
      if (File.eof()) {break;};
      File >> prime;
      File >> coeff;
      while (!(prime == 0 && coeff == 0)) {
        num_entry++;
      	oFile << num_relation << " " << prime_index[prime] + 1 << " " << coeff << "\n";
        assert(num_relation >= 1 && num_relation <= row + 1);
        assert(prime_index[prime] + 1 >= 1 && prime_index[prime] + 1 <= primes.size());
        File >> prime >> coeff;
      }
      num_relation++;
    }
		File.close();
	}
  assert (num_relation == row + 1);
  oFile << num_relation << " " << p << " " << 1 << endl;
  num_entry++;
	oFile.close();

	cout << "ends up " << num_relation << " rows, " << primes.size() << " columns" <<  ", entries=" << num_entry <<endl;
}