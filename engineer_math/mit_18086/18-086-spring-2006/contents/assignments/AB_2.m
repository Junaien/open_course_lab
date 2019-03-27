%2nd orer Adams-Bashforth
%AB_2(function, time_interval, initial value, step_size)
function [x,y]=AB_2(f,interval,y0,h)
a=interval(1);b=interval(2);
n=(b-a)/h;
x=(a+h:h:b);

z0=feval(f,a,y0);
k1=h*z0;
k2=h*feval(f,a+0.5*h,y0+0.5*k1);
y(1)=y0+k2;
z(1)=feval(f,x(1),y(1));
y(2)=y(1)+0.5*h*(3*z(1)-z0);

for i=2:n-1
    z(i)=feval(f,x(i),y(i));
    y(i+1)=y(i)+0.5*h*(3*z(i)-z(i-1));
end
x=[a x];
y=[y0 y];
