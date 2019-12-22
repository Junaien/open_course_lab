%2006 srping 18.086 TA Yeunwoo Cho
%HW2-(3) Upwind Method
L=15;
dx=0.1;
dt=0.05;
x=[-L+dx:dx:0]';
n=length(x);
%Initial value
u1=zeros(1,n-10);
u2=ones(1,10);
u=[u1 u2]';

r=dt/dx
%algorithm
A_up=sparse(diag((1-r)*ones(n,1))+diag(r*ones(n-1,1),1));
b=[0 zeros(1,n-2) r]';
plot(x,u);
hold on;
shock_width=[];

for t=dt:dt:7
    u=A_up*u+b;
    if fix(t)==t;
        plot(x,u)
        a_index=find(u>=0.9 & u<=1);
        b_index=find(u>=0 & u<=0.1);
        a_size=size(a_index);
        b_size=size(b_index);
        wid_1=a_index(1,1);
        wid_2=b_index(b_size(1,1),1);
        sw=shock_width;
        shock_width=[sw wid_1-wid_2];
        axis([-15 0 -1 2]);
    end
end
hold off
xlabel('position')
ylabel('u(x,t)')
title('Upwind')
shock_width
