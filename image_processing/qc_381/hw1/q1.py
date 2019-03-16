# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""
import numpy as np, cv2 as cv
import matplotlib.pyplot as plt


def to_gray(img):
    (row,col) = (img.shape[0], img.shape[1])
    ret_img = np.zeros((row,col))
    for i in range(row):
        for j in range(col):
            ret_img[i][j] = (int(img[i][j][0]) +
                                           img[i][j][1] +
                                           img[i][j][2]) / 3

    return ret_img
def calculate_histo(img, level, row, col):
    hist = np.zeros(level);
    for i in range(row):
        for j in range(col):
            hist[img(i)(j)] += 1
    return hist
    
def main():
    over_exposed_img = cv.imread("overexposed.jpg")
    under_exposed_img = cv.imread("underexposed.jpg")
    over_exposed_gray_img = to_gray(over_exposed_img)
    under_exposed_gray_img = to_gray(under_exposed_img)
    plt.figure(0)
    plt.hist(np.ravel(over_exposed_gray_img),256, label = 'gray')
    plt.legend()
    #plt.hist(np.ravel(over_exposed_img[:,:,0]),256, label = 'blue')
    #plt.legend()
    #plt.hist(np.ravel(over_exposed_img[:,:,1]),256, label = 'green')
    #plt.legend()
    #plt.hist(np.ravel(over_exposed_img[:,:,2]),256, label = 'red')
    #plt.legend()


    plt.figure(1)
    plt.hist(np.ravel(under_exposed_gray_img),256, label = 'gray')
    plt.legend()
    #plt.hist(np.ravel(under_exposed_img[:,:,0]),256, label = 'blue')
    #plt.legend()
    #plt.hist(np.ravel(under_exposed_img[:,:,1]),256, label = 'green')
    #plt.legend()
    #plt.hist(np.ravel(under_exposed_img[:,:,2]),256, label = 'red')
    #plt.legend()
if __name__ == "__main__":
    main()
