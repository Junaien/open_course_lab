import numpy as np
import cv2
import matplotlib.pyplot as plt

#input <img>:   gray level img
#input <level>: pixel quantization level
#output:        histogram of img
def calHist(img, level):
    hist = np.zeros(level)
    (rows, cols) = (len(img), len(img[0]))
    for i in range(rows):
        for j in range(cols):
            hist[img[i,j]] += 1
    return hist

#input <hist>:   histogram of gray image
#input <color>:  color for line plot
#output:         save plot to current folder
def plot_save(i, title, path, hist, color, level):
    plt.figure(i)
    plt.xlim([0,level])
    plt.title(title)
    plt.plot(hist, color = color)
    plt.savefig(path)


def main():
    orig_img   = cv2.imread('./orig_images/underexposed.jpg')
    orig_gray  = cv2.cvtColor(orig_img, cv2.COLOR_BGR2GRAY)
    equal_gray = cv2.equalizeHist(orig_gray)
    cv2.imwrite('./orig_gray.jpg', orig_gray)
    cv2.imwrite('./equal_gray.jpg', equal_gray)
    plot_save(1,"original histogram", "./orig_histo.png", calHist(orig_gray,256),'r',256)
    plot_save(2,"equalized histogram", "./equal_histo.png", calHist(equal_gray,256),'b',256)

if __name__ == "__main__":
    main()
