
/*
2 Climbing Stairs


Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

Note: Given n will be a positive integer.

Example 1:

Input: 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
Example 2:

Input: 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step'

DS:
M[i] represents that the distinct ways we can climb to stair i.

Al:
base case: 
M[0] = 1
M[1] = 1

M[i] = M[i-1] + M[i-2]

*/

class Solution {
  public int climbStairs(int n) {
    int pp = 1;
    int p = 1;

    for(int i = 2; i <= n; i++){
      int cur = p + pp;
      pp = p;
      p = cur;
    }

    return p;
  }
}

/*
3  Permutations

Given a collection of distinct integers, return all possible permutations.

Example:

Input: [1,2,3]
Output:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]

              1(23)      2(13)     3(21)
              /  \       /  \      /   \
             2(3) 3(2)  1(3) 3(1) 2(1) 1(2)
             |    |      |   |     |    |
             3    2      3   1     1    2

there are nums.length level, for each level, we select one possible num

*/

class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 1){
            return res;
        }
        List<Integer> cur = new ArrayList<>();
        generatePermute(nums, res, 0,cur);
        return res;
    }

    private void generatePermute(int[] nums, List<List<Integer>> res, int level, List<Integer> cur){
        if(level >= nums.length){
            res.add(new ArrayList(cur));
            return;
        }


        for(int i = level; i < nums.length; i++){
            cur.add(nums[i]);
            swap(nums, i, level);
            generatePermute(nums, res, level + 1, cur);
            cur.remove(cur.size()-1);
            swap(nums, i, level);
        }
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

/*
4 Permutations II
Given a collection of numbers that might contain duplicates, return all possible unique permutations.

Example:

Input: [1,1,2]
Output:
[
  [1,1,2],
  [1,2,1],
  [2,1,1]
]

the only difference between Permutations | and Permutations || is that we need to check whether there
are duplicates in nums, eg, if we have 1 in nums to compose permutations in the first place, 
for the next candidte in the first place can not be 1 which has different index with the previous one.
so we need a set for each level to check whether we have add the cur num or not.

time: O(2^n)
space: O(n^2)
*/

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if(nums == null || nums.length < 1){
            return res;
        }
        List<Integer> cur = new ArrayList<>();
        generatePermute(nums, res, 0,cur);
        return res;
    }

    private void generatePermute(int[] nums, List<List<Integer>> res, int level, List<Integer> cur){
        if(level >= nums.length){
            res.add(new ArrayList(cur));
            return;
        }

        Set<Integer> check = new HashSet<>();
        for(int i = level; i < nums.length; i++){
            if(check.add(nums[i])){
                cur.add(nums[i]);
                swap(nums, i, level);
                generatePermute(nums, res, level + 1, cur);
                cur.remove(cur.size()-1);
                swap(nums, i, level);
            }

        }
    }

    private void swap(int[] nums, int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}

/*
5 3Sum Closest

Given an array nums of n integers and an integer target, 
find three integers in nums such that the sum is closest to target. 
Return the sum of the three integers. 
You may assume that each input would have exactly one solution.

Example:

Given array nums = [-1, 2, 1, -4], and target = 1.

The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).

DS:
left, right pointers are to point the sorted input nums.
res: store the return sum
diff: the difference between target and a+b+c

AL:
step1: sort the input nums
step2: for outer loop we try n-2 elements in the input, for the inner loop,
use two pointer, 
if nums[left] + nums[right] = target - nums[i] where i is the index in outer loop. return target
if nums[left] + nums[right] < target - nums[i], left++
if nums[left] + nums[right] > target - nums[i], right--
at the same update res and diff

time: O(nlogn) + O(n^2)
space: O(1)
*/

class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int diff = Integer.MAX_VALUE;
        int res = 0;
        for(int k = 0; k < nums.length-2; k++){
          int tempTarget = target - nums[k];

          int  i = k + 1;
          int j = nums.length-1;
          while(i < j){
            int sum = nums[i] + nums[j];
            if(sum == tempTarget){
              return target;
            }else if(sum > tempTarget){
              j--;
            }else{
              i++;
            }

            if(Math.abs(sum-tempTarget) < diff){
              diff = Math.abs(sum-tempTarget);
              res = sum + nums[k];
            }
            
          }
        }
        return res;
    }
}

// same thought but better code


class Solution {
     public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int res = nums[nums.length-3] + nums[nums.length-2] + nums[nums.length-1];
        for(int k = 0; k < nums.length-2; k++){
          int  i = k + 1;
          int j = nums.length-1;
          while(i < j){
            int sum = nums[i] + nums[j] + nums[k];
            if(sum ==target){
              return target;
            }else if(sum > target){
              j--;
            }else{
              i++;
            }

            if(Math.abs(sum-target) < Math.abs(res - target)){
              res= sum;
            }
          }
        }
        return res;
        
    }
}
