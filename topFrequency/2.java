// 8.26
/*
1 Median of Two Sorted Arrays    
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5


difficulty: 
1 How to determine the terminal condition???
2 what if one of array is deleted?

it is the seem with this problem:
Find the kth element of Two Sorted Arrays

Data structure:
nums1Left: the beginning index of nums1
nums2Left: the beginning index of nums2
k: the kth element


Algorithm:
compare nums1[k/2-1] and nums2[k/2-1], which one is small, delete whose  k/2 elements 
0到k/2-1 = k/2

findKthEleSortedArrays(int[] nums1, int nums1Left, int[] nums2, int nums2Left, int k)

https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2471/Very-concise-O(log(min(MN)))-iterative-solution-with-detailed-explanation

*/

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
                	int nums1Len = nums1.length;
    	int nums2Len = nums2.length;

    	double res1 = findKthEleSortedArrays(nums1, 0, nums2, 0,(nums1.length + nums2.length)/2 + 1);
    	if((nums1Len + nums2Len)%2 == 0){
    		double res2 = findKthEleSortedArrays(nums1, 0, nums2, 0,(nums1.length + nums2.length)/2);
    		return (res1 + res2)/2.0 ;
    	}
        return res1;
    }

    private double findKthEleSortedArrays(int[] nums1, int nums1Left, int[] nums2, int nums2Left, int k){
    	if(nums1Left >= nums1.length){
    		return nums2[nums2Left + k - 1];
    	}

    	if(nums2Left >= nums2.length){
    		return nums1[nums1Left + k - 1];
    	}

    	if(k == 1){
    		return Math.min(nums1[nums1Left], nums2[nums2Left]);
    	}


    	int nums1Mid = nums1Left + k/2 - 1 >= nums1.length ? Integer.MAX_VALUE : nums1[nums1Left + k/2 - 1];
    	int nums2Mid = nums2Left + k/2 - 1 >= nums2.length ? Integer.MAX_VALUE : nums2[nums2Left + k/2 - 1];

    	if(nums1Mid > nums2Mid){
    		return findKthEleSortedArrays(nums1, nums1Left, nums2, nums2Left + k/2, k-k/2);
    	}
    	return findKthEleSortedArrays(nums1, nums1Left + k/2, nums2, nums2Left, k-k/2); // 250, 125, 125-125/2 = 63, even and odd
        
    }
}


/*
2 Container With Most Water
Given n non-negative integers a1, a2, ..., an , 
where each represents a point at coordinate (i, ai). 
n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). Find two lines,
 which together with x-axis forms a container, such that the container contains the most water.

Note: You may not slant the container and n is at least 2.

The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7].
 In this case, the max area of water (blue section) the container can contain is 49.


 method1: two pointer
 Data Structure:
 left: points to the left 
 right: points to the right
 maxArea: store the max area

 Algorithm:
 an area = (right-left) * Math.min(height[right], height[left])
 left points the left of input and right points the right of input;
 each time, maxArea = max((right-left) * Math.min(height[right], height[left]), maxArea), and move the
 one which has smaller height




Example:

Input: [1,8,6,2,5,4,8,3,7]
Output: 49
*/

class Solution {
    public int maxArea(int[] height) {
            if(height == null || height.length < 1){
                return 0;
            }

            int max_Area = Integer.MIN_VALUE;
            int left = 0;
            int right = height.length - 1;

            while(left < right){
                int tempArea = (right-left) * Math.min(height[right], height[left]);
                if(tempArea > max_Area){
                    max_Area = tempArea;
                }
                if(height[left] < height[right]){
                    left++;
                }else{
                    right--;
                }
            }

            return max_Area == Integer.MIN_VALUE ? 0 : max_Area;

        
    }
}

/*
3 Reverse Linked List
Reverse a singly linked list.

Example:

Input: 1->  2<-3<-4<-5
       h     n

Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?

Data structure:
iterative: 
cur: points to the current node
prev: points to the previous node of the cur node
next points to the next node of the current node


Algorithm:
recursion : reverseList(head) : get the reverse List according to the input

time: O(n)
space: O(n)

iterative:
next = cur.next
cur.next = prev
prev = cur
cur = next

time: O(n)
space: O(1)

*/

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

// recursion
class Solution {
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }   
        ListNode cur = head.next;
        ListNode newHead = reverseList(cur);
        cur.next = head;
        head.next = null;
        return newHead;
        
    }
}

// iterative
class Solution {
    public ListNode reverseList(ListNode head) {
    	if(head == null){
    		return null;
    	}

    	ListNode prev = null;
    	ListNode cur = head;
    	ListNode next = null;

    	while(cur != null){
    		next = cur.next;
    		cur.next = prev;
    		prev = cur;
    		cur = next;
    	}

    	return prev;     
    }
}

/*
4  Linked List Cycle
Given a linked list, determine if it has a cycle in it.

Follow up:
Can you solve it without using extra space?


Data structue:
slow: the pointer that we move one step each time
fast: the pointer that we move two step each time

Algorithm:
each time slow move one step, at the same time,fast moves two step each time.
if there is a cycle in the input linkedlist, the fast and slow pointer will meet, otherwise, if
fast = null, that means there is no cycle. 


time: 
o(slow + fast)
space: O(1)
*/

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
    	// corner case
    	if(head == null || head.next == null){
    		return false;
    	}

    	ListNode slow = head;
    	ListNode fast = head;

    	while(fast.next != null && fast.next.next != null){
    		slow = slow.next;
    		fast = fast.next.next;
    		if(slow == fast){
    			return true;
    		}
    	}

    	return false;
        
    }
}