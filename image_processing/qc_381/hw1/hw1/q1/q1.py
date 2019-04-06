from __future__ import division
import numpy as np
import math
import matplotlib.pyplot as plt
import PIL
import cv2


def question1():
    img_under = cv2.imread('./Q1_underexposed.jpg')
    img_over = cv2.imread('./Q1_overexposed.jpg')
    
    color = ('b','g','r','k')
    for status,color in enumerate(color):
        hist = calHis(img_under, status)
        plot(hist, 1, color)
        hist = calHis(img_over, status)
        plot(hist, 2, color)
    savePlot(1, 'underexposed color histogram', './underexposed_color_histogram.png')
    savePlot(2, 'overexposed color histogram', './overexposed_color_histogram.png')

def calHis(img, status):
    hist = [0]*256
    width, height = img.shape[:2]
    for y in range(height):
        for x in range(width):
            bgr = img[x , y]
            if(status == 4):
                hist[bgr] += 1
            elif(status == 3):
                avg = (int(bgr[0])+int(bgr[1])+int(bgr[2]))/3
                hist[int(avg)] += 1
            else:
                hist[bgr[status]] += 1
    return np.array(hist)

def plot(hist, fig, color):
    plt.figure(fig)
    plt.plot(hist, color = color)
    plt.xlim([0,256])

def savePlot(fig, title, location):
    plt.figure(fig)
    plt.title(title)
    plt.savefig(location)

def main():
    question1()

if __name__== "__main__":
    main()