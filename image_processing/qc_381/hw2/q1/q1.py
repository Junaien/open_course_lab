import cv2
import numpy as np
import matplotlib.pyplot as plt
#input  <img>:   gray level img
#input  <c>:     constant of gamma transformation euqation
#input  <g>:     the power of gamma transformation
#output :        gray scale img after gamma transformation
def gamma_trans(img, g, level):
    (rows, cols) = (len(img), len(img[0]))
    ret_img = np.zeros((rows,cols), 'uint8')
    c = (level - 1) / pow(level - 1,g)
    for i in range(rows):
        for j in range(cols):
            ret_img[i,j] = c * pow(img[i,j],g)
    return ret_img

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
    underexposed = cv2.cvtColor(cv2.imread('./orig_images/underexposed.jpg'), cv2.COLOR_BGR2GRAY)
    overexposed = cv2.cvtColor(cv2.imread('./orig_images/overexposed.jpg'), cv2.COLOR_BGR2GRAY)
    g_underexposed = gamma_trans(underexposed,0.4,256)
    g_overexposed = gamma_trans(overexposed,6,256)

    plot_save(1,"orig_under","./orig_under.png",calHist(underexposed,256),'r',256)
    plot_save(2,"orig_over","./orig_over.png",calHist(overexposed,256),'g',256)
    plot_save(3,"corrected_under","./corrected_under.png",calHist(g_underexposed,256),'b',256)
    plot_save(4,"corrected_over","./corrected_over.png",calHist(g_overexposed,256),'k',256)
    cv2.imwrite("gray_underexposed.jpg", underexposed);
    cv2.imwrite("gray_overexposed.jpg", overexposed);
    cv2.imwrite("ret_underexposed.jpg", g_underexposed);
    cv2.imwrite("ret_overexposed.jpg", g_overexposed);

if __name__ == "__main__" :
     main()
