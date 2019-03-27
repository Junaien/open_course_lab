#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar 14 19:08:56 2019
Q1: intensity represent the gray scale value?
Q2: how to select one building 
Q3: how to calculate height, is height z? or do I have to substract z by the distance to building? 
@author: enlin
"""


from laspy.file import File
import numpy as np
import matplotlib.pyplot as plt

#input lidar data cloud
def generate_raster_from_las(las):
    mmmin = las.header.min
    mmmax = las.header.max
    flat_ite = las.Intensity
    minIte = min(flat_ite)
    maxIte = max(flat_ite)
    (cols,rows) = (int((mmmax[0] - mmmin[0]) / 8), int((mmmax[1] - mmmin[1]) / 8))
    ret_img = np.zeros((rows + 1,cols + 1),'int16')
    
    for x, y, z,ite in np.nditer([las.x, las.y, las.z, las.Intensity]):
        map_x = int((x - mmmin[0]) / 8)
        map_y = int((y - mmmin[1]) / 8)
        ret_img[map_y][map_x] = 255 * (float(ite) - minIte) / (maxIte - minIte)
    return ret_img
#q use 8 m for the length of each cell in the raster
def main():
    source = "17258975.las"
    las    = File(source, mode = "r")
    img    = generate_raster_from_las(las) 
    plt.imshow(img, cmap='gray')

if __name__ == "__main__":
    main()