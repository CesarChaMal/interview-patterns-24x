import scala.collection.mutable

object ScalaSlidingWindowExamples {
  
  // 1. Maximum Subarray Sum of Size K
  def maxSubarraySum(nums: Array[Int], k: Int): Int = {
    var sum = 0
    var maxSum = Int.MinValue
    for (i <- nums.indices) {
      sum += nums(i)
      if (i >= k - 1) {
        maxSum = math.max(maxSum, sum)
        sum -= nums(i - k + 1)
      }
    }
    maxSum
  }
  
  // 2. Longest Substring Without Repeating Characters
  def lengthOfLongestSubstring(s: String): Int = {
    val set = mutable.Set[Char]()
    var left = 0
    var maxLen = 0
    for (right <- s.indices) {
      while (set.contains(s(right))) {
        set.remove(s(left))
        left += 1
      }
      set.add(s(right))
      maxLen = math.max(maxLen, right - left + 1)
    }
    maxLen
  }
  
  // 3. Minimum Window Substring
  def minWindow(s: String, t: String): String = {
    val need = mutable.Map[Char, Int]()
    t.foreach(c => need(c) = need.getOrElse(c, 0) + 1)
    
    var left = 0
    var right = 0
    var valid = 0
    var start = 0
    var len = Int.MaxValue
    val window = mutable.Map[Char, Int]()
    
    while (right < s.length) {
      val c = s(right)
      right += 1
      if (need.contains(c)) {
        window(c) = window.getOrElse(c, 0) + 1
        if (window(c) == need(c)) valid += 1
      }
      
      while (valid == need.size) {
        if (right - left < len) {
          start = left
          len = right - left
        }
        val d = s(left)
        left += 1
        if (need.contains(d)) {
          if (window(d) == need(d)) valid -= 1
          window(d) = window(d) - 1
        }
      }
    }
    if (len == Int.MaxValue) "" else s.substring(start, start + len)
  }
  
  // 4. Sliding Window Maximum
  def maxSlidingWindow(nums: Array[Int], k: Int): Array[Int] = {
    val deque = mutable.ArrayDeque[Int]()
    val result = Array.ofDim[Int](nums.length - k + 1)
    
    for (i <- nums.indices) {
      while (deque.nonEmpty && deque.head < i - k + 1) {
        deque.removeHead()
      }
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
  
  // 5. Longest Repeating Character Replacement
  def characterReplacement(s: String, k: Int): Int = {
    val count = Array.fill(26)(0)
    var left = 0
    var maxCount = 0
    var maxLen = 0
    
    for (right <- s.indices) {
      maxCount = math.max(maxCount, { count(s(right) - 'A') += 1; count(s(right) - 'A') })
      while (right - left + 1 - maxCount > k) {
        count(s(left) - 'A') -= 1
        left += 1
      }
      maxLen = math.max(maxLen, right - left + 1)
    }
    maxLen
  }
  
  // 6. Permutation in String
  def checkInclusion(s1: String, s2: String): Boolean = {
    val need = mutable.Map[Char, Int]()
    s1.foreach(c => need(c) = need.getOrElse(c, 0) + 1)
    
    var left = 0
    var right = 0
    var valid = 0
    val window = mutable.Map[Char, Int]()
    
    while (right < s2.length) {
      val c = s2(right)
      right += 1
      if (need.contains(c)) {
        window(c) = window.getOrElse(c, 0) + 1
        if (window(c) == need(c)) valid += 1
      }
      
      while (right - left >= s1.length) {
        if (valid == need.size) return true
        val d = s2(left)
        left += 1
        if (need.contains(d)) {
          if (window(d) == need(d)) valid -= 1
          window(d) = window(d) - 1
        }
      }
    }
    false
  }
  
  // 7. Find All Anagrams in a String
  def findAnagrams(s: String, p: String): List[Int] = {
    val result = mutable.ListBuffer[Int]()
    val need = mutable.Map[Char, Int]()
    p.foreach(c => need(c) = need.getOrElse(c, 0) + 1)
    
    var left = 0
    var right = 0
    var valid = 0
    val window = mutable.Map[Char, Int]()
    
    while (right < s.length) {
      val c = s(right)
      right += 1
      if (need.contains(c)) {
        window(c) = window.getOrElse(c, 0) + 1
        if (window(c) == need(c)) valid += 1
      }
      
      while (right - left >= p.length) {
        if (valid == need.size) result += left
        val d = s(left)
        left += 1
        if (need.contains(d)) {
          if (window(d) == need(d)) valid -= 1
          window(d) = window(d) - 1
        }
      }
    }
    result.toList
  }
  
  // 8. Longest Subarray with Ones after Replacement
  def longestOnes(nums: Array[Int], k: Int): Int = {
    var left = 0
    var zeros = 0
    var maxLen = 0
    for (right <- nums.indices) {
      if (nums(right) == 0) zeros += 1
      while (zeros > k) {
        if (nums(left) == 0) zeros -= 1
        left += 1
      }
      maxLen = math.max(maxLen, right - left + 1)
    }
    maxLen
  }
  
  // 9. Subarray Product Less Than K
  def numSubarrayProductLessThanK(nums: Array[Int], k: Int): Int = {
    if (k <= 1) return 0
    var left = 0
    var product = 1
    var count = 0
    for (right <- nums.indices) {
      product *= nums(right)
      while (product >= k) {
        product /= nums(left)
        left += 1
      }
      count += right - left + 1
    }
    count
  }
  
  // 10. Minimum Size Subarray Sum
  def minSubArrayLen(target: Int, nums: Array[Int]): Int = {
    var left = 0
    var sum = 0
    var minLen = Int.MaxValue
    for (right <- nums.indices) {
      sum += nums(right)
      while (sum >= target) {
        minLen = math.min(minLen, right - left + 1)
        sum -= nums(left)
        left += 1
      }
    }
    if (minLen == Int.MaxValue) 0 else minLen
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Sliding Window Examples ===")
    
    // Test 1: Maximum Subarray Sum
    val nums1 = Array(2, 1, 5, 1, 3, 2)
    println("Test 1 - Max Subarray Sum([2,1,5,1,3,2], k=3): " + maxSubarraySum(nums1, 3))
    
    // Test 2: Longest Substring Without Repeating
    println("Test 2 - Longest Substring 'abcabcbb': " + lengthOfLongestSubstring("abcabcbb"))
    
    // Test 3: Minimum Window Substring
    println("Test 3 - Min Window('ADOBECODEBANC', 'ABC'): " + minWindow("ADOBECODEBANC", "ABC"))
    
    // Test 4: Sliding Window Maximum
    val nums4 = Array(1, 3, -1, -3, 5, 3, 6, 7)
    println("Test 4 - Sliding Window Max([1,3,-1,-3,5,3,6,7], k=3): " + maxSlidingWindow(nums4, 3).mkString("[", ",", "]"))
    
    // Test 5: Character Replacement
    println("Test 5 - Character Replacement('ABAB', k=2): " + characterReplacement("ABAB", 2))
    
    // Test 6: Permutation in String
    println("Test 6 - Check Inclusion('ab', 'eidbaooo'): " + checkInclusion("ab", "eidbaooo"))
    
    // Test 7: Find Anagrams
    println("Test 7 - Find Anagrams('cbaebabacd', 'abc'): " + findAnagrams("cbaebabacd", "abc"))
    
    // Test 8: Longest Ones
    val nums8 = Array(1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0)
    println("Test 8 - Longest Ones([1,1,1,0,0,0,1,1,1,1,0], k=2): " + longestOnes(nums8, 2))
    
    // Test 9: Subarray Product Less Than K
    val nums9 = Array(10, 5, 2, 6)
    println("Test 9 - Subarray Product([10,5,2,6], k=100): " + numSubarrayProductLessThanK(nums9, 100))
    
    // Test 10: Minimum Size Subarray Sum
    val nums10 = Array(2, 3, 1, 2, 4, 3)
    println("Test 10 - Min Subarray Len(target=7, [2,3,1,2,4,3]): " + minSubArrayLen(7, nums10))
  }
}