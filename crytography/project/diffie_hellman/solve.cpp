#include <linbox/solutions/rank.h>
#include <NTL/ZZ_p.h>
#include <NTL/ZZ.h>
#include <linbox/algorithms/wiedemann.h>
#include <iostream>
#include <vector>
#include <fstream>
#include <unordered_map>
#include <linbox/linbox-config.h>
#include <givaro/modular.h>
#include <assert.h>
#include <linbox/util/matrix-stream.h>
#include <linbox/solutions/methods.h>
#include <linbox/matrix/sparse-matrix.h>
#include <linbox/solutions/solve.h>
using namespace std;

using namespace NTL;
using namespace LinBox;
// typedef UnparametricField<LinBox::Integer> Field;
typedef Givaro::Modular<LinBox::integer> Field;
// typedef UnparametricField <NTL::ZZ_p> Field;
// typedef NTL::ZZ_p Field;
// typedef UnparametricField<NTL::ZZ_p> Field;
typedef SparseMatrix<Field> Spat;

int main() {

  vector<Integer> primes;
  ifstream File;
  File.open("./c=100000/master.txt");
  int row, col;
  File >> row >> col;
  for (int i = 0; i < col; i++) {
    Integer temp;
    File >> temp;
    primes.push_back(temp);
  }
  cout << "reading in r=" << row << ", c="  << col << endl;
  LinBox::integer  q;
  cout << "type q: ";
  cin >> q;
  Field f(q);
  Spat A(f, row + 1, col);

  cout << "A.row=" << A.rowdim() << ", A.col=" << A.coldim() << endl;
  while(true) { 
    size_t r, c;
    Integer v;
    File >> r >> c >> v;
    if(r == 0 && c == 0 && v == 0) {
      break;
    }
    A.setEntry(r-1, c-1, v);
  }
  cout << "input matrix success!" << endl;
  A.setEntry(A.rowdim() - 1, 0, 1);
  cout << A << endl;

  Transpose<Spat> AT(&A);
  typedef Compose<Spat, Transpose<Spat>> art;
  art AAT(&A, &AT);
  
  DenseVector<Field>W(f, A.rowdim()), B(f, A.rowdim());
  B.setEntry(B.size() - 1, 1);
  cout << "start solving" << endl;
  cout << AAT.rowdim() << ", " << AAT.coldim() << endl;
  solve (W, AAT, B, Method::Blackbox());
  cout << "solved!" << endl;
  A.applyTranspose(X,W);
  for (unsigned int i = 0; i < X.size(); i++) {
    cout << primes[i] << ", " << X.getEntry(i) << endl;
  }
  File.close();
  return 1;
}
