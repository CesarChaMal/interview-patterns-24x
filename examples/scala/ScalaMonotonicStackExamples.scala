import scala.collection.mutable

object ScalaMonotonicStackExamples {
  
  // 1. Next Greater Element I
  def nextGreaterElement(nums1: Array[Int], nums2: Array[Int]): Array[Int] = {
    val stack = mutable.Stack[Int]()
    val map = mutable.Map[Int, Int]()
    
    for (num <- nums2) {
      while (stack.nonEmpty && stack.top < num) {
        map(stack.pop()) = num
      }
      stack.push(num)
    }
    
    nums1.map(map.getOrElse(_, -1))
  }
  
  // 2. Daily Temperatures
  def dailyTemperatures(temperatures: Array[Int]): Array[Int] = {
    val result = Array.fill(temperatures.length)(0)
    val stack = mutable.Stack[Int]()
    
    for (i <- temperatures.indices) {
      while (stack.nonEmpty && temperatures(stack.top) < temperatures(i)) {
        val idx = stack.pop()
        result(idx) = i - idx
      }
      stack.push(i)
    }
    
    result
  }
  
  // 3. Largest Rectangle in Histogram
  def largestRectangleArea(heights: Array[Int]): Int = {
    val stack = mutable.Stack[Int]()
    var maxArea = 0
    
    for (i <- 0 to heights.length) {
      val h = if (i == heights.length) 0 else heights(i)
      while (stack.nonEmpty && heights(stack.top) > h) {
        val height = heights(stack.pop())
        val width = if (stack.isEmpty) i else i - stack.top - 1
        maxArea = math.max(maxArea, height * width)
      }
      stack.push(i)
    }
    
    maxArea
  }
  
  // 4. Trapping Rain Water
  def trap(height: Array[Int]): Int = {
    val stack = mutable.Stack[Int]()
    var water = 0
    
    for (i <- height.indices) {
      while (stack.nonEmpty && height(i) > height(stack.top)) {
        val top = stack.pop()
        if (stack.nonEmpty) {
          val distance = i - stack.top - 1
          val boundedHeight = math.min(height(i), height(stack.top)) - height(top)
          water += distance * boundedHeight
        }
      }
      stack.push(i)
    }
    
    water
  }
  
  // 5. Remove Duplicate Letters
  def removeDuplicateLetters(s: String): String = {
    val count = Array.fill(26)(0)
    val inStack = Array.fill(26)(false)
    val stack = mutable.Stack[Char]()
    
    s.foreach(c => count(c - 'a') += 1)
    
    for (c <- s) {
      count(c - 'a') -= 1
      if (!inStack(c - 'a')) {
        while (stack.nonEmpty && stack.top > c && count(stack.top - 'a') > 0) {
          inStack(stack.pop() - 'a') = false
        }
        stack.push(c)
        inStack(c - 'a') = true
      }
    }
    
    stack.reverse.mkString
  }
  
  // 6. Next Greater Elements II
  def nextGreaterElements(nums: Array[Int]): Array[Int] = {
    val n = nums.length
    val result = Array.fill(n)(-1)
    val stack = mutable.Stack[Int]()
    
    for (i <- 0 until 2 * n) {
      while (stack.nonEmpty && nums(stack.top) < nums(i % n)) {
        result(stack.pop()) = nums(i % n)
      }
      if (i < n) stack.push(i)
    }
    
    result
  }
  
  // 7. Valid Parentheses
  def isValid(s: String): Boolean = {
    val stack = mutable.Stack[Char]()
    val mapping = Map(')' -> '(', '}' -> '{', ']' -> '[')
    
    for (c <- s) {
      if (mapping.contains(c)) {
        if (stack.isEmpty || stack.pop() != mapping(c)) return false
      } else {
        stack.push(c)
      }
    }
    
    stack.isEmpty
  }
  
  // 8. Maximal Rectangle
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
  
  // 9. Sum of Subarray Minimums
  def sumSubarrayMins(arr: Array[Int]): Int = {
    val MOD = 1000000007
    val stack = mutable.Stack[Int]()
    var result = 0L
    
    for (i <- 0 to arr.length) {
      while (stack.nonEmpty && (i == arr.length || arr(stack.top) >= arr(i))) {
        val mid = stack.pop()
        val left = if (stack.isEmpty) -1 else stack.top
        val right = i
        val count = (mid - left).toLong * (right - mid)
        result = (result + count * arr(mid)) % MOD
      }
      stack.push(i)
    }
    
    result.toInt
  }
  
  // 10. Minimum Stack
  class MinStack {
    private val stack = mutable.Stack[Int]()
    private val minStack = mutable.Stack[Int]()
    
    def push(x: Int): Unit = {
      stack.push(x)
      if (minStack.isEmpty || x <= minStack.top) {
        minStack.push(x)
      }
    }
    
    def pop(): Unit = {
      if (stack.pop() == minStack.top) {
        minStack.pop()
      }
    }
    
    def top(): Int = stack.top
    
    def getMin(): Int = minStack.top
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Monotonic Stack Examples ===")
    
    // Test 1: Next Greater Element I
    println("Test 1 - Next Greater Element I([4,1,2], [1,3,4,2]): " + nextGreaterElement(Array(4,1,2), Array(1,3,4,2)).mkString("[", ",", "]"))
    
    // Test 2: Daily Temperatures
    println("Test 2 - Daily Temperatures([73,74,75,71,69,72,76,73]): " + dailyTemperatures(Array(73,74,75,71,69,72,76,73)).mkString("[", ",", "]"))
    
    // Test 3: Largest Rectangle in Histogram
    println("Test 3 - Largest Rectangle([2,1,5,6,2,3]): " + largestRectangleArea(Array(2,1,5,6,2,3)))
    
    // Test 4: Trapping Rain Water
    println("Test 4 - Trap([0,1,0,2,1,0,1,3,2,1,2,1]): " + trap(Array(0,1,0,2,1,0,1,3,2,1,2,1)))
    
    // Test 5: Remove Duplicate Letters
    println("Test 5 - Remove Duplicate Letters('bcabc'): " + removeDuplicateLetters("bcabc"))
    
    // Test 6: Next Greater Elements II
    println("Test 6 - Next Greater Elements II([1,2,1]): " + nextGreaterElements(Array(1,2,1)).mkString("[", ",", "]"))
    
    // Test 7: Valid Parentheses
    println("Test 7 - Valid Parentheses('()[]{}'): " + isValid("()[]{}"))
    
    // Test 8: Maximal Rectangle
    val matrix = Array(Array('1','0','1','0','0'),Array('1','0','1','1','1'),Array('1','1','1','1','1'),Array('1','0','0','1','0'))
    println("Test 8 - Maximal Rectangle: " + maximalRectangle(matrix))
    
    // Test 9: Sum of Subarray Minimums
    println("Test 9 - Sum Subarray Mins([3,1,2,4]): " + sumSubarrayMins(Array(3,1,2,4)))
    
    // Test 10: Min Stack
    val minStack = new MinStack()
    minStack.push(-2)
    minStack.push(0)
    minStack.push(-3)
    println("Test 10 - Min Stack getMin(): " + minStack.getMin())
    minStack.pop()
    println("Test 10 - Min Stack top(): " + minStack.top())
    println("Test 10 - Min Stack getMin(): " + minStack.getMin())
  }
}