import numpy as np
import cv2
import matplotlib.pyplot as plt
import math
def produce_box_kernel(box_size):
    return np.float32([[1 / (box_size * box_size)] * box_size] * box_size);

#<input> img:  gray scale image
#<output>:     normalize gray scale image(int 32)
def normalize(img):
    (rows, cols) = (len(img), len(img[0]))
    flat_img     = np.ravel(img)
    (low, high) = (min(flat_img),max(flat_img))
    ret_img = np.zeros((rows,cols),'int32')
    for i in range(rows):
        for j in range(cols):
                ret_img[i,j] = int(255 * (img[i,j] - low) / (high - low))
    return ret_img

#<input> theta: standard deviation for kernel
#<input> K: coefficient for gaussian kernel
def produce_gaussian_kernel(theta, K):
    #gain odd size
    size       = int((6 * theta) // 2) * 2 + 1
    ret_kernel = np.zeros((size,size),'float32')
    for i in range(size):
        for j in range(size):
            (s, t) = (i - size // 2, j - size // 2)
            ret_kernel[i,j] = K * math.exp(-(pow(s,2) + pow(t,2)) / (2 * pow(theta,2)))
    return ret_kernel

def question1():
    img  = cv2.cvtColor(cv2.imread('./orig_images/pic.jpg'), cv2.COLOR_BGR2GRAY)
    cv2.imwrite("./question1/orig_img.jpg", img);

    #apply box kernel
    box_size     = len(img) // 20
    box_filter   = produce_box_kernel(box_size)
    box          = cv2.filter2D(img, -1, box_filter)
    cv2.imwrite("./question1/box_filtered_img.jpg", box);

    #apply gaussian kernel
    gaussian_filter   = produce_gaussian_kernel(7, 0.005)
    gaussian_over     = cv2.filter2D(img, -1, gaussian_filter)
    cv2.imwrite("./question1/gaussian_filtered_img.jpg", gaussian_over);

def question2():
    img  = cv2.cvtColor(cv2.imread('./orig_images/pic.jpg'), cv2.COLOR_BGR2GRAY)
    cv2.imwrite("./question2/orig_img.jpg", img);

    #sobel kernel
    sobel_px  = np.float32([[-1,-2,-1],[0,0,0],[1,2,1]])
    sobel_py  = np.float32([[-1,0,1],[-2,0,2],[-1,0,1]])
    sobel_img = abs(cv2.filter2D(img, -1, sobel_px)) + abs(cv2.filter2D(img, -1, sobel_py))
    cv2.imwrite("./question2/sobel_img.jpg", sobel_img);

    #rober kernel
    robert_px  = np.float32([[0,0,0],[0,-1,1],[0,0,0]])
    robert_py  = np.float32([[0,0,0],[0,-1,0],[0,1,0]])
    robert_img = abs(cv2.filter2D(img, -1, robert_px)) + abs(cv2.filter2D(img, -1, robert_py))
    cv2.imwrite("./question2/robert_img.jpg", robert_img);



def question3():
    img  = cv2.cvtColor(cv2.imread('./orig_images/pic.jpg'), cv2.COLOR_BGR2GRAY)
    cv2.imwrite("./question3/orig_img.jpg", img);

    #second order derivative kernel
    second_deri  = np.float32([[0,-1,0],[-1,4,-1],[0,-1,0]])
    second_deri_img = cv2.filter2D(img, -1, second_deri)
    # print(normalize(second_deri_img))
    cv2.imwrite("./question3/second_derivative_img.jpg", second_deri_img);

def question4():
    img  = cv2.cvtColor(cv2.imread('./orig_images/pic.jpg'), cv2.COLOR_BGR2GRAY)

    #produce blur image by applying gaussian kernel
    box_size     = len(img) // 310
    box_filter   = produce_box_kernel(box_size)
    subtracted_img  = img - cv2.filter2D(img, -1, box_filter)

    #highboost operation
    highboost         = img + 2 * subtracted_img

    #unsharp operation
    unsharp           = img + subtracted_img
    cv2.imwrite("./question4/orig_img.jpg", img);
    cv2.imwrite("./question4/subtracted_img.jpg", subtracted_img);
    cv2.imwrite("./question4/highboost_img.jpg", highboost);
    cv2.imwrite("./question4/unsharp_img.jpg", unsharp);


def main():
    question1()
    question2()
    question3()
    question4()

if __name__ == "__main__":
    main()
