// question 1: do we need to do deep copy on node.next
// question 2: what happend 2 to 1 in linkedlist, how do we do delete
#include<iostream>
#include<cstdlib>
#include<cassert>
using namespace std;

class poly_node {
  private:
    int coefficient;
    int exponential;
    poly_node * next;

  public:
    poly_node() {
      coefficient = 0;
      exponential = -1;
      next = NULL;
    }

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

    ~poly_node() {
      delete next;
    }

    int get_exp() const {
      return exponential;
    }

    int get_coeff() const {
      return coefficient;
    }
    
    void set_coeff(int coeff) {
      coefficient = coeff;
    }

    poly_node * get_next() const {
      return next;
    }

    void set_next(poly_node * n) {
      next = n;
    }
};

namespace poly_node_op {
  poly_node operator+(const poly_node & this_node, const poly_node & that_node) {
      if(this_node.get_exp() != that_node.get_exp()) {
        return poly_node();
      }

      int n_exp = that_node.get_exp();
      int n_coeff = that_node.get_coeff() + this_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }

    poly_node operator-(const poly_node & this_node, const poly_node & that_node) {
      if(this_node.get_exp() != that_node.get_exp()) {
        return poly_node();
      }

      int n_exp = that_node.get_exp();
      int n_coeff = that_node.get_coeff() - this_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }

    poly_node operator*(const poly_node & this_node, const poly_node & that_node) {
      int n_exp = that_node.get_exp() + this_node.get_exp();
      int n_coeff = that_node.get_coeff() * this_node.get_coeff();
      return poly_node(n_coeff, n_exp);
    }
}

  
