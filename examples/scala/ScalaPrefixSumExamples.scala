import scala.collection.mutable

object ScalaPrefixSumExamples {
  
  // 1. Subarray Sum Equals K
  def subarraySum(nums: Array[Int], k: Int): Int = {
    val prefixSumCount = mutable.Map[Int, Int]()
    prefixSumCount(0) = 1
    var prefixSum = 0
    var count = 0
    
    for (num <- nums) {
      prefixSum += num
      count += prefixSumCount.getOrElse(prefixSum - k, 0)
      prefixSumCount(prefixSum) = prefixSumCount.getOrElse(prefixSum, 0) + 1
    }
    
    count
  }
  
  // 2. Continuous Subarray Sum
  def checkSubarraySum(nums: Array[Int], k: Int): Boolean = {
    val remainderMap = mutable.Map[Int, Int]()
    remainderMap(0) = -1
    var prefixSum = 0
    
    for (i <- nums.indices) {
      prefixSum += nums(i)
      val remainder = prefixSum % k
      
      if (remainderMap.contains(remainder)) {
        if (i - remainderMap(remainder) > 1) return true
      } else {
        remainderMap(remainder) = i
      }
    }
    
    false
  }
  
  // 3. Range Sum Query - Immutable
  class NumArray(nums: Array[Int]) {
    private val prefixSums = Array.ofDim[Int](nums.length + 1)
    
    for (i <- nums.indices) {
      prefixSums(i + 1) = prefixSums(i) + nums(i)
    }
    
    def sumRange(left: Int, right: Int): Int = {
      prefixSums(right + 1) - prefixSums(left)
    }
  }
  
  // 4. Product of Array Except Self
  def productExceptSelf(nums: Array[Int]): Array[Int] = {
    val result = Array.ofDim[Int](nums.length)
    
    // Left products
    result(0) = 1
    for (i <- 1 until nums.length) {
      result(i) = result(i - 1) * nums(i - 1)
    }
    
    // Right products
    var right = 1
    for (i <- nums.length - 1 to 0 by -1) {
      result(i) *= right
      right *= nums(i)
    }
    
    result
  }
  
  // 5. Find Pivot Index
  def pivotIndex(nums: Array[Int]): Int = {
    val totalSum = nums.sum
    var leftSum = 0
    
    for (i <- nums.indices) {
      if (leftSum == totalSum - leftSum - nums(i)) {
        return i
      }
      leftSum += nums(i)
    }
    
    -1
  }
  
  // 6. Subarray Sums Divisible by K
  def subarraysDivByK(nums: Array[Int], k: Int): Int = {
    val remainderCount = mutable.Map[Int, Int]()
    remainderCount(0) = 1
    var prefixSum = 0
    var count = 0
    
    for (num <- nums) {
      prefixSum += num
      val remainder = ((prefixSum % k) + k) % k
      count += remainderCount.getOrElse(remainder, 0)
      remainderCount(remainder) = remainderCount.getOrElse(remainder, 0) + 1
    }
    
    count
  }
  
  // 7. Maximum Size Subarray Sum Equals k
  def maxSubArrayLen(nums: Array[Int], k: Int): Int = {
    val sumIndexMap = mutable.Map[Int, Int]()
    sumIndexMap(0) = -1
    var prefixSum = 0
    var maxLen = 0
    
    for (i <- nums.indices) {
      prefixSum += nums(i)
      
      if (sumIndexMap.contains(prefixSum - k)) {
        maxLen = math.max(maxLen, i - sumIndexMap(prefixSum - k))
      }
      
      if (!sumIndexMap.contains(prefixSum)) {
        sumIndexMap(prefixSum) = i
      }
    }
    
    maxLen
  }
  
  // 8. Binary Subarrays With Sum
  def numSubarraysWithSum(nums: Array[Int], goal: Int): Int = {
    val prefixSumCount = mutable.Map[Int, Int]()
    prefixSumCount(0) = 1
    var prefixSum = 0
    var count = 0
    
    for (num <- nums) {
      prefixSum += num
      count += prefixSumCount.getOrElse(prefixSum - goal, 0)
      prefixSumCount(prefixSum) = prefixSumCount.getOrElse(prefixSum, 0) + 1
    }
    
    count
  }
  
  // 9. Contiguous Array
  def findMaxLength(nums: Array[Int]): Int = {
    val sumIndexMap = mutable.Map[Int, Int]()
    sumIndexMap(0) = -1
    var balance = 0
    var maxLen = 0
    
    for (i <- nums.indices) {
      balance += (if (nums(i) == 1) 1 else -1)
      
      if (sumIndexMap.contains(balance)) {
        maxLen = math.max(maxLen, i - sumIndexMap(balance))
      } else {
        sumIndexMap(balance) = i
      }
    }
    
    maxLen
  }
  
  // 10. Path Sum III
  final class TreeNode(var value: Int, var left: TreeNode, var right: TreeNode) {
    def this(value: Int) = this(value, null, null)
  }
  object TreeNode {
    def apply(value: Int): TreeNode = new TreeNode(value)
    def apply(value: Int, left: TreeNode): TreeNode = new TreeNode(value, left, null)
    def apply(value: Int, left: TreeNode, right: TreeNode): TreeNode = new TreeNode(value, left, right)
  }

  def pathSum(root: TreeNode, targetSum: Int): Int = {
    val prefixSumCount = mutable.Map[Long, Int]()
    prefixSumCount(0L) = 1
    
    def dfs(node: TreeNode, currentSum: Long): Int = {
      if (node == null) return 0
      
      val newSum = currentSum + node.value
      val count = prefixSumCount.getOrElse(newSum - targetSum, 0)
      
      prefixSumCount(newSum) = prefixSumCount.getOrElse(newSum, 0) + 1
      
      val result = count + dfs(node.left, newSum) + dfs(node.right, newSum)
      
      prefixSumCount(newSum) = prefixSumCount(newSum) - 1
      if (prefixSumCount(newSum) == 0) {
        prefixSumCount.remove(newSum)
      }
      
      result
    }
    
    dfs(root, 0L)
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Prefix Sum Examples ===")
    
    // Test 1: Subarray Sum Equals K
    println("Test 1 - Subarray Sum Equals K([1,1,1], k=2): " + subarraySum(Array(1,1,1), 2))
    
    // Test 2: Continuous Subarray Sum
    println("Test 2 - Check Subarray Sum([23,2,4,6,7], k=6): " + checkSubarraySum(Array(23,2,4,6,7), 6))
    
    // Test 3: Range Sum Query - Immutable
    val numArray = new NumArray(Array(-2, 0, 3, -5, 2, -1))
    println("Test 3 - Sum Range(0, 2): " + numArray.sumRange(0, 2))
    
    // Test 4: Product of Array Except Self
    println("Test 4 - Product Except Self([1,2,3,4]): " + productExceptSelf(Array(1,2,3,4)).mkString("[", ",", "]"))
    
    // Test 5: Find Pivot Index
    println("Test 5 - Pivot Index([1,7,3,6,5,6]): " + pivotIndex(Array(1,7,3,6,5,6)))
    
    // Test 6: Subarray Sums Divisible by K
    println("Test 6 - Subarrays Div By K([4,5,0,-2,-3,1], k=5): " + subarraysDivByK(Array(4,5,0,-2,-3,1), 5))
    
    // Test 7: Maximum Size Subarray Sum Equals k
    println("Test 7 - Max SubArray Len([1,-1,5,-2,3], k=3): " + maxSubArrayLen(Array(1,-1,5,-2,3), 3))
    
    // Test 8: Binary Subarrays With Sum
    println("Test 8 - Num Subarrays With Sum([1,0,1,0,1], goal=2): " + numSubarraysWithSum(Array(1,0,1,0,1), 2))
    
    // Test 9: Contiguous Array
    println("Test 9 - Find Max Length([0,1]): " + findMaxLength(Array(0,1)))
    
    // Test 10: Path Sum III
    val root = TreeNode(10, TreeNode(5, TreeNode(3, TreeNode(3), TreeNode(-2)), TreeNode(2, null, TreeNode(1))), TreeNode(-3, null, TreeNode(11)))
    println("Test 10 - Path Sum III: " + pathSum(root, 8))
  }
}