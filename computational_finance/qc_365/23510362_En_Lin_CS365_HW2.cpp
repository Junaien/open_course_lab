// run this code to see answer question

#include <vector>
#include <math.h>
#include <iostream>
const double tol =  1.0e-6;
class Bond{
  public:
    Bond(double T, double F, double c = 0, int freq=2);
    ~Bond();

    //public methods
    int set_coupons(std::vector<double> & c);
    int FV_duration(double t0, double y,
                    double &B,
                    double &Macaulay_duration,
                    double &modified_duration)const;

    int yield(double B_target, double tol, int max_iter, double t0,
              double & y, int & num_iter)const;
    double FairValue(double t0, double y) const;
    double maturity()const {return T_maturity;}
    double Dmac(double t0, double y) const;
    double Dmod(double t0, double y) const;

  private:
    //data
    double Face;
    double T_maturity;
    int cpn_freq;
    int num_coupons;
    std::vector<double> coupons;

};

Bond::Bond(double T, double F, double c, int freq){
  if(F < 0)throw std::invalid_argument("Face value can't be negative");
  if(freq < 1) throw std::invalid_argument("coupond frequency can't be less than 1");
  Face = F;
  cpn_freq = freq;
  num_coupons = int(cpn_freq * T + tol);
  if(num_coupons < 0)
    throw std::invalid_argument("coupond frequency can't be less than 1");
  T_maturity = num_coupons / cpn_freq;

  if (num_coupons > 0) {
    coupons.resize(num_coupons, c);

  }

}

Bond::~Bond(){
  coupons.clear();
  num_coupons = 0;
}

int Bond::set_coupons(std::vector<double> & c){
    coupons.clear();
    if (c.size() < num_coupons) return 1;
    for(int i = 0; i < num_coupons; i++){
      if(c[i] <= 0.0)coupons.push_back(0.0);
      else coupons.push_back(c[i]);
    }
    return 0;
}

int Bond::FV_duration(double t0, double y, double & B,
                      double &Macaulay_duration,
                      double &modified_duration)const{

  if(num_coupons <= 0 || t0 >= T_maturity)return 1;
  double for_dmac = 0.0;
  double for_bond_price = 0.0;
  for(int i = 1; i <= num_coupons; i++){
      double ti = double(i)/double(cpn_freq);
      if(ti >= t0 + tol){
        double numerator = coupons[i-1]/cpn_freq;
        double denominator = (1 + (0.01*y)/cpn_freq);
        denominator = pow(denominator, cpn_freq *(ti-t0));
        if(i == num_coupons){
          numerator += Face;
        }
        for_bond_price += numerator / denominator;
        for_dmac += (ti-t0)*numerator/denominator;
      }
  }
  B = for_bond_price;
  Macaulay_duration = for_dmac/B;
  modified_duration = Macaulay_duration/(1+ (0.01*y)/cpn_freq);
  return 0;
}

int Bond::yield(double B_target, double tol, int max_iter, double t0,
                double & y, int & num_iter)const{
  y = 0;
  num_iter = 0;
  if(B_target <= 0.0 || num_coupons <= 0 || t0 >= T_maturity)return 1;
  double y_low = 0.0;
  double y_high = 100.0;
  double B_y_low = FairValue(t0, y_low);
  double diff_B_y_low = B_y_low - B_target;
  if (fabs(diff_B_y_low <= tol)){
    y = y_low;
    return 0;
  }
  double B_y_high = FairValue(t0, y_high);

  double diff_B_y_high = B_y_high - B_target;
  if (fabs(diff_B_y_high) <= tol){
    y = y_high;
    return 0;
  }

  if(diff_B_y_low * diff_B_y_high > 0){
    y = 0;
    return 1;
  }

  for(num_iter = 1; num_iter < max_iter; ++num_iter){
    y = (y_low + y_high)/2.0;
    double B = FairValue(t0, y);
    double diff_B = B - B_target;
    if(fabs(diff_B) <= tol)return 0;

    if (diff_B * diff_B_y_low > 0.0){
      y_low = y;
    }else{
      y_high = y;
    }

    if(fabs(y_high-y_low) <= tol)return 0;
  }

  y =0;
  return 1;



}

double Bond::FairValue(double t0, double y) const{
  double B = 0;
  double Macaulay_duration = 0;
  double modified_duration = 0;
  FV_duration(t0, y, B, Macaulay_duration, modified_duration);
  return B;
}

double Bond::Dmac(double t0, double y) const {
  double B = 0;
  double Macaulay_duration = 0;
  double modified_duration = 0;
  FV_duration(t0, y, B, Macaulay_duration, modified_duration);
  return Macaulay_duration;
}
double Bond::Dmod(double t0, double y) const {
  double B = 0;
  double Macaulay_duration = 0;
  double modified_duration = 0;
  FV_duration(t0, y, B, Macaulay_duration, modified_duration);
  return modified_duration;
}

int main(){

  std::cout << "Question 1: " << "The fair value, duration and yield calculations are all"<<
  "const methods.Explain why?" << std::endl;
  std::cout << "Answer: Those query functions are not supposed to change interal state of bond" << std::endl<<std::endl;



  std::cout << "test for par bond ###### T:10, F:100, c:10, y:10, t0:0"<<std::endl;
  std::cout << "Face = " << 100 <<std::endl;
  Bond * my_bond1 = new Bond(10, 100, 10);
  printf("Bond price = %f$\n\n", (*my_bond1).FairValue(0,10));


  std::cout << "test for 0 yield bond ###### T:10, F:100, c:10, y:0, t0:0"<<std::endl;
  Bond * my_bond2 = new Bond(10, 100, 10);
  std::cout << "Face = " << 100 <<std::endl;
  printf("Bond price = %f$\n\n", (*my_bond2).FairValue(0,0));



  std::cout << "test for zero coupon bond ###### T:10, F:100, c:0, y:10, t0:0"<<std::endl;
  Bond * my_bond3 = new Bond(10, 100, 0);
  std::cout << "Face = " << 100 <<std::endl;
  printf("Bond price = %f$\n\n", (*my_bond3).FairValue(0,10));

  std::cout << "test for yield extrapolation ###### T:10, F:100, c:10, t0:0"<<std::endl;
  Bond * my_bond4 = new Bond(10, 100.0, 10);
  std::cout << "Face = " << 100 <<std::endl;
  double y = 0.0;
  int num_iter = 0;
  (*my_bond4).yield(100,1.0e-4, 100, 0,y,num_iter);
  printf("yield = %f, when Bond price = face \n", y);
  printf("using %d iterations \n\n", num_iter);

  std::cout << "test for yield extrapolation ###### T:10, F:100, c:10, t0:0"<<std::endl;
  Bond * my_bond5 = new Bond(10, 100.0, 10);
  std::cout << "Face = " << 100 <<std::endl;
  y = 0.0;
  num_iter = 0;
  (*my_bond5).yield(80,1.0e-4, 100, 0,y,num_iter);
  printf("yield = %f, when Bond price = face - 20$ \n", y);
  printf("using %d iterations \n\n", num_iter);

  std::cout << "test for yield extrapolation ###### T:10, F:100, c:10, t0:0"<<std::endl;
  Bond * my_bond6 = new Bond(10, 100.0, 10);
  std::cout << "Face = " << 100 <<std::endl;
  y = 0.0;
  num_iter = 0;
  (*my_bond6).yield(120,1.0e-4, 100, 0,y,num_iter);
  printf("yield = %f, when Bond price = face + 20$ \n", y);
  printf("using %d iterations \n\n", num_iter);

  std::cout << "test for correctness of yield extrapolation ###### T:10, F:100, c:10, t0:0"<<std::endl;
  Bond * my_bond7 = new Bond(10, 100.0, 10);
  num_iter = 0;
  y = 0.0;
  (*my_bond7).yield(101,1.0e-4, 100, 0,y,num_iter);
  printf("yield = %f, when Bond price 100 +1 \n", y);
  printf("bond_price = %.4f, when yield = %.4f\n\n", (*my_bond7).FairValue(0, y), y);

  std::cout << "test for Macaulay_duration of zero coupon ###### T:10, F:100, y:10, c:0, t0:0" << std::endl;
  Bond * my_bond8 = new Bond(10, 100, 0);
  printf("Macaulay_duration of zero coupon = %f, when T = 10 \n\n", (*my_bond8).Dmac(0,10));
  return 0;
}
