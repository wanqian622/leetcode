// 9.3

/*
1 Kth Largest Element in an Array(how to understand???)

Find the kth largest element in an unsorted array.
 Note that it is the kth largest element in the sorted order, not the kth distinct element.

Example 1:

Input: [3,2,1,5,6,4] and k = 2
Output: 5
Example 2:

Input: [3,2,3,1,2,4,5,5,6] and k = 4
Output: 4
Note: 
You may assume k is always valid, 1 ≤ k ≤ array's length.

Method1: sort
Method2: min/max heap --> klogk + (n-k)logk/ klogn+nlogn
Method3: quick select --> O(n) for avarage
*/

class Solution {
    public int findKthLargest(int[] nums, int k) {
        k = nums.length - k;
        int start = 0;
        int end = nums.length-1;
        while(start < end){
            int pivot = findKthLargest(nums, start, end);
            if(pivot == k){
                break;
            }else if(pivot < k){
                start = pivot + 1;
            }else{
                end = pivot -1;
            }
        }
        return nums[k];
    }


    private int findKthLargest(int[] nums, int start, int end){
        int randomIndex = start + (int)(Math.random() * (end-start+1));
        swap(nums, randomIndex, end);
        int left = start;
        int right = end-1;

        while(left <= right){
            if(nums[left] > nums[end]){
                swap(nums, left, right--);
            }else if(nums[right] < nums[end]){
                swap(nums, left++, right);
            }else{
                left++;
                right--;
            }
        }

        swap(nums, left, end);
        return left;
    }
    private void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}


 /*
2 Count Primes

Count the number of prime numbers less than a non-negative number, n.

Example:

Input: 10
Output: 4
Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.


DS:
boolean array, notPrime is to record whether num less than n is prime or not
count: count the num of primes which are less than n


AL:
the outer loop is to try every possible prime which is less than n;
the inner loop is to set all count*possiblePrime as not prime

time: O(n + n/2 + n/4 + .... 1) ~O(n)
space: O(1)
 */

class Solution {
  public int countPrimes(int n) {
    if(n < 2){
      return n;
    }

    boolean[] notPrime = new boolean[n];
    int count = 0;

    for(int i = 2; i < n; i++){
      if(!notPrime[i]){
        count++;
        for(int j = 2; j*i < n; j++){
          notPrime[j*i] = true;
        }
      }
    }

    return count;
        
  }
}



/*
3 Binary Tree Maximum Path Sum
Given a non-empty binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node 
to any node in the tree along the parent-child connections. 
The path must contain at least one node and does not need to go through the root.

Example 1:

Input: [1,2,3]

       1
      / \
     2   3

Output: 6
Example 2:

Input: [-10,9,20,null,null,15,7]

   -10
   / \
  9  20
    /  \
   15   7

Output: 42


DS:
root: the root of current subtree
int[] max: store the max path sum

AL:
the child node report the max sum to its parent;
for current parent, if both of child's sum < 0, return current parent's value;
otherwise return max(left, right) + parent's value;
at the same time update max

*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public int maxPathSum(TreeNode root) {
      int[] max = new int[]{Integer.MIN_VALUE};
      getMaxPathSum(root,max);
      return max[0];     
    }

    private int getMaxPathSum(TreeNode root, int[] max){
      if(root == null){
        return 0;
      }

      int left = getMaxPathSum(root.left, max);
      int right = getMaxPathSum(root.right, max);

      if(left > 0 && right > 0){
        max[0] = Math.max(max[0], left + right + root.val);
        return Math.max(left, right) + root.val;
      }

      int tempSum = Math.max(0, Math.max(left, right)) + root.val;
      max[0] = Math.max(max[0], tempSum);
      return tempSum;
    }
}
