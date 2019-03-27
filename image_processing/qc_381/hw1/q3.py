#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sun Mar 10 09:57:17 2019
@author: enlin
"""
from spectral import open_image
import numpy as np
import matplotlib.pyplot as plt
#assumption1: shape  == (rows, cols, bands)
#assumption2: max value - min value > 0 in every band
#input:  image with pixel value from any range(allows gray-scale image)
#output: image with float pixel value [0,1]

def normalize(img,shape):
    (rows, cols, bands) = shape
    min_max = []
    for i in range(bands):
        flat_intensity = np.ravel(img[:,:,i]) if bands > 1 else np.ravel(img[:,:])
        min_max.append(min(flat_intensity))
        min_max.append(max(flat_intensity))
    ret_img = np.zeros(shape,'float') if bands > 1 else np.zeros((rows,cols),'float')
    for i in range(rows):
        for j in range(cols):
            for k in range(bands):
                minV = min_max[k * 2]
                maxV = min_max[k * 2+1]
                if(bands > 1):
                    ret_img[i][j][k] = (float(img[i,j,k]) - minV) / (float(maxV) - minV)
                else:
                    ret_img[i,j] = (float(img[i,j]) - minV) / (float(maxV) - minV)
    return ret_img

#assumption1: shape == (rows, cols, bands)
#input:  normalized image \allow gray-scale image\
#output: scaled image, pixel value type same as level
def scale(img, shape, level): 
    (rows, cols, bands) = shape
    ret_img = np.zeros(shape,type(level)) if bands > 1 else np.zeros((rows,cols),type(level))
    for i in range(rows):
        for j in range(cols):
            for k in range(bands):
                if bands > 1:
                    ret_img[i,j,k] = int(img[i,j,k] * level)
                else:
                    ret_img[i,j]   = int(img[i,j] * level)
    return ret_img


def generateFalseColor(img):
    
    (rows, cols) = (img.shape[0],img.shape[1])
    img = normalize(img,(rows,cols,5))
    img = scale(img,(rows,cols,5),255)
    
    #swap bands for false image
    ret_img = np.zeros((rows,cols,3),'uint8')
    ret_img[:,:,0] = img[:,:,3] 
    ret_img[:,:,1] = img[:,:,2] 
    ret_img[:,:,2] = img[:,:,1] 
    
    return ret_img

def generate_binary_img_for_vegetation(img):
    (rows, cols) = (img.shape[0],img.shape[1])
    img = normalize(img,(rows,cols,5))
    ret_img = np.zeros((rows,cols),'uint8')    
    for i in range(rows):
        for j in range(cols):
            val = (img[i,j,3] - img[i,j,2]) / (img[i,j,2] + img[i,j,3] + 0.001)
            ret_img[i][j] = 1 if val > 0.0 else 0
    return ret_img
    
def main():
    img = open_image("TIPJUL1.LAN")
    #display false image
    plt.figure(0)
    false_img = generateFalseColor(img)
    plt.imshow(false_img)
    
    #display binary NDVI image
    plt.figure(1)
    binary_NDVI = generate_binary_img_for_vegetation(img)
    plt.imshow(binary_NDVI * 255,cmap='gray')
    
if __name__ == "__main__":
    main();


#why negative number??  datatype = int16
#why display yellow???
