#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar  9 17:17:07 2019
Q1: what do you mean by quantify the number of pixels that belong ....?
Q2: visualize the histogram of which image?
@author: enlin
"""

import numpy as np
import q1
import matplotlib.pyplot as plt

def generate_detected_object(img, background, threshold):
    if (img.shape[0] > background.shape[0]) or (img.shape[1] > background.shape[1]):
           raise Exception('background should have large size')
    (rows, cols) = img.shape
    
    binary_img = np.zeros(img.shape,'uint8')
    
    for i in range(rows):
        for j in range(cols):
            curDiff = abs(int(background[i][j]) - int(img[i][j]))
            if curDiff > threshold:
                binary_img[i][j] = 1;
            else:
                binary_img[i][j] = 0;
    return binary_img

def main():
    img = plt.imread('img.jpg')
    background = plt.imread('background.jpg')
    plt.figure(0)
    plt.title('image')
    plt.imshow(img)
    #------
    plt.figure(1)
    plt.title('background')
    plt.imshow(background)
    #-------
    plt.figure(3)
    plt.title('detect')
    img_gray = q1.to_gray(img)
    
    background_gray = q1.to_gray(background)
    result = generate_detected_object(img_gray,background_gray,30)
    plt.imshow(result,cmap='gray')
    #------- 
if __name__ == "__main__":
    main()
    
    
# use diff data type to store binary image?
