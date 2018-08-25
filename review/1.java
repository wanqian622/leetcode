// review part 1

/*
Q3.2: How to rotate an N*N matrix clockwise by 90 degree?

Rotate an N * N matrix clockwise 90 degrees.

Assumptions

The matrix is not null and N >= 0
Examples

{ {1,  2,  3}

  {8,  9,  4},

  {7,  6,  5} }

after rotation is

{ {7,  8,  1}

  {6,  9,  2},

  {5,  4,  3} }


method1: 

matrix[i][j] = matrix[n-j-1][i]
matrix[n-j-1][i] = matrix[n-i-1][n-j-1]
matrix[n-i-1][n-j-1] = matrix[j][n-i-1]
matrix[j][n-i-1] = temp

time: O(n)
space: O(1)
*/

 public class Solution {
  public void rotate(int[][] matrix) {
  	if(matrix == null || matrix.length < 1){
  		return;
  	}

  	int n = matrix.length;

  	for(int i = 0; i < n/2; i++){ // how much level we need to rotate
  		for(int j = i; j < n-i-1; j++){ // for each level, how much ele, we need to swap
  			int temp = matrix[i][j];
  			matrix[i][j] = matrix[n-j-1][i];
			matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
			matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
			matrix[j][n-i-1] = temp;
  		}
  	}
  	return; 
  }
}

/*
Q4.2: Classical way to print the tree level by level in a zig-zag way
Get the list of keys in a given binary tree layer by layer in zig-zag order.

Examples

        5							<-  1

      /    \

    3        8						->  2

  /   \        \

 1     4        11					<-  3
 	 /	\
 	6.   7							->4

the result is [5, 3, 8, 11, 4, 1]

Corner Cases

What if the binary tree is null? Return an empty list in this case.
How is the binary tree represented?

We use the level order traversal sequence with a special symbol "#" denoting the null node.

For Example:

The sequence [1, 2, 3, #, #, 4] represents the following binary tree:

    1

  /   \

 2     3

      /

    4


method1:
deque: contains the current level from left to right

algorithm:
case1: even level -> head in, rear out
case2: odd level.  -> head out, rear in

        5							<-  1

      /    \

    3        8						->  2

  /   \        \

 1     4        11					<-  3
 	 /	\
 	6.   7							->4

the result is [5, 3, 8, 11, 4, 1]

*/

public class Solution {
  public List<Integer> zigZag(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if(root == null) {
        return res;
    }

    Deque<TreeNode> deque = new LinkedList<>();
    boolean odd = true;
    deque.offerLast(root);

    while(!deque.isEmpty()){
    	odd = !odd;
    	int size = deque.size();
    	while(size > 0){
    		if(odd){
    			TreeNode cur = deque.pollFirst();
    			res.add(cur.key);
    			if(cur.left != null){
    				deque.offerLast(cur.left);
    			}

    			if(cur.right != null){
    				deque.offerLast(cur.right);
    			}
    		}else{
    			TreeNode cur = deque.pollLast();

    			if(cur.right != null){
    				deque.offerFirst(cur.right);
    			}

    			if(cur.left != null){
    				deque.offerFirst(cur.left);
    			}
    		}
    		size--;
    	}
    }

    return res;     
  }
}
/*
Q5.2 Assume we do have parent pointers in each node
Given two nodes in a binary tree (with parent pointer available),
 find their lowest common ancestor.

Assumptions

There is parent pointer for the nodes in the binary tree

The given two nodes are not guaranteed to be in the binary tree

Examples

        5

      /   \

     9     12

   /  \      \

  2    3      14

The lowest common ancestor of 2 and 14 is 5

The lowest common ancestor of 2 and 9 is 9

The lowest common ancestor of 2 and 8 is null (8 is not in the tree)

method1: 
step1: From a, keep looking up and store all ancestors to a HashSet
step2: From b, keep looking up, untill we find the current node is in the HashSet from step1
time: o(height)
space:o(height)

method2:
step1:keep looking up from a and b respectively, to find depth(a) and depth(b)
step2:trace up from the deeper one. move|depth(a)-depth(b)|
step3:move a and b together one step by one step, until a == b
time: o(height)
space:o(1)
*/

public class Solution {
	public TreeNodeP LCA(TreeNodeP one, TreeNodeP two) {
		if(one == null || two == null){
			return one == null ? two : one;
		}

		int depOne = getDepth(one);
		int depTwo = getDepth(two);

		if(depOne >= depTwo){
			return getLCA(one, depTwo, depOne - depTwo);
		}else{
			return getLCA(two, depOne, depTwo - depOne);
		}
	}

	private TreeNodeP getLCA(TreeNodeP longer, TreeNodeP shorter, int diff){
		while(diff > 0){
			longer = longer.parent;
			diff--;
		}

		while(longer != shorter){
			longer = longer.parent;
			shorter = shorter.parent;
		}

		return longer;
	}

	private int getDepth(TreeNodeP node){
		int depth = 0;
		while(node != null){
			node = node.parent;
			depth++;
		}

		return depth;
	}
}

/*
Q5.3: LCA of k nodes(No parent pointers)

Given K nodes in a binary tree, find their lowest common ancestor.

Assumptions

K >= 2

There is no parent pointer for the nodes in the binary tree

The given K nodes are guaranteed to be in the binary tree

Examples

        5

      /   \

     9     12

   /  \      \

  2    3      14

The lowest common ancestor of 2, 3, 14 is 5

The lowest common ancestor of 2, 3, 9 is 9

*/

public class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, List<TreeNode> nodes) {
    if( root == null || nodes.contains(root)){
    	return root;
    }

    TreeNode left = lowestCommonAncestor(root.left, nodes);
    TreeNode right = lowestCommonAncestor(root.right, nodes);

    if(left != null && right != null){
    	return root;
    }

    return left == null ? right : left;

  }

}

/*
Q5.4: LCA of k-nary Tree
class TreeNode{
  int val;
  List<TreeNode> children;
}
*/

public class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode a, TreeNode b) {
    if( root == null || root == a || root == b){
    	return root;
    }

    int count = 0;
    TreeNode temp = null;

    for(TreeNode node : root.children){
    	TreeNode cur = lowestCommonAncestor(node,a,b);
    	if(cur != null){
    		temp = cur;
    		count = count + 1;
    	}

    	if(count > 1){
    		return root;
    	}
    }

    return temp;	

  }

}

/*
Q5.5 LCA for k nodes in k-nary

*/

public class Solution {
  public TreeNode lowestCommonAncestor(TreeNode root, List<TreeNode> nodes) {
    if( root == null || nodes.contains(root)){
    	return root;
    }

    int count = 0;
    TreeNode temp = null;

    for(TreeNode node : root.children){
    	TreeNode cur = lowestCommonAncestor(node,a,b);
    	if(cur != null){
    		temp = cur;
    		count = count + 1;
    	}

    	if(count > 1){
    		return root;
    	}
    }

    return temp;	

  }

}

