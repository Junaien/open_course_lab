%2006 srping 18.086 TA Yeunwoo Cho
%HW2-(4) Lax-Wendroff Method
L=10;dx=0.1;dt=0.05;
%Ininial value
x0=[-L+dx:dx:-1]';
x1=[-1+dx:dx:1-dx]';
x2=[1:dx:L-dx]';

u0=zeros(1,length(x0));
u1=ones(1,length(x1));
u2=zeros(1,length(x2));

x=[x0' x1' x2']';len=length(x);

u3=[u0 u1 u2]';

r=dt/dx;

t=0:dt:10;

u=zeros(len,length(t));

for j=1:len
u(j,1)=u3(j,1);
end  

plot(x,u(:,1))
hold on
%algorithm
for k=1:length(t)-1
    for j=1:len
        f(j,k)=-0.5*(u(j,k)^2);
        
        if j==len
            f(j+1,k)=0;
            u(j+1,k)=0;
        else
            f(j+1,k)=-0.5*(u(j+1,k)^2);
        end
        
        sum_f(j,k)=f(j+1,k)+f(j,k);
        delta_f(j,k)=f(j+1,k)-f(j,k);
        delta_u(j,k)=u(j+1,k)-u(j,k);
            
           if delta_u(j,k)==0
                a(j,k)=-u(j,k);
           else
                a(j,k)=delta_f(j,k)/delta_u(j,k);
           end
            
        F1(j,k)=0.5*sum_f(j,k)-0.5*r*(a(j,k)^2)*delta_u(j,k);
        
        if j==1
            F2(j,k)=0;
        else
            F2(j,k)=F1(j-1,k);
        end
        
        u(j,k+1)=u(j,k)-r*(F1(j,k)-F2(j,k));
    end
    
    if fix(dt*(k+1))==dt*(k+1)
            u4=u(:,k+1);
            u4(1,:)=[];
            plot(x,u4(:,1))
    end
end
xlabel('position')
ylabel('u(x,t)')
title('Lax-Wendroff')
hold off

axis([-10 2 -1 2]);
