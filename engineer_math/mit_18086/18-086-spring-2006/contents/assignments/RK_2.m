%2nd order Runge-Kutta
%RK_2(function, time_interval, initial value, step_size)
function [x,y]=RK_2(f,interval,y0,h)
a=interval(1);b=interval(2);
n=(b-a)/h;
x=(a+h:h:b);

k1=h*feval(f,a,y0);
k2=h*feval(f,a+h,y0+k1);
y(1)=y0+0.5*(k1+k2);

for i=1:n-1
    k1=h*feval(f,x(i),y(i));
    k2=h*feval(f,x(i)+h,y(i)+k1);
    y(i+1)=y(i)+0.5*(k1+k2);
end
x=[a x];
y=[y0 y];
