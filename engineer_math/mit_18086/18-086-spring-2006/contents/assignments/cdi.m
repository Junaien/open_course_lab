%2006 srping 18.086 TA Yeunwoo Cho
%HW2-(1) Implicit Method
L=10;
dx=0.1;
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
%algorithm
A_cdi=sparse(diag(-R*ones(n-1,1),1)+...
             diag((1+2*R)*ones(n,1))+...
             diag(-R*ones(n-1,1),-1));
         
B_cdi=sparse(diag(0.5*r*ones(n-1,1),1)+...
             diag(ones(n,1))+...
             diag(-0.5*r*ones(n-1,1),-1));         

     
plot(x,u);
hold on;
for t=dt:dt:7
    u=A_cdi\(B_cdi*u);
    if fix(t)==t
        
        plot(x,u)
        axis([-10 2 -1 2]);
    end
end
xlabel('position')
ylabel('u(x,t)')
title('Implicit method')
hold off