% Case 1: 'shock' problem
% Problem discription: use Lax-Friedrichs scheme to solve PDE: Ut+UUx=0 for U at time t=4 and -4<x<4;
% Given inital boundary condition: U=1 when x<0 and U=0 when x>0.
%
color =  ['r', 'b', 'g'];
index = 1;
%%
for dt = [0.5, 0.1, 0.02]
dx = dt * 2;
r = 0.5;   %% r= dt/dx;
x = [-9:dx:9]; 
time = [dt:dt:4];
u0 = 1*(x<=0) + 0*(x>0);  % Set initial condition;
u = u0;
for t = time
    u_plus = [u(2:end), u(end)];
    u_minus = [u(1), u(1:end-1)];
    u = (u_plus + u_minus)/2 - r*(u_plus.^2-u_minus.^2)/4; % Lax-Friedrichs scheme;
end
% plot the result U for t=4 and -4<x<4;
rng = find(x<=4 & x>=-4);
plot(x(rng), u(rng), color(index), 'linewidth',1);
hold on
index = index+1;
end
%%
%% Also plot Analytical solution for PDE;
linex = [-4, 2, 2, 4];
liney = [1, 1, 0, 0];
plot(linex, liney, 'k', 'linewidth', 1);