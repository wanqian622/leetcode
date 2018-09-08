//9.6
/*
1 Diameter of Binary Tree
Given a binary tree, you need to compute the length of the diameter of the tree. 
The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
 This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.


DS:
diameter: the length of the longest path between any two nodes that we need to return
root: the current root in the current sub-tree


AL:
the child node reports the length of the longest path according to the child node as root;
the parent node select the length of the longest path from its right child node and left child node,
at the same update the length of the longest path according to the parent node.

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
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        int[] diameter = new int[]{Integer.MIN_VALUE};
        generateDiameterBinaryTree(root, diameter);
        return diameter[0]-1;

    }

    private int generateDiameterBinaryTree(TreeNode root, int[] diameter){
        if(root == null){
            return 0;
        }

        int left = generateDiameterBinaryTree(root.left, diameter);
        int right = generateDiameterBinaryTree(root.right, diameter);

        diameter[0] = Math.max(diameter[0], left + right + 1);

        return Math.max(left, right) + 1;
        
    }
}

/*
2  House Robber
You are a professional robber planning to rob houses along a street. 
Each house has a certain amount of money stashed, 
the only constraint stopping you from robbing each of them is 
that adjacent houses have security system connected and it will automatically contact the police 
if two adjacent houses were broken into on the same night.

Given a list of non-negative integers representing the amount of money of each house, 
determine the maximum amount of money you can rob tonight without alerting the police.

Example 1:

Input: [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
             Total amount you can rob = 1 + 3 = 4.
Example 2:

Input: [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
             Total amount you can rob = 2 + 9 + 1 = 12.


DS:
M[i]: represents the the maximum amount of money you can rob tonight from the first hourse to 
the current hourse.

AL:
M[i] = M[i-2] + nums[i]
      M[i-1]

*/
class Solution {
    public int rob(int[] nums) {
        if(nums == null || nums.length < 1){
            return 0;
        }

        int pp = 0;
        int p = 0;
        for(int i = 0; i < nums.length; i++){
            int cur = Math.max(p, pp+nums[i]);
            pp = p;
            p = cur;
        }
        return p;
    }
}            

/*
3 Plus Basic Calculator
Implement a basic calculator to evaluate a simple expression string.

The expression string may contain open ( and closing parentheses ), 
the plus + or minus sign -, non-negative integers and empty spaces .

Example 1:

Input: "1 + 1"
Output: 2
Example 2:

Input: " 2-1 + 2 "
Output: 3
Example 3:

Input: "(1+(4+5+2)-3)+(6+8)"
Output: 23
Note:
You may assume that the given expression is always valid.
Do not use the eval built-in library function.

Input:"2147483647

Simple iterative solution by identifying characters one by one. One important thing is that the input is valid, which means the parentheses are always paired and in order.
Only 5 possible input we need to pay attention:

digit: it should be one digit from the current number
'+': number is over, we can add the previous number and start a new number
'-': same as above
'(': push the previous result and the sign into the stack, set result to 0, just calculate the new result within the parenthesis.
')': pop out the top two numbers from stack, first one is the sign before this pair of parenthesis, second is the temporary result before this pair of parenthesis. We add them together.

            //Input: "(1+(4+5+2)-3)+(6+8)"
        // stack: 0, 1
        //stack: 0, 1, 1, 1, 
        // stack: 0, 1,                     result = 4+5+2+1, 
        // stack: 0, 1,                     result = 4+5+2+1-3 = 9,
        // stack: 0,1,9,1,                  result = 6+8 = 14
        // stack: 0,1                       result = 14+9 

*/

class Solution {
    public int calculate(String s) {

        int result = 0;
        int sign = 1;
        Deque<String> stack = new LinkedList<>();

        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                int sum = s.charAt(i) - '0';
                while(i+1 < s.length() && Character.isDigit(s.charAt(i+1))){
                    sum = sum*10 + s.charAt(i+1) - '0'
                    i++;
                }
                result += sum*sign;
            }

            if(s.charAt(i) == '+'){
                sign = 1;
            }

            if(s.charAt(i) == '-'){
                sign = -1;
            }

            if(s.charAt(i) == '('){
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            }

            if(s.charAt(i) == ')'){
                result = stack.pop() * result + stack.pop();
            }
        }

        return result;
  
    }
}

