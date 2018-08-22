// 8.20
/*
1  Add Two Numbers

You are given two non-empty linked lists representing two non-negative integers. 
The digits are stored in reverse order and each of their nodes contain a single digit
Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example:

Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8
Explanation: 342 + 465 = 807.


method1: we do not build new ListNode for each sum
step1: we need a helper function to determin which one is longer, because we use the longer linkedlist as our res
step2: after we know which one is longer, say, curL and curS. we for loop the input
linkedlist, and a local var carry to store carry bit. if curS is not null, 
our sum = (curS.val + curL.val + carry)%10, carry = (curS.val + curL.val + carry)/10,
then set curL.val = sum, we move the next;
if curS is null, and carry is 1, 
our sum = (curL.val + carry)%10, carry = (curL.val + carry)/10,
then set curL.val = sum, we move the next;
otherwise, we break, return dummyNode.next;
step3: finally, if curL is null but carry is 1, we need to add a new listNode(1).

time: O(n)
space:O(1)

method2:  we do build new ListNode for each sum

*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

//method1
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null || l2 == null){
            return l1 == null ? l2 : l1;
        }

        ListNode dummyNode = new ListNode(0);
        ListNode dummy = dummyNode;
        int carry = 0;
        int flag = longer(l1, l2);
        ListNode curS = null;
        ListNode curL = null;
        if(flag == 1){
            curL = l1;
            curS = l2;
        }else{
            curL = l2;
            curS = l1;
        }
        dummy.next = curL;
    
        while(curL != null){
            if(curS != null){
                int tempValue1 = (curL.val + curS.val + carry)%10;
                carry = (curL.val + curS.val + carry)/10;
                curL.val = tempValue1;
                dummy = dummy.next;
                curL = curL.next;
                curS = curS.next;
            }else if(carry == 1){
                int tempValue2 = (curL.val +carry)%10;
                carry = (curL.val +carry)/10;
                curL.val = tempValue2;
                dummy = dummy.next;
                curL = curL.next;
            }else{
                break;
            }
        }

        if(carry == 1){
            dummy.next = new ListNode(1);
            dummy = dummy.next;
            dummy.next = null;
        }
        return dummyNode.next;
    }


    private int longer(ListNode l1, ListNode l2){
        ListNode cur1 = l1;
        ListNode cur2 = l2;

        while(cur1 != null && cur2 != null){
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        return cur2 == null ? 1 : 2;
        
    }
}

// method2
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if(l1 == null || l2 == null){
            return l1 == null ? l2 : l1;
        }

        ListNode dummyNode = new ListNode(0);
        ListNode dummy = dummyNode;
        ListNode c1 = l1;
        ListNode c2 = l2;
        int sum = 0;

        while(c1 != null || c2 != null){
            sum /= 10 ; // carry
            if(c1 != null){
                sum += c1.val;
                c1 = c1.next;
            }

            if(c2 != null){
                sum += c2.val;
                c2 = c2.next;
            }
            dummy.next = new ListNode(sum%10);
            dummy = dummy.next;
        }

        if(sum/10 == 1){
            dummy.next = new ListNode(1);
        }
        return dummyNode.next;
        
    }
}


/*
2  Longest Substring Without Repeating Characters

Given a string, find the length of the longest substring without repeating characters.

Example 1:

Input: "abcabcbb"
Output: 3 
Explanation: The answer is "abc", which the length is 3.
Example 2:

Input: "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.
Example 3:

Input: "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3. 
             Note that the answer must be a substring, "pwke" is a subsequence and not a substring.

method1: two pointer + map
map --> <ele,index>
we go through the input, if there are no dup, we put each ele with its index into map;
if there are dup, wo get the prev dup and its index, start from index+1

the basic idea is, keep a hashmap which stores the characters in string as keys and their positions as values, 
and keep two pointers which define the max substring. 
move the right pointer to scan through the string , 
and meanwhile update the hashmap.
 If the character is already in the hashmap, 
 then move the left pointer to the right of the same character last found. 
 Note that the two pointers can only move forward.

 time: O(n)
 space: O(n)
*/

class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() < 2){
            return s == null ? 0 : s.length();
        }

        Map<Character, Integer> map = new HashMap<>();
        int start = 0;
        int end = 0;
        int maxLen = 0;

        while(end < s.length()){
            Integer value = map.get(s.charAt(end));
            if(value != null){
                maxLen = Math.max(maxLen, end-start);
                // abba
                start = Math.max(value + 1, start);
            }
            map.put(s.charAt(end), end);
            end++;
        }
        maxLen = Math.max(maxLen, end-start);
        return maxLen;
        
    }
}

// better code with same thought
 public int lengthOfLongestSubstring(String s) {
        if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max=0;
        for (int i=0, j=0; i<s.length(); ++i){
            if (map.containsKey(s.charAt(i))){
                j = Math.max(j,map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i-j+1);
        }
        return max;
    }

/*
3 Group Anagrams
Given an array of strings, group anagrams together.

Example:

Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
Output:
[
  ["ate","eat","tea"],
  ["nat","tan"],
  ["bat"]
]
Note:

All inputs will be in lowercase.
The order of your output does not matter.

Assumptions:
input is null or len < 1?
uppercase or lowercase ?

*/

class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> res = new ArrayList<>();
        if(strs == null || strs.length < 1){
            return res;
        }

        Map<String, List<String>> map = new HashMap<>();

        for(String str : strs){
            char[] tempArray =str.toCharArray();
            Arrays.sort(str.toCharArray());
            String convert = tempArray.toString();
            List<String> list= map.get(convert);
            if(list == null){
                list = new ArrayList<>();
            }
            list.add(str);
            map.put(convert, list);
        }

        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            res.add(entry.getValue());
        }

        return res;    
    }
}

// better solution
    public List<List<String>> groupAnagrams(String[] strs) {
        int[] prime = new int[26];
        int cur = 2;
        for(int i = 0; i < 26; i++){
          while(!isPrime(cur)){
            cur++;  
          }
          prime[i] = cur;
          cur++;
        }
        
        List<List<String>> res = new ArrayList<List<String>>();
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(String str: strs){
           int hashcode = 1;
           for(char c: str.toCharArray()){
             hashcode *= prime[c - 'a'];
           }
           if(map.containsKey(hashcode)){
             List<String> list = res.get(map.get(hashcode));
             list.add(str);
           } else {
             List<String> list = new ArrayList<String>();
             list.add(str);
             res.add(list);
             map.put(hashcode, res.size() - 1);
           }
        }
        return res;
    }
    
    boolean isPrime(int n){
      for(int i = 2; i <= Math.sqrt(n); i++){
        if(n % i == 0) return false;  
      } 
        return true;
    }

/*
4  Longest Palindromic Substring

Given a string s, find the longest palindromic substring in s. 
You may assume that the maximum length of s is 1000.

Example 1:

Input: "babad"
Output: "bab"
Note: "aba" is also a valid answer.
Example 2:

Input: "cbbd"
Output: "bb"

it seems dp not work~~~ try other ways

for each possible position, we try to extend Palindrome as possible
*/

class Solution {
	private int lo;
	private int maxLen;
    public String longestPalindrome(String s) {
    	int len = s.length();
        if(len < 2){
        	return s;
        }

        // babad
        // cbbd
        for(int i = 0; i < len; i++){
        	extendPalindrome(s, i ,i);//assume odd length, try to extend Palindrome as possible
        	extendPalindrome(s, i ,i+1);  //assume even length.
        }

        return s.substring(lo, lo+maxLen);
    }

    private void extendPalindrome(String s, int left, int right){
    	while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
    		left--;
    		right++;
    	}

    	if(maxLen < right-left - 1){
    		lo = left + 1;
    		maxLen = right-left - 1;
    	}
    }
}

// without global var
public String longestPalindrome(String s) {
        int[] maxStart = new int[1], maxEnd = new int[1]; // use array in order to pass by reference instead of pass by value
        
        for (int i = 0; i < s.length()-1; i++) {
            extend(s, i, i, maxStart, maxEnd);    
            extend(s, i, i+1, maxStart, maxEnd);
        }
        
        return s.substring(maxStart[0], maxEnd[0]+1);
    }
    
    private void extend(String s, int i, int j, int[] maxStart, int[] maxEnd) {
        // loop until meet invalid match
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            i--; j++; 
        }
        
        i++; j--; // back to the last valid match
        
        if (j - i + 1 > maxEnd[0] - maxStart[0] + 1) {
            maxStart[0] = i;
            maxEnd[0] = j;
        }
        
    }