## step -1: install NTL library

### windows: 

https://www.shoup.net/ntl/doc/tour-win.html

### unix: 

https://www.shoup.net/ntl/doc/tour-unix.html

## step 0: 

you need to set seiving parameter in input.txt

## step 1: 

run ```g++ -g -O2 -std=c++11 -pthread -march=native precompute.cpp -o generate_relation  -lntl -lgmp -lm```

## step 2: 

```run ./generate_relation```
