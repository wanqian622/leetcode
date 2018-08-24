// 8.23
/*
1 Merge Two Sorted Lists

Merge two sorted linked lists and return it as a new list. 
The new list should be made by splicing together the nodes of the first two lists.

Example:

Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4


method1: iterative
because we can not gurantee the head, so we need a dummy node
time: O(n + m)
space: O(1)

method2: recursion
time:O(n+m)
space: O(min(n,m))
*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

// iterative
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // corner case
      if(l1 == null || l2 == null){
        return l1 == null ? l2 : l1;
      }

      // set dummy node
      ListNode dummyNode = new ListNode(0);
      ListNode dummy = dummyNode;

      // there are three conditions
      // if l1 is null, dummy.next = l2, return dummyNode.next
      // if l2 is null, dummy.next = l1, return dummyNode.next;
      // if none of them, compare which one's value is smaller
      // dummy.next = the smaller one node, move to next
      while(l1 != null || l2 != null){
        if(l1 == null){
          dummy.next = l2;
          return dummyNode.next;
        }else if(l2 == null){
          dummy.next = l1;
          return dummyNode.next;
        }else{
          if(l1.val <= l2.val){
            dummy.next = l1;
            dummy = dummy.next;
            l1 = l1.next;
          }else{
            dummy.next = l2;
            dummy = dummy.next;
            l2 = l2.next;
          }
        }
      }

      dummy.next = null;
      return dummyNode.next;

    }
}

// recursion
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      // base case
      if(l1 == null){
        return l2;
      }

      if(l2 == null){
        return l2;
      }

      if(l1.val <= l2.val){
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
      }else{
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;       
      }

    }
}

/*
2 Best Time to Buy and Sell Stock

Say you have an array for which the ith element is the price of a given stock on day i.

If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock), 
design an algorithm to find the maximum profit.

Note that you cannot sell a stock before you buy one.

Example 1:

Input: [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
             Not 7-1 = 6, as selling price needs to be larger than buying price.
Example 2:

Input: [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.

method1:
we use var maxProfit as our return res;
use var min as the price that we buy;
for loop the pirces,
there are two cinditions:
when cur price as buy price:
if cur price < min, we use the cur price as min
when cur price as sell price:
if cur price - min > maxProfit, update maxProfit


method2: Kadane's Algorithm
I think I do this problem several times, but still can not get this way
The logic to solve this problem is same as "max subarray problem" using Kadane's Algorithm. Since no body has mentioned this so far, I thought it's a good thing for everybody to know.

All the straight forward solution should work, but if the interviewer twists the question slightly by giving the difference array of prices, Ex: for {1, 7, 4, 11}, if he gives {0, 6, -3, 7}, you might end up being confused.

Here, the logic is to calculate the difference (maxCur += prices[i] - prices[i-1]) of the original array, and find a contiguous subarray giving maximum profit. If the difference falls below 0, reset it to zero.

*maxCur = current maximum value

*maxSoFar = maximum value found so far

*/

class Solution {
    public int maxProfit(int[] prices) {
        // corner case
        if(prices == null || prices.length < 2){
            return 0;
        }

        int maxProfit = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(int i = 0; i < prices.length; i++){
            if(min > prices[i]){
                min = prices[i];
            }else if(prices[i] - min > maxProfit){
                maxProfit = prices[i] - min;
            }
        }

        return maxProfit < 0 ? 0 : maxProfit;
    }
}

// method2
   public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        for(int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
    }

/*
3 Product of Array Except Self

Given an array nums of n integers where n > 1,  
return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? 
(The output array does not count as extra space for the purpose of space complexity analysis.)

method1:
there are two process:
array res is our return res
step1:
for loop from start to end
res[0] = 1
res[1] = res[0]*nums[0]
res[2] = res[1]*nums[1] = 1*nums[0]*nums[1]
res[3] = res[2] * nums[2] = 1*nums[0]*nums[1]*nums[2]
.
.
res[i] = nums[i-1]*res[i-1] 
.
.
for the last one: res[n] = res[n-1]*nums[n-1] = 1*nums[0]*nums[1]*nums[2]...*nums[n-2]*nums[n-1]
step2:

input  1 2 3 4

res.  1  1  2 6
output24 12 8 6
     -----------
      24 12 4 1

for loop from end to start
init right = 1
we could see res[3] is the res we want
right = nums[i] = 4

res[2] = res[2] * right = 8
right = nums[i]*right = 12

res[1] = res[1]*right = 12
right = nums[i]*right = 24

*/

class Solution {
  public int[] productExceptSelf(int[] nums) {
    if(nums == null || nums.length < 1){
      return nums;
    }

    int[] res = new int[nums.length];

    // the first process
    res[0] = 1;
    for(int i = 1; i < nums.length; i++){
      res[i] = res[i-1]*nums[i-1];
    }

    // the second process
    int right = 1;
    for(int i = nums.length-1; i >= 0; i--){
      res[i] *= right;
      right *= nums[i];
    }

    return res;
  }
}

/*
For those who find it confusing :
The two iterations are basically doing the exact same thing, 
except one starts from 0 and one starts from end. Also, 
the order of the iteration does not matter, the second for loop can go first.
*/

public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        
        for (int i = 0; i < res.length; i++)
            res[i] = 1; // use 1 instead of 0 so it can be multipled
        
        
        int left = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] *= left;
            left *= nums[i];
        }
        
        int right = 1;
        for (int i = nums.length-1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        
        return res;
    }

/*
4  Lowest Common Ancestor of a Binary Tree

Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.

According to the definition of LCA on Wikipedia:
 “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”

Given the following binary tree:  root = [3,5,1,6,2,0,8,null,null,7,4]

        _______3______
       /              \
    ___5__          ___1__
   /      \        /      \
   6      _2       0       8
         /  \
         7   4
Example 1:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
Output: 3
Explanation: The LCA of of nodes 5 and 1 is 3.
Example 2:

Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
Output: 5
Explanation: The LCA of nodes 5 and 4 is 5, since a node can be a descendant of itself
             according to the LCA definition.
Note:

All of the nodes' values will be unique.
p and q are different and both values will exist in the binary tree.


method1: recursion
the child node will report the matched node, if no matched , report null;
the parent will get matched nodes from two children, if both nodes are not null, return the
curent parent; if parent is the matched node, at the same time, one of reported nodes are not null, return 
the parent node; if parent is the matched node, at the same time, both of reported nodes are  null, return 
the parent node; if all are null, return null 

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
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if(root == null || root.val == p.val || root.val == q.val){
      return root;
    }

    TreeNode curLeft = lowestCommonAncestor(root.left, p, q);
    TreeNode curRight = lowestCommonAncestor(root.right, p, q);

    if(curLeft != null && curRight != null){
      return root;
    }else if(curLeft == null){
      return curRight;
    }else if (curRight == null){
      return curLeft;
    }else{
      return null;
    }
    }
}