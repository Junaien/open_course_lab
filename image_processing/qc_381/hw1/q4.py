#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Mar 14 19:08:56 2019

@author: enlin
"""

from laspy.file import File
import numpy as np

source = "17258975.las"
las = File(source,mode="r")
mmmin = las.header.min
mmmax = las.header.max
print(mmmin)
print(mmmax)
#for x, y, z, ite, c,nr,rn in np.nditer([las.x, las.y, las.z, las.Intensity, las.Classification, las.num_returns,las.return_num]):
#    print(" Longitude: " ,x)
#    print(" Latitude: " ,y)
#    print(" Altitude: " ,z)
#    print(" Intensity value: " ,ite)
#    print(" Classification: " ,c)
#    print(" Number of Returns: " ,nr)
#    print(" Return Number: " ,rn)

    
#q use 8 m for the length of each cell in the raster