// TreeNode class
class TreeNode {
    constructor(val, left, right) {
        this.val = val === undefined ? 0 : val;
        this.left = left === undefined ? null : left;
        this.right = right === undefined ? null : right;
    }
}

// 1. Binary Tree Inorder Traversal
function inorderTraversal(root) {
    const result = [];
    
    function inorder(node) {
        if (node) {
            inorder(node.left);
            result.push(node.val);
            inorder(node.right);
        }
    }
    
    inorder(root);
    return result;
}

// 2. Binary Tree Preorder Traversal
function preorderTraversal(root) {
    const result = [];
    
    function preorder(node) {
        if (node) {
            result.push(node.val);
            preorder(node.left);
            preorder(node.right);
        }
    }
    
    preorder(root);
    return result;
}

// 3. Binary Tree Postorder Traversal
function postorderTraversal(root) {
    const result = [];
    
    function postorder(node) {
        if (node) {
            postorder(node.left);
            postorder(node.right);
            result.push(node.val);
        }
    }
    
    postorder(root);
    return result;
}

// 4. Maximum Depth of Binary Tree
function maxDepth(root) {
    if (!root) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}

// 5. Validate Binary Search Tree
function isValidBST(root) {
    function validate(node, min, max) {
        if (!node) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }
    
    return validate(root, -Infinity, Infinity);
}

// 6. Symmetric Tree
function isSymmetric(root) {
    function isMirror(left, right) {
        if (!left && !right) return true;
        if (!left || !right) return false;
        return left.val === right.val && 
               isMirror(left.left, right.right) && 
               isMirror(left.right, right.left);
    }
    
    return !root || isMirror(root.left, root.right);
}

// 7. Path Sum
function hasPathSum(root, targetSum) {
    if (!root) return false;
    if (!root.left && !root.right) return root.val === targetSum;
    return hasPathSum(root.left, targetSum - root.val) || 
           hasPathSum(root.right, targetSum - root.val);
}

// 8. Binary Tree Maximum Path Sum
function maxPathSum(root) {
    let maxSum = -Infinity;
    
    function maxGain(node) {
        if (!node) return 0;
        
        const leftGain = Math.max(maxGain(node.left), 0);
        const rightGain = Math.max(maxGain(node.right), 0);
        
        const pathSum = node.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, pathSum);
        
        return node.val + Math.max(leftGain, rightGain);
    }
    
    maxGain(root);
    return maxSum;
}

// 9. Lowest Common Ancestor of Binary Tree
function lowestCommonAncestor(root, p, q) {
    if (!root || root === p || root === q) return root;
    
    const left = lowestCommonAncestor(root.left, p, q);
    const right = lowestCommonAncestor(root.right, p, q);
    
    if (left && right) return root;
    return left || right;
}

// 10. Diameter of Binary Tree
function diameterOfBinaryTree(root) {
    let diameter = 0;
    
    function depth(node) {
        if (!node) return 0;
        
        const leftDepth = depth(node.left);
        const rightDepth = depth(node.right);
        
        diameter = Math.max(diameter, leftDepth + rightDepth);
        
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    depth(root);
    return diameter;
}

// Helper function to create test tree
function createTestTree() {
    // Create tree: [3,9,20,null,null,15,7]
    const root = new TreeNode(3);
    root.left = new TreeNode(9);
    root.right = new TreeNode(20);
    root.right.left = new TreeNode(15);
    root.right.right = new TreeNode(7);
    return root;
}

// Test cases
console.log("=== Tree DFS Examples ===");

const root = createTestTree();

console.log("Inorder Traversal:", inorderTraversal(root));
console.log("Preorder Traversal:", preorderTraversal(root));
console.log("Postorder Traversal:", postorderTraversal(root));
console.log("Max Depth:", maxDepth(root));
console.log("Is Valid BST:", isValidBST(root));
console.log("Is Symmetric:", isSymmetric(root));
console.log("Has Path Sum (target=12):", hasPathSum(root, 12));
console.log("Max Path Sum:", maxPathSum(root));
console.log("Diameter:", diameterOfBinaryTree(root));

// Create BST for testing
const bst = new TreeNode(5);
bst.left = new TreeNode(3);
bst.right = new TreeNode(8);
bst.left.left = new TreeNode(2);
bst.left.right = new TreeNode(4);
bst.right.left = new TreeNode(7);
bst.right.right = new TreeNode(9);

console.log("BST Is Valid:", isValidBST(bst));

// Test LCA
const p = bst.left;  // node 3
const q = bst.left.right;  // node 4
console.log("LCA of 3 and 4:", lowestCommonAncestor(bst, p, q).val);

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        TreeNode,
        inorderTraversal,
        preorderTraversal,
        postorderTraversal,
        maxDepth,
        isValidBST,
        isSymmetric,
        hasPathSum,
        maxPathSum,
        lowestCommonAncestor,
        diameterOfBinaryTree
    };
}