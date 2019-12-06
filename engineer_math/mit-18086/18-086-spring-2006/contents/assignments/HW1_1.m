
figure(1)
[x,y]=meshgrid(-3:.1:1,-3:.1:3);
z=x+i*y;
f1=abs(1+z+z.^2/2)-1;
contour(x,y,f1,[0,0],'k')
title('2nd order Runge-Kutta stability region')
xlabel('Real');ylabel('Imag')
axis equal,grid on


figure(2)
[x,y]=meshgrid(-3:.1:1,-3:.1:3);
z=x+i*y;
f2=abs(1+z+z.^2/2+z.^3/6+z.^4/24)-1;
f1=abs(1+z+z.^2/2)-1;
contour(x,y,f2,[0,0],'k')
title('4th order Runge-Kutta stability region')
xlabel('Real');ylabel('Imag')
axis equal,grid on
