object ScalaBitManipulationExamples {
  
  // 1. Single Number
  def singleNumber(nums: Array[Int]): Int = {
    nums.reduce(_ ^ _)
  }
  
  // 2. Number of 1 Bits
  def hammingWeight(n: Int): Int = {
    var count = 0
    var num = n
    while (num != 0) {
      count += num & 1
      num >>>= 1
    }
    count
  }
  
  // 3. Counting Bits
  def countBits(n: Int): Array[Int] = {
    val result = Array.ofDim[Int](n + 1)
    for (i <- 1 to n) {
      result(i) = result(i >> 1) + (i & 1)
    }
    result
  }
  
  // 4. Missing Number
  def missingNumber(nums: Array[Int]): Int = {
    var result = nums.length
    for (i <- nums.indices) {
      result ^= i ^ nums(i)
    }
    result
  }
  
  // 5. Reverse Bits
  def reverseBits(n: Int): Int = {
    var result = 0
    var num = n
    for (_ <- 0 until 32) {
      result = (result << 1) | (num & 1)
      num >>>= 1
    }
    result
  }
  
  // 6. Power of Two
  def isPowerOfTwo(n: Int): Boolean = {
    n > 0 && (n & (n - 1)) == 0
  }
  
  // 7. Sum of Two Integers
  def getSum(a: Int, b: Int): Int = {
    var x = a
    var y = b
    while (y != 0) {
      val carry = x & y
      x = x ^ y
      y = carry << 1
    }
    x
  }
  
  // 8. Single Number II
  def singleNumberII(nums: Array[Int]): Int = {
    var ones = 0
    var twos = 0
    for (num <- nums) {
      ones = (ones ^ num) & ~twos
      twos = (twos ^ num) & ~ones
    }
    ones
  }
  
  // 9. Maximum XOR of Two Numbers in an Array
  def findMaximumXOR(nums: Array[Int]): Int = {
    var maxXor = 0
    var mask = 0
    
    for (i <- 31 to 0 by -1) {
      mask |= (1 << i)
      val prefixes = nums.map(_ & mask).toSet
      val candidate = maxXor | (1 << i)
      
      if (prefixes.exists(prefix => prefixes.contains(candidate ^ prefix))) {
        maxXor = candidate
      }
    }
    
    maxXor
  }
  
  // 10. Subsets
  def subsets(nums: Array[Int]): List[List[Int]] = {
    val result = scala.collection.mutable.ListBuffer[List[Int]]()
    val n = nums.length
    
    for (mask <- 0 until (1 << n)) {
      val subset = scala.collection.mutable.ListBuffer[Int]()
      for (i <- 0 until n) {
        if ((mask & (1 << i)) != 0) {
          subset += nums(i)
        }
      }
      result += subset.toList
    }
    
    result.toList
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Bit Manipulation Examples ===")
    
    // Test 1: Single Number
    println("Test 1 - Single Number([2,2,1]): " + singleNumber(Array(2, 2, 1)))
    
    // Test 2: Number of 1 Bits
    println("Test 2 - Hamming Weight(11): " + hammingWeight(11))
    
    // Test 3: Counting Bits
    println("Test 3 - Count Bits(2): " + countBits(2).mkString("[", ",", "]"))
    
    // Test 4: Missing Number
    println("Test 4 - Missing Number([3,0,1]): " + missingNumber(Array(3, 0, 1)))
    
    // Test 5: Reverse Bits
    println("Test 5 - Reverse Bits(43261596): " + reverseBits(43261596))
    
    // Test 6: Power of Two
    println("Test 6 - Is Power of Two(1): " + isPowerOfTwo(1))
    
    // Test 7: Sum of Two Integers
    println("Test 7 - Get Sum(1, 2): " + getSum(1, 2))
    
    // Test 8: Single Number II
    println("Test 8 - Single Number II([2,2,3,2]): " + singleNumberII(Array(2, 2, 3, 2)))
    
    // Test 9: Maximum XOR of Two Numbers
    println("Test 9 - Find Maximum XOR([3,10,5,25,2,8]): " + findMaximumXOR(Array(3, 10, 5, 25, 2, 8)))
    
    // Test 10: Subsets
    println("Test 10 - Subsets([1,2,3]): " + subsets(Array(1, 2, 3)).length + " subsets")
  }
}