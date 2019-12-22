% using difference equation to solve nonlinear PDE
% Ce Liu
% CSAIL MIT
% celiu@mit.edu

function U=simulation(A,B,delta_t)
%parameter setting
if exist('delta_t')~=1
    delta_t=1/32;
end
delta_x=delta_t*2;
x_min=-5;
x_max=5;
t_max=4;
width=(x_max-x_min)/delta_x+1;
height=t_max/delta_t+1;

U(1,1:(width-1)/2)=A;
U(1,(width-1)/2+1:width)=B;
r=delta_t/delta_x/4;
for i=2:height
   for j=1:width
      if j==1
         U(i,j)=-r*(U(i-1,j+1)^2-U(i-1,j)^2)+1/2*(U(i-1,j+1)+U(i-1,j));
         continue;
      end
      if j==width
         U(i,j)=-r*(U(i-1,width)^2-U(i-1,j-1)^2)+1/2*(U(i-1,width)+U(i-1,j-1));
         continue; 
      end
      U(i,j)=-r*(U(i-1,j+1)^2-U(i-1,j-1)^2)+1/2*(U(i-1,j+1)+U(i-1,j-1));
   end
end
Z=U;
U=Z(end:-1:1,:);