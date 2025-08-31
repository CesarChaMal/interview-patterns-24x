object ScalaFenwickTreeExamples {
  
  // 1. Binary Indexed Tree (Fenwick Tree)
  class BinaryIndexedTree(n: Int) {
    private val tree = Array.fill(n + 1)(0)
    
    def update(i: Int, delta: Int): Unit = {
      var idx = i + 1
      while (idx < tree.length) {
        tree(idx) += delta
        idx += idx & (-idx)
      }
    }
    
    def query(i: Int): Int = {
      var sum = 0
      var idx = i + 1
      while (idx > 0) {
        sum += tree(idx)
        idx -= idx & (-idx)
      }
      sum
    }
    
    def rangeQuery(left: Int, right: Int): Int = {
      if (left == 0) query(right)
      else query(right) - query(left - 1)
    }
  }
  
  // 2. Range Sum Query - Mutable
  class NumArray(nums: Array[Int]) {
    private val bit = new BinaryIndexedTree(nums.length)
    private val original = nums.clone()
    
    for (i <- nums.indices) {
      bit.update(i, nums(i))
    }
    
    def update(index: Int, value: Int): Unit = {
      val delta = value - original(index)
      original(index) = value
      bit.update(index, delta)
    }
    
    def sumRange(left: Int, right: Int): Int = {
      bit.rangeQuery(left, right)
    }
  }
  
  // 3. Count of Smaller Numbers After Self
  def countSmaller(nums: Array[Int]): List[Int] = {
    val sorted = nums.distinct.sorted
    val compress = sorted.zipWithIndex.toMap
    val bit = new BinaryIndexedTree(sorted.length)
    val result = Array.ofDim[Int](nums.length)
    
    for (i <- nums.length - 1 to 0 by -1) {
      val compressedVal = compress(nums(i))
      result(i) = if (compressedVal > 0) bit.query(compressedVal - 1) else 0
      bit.update(compressedVal, 1)
    }
    
    result.toList
  }
  
  // 4. Range Sum Query 2D - Mutable
  class NumMatrix(matrix: Array[Array[Int]]) {
    private val m = matrix.length
    private val n = if (m > 0) matrix(0).length else 0
    private val tree = Array.fill(m + 1, n + 1)(0)
    private val nums = Array.ofDim[Int](m, n)
    
    for (i <- matrix.indices; j <- matrix(0).indices) {
      update(i, j, matrix(i)(j))
    }
    
    def update(row: Int, col: Int, value: Int): Unit = {
      val delta = value - nums(row)(col)
      nums(row)(col) = value
      
      var i = row + 1
      while (i <= m) {
        var j = col + 1
        while (j <= n) {
          tree(i)(j) += delta
          j += j & (-j)
        }
        i += i & (-i)
      }
    }
    
    def sumRegion(row1: Int, col1: Int, row2: Int, col2: Int): Int = {
      sum(row2 + 1, col2 + 1) - sum(row1, col2 + 1) - sum(row2 + 1, col1) + sum(row1, col1)
    }
    
    private def sum(row: Int, col: Int): Int = {
      var result = 0
      var i = row
      while (i > 0) {
        var j = col
        while (j > 0) {
          result += tree(i)(j)
          j -= j & (-j)
        }
        i -= i & (-i)
      }
      result
    }
  }
  
  // 5. Create Sorted Array through Instructions
  def createSortedArray(instructions: Array[Int]): Int = {
    val MOD = 1000000007
    val maxVal = instructions.max
    val bit = new BinaryIndexedTree(maxVal)
    var cost = 0L
    
    for (i <- instructions.indices) {
      val num = instructions(i)
      val smaller = if (num > 1) bit.query(num - 2) else 0
      val larger = i - bit.query(num - 1)
      cost = (cost + math.min(smaller, larger)) % MOD
      bit.update(num - 1, 1)
    }
    
    cost.toInt
  }
  
  // 6. Reverse Pairs
  def reversePairs(nums: Array[Int]): Int = {
    val sorted = (nums.map(_.toLong) ++ nums.map(_ * 2L)).distinct.sorted
    val compress = sorted.zipWithIndex.toMap
    val bit = new BinaryIndexedTree(sorted.length)
    var count = 0
    
    for (i <- nums.length - 1 to 0 by -1) {
      val target = nums(i) * 2L
      val compressedTarget = compress(target)
      count += bit.query(sorted.length - 1) - bit.query(compressedTarget)
      
      val compressedNum = compress(nums(i).toLong)
      bit.update(compressedNum, 1)
    }
    
    count
  }
  
  // 7. Count of Range Sum
  def countRangeSum(nums: Array[Int], lower: Int, upper: Int): Int = {
    val prefixSums = Array.ofDim[Long](nums.length + 1)
    for (i <- nums.indices) {
      prefixSums(i + 1) = prefixSums(i) + nums(i)
    }
    
    val allSums = (prefixSums ++ prefixSums.map(_ - lower) ++ prefixSums.map(_ - upper - 1)).distinct.sorted
    val compress = allSums.zipWithIndex.toMap
    val bit = new BinaryIndexedTree(allSums.length)
    var count = 0
    
    for (i <- prefixSums.indices) {
      if (i > 0) {
        val lowerBound = compress(prefixSums(i) - upper - 1)
        val upperBound = compress(prefixSums(i) - lower)
        count += bit.rangeQuery(lowerBound + 1, upperBound)
      }
      val compressedSum = compress(prefixSums(i))
      bit.update(compressedSum, 1)
    }
    
    count
  }
  
  // 8. Maximum Frequency Stack
  class FreqStack {
    private var maxFreq = 0
    private val freq = scala.collection.mutable.Map[Int, Int]()
    private val freqToStack = scala.collection.mutable.Map[Int, scala.collection.mutable.Stack[Int]]()
    
    def push(value: Int): Unit = {
      val f = freq.getOrElse(value, 0) + 1
      freq(value) = f
      maxFreq = math.max(maxFreq, f)
      
      freqToStack.getOrElseUpdate(f, scala.collection.mutable.Stack[Int]()).push(value)
    }
    
    def pop(): Int = {
      val value = freqToStack(maxFreq).pop()
      freq(value) -= 1
      
      if (freqToStack(maxFreq).isEmpty) {
        maxFreq -= 1
      }
      
      value
    }
  }
  
  // 9. Longest Increasing Subsequence with BIT
  def lengthOfLIS(nums: Array[Int]): Int = {
    val sorted = nums.distinct.sorted
    val compress = sorted.zipWithIndex.toMap
    val bit = new BinaryIndexedTree(sorted.length)
    
    for (num <- nums) {
      val compressedNum = compress(num)
      val maxLen = if (compressedNum > 0) bit.query(compressedNum - 1) else 0
      bit.update(compressedNum, maxLen + 1)
    }
    
    bit.query(sorted.length - 1)
  }
  
  // 10. Count Inversions
  def countInversions(nums: Array[Int]): Int = {
    val sorted = nums.distinct.sorted
    val compress = sorted.zipWithIndex.toMap
    val bit = new BinaryIndexedTree(sorted.length)
    var count = 0
    
    for (i <- nums.length - 1 to 0 by -1) {
      val compressedNum = compress(nums(i))
      count += (if (compressedNum > 0) bit.query(compressedNum - 1) else 0)
      bit.update(compressedNum, 1)
    }
    
    count
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Fenwick Tree Examples ===")
    
    // Test 1: Binary Indexed Tree
    val bit = new BinaryIndexedTree(5)
    bit.update(0, 1)
    bit.update(1, 3)
    bit.update(2, 5)
    println("Test 1 - BIT Query(2): " + bit.query(2))
    
    // Test 2: Range Sum Query - Mutable
    val numArray = new NumArray(Array(1, 3, 5))
    println("Test 2 - Sum Range(0, 2): " + numArray.sumRange(0, 2))
    
    // Test 3: Count of Smaller Numbers After Self
    println("Test 3 - Count Smaller: " + countSmaller(Array(5, 2, 6, 1)))
    
    // Test 4: Range Sum Query 2D - Mutable
    val matrix = Array(Array(3, 0, 1, 4, 2), Array(5, 6, 3, 2, 1))
    val numMatrix = new NumMatrix(matrix)
    println("Test 4 - Sum Region(0, 1, 1, 3): " + numMatrix.sumRegion(0, 1, 1, 3))
    
    // Test 5: Create Sorted Array through Instructions
    println("Test 5 - Create Sorted Array: " + createSortedArray(Array(1, 5, 6, 2)))
    
    // Test 6: Reverse Pairs
    println("Test 6 - Reverse Pairs: " + reversePairs(Array(1, 3, 2, 3, 1)))
    
    // Test 7: Count of Range Sum
    println("Test 7 - Count Range Sum: " + countRangeSum(Array(-2, 5, -1), -2, 2))
    
    // Test 8: Maximum Frequency Stack
    val freqStack = new FreqStack()
    freqStack.push(5)
    freqStack.push(7)
    freqStack.push(5)
    println("Test 8 - Freq Stack Pop: " + freqStack.pop())
    
    // Test 9: Longest Increasing Subsequence
    println("Test 9 - Length of LIS: " + lengthOfLIS(Array(10, 9, 2, 5, 3, 7, 101, 18)))
    
    // Test 10: Count Inversions
    println("Test 10 - Count Inversions: " + countInversions(Array(2, 3, 8, 6, 1)))
  }
}