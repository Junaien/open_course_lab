// Q1 - why are they const
// we don't want to change the original data passed in all we do is
// calculate yield curve and spot rate based on value passed in

// Q2 - why are they references to vectors
// only passed in by references can we modified it inside function

// Q3 - why are all of these methods const?
// const double* df(int n) const;
// const double* spot(int n) const;
// const double* tenor(int n) const;
// const double* yield(int n) const;
//
// thoes are get method, intended to get information instead of change it
//
// Q4 - why is interpolate const
// interpolate only calculate the spot rate that is specified buy caller
// it does not change any internal state
//
// Q5 - disable copying , set copy constructor to private
// if we set copy constructor private, outside this class we not allow
// to call copy constructor anymore, that means no copies outside
//
//
class YieldCurve
{
  public:
      YieldCurve();
      ~YieldCurve();
      int create_curves(int n, const std::vector<double> & dates,
      const std::vector<double> & yields);
      // const methods
      int length_par_curve() const { return par_yields.size(); }
      int length_spot_curve() const { return spot_rates.size(); }
      const double* df(int n) const;
      const double* spot(int n) const;
      const double* tenor(int n) const;
      const double* yield(int n) const;
      int interpolate(int algorithm, double t, double & df, double & r) const;

  private:
      void reset();
      int set_yields(int n, const std::vector<double> & dates,
      const std::vector<double> & yields);
      void bootstrap();
      // data
      std::vector<double> tenors;
      std::vector<double> par_yields;
      std::vector<double> discount_factors;
      std::vector<double> spot_rates;
      // disable copy
      YieldCurve(const YieldCurve& );
      YieldCurve& operator=(const YieldCurve& );
};

YieldCurve::YieldCurve(){
}
YieldCurve::~YieldCurve(){}
YieldCurve::reset(){
  tenors.clear();
  par_yields.clear();
  discount_factors.clear();
  spot_rates.clear();
}

YieldCurve::YieldCurve(const YieldCurve& ){}
int YieldCurve::create_curves(int n, const std::vector<double> & dates,
const std::vector<double> & yields){
    reset();
    int rc = set_yields(n, dates, yields);
    if (rc) return rc;
    bootstrap();
    return 0;
}
int YieldCurve::set_yields(int n, const std::vector<double> & dates,
const std::vector<double> & yields){
  if(n <= 0 || dates.size() < n || yields.size() < n)return 1;
  if(dates[0] <= 0)return 1;
  for(int i =0; i < n; i++){
    if(yields[i] < 0 || i != n-1 && dates[i] >= dates[i+1])
      return 1;
  }
  //pupilate data
  tenors.push_back(0.0);
  par_yields.push_back(0.0);
  for(int i =0; i < n; i++){
      tenors.push_back(dates[i]);
      par_yields.push_back(yields[i]);
  }
  return 0;
}

void YieldCurve::bootstrap(){
  discount_factors.push_back(1.0);
  spot_rates.push_back(0.0);

  double y_dec = 0.01 * par_yields[1];
  double df = 1.0/(1.0 + 0.5 * y_dec);
  discount_factors.push_back(df);
  double r = -100.0 * log(df) / (tenors[1] - tenors[0]);
  spot_rates.push_back(r);

  spot_rates[0] = spot_rates[1];

}
