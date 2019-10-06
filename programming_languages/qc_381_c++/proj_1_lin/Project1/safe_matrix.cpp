#include <iostream>
#include <cstdlib>
#include <cassert>
#include "safe_array.cpp"
using namespace std;

// Author: En Lin
// CunyID: 23510362
template <class T>
class SafeMatrix {
	private:
	  int r_low, r_high;
	  SA<T>* row_p;

  public:
    // default constructor
    SafeMatrix() {
    	r_low = 0;
    	r_high = -1;
    	row_p = NULL;
    }

    // constructor with parameters
    SafeMatrix(int r_l, int r_h, int c_l, int c_h) {
      if(r_h - r_l + 1 <= 0) {
        cout << "constructor error in row bounds definition" << endl;
        exit(1);
      }

      if(c_h - c_l + 1 <= 0) {
        cout << "constructor error in column bounds definition" << endl;
        exit(1);
      }

      r_low = r_l;
      r_high = r_h;
      row_p = new SA<T>[r_high - r_low + 1];
      for(int i = r_low; i <= r_high; i++) {
        row_p[i - r_low] = SA<T>(c_l, c_h);
      }
    }

    // constructor with two parameters
    // will construct a standard matrix A[0...x][0...y]
    SafeMatrix(int x, int y) : SafeMatrix(0, x, 0, y) {}

    // copy constructor
    SafeMatrix(const SafeMatrix<T>& s) {
      r_low = s.r_low;
      r_high = s.r_high;
      row_p = new SA<T>[r_high - r_low + 1];
      for(int i = r_low; i <= r_high; i++) {
        row_p[i - r_low] = SA<T>(s[i]);
      }
    }

    // destructor
    ~SafeMatrix() {
    	delete[] row_p;
    }

    // overloaded [] lets us do a[] = b
	  SA<T>& operator[](int i) const{
	    if(i < r_low || i > r_high) {
	      cout<< "row index " << i << " out of range" <<endl;
	      exit(1);
	    }
	    return row_p[i - r_low];
	  }

	  // overload = operation on safe matrix
	  SafeMatrix<T>& operator=(const SafeMatrix<T>& s) {
	  	if(this == &s){ return *this; }
	  	r_low = s.r_low;
	  	r_high = s.r_high;
      delete [] row_p;
      row_p = new SA<T>[r_high - r_low + 1];
      for(int i = r_low; i <=  r_high; i++) {
      	row_p[i - r_low] = SA<T>(s[i]);
      }
      return *this;
	  }

    // overload * so we can multiply two matrixes
    // the result of multiplication of A * B, where A(r1, r2, c1, c2) * B(r3, r4, c3, c4) = C(r1, r2, c3, c4)
    SafeMatrix<T> operator*(const SafeMatrix<T>& rhs) {
      int r1 = r_low, r2 = r_high, c1 = row_p[0].get_low(), c2 = row_p[0].get_high();
      int r3 = rhs.r_low, r4 = rhs.r_high, c3 = rhs.row_p[0].get_low(), c4 = rhs.row_p[0].get_high();
      if(c2 - c1 != r4 - r3) {
        cout << "illegal multiplication on two such matrixes" << endl;
        exit(1);
      }

      SafeMatrix<T> rt(c1, c2, r3, r4);
      for(int i = r1; i <= r2; i++) {
        for(int j = c3; j <= c4; j++) {
          T sum = 0;
          for(int z = 0; z <= c2 - c1; z++) {
            sum += ((*this)[i][c1 + z] * rhs[r3 + z][j]);
          }
          rt[i][j] = sum;
        }
      }
      return rt;
    }

	  // overloads << so we can directly print SaceMatrix
    friend ostream& operator<<(ostream& os, const SafeMatrix<T> & s) {
      for(int i = s.r_low; i <= s.r_high; i++) {
        os << s.row_p[i - s.r_low] << endl;
      }
      return os;
    }
};

int main() {
	SafeMatrix <double> s1(2, 4, 2, 4);
	SafeMatrix <double> s2(2, 4, 2, 4);
  for(int i = 2; i <= 4; i++) {
    for(int j = 2; j <= 4; j++) {
      s1[i][j] = (i + j) * 2.41;
      s2[i][j] = j * 1.82;
    }
  }
  cout << "Matrix 1: " << endl << s1 << endl;
  cout << "Matrix 2: " << endl << s2 << endl;
  cout << "Result Matrix 1x2: " << endl << (s1 * s2);

  SafeMatrix <double> s3(9, 11, 9, 11);
  for(int i = 9; i <= 11; i++) {
    for(int j = 9; j <= 11; j++) {
      s3[i][j] = 2.0;
    }
  }

  cout << "Matrix 3: " << endl << s3 << endl;
  cout << "Result Matrix 1x3: " << endl << (s1 * s3);

  s3[9][100];
  return 0;
}
