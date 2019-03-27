# -*- coding: utf-8 -*-
"""
Created on Thu Mar 14 19:08:56 2019

@author: enlin
"""
"""
Question: Describe briefly the differences in the histograms?
Answer:
    
    
    
"""
import numpy as np
import matplotlib.pyplot as plt

#assumption1: image has three bands
#input:       image with R,G,B bands
#output:      image with one band
def to_gray(img):
    (rows,cols) = (img.shape[0], img.shape[1])
    ret_img = np.zeros((rows,cols),'uint8')
    for i in range(rows):
        for j in range(cols):
            ret_img[i][j] = (int(img[i][j][0]) +
                             int(img[i][j][1]) +
                             int(img[i][j][2])) / 3
    return ret_img

#assumption1: image has only one band, with pixel value [0,level)
#input:       image with gray scale value
#output:      a histogram of this image
def calculate_histo(img, level):
    hist = np.zeros(level,'int16');
    (rows, cols) = np.shape(img)
    for i in range(rows):
        for j in range(cols):
            hist[img[i,j]] += 1
    return hist
    
def main():
    over_exposed_img = plt.imread("overexposed.jpg")
    under_exposed_img = plt.imread("underexposed.jpg")
    
    over_exposed_gray_img = to_gray(over_exposed_img)
    under_exposed_gray_img = to_gray(under_exposed_img)
    x = range(256)
    plt.figure(0)
    plt.title('over-exposed')
    plt.plot(x,calculate_histo(over_exposed_gray_img,256),color='k',label = 'gray histo')
    plt.legend()
    plt.plot(x,calculate_histo(over_exposed_img[:,:,0],256),color='r',label = 'red histo')

    plt.legend()
    plt.plot(x,calculate_histo(over_exposed_img[:,:,1],256),color='g',label = 'green histo')
    plt.legend()
    plt.plot(x,calculate_histo(over_exposed_img[:,:,2],256),color='b',label = 'blue histo')
    plt.legend()
    
    plt.figure(1)
    plt.title('under-exposed')
    plt.plot(x,calculate_histo(under_exposed_gray_img,256),color='k',label = 'gray histo')
    plt.legend()
    plt.plot(x,calculate_histo(under_exposed_img[:,:,0],256),color='r',label = 'red histo')

    plt.legend()
    plt.plot(x,calculate_histo(under_exposed_img[:,:,1],256),color='g',label = 'green histo')
    plt.legend()
    plt.plot(x,calculate_histo(under_exposed_img[:,:,2],256),color='b',label = 'blue histo')
    plt.legend()
    
if __name__ == "__main__":
    main()
