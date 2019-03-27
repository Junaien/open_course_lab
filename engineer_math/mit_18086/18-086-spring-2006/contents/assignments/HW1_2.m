h=0.1;%step size

rk2_u=[];
ab2_u=[];
h_t=[];

t=0:.05:1;
y=exp(-10*t);%exact solution

%step size change 
for k=1:5 
[trk2,urk2]=RK_2(@f_is,[0,1],1,h);
[tab2,uab2]=AB_2(@f_is,[0,1],1,h);
plot(t,y,trk2,urk2,'.',tab2,uab2,'+')
xlabel('t');ylabel('u')
legend('Exact','RK2','AB2')

exact_rk2=exp(-10*trk2);
rk2error=max(abs(exact_rk2-urk2));
exact_ab2=exp(-10*tab2);
ab2error=max(abs(exact_ab2-uab2));

h_t=[h_t,h];
rk2_u=[rk2_u,rk2error];
ab2_u=[ab2_u,ab2error];
pause
h=h/2;
end

%accuracy 
loglog(h_t,rk2_u,h_t,ab2_u)
legend('rk2','ab2')
grid
xlabel('Step size')
ylabel('Maximum error')
title('Maximum error vs step size')
axis tight

%log10(max_error)=B log10(h_t)+A
prk2=polyfit(log10(h_t),log10(rk2_u),1)
pab2=polyfit(log10(h_t),log10(ab2_u),1)