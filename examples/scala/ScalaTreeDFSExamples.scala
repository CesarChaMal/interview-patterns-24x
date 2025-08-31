import scala.collection.mutable

object ScalaTreeDFSExamples {
  
  final class TreeNode(var value: Int, var left: TreeNode, var right: TreeNode) {
    def this(value: Int) = this(value, null, null)
  }
  object TreeNode {
    def apply(value: Int): TreeNode = new TreeNode(value)
    def apply(value: Int, left: TreeNode): TreeNode = new TreeNode(value, left, null)
    def apply(value: Int, left: TreeNode, right: TreeNode): TreeNode = new TreeNode(value, left, right)
  }

  // 1. Binary Tree Inorder Traversal
  def inorderTraversal(root: TreeNode): List[Int] = {
    val result = mutable.ListBuffer[Int]()
    
    def dfs(node: TreeNode): Unit = {
      if (node != null) {
        dfs(node.left)
        result += node.value
        dfs(node.right)
      }
    }
    
    dfs(root)
    result.toList
  }
  
  // 2. Maximum Depth of Binary Tree
  def maxDepth(root: TreeNode): Int = {
    if (root == null) 0
    else 1 + math.max(maxDepth(root.left), maxDepth(root.right))
  }
  
  // 3. Diameter of Binary Tree
  def diameterOfBinaryTree(root: TreeNode): Int = {
    var maxDiameter = 0
    
    def dfs(node: TreeNode): Int = {
      if (node == null) return 0
      
      val leftHeight = dfs(node.left)
      val rightHeight = dfs(node.right)
      
      maxDiameter = math.max(maxDiameter, leftHeight + rightHeight)
      1 + math.max(leftHeight, rightHeight)
    }
    
    dfs(root)
    maxDiameter
  }
  
  // 4. Path Sum
  def hasPathSum(root: TreeNode, targetSum: Int): Boolean = {
    if (root == null) return false
    if (root.left == null && root.right == null) return root.value == targetSum
    
    val remaining = targetSum - root.value
    hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining)
  }
  
  // 5. Binary Tree Maximum Path Sum
  def maxPathSum(root: TreeNode): Int = {
    var maxSum = Int.MinValue
    
    def dfs(node: TreeNode): Int = {
      if (node == null) return 0
      
      val leftGain = math.max(dfs(node.left), 0)
      val rightGain = math.max(dfs(node.right), 0)
      
      val currentMax = node.value + leftGain + rightGain
      maxSum = math.max(maxSum, currentMax)
      
      node.value + math.max(leftGain, rightGain)
    }
    
    dfs(root)
    maxSum
  }
  
  // 6. Lowest Common Ancestor
  def lowestCommonAncestor(root: TreeNode, p: TreeNode, q: TreeNode): TreeNode = {
    if (root == null || root == p || root == q) return root
    
    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)
    
    if (left != null && right != null) root
    else if (left != null) left
    else right
  }
  
  // 7. Serialize and Deserialize Binary Tree
  def serialize(root: TreeNode): String = {
    val result = mutable.ListBuffer[String]()
    
    def dfs(node: TreeNode): Unit = {
      if (node == null) {
        result += "null"
      } else {
        result += node.value.toString
        dfs(node.left)
        dfs(node.right)
      }
    }
    
    dfs(root)
    result.mkString(",")
  }
  
  def deserialize(data: String): TreeNode = {
    val values = data.split(",").iterator
    
    def dfs(): TreeNode = {
      val value = values.next()
      if (value == "null") {
        null
      } else {
        val node = TreeNode(value.toInt)
        node.left = dfs()
        node.right = dfs()
        node
      }
    }
    
    dfs()
  }
  
  // 8. Validate Binary Search Tree
  def isValidBST(root: TreeNode): Boolean = {
    def validate(node: TreeNode, min: Long, max: Long): Boolean = {
      if (node == null) return true
      if (node.value <= min || node.value >= max) return false
      
      validate(node.left, min, node.value) && validate(node.right, node.value, max)
    }
    
    validate(root, Long.MinValue, Long.MaxValue)
  }
  
  // 9. Binary Tree Right Side View
  def rightSideView(root: TreeNode): List[Int] = {
    val result = mutable.ListBuffer[Int]()
    
    def dfs(node: TreeNode, level: Int): Unit = {
      if (node != null) {
        if (level == result.length) {
          result += node.value
        }
        dfs(node.right, level + 1)
        dfs(node.left, level + 1)
      }
    }
    
    dfs(root, 0)
    result.toList
  }
  
  // 10. Count Good Nodes in Binary Tree
  def goodNodes(root: TreeNode): Int = {
    def dfs(node: TreeNode, maxSoFar: Int): Int = {
      if (node == null) return 0
      
      val isGood = if (node.value >= maxSoFar) 1 else 0
      val newMax = math.max(maxSoFar, node.value)
      
      isGood + dfs(node.left, newMax) + dfs(node.right, newMax)
    }
    
    dfs(root, Int.MinValue)
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Tree DFS Examples ===")
    
    // Create test tree: [3,9,20,null,null,15,7]
    val root = TreeNode(3, TreeNode(9), TreeNode(20, TreeNode(15), TreeNode(7)))
    
    // Test 1: Binary Tree Inorder Traversal
    println("Test 1 - Inorder Traversal: " + inorderTraversal(root))
    
    // Test 2: Maximum Depth of Binary Tree
    println("Test 2 - Max Depth: " + maxDepth(root))
    
    // Test 3: Diameter of Binary Tree
    println("Test 3 - Diameter: " + diameterOfBinaryTree(root))
    
    // Test 4: Path Sum
    println("Test 4 - Has Path Sum(22): " + hasPathSum(root, 22))
    
    // Test 5: Binary Tree Maximum Path Sum
    println("Test 5 - Max Path Sum: " + maxPathSum(root))
    
    // Test 6: Lowest Common Ancestor
    val p = root.left
    val q = root.right.left
    println("Test 6 - LCA: " + (if (lowestCommonAncestor(root, p, q) != null) "Found" else "Not Found"))
    
    // Test 7: Serialize and Deserialize Binary Tree
    val serialized = serialize(root)
    val deserialized = deserialize(serialized)
    println("Test 7 - Serialize/Deserialize: " + (if (deserialized != null) "Success" else "Failed"))
    
    // Test 8: Validate Binary Search Tree
    val bst = TreeNode(2, TreeNode(1), TreeNode(3))
    println("Test 8 - Is Valid BST: " + isValidBST(bst))
    
    // Test 9: Binary Tree Right Side View
    println("Test 9 - Right Side View: " + rightSideView(root))
    
    // Test 10: Count Good Nodes in Binary Tree
    val goodTree = TreeNode(3, TreeNode(1, TreeNode(3)), TreeNode(4, TreeNode(1), TreeNode(5)))
    println("Test 10 - Good Nodes: " + goodNodes(goodTree))
  }
}