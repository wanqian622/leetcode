// 8.28

/*
1 Search in Rotated Sorted Array
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

You are given a target value to search. If found in the array return its index, otherwise return -1.

You may assume no duplicate exists in the array.

Your algorithm's runtime complexity must be in the order of O(log n).

Example 1:

Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
Example 2:

Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1


Method1:
Data structure:
left: points to the most left boundary
right: points to the most right boundary
mid: the mid index in the range of [left, right]

Al:
Explanation
Let's say nums looks like this: [12, 13, 14, 15, 16, 17, 18, 19, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

Because it's not fully sorted, we can't do normal binary search. But here comes the trick:

If target is let's say 14, then we adjust nums to this, where "inf" means infinity:
[12, 13, 14, 15, 16, 17, 18, 19, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf]

If target is let's say 7, then we adjust nums to this:
[-inf, -inf, -inf, -inf, -inf, -inf, -inf, -inf, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]

And then we can simply do ordinary binary search.

Of course we don't actually adjust the whole array but instead adjust only on the fly only the elements we look at. And the adjustment is done by comparing both the target and the actual element against nums[0].

Code

If nums[mid] and target are "on the same side" of nums[0], we just take nums[mid]. Otherwise we use -infinity or +infinity as needed.


4,5,6,7,8,1,2,3
https://leetcode.com/problems/search-in-rotated-sorted-array/discuss/14435/Clever-idea-making-it-simple

*/

class Solution {
    public int search(int[] nums, int target) {
        if(nums == null || nums.length < 1){
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = left + (right-left)/2;

            int num = (nums[mid] < nums[left]) == (target < nums[left]) ? nums[mid] : target < nums[left] ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            if(num > target){
                right = mid - 1;
            }else if(num < target){
                left = mid + 1;
            }else{
                return  mid;
            }
        }

        return -1;

    }
}

/*
2 Longest Common Prefix
Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".

Example 1:

Input: ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.
Note:

All given inputs are in lowercase letters a-z.

method1:
Data structure:
res: store the the longest common prefix string from index 0 to index i in strs.

Algorithm:
we go through the input array, each time we compare String pairs whosse index is (i, i+1)  in strs in getLongestCommonPrefix(String s1, String s2);
helper function getLongestCommonPrefix(String s1, String s2) , get String s1 and String s2, return 
the longest common prefix string between those two input string. 
if there is no common, set res = "";
otherwise, if res.length() < the len of return value from helper function, update res;


time: O(nk) k is the averange len of strings in strs 
space: O(1) if we do not count subString
*/

public class Solution {
    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length < 1) {
            return "";
        }

        String res = strs[0];
        for(int i = 1; i < strs.length; i++){
            String tempPrefix = getLongestCommonPrefix(res, strs[i]);
            if(res.length() > tempPrefix.length()){
                res = tempPrefix;
                if(res.length() < 1){
                    return res;
                }
            }
        }

        return res;
    }

    private String getLongestCommonPrefix(String s1, String s2){
        if(s1.length() < 1 || s2.length() < 1){
            return "";
        }

        int i = 0;
        int j = 0;
        while(i < s1.length() && j < s2.length()){
            if(s1.charAt(i) == s2.charAt(j)){
                i++;
                j++;
            }else{
                return s1.substring(0, i);
            }
        }
        return s1.substring(0, i);
    }

}

// concise solution
/*
indexOf(String str)
Returns the index within this string of the first occurrence of the specified substring.
*/
public String longestCommonPrefix(String[] strs) {
    if(strs == null || strs.length == 0)    return "";
    String pre = strs[0];
    int i = 1;
    while(i < strs.length){
        while(strs[i].indexOf(pre) != 0)
            pre = pre.substring(0,pre.length()-1);
        i++;
    }
    return pre;
}





/*
3 Palindrome Number
Determine whether an integer is a palindrome. 
An integer is a palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121. 
From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

Coud you solve it without converting the integer to a string?


method1:
Data structure:
res: is to store the "reverse" digit when we split digit from end to start in input x. 
Algorithm:
there are two conditions:
1 if the input is negitive num, return false;
2 if not, 
    1 if the input x is palindrome, the reverse of x equals to x.(we need to consider overflow)
    2 otherwise, it is not;

time: O(n)
space: O(1)

method2: without checking overflow
just need to compare the first half and the rest.
Data structure:
rest: the result that each time we delete the hightest digit from the cur input
cur: to store the deleting hightest digit from the cur input

Algorithm:
each time we delete the hightest digit from the cur input, and cur is to store the deleting hightest digit from the cur input
, if the rest < cur, jump out the for loop, if rest == cur or cur/10 == rest, that means the input is palidrome

Time: O(n/2)
Space: O(1)

*/

// method1:
class Solution {
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }

        int input = Math.abs(x);
        int cur = input;
        int res = 0;

        while(cur > 0){
            // check whether it is overflow
            int prev = res;
            res = res*10 + cur%10;
            int temp = (res - cur%10)/10;

            if(prev != temp){
                return false;
            }
            cur/= 10;
        }

        return x == res;

    }
}
// method2:
class Solution {
    public boolean isPalindrome(int x) {
        // avoid input is 10, 100, 1000...., keypoint
        if (x<0 || (x!=0 && x%10==0)) return false;

        int input = Math.abs(x);
        int rest = input;
        int cur = 0;

        while(rest > cur){ // 
            cur = cur*10 + rest%10;
            rest = rest/10;
        }

        return (rest == cur || rest == cur/10);
        
    }
}

/*
4 Reverse Words in a String

Given an input string, reverse the string word by word.

Example:  

Input: "the sky is blue",
Output: "blue is sky the".
Note:

A word is defined as a sequence of non-space characters.
Input string may contain leading or trailing spaces. 
However, your reversed string should not contain leading or trailing spaces.
You need to reduce multiple spaces between two words to a single space in the reversed string.
Follow up: For C programmers, try to solve it in-place in O(1) space.

method1: Navie
converted array: convert the input s into String array accoding to space to split
left: points to the left of the converted array;
right: points to the right of the converted array;

Algorithm:
step1: convert the input s into String array accoding to space to split
step2: two pointer, one points to the left, another points to right, each time set elements in 
pointer left and pointer right; then left++, right--, untill left >= right
step3: for loop the converted array, compose each element with space, then return as string.


method2: two pointers without stringbuilder, split and trim
Data Structure:
charArray: we convert the input string into charArray.
(~,slow): are the res we wants
[slow, fast): ignore;
[fast, ~): not explore

Al:
step1: we convert s into charArray;
step2: dump the input charArray into right format, that is not contain leading or trailing spaces and
reduce multiple spaces between two words to a single space in the reversed string.
we use two pointers slow and fast
(~,slow): are the res we wants
[slow, fast): ignore;
[fast, ~): not explore
if array[fast] != ' ', we do not use swap, instead, we replace array[slow] = array[fast], so that we could
add space when we meet "the new word"; otherwise, we do not know the space is the original space or the switching
one.
step3: think about "I love google" --> "google love I"
reverse each word in the array that means those elements are seperated by space
reverse the whole array

time: O(n)
space:O(n)

*/
// method1
public class Solution {
    public String reverseWords(String s) {
              if(s == null){
            return s;
        }

        String[] sArray = s.split(" ");

        // spaw every left element and right element
        int left = 0;
        int right = sArray.length-1;
        while(left < right){
            if(sArray[left].equals("")){
                left++;
            }else if(sArray[right].equals("")){
                right--;
            }else {
                swap(sArray, left++, right--);
            }
        }

        // compose new String
        String res = "";
        for(int i = 0; i < sArray.length; i++){
            if(sArray[i].equals("")){
                continue;
            }
            res = res + " " + sArray[i];
        }
        return res.length() < 1 ? "" : res.substring(1, res.length());
    }

    private void swap(String[] array, int i, int j){
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        
    }
}
// concise solution, but, I think it does not work in interview
public String reverseWords(String s) {
    String[] words = s.trim().split(" +");
    Collections.reverse(Arrays.asList(words));
    return String.join(" ", words);
}

// method2
public class Solution {
    public String reverseWords(String s) {
        if(s == null){
            return s;
        }

        char[] array = s.toCharArray();
         /* step2
            (~,slow): are the res we wants
            [slow, fast): ignore;
            [fast, ~): not explore
         */
        int slow = 0;
        int fast = 0;

        while(fast < s.length()){
            if(array[fast] != ' '){
                if(slow != 0 &&fast != 0&& array[fast-1] == ' '){
                    array[slow++] = ' ';
                }
                array[slow++] = array[fast++];
//                swap(array, slow++, fast++);
            }else{
                fast++;
            }
        }

        // step3, once we get format array
        // the first reverse for each word in the array seperated by space
        // the second reverse for the whole array
        int i = 0;
        int j = 0;
        while(j < slow){
            while(j < slow && array[j] != ' '){
                j++;
            }
            reverse(array, i, j);
            j++;
            i = j;
        }
        reverse(array, 0, slow);

        return String.valueOf(array, 0, slow);
    }

    private void swap(char[] array, int i, int j){
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;

    }

    private void reverse(char[] array, int start, int end){
        int left = start;
        int right = end - 1;

        while(left < right){
            swap(array, left++, right--);
        }


    }
}

/*
5 Reverse Integer

Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment 
which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1]. 
For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.

method1:
Data structure:
res: is to store the "reverse" digit when we split digit from end to start in input x. 
prev: is to store the last res
Algorithm:
each time we split digit from end to start in input x and add the splited digit into res's current lowest digit.
the key pointer is how to check overflow. 
prev = res;
res = res*10+x%10;
if prev == (res-x%10)/10, that means not overflow; otherwise, overflow

time: O(n)
space: O(1)

*/

class Solution {
    public int reverse(int x) {
        if(x == Integer.MAX_VALUE || x == Integer.MIN_VALUE){
            return 0;
        }

        int input = x < 0 ? -x : x;
        int res = 0;
        int prev = 0;

        while(input > 0){
            prev = res;
            res = res*10 + input%10;
            if(prev != (res-input%10)/10){
                return 0;
            }
            input /= 10;
        }

        return x < 0 ? -res : res;
    }
}
