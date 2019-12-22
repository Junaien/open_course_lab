#include<iostream>
#include <cstdlib>
#include<cassert>
using namespace std;

// Author: En Lin
// CunyID: 23510362
template <class T>
class SA {
  private:
    int low, high;
    T* p;

  public:
    // default constructor
    // allows for writing things like SA a;
    SA() {
      low = 0; 
      high = -1;
      p = NULL;
    }

    // 2 parameter constructor lets us write
    // SA x(10,20);
    SA(int l, int h) {
      if((h-l+1)<=0) {
        cout<< "constructor error in bounds definition"<<endl;
        exit(1);
      }
      low = l;
      high = h;
      p = new T[h-l+1];
    }
    
    SA(T arr[], int l, int h): SA(l,h) {
      for (int i = l; i <= h; i++) {
        p[i-l] = arr[i-l];
      }
    }
    // single parameter constructor lets us
    // create a SA almost like a "standard" one by writing
    // SA x(10); and getting an array x indexed from 0 to 9
    SA(int i) {
      low = 0; 
      high = i-1;
      p = new T[i];
    }

    // copy constructor for pass by value and
    // initialization
    SA(const SA & s) {
      int size = s.high - s.low + 1;
      p = new T[size];
      for(int i=0; i < size; i++) { p[i]=s.p[i]; }
      low = s.low;
      high = s.high;
    }

    // destructor
    ~SA() {
      delete [] p;
    }

    // getters
    int get_low() {return low;}
    int get_high() {return high;}

    // overloaded [] lets us write
    // SA x(10,20); x[15]= 100;
    T& operator[](int i) const{
      if(i < low || i > high) {
        cout<< "index " << i << " out of range" <<endl;
        exit(1);
      }
      return p[i-low];
    }

    // overloaded assignment lets us assign
    // one SA to another
    SA& operator=(const SA<T>& s) {
      if(this==&s) {return *this; }
      delete [] p;
      int size = s.high - s.low + 1;
      p = new T[size];
      for(int i=0; i<size; i++) { p[i] = s.p[i]; }
      low = s.low;
      high = s.high;
      return *this;
    }

    // overloads << so we can directly print SAs
    friend ostream& operator<<(ostream& os, const SA<T>& s){
      int size = s.high - s.low + 1;
      for(int i=0; i<size; i++) {os << s.p[i] << ",  "; }
      os << endl;
      return os;
    }

    class iterator: public std::iterator<std::random_access_iterator_tag, T>
    {
    public:
        iterator(): p_(NULL) {}
        iterator(T* p): p_(p) {}
        iterator(const iterator& other): p_(other.p_) {}
        const iterator& operator=(const iterator& other) {p_ = other.p_; return other;}

        iterator& operator++()    {p_++; return *this;} // prefix++
        iterator  operator++(int) {iterator tmp(*this); ++(*this); return tmp;} // postfix++
        iterator& operator--()    {p_--; return *this;} // prefix--
        iterator  operator--(int) {iterator tmp(*this); --(*this); return tmp;} // postfix--

        void     operator+=(const std::size_t& n)  {p_ += n;}
        void     operator+=(const iterator& other) {p_ += other.p_;}
        iterator operator+ (const std::size_t& n)  {iterator tmp(*this); tmp += n; return tmp;}
        iterator operator+ (const iterator& other) {iterator tmp(*this); tmp += other; return tmp;}

        void        operator-=(const std::size_t& n)  {p_ -= n;}
        void        operator-=(const iterator& other) {p_ -= other.p_;}
        iterator    operator- (const std::size_t& n)  {iterator tmp(*this); tmp -= n; return tmp;}
        std::size_t operator- (const iterator& other) {return p_ - other.p_;}

        bool operator< (const iterator& other) {return (p_-other.p_)< 0;}
        bool operator<=(const iterator& other) {return (p_-other.p_)<=0;}
        bool operator> (const iterator& other) {return (p_-other.p_)> 0;}
        bool operator>=(const iterator& other) {return (p_-other.p_)>=0;}
        bool operator==(const iterator& other) {return  p_ == other.p_; }
        bool operator!=(const iterator& other) {return  p_ != other.p_; }

        T& operator[](const int& n) {return *(p_+n);}
        T& operator*() {return *p_;}
        T* operator->(){return  p_;}

    public:
        T* p_;
    };

    iterator begin(){ return iterator(p); }
    iterator end()  { return iterator(p + (high - low + 1)); }
};

// check sorted
template <class T>
bool sorted(SA<T> & arr, int low, int high) {
  for (int i = low + 1; i <= high; i++) {
    if(arr[i] < arr[i-1]) {
      return false;
    }
  }
  return true;
}

int main() {
  // set up test data
  int arr1[] = {1,2,3,4,5,1,999,1,2,4,21,4,1};
  int arr_postfix[] = {1,2,4,21,4,1};
  SA<int> test1(arr1, 3, 15);

  // test std::find
  SA<int>::iterator itr = std::find(test1.begin(), test1.end(), 999);
  for (int i : arr_postfix) {
    assert(i == *(++itr));
  }
  assert (++itr == test1.end());

  // test std::sort
  std::sort(test1.begin(), test1.end());
  assert (sorted(test1, 3, 15));
}


