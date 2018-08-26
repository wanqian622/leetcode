// 8.25

/*
1 Copy List with Random Pointer   
A linked list is given such that each node contains an additional random pointer which could point to any node in the list or null.

Return a deep copy of the list.

Data structue:
curHead: points to the current orginal node
copyHead: points to the current copy node
lookup(hashmap): {<curNode, copyNode>}

Algorithm:
for the curHead,
if curHead.next not exits in lookup,
we put lookup.put(curHead.next, new ListNode(curHead.next.val));
copyHead.next = lookup.get(curHead.next)
if curHead.random not exits in lookup,
we put lookup.put(curHead.random, new ListNode(curHead.random.val));
copyHead.random = lookup.get(curHead.random)


time: O(n)
space: O(n)

*/

/**
 * Definition for singly-linked list with a random pointer.
 * class RandomListNode {
 *     int label;
 *     RandomListNode next, random;
 *     RandomListNode(int x) { this.label = x; }
 * };
 */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null){
            return head;
        }

        // lookup is to store the curNode and corresponding copyNode
        Map<RandomListNode,RandomListNode> lookup = new HashMap<>();
        RandomListNode newHead = new RandomListNode(head.label);
        RandomListNode copyHead = newHead;
        lookup.put(head, copyHead);

        while(head != null){
            if(head.next != null){
                if(!lookup.containsKey(head.next)){
                    lookup.put(head.next, new RandomListNode(head.next.label));
                }
                copyHead.next = lookup.get(head.next);
            }

            if(head.random != null){
                if(!lookup.containsKey(head.random)){
                    lookup.put(head.random, new RandomListNode(head.random.label));
                }
                copyHead.random = lookup.get(head.random);                
            }
            head = head.next;
            copyHead = copyHead.next;
        }

        return newHead;
    }
}

/*
2 Maximum Subarray

Given an integer array nums, 
find the contiguous subarray (containing at least one number) 
which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution,
 try coding another solution using the divide and conquer approach, which is more subtle.


 Assumption:
 input is null or len < 1?
 time or space?

 Data structure:
 M[i] is to store the max subarary sum from index 0 to index i
 max_sum: to store the max subarary sum

 Algorithm:
 base case: M[0] = nums[0]
 induction rule: M[i] = M[i-1] + nums[i] if M[i-1] >= 0
 					M[i] = nums[i]

*/

class Solution {
    public int maxSubArray(int[] nums) {
    	if(nums == null || nums.length < 1){
    		return 0;
    	}
    	// M is to store the max subarary sum from index 0 to index i
    	int[] M = new int[nums.length];
    	M[0] = nums[0];
    	max_sum = nums[0];
    	for(int i = 1; i < nums.length; i++){
    		if(M[i-1] >= 0){
    			M[i] = M[i-1] + nums[i];
    		}else{
    			M[i] = nums[i];
    		}

    		max_sum = Math.max(max_sum, M[i]);
    	}

    	return max_sum;
        
    }
}

// improvement
class Solution {
    public int maxSubArray(int[] nums) {
        if(nums == null || nums.length < 1){
            return 0;
        }
        // M is to store the max subarary sum from index 0 to index i
        int prev = nums[0];
        int max_sum = nums[0];
        for(int i = 1; i < nums.length; i++){
            int cur = prev >= 0 ? prev + nums[i] : nums[i];
            max_sum = Math.max(max_sum, cur);
            prev = cur;

        }

        return max_sum;   
    }
}

/*
3 Merge k Sorted Lists

Merge k sorted linked lists and return it as one sorted list.
Analyze and describe its complexity.

Example:

Input:
[
  1->4->5,
  1->3->4,
  2->6
]
Output: 1->1->2->3->4->4->5->6

Data structure:
k pointers to point those k linkedlist;
min heap is to store those k pointers

Algorithm:
init:
dummyNode whose next is our res
k pointers to point the first node in each linkedlist and push
those k pointers into the min heap according to each k pointers value to sort

we pop the smallest one, and dummy.next -> the smallest one;
then we push smallest.next into min heap
we repeat thise process until the min heap is empty

[
  1->4->5,
          x

  1->3->4,
          y

  2->6
     z
]
output:
0 -> 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5


time: O(nklogk)
space: space(k)

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
    public ListNode mergeKLists(ListNode[] lists) {
    	// corner case 
    	if(lists == null || lists.length < 1){
    		return null;
    	}

    	// we can not ensure our head, so we use a dummy node
    	ListNode dummyNode = new ListNode(0);
    	ListNode dummy = dummyNode;

		// minHeap is to store those k pointers, each pointer points 
		// the cur node in each linkedlist
		PriorityQueue<ListNode> minHeap = new PriorityQueue<ListNode>(lists.length, (a,b)->a.val - b.val);

		// init
		for(ListNode node : lists){
            if(node != null){. // in case [[]]
              minHeap.offer(node);  
            }
		}

		while(!minHeap.isEmpty()){
			ListNode cur = minHeap.poll();
			dummy.next = cur;
			if(cur.next != null){
				cur = cur.next;
				minHeap.offer(cur);
			}
			dummy = dummy.next;
		}
		dummy.next = null;
		return dummyNode;   
    }
}