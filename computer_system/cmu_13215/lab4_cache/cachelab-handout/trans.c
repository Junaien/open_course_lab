/*
 * trans.c - Matrix transpose B = A^T
 *
 * Each transpose function must have a prototype of the form:
 * void trans(int M, int N, int A[N][M], int B[M][N]);
 *
 * A transpose function is evaluated by counting the number of misses
 * on a 1KB direct mapped cache with a block size of 32 bytes.
 */
#include <stdio.h>
#include "cachelab.h"
#include <assert.h>
#define MIN(a,b) (a <= b? a:b)
int is_transpose(int M, int N, int A[N][M], int B[M][N]);
void transpose_MN_MN(int M, int N, int src[M][N], int s_i, int s_j,
                                   int t[M][N],   int t_i, int t_j, int blk_size);
void trans(int M, int N, int A[N][M], int B[M][N], int blk_size);
void transpose_NM_MN(int M, int N, int src[N][M], int s_i, int s_j,
                             int t[M][N],   int t_i, int t_j, int blk_size);
void trans_blk(int M, int N, int A[N][M], int B[M][N]);
void flat_trans(int M, int N, int src[N][M], int s_i, int s_j,
                             int t[M][N], int t_i, int t_j, int blk_size);
/*
 * transpose_submit - This is the solution transpose function that you
 *     will be graded on for Part B of the assignment. Do not change
 *     the description string "Transpose submission", as the driver
 *     searches for that string to identify the transpose function to
 *     be graded.
 */
char transpose_submit_desc[] = "Transpose submission";
void transpose_submit(int M, int N, int A[N][M], int B[M][N])
{
  if(M == 32)trans(M,N,A,B,8);
  else if(M == 64)trans(M,N,A,B,8);
  else trans_blk(M,N,A,B);
}

/*
 * You can define additional transpose functions below. We've defined
 * a simple one below to help you get started.
 */

/*
 * trans - A simple baseline transpose function, not optimized for the cache.
 */
char trans_desc[] = "matrix shift tecnique";
void trans(int M, int N, int A[N][M], int B[M][N], int blk_size)
{

    int effective_n = blk_size * MIN(N / blk_size, M / blk_size);
    for(int i = 0; i < effective_n; i+= blk_size){
        int j = i;
        //copy to the right block
        for(int k = i; k < i + blk_size; k++){
          for(int z = j; z < j + blk_size; z++){
            B[k][(z + blk_size) % effective_n] = A[k][z];
          }
        }
        //traspose back
        transpose_MN_MN(M, N, B, i, (j + blk_size) % effective_n, B, i, j, blk_size);
        // assert traspose back
        // for(int k = 0; k < blk_size; k++)
        //   for(int z = 0; z < blk_size; z++){
        //     assert(A[i + k][z + j] == B[z+j][i+k]);
        //   }
        //tranpose below to right
        int belw_base_i  = (i + blk_size) % effective_n ;
        int right_base_j = (blk_size + j) % effective_n;
        transpose_NM_MN(M,N,A,belw_base_i, j, B, i, right_base_j, blk_size);
    }

    //regular transpose
    for(int j = 0; j < N; j+= blk_size)
      for(int i = 0; i < M; i+= blk_size){
        //transposing blocks
        if((i == j || (j + blk_size) % effective_n == i) &&
           (i < effective_n && j < effective_n))continue;
        if(i + blk_size > N || j + blk_size > M){
            for(int k = i; k < N && (k - i) < blk_size; k++){
              for(int z = j; j < M && (z - j) < blk_size; z++){
                B[z][k] = A[k][z];
              }
            }
        }else{
          //optimize for blocks who can use the space to the right of it
          if(i + blk_size != j && i + blk_size != effective_n && i + blk_size != j){
            flat_trans(M, N, A, i, j, B, j, i, blk_size);
          }else{
            transpose_NM_MN(M,N,A,i, j, B, j, i, blk_size);
          }
        }
      }
}

char trans_blk_desc[] = "simple blocking";
void trans_blk(int M, int N, int A[N][M], int B[M][N])
{
    int blk_size = 16;
    for(int i = 0; i < N; i+= blk_size){
      for(int j = 0; j < M; j+= blk_size){
        for(int k = 0; k < blk_size; k++){
          for(int z = 0; z < blk_size; z++){
            if(j + z < M && i + k < N)B[j + z][i + k] = A[i + k][j + z];
          }
        }
      }
    }
}


/*
 * registerFunctions - This function registers your transpose
 *     functions with the driver.  At runtime, the driver will
 *     evaluate each of the registered functions and summarize their
 *     performance. This is a handy way to experiment with different
 *     transpose strategies.
 */
void registerFunctions()
{
    /* Register your solution function */
    registerTransFunction(transpose_submit, transpose_submit_desc);

    /* Register any additional transpose functions */
    // registerTransFunction(trans_blk, trans_blk_desc);
    // registerTransFunction(trans_col, trans_col_desc);
    // registerTransFunction(trans_blocking_4, trans_blocking_4_desc);

}

/*
 * is_transpose - This helper function checks if B is the transpose of
 *     A. You can check the correctness of your transpose by calling
 *     it before returning from the transpose function.
 */
int is_transpose(int M, int N, int A[N][M], int B[M][N])
{
    int i, j;

    for (i = 0; i < N; i++) {
        for (j = 0; j < M; ++j) {
            if (A[i][j] != B[j][i]) {
                return 0;
            }
        }
    }
    return 1;
}

//blk_size must be divisible by 2
void transpose_MN_MN(int M, int N, int src[M][N], int s_i, int s_j,
                             int t[M][N],   int t_i, int t_j, int blk_size){
  for(int d = 0; d < 4; d++){

    int shift_i   = 0,  shift_j = 0;
    if(d == 0 || d == 1){shift_i = blk_size / 2;}
    if(d == 2 || d == 1){shift_j = blk_size / 2;}
    for(int k = 0; k < blk_size / 2; k++){
      for(int z = 0; z < blk_size / 2; z++){
        t[t_i + shift_j + k][t_j + shift_i + z] = src[s_i + shift_i + z][s_j + shift_j + k];
      }
    }
  }
}

//blk_size must be divisible by 2
//this method transpose the matrix in 4x4 sub matrix
void transpose_NM_MN(int M, int N, int src[N][M], int s_i, int s_j,
                             int t[M][N], int t_i, int t_j, int blk_size){
  for(int d = 0; d < 4; d++){
    int shift_i   = 0,  shift_j = 0;
    if(d == 1 || d == 2){shift_i = blk_size / 2;}
    if(d == 3 || d == 2){shift_j = blk_size / 2;}
    for(int k = 0; k < blk_size / 2; k++){
      for(int z = 0; z < blk_size / 2; z++){
        t[t_i + shift_j + k][t_j + shift_i + z] = src[s_i + shift_i + z][s_j + shift_j + k];
      }
    }
  }
}

//this transform the matrix without subdividing
void transpose(int M, int N, int src[N][M], int s_i, int s_j,
                             int t[M][N], int t_i, int t_j, int blk_size){
  for(int k = 0; k < blk_size; k++){
   for(int z = 0; z < blk_size; z++){
     t[t_i + z][t_j + k] = src[s_i + k][s_j + z];
   }
  }
}
void swap_blk(int M, int N, int src[M][N], int s_i, int s_j,
                              int t[M][N], int t_i, int t_j, int blk_size){
    for(int i = 0; i < blk_size; i++){
      for(int j = 0; j < blk_size; j++){
        int temp = t[t_i + i][t_j + j];
        t[t_i + i][t_j + j] = src[s_i + i][s_j + j];
        src[s_i + i][s_j + j] = temp;
      }
    }
}

//flat transformation for 64X64 optimization
void flat_trans(int M, int N, int src[N][M], int s_i, int s_j,
                              int t[M][N], int t_i, int t_j, int blk_size){

  int half_blk_size = blk_size / 2;
  //flat copy and transpose
  transpose(M, N, src, s_i, s_j, t, t_i, t_j, half_blk_size);
  transpose(M, N, src, s_i, s_j + half_blk_size, t, t_i, t_j + half_blk_size, half_blk_size);
  //flat copy and transpose
  transpose(M, N, src, s_i + half_blk_size, s_j, t, t_i, t_j + blk_size, half_blk_size);
  transpose(M, N, src, s_i + half_blk_size, s_j + half_blk_size, t, t_i, t_j + 3 * half_blk_size, half_blk_size);

  //swap position 2 and 3
  swap_blk(M, N, t, t_i, t_j + blk_size, t, t_i, t_j + half_blk_size, half_blk_size);

  //shift flat blk to where it is supposed to be
  for(int i = 0; i < half_blk_size; i++){
    for(int j = 0; j < blk_size; j++){
      t[t_i + half_blk_size + i][t_j + j] = t[t_i + i][t_j + blk_size + j];
    }
  }
}
