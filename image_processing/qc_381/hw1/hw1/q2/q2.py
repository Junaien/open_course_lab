import cv2 
import numpy as np 
import matplotlib.pyplot as plt
from collections import Counter

def makeGrayImage(colorImg):
    height, width = (colorImg.shape[0], colorImg.shape[1])
    greyImg = np.zeros((height,width),'uint8')
    for y in range(height):
        for x in range(width):
            greyImg[y][x] = (int(colorImg[y][x][0]) +
                             int(colorImg[y][x][1]) +
                             int(colorImg[y][x][2])) / 3
    return greyImg

#Capture an image with a uniform background, then put an object on the scene and capture a second image of the scene.
def edgeDectection(img1,img2, threshold):
     height, width = np.shape(img1)
     s =  height, width

     obj = np.zeros(s, dtype = 'bool')
     diff = 0
     pixelCount = 0
     for y in range(height):
          for x in range(width):          
                diff = abs(img1[y][x] - img2[y][x])
                if diff > threshold:
                    obj[y][x] = 1  
                    pixelCount = pixelCount + 1
                else:
                    obj[y][x] = 0      
     return obj, pixelCount

def showHistogram(img):
        #img =cv2.imread(img)  
        height, width = np.shape(img)

        histogram = np.zeros(2)

        for y in range(height):
            for x in range(width):
                # retrival color value from x,y position. assuming the color is grayscale and ranged from 0 to 255
                if img[y][x]==1 : 
                    histogram[1]+=1 # count the same color frequency
                elif img[y][x]==0:
                    histogram[0]+=1 # count the same color frequency


        objects = ('0', '1')
        y_pos = np.arange(len(objects))
        frequency = histogram
 
        plt.bar(y_pos, frequency, align='center', alpha=0.5)
        plt.xticks(y_pos, objects)
        plt.ylabel('The number of pixel')
        plt.xlabel('Color')
        plt.title('Color Intensity for binary image')
        plt.show()

            
def main():
     colorImg1 = cv2.imread("img.jpg",cv2.IMREAD_COLOR)
     colorImg2 = cv2.imread("background.jpg",cv2.IMREAD_COLOR)

     greyImg1 = makeGrayImage(colorImg1)
     greyimg2 = makeGrayImage(colorImg2)

     threshold = 100
     binaryImg, pixelCount = edgeDectection(greyImg1,greyimg2, threshold)

     print('pixel count:' + str(pixelCount))
     showHistogram(binaryImg)

     plt.imshow(binaryImg, cmap = 'gray')
     #plt.xticks([]), plt.yticks([])  # hide x，y axis
     plt.show()


     cv2.waitKey(0)
     

if __name__ == "__main__":
    main()