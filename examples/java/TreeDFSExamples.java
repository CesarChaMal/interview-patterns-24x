import java.util.*;

public class TreeDFSExamples {
    
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }
    
    // 1. Binary Tree Inorder Traversal
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    
    private void inorder(TreeNode node, List<Integer> result) {
        if (node != null) {
            inorder(node.left, result);
            result.add(node.val);
            inorder(node.right, result);
        }
    }
    
    // 2. Binary Tree Preorder Traversal
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }
    
    private void preorder(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.val);
            preorder(node.left, result);
            preorder(node.right, result);
        }
    }
    
    // 3. Binary Tree Postorder Traversal
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }
    
    private void postorder(TreeNode node, List<Integer> result) {
        if (node != null) {
            postorder(node.left, result);
            postorder(node.right, result);
            result.add(node.val);
        }
    }
    
    // 4. Maximum Depth of Binary Tree
    public int maxDepth(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
    
    // 5. Diameter of Binary Tree
    int diameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        dfs(root);
        return diameter;
    }
    
    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    }
    
    // 6. Binary Tree Maximum Path Sum
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        maxGain(root);
        return maxSum;
    }
    
    private int maxGain(TreeNode node) {
        if (node == null) return 0;
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);
        int pathSum = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, pathSum);
        return node.val + Math.max(leftGain, rightGain);
    }
    
    // 7. Path Sum
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == targetSum;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }
    
    // 8. Path Sum II
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(root, targetSum, new ArrayList<>(), result);
        return result;
    }
    
    private void dfs(TreeNode node, int sum, List<Integer> path, List<List<Integer>> result) {
        if (node == null) return;
        path.add(node.val);
        if (node.left == null && node.right == null && sum == node.val) {
            result.add(new ArrayList<>(path));
        } else {
            dfs(node.left, sum - node.val, path, result);
            dfs(node.right, sum - node.val, path, result);
        }
        path.remove(path.size() - 1);
    }
    
    // 9. Binary Tree Paths
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root != null) dfs(root, "", result);
        return result;
    }
    
    private void dfs(TreeNode node, String path, List<String> result) {
        if (node.left == null && node.right == null) {
            result.add(path + node.val);
        } else {
            if (node.left != null) dfs(node.left, path + node.val + "->", result);
            if (node.right != null) dfs(node.right, path + node.val + "->", result);
        }
    }
    
    // 10. Path Sum III
    public int pathSumIII(TreeNode root, int targetSum) {
        if (root == null) return 0;
        return dfsPathSum(root, targetSum) + pathSumIII(root.left, targetSum) + pathSumIII(root.right, targetSum);
    }
    
    private int dfsPathSum(TreeNode node, long sum) {
        if (node == null) return 0;
        return (node.val == sum ? 1 : 0) + dfsPathSum(node.left, sum - node.val) + dfsPathSum(node.right, sum - node.val);
    }
    
    public static void main(String[] args) {
        TreeDFSExamples examples = new TreeDFSExamples();
        
        System.out.println("=== Tree DFS Examples ===");
        
        // Create test tree: [3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        
        // Test 1: Binary Tree Inorder Traversal
        System.out.println("1. Inorder Traversal: " + examples.inorderTraversal(root));
        
        // Test 2: Binary Tree Preorder Traversal
        System.out.println("2. Preorder Traversal: " + examples.preorderTraversal(root));
        
        // Test 3: Binary Tree Postorder Traversal
        System.out.println("3. Postorder Traversal: " + examples.postorderTraversal(root));
        
        // Test 4: Maximum Depth of Binary Tree
        System.out.println("4. Max Depth: " + examples.maxDepth(root));
        
        // Test 5: Diameter of Binary Tree
        System.out.println("5. Diameter: " + examples.diameterOfBinaryTree(root));
        
        // Test 6: Binary Tree Maximum Path Sum
        System.out.println("6. Max Path Sum: " + examples.maxPathSum(root));
        
        // Test 7: Path Sum
        System.out.println("7. Has Path Sum (12): " + examples.hasPathSum(root, 12));
        
        // Test 8: Path Sum II
        System.out.println("8. Path Sum II (12): " + examples.pathSum(root, 12));
        
        // Test 9: Binary Tree Paths
        System.out.println("9. Binary Tree Paths: " + examples.binaryTreePaths(root));
        
        // Test 10: Path Sum III
        System.out.println("10. Path Sum III (3): " + examples.pathSumIII(root, 3));
    }
}