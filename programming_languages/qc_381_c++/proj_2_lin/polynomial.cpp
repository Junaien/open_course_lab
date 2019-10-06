#include<iostream>
#include<fstream>
#include<cstdlib>
#include<cassert>
#include "poly_node.cpp"
using namespace std;

// Author En Lin
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

      while (p_node != NULL) {
        this_node->set_next(new poly_node(*p_node)); // call copy constructor of poly_node
        this_node = this_node->get_next();
        p_node = p_node->get_next();
      }
    }

    ~polynomial() {
      delete dummy_head;
    }

    void insert(const poly_node & n) {
      if (n.get_coeff() == 0 || n.get_exp() < 0) {
        return;
      }
      poly_node * cur_node = dummy_head;

      // find the largest node that is no greater than inserted node
      while (cur_node->get_next() != NULL && cur_node->get_next()->get_exp() > n.get_exp()) {
        cur_node = cur_node->get_next();
      }
      poly_node * no_greater_node = cur_node->get_next();

      // add coefficient if there exists some node that has same exponential as inserted node
      if (no_greater_node != NULL && (no_greater_node->get_exp() == n.get_exp())) {
        int n_coeff = no_greater_node->get_coeff() + n.get_coeff();
        if (n_coeff != 0) {
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
    
    // without ordering, adding. simply append
    void append(const poly_node & n) {
      poly_node * cur_node = dummy_head;

      // find the largest node that is no greater than inserted node
      while (cur_node->get_next() != NULL) {
        cur_node = cur_node->get_next();
      }
      cur_node->set_next(new poly_node(n));
    }

    // < do element-wise operation on two polynomials in linear time >
    // when to append this_node?:   when that_node is null or this_node is bigger in exponential
    // when to append that_node?:   when this_node is null or that_node is bigger in exponential
    // when to append merged node?: when both nodes are equal in exponential
    // when to stop?:               when both this_node and that_node point to NULL
    polynomial element_operation(const polynomial & p, node_operation op) {
      polynomial ret = polynomial();
      poly_node * this_node = dummy_head->get_next();
      poly_node * that_node = p.dummy_head->get_next();
      poly_node * new_node = ret.dummy_head;

      while (this_node != NULL || that_node != NULL) {

          if (this_node != NULL && that_node != NULL && this_node->get_exp() == that_node->get_exp()) {
            poly_node op_result = op(*this_node, *that_node);

            if (op_result.get_coeff() != 0) {
              new_node->set_next(new poly_node(op_result));
              new_node = new_node->get_next();
            }

            this_node = this_node->get_next();
            that_node = that_node->get_next();
          } else if (that_node == NULL || (this_node != NULL && this_node->get_exp() > that_node->get_exp())) {

            // this is the same as do operation with another node witih coefficient 0 and same exponential
            new_node->set_next(new poly_node(op(*this_node, poly_node(0, this_node->get_exp()))));
            new_node = new_node->get_next();
            this_node = this_node->get_next();
          } else {

            // this is the same as do operation with another node witih coefficient 0 and same exponential
            new_node->set_next(new poly_node(op(poly_node(0, that_node->get_exp()), *that_node)));
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
    
    // assignment operator, following RAII
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
    
    // multiplication between one node and one polynomial
    polynomial operator*(const poly_node & n) {
      polynomial ret = polynomial();
      poly_node * this_node = dummy_head->get_next();

      while (this_node != NULL) {
        int n_coeff = this_node->get_coeff() * n.get_coeff();
        int n_exp = this_node->get_exp() + n.get_exp();
        ret.insert(poly_node(n_coeff, n_exp));
        this_node = this_node->get_next();
      }

      return ret;
    }

    // multiplication between polynomials
    // it breaks down to sum of multiplication between node and polynomial
    polynomial operator*(const polynomial & p) {
      poly_node * p_node = p.dummy_head->get_next();
      polynomial ret = polynomial();

      while (p_node != NULL) {
        polynomial temp = (*this) * (*p_node);
        ret = (ret + temp);
        p_node = p_node->get_next();
      }
      return ret;
    }

    // overloads << so we can directly output polynomial
    friend ostream& operator<<(ostream& os, const polynomial & p) {
      poly_node * cur_node = p.dummy_head->get_next();
      while (cur_node != NULL) {
        os << *cur_node;
        cur_node = cur_node->get_next();
      }
      return os;
    }

    // // overloads >> so we can directly input polynomial
    // friend istream& operator>>(istream& is, polynomial & p) {
    //   int coeff = 0;
    //   int exp = 0;
    //   is >> coeff;
    //   is >> exp;
    //   p.insert(poly_node(coeff, exp));
    //   return is;
    // }
};

int main() {
  polynomial ps_canonical[8];
  polynomial ps[8];
  ifstream inFile;
  ofstream outfile;
  inFile.open("./input.txt");
  outfile.open ("./output.txt");
 
  // read in pair
  int coeff = 0, exp = 0;
  for (int i = 0; i < 8; i += 2) {
    do {
      inFile >> coeff >> exp;
      poly_node temp(coeff, exp);
      ps_canonical[i].insert(temp);
      ps[i].append(temp);
    } while (inFile.peek() != '\n' && inFile);
    do {
      inFile >> coeff >> exp;
      poly_node temp(coeff, exp);
      ps_canonical[i + 1].insert(temp);
      ps[i + 1].append(temp);
    } while (inFile.peek() != '\n' && inFile);
  }

  // print out both regular form and canonical form
  outfile << "------------ Printing the format of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i++) {
    outfile << "p-" << i << "-normal: " << ps[i] << endl;
    outfile << "p-" << i << "-canonical: " << ps_canonical[i] << endl << endl;
  }
  
  // print out summation
  outfile << "------------ Printing the summation of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " + " << "p-" << i + 1 << " = " << ps_canonical[i] + ps_canonical[i+1] << endl << endl;
  }

  // print out subtraction
  outfile << "------------ Printing the subtraction of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " - " << "p-" << i + 1 << " = " << ps_canonical[i] - ps_canonical[i+1] << endl << endl;
  }

  // print out multiplication
  outfile << "------------ Printing the multiplication of polynomials ----------------" << endl;
  for (int i = 0; i < 8; i+= 2) {
    outfile << "p-" << i << " * " << "p-" << i + 1 << " = " << ps_canonical[i] * ps_canonical[i+1] << endl << endl;
  }
  return 0;
}