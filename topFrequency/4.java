// 8.28


/*
2 Reverse String

Write a function that takes a string as input and returns the string reversed.

Example 1:

Input: "hello"
Output: "olleh"
Example 2:

Input: "A man, a plan, a canal: Panama"
Output: "amanaP :lanac a ,nalp a ,nam A"

method1:iterative
array: convert input s to char array
left: points to the most left boundary
right: points to the most right boundary

Algorithm:
we use left to point the start in input s, and right  to point the end in input s.
each time, switch the element in left and right, left++ and right--. untill left >= right

*/

class Solution {
  public String reverseString(String s) {
    if(s == null || s.length() < 1){
      return s;
    }

    char[] array = s.toCharArray();
    int left = 0;
    int right = s.length() - 1;

    while(left < right){
      swap(array, left++, right--);
    }

    return String.valueOf(array);
  }

  private void swap(char[] array, int i, int j){
    char temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }  
}

// recursion

class Solution {
  public String reverseString(String s) {
    if(s == null || s.length() < 1){
      return s;
    }
    char[] array = s.toCharArray();
    getReverseString(array, 0, array.length - 1);
    return s.valueOf(array);
  }

  private void getReverseString(char[] array,int start, int end){
    if(start >= end){
      return;
    }

    swap(array, start, end);
    getReverseString(array, start+1, end-1);
  }

  private void(char[] array, int i, int j){
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }   
}
/*
3 Most Common Word
Given a paragraph and a list of banned words, 
return the most frequent word that is not in the list of banned words.  
It is guaranteed there is at least one word that isn't banned, and that the answer is unique.

Words in the list of banned words are given in lowercase, and free of punctuation. 
 Words in the paragraph are not case sensitive.  The answer is in lowercase.

Example:
Input: 
paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
banned = ["hit"]
Output: "ball"
Explanation: 
"hit" occurs 3 times, but it is a banned word.
"ball" occurs twice (and no other word does), 
so it is the most frequent non-banned word in the paragraph. 
Note that words in the paragraph are not case sensitive,
that punctuation is ignored (even if adjacent to words, such as "ball,"), 
and that "hit" isn't the answer even though it occurs more because it is banned.
 

Note:

1 <= paragraph.length <= 1000.
1 <= banned.length <= 100.
1 <= banned[i].length <= 10.
The answer is unique, and written in lowercase 
(even if its occurrences in paragraph may have uppercase symbols, and even if it is a proper noun.)
paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
Different words in paragraph are always separated by a space.
There are no hyphens or hyphenated words.
Words only consist of letters, never apostrophes or other punctuation symbols.


Method1:
DS:
HashSet: write string in banned array in HashSet so that we could check one string is banned or not in O(1)
TreeMap: the key is to store the unbanned string and the value is to store the apprence of this unbanned string
slow and fast is to dump input string into valid char Array.

Algorithm:
step1: write string in banned array in HashSet so that we could check one string is banned or not in O(1)
step2: use two pointers slow and fast to make input string into valid char Array
step3: for loop the char Array, store each valid string into treemap.
*/

public class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {

        // write string in banned array in HashSet
        // step1
        Set<String> check = new HashSet<>();
        for(String s : banned){
            check.add(s);
        }

        // step2
        char[] array = paragraph.toCharArray();
        int slow = 0;
        int fast = 0;

        while(fast < array.length){
            if(Character.isLetter(array[fast])){
                if(slow != 0 && array[fast-1] == ' '){
                    array[slow++] = ' ';
                }
                array[slow++] = Character.toLowerCase(array[fast]);
            }
            fast++;
        }
//        return String.valueOf(array, 0, slow);

        // step3
        Map<String, Integer> map = new HashMap<>();
        int i = 0;
        int j = 0;
        while(j < slow){
            while(j < slow && Character.isLetter(array[j])){
                j++;
            }
            String temp = String.valueOf(array, i, j-i);
//            String temp = String.copyValueOf(array, i, j-i);
            if(!check.contains(temp)){
              map.put(temp, map.getOrDefault(temp, 0) + 1);
            }

            j++;
            i = j;
        }
        int max = 0;
        String res = "";
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if( max < entry.getValue()){
                max = entry.getValue();
                res = entry.getKey();
            }
        }
        return res;

       // return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

}

/*
4 Group Anagrams
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
            Arrays.sort(tempArray);
            // String convert = tempArray.toString();
            String convert = String.valueOf(tempArray); 
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
5 Word Break
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
determine if s can be segmented into a space-separated sequence of one or more dictionary words.

Note:

The same word in the dictionary may be reused multiple times in the segmentation.
You may assume the dictionary does not contain duplicate words.
Example 1:

Input: s = "leetcode", wordDict = ["leet", "code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:

Input: s = "applepenapple", wordDict = ["apple", "pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
             Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
Output: false

Method1: DP
左大段: check table
右小段: 人为的maunal check

DS:
M[i]: represents the string composed with chars from index 0 to index i in s can be segmented into a space-separated sequence of one or more dictionary words

Algorithm:
M[i] = true when M[j] is true and s.substring[j+1, i] is true

Input: s = "leetcode", wordDict = ["leet", "code"]
size = 1
"l" not in dic, M[0] = false

size = 2
"le", condition1:check  "le"
      condition2: chech M[0] and "e"
      M[1] = con1 || con2

size = 3
"lee", con1: check"lee"
       con2: check M[0] and "ee"
       con3: check M[1] and "e"
      M[2] = con1 || con2 || con3
*/

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
    if(s == null || s.length() < 1 || wordDict.size() < 1){
      return false;
    }

    boolean[] M = new boolean[s.length()];
    for(int i = 0; i < s.length(); i++){
      if(wordDict.contains(s.substring(0, i+1))){
        M[i] = true;
        continue;
      }
      for(int j = 0; j < i; j++){
        if(M[j] && wordDict.contains(s.substring(j+1, i+1))){
          M[i] = true;
          continue;
        }
      }
    }
    return M[s.length()-1];
        
    }
}

/*
6 Minimum Window Substring

Given a string S and a string T, 
find the minimum window in S which will contain all the characters in T in complexity O(n).

Example:

Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"
Note:

If there is no such window in S that covers all characters in T, return the empty string "".
If there is such window, 
you are guaranteed that there will always be only one unique minimum window in S.



*/

class Solution {
 public String minWindow(String s, String t) {
        int[] map = new int[128];
        
        for (char c : t.toCharArray()) map[c]++;
        
        int count = t.length(), begin = 0, end = 0, d = Integer.MAX_VALUE, head = 0;
        
        while (end < s.length()) {
            if (map[s.charAt(end++)]-- > 0) count--;
            while (count == 0) {
                if (end - begin < d) {
                    d = end - begin;
                    head = begin;
                }
                if (map[s.charAt(begin++)]++ == 0) 
                    count++;
            }
        }
        
        return d == Integer.MAX_VALUE ? "" : s.substring(head, head+d);
        
        
    }
}

int findSubstring(string s){
        vector<int> map(128,0);
        int counter; // check whether the substring is valid
        int begin=0, end=0; //two pointers, one point to tail and one  head
        int d; //the length of substring

        for() { /* initialize the hash map here */ }

        while(end<s.size()){

            if(map[s[end++]]-- ?){  /* modify counter here */ }

            while(/* counter condition */){ 
                 
                 /* update d here if finding minimum*/

                //increase begin to make it invalid/valid again
                
                if(map[s[begin++]]++ ?){ /*modify counter here*/ }
            }  

            /* update d here if finding maximum*/
        }
        return d;
  }
