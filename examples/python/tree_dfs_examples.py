class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

# 1. Preorder Traversal
def preorder_traversal(root):
    result = []
    def dfs(node):
        if node:
            result.append(node.val)
            dfs(node.left)
            dfs(node.right)
    dfs(root)
    return result

# 2. Inorder Traversal
def inorder_traversal(root):
    result = []
    def dfs(node):
        if node:
            dfs(node.left)
            result.append(node.val)
            dfs(node.right)
    dfs(root)
    return result

# 3. Postorder Traversal
def postorder_traversal(root):
    result = []
    def dfs(node):
        if node:
            dfs(node.left)
            dfs(node.right)
            result.append(node.val)
    dfs(root)
    return result

# 4. Max Depth
def max_depth(root):
    if not root:
        return 0
    return 1 + max(max_depth(root.left), max_depth(root.right))

# 5. Is Same Tree
def is_same_tree(p, q):
    if not p and not q:
        return True
    if not p or not q or p.val != q.val:
        return False
    return is_same_tree(p.left, q.left) and is_same_tree(p.right, q.right)

# 6. Is Symmetric
def is_symmetric(root):
    def is_mirror(left, right):
        if not left and not right:
            return True
        if not left or not right or left.val != right.val:
            return False
        return is_mirror(left.left, right.right) and is_mirror(left.right, right.left)
    return not root or is_mirror(root.left, root.right)

# 7. Has Path Sum
def has_path_sum(root, target_sum):
    if not root:
        return False
    if not root.left and not root.right:
        return root.val == target_sum
    return (has_path_sum(root.left, target_sum - root.val) or 
            has_path_sum(root.right, target_sum - root.val))

# 8. Diameter Of Binary Tree
def diameter_of_binary_tree(root):
    max_diameter = 0
    def dfs(node):
        nonlocal max_diameter
        if not node:
            return 0
        left = dfs(node.left)
        right = dfs(node.right)
        max_diameter = max(max_diameter, left + right)
        return 1 + max(left, right)
    dfs(root)
    return max_diameter

# 9. Lowest Common Ancestor
def lowest_common_ancestor(root, p, q):
    if not root or root == p or root == q:
        return root
    left = lowest_common_ancestor(root.left, p, q)
    right = lowest_common_ancestor(root.right, p, q)
    return root if left and right else left or right

# 10. Binary Tree Paths
def binary_tree_paths(root):
    result = []
    def dfs(node, path):
        if node:
            path += str(node.val)
            if not node.left and not node.right:
                result.append(path)
            else:
                path += "->"
                dfs(node.left, path)
                dfs(node.right, path)
    dfs(root, "")
    return result

# 11. Is Valid Bst
def is_valid_bst(root):
    def validate(node, min_val, max_val):
        if not node:
            return True
        if node.val <= min_val or node.val >= max_val:
            return False
        return (validate(node.left, min_val, node.val) and 
                validate(node.right, node.val, max_val))
    return validate(root, float('-inf'), float('inf'))

# 12. Sum Root To Leaf Numbers
def sum_root_to_leaf_numbers(root):
    def dfs(node, current_sum):
        if not node:
            return 0
        current_sum = current_sum * 10 + node.val
        if not node.left and not node.right:
            return current_sum
        return dfs(node.left, current_sum) + dfs(node.right, current_sum)
    return dfs(root, 0)

# 13. Run Examples
def run_examples():
    print("=== Tree DFS Examples ===")
    
    # Create test tree: [3,9,20,null,null,15,7]
    root = TreeNode(3)
    root.left = TreeNode(9)
    root.right = TreeNode(20)
    root.right.left = TreeNode(15)
    root.right.right = TreeNode(7)
    
    # Test 1: Preorder Traversal
    print(f"1. Preorder: {preorder_traversal(root)}")
    
    # Test 2: Inorder Traversal
    print(f"2. Inorder: {inorder_traversal(root)}")
    
    # Test 3: Postorder Traversal
    print(f"3. Postorder: {postorder_traversal(root)}")
    
    # Test 4: Max Depth
    print(f"4. Max Depth: {max_depth(root)}")
    
    # Test 5: Same Tree
    root2 = TreeNode(3)
    root2.left = TreeNode(9)
    root2.right = TreeNode(20)
    print(f"5. Is Same Tree: {is_same_tree(root, root2)}")
    
    # Test 6: Symmetric Tree
    sym_root = TreeNode(1)
    sym_root.left = TreeNode(2)
    sym_root.right = TreeNode(2)
    print(f"6. Is Symmetric: {is_symmetric(sym_root)}")
    
    # Test 7: Path Sum
    print(f"7. Has Path Sum (12): {has_path_sum(root, 12)}")
    
    # Test 8: Diameter
    print(f"8. Diameter: {diameter_of_binary_tree(root)}")
    
    # Test 9: LCA
    p = root.left
    q = root.right.left
    lca = lowest_common_ancestor(root, p, q)
    print(f"9. LCA of 9 and 15: {lca.val if lca else None}")
    
    # Test 10: Binary Tree Paths
    print(f"10. Binary Tree Paths: {binary_tree_paths(root)}")
    
    # Bonus: Valid BST
    bst_root = TreeNode(2)
    bst_root.left = TreeNode(1)
    bst_root.right = TreeNode(3)
    print(f"Valid BST: {is_valid_bst(bst_root)}")
    
    # Bonus: Sum Root to Leaf
    num_root = TreeNode(1)
    num_root.left = TreeNode(2)
    num_root.right = TreeNode(3)
    print(f"Sum Root to Leaf: {sum_root_to_leaf_numbers(num_root)}")

if __name__ == "__main__":
    run_examples()