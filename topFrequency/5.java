// 8.29 
/*
1  Trapping Rain Water

Given n non-negative integers representing an elevation map where the width of each bar is 1,
 compute how much water it is able to trap after raining.


The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. 
In this case, 6 units of rain water (blue section) are being trapped. 
Thanks Marcos for contributing this image!

Example:

Input: [0,1,0,2,1,0,1,3,2,1,2,1]
Output: 6
*/

class Solution {
    public int trap(int[] height) {
        
    }
}


/*
2 Integer to Roman
Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, two is written as II in Roman numeral, 
ust two one's added together. Twelve is written as, XII, which is simply X + II. The number twenty seven is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. 
However, the numeral for four is not IIII. Instead, the number four is written as IV. 
Because the one is before the five we subtract it making four. 
The same principle applies to the number nine, which is written as IX. 
There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9. 
X can be placed before L (50) and C (100) to make 40 and 90. 
C can be placed before D (500) and M (1000) to make 400 and 900.
Given an integer, convert it to a roman numeral. 
Input is guaranteed to be within the range from 1 to 3999.

Example 1:

Input: 3
Output: "III"
Example 2:

Input: 4
Output: "IV"
Example 3:

Input: 9
Output: "IX"
Example 4:

Input: 58
Output: "LVIII"
Explanation: C = 100, L = 50, XXX = 30 and III = 3.
Example 5:

Input: 1994
Output: "MCMXCIV"
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.



Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000

DS:
1-9 : I II III IV V VI VII VIII IX
10-90: X XX XXX XL L LX LXX LXXX XC 
100-900: C CC CCC CD D DC DCC DCCC CM
1000-3000: M MM MMM
digit: record Number of digits
res: return our result



*/
class Solution {
    public String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];

    }
}

/*
3 Merge Sorted Array
Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.

Note:

The number of elements initialized in nums1 and nums2 are m and n respectively.
You may assume that nums1 has enough space 
(size that is greater or equal to m + n) to hold additional elements from nums2.
Example:

Input:
nums1 = [2,5,6,0,0,0], m = 3
nums2 = [1,2,3],       n = 3

Output: [1,2,2,3,5,6]

we can not start in ascending order.
normally, if we merge two sorted array, we need an extra space composing m+n space, then 谁小移动谁 in
nums1 and nums2.

however, if we use the same way, if we both start at beginning, for the first pos, if we set 1 in nums2 
to the first pos in nums1, how about 2 in nums1?

so, what we can do is to start from the end in nums1 and nums2, we should do 谁大移动谁～
nums1 = [2,5,6,0,0,6]
           i     k
nums2 = [1,2,3],       
             j
*/

class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m-1;
        int j = n-1;
        int k = m+n-1;

        while(i >= 0 || j >= 0){
            if(i < 0){
                nums1[k--] = nums2[j--];
            }else if(j < 0){
                nums1[k--] = nums1[i--];
            }else{
                if(nums1[i] >= nums2[j]){
                    nums1[k--] = nums1[i--];
                }else{
                    nums1[k--] = nums2[j--];
                }
            }
        }
        
    }
}

/*
4 Decode Ways
A message containing letters from A-Z is being encoded to numbers using the following mapping:

'A' -> 1
'B' -> 2
...
'Z' -> 26
Given a non-empty string containing only digits, 
determine the total number of ways to decode it.
Example 1:

Input: "12"
Output: 2
Explanation: It could be decoded as "AB" (1 2) or "L" (12).
Example 2:

Input: "226"
Output: 3
Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).


DS:
M[i] represents the total number of ways of decode digits from index 0 to i in the input s. 

Al:
we use dp to solve this problem.
M[0] = if s[0] not in 1 to 9, return 0; otherwise set to 1
M[i] = M[i-1] where (s[i] in range 1 to 9) + M[i-2] where(s[i-1] in range 1 and s[i] in range 0 to 9 and  in 2 s[i-1] in range 0 to 6)

eg: input = "226"

i = 0:
M[0] = (2)
M[0] = 1

i = 1:
M[1] = (2,2) + (22)
M[1] = M[0](where "2" in range 1 to 9)
M[1] += 1 (where s[i-1] = "2" and s[i] in range 0 to 6)

i = 2:
M[2] = (2,2,6) + (22,6) + (2, 26)
M[2] = M[1](where "6" in range 1 to 9)
M[2] += M[0](where s[i-1] = "2" and s[i] in range 0 to 6)



for using less space, we need to consider:
the init of p and pp;
the start of i;
*/

class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() < 1){
            return 0;
        }

        // M[i] represents the total number of ways of decode digits from index 0 to i in the input s.
        // 100
        int[] M = new int[s.length()];

        for(int i = 0; i < M.length; i++){
            if(i == 0){
                if(s.charAt(i) - '0' > 0 && s.charAt(i) - '0' < 10){
                    M[i] = 1;
                }else{
                    return 0;
                }
            }else{
                if(s.charAt(i) - '0' > 0 && s.charAt(i) - '0' < 10){
                    M[i] = M[i-1];
                }

                if((s.charAt(i-1) - '0' == 1 && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 9) || (s.charAt(i-1) - '0' == 2 && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 6)){
                    int temp = i > 1 ? M[i-2] : 1;
                    M[i] += temp;
                }
            }
        }

        return M[s.length()-1];
    }
}

/*
Given a non-empty string containing only digits, 
determine the total number of ways to decode it.

*/

class Solution {
    public int numDecodings(String s) {
        if(s == null || s.length() < 1 || s.charAt(0) == '0'){
            return 0;
        }

        int p = 0;  // can not set 1 directly, 100
        int pp = 0;

        for(int i = 0; i < s.length(); i++){
            int cur = 0;

            if(s.charAt(i) - '0' > 0 && s.charAt(i) - '0' < 10){
                p = i == 0 ? 1 : p;
                cur = p;
            }
            if(i > 0 && (s.charAt(i-1) - '0' == 1 || (s.charAt(i-1) - '0' == 2 && s.charAt(i) - '0' >= 0 && s.charAt(i) - '0' <= 6))){

                pp = i > 0 ? pp : 1;
                cur += pp;
            }

            pp = p;
            p = cur;
        }

        return p;
    }
}

// if we start from the end , we could avoid some conditions

class Solution {
    public int numDecodings(String s) {
               if(s == null || s.length() < 1 || s.charAt(0) == '0'){
            return 0;
        }

        int p = 1;  // can not set 1 directly, 100
        int pp = 0;
        int n = s.length();
        for(int i = n - 1; i >= 0; i--){
            int cur = s.charAt(i) == '0' ? 0 : p;

            if(i < n-1 && (s.charAt(i) - '0' == 1 || (s.charAt(i) - '0' == 2 && s.charAt(i+1) - '0' >= 0 && s.charAt(i+1) - '0' <= 6))){
                cur += pp;
            }

            pp = p;
            p = cur;
        }

        return p;
    }
}

/*
5 Next Closest Time

Given a time represented in the format "HH:MM", 
form the next closest time by reusing the current digits. 
There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid. 
For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.

Example 1:

Input: "19:34"
Output: "19:39"
Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, 
which occurs 5 minutes later.  It is not 19:33, because this occurs 23 hours and 59 minutes later.
Example 2:

Input: "23:59"
Output: "22:22"
Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22. 
It may be assumed that the returned time is next day's time since it is smaller than the input time numerically.

*/

class Solution {
    public String nextClosestTime(String time) {
        
    }
}

/*
6 Serialize and Deserialize Binary Tree

Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

Example: 

You may serialize the following tree:

    1
   / \
  2   3
     / \
    4   5

as "[1,2,3,null,null,4,5]"
Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format, so please be creative and come up with different approaches yourself.

Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

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
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));