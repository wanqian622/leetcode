// diff : 3

/*
1 Remove Nth Node From End of List
Given a linked list, remove the n-th node from the end of list and return its head.

Example:

Given linked list: 1->2->3->4->5, and n = 2.

After removing the second node from the end, the linked list becomes 1->2->3->5.
Note:

Given n will always be valid.

Follow up:

Could you do this in one pass?


Asumption:
n is valid
singly linked list

DS:
slow pointer and fast pointer. there are kth eles between slow and fast.

Al:
step1: 
init slow = head and fast = head.
move fast pointer until there are kth eles between slow and fast.
step2:
move fast and slow pointer, until fast.next = null
at this time, slow.next is the node that we need to remove.

*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null || n == 0){
          return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        while(n > 0){
          fast = fast.next;
          n--;
        }

        // fast == null, that means the head we need to remove
        if(fast == null){
          return head.next;
        }

        while(fast.next != null){
          slow = slow.next;
          fast = fast.next;
        }

        slow.next = slow.next.next;
        return head;
    }
}


// same thought but better code. because my soluiton has too many conditions
// we can not ensure head, so we need dummy node
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null || n == 0){
          return head;
        }

        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode slow = dummyNode;
        ListNode fast = dummyNode;

        while(fast.next != null){
          if(n <= 0){
            slow = slow.next;
          }
          fast = fast.next;
          n--;
        }

        if(slow.next != null){
          slow.next = slow.next.next;
        }

        return dummyNode.next;
    }
}


/*
2 Top K Frequent Elements

Given a non-empty array of integers, return the k most frequent elements.

Example 1:

Input: nums = [1,1,1,2,2,3], k = 2
Output: [1,2]
Example 2:

Input: nums = [1], k = 1
Output: [1]
Note:

You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
Your algorithm's time complexity must be better than O(n log n), where n is the array's size.


method1: HashMap + min-heap
DS:
HashMap: is to store each Integer and the corresponding appearence
min-heap: is to store <key, value> pair in HashMap according to value to order

AL:
step1: for loop the input nums, put each Integer and the corresponding appearence into map
step2: store the first k pair of map into min-heap. for the rest n-k pair in map, if
the value of the pair in map > the peek value if the pair in min-heap, pop the peek pair, and push
the larger one into min-heap

Time: O(k +  (n-k)logk)
space: O(n)


method2: HashMap + quickselect
AL:
step1: for loop the input nums, put each Integer and the corresponding appearence into map
step2: run quick select(the same with finding the first kth element)

Time: Average for O(n), worst o(n^2)
space: O(n)


method3: Bucket sort
step1: review bucket sort first
*/

class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> res = new ArrayList<>();
        if(nums == null || nums.length < 1){
            return res;
        }

        // init map, store every ele and its appearence into map
        Map<Integer, Integer> map = new HashMap<>();
        for(int element : nums){
            map.put(element, map.getOrDefault(element, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> tempRes = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            tempRes.add(entry);
        }

        int l = 0;
        int r = tempRes.size()-1;

        while(l < r){
            int pivot = findKthElements(tempRes, l, r);
            if(pivot == k){
                break;
            }else if(pivot > k){
                r = pivot - 1;
            }else{
                l = pivot + 1;
            }
        }

        int index = 0;
        while(index < k){
            res.add(tempRes.get(index++).getKey());
        }
        return res;
    }

    private int findKthElements(List<Map.Entry<Integer, Integer>> tempRes, int start, int end){
        int randomIndex = start + (int)(Math.random() * (end-start + 1));
        swap(tempRes, randomIndex, end);
        int target = tempRes.get(end).getValue();
        int left = start;
        int right = end-1;

        while(left <= right){
            if(tempRes.get(left).getValue() < target){
                swap(tempRes, left, right--);
            }else if(tempRes.get(right).getValue() > target){
                swap(tempRes, left++, right);
            }else{
                left++;
                right--;
            }
        }

        swap(tempRes, left, end);
        return left;
    }

    private void swap(List<Map.Entry<Integer, Integer>> tempRes, int i, int j){
        Map.Entry<Integer, Integer> temp= tempRes.get(i);
        tempRes.set(i, tempRes.get(j));
        tempRes.set(j, temp);
        
    }
}


// bucket sort
public List<Integer> topKFrequent(int[] nums, int k) {

  List<Integer>[] bucket = new List[nums.length + 1];
  Map<Integer, Integer> frequencyMap = new HashMap<Integer, Integer>();

  for (int n : nums) {
    frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
  }

  for (int key : frequencyMap.keySet()) {
    int frequency = frequencyMap.get(key);
    if (bucket[frequency] == null) {
      bucket[frequency] = new ArrayList<>();
    }
    bucket[frequency].add(key);
  }

  List<Integer> res = new ArrayList<>();

  for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
    if (bucket[pos] != null) {
      res.addAll(bucket[pos]);
    }
  }
  return res;
}


/*
3 ZigZag Conversion
The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: 
(you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);
Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"
Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:

P     I    N
A   L S  I G
Y A   H R
P     I


method1:
DS:
StringBuilder[] sb: is to store each line

Al:
we go throught the input string, and fill out the sb array according to 
vertically down and obliquely up directions.
for vertically down direction, each sb[i], add one character
got obliquely up diretion, each sb[i] except sb[0] and sb[numRows-1]

sb[0]    P     I    N
sb[1]    A   L S  I G
sb[2]    Y A   H R
sb[3]    P     I

we can not store res according to each row.
like sb[0] store P, A, Y, P, it is wrong.


Time: O(n) --> n is the len of s
Space:O(n)

*/
class Solution {
    public String convert(String s, int numRows) {
        if(s == null || s.length() < 1 || s.length() < numRows){
            return s;
        }

        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < sb.length; i++) sb[i] = new StringBuilder();
        int len = s.length();

        // fill out sb array. idx represents the index of sb array
        // sb[idx] represent the ith stringbuilder in array sb
        // i is the ith character in s
        int i = 0;
        while(i < len){
            // for vertically down direction
            for(int idx = 0; idx < numRows && i < len; idx++){
                sb[idx].append(s.charAt(i++));
            }

            // for obliquely up diretion
            for(int idx = numRows-2; idx > 0 && i < len; idx--){
                sb[idx].append(s.charAt(i++));
            }
        }

        // sb[idx] represent the ith stringbuilder in array sb
        // combine all lines
        String res = "";
        for(int idx = 0; idx < numRows; idx++){
            res += sb[idx].toString();
        }
        return res;
    }
}

/*
4 Validate Binary Search Tree

Given a binary tree, determine if it is a valid binary search tree (BST).

Assume a BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
Example 1:

Input:
    2
   / \
  1   3
Output: true
Example 2:

    5
   / \
  1   4
     / \
    3   6
Output: false
Explanation: The input is: [5,1,4,null,null,3,6]. The root node's value
             is 5 but its right child's value is 4.


DS:
root: the current node when we go through the tree
max: all nodes value in cur subtree can not large than or equal to max
min: all nodes value in cur subtree can not smaller than or equal to min

private boolean ValidBST(TreeNode root, int max, int min)

AL:
each time we input the current subtree's root, and the max and the min boundary of this tree
base case:
if root == null, true
if root.value <= min || >= max, return false;
return ValidBST(root.left, min, root.value) && ValidBST(root.right, root.value, max)


time: O(n)
space: O(logn)



Key Point!!!
if we init min and max as Integer.MIN_VALUE and Integer.MAX_VALUE, how about [2147483647]???
so we need to init null.
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
    public boolean isValidBST(TreeNode root) {
      return verifyValidBST(root, null, null);  
    }

    private boolean verifyValidBST(TreeNode root, Integer min, Integer max){
    	if(root == null){
    		return true;
    	}

    	if((max != null && root.val >= max) || (min != null && root.val <= min)){
    		return false;
    	}


    	return verifyValidBST(root.left, min, root.val) && verifyValidBST(root.right, root.val, max);
    }
}
