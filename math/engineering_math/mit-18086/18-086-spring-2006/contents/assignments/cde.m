%2006 srping 18.086 TA Yeunwoo Cho
%HW2-(1) Explicit Method
L=10;
dx=0.09;
dt=0.05;
c=1;
d=0.05;
%Initial value
x0=[-L+dx:dx:0]';
x1=[dx:dx:1]';
x2=[1+dx:dx:2]';

u0=zeros(1,length(x0));
u1=x1;
u2=2-x2;

x=[x0' x1' x2']';
n=length(x);

u=[u0 u1' u2']';

r=c*dt/dx
R=d*(dt/(dx^2))
P=r/(2*R)
%algorithm
A_cde=sparse(diag((R+0.5*r)*ones(n-1,1),1)+...
             diag((1-2*R)*ones(n,1))+...
             diag((R-0.5*r)*ones(n-1,1),-1));
         
plot(x,u);
hold on;
for t=dt:dt:7
    u=A_cde*u;
    if fix(t)==t;
        plot(x,u)
        axis([-10 2 -1 2]);
    end
end
xlabel('position')
ylabel('u(x,t)')
title('Explicit method')
hold off