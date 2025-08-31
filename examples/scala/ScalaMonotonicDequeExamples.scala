import scala.collection.mutable

object ScalaMonotonicDequeExamples {
  
  // 1. Sliding Window Maximum
  def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val deque = mutable.ArrayDeque[Int]()
    val result = Array.ofDim[Int](nums.length - k + 1)
    
    for (i <- nums.indices) {
      // Remove elements outside window
      while (deque.nonEmpty && deque.head < i - k + 1) {
        deque.removeHead()
      }
      
      // Remove smaller elements
      while (deque.nonEmpty && nums(deque.last) < nums(i)) {
        deque.removeLast()
      }
      
      deque.append(i)
      
      if (i >= k - 1) {
        result(i - k + 1) = nums(deque.head)
      }
    }
    
    result
  }
  
  // 2. Sliding Window Minimum
  def minSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val deque = mutable.ArrayDeque[Int]()
    val result = Array.ofDim[Int](nums.length - k + 1)
    
    for (i <- nums.indices) {
      while (deque.nonEmpty && deque.head < i - k + 1) {
        deque.removeHead()
      }
      
      while (deque.nonEmpty && nums(deque.last) > nums(i)) {
        deque.removeLast()
      }
      
      deque.append(i)
      
      if (i >= k - 1) {
        result(i - k + 1) = nums(deque.head)
      }
    }
    
    result
  }
  
  // 3. Constrained Subsequence Sum
  def constrainedSubsetSum(nums: Array[Int], k: Int): Int = {
    val deque = mutable.ArrayDeque[Int]()
    val dp = Array.ofDim[Int](nums.length)
    
    for (i <- nums.indices) {
      // Remove elements outside window
      while (deque.nonEmpty && deque.head < i - k) {
        deque.removeHead()
      }
      
      dp(i) = nums(i) + (if (deque.nonEmpty) math.max(0, dp(deque.head)) else 0)
      
      // Maintain decreasing order
      while (deque.nonEmpty && dp(deque.last) <= dp(i)) {
        deque.removeLast()
      }
      
      deque.append(i)
    }
    
    dp.max
  }
  
  // 4. Shortest Subarray with Sum at Least K
  def shortestSubarray(nums: Array[Int], k: Int): Int = {
    val prefixSum = Array.ofDim[Long](nums.length + 1)
    for (i <- nums.indices) {
      prefixSum(i + 1) = prefixSum(i) + nums(i)
    }
    
    val deque = mutable.ArrayDeque[Int]()
    var minLen = Int.MaxValue
    
    for (i <- prefixSum.indices) {
      // Check if we can form a valid subarray
      while (deque.nonEmpty && prefixSum(i) - prefixSum(deque.head) >= k) {
        minLen = math.min(minLen, i - deque.removeHead())
      }
      
      // Maintain increasing order of prefix sums
      while (deque.nonEmpty && prefixSum(deque.last) >= prefixSum(i)) {
        deque.removeLast()
      }
      
      deque.append(i)
    }
    
    if (minLen == Int.MaxValue) -1 else minLen
  }
  
  // 5. Jump Game VI
  def maxResult(nums: Array[Int], k: Int): Int = {
    val deque = mutable.ArrayDeque[Int]()
    val dp = Array.ofDim[Int](nums.length)
    dp(0) = nums(0)
    deque.append(0)
    
    for (i <- 1 until nums.length) {
      // Remove elements outside window
      while (deque.nonEmpty && deque.head < i - k) {
        deque.removeHead()
      }
      
      dp(i) = nums(i) + dp(deque.head)
      
      // Maintain decreasing order
      while (deque.nonEmpty && dp(deque.last) <= dp(i)) {
        deque.removeLast()
      }
      
      deque.append(i)
    }
    
    dp.last
  }
  
  // 6. Largest Rectangle in Histogram
  def largestRectangleArea(heights: Array[Int]): Int = {
    val deque = mutable.ArrayDeque[Int]()
    var maxArea = 0
    
    for (i <- 0 to heights.length) {
      val h = if (i == heights.length) 0 else heights(i)
      
      while (deque.nonEmpty && heights(deque.last) > h) {
        val height = heights(deque.removeLast())
        val width = if (deque.isEmpty) i else i - deque.last - 1
        maxArea = math.max(maxArea, height * width)
      }
      
      deque.append(i)
    }
    
    maxArea
  }
  
  // 7. Maximal Rectangle
  def maximalRectangle(matrix: Array[Array[Char]]): Int = {
    if (matrix.isEmpty) return 0
    
    val heights = Array.fill(matrix(0).length)(0)
    var maxArea = 0
    
    for (row <- matrix) {
      for (i <- row.indices) {
        heights(i) = if (row(i) == '1') heights(i) + 1 else 0
      }
      maxArea = math.max(maxArea, largestRectangleArea(heights))
    }
    
    maxArea
  }
  
  // 8. Remove K Digits
  def removeKdigits(num: String, k: Int): String = {
    val deque = mutable.ArrayDeque[Char]()
    var toRemove = k
    
    for (digit <- num) {
      while (deque.nonEmpty && deque.last > digit && toRemove > 0) {
        deque.removeLast()
        toRemove -= 1
      }
      deque.append(digit)
    }
    
    // Remove remaining digits from the end
    while (toRemove > 0) {
      deque.removeLast()
      toRemove -= 1
    }
    
    // Remove leading zeros
    while (deque.nonEmpty && deque.head == '0') {
      deque.removeHead()
    }
    
    if (deque.isEmpty) "0" else deque.mkString
  }
  
  // 9. Create Maximum Number
  def maxNumber(nums1: Array[Int], nums2: Array[Int], k: Int): Array[Int] = {
    def maxSubsequence(nums: Array[Int], k: Int): Array[Int] = {
      val deque = mutable.ArrayDeque[Int]()
      var toRemove = nums.length - k
      
      for (num <- nums) {
        while (deque.nonEmpty && deque.last < num && toRemove > 0) {
          deque.removeLast()
          toRemove -= 1
        }
        deque.append(num)
      }
      
      deque.take(k).toArray
    }
    
    def merge(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
      val result = Array.ofDim[Int](nums1.length + nums2.length)
      var i = 0
      var j = 0
      var k = 0
      
      while (i < nums1.length && j < nums2.length) {
        if (isGreater(nums1, i, nums2, j)) {
          result(k) = nums1(i)
          i += 1
        } else {
          result(k) = nums2(j)
          j += 1
        }
        k += 1
      }
      
      while (i < nums1.length) {
        result(k) = nums1(i)
        i += 1
        k += 1
      }
      
      while (j < nums2.length) {
        result(k) = nums2(j)
        j += 1
        k += 1
      }
      
      result
    }
    
    def isGreater(nums1: Array[Int], i: Int, nums2: Array[Int], j: Int): Boolean = {
      var x = i
      var y = j
      while (x < nums1.length && y < nums2.length && nums1(x) == nums2(y)) {
        x += 1
        y += 1
      }
      if (y == nums2.length) true
      else if (x == nums1.length) false
      else nums1(x) > nums2(y)
    }
    
    var result = Array[Int]()
    
    for (i <- math.max(0, k - nums2.length) to math.min(k, nums1.length)) {
      val candidate = merge(maxSubsequence(nums1, i), maxSubsequence(nums2, k - i))
      if (result.isEmpty || isGreater(candidate, 0, result, 0)) {
        result = candidate
      }
    }
    
    result
  }
  
  // 10. Minimum Window Subsequence
  def minWindow(s: String, t: String): String = {
    val deque = mutable.ArrayDeque[(Int, Int)]() // (index in s, index in t)
    var minLen = Int.MaxValue
    var result = ""
    
    for (i <- s.indices) {
      if (t.contains(s(i))) {
        // Remove elements that won't lead to optimal solution
        while (deque.nonEmpty && s(i) == t(deque.last._2) && deque.last._2 <= t.indexOf(s(i))) {
          deque.removeLast()
        }
        
        if (deque.isEmpty || deque.last._2 < t.length - 1) {
          val nextIndex = if (deque.isEmpty) 0 else deque.last._2 + 1
          if (nextIndex < t.length && s(i) == t(nextIndex)) {
            deque.append((i, nextIndex))
          }
        }
        
        if (deque.nonEmpty && deque.last._2 == t.length - 1) {
          val len = i - deque.head._1 + 1
          if (len < minLen) {
            minLen = len
            result = s.substring(deque.head._1, i + 1)
          }
        }
      }
    }
    
    result
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Monotonic Deque Examples ===")
    
    // Test 1: Sliding Window Maximum
    println("Test 1 - Sliding Window Max([1,3,-1,-3,5,3,6,7], k=3): " + maxSlidingWindow(Array(1,3,-1,-3,5,3,6,7), 3).mkString("[", ",", "]"))
    
    // Test 2: Sliding Window Minimum
    println("Test 2 - Sliding Window Min([1,3,-1,-3,5,3,6,7], k=3): " + minSlidingWindow(Array(1,3,-1,-3,5,3,6,7), 3).mkString("[", ",", "]"))
    
    // Test 3: Constrained Subsequence Sum
    println("Test 3 - Constrained Subset Sum([10,2,-10,5,20], k=2): " + constrainedSubsetSum(Array(10,2,-10,5,20), 2))
    
    // Test 4: Shortest Subarray with Sum at Least K
    println("Test 4 - Shortest Subarray([1], k=1): " + shortestSubarray(Array(1), 1))
    
    // Test 5: Jump Game VI
    println("Test 5 - Max Result([1,-1,-2,4,-7,3], k=2): " + maxResult(Array(1,-1,-2,4,-7,3), 2))
    
    // Test 6: Largest Rectangle in Histogram
    println("Test 6 - Largest Rectangle([2,1,5,6,2,3]): " + largestRectangleArea(Array(2,1,5,6,2,3)))
    
    // Test 7: Maximal Rectangle
    val matrix = Array(Array('1','0','1','0','0'),Array('1','0','1','1','1'),Array('1','1','1','1','1'),Array('1','0','0','1','0'))
    println("Test 7 - Maximal Rectangle: " + maximalRectangle(matrix))
    
    // Test 8: Remove K Digits
    println("Test 8 - Remove K Digits('1432219', k=3): " + removeKdigits("1432219", 3))
    
    // Test 9: Create Maximum Number
    println("Test 9 - Max Number([3,4,6,5], [9,1,2,5,8,3], k=5): " + maxNumber(Array(3,4,6,5), Array(9,1,2,5,8,3), 5).mkString("[", ",", "]"))
    
    // Test 10: Minimum Window Subsequence
    println("Test 10 - Min Window('abcdebdde', 'bde'): " + minWindow("abcdebdde", "bde"))
  }
}