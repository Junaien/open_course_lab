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

class ZZ_hash { 
public: 

    size_t operator()(const ZZ& p) const {   
        std::hash<long> long_hash;
        return long_hash(conv<long>(p));
    } 
};

ZZ pollard_rho(const ZZ &n) {
  if (n == 1 || n == 2 || n == 3) return n;
reset:
  ZZ x = RandomBnd(n);
  ZZ y = x;
  ZZ d;

  while (1) {
    x = (x * x + 1) % n;
    y = (y * y + 1) % n;
    y = (y * y + 1) % n;
    d = GCD(abs(y - x), n);
    if (d != 1 && d != n) { return d; }
    if (d == n) { goto reset;}
  }
}

// pollard rho implementation
// this implementation will do trivial divide for first 200 primes, then it uses pollard rho
Vec<ZZ> rho_factoring(const Vec<ZZ> &factor_base, ZZ residue, unordered_map<ZZ, long, ZZ_hash> &prime_index, ofstream &oFile) {
  oFile << residue << "\n";
  Vec<ZZ> rt;
  rt.SetLength(factor_base.length());

  // trivial divide first 100 primes
  for (long i = 0; i < 100; i++) {
    while (residue % factor_base[i] == 0) {
      residue /= factor_base[i];
      rt[i] += 1;
    }
    if (rt[i] > 0) {
      oFile << factor_base[i] << " " << rt[i] << "\n";
    }

    if (residue == 1) {return rt;}
  }

  // pollard rho division until we factor all out
  queue<ZZ> factor_queue;
  factor_queue.push(residue);
  while (!factor_queue.empty()) {
    ZZ candidate = factor_queue.front();
    factor_queue.pop();

    // keep factoring if candidate is not a prime
    while (prime_index.find(candidate) == prime_index.end()) {
      ZZ f = pollard_rho(candidate);
      factor_queue.push(f);
      candidate /= f;
    }

    rt[prime_index[candidate]]++;
    oFile << candidate << " " << 1 << "\n";
  }
  return rt;
}

Vec<ZZ> factoring(Vec<ZZ> &factor_base, ZZ residue, unordered_map<ZZ, long, ZZ_hash> &prime_index, ofstream &oFile) {
  return rho_factoring(factor_base, residue, prime_index, oFile);
}

void generate_relations(ZZ &p, long B, long c_limit, double ratio, long qf_limit, long c1_start, long c1_end) {
  long num_relations = 0;
  // step 1: read B primes from primes.txt into vector & computing logs for every prime
  Vec<ZZ> factor_base;
  Vec<RR> logs;
  unordered_map<ZZ, long, ZZ_hash> prime_index;
  ifstream File;
  File.open("primes.txt");
  while(!File.eof()) {  
    ZZ temp;
    File >> temp;
    if (temp > B) break;
    prime_index[temp] = factor_base.length();
    factor_base.append(temp);
    logs.append(RR(log(temp)));
  }
  File.close();

  ofstream oFile;
  oFile.open("unreduced.txt");
  // step2: fix c1, loop through qf to sieve c2, <notice: c1 starts from 1>
  // h = sqrt(p) + 1
  // j = h^2 - p
  // for small f up to qf_limit, let c2 = d = -(j + c1 * h)(c1 * h)^-1 (mod q^f), 
  //     then <residue> of (h + c1)(h + c1) = 0 (mod q^f), based on this we sieve c2
  for (ZZ c1 = ZZ(c1_start); c1 < ZZ(c1_end); c1++) {
    // initialize sieve to be [0.0, 0.0, ...]
    Vec<RR> sieve;
    sieve.SetLength(c_limit);
    ZZ h = SqrRoot(p) + ZZ(1);
    ZZ j = power(h, 2) - p;
    ZZ m1 = h + c1;
    ZZ m2 = -(j + c1 * h);
    // for every prime in our factor base,
    for (long i = 0; i < factor_base.length(); i++) {
      ZZ q = factor_base[i];
      RR logq = logs[i]; 

      // for every small power f upto qf_limit,
      ZZ qf = q;
      ZZ c2;
      ZZ last_c2 = ZZ(c_limit);
      ZZ last_qf = qf;
      for (; qf < qf_limit; qf *= q) {
        if (m1 % q == 0) continue;
        c2 = (InvMod(m1 % qf, qf) * m2) % qf;
        while (c2 < c1) {c2 += qf;} // make sure c2 >= c1
        last_c2 = c2;
        last_qf = qf;

        while (c2 < c_limit) {
          ZZ residue = j + (c1 + c2) * h + c1 * c2;
          // assert (residue % qf == 0);
          sieve[conv<long>(c2)] += logq; // increase the real log of every c2 that is multiple of qf
          c2 += qf;
        }
      }

      // at the limit of f, 
      // we also need to clean up some (c1, c2) residues that doesn't divide enough q
      c2 = last_c2;
      qf = last_qf;
      while (c2 < c_limit) {
        ZZ residue = j + (c1 + c2) * h + c1 * c2;
        ZZ qf_copy = qf * q;

        while (residue % qf_copy == 0) {
          sieve[conv<long>(c2)] += logq; // increase the real log of every c2 that is multiple of qf
          qf_copy *= q;
        }
        c2 += qf;
      }
    }

    // check our sieve to catch smooth integer
    for (ZZ c2 = ZZ(c1); c2 < sieve.length(); c2++) {
      ZZ residue = j + (c1 + c2) * h + c1 * c2;
      Vec<ZZ> relation;
      if (abs(sieve[conv<long>(c2)] - log(residue)) < 1.0) {
        relation = factoring(factor_base, residue, prime_index, oFile);
      }

      if (relation.length() > 0) {
        if (prime_index.find(h + c1) == prime_index.end()) { 
          prime_index[h + c1] = prime_index.size();
        }
        if (prime_index.find(h + c2) == prime_index.end()) { 
          prime_index[h + c2] = prime_index.size();
        }

        relation.SetLength(prime_index.size());
        relation[prime_index[h + c1]] -= 1;
        relation[prime_index[h + c2]] -= 1;
        // if(num_relations++ % 1024 == 0) {
        cout << "generated= " << num_relations++ << ", unknowns= " << prime_index.size() << ", c1=" << c1 << endl;
        // };
        oFile << h + c1 << " " << -1 << "\n";
        oFile << h + c2 << " " << -1 << "\n";
        oFile << 0 << " " << 0 << "\n";
      }
    }
  }

  oFile.close();
  // write header info
  oFile.open("factor_base.txt");
  oFile << num_relations << " " << prime_index.size() << "\n";
  for (auto x : prime_index) {
      oFile << x.first << " " << x.second << "\n";
  }
  oFile.close();
}

int main() {
  string file;
  cout << "file: ";
  cin >> file;
  // initialize variable
  ZZ p, base;
  long c_limit, B, qf_limit, c1_start, c1_end;
  double ratio;
  ifstream File;

  // read sieving parameter from input.txt
  File.open(file);
  File >> p >> B >> c_limit >> ratio >> qf_limit;
  File.close();

  // specify c1 starting point and end point
  cout << "c1 starts at: ";
  cin >> c1_start;
  cout << "c1 ends at: ";
  cin >> c1_end;
  cout << "collecting relations for c1 in interval [" << c1_start << ", " << c1_end << ")..." << endl;
  generate_relations(p, B, c_limit, ratio, qf_limit, c1_start, c1_end);
  return 0;
}