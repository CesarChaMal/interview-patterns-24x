object ScalaSegmentTreeExamples {
  
  // 1. Range Sum Query - Mutable
  class NumArray(nums: Array[Int]) {
    private val n = nums.length
    private val tree = Array.ofDim[Int](4 * n)
    
    build(nums, 0, 0, n - 1)
    
    private def build(nums: Array[Int], node: Int, start: Int, end: Int): Unit = {
      if (start == end) {
        tree(node) = nums(start)
      } else {
        val mid = (start + end) / 2
        build(nums, 2 * node + 1, start, mid)
        build(nums, 2 * node + 2, mid + 1, end)
        tree(node) = tree(2 * node + 1) + tree(2 * node + 2)
      }
    }
    
    def update(index: Int, value: Int): Unit = {
      updateHelper(0, 0, n - 1, index, value)
    }
    
    private def updateHelper(node: Int, start: Int, end: Int, index: Int, value: Int): Unit = {
      if (start == end) {
        tree(node) = value
      } else {
        val mid = (start + end) / 2
        if (index <= mid) {
          updateHelper(2 * node + 1, start, mid, index, value)
        } else {
          updateHelper(2 * node + 2, mid + 1, end, index, value)
        }
        tree(node) = tree(2 * node + 1) + tree(2 * node + 2)
      }
    }
    
    def sumRange(left: Int, right: Int): Int = {
      queryHelper(0, 0, n - 1, left, right)
    }
    
    private def queryHelper(node: Int, start: Int, end: Int, left: Int, right: Int): Int = {
      if (right < start || end < left) {
        0
      } else if (left <= start && end <= right) {
        tree(node)
      } else {
        val mid = (start + end) / 2
        val leftSum = queryHelper(2 * node + 1, start, mid, left, right)
        val rightSum = queryHelper(2 * node + 2, mid + 1, end, left, right)
        leftSum + rightSum
      }
    }
  }
  
  // 2. Range Minimum Query
  class RMQ(nums: Array[Int]) {
    private val n = nums.length
    private val tree = Array.ofDim[Int](4 * n)
    
    build(nums, 0, 0, n - 1)
    
    private def build(nums: Array[Int], node: Int, start: Int, end: Int): Unit = {
      if (start == end) {
        tree(node) = nums(start)
      } else {
        val mid = (start + end) / 2
        build(nums, 2 * node + 1, start, mid)
        build(nums, 2 * node + 2, mid + 1, end)
        tree(node) = math.min(tree(2 * node + 1), tree(2 * node + 2))
      }
    }
    
    def query(left: Int, right: Int): Int = {
      queryHelper(0, 0, n - 1, left, right)
    }
    
    private def queryHelper(node: Int, start: Int, end: Int, left: Int, right: Int): Int = {
      if (right < start || end < left) {
        Int.MaxValue
      } else if (left <= start && end <= right) {
        tree(node)
      } else {
        val mid = (start + end) / 2
        val leftMin = queryHelper(2 * node + 1, start, mid, left, right)
        val rightMin = queryHelper(2 * node + 2, mid + 1, end, left, right)
        math.min(leftMin, rightMin)
      }
    }
  }
  
  // 3. Range Maximum Query
  class RMaQ(nums: Array[Int]) {
    private val n = nums.length
    private val tree = Array.ofDim[Int](4 * n)
    
    build(nums, 0, 0, n - 1)
    
    private def build(nums: Array[Int], node: Int, start: Int, end: Int): Unit = {
      if (start == end) {
        tree(node) = nums(start)
      } else {
        val mid = (start + end) / 2
        build(nums, 2 * node + 1, start, mid)
        build(nums, 2 * node + 2, mid + 1, end)
        tree(node) = math.max(tree(2 * node + 1), tree(2 * node + 2))
      }
    }
    
    def query(left: Int, right: Int): Int = {
      queryHelper(0, 0, n - 1, left, right)
    }
    
    private def queryHelper(node: Int, start: Int, end: Int, left: Int, right: Int): Int = {
      if (right < start || end < left) {
        Int.MinValue
      } else if (left <= start && end <= right) {
        tree(node)
      } else {
        val mid = (start + end) / 2
        val leftMax = queryHelper(2 * node + 1, start, mid, left, right)
        val rightMax = queryHelper(2 * node + 2, mid + 1, end, left, right)
        math.max(leftMax, rightMax)
      }
    }
  }
  
  // 4. Lazy Propagation Segment Tree
  class LazySegmentTree(nums: Array[Int]) {
    private val n = nums.length
    private val tree = Array.ofDim[Long](4 * n)
    private val lazyProp = Array.ofDim[Long](4 * n)
    
    build(nums, 0, 0, n - 1)
    
    private def build(nums: Array[Int], node: Int, start: Int, end: Int): Unit = {
      if (start == end) {
        tree(node) = nums(start)
      } else {
        val mid = (start + end) / 2
        build(nums, 2 * node + 1, start, mid)
        build(nums, 2 * node + 2, mid + 1, end)
        tree(node) = tree(2 * node + 1) + tree(2 * node + 2)
      }
    }
    
    private def push(node: Int, start: Int, end: Int): Unit = {
      if (lazyProp(node) != 0) {
        tree(node) += lazyProp(node) * (end - start + 1)
        if (start != end) {
          lazyProp(2 * node + 1) += lazyProp(node)
          lazyProp(2 * node + 2) += lazyProp(node)
        }
        lazyProp(node) = 0
      }
    }
    
    def updateRange(left: Int, right: Int, value: Long): Unit = {
      updateRangeHelper(0, 0, n - 1, left, right, value)
    }
    
    private def updateRangeHelper(node: Int, start: Int, end: Int, left: Int, right: Int, value: Long): Unit = {
      push(node, start, end)
      if (start > right || end < left) return
      
      if (start >= left && end <= right) {
        lazyProp(node) += value
        push(node, start, end)
        return
      }
      
      val mid = (start + end) / 2
      updateRangeHelper(2 * node + 1, start, mid, left, right, value)
      updateRangeHelper(2 * node + 2, mid + 1, end, left, right, value)
      
      push(2 * node + 1, start, mid)
      push(2 * node + 2, mid + 1, end)
      tree(node) = tree(2 * node + 1) + tree(2 * node + 2)
    }
    
    def query(left: Int, right: Int): Long = {
      queryHelper(0, 0, n - 1, left, right)
    }
    
    private def queryHelper(node: Int, start: Int, end: Int, left: Int, right: Int): Long = {
      if (start > right || end < left) return 0
      
      push(node, start, end)
      if (start >= left && end <= right) return tree(node)
      
      val mid = (start + end) / 2
      val leftSum = queryHelper(2 * node + 1, start, mid, left, right)
      val rightSum = queryHelper(2 * node + 2, mid + 1, end, left, right)
      leftSum + rightSum
    }
  }
  
  // 5. Count of Range Sum
  def countRangeSum(nums: Array[Int], lower: Int, upper: Int): Int = {
    val prefixSums = Array.ofDim[Long](nums.length + 1)
    for (i <- nums.indices) {
      prefixSums(i + 1) = prefixSums(i) + nums(i)
    }
    
    def mergeSort(start: Int, end: Int): Int = {
      if (start >= end) return 0
      
      val mid = (start + end) / 2
      var count = mergeSort(start, mid) + mergeSort(mid + 1, end)
      
      var j = mid + 1
      var k = mid + 1
      for (i <- start to mid) {
        while (j <= end && prefixSums(j) - prefixSums(i) < lower) j += 1
        while (k <= end && prefixSums(k) - prefixSums(i) <= upper) k += 1
        count += k - j
      }
      
      val temp = Array.ofDim[Long](end - start + 1)
      var i = start
      j = mid + 1
      var t = 0
      
      while (i <= mid && j <= end) {
        if (prefixSums(i) <= prefixSums(j)) {
          temp(t) = prefixSums(i)
          i += 1
        } else {
          temp(t) = prefixSums(j)
          j += 1
        }
        t += 1
      }
      
      while (i <= mid) {
        temp(t) = prefixSums(i)
        i += 1
        t += 1
      }
      
      while (j <= end) {
        temp(t) = prefixSums(j)
        j += 1
        t += 1
      }
      
      for (i <- temp.indices) {
        prefixSums(start + i) = temp(i)
      }
      
      count
    }
    
    mergeSort(0, prefixSums.length - 1)
  }
  
  // 6. Range Sum Query 2D - Mutable
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
  
  // 7. Reverse Pairs
  def reversePairs(nums: Array[Int]): Int = {
    def mergeSort(start: Int, end: Int): Int = {
      if (start >= end) return 0
      
      val mid = (start + end) / 2
      var count = mergeSort(start, mid) + mergeSort(mid + 1, end)
      
      var j = mid + 1
      for (i <- start to mid) {
        while (j <= end && nums(i) > 2L * nums(j)) j += 1
        count += j - (mid + 1)
      }
      
      val temp = Array.ofDim[Int](end - start + 1)
      var i = start
      j = mid + 1
      var k = 0
      
      while (i <= mid && j <= end) {
        if (nums(i) <= nums(j)) {
          temp(k) = nums(i)
          i += 1
        } else {
          temp(k) = nums(j)
          j += 1
        }
        k += 1
      }
      
      while (i <= mid) {
        temp(k) = nums(i)
        i += 1
        k += 1
      }
      
      while (j <= end) {
        temp(k) = nums(j)
        j += 1
        k += 1
      }
      
      for (i <- temp.indices) {
        nums(start + i) = temp(i)
      }
      
      count
    }
    
    mergeSort(0, nums.length - 1)
  }
  
  // 8. Create Sorted Array through Instructions
  def createSortedArray(instructions: Array[Int]): Int = {
    val MOD = 1000000007
    val maxVal = instructions.max
    val tree = Array.fill(maxVal + 1)(0)
    
    def update(i: Int, delta: Int): Unit = {
      var idx = i
      while (idx <= maxVal) {
        tree(idx) += delta
        idx += idx & (-idx)
      }
    }
    
    def query(i: Int): Int = {
      var sum = 0
      var idx = i
      while (idx > 0) {
        sum += tree(idx)
        idx -= idx & (-idx)
      }
      sum
    }
    
    var cost = 0L
    for (i <- instructions.indices) {
      val num = instructions(i)
      val smaller = query(num - 1)
      val larger = i - query(num)
      cost = (cost + math.min(smaller, larger)) % MOD
      update(num, 1)
    }
    
    cost.toInt
  }
  
  // 9. Count of Smaller Numbers After Self
  def countSmaller(nums: Array[Int]): List[Int] = {
    val sorted = nums.distinct.sorted
    val compress = sorted.zipWithIndex.toMap
    val tree = Array.fill(sorted.length + 1)(0)
    
    def update(i: Int, delta: Int): Unit = {
      var idx = i
      while (idx < tree.length) {
        tree(idx) += delta
        idx += idx & (-idx)
      }
    }
    
    def query(i: Int): Int = {
      var sum = 0
      var idx = i
      while (idx > 0) {
        sum += tree(idx)
        idx -= idx & (-idx)
      }
      sum
    }
    
    val result = Array.ofDim[Int](nums.length)
    for (i <- nums.length - 1 to 0 by -1) {
      val compressedVal = compress(nums(i)) + 1
      result(i) = query(compressedVal - 1)
      update(compressedVal, 1)
    }
    
    result.toList
  }
  
  // 10. Range Module
  class RangeModule {
    import scala.collection.mutable.TreeMap
    private val intervals = TreeMap[Int, Int]()
    
    def addRange(left: Int, right: Int): Unit = {
      var start = left
      var end = right
      
      val toRemove = intervals.range(left, right + 1).toList
      for ((l, r) <- toRemove) {
        start = math.min(start, l)
        end = math.max(end, r)
        intervals.remove(l)
      }
      
      intervals(start) = end
    }
    
    def queryRange(left: Int, right: Int): Boolean = {
      intervals.maxBefore(left + 1) match {
        case Some((start, end)) => start <= left && right <= end
        case None => false
      }
    }
    
    def removeRange(left: Int, right: Int): Unit = {
      val toModify = intervals.range(left, right + 1).toList
      for ((l, r) <- toModify) {
        intervals.remove(l)
        if (l < left) intervals(l) = left
        if (right < r) intervals(right) = r
      }
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Segment Tree Examples ===")
    
    // Test 1: Range Sum Query - Mutable
    val numArray = new NumArray(Array(1, 3, 5))
    println("Test 1 - Sum Range(0, 2): " + numArray.sumRange(0, 2))
    
    // Test 2: Range Minimum Query
    val rmq = new RMQ(Array(1, 3, 2, 7, 9, 11))
    println("Test 2 - RMQ(1, 3): " + rmq.query(1, 3))
    
    // Test 3: Range Maximum Query
    val rmaq = new RMaQ(Array(1, 3, 2, 7, 9, 11))
    println("Test 3 - RMaQ(1, 3): " + rmaq.query(1, 3))
    
    // Test 4: Lazy Propagation Segment Tree
    val lazyTree = new LazySegmentTree(Array(1, 2, 3, 4, 5))
    lazyTree.updateRange(0, 2, 10)
    println("Test 4 - Lazy Query(0, 2): " + lazyTree.query(0, 2))
    
    // Test 5: Count of Range Sum
    println("Test 5 - Count Range Sum: " + countRangeSum(Array(-2, 5, -1), -2, 2))
    
    // Test 6: Range Sum Query 2D - Mutable
    val matrix = Array(Array(3, 0, 1, 4, 2), Array(5, 6, 3, 2, 1), Array(1, 2, 0, 1, 5), Array(4, 1, 0, 1, 7), Array(1, 0, 3, 0, 5))
    val numMatrix = new NumMatrix(matrix)
    println("Test 6 - Sum Region(2, 1, 4, 3): " + numMatrix.sumRegion(2, 1, 4, 3))
    
    // Test 7: Reverse Pairs
    println("Test 7 - Reverse Pairs: " + reversePairs(Array(1, 3, 2, 3, 1)))
    
    // Test 8: Create Sorted Array through Instructions
    println("Test 8 - Create Sorted Array: " + createSortedArray(Array(1, 5, 6, 2)))
    
    // Test 9: Count of Smaller Numbers After Self
    println("Test 9 - Count Smaller: " + countSmaller(Array(5, 2, 6, 1)))
    
    // Test 10: Range Module
    val rangeModule = new RangeModule()
    rangeModule.addRange(10, 20)
    println("Test 10 - Range Module Query(10, 14): " + rangeModule.queryRange(10, 14))
  }
}