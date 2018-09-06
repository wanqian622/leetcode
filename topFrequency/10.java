// 9.5

/*
1  Divide Two Integers

Given two integers dividend and divisor, divide two integers without using multiplication,
division and mod operator.

Return the quotient after dividing dividend by divisor.

The integer division should truncate toward zero.

Example 1:

Input: dividend = 10, divisor = 3
Output: 3
Example 2:

Input: dividend = 7, divisor = -3
Output: -2
Note:

Both dividend and divisor will be 32-bit signed integers.
The divisor will never be 0.
Assume we are dealing with an environment 
which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. 
For the purpose of this problem, assume that your function returns 231 − 1 
when the division result overflows.

AL:

In this problem, we are asked to divide two integers. However, we are not allowed to use division, multiplication and mod operations. So, what else can we use? Yeah, bit manipulations.

Let's do an example and see how bit manipulations work.

Suppose we want to divide 15 by 3, so 15 is dividend and 3 is divisor. Well, division simply requires us to find how many times we can subtract the divisor from the the dividend without making the dividend negative.

Let's get started. We subtract 3 from 15 and we get 12, which is positive. Let's try to subtract more. Well, we shift 3 to the left by 1 bit and we get 6. Subtracting 6 from 15 still gives a positive result. Well, we shift again and get 12. We subtract 12 from 15 and it is still positive. We shift again, obtaining 24 and we know we can at most subtract 12. Well, since 12 is obtained by shifting 3 to left twice, we know it is 4 times of 3. How do we obtain this 4? Well, we start from 1 and shift it to left twice at the same time. We add 4 to an answer (initialized to be 0). In fact, the above process is like 15 = 3 * 4 + 3. We now get part of the quotient (4), with a remainder 3.

Then we repeat the above process again. We subtract divisor = 3 from the remaining dividend = 3 and obtain 0. We know we are done. No shift happens, so we simply add 1 << 0 to the answer.

Now we have the full algorithm to perform division.

According to the problem statement, we need to handle some exceptions, such as overflow.

Well, two cases may cause overflow:

divisor = 0;
dividend = INT_MIN and divisor = -1 (because abs(INT_MIN) = INT_MAX + 1).
Of course, we also need to take the sign into considerations, which is relatively easy.
*/

class Solution {
    public int divide(int dividend, int divisor) {
        // there are two cases causing overflow
        if(divisor == 0 || dividend == Integer.MIN_VALUE && divisor == -1){
            return Integer.MAX_VALUE;
        }

        int sign = (dividend > 0 && divisor < 0) || (dividend < 0 || divisor > 0) ? -1 : 1;

        int res = 0;
        long dvd = Math.abs((long)dividend); // if we change to int, the input is Integer.MAX_VALUE, 1, it will cause time limited
        long dvs = Math.abs((long)divisor);

        while(dvd >= dvs){
            long temp = dvs;
            int mul = 1;

            while(dvd >= (temp << 1)){ // temp = 2^30 --> 2^31 overflow, so we need to use long
                temp = temp <<1;
                mul = mul<<1;
            }
            dvd = dvd - temp;
            res += mul;
        }

        return sign > 0 ? res : -res;        
    }
}

/*
2 Basic Calculator II
Implement a basic calculator to evaluate a simple expression string.

The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . 
The integer division should truncate toward zero.

Example 1:

Input: "3+2*2+5"
Output: 7
Example 2:

Input: " 3/2 "
Output: 1
Example 3:

Input: " 3+5 / 2 "
Output: 5
Note:

Input: "42 -  3+5 / 2 "
                    i
        +     - +   / 

stack: 42, -3, 5/2
You may assume that the given expression is always valid.
Do not use the eval built-in library function.


Conditions:
1 space
2 "42"
3 +, -, * /

DS:
i: points to the current character in the input s
sign: the previous sign
num: the temp varible, store each valid number, from the previous sign to the current sign
stack: store  digits or cauculated digits between the previous sign and the current signs


AL:
we loop the input s, 
    1 the cur char is the digit, store it into num(convert it to int)
    2 the cur char is the '+', '-', '*', '/',
        if the prev sign '+'or '-', push the num(-num) into stack
        if the prev sign '*'or '/', stack.push(stack.pop()/(*)num)
        update sign to cur sign
        set num = 0
    3 continue
time: O(n)
space: O(n)


*/

class Solution {
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList<>();
        int num = 0;
        char sign = '+';
        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                num = num*10 + s.charAt(i) - '0';
            }

            if(!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == s.length()-1){
                if(sign == '+' || sign == '-'){
                    int temp = sign == '+' ? num : -num;
                    stack.push(temp);
                }
                if(sign == '*' || sign == '/'){
                    int temp = sign == '*' ? stack.pop() * num : stack.pop()/num;
                    stack.push(temp);
                }
                sign = s.charAt(i);
                num = 0;
            }
        }

        int res = 0;
        for(Integer val : stack){
            res += val;
        }
        return res;
    }
}


/*
3 Edit Distance
Given two words word1 and word2, 
find the minimum number of operations required to convert word1 to word2.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')

size = 0
M[0][0] = 0

size = 1
if s1[1] == s2[1]: M[1][1] = M[0][0]
else: M[1][1] = M[0][0] + 1 --> replace in s1
              = M[0][1] + 1 --> delete in s1
              = M[1][0] + 1 --> insert in s1

size = 2
if s1[2] = s2[2]: M[2][2] = M[1][1]
else: M[2][2] = M[1][1] + 1 --> replace in s1
                M[1][2] + 1 --> delete in s1
                M[2][1] + 1 --> insert in s1

DS:
M[i][j] represents the minimum number of operations required to convert word1(from the first index to i) to word2(from the first index to j).

al:
base case:
M[i][0] = i
M[0][j] =j
induction rule:
M[i][j] = M[i-1][j-1] if s1[i] == s2[j]
          min(M[i-1][j-1], M[i-1][j], M[i][j-1]) + 1 if s1[i] != s2[j]


*/

class Solution {
    public int minDistance(String word1, String word2) {
        if(word1 == null || word2 == null){
            return (word1 == null && word2 == null) ? 0 : (word1 == null ? word2.length() : word1.length());
        }

        if(word1.length() < 1 || word2.length() < 1){
            return (word1.length() == word2.length()) ? 0 : (word1.length() < 1 ? word2.length() : word1.length());
        }

        int[][] M = new int[word1.length()+1][word2.length()+1];
        for(int i = 0; i < M.length; i++){
            for(int j = 0; j < M[0].length; j++){
                if(i == 0){
                    M[i][j] = j;
                }else if(j == 0){
                    M[i][j] = i;
                }else{
                    M[i][j] = Integer.MAX_VALUE;
                    if(word1.charAt(i-1) == word2.charAt(j-1)){
                        M[i][j] = M[i-1][j-1];
                    }else{
                        M[i][j] = Math.min(M[i-1][j-1], Math.min(M[i-1][j], M[i][j-1])) + 1;
                    }
                }
            }
        }

        return M[word1.length()][word2.length()];
    }
}

/*
4 Reverse Nodes in k-Group
Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.

k is a positive integer and is less than or equal to the length of the linked list. 
If the number of nodes is not a multiple of k then left-out nodes in the end should remain as it is.

Example:

Given this linked list: 1->2->3->4->5

For k = 2, you should return: 2->1->4->3->5

For k = 3, you should return: 3->2->1->4->5

Note:

Only constant extra memory is allowed.
You may not alter the values in the list's nodes, only nodes itself may be changed.

DS:
head: the cur head we are

AL:
private ListNode generateReverseKGroup(ListNode head, int k)
base case: 
if the current head with its remains < k, just return the cur head;
otherwise:
    reverse k nodes starting from the current head;
    at the same time get the res from the next recursion level

2->1->4->3->5
h  e


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
    public ListNode reverseKGroup(ListNode head, int k) {
        if( head == null || k < 2){
            return head;
        }

        return generateReverseKGroup(head, k);
        
    }

    private ListNode generateReverseKGroup(ListNode head, int k){

        // base case
        if(head == null){
            return null;
        }

        int count = 0;
        ListNode cur = head;
        while(count < k){
            if(cur == null){
                return head;
            }
            cur = cur.next;
            count++;
        }

        // recursion rule
        // reverse listnode from head to prev
        ListNode next = null;
        ListNode curr = head;
        ListNode prev = null;
        while(curr != cur){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // connect with sub-solution
        head.next = generateReverseKGroup(cur, k);
        return prev;
    }
}


/*
5 Longest Palindrome变形。
要求：
1. case-insensitive
2. multiple 等长 palindromes 存在，返回第一个
3. ignore space, e.g. "nurses run" is a valid palindrome
4. boundries , e.g. Input: "I went to my gym yesterday",  Output: " my gym ",  with spaces on either end..1



Method1: dp
the same with lc 5, but need to consider to more conditions

M[i][j] represents whether the substring from index i to index j in the input string is palindrom.
M[i][j] = true if s[i] == s[j] && j-i < 2 || M[i+1][j-1] = true
               if s[i] == ' ' && j-i < 1 || M[i+1][j] == true
               if s[j] == ' ' && j-i < 1 || M[i][j-1] == true

here, we can see, if we want to know M[i][j], we need to know M[i+1][j-1],M[i+1][j],M[i][j-1]
at the same time, j >= i, so i start from len-1 to 0, and j start from i to len-1. 
*/

public class Solution{
    public String longestPalindrome(String input){
        if(input == null || input.length() < 2){
            return input;
        }

        int start = -1;
        int end = -1;
        int len = input.length();
        boolean[][] M = new boolean[len][len];

        for(int i  = len-1; i >= 0; i--){
            for(int j = i; j < len; j++){
                if((Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(input.charAt(j)) && (j - i < 2 || M[i+1][j-1])) || (input.charAt(i) == ' ' && (j - i < 1 || M[i+1][j])) || (input.charAt(j) == ' ' && (j - i < 1 || M[i][j-1]))){
                    M[i][j] = true;
                    if(j-i > end - start){
                        end = j;
                        start = i;
                    }
                }
            }
        }

        return end == -1 ? "" : input.substring(start, end+1);
    }
}




