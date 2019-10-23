#include <unordered_map> 
#include <climits> 
#include "safe_matrix.cpp"
#include <assert.h>

// Author: En Lin
// CunyID: 23510362

// !!!!!!!!!!!!!!!!!!! Very important notes for graders !!!!!!!!!!!!!!!!!
// 1 - the implementation of this data structure is similar to Heap
// 2 - we fill the matrix from left to right in the row.  we only consider next row if previous row is filled
// 3 - we use hash table to implement find() function in O(1) time at the cost of extra memory.
// 4 - like heap, we maintain the invariant (non-decreasing in both right and down direction)
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class VNT {
  private:
    SafeMatrix<int>* mat;
    unordered_map<int, int>* count;
    int size;
    int m;
    int n;
    
    void update_count(int i, int amount) {
      if (count -> find(i) == count -> end()) (*count)[i] = 0;
      (*count)[i] += amount;
      if ((*count)[i] == 0) {
        count -> erase(i);
      }
    }
    
    void swap(int p1, int p2) {
      int temp = (*mat)[p1 / n][p1 % n];
      (*mat)[p1 / n][p1 % n] = (*mat)[p2 / n][p2 % n];
      (*mat)[p2 / n][p2 % n] = temp;
    }
    
    // swap element to the direction of (0, 0) if any parent of it is bigger
    void bubble_up(int p) {
      int cur_p = p, new_p = p;
      do {
        swap(cur_p, new_p);
        cur_p = new_p;
        if (cur_p % n > 0 && (*mat)[(cur_p - 1) / n][(cur_p - 1) % n] > (*mat)[(new_p) / n][(new_p) % n]) {
          new_p = cur_p - 1;
        }

        if (cur_p / n > 0 && (*mat)[(cur_p - n) / n][(cur_p - n) % n] > (*mat)[(new_p) / n][(new_p) % n]) {
          new_p = cur_p - n; 
        }
      } while (new_p != cur_p);
      
      return;
    }
    
    // swap element to the direction of (m, n) if any parent of it is bigger
    void bubble_down(int p) {
      int cur_p = p, new_p = p;
      do {
        swap(cur_p, new_p);
        cur_p = new_p;
        if (cur_p % n < n - 1 && cur_p + 1 <= size && (*mat)[(cur_p + 1) / n][(cur_p + 1) % n] < (*mat)[(new_p) / n][(new_p) % n]) {
          new_p = cur_p + 1;
        }

        if (cur_p / n < m - 1 && cur_p + n <= size && (*mat)[(cur_p + n) / n][(cur_p + n) % n] < (*mat)[(new_p) / n][(new_p) % n]) {
          new_p = cur_p + n; 
        }
      } while (new_p != cur_p);
      
      return;
    }


  public:
    // default constructor
    VNT() {}

    // constructor
    VNT(int m, int n) {
      this->m = m;
      this->n = n;
      mat = new SafeMatrix<int>(m - 1, n - 1);
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          (*mat)[i][j] = INT_MAX; 
        }
      }
      size = 0;
      count = new unordered_map<int, int>;
    }
    
    // destructor
    ~VNT() {
      delete mat;
      delete count;
    }

    // put new element at the end, and bubble it up until the invariant of datastructure holds  
    /* postcondition:
        return: i if success, INT_MAX if failure 
     */
    int add(int i) {
      if (size == m * n) return INT_MAX;
      size++;
      (*mat)[(size - 1) / n][(size - 1) % n] = i;

      bubble_up(size - 1);
      update_count(i, 1);
      return i;
    }

    // swap first element and last, and bubble the last element(at position (0, 0)) down
    /* postcondition:
       return: min if success, INT_MAX if failure 
     */
    int getMin() {
      if (size == 0)return INT_MAX;
      size--;

      int rt = (*mat)[0][0];
      (*mat)[0][0] = (*mat)[size / n][size % n];
      (*mat)[size / n][size % n] = INT_MAX;
      bubble_down(0);
      update_count(rt, -1);

      return rt;
    }
    
    /* precondition: unsorted k
       postcondition: in-place sorted array k
     */
    void sort(int k[], int size) {
      int n = ceil(sqrt(size));
      VNT temp(n, n);
      for (int i = 0; i < size; i++) {
        temp.add(k[i]);
      }

      for (int i = 0; i < size; i++) {
        k[i] = temp.getMin();
      }
    }
    
    /* postcondition: 
         return true if i is in matrix, false otherwise
     */
    bool find(int i) {
      return count->find(i) != count->end();
    }

    // overloads << so we can directly print VNT
    friend ostream& operator<<(ostream& os, const VNT & s) {
      os << (*s.mat);
      return os;
    }
};

void print(int k[], int size) {
  for (int i = 0; i < size; i++) {
    cout << k[i] << ",";
  }
}

int main() {
  // test from the assignment
  cout << "step1: initialize VNT A(5,7)....." << endl;
  VNT A(5,7);
  cout << "step2: A.add(25)......" << endl;
  A.add(25);
  cout << "A = " << endl;
  cout << A << endl;
  cout << "step3: A.getMin()" << endl;
  assert (A.getMin() == 25);
  cout << "A = " << endl << A << endl;;
  
  // test from myself
  cout << "------------------------- My test ------------------" << endl;
  VNT v(3,3);
  cout << "step1: adding 2,4,2,5,1,3,7,9,11,9(will not be added because full) ......" << endl;
  assert (v.add(2) == 2);
  assert (v.add(4) == 4);
  assert (v.add(2) == 2);
  assert (v.add(5) == 5);
  assert (v.add(1) == 1);
  assert (v.add(3) == 3);
  assert (v.add(7) == 7);
  assert (v.add(9) == 9);
  assert (v.add(11) == 11);
  assert (v.add(9) == INT_MAX);
  assert (v.find(2));
  cout << "v = " << endl << v << endl;

  cout << "step2: getMin() 2 times....." << endl;
  assert (v.getMin() == 1);
  assert (v.getMin() == 2);
  assert (!v.find(1));
  cout << "v = " << endl << v << endl;
  
  cout << "step3: adding 20 33....." << endl;
  assert (v.add(20) == 20);
  assert (v.add(33) == 33);
  cout << "v = " << endl << v << endl;
  
  cout << "step4: testing find(i)....." << endl;
  assert (v.find(2));
  assert (v.find(3));
  assert (v.find(4));
  assert (v.find(5));
  assert (v.find(7));
  assert (v.find(9));
  assert (v.find(11));
  assert (v.find(20));
  assert (v.find(33));

  cout << "step5: getMin() 10 times (last time will remove nothing)....." << endl;
  assert (v.getMin() == 2);
  assert (v.getMin() == 3);
  assert (v.getMin() == 4);
  assert (v.getMin() == 5);
  assert (v.getMin() == 7);
  assert (v.getMin() == 9);
  assert (v.getMin() == 11);
  assert (v.getMin() == 20);
  assert (v.getMin() == 33);
  assert (v.getMin() == INT_MAX);
  cout << "v = " << endl << v << endl;
  
  assert (!v.find(2));
  assert (!v.find(33));

  cout << "step6: testing sort function....." << endl;
  int k[]{2,3,4,1,2,5,1,2,3,5,1,2,4,4,2,4,4,3,5,1,2,62,61};
  cout << "before sort k = " << endl;
  print(k, 23);
  cout << endl;
  v.sort(k, 23);
  cout << "after sort k = " << endl;
  print(k, 23);
  cout << endl;
  return 0;
}
