#include<iostream>
#include<cstdlib>
#include<cassert>
using namespace std;

// Author En Lin
class poly_node {
  private:
    int coefficient;
    int exponential;
    poly_node * next;

  public:
    // default constructor
    poly_node() {
      coefficient = 0;
      exponential = 0;
      next = NULL;
    }

    // constructor: initializes coefficient and exponential
    poly_node(int coeff, int exp) {
      coefficient = coeff;
      exponential = exp;
      next = NULL;
    }

    // copy constructor: doesn't construct the node it links to
    poly_node(const poly_node & p_node) {
      coefficient = p_node.get_coeff();
      exponential = p_node.get_exp();
      next = NULL;
    }

    // destruct the node it links to, if the linked node is not NULL
    ~poly_node() {
      delete next;
    }

    int get_exp() const {
      return exponential;
    }

    int get_coeff() const {
      return coefficient;
    }

    poly_node * get_next() const {
      return next;
    }

    void set_coeff(int coeff) {
      coefficient = coeff;
    }

    void set_next(poly_node * n) {
      next = n;
    }

    // overloads << so we can directly output poly_node
    friend ostream& operator<<(ostream& os, const poly_node & p_node) {
      // we need to print sign
      if (p_node.coefficient > 0) {
        os << "+";
      }

      // print coefficient only when appropriate
      if (p_node.exponential == 0 || p_node.coefficient != 1) {
        os << p_node.coefficient;
      }

      // print exponential only when appropriate
      if (p_node.exponential == 1) {
        os << "X";
      } else if(p_node.exponential != 0) {
        os << "X^" << p_node.exponential;
      }
      return os;
    }
};

// overloaded operations for poly_node
namespace poly_node_op {
  poly_node operator+(const poly_node & this_node, const poly_node & that_node) {
      int n_exp = that_node.get_exp();
      int n_coeff = this_node.get_coeff() + that_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }

    poly_node operator-(const poly_node & this_node, const poly_node & that_node) {
      int n_exp = that_node.get_exp();
      int n_coeff = this_node.get_coeff() - that_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }

    poly_node operator*(const poly_node & this_node, const poly_node & that_node) {
      int n_exp = that_node.get_exp() + this_node.get_exp();
      int n_coeff = that_node.get_coeff() * this_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }
}