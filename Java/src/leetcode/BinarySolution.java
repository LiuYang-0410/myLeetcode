package leetcode;

public class BinarySolution {

	/***
	 * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
	 * @param nums sorted input array
	 * @return a height balanced BST
	 */
	public TreeNode sortedArrayToBST(int[] nums) {//平衡二叉树的概念,输入可以无序
		if (nums.length == 0) {
			return null;
		}
		/**
		 * 1.放置新节点
		 * 2.找到不平衡子树及不平衡模式（LL,RR,RL,LR）
		 * 3.旋转
		 */
		balancedTreeNode root = new balancedTreeNode(nums[0]);
		for (int i = 1; i < nums.length; i++) {
			balancedTreeNode curNode = root;
			balancedTreeNode insertedNode;
			while (true) {//放置新节点
				if (nums[i] < curNode.val) {//左子树
					if (curNode.left != null) {
						curNode = curNode.left;
					}else {
						insertedNode = new balancedTreeNode(nums[i]);
						curNode.setLeftChild(insertedNode);
						break;
					}
				}else {//右子树
					if (curNode.right != null) {
						curNode = curNode.right;
					}else {
						insertedNode = new balancedTreeNode(nums[i]);
						curNode.setRightChild(insertedNode);
						break;
					}
				}
			}
//			System.out.println("插入后：");
//			printBianryTree(root);

			balancedTreeNode unbalancedRoot = updateBalanceFactor(insertedNode);
			if (unbalancedRoot != null) {
				if (unbalancedRoot.balanceFactor == 2) {
					if (unbalancedRoot.left.balanceFactor == -1) {//LR
						unbalancedRoot.balanceFactor = -1;
						unbalancedRoot.left.balanceFactor = 0;
						unbalancedRoot.left.right.balanceFactor = 0;
						balancedTreeNode curCopyNode = unbalancedRoot.left;
						unbalancedRoot.setLeftChild(curCopyNode.right);
						curCopyNode.setRightChild(unbalancedRoot.left.left);
						unbalancedRoot.left.setLeftChild(curCopyNode);
					}else {//LL
						unbalancedRoot.balanceFactor = 0;
						unbalancedRoot.left.balanceFactor = 0;
					}
					//LL&&LR
					balancedTreeNode rootCopyNode = new balancedTreeNode(unbalancedRoot);
					rootCopyNode.setLeftChild(unbalancedRoot.left.right);
					unbalancedRoot.setRightChild(rootCopyNode);
					unbalancedRoot.val = unbalancedRoot.left.val;
					unbalancedRoot.setLeftChild(unbalancedRoot.left.left);
				}else if (unbalancedRoot.balanceFactor == -2) {
					if (unbalancedRoot.right.balanceFactor == 1) {	//RL
						unbalancedRoot.balanceFactor = 1;
						unbalancedRoot.right.balanceFactor = 0;
						unbalancedRoot.right.left.balanceFactor = 0;
						balancedTreeNode curCopyNode = unbalancedRoot.right;
						unbalancedRoot.setRightChild(curCopyNode.left);
						curCopyNode.setLeftChild(unbalancedRoot.right.right);
						unbalancedRoot.right.setRightChild(curCopyNode);
					}else {//RR
						unbalancedRoot.balanceFactor = 0;
						unbalancedRoot.right.balanceFactor = 0;
					}
					//RR && RL
					balancedTreeNode rootCopyNode = new balancedTreeNode(unbalancedRoot);
					rootCopyNode.setRightChild(unbalancedRoot.right.left);
					unbalancedRoot.setLeftChild(rootCopyNode);
					unbalancedRoot.val = unbalancedRoot.right.val;
					unbalancedRoot.setRightChild(unbalancedRoot.right.right);
				}
			}
			
//		    System.out.println("调整后：");
//		    printBianryTree(root);
		}
		return root;
    }
	
	private balancedTreeNode updateBalanceFactor(balancedTreeNode leafNode) {
		balancedTreeNode parentNode = leafNode.parent;
		balancedTreeNode childNode = leafNode;
		while (parentNode != null) {
			if (childNode.equals(parentNode.left)) {
				parentNode.balanceFactor ++;
			}else {
				parentNode.balanceFactor --;
			}
			if (parentNode.balanceFactor == 0) {	//停止更新
				break;
			}
			if (Math.abs(parentNode.balanceFactor) == 2) {	//找到最小不平衡子树
				 return parentNode;
			}
			childNode = parentNode;
			parentNode = parentNode.parent;
		}
		return null;
	}
	
	public TreeNode sortedArrayToBST2(int[] nums) {//分治，输入必须有序
		return sortedArrayToBST(nums, 0, nums.length-1);
	}
	private TreeNode sortedArrayToBST(int[] nums,int beginIndex,int endIndex) {//分治
		if (beginIndex > endIndex) {
			return null;
		}
		if (beginIndex == endIndex) {
			return new TreeNode(nums[beginIndex]);
		}
		int rootIndex = (beginIndex + endIndex)/2;
		TreeNode root = new TreeNode(nums[rootIndex]);
		root.left = sortedArrayToBST(nums, beginIndex, rootIndex-1);
		root.right = sortedArrayToBST(nums, rootIndex+1, endIndex);
		return root;
	}

	private void printBianryTree(TreeNode root) {
		if (root == null || root.left == null && root.right == null) {
			return;
		}
		System.out.print(root.val+"\t");
		if (root.left != null) {
			System.out.print("left value:\t"+root.left.val+"\t");
		}
		if (root.right != null) {
			System.out.print("right value:\t"+root.right.val+"\t");
		}
		System.out.print("\n");
		printBianryTree(root.left);
		printBianryTree(root.right);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BinarySolution solution = new BinarySolution();
//		int array[] = {20,35,40,15,30,25,38};
		int array[] = {15,20,25,35,30,38,40};
		TreeNode root = solution.sortedArrayToBST2(array);
		solution.printBianryTree(root);
	}

}

class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

class balancedTreeNode extends TreeNode{
	balancedTreeNode left;
	balancedTreeNode right;
	int balanceFactor = 0;	//平衡因子
	balancedTreeNode parent = null;	//指向父节点
	public balancedTreeNode(int x) {
		// TODO Auto-generated constructor stub
		super(x);
	}
	
	public balancedTreeNode(balancedTreeNode node) {
		// TODO Auto-generated constructor stub
		super(node.val);
		this.balanceFactor = node.balanceFactor;
		this.setLeftChild(node.left);
		this.setRightChild(node.right);
	}
	
	public void setLeftChild(balancedTreeNode childNode) {
		this.left = childNode;
		super.left = childNode;
		if (childNode != null) {
			childNode.parent = this;
		}
	}
	public void setRightChild(balancedTreeNode childNode) {
		this.right = childNode;
		super.right = childNode;
		if (childNode != null) {
			childNode.parent = this;
		}	
	}
}