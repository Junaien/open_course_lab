% using difference equation to solve nonlinear PDE
n=6;
Para=[1,0;0,1];
for k=1:2
    A=Para(k,1);B=Para(k,2);
    for i=1:n
        delta_t=(1/2)^i;
        U=simulation(A,B,delta_t);
        %subplot(n,1,i,'align');
        %close;close;
        figure;
        plot(U(1,:));ylim([-.2,1.2]);xlim([1,size(U,2)]);
        %title(['\Delta_t=' num2str(delta_t)]);
        %set(gca,'DataAspectRatio',[size(U,2) 2 1]);
        %saveas(gcf,['A=' num2str(A) 'B=' num2str(B) 'Deltat=' num2str(delta_t) 'curve.eps'],'psc2');
        figure;
        if i<n
            S=imresize(U,2^(n-i),'nearest');
        else
            S=U;
        end
        [height,width]=size(S);
        U=zeros(height+4,width+4);
        U(3:height+2,3:width+2)=S;
        imshow(U);
        %saveas(gcf,['A=' num2str(A) 'B=' num2str(B) 'Deltat=' num2str(delta_t) 'image.eps'],'psc2');
    end
end