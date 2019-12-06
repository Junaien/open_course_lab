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
using namespace std;
using namespace NTL;

ZZ check_regular(ZZ residue, ZZ p1, ZZ p2) {
  for (ZZ i = ZZ(0); i < p2; i++) {
  	assert(residue % p1 == 0);
  	residue = residue / p1;
  }
  return residue;
}

int main() {
	ZZ p;
	string file;
	cout << "check: ";
	cin >> file;
	cout << "p: ";
	cin >> p;
	ifstream File;
	Vec<ZZ> stream;
	File.open(file);
	bool flag = true;
	ZZ old_residue;
	ZZ residue;

  while (!File.eof()) {
  	if (flag) {
  		File >> residue; 
  		assert(residue > 1 || File.eof());
  		old_residue = residue + p;
  		flag = false;
  	} else {
      ZZ p1, p2;
      File >> p1 >> p2;
      if (p1 == 0 && p2 == 0){ 
      	assert(residue == 1);
      	assert(old_residue == 1);
      	flag = true; 
      }else if (p2 == -1) {
      	assert(old_residue % p1 == 0);
      	old_residue = old_residue / p1;
      }else {
      	residue = check_regular(residue, p1, p2);
      }
  	}
  }
	File.close();
	cout << "checking process passed!" << endl;
}