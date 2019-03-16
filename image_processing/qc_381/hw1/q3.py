#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Mar 10 09:57:17 2019

@author: enlin
"""
from spectral import *
import cv2 as cv
import numpy as np
import matplotlib.pyplot as plt
def generateFalseColor(img):
    
    (row, col, band) = (img.shape[0],img.shape[1],3)
    ret_img = np.zeros((row,col,band))
    for i in range(row):
        for j in range(col):
            ret_img[i][j][0] = img[i,j,1]
            ret_img[i][j][1] = img[i,j,2]
            ret_img[i][j][2] = img[i,j,3]
    
    return ret_img
    
def generateNDVI(img):
    (row, col) = (img.shape[0],img.shape[1])
    ret_img = np.zeros((row,col),'uint8')
    for i in range(row):
        for j in range(col):
            val = float(img[i,j,3] - img[i,j,2]) / float(img[i,j,3] + img[i,j,2])
            ret_img[i][j] = 1 if (val > 0.0) else 0
    return ret_img
    
def main():
    img = open_image("TIPJUL1.LAN")
    plt.figure(0)
    plt.imshow(generateFalseColor(img))
    gray_NDVI = generateNDVI(img) * 255
    plt.figure(1)
    plt.imshow(gray_NDVI,cmap='gray')
if __name__ == "__main__":
    main();


#why negative number??  datatype = int16
#why display yellow???
