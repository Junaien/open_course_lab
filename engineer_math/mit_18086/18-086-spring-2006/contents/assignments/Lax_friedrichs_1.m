clear all; close all;

% Shock

for (m = 1:4)
    n = 8*m;
    h = 4/n;
    dt = h/2;
    
    u(1,1:4*n+1,m) = [ones(1,2*n) 0.5 zeros(1,2*n)];
    for (i = 2:n+1)
            for (j = 2:4*n)
                u(i,j,m) = ( (u(i-1,j-1,m))^2 - (u(i-1,j+1,m))^2 )/4/h*dt + (u(i-1,j+1,m)+u(i-1,j-1,m))/2;
            end
    end

    for (k = 1:2*n+1)
        y(k,m) = u(n+1,k+n,m);
    end 
end

figure(1);
x = linspace(-4, 4, 16+1);
subplot(4,1,1); plot(x, y(1:length(x),1)); legend('gird = 8'); xlabel('x'); ylabel('u'); title('Shock');
x = linspace(-4, 4, 16*2+1);
subplot(4,1,2); plot(x, y(1:length(x),2)); legend('gird = 16'); xlabel('x'); ylabel('u');
x = linspace(-4, 4, 16*3+1);
subplot(4,1,3); plot(x, y(1:length(x),3)); legend('gird = 24'); xlabel('x'); ylabel('u');
x = linspace(-4, 4, 16*4+1);
subplot(4,1,4); plot(x, y(1:length(x),4)); legend('gird = 32'); xlabel('x'); ylabel('u');


for (k = 1:n+1)
    t(k) = 2*dt*k;
end

v = zeros(n+1,2*n+1);
for (i = 1:n+1)
    for (j = 1:2*n+1)
        v(i,j) = u(i,j+n,4);
    end
end

figure(2);
title('Shock');
mesh(x,t,v);
xlabel('x'); ylabel('t'); zlabel('u');


%Fan
for (m = 1:4)
    n = 8*m;
    h = 4/n;
    dt = h/2;
    
    u(1,1:4*n+1,m) = [zeros(1,2*n) 0.5 ones(1,2*n)];
    for (i = 2:n+1)
            for (j = 2:4*n)
                u(i,j,m) = ( (u(i-1,j-1,m))^2 - (u(i-1,j+1,m))^2 )/4/h*dt + (u(i-1,j+1,m)+u(i-1,j-1,m))/2;
            end
    end

    for (k = 1:2*n+1)
        y(k,m) = u(n+1,k+n,m);
    end 
end

figure(3);
x = linspace(-4, 4, 16+1);
subplot(4,1,1); plot(x, y(1:length(x),1)); legend('gird = 8'); xlabel('x'); ylabel('u'); title('Fan');
x = linspace(-4, 4, 16*2+1);
subplot(4,1,2); plot(x, y(1:length(x),2)); legend('gird = 16'); xlabel('x'); ylabel('u');
x = linspace(-4, 4, 16*3+1);
subplot(4,1,3); plot(x, y(1:length(x),3)); legend('gird = 24'); xlabel('x'); ylabel('u');
x = linspace(-4, 4, 16*4+1);
subplot(4,1,4); plot(x, y(1:length(x),4)); legend('gird = 32'); xlabel('x'); ylabel('u');

for (k = 1:n+1)
    t(k) = 2*dt*k;
end

v = zeros(n+1,2*n+1);
for (i = 1:n+1)
    for (j = 1:2*n+1)
        v(i,j) = u(i,j+n,4);
    end
end

figure(4);
title('Fan');
mesh(x,t,v);
xlabel('x'); ylabel('t'); zlabel('u');