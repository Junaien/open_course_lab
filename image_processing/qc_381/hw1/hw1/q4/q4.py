#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar 14 19:08:56 2019
@author: enlin
"""


from laspy.file import File
import numpy as np
import matplotlib.pyplot as plt
import os

#input:  
#[las] => lidar data cloud from hw1
#--------
#output: 
# => pixel matrix stored to './q4_output_height.txt' and './q4_output_img.txt'
def generate_raster_and_height_from_las(las):
    mmmin = las.header.min
    mmmax = las.header.max
    flat_ite = las.Intensity
    minIte = min(flat_ite)
    maxIte = max(flat_ite)
    (cols,rows) = (int((mmmax[0] - mmmin[0]) / 8.0), int((mmmax[1] - mmmin[1]) / 8.0))
    ret_img = np.zeros((rows + 1,cols + 1),'int16')
    ret_height = np.zeros((rows + 1,cols + 1), 'int16')
    for x, y, z,ite in np.nditer([las.x, las.y, las.z, las.Intensity]):
        map_x = int((x - mmmin[0]) / 8.0)
        map_y = int((y - mmmin[1]) / 8.0)
        ret_img[map_y][map_x] = 255 * (float(ite) - minIte) / (maxIte - minIte)
        ret_height[map_y][map_x] = z
    np.savetxt('./q4_output_height.txt',ret_height)
    np.savetxt('./q4_output_img.txt',ret_img)


#produce a binary mask, based on provided boundary
def generate_mask(rows, cols, r_min,r_max, c_min, c_max):
    ret_mask = np.zeros((rows, cols), 'int16')
    for i in range(r_min,r_max):
        for j in range(c_min,c_max):
            ret_mask[i,j] = -1  #-1 reprensents 111111..1111 in int16
    return ret_mask
          
  
#input:
#[rough_masked_img] => gray-scale image with *one* selected building and its ground
#[img_height]       => a matrix indicates the height of image
#[ground]           => pre-observed ground height
#----------
#output: 
#(image_with_ground_eliminated, area based on 8m per pixel, height of selected building) 
def calculateAreaHeight(rough_masked_img,img_height,ground):
    (rows, cols) = (len(img_height), len(img_height[0]))
    heights = []
    exact_masked_img = np.zeros((rows,cols),'int16')
    for i in range(rows):
        for j in range(cols):
            if img_height[i,j] < ground or rough_masked_img[i,j] == 0:
               exact_masked_img[i,j] = 0
            else:
               exact_masked_img[i,j] = rough_masked_img[i,j]
               heights.append(img_height[i,j])
    return (exact_masked_img,len(heights) * 8, np.mean(heights))


def main():
    
    #calculate pixel matrix from lidar point data
    source = "./17258975.las"
    output_img_exists = os.path.isfile('./q4_output_img.txt')
    output_height_exists = os.path.isfile('./q4_output_height.txt')
    img = None
    
    #used pixel matrix cached file if previously computed
    if not (output_img_exists and output_height_exists):
        las = File(source, mode = "r")
        generate_raster_and_height_from_las(las)
    
    #load image pixel matrix, image height matrix
    img = np.loadtxt('./q4_output_img.txt', dtype = np.uint16)
    img_height = np.loadtxt('./q4_output_height.txt',dtype = np.int16)
    
    #roughly mask out building by hand
    img_rough_mask    = generate_mask(img.shape[0],img.shape[1],10,117,90,143)
    rough_masked_img  = np.bitwise_and(img_rough_mask,img)
    ground = 660
    
    #exactly mask out selected building 
    (exact_masked_img, area , height) = calculateAreaHeight(rough_masked_img,img_height,ground)
    plt.imsave('original',img, cmap='gray')
    plt.imsave('original_height',img_height, cmap='gray')
    plt.imsave("roughly masked-out",rough_masked_img, cmap='gray')
    plt.imsave("exactly_masked-out",exact_masked_img, cmap='gray')
    print("selected building has area: %f m^2" % area)
    print("selected building has height: %d m, (used ground threshold = 660)" % height) 

    
if __name__ == "__main__":
    main()
