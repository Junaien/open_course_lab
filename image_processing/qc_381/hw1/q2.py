#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Mar  9 17:17:07 2019

@author: enlin
"""
import numpy as np
import cv2 as cv
import q1
import matplotlib.pyplot as plt

#accept gray image
def generate_detected_object(img, backgroud, threshold):
    if (img.shape[0] > backgroud.shape[0]) or (img.shape[1] > backgroud.shape[1]):
           raise Exception('background should have large size')
    (row, col) = img.shape
    
    binary_img = np.zeros(img.shape)
    
    for i in range(row):
        for j in range(col):
            curDiff = abs(backgroud[i][j] - img[i][j])
            if curDiff > threshold:
                binary_img[i][j] = 0;
            else:
                binary_img[i][j] = 1;
    
    return binary_img

def main():
    img = cv.imread('img.jpg')
    img_gray = q1.to_gray(img)
    background = cv.imread('background.jpg')
    background_gray = q1.to_gray(background)
    
    result = generate_detected_object(img_gray,background_gray,30)
    plt.imshow(result,cmap='gray')
    
    
if __name__ == "__main__":
    main()
    
    
# use diff data type to store binary image?
