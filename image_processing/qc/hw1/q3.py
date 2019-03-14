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
def main():
    img = open_image("TIPJUL1.LAN")
    
    (row, col, band) = (img.shape[0],img.shape[1],3)
    ret_img = np.zeros((row,col,band))
    for i in range(row):
        for j in range(col):
            ret_img[i][j][0] = img[i,j,1]
            ret_img[i][j][1] = img[i,j,2]
            ret_img[i][j][2] = img[i,j,3]
    plt.imshow(ret_img)
if __name__ == "__main__":
    main();


#why negative number??  datatype = int16
#why display yellow???
#why to see 2m wide pit, it requires 1 sample every meter
    