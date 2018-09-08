/*
1 Pow(x, n)
Implement pow(x, n), which calculates x raised to the power n (xn).

Example 1:

Input: 2.00000, 10
Output: 1024.00000
Example 2:

Input: 2.10000, 3
Output: 9.26100
Example 3:

Input: 2.00000, -2
Output: 0.25000
Explanation: 2-2 = 1/22 = 1/4 = 0.25
Note:

-100.0 < x < 100.0
n is a 32-bit signed integer, within the range [−2^31, 2^31 − 1]


*/

class Solution {
    public double myPow(double x, int n) {
        // base case
        if(n == 0){
            return 1;
        }

        if(x == 0){
            return 0;
        }

        if(n < 0){
            return 1/x * myPow(1/x, -(n+1));
        }

        double half = myPow(x, n/2);

        return n %2 == 0 ? half * half : x*half*half;
    }
}


/*
2 Reverse Linked List II

Reverse a linked list from position m to n. Do it in one-pass.

Note: 1 ≤ m ≤ n ≤ length of list.

Example:

Input: 1->2->3->4->5->NULL, m = 2, n = 4

Output: 1->4->3->2->5->NULL


DS:
dummyNode: we can not ensure the head, so we need a dummyNode
cur: the cur node we points
prev: the previous node for the cur node
next: the next node for the cur node
cout: how many node we have visited


AL:
we go through the input nodes.each time we visit a node, m--;
when count == m, that means we need to reverse the linkedlist starting from this node.
the reverse num should be n-m

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
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(m == n || head == null){
            return head;
        }

        ListNode dummyNode = new ListNode(0);
        ListNode dummy = dummyNode;
        ListNode cur = head;
        int num = n-m;

        while(m > 1){
            dummy.next = cur;
            dummy = dummy.next;
            cur = cur.next;
            m--;
        }

        ListNode prev = null;
        ListNode next = null;
        ListNode curr = cur;

        while(num >= 0){
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
            num--;
        }

        dummy.next = prev;
        curr.next =next;

        return dummyNode.next;
    }
}



/*
3 Word Search
Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, 
where "adjacent" cells are those horizontally or vertically neighboring. 
The same letter cell may not be used more than once.

Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.

Method1: dfs
DS:
boolean[][] visited: check whether we visited this cell or not;
index: the current char word[index] that we need to check whether in the grid or not
i, j: the current cell's index 


AL:
for for loop board:
    if board[i][j] == word[0] (that means we could run bfs starting from the current index)
        if index == word.length, return true
        if it is out of boundary, return  false;
        if it has beed visited, return false;
        if board[i][j] != word[index], return false; (we could not set it visit, because it is possible
        in word)

        
        visited[i][j] = true;
        if (go (i+1, j), (i,j+1), (i-1,j), (i, j-1)) --> return true;
        else:
            visited[i][j] = false (we need to reset it to false, because it is possible
        in word)
            return false;
*/

public class Solution {
    public boolean exist(char[][] board, String word) {
        if(board == null || word == null || board.length < 1 || word.length() < 1){
            return false;
        }

        int m = board.length;
        int n = board[0].length;

        if(m*n < word.length()){
            return false;
        }
        boolean[][] visited = new boolean[m][n];


        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(board[i][j] == word.charAt(0) && isExist(board, word, visited, 0, i, j)){
                    return true;
                }
            }
        }

        return false;

    }


    private boolean isExist(char[][] board, String word, boolean[][] visited, int index, int i, int j){

        if(index >= word.length()){
            return true;
        }

        if(i < 0 || j < 0 || i >= board.length || j >= board[0].length || visited[i][j] || board[i][j] != word.charAt(index)){ // if board[i][j] != word.charAt(index), we should not set it visit. 
            return false;
        }

        visited[i][j] = true;

        index = index + 1;
        if(isExist(board, word, visited, index, i, j+1) || isExist(board, word, visited, index, i+1, j) || isExist(board, word, visited, index, i-1, j) || isExist(board, word, visited, index, i, j-1)){
            return true;
        }

        visited[i][j] = false;
        return false;
    }
}

