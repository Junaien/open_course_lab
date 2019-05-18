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
            assert img[i,j] >= 0 and img[i,j] < level
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
    orig_under_img   = cv2.imread('./orig_images/underexposed.jpg')
    orig_over_img    = cv2.imread('./orig_images/overexposed.jpg')

    orig_under_gray  = cv2.cvtColor(orig_under_img, cv2.COLOR_BGR2GRAY)
    orig_over_gray   = cv2.cvtColor(orig_over_img, cv2.COLOR_BGR2GRAY)

    equal_under_gray = cv2.equalizeHist(orig_under_gray)
    equal_over_gray  = cv2.equalizeHist(orig_over_gray)

    cv2.imwrite('./orig_under_gray.jpg', orig_under_gray)
    cv2.imwrite('./orig_over_gray.jpg', orig_over_gray)

    cv2.imwrite('./equal_under_gray.jpg', equal_under_gray)
    cv2.imwrite('./equal_over_gray.jpg', equal_over_gray)

    plot_save(1,"original under histogram", "./orig_under_histo.png", calHist(orig_under_gray,256),'r',256)
    plot_save(2,"equalized under histogram", "./equal_under_histo.png", calHist(equal_under_gray,256),'b',256)
    plot_save(3,"original over histogram", "./orig_over_histo.png", calHist(orig_over_gray,256),'r',256)
    plot_save(4,"equalized over histogram", "./equal_over_histo.png", calHist(equal_over_gray,256),'b',256)

if __name__ == "__main__":
    main()
