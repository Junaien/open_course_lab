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

#<input> img:   gray scale img
#<input> histo: histogram that you want to match
#<output>       image after apply exact histogram match
def exact_histo(img, histo):
  (rows, cols) = (len(img), len(img[0]))
  ret_img      = np.array(img, 'float32')
  w1_kernel = np.float32([1])
  w2_kernel = np.float32([[0,1/5,0], [1/5,1/5,1/5],[0,1/5,0]])
  w3_kernel = np.float32([[1/9,1/9,1/9], [1/9,1/9,1/9],[1/9,1/9,1/9]])
  img_w1 = cv2.filter2D(ret_img, -1, w1_kernel)
  img_w2 = cv2.filter2D(ret_img, -1, w2_kernel)
  img_w3 = cv2.filter2D(ret_img, -1, w3_kernel)
  filter_arr = []
  for i in range(rows):
      for j in range(cols):
          filter_arr.append((img_w1[i,j],img_w2[i,j],img_w3[i,j], i, j))
  filter_arr = sorted(filter_arr)
  k = 0
  for i in range(len(histo)):
      while(histo[i] > 0):
          ret_img[filter_arr[k][3],filter_arr[k][4]] = i
          histo[i] -= 1
          k += 1
  return ret_img.astype("int")

def main():
  underexposed = cv2.cvtColor(cv2.imread('./orig_images/underexposed.jpg'), cv2.COLOR_BGR2GRAY)
  overexposed  = cv2.cvtColor(cv2.imread('./orig_images/overexposed.jpg'), cv2.COLOR_BGR2GRAY)

  #compute uniform histogram for underexposed image
  under_size   = len(underexposed) * len(underexposed[0])
  histo_under  = [(under_size  - (under_size % 256)) / 256] * 256
  histo_under[255] += (under_size % 256)

  #compute uniform histogram for overexposed image
  over_size    = len(overexposed) * len(overexposed[0])
  histo_over   = [(over_size - (over_size % 256)) / 256] * 256
  histo_over[255]  += (over_size % 256)

  equal_under  = exact_histo(underexposed, histo_under)
  equal_over  = exact_histo(overexposed, histo_over)
  cv2.imwrite("equal_underexposed.jpg", equal_under);
  cv2.imwrite("equal_overexposed.jpg", equal_over);
  plot_save(1,"orig_under","./orig_under.png",calHist(underexposed,256),'r',256)
  plot_save(2,"orig_over","./orig_over.png",calHist(overexposed,256),'g',256)
  plot_save(3,"equal_under","./equal_under.png",calHist(equal_under,256),'b',256)
  plot_save(4,"equal_over","./equal_over.png",calHist(equal_over,256),'k',256)

if __name__ == "__main__":
    main()

#question: what do you mean by comparing (1)(2)(3)
