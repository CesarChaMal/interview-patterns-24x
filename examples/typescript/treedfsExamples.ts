class TreeNode {
    val: number;
    left: TreeNode | null;
    right: TreeNode | null;
    
    constructor(val?: number, left?: TreeNode | null, right?: TreeNode | null) {
        this.val = val ?? 0;
        this.left = left ?? null;
        this.right = right ?? null;
    }
}

// 1. Binary Tree Inorder Traversal
function inorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    const inorder = (node: TreeNode | null): void => {
        if (node) {
            inorder(node.left);
            result.push(node.val);
            inorder(node.right);
        }
    };
    inorder(root);
    return result;
}

// 2. Binary Tree Preorder Traversal
function preorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    const preorder = (node: TreeNode | null): void => {
        if (node) {
            result.push(node.val);
            preorder(node.left);
            preorder(node.right);
        }
    };
    preorder(root);
    return result;
}

// 3. Binary Tree Postorder Traversal
function postorderTraversal(root: TreeNode | null): number[] {
    const result: number[] = [];
    const postorder = (node: TreeNode | null): void => {
        if (node) {
            postorder(node.left);
            postorder(node.right);
            result.push(node.val);
        }
    };
    postorder(root);
    return result;
}

// 4. Maximum Depth of Binary Tree
function maxDepth(root: TreeNode | null): number {
    return root ? 1 + Math.max(maxDepth(root.left), maxDepth(root.right)) : 0;
}

// 5. Diameter of Binary Tree
function diameterOfBinaryTree(root: TreeNode | null): number {
    let diameter = 0;
    const dfs = (node: TreeNode | null): number => {
        if (!node) return 0;
        const left = dfs(node.left);
        const right = dfs(node.right);
        diameter = Math.max(diameter, left + right);
        return 1 + Math.max(left, right);
    };
    dfs(root);
    return diameter;
}

// 6. Binary Tree Maximum Path Sum
function maxPathSum(root: TreeNode | null): number {
    let maxSum = Number.MIN_SAFE_INTEGER;
    const maxGain = (node: TreeNode | null): number => {
        if (!node) return 0;
        const leftGain = Math.max(maxGain(node.left), 0);
        const rightGain = Math.max(maxGain(node.right), 0);
        const pathSum = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, pathSum);
        return node.val + Math.max(leftGain, rightGain);
    };
    maxGain(root);
    return maxSum;
}

// 7. Path Sum
function hasPathSum(root: TreeNode | null, targetSum: number): boolean {
    if (!root) return false;
    if (!root.left && !root.right) return root.val === targetSum;
    return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
}

// 8. Path Sum II
function pathSum(root: TreeNode | null, targetSum: number): number[][] {
    const result: number[][] = [];
    const dfs = (node: TreeNode | null, sum: number, path: number[]): void => {
        if (!node) return;
        path.push(node.val);
        if (!node.left && !node.right && sum === node.val) {
            result.push([...path]);
        } else {
            dfs(node.left, sum - node.val, path);
            dfs(node.right, sum - node.val, path);
        }
        path.pop();
    };
    dfs(root, targetSum, []);
    return result;
}

// 9. Binary Tree Paths
function binaryTreePaths(root: TreeNode | null): string[] {
    const result: string[] = [];
    const dfs = (node: TreeNode | null, path: string): void => {
        if (!node) return;
        if (!node.left && !node.right) {
            result.push(path + node.val);
        } else {
            const newPath = path + node.val + "->";
            dfs(node.left, newPath);
            dfs(node.right, newPath);
        }
    };
    dfs(root, "");
    return result;
}

// 10. Path Sum III
function pathSumIII(root: TreeNode | null, targetSum: number): number {
    const dfs = (node: TreeNode | null, sum: number): number => {
        if (!node) return 0;
        return (node.val === sum ? 1 : 0) + dfs(node.left, sum - node.val) + dfs(node.right, sum - node.val);
    };
    if (!root) return 0;
    return dfs(root, targetSum) + pathSumIII(root.left, targetSum) + pathSumIII(root.right, targetSum);
}

// Test cases
console.log("=== Tree DFS Examples ===");

// Create test tree: [3,9,20,null,null,15,7]
const root = new TreeNode(3);
root.left = new TreeNode(9);
root.right = new TreeNode(20);
root.right.left = new TreeNode(15);
root.right.right = new TreeNode(7);

// Test 1: Binary Tree Inorder Traversal
console.log("1. Inorder Traversal:", inorderTraversal(root));

// Test 2: Binary Tree Preorder Traversal
console.log("2. Preorder Traversal:", preorderTraversal(root));

// Test 3: Binary Tree Postorder Traversal
console.log("3. Postorder Traversal:", postorderTraversal(root));

// Test 4: Maximum Depth of Binary Tree
console.log("4. Max Depth:", maxDepth(root));

// Test 5: Diameter of Binary Tree
console.log("5. Diameter:", diameterOfBinaryTree(root));

// Test 6: Binary Tree Maximum Path Sum
console.log("6. Max Path Sum:", maxPathSum(root));

// Test 7: Path Sum
console.log("7. Has Path Sum (12):", hasPathSum(root, 12));

// Test 8: Path Sum II
console.log("8. Path Sum II (12):", pathSum(root, 12));

// Test 9: Binary Tree Paths
console.log("9. Binary Tree Paths:", binaryTreePaths(root));

// Test 10: Path Sum III
console.log("10. Path Sum III (3):", pathSumIII(root, 3));