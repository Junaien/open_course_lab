// question 1: is return a copy of addition result inefficient?
//X question 2: is A * a = &A(a1) same as A * a = new A(a1) 
// question 3: what happend when we reassign variable, what happens to original object value in memory
// question 4: what is non-throw swap
// question 5: how to follow RAII 
// question 6: how to pass a pointer to member function
#include<iostream>
#include<fstream>
#include<cstdlib>
#include<cassert>
#include "poly_node.cpp"
using namespace std;
// typedef poly_node(poly_node::*node_operation)(const poly_node & that_n);
/* invariant: polynomial instance always retains it's poly_nodes in descending order, 
   with respect to exponential
 */
typedef poly_node(* node_operation)(const poly_node & this_n, const poly_node & that_n);

class polynomial {
  private:
    poly_node * dummy_head;

  public:
    // default constructor: set up dummy head
    polynomial() {
      dummy_head = new poly_node();
    }

    // copy constructor
    polynomial(const polynomial & p) {
      dummy_head = new poly_node();
      poly_node * this_node = dummy_head;
      poly_node * p_node = p.dummy_head->get_next();

      while(p_node != NULL) {
        this_node->set_next(new poly_node(*p_node));
        this_node = this_node->get_next();
        p_node = p_node->get_next();
      }
    }

    ~polynomial() {
      delete dummy_head;
    }
    
    void insert(const poly_node & n) {
      if(n.get_coeff() == 0 || n.get_exp() == -1) {return;}
      poly_node * cur_node = dummy_head;
      while(cur_node->get_next() != NULL && (cur_node->get_next())->get_exp() > n.get_exp()) {
        cur_node = cur_node->get_next();
      }
      poly_node * no_greater_node = cur_node->get_next();
      if(no_greater_node != NULL && (no_greater_node->get_exp() == n.get_exp())) {
        int n_coeff = no_greater_node->get_coeff() + n.get_coeff();
        if(n_coeff != 0) {
          no_greater_node->set_coeff(n_coeff);
        } else {
          cur_node->set_next(no_greater_node->get_next());
        }
      } else {
        poly_node * new_node = new poly_node(n);
        new_node->set_next(no_greater_node);
        cur_node->set_next(new_node);
      }
    }

    // using two pointer to do element wise operation on two polynomial in linear time
    // when to append this node?: that is null or this is bigger in exponential
    // when to append that node?: this is null or that is bigger in exponential
    // when to append both node?: when both nodes are not null and equal in exponential
    polynomial element_operation(const polynomial & p, node_operation op) {
      polynomial ret = polynomial();
      poly_node * this_node = dummy_head->get_next();
      poly_node * that_node = p.dummy_head->get_next();
      poly_node * new_node = ret.dummy_head;

      while(this_node != NULL || that_node != NULL) {

          if(this_node != NULL && that_node != NULL && this_node->get_exp() == that_node->get_exp()) {
            poly_node op_result = op(*this_node, *that_node);

            if (op_result.get_coeff() != 0 && op_result.get_exp() != -1) {
              new_node->set_next(new poly_node(op_result));
              new_node = new_node->get_next();
            }

            this_node = this_node->get_next();
            that_node = that_node->get_next();
          } else if(that_node == NULL || (this_node != NULL && this_node->get_exp() > that_node->get_exp())) {

            new_node->set_next(new poly_node(op(*this_node, poly_node(0,this_node->get_exp()))));
            new_node = new_node->get_next();
            this_node = this_node->get_next();
          } else {
            new_node->set_next(new poly_node(op(poly_node(0,that_node->get_exp()), *that_node)));
            new_node = new_node->get_next();
            that_node = that_node->get_next();
          }
      }
      return ret;
    }

    void swap(polynomial & p) throw() {
      poly_node * temp = dummy_head;
      dummy_head = p.dummy_head;
      p.dummy_head = temp;
    }

    polynomial & operator=(const polynomial & p) {
      if (&p != this) {
        polynomial(p).swap(*this);
      }

      return *this;
    }

    polynomial operator+(const polynomial & p) {
      return element_operation(p, poly_node_op::operator+);
    }

    polynomial operator-(const polynomial & p) {
      return element_operation(p, poly_node_op::operator-);
    }

    polynomial operator*(const poly_node & n) {
      polynomial ret = polynomial();
      poly_node * new_node = ret.dummy_head;
      poly_node * this_node = dummy_head->get_next();

      while(this_node != NULL) {
        int n_coeff = this_node->get_coeff() * n.get_coeff();
        int n_exp = this_node->get_exp() + n.get_exp();
        if(n_coeff != 0) {
          new_node->set_next(new poly_node(n_coeff, n_exp));
          new_node = new_node->get_next();
        }
        this_node = this_node->get_next();
      }
      return ret;
    }

    polynomial operator*(const polynomial & p) {
      poly_node * p_node = p.dummy_head->get_next();
      polynomial ret = polynomial();

      while(p_node != NULL) {
        polynomial temp = (*this) * (*p_node);
        ret = (ret + temp);
        p_node = p_node->get_next();
      }
      return ret;
    }

    // overloads << so we can directly print polynomial
    friend ostream& operator<<(ostream& os, const polynomial & p) {
      poly_node * cur_node = p.dummy_head->get_next();
      while(cur_node != NULL) {
        int coeff = cur_node->get_coeff();
        int exp = cur_node->get_exp();
        if (coeff > 0) {
          os << "+";
        }

        if (exp == 0 || coeff != 1) {
          os << coeff;
        }

        if (exp == 1) {
          os << "X";
        } else if(exp != 0) {
          os << "X^" << exp;
        }

        cur_node = cur_node->get_next();
      }
      return os;
    }

    // overloads >> so we can directly read polynomial from txt
    friend istream& operator>>(istream& is, polynomial & p) {
      int coeff = 0;
      int exp = -1;
      is >> coeff;
      is >> exp;
      p.insert(poly_node(coeff, exp));
      return is;
    }
};

int main() {
  polynomial p1, p2;
  ifstream inFile;
  inFile.open("./input.txt");
  while (inFile) {
    do {inFile >> p1;} while(inFile.peek() != '\n' && inFile);
    do {inFile >> p2;} while(inFile.peek() != '\n' && inFile);
  }
  cout << p1 << endl;
  cout << p2 << endl;

  // p1.insert(poly_node(-1,0));
  // p1.insert(poly_node(5,1));
  // p1.insert(poly_node(20,3));
  // p1.insert(poly_node(-9,2));
  // p1.insert(poly_node(-2,1));
  // p1.insert(poly_node(1,2));
  // p1.insert(poly_node(-2,3));
  // p1.insert(poly_node(1,9));

  // p2.insert(poly_node(9,2));
  // p2.insert(poly_node(2,3));
  // p2.insert(poly_node(10,10));
  // cout << p1 <<  endl << "+" << endl << p2 << endl << "=" << endl;
  // cout << p1 + p2 << endl << endl;

  // cout << p1 <<  endl << "-" << endl << p2 << endl << "=" << endl;
  // cout << p1 - p2 << endl << endl;

  // cout << p1 <<  endl << "*" << endl << p2 << endl << "=" << endl;
  // cout << p1 * p2 << endl;
  
  return 0;
}