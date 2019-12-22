%2006 srping 18.086 TA Yeunwoo Cho
%HW2-(3) Lax-Wendroff Method
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
A_lw=sparse(diag((1-r*r)*ones(n,1))+...
            diag((0.5*r*r+0.5*r)*ones(n-1,1),1)+...
            diag((0.5*r*r-0.5*r)*ones(n-1,1),-1));
b=[0 zeros(1,n-2) 0.5*r*r+0.5*r]';
plot(x,u);
hold on;
shock_width=[];
for t=dt:dt:7
    u=A_lw*u+b;
    if fix(t)==t;
        plot(x,u)
        shock_index=find(u>=0.01 & u<=0.99);
        shock_size=size(shock_index);
        for m=1:shock_size-1
            if shock_index(m+1)-shock_index(m)>1
                sw1=shock_index(m)-shock_index(1);           
                break
            else
                sw1=shock_index(end)-shock_index(1);
            end  
        end
        sw=shock_width;
        shock_width=[sw sw1];
        axis([-15 0 -1 2]);
    end
end
xlabel('position')
ylabel('u(x,t)')
title('Lax-Wendroff')
shock_width
hold off