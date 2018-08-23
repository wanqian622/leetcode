// 8.22
/*
1 3Sum
Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? 
Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

-4, -1, -1, 0, 1, 2


method1: sort
The idea is to sort an input array and then run through all indices of a possible first element of a triplet. 
For each possible first element we make a standard bi-directional 2Sum sweep of the remaining part of the array.
 Also we want to skip equal elements to avoid duplicates in the answer without making a set or smth like that.

time: O(n^2 + nlogn)
space: O(1)


*/
// method1
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
                List<List<Integer>> res = new ArrayList<>();
        // corner case
        if(nums == null || nums.length < 3){
          return res;
        }

        Arrays.sort(nums);

        for(int i = 0; i < nums.length - 2; i++){
          if(i != 0 && nums[i] == nums[i-1]){
            continue;
          }

          int left = i+1;
          int right = nums.length -1;
          int target = 0-nums[i];

          while(left < right){

            int sum = nums[left] + nums[right];
            if(sum == target){
              List<Integer> temp = new ArrayList<>();
              temp.add(nums[i]);
              temp.add(nums[left]);
              temp.add(nums[right]);
              res.add(temp);
              left++;
              right--;
            }else if(sum < target){
              left++;
            }else{
              right--;
            }
              // skip dup
            while(left < nums.length-1 && left != i + 1 &&nums[left] == nums[left-1]){
              left++;
             }

            while(right > left &&right != nums.length - 1&&nums[right] == nums[right+1]){
              right--;
            }
          }
        }

        return res;
        
    }
}

// better concise solution
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
                List<List<Integer>> res = new ArrayList<>();
        // corner case
        if(nums == null || nums.length < 3){
          return res;
        }

        Arrays.sort(nums);

        for(int i = 0; i < nums.length - 2; i++){
          if(i != 0 && nums[i] == nums[i-1]){
            continue;
          }

          int left = i+1;
          int right = nums.length -1;
          int target = 0-nums[i];

          while(left < right){

            int sum = nums[left] + nums[right];
            if(sum == target){
              List<Integer> temp = new ArrayList<>();
              temp.add(nums[i]);
              temp.add(nums[left]);
              temp.add(nums[right]);
              res.add(temp);
              while(left < right &&nums[left] == nums[left+1]){
                left++;
              }

              while(left < right &&nums[right] == nums[right-1]){
                right--;
              }
              left++;
              right--;
            }else if(sum < target){
              left++;
            }else{
              right--;
            }

          }
        }

        return res;
        
    }
}

/*
2 Rotate Image
You are given an n x n 2D matrix representing an image.

Rotate the image by 90 degrees (clockwise).

Note:

You have to rotate the image in-place, 
which means you have to modify the input 2D matrix directly. 
DO NOT allocate another 2D matrix and do the rotation.

Example 1:

Given input matrix = 
[
  [1,2,3],
  [4,5,6],
  [7,8,9]
],

rotate the input matrix in-place such that it becomes:
[
  [7,4,1],
  [8,5,2],
  [9,6,3]
]
Example 2:

Given input matrix =
[
  [ 5, 1, 9,11],
  [ 2, 4, 8,10],
  [13, 3, 6, 7],
  [15,14,12,16]
], 


[
  [ 11, 10, 7,5],
  [ 2, 4, 8, 1],
  [13, 3, 6, 9],
  [15,14,12,16]
],

[
  [ 16, 12, 14,5],
  [ 2, 4, 8, 1],
  [13, 3, 6, 9],
  [15,7,10,11]
], 

[
  [ 15, 13, 2,5],
  [ 14, 4, 8, 1],
  [12, 3, 6, 9],
  [16,7,10,11]
], 

rotate the input matrix in-place such that it becomes:
[
  [15,13, 2, 5],
  [14, 3, 4, 1],
  [12, 6, 8, 9],
  [16, 7,10,11]
]


method1: recursion
there are four directions:
L-R, T-B, R-L, B-T; 
the elements in L-R should be in T-B after rotating;
the elements in T-B should be in R-L after rotating;
the elements in R-L should be in B-T after rotating;
the elements in B-T should be in L-R after rotating;
we use the first row as the temp container, 
we firstly swap L-R and T-B, after that, eles in L-R are in T-B, 
eles in T-B are in L-R;
then, we need to move T-B to R-L, T-B is in L-R, so, we just need to
swap R-L with L-R;


time: O(n^2)
space: O(n/2)

method2:
The idea was firstly transpose the matrix and then flip it symmetrically. For instance,

1  2  3             
4  5  6
7  8  9
after transpose, it will be swap(matrix[i][j], matrix[j][i]) --> 270

1  4  7
2  5  8
3  6  9
Then flip the matrix horizontally. (swap(matrix[i][j], matrix[i][matrix.length-1-j]) --> 180

7  4  1
8  5  2
9  6  3

time: O(n^2)
space: O(1)

*/
//method1
class Solution {
    public void rotate(int[][] matrix) {
        if(matrix == null || matrix.length < 1){
            return;
        }
        getRotate(matrix, 0, matrix.length-1, 0, matrix[0].length - 1);
    }


    private void getRotate(int[][] matrix, int rowS, int rowE, int colS, int colE){
        if(rowS >= rowE || colS >= colE){
            return;
        }

        // colE, from top to bottom
        for(int i = rowS, j = colS; i < rowE && j < colE; i++, j++){
            swap(matrix, i, colE, rowS, j);
        }

        // rowE, right to left
        for(int i = colE, j = colS; i > colS && j < colE; i--, j++){
            swap(matrix, rowE, i, rowS, j);
        }

        // colS, bottom to top
        for(int i = rowE, j = colS; i > rowS && j < colE; i--, j++){
            swap(matrix, i, colS, rowS, j);
        }

        getRotate(matrix, rowS+1, rowE-1, colS + 1, colE - 1);

    }

    private void swap(int[][] matrix, int firstRow, int firstCol, int secondRow, int secondCol){
        int temp = matrix[firstRow][firstCol];
        matrix[firstRow][firstCol] = matrix[secondRow][secondCol];
        matrix[secondRow][secondCol] = temp;
    }
}


// methods2
public class Solution {
    public void rotate(int[][] matrix) {
      // corner case
      if(matrix == null || matrix.length < 0){
        return;
      }

      int rows = matrix.length;
      int cols = matrix[0].length;

      // flap 270 that is matrix[i][j] --> matrix[j][i]
      for(int i = 0; i <rows; i++){
        for(int j = i; j < cols; j++){
          int temp = matrix[i][j];
          matrix[i][j] = matrix[j][i];
          matrix[i][j] = temp;
        }
      }


      // flap 180 that is matrix[i][j] --> matrix[i][n-j-1]
      for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols/2; j++){
          int temp = matrix[i][j];
          matrix[i][j] = matrix[i][cols-j-1];
          matrix[i][cols-j-1] = matrix[i][j];
        }
      }

      return;
  
    }
}

// better and consice
/*
here is how it works~
the outer loop is to control the range that we need to rotate;
the inner loop is the index that we need to swap or rotate

*/

public class Solution {
    public void rotate(int[][] matrix) {
      // corner case
      if(matrix == null || matrix.length < 0){
        return;
      }

      int n = matrix.length;

      for(int i = 0; i < n/2; i++){
        for(int j = i; j < n-i-1; j++){
          // (left, top)
          int temp = matrix[i][j];
          // move (left, bottom) to (left, top)
          matrix[i][j] = matrix[n-j-1][i];
          // move(right, bottom) to (left, bottom)
          matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
          // move(right, top) to (right, bottom)
          matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
          // move(left, top) to (right top)
          matrix[j][n-i-1] = temp;
        }
      }
  
    }
}

/*
3  Set Matrix Zeroes
Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.

Example 1:

Input: 
[
  [1,1,1],
  [1,0,1],
  [1,1,1]
]
Output: 
[
  [1,0,1],
  [0,0,0],
  [1,0,1]
]
Example 2:

Input: 
[
  [0,1,2,0],
  [3,4,5,2],
  [1,3,1,5]
]
Output: 
[
  [0,0,0,0],
  [0,4,5,0],
  [0,3,1,0]
]
Follow up:

A straight forward solution using O(mn) space is probably a bad idea.
A simple improvement uses O(m + n) space, but still not the best solution.
Could you devise a constant space solution?

method1: O(m+n)
we for for loop the  matrix, and use two arrays to record which row and col should be
set 0

method2: better solution
we use the first row and the first col to record which row and col should be set 0;
step1: use two boolean flags to check we need to set the first col and row as 0; for loop
the first col and row, if there are 0, set corresponding flag as true;
step2: for for loop the input matrix, the first row is to record the col num, 
the first col is to record the row num,eg if matrix[i][j] = 0, that means matrix[0][j] = 0,
matrix[i][0] = 0.
step3: after that, we for loop the first row and col, if matrix[0][j] = 0, set the corresopnding col as 0;
if matrix[i][0] = 0, set the corresopnding row as 0;
*/
// [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
class Solution {
    public void setZeroes(int[][] matrix) {
    boolean fr = false,fc = false;

        // check whether we need to set the first col and row as 0
        // for for loop to check which col or row we need to set 0
    for(int i = 0; i < matrix.length; i++) {
        for(int j = 0; j < matrix[0].length; j++) {
            if(matrix[i][j] == 0) {
                if(i == 0) fr = true;
                if(j == 0) fc = true;
                matrix[0][j] = 0;
                matrix[i][0] = 0;
            }
        }
    }


        // set those col as 0 except the first col 
        // set those row as 0 except the first row 
    for(int i = 1; i < matrix.length; i++) {
        for(int j = 1; j < matrix[0].length; j++) {
            if(matrix[i][0] == 0 || matrix[0][j] == 0) {
                matrix[i][j] = 0;
            }
        }
    }
    if(fr) {
        for(int j = 0; j < matrix[0].length; j++) {
            matrix[0][j] = 0;
        }
    }
    if(fc) {
        for(int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 0;
        }
    }

    }
}

// better and concise code.... need to review

class Solution {
    public void setZeroes(int[][] matrix) {
      /*
 store states of each row in the first of that row, 
 and store states of each column in the first of that column. 
 Because the state of row0 and the state of column0 would occupy the same cell, 
 I let it be the state of row0, and use another variable "col0" for column0. 
 In the first phase, use matrix elements to set states in a top-down way. 
 In the second phase, use states to set matrix elements in a bottom-up way.
      */


      int col0 = 1; // the flag to mark whether we need to set the first col as zero
      int rows = matrix.length;
      int cols = matrix[0].length;


      // from 
      // set which col and row as zero
      for(int i = 0; i < rows; i++){
        // there are zero in first col, we need to set first col as 0 finally
        if(matrix[i][0] == 0){
          col0 = 0;
        }
        for(int j = 1; j < cols; j++){
          if(matrix[i][j] == 0){
            matrix[i][0] = 0;
            matrix[0][j] = 0;
          }
        }
      }

      for(int i = rows-1; i >= 0; i--){
        for(int j  = cols-1; j >=1; j--){
          if(matrix[i][0] == 0 || matrix[0][j] == 0){
            matrix[i][j] = 0;
          }
        }
        if(col0 == 0){
          matrix[i][0] = 0;
        }
      }

    }
}

/*
4 Subsets

Given a set of distinct integers, nums, return all possible subsets (the power set).

Note: The solution set must not contain duplicate subsets.

Example:

Input: nums = [1,2,3]
Output:
[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

method1: dfs
there are three levels, for each state ina al level, there are two chooses,
choose this element or not

time: O(2^n)
space: O(n)

method2:
No messy indexing. Avoid the ConcurrentModificationException by using a temp list.
https://leetcode.com/problems/subsets/discuss/27279/Simple-Java-Solution-with-For-Each-loops?page=2
*/



// method1
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if(nums == null || nums.length < 1){
          return res;
        }

        List<Integer> cur = new ArrayList<>();
        dfs(nums, res, cur, 0);
        return res;
    }

    private void dfs(int[] nums, List<List<Integer>> res, List<Integer> cur ,int level){
      // base case
      if(level == nums.length){
        res.add(new ArrayList<Integer>(cur));
        return;
      }

      cur.add(nums[level]);
      dfs(nums, res, cur, level + 1);
      cur.remove(cur.size() - 1);
      dfs(nums, res, cur, level + 1);

    }
}

// method2

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if(nums == null || nums.length < 1){
          return res;
        }

        res.add(new ArrayList<>());
        for(int i = 0; i < nums.length; i++){
          // we need to set this, because if we use res directly to add cur,
          // that means each time we add cur, res change, the inner loop is not allowed.
          // it will throw Exception in thread "main" java.util.ConcurrentModificationException 
          List<List<Integer>> tempRes = new ArrayList<>(); 
          for(List<Integer> tempList : res){
            List<Integer> cur = new ArrayList<>(tempList);
            cur.add(nums[i]);
            tempRes.add(cur);
          }
          res.addAll(tempRes);
        }

        return res;
    }
}