object ScalaDynamicProgrammingExamples {
  
  // 1. Climbing Stairs
  def climbStairs(n: Int): Int = {
    if (n <= 2) return n
    var prev2 = 1
    var prev1 = 2
    for (i <- 3 to n) {
      val curr = prev1 + prev2
      prev2 = prev1
      prev1 = curr
    }
    prev1
  }
  
  // 2. House Robber
  def rob(nums: Array[Int]): Int = {
    var prev2 = 0
    var prev1 = 0
    for (num <- nums) {
      val curr = math.max(prev1, prev2 + num)
      prev2 = prev1
      prev1 = curr
    }
    prev1
  }
  
  // 3. Coin Change
  def coinChange(coins: Array[Int], amount: Int): Int = {
    val dp = Array.fill(amount + 1)(amount + 1)
    dp(0) = 0
    
    for (i <- 1 to amount) {
      for (coin <- coins) {
        if (coin <= i) {
          dp(i) = math.min(dp(i), dp(i - coin) + 1)
        }
      }
    }
    if (dp(amount) > amount) -1 else dp(amount)
  }
  
  // 4. Longest Increasing Subsequence
  def lengthOfLIS(nums: Array[Int]): Int = {
    val dp = scala.collection.mutable.ArrayBuffer[Int]()
    for (num <- nums) {
      val pos = dp.search(num).insertionPoint
      if (pos == dp.length) {
        dp += num
      } else {
        dp(pos) = num
      }
    }
    dp.length
  }
  
  // 5. Maximum Subarray
  def maxSubArray(nums: Array[Int]): Int = {
    var maxSum = nums(0)
    var currSum = nums(0)
    for (i <- 1 until nums.length) {
      currSum = math.max(nums(i), currSum + nums(i))
      maxSum = math.max(maxSum, currSum)
    }
    maxSum
  }
  
  // 6. Unique Paths
  def uniquePaths(m: Int, n: Int): Int = {
    val dp = Array.fill(n)(1)
    for (i <- 1 until m) {
      for (j <- 1 until n) {
        dp(j) += dp(j - 1)
      }
    }
    dp(n - 1)
  }
  
  // 7. 0/1 Knapsack
  def knapsack(weights: Array[Int], values: Array[Int], capacity: Int): Int = {
    val dp = Array.fill(capacity + 1)(0)
    for (i <- weights.indices) {
      for (w <- capacity to weights(i) by -1) {
        dp(w) = math.max(dp(w), dp(w - weights(i)) + values(i))
      }
    }
    dp(capacity)
  }
  
  // 8. Edit Distance
  def minDistance(word1: String, word2: String): Int = {
    val m = word1.length
    val n = word2.length
    val dp = Array.tabulate(n + 1)(identity)
    
    for (i <- 1 to m) {
      var prev = dp(0)
      dp(0) = i
      for (j <- 1 to n) {
        val temp = dp(j)
        if (word1(i - 1) == word2(j - 1)) {
          dp(j) = prev
        } else {
          dp(j) = 1 + math.min(prev, math.min(dp(j), dp(j - 1)))
        }
        prev = temp
      }
    }
    dp(n)
  }
  
  // 9. Longest Common Subsequence
  def longestCommonSubsequence(text1: String, text2: String): Int = {
    val m = text1.length
    val n = text2.length
    val dp = Array.fill(n + 1)(0)
    
    for (i <- 1 to m) {
      var prev = 0
      for (j <- 1 to n) {
        val temp = dp(j)
        if (text1(i - 1) == text2(j - 1)) {
          dp(j) = prev + 1
        } else {
          dp(j) = math.max(dp(j), dp(j - 1))
        }
        prev = temp
      }
    }
    dp(n)
  }
  
  // 10. Word Break
  def wordBreak(s: String, wordDict: List[String]): Boolean = {
    val wordSet = wordDict.toSet
    val dp = Array.fill(s.length + 1)(false)
    dp(0) = true
    
    for (i <- 1 to s.length) {
      var found = false
      for (j <- 0 until i if !found) {
        if (dp(j) && wordSet.contains(s.substring(j, i))) {
          dp(i) = true
          found = true
        }
      }
    }
    dp(s.length)
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Dynamic Programming Examples ===")
    
    // Test 1: Climbing Stairs
    println("Test 1 - Climb Stairs(5): " + climbStairs(5))
    
    // Test 2: House Robber
    println("Test 2 - House Robber([2,7,9,3,1]): " + rob(Array(2,7,9,3,1)))
    
    // Test 3: Coin Change
    println("Test 3 - Coin Change([1,3,4], 6): " + coinChange(Array(1,3,4), 6))
    
    // Test 4: Longest Increasing Subsequence
    println("Test 4 - LIS([10,9,2,5,3,7,101,18]): " + lengthOfLIS(Array(10,9,2,5,3,7,101,18)))
    
    // Test 5: Maximum Subarray
    println("Test 5 - Max Subarray([-2,1,-3,4,-1,2,1,-5,4]): " + maxSubArray(Array(-2,1,-3,4,-1,2,1,-5,4)))
    
    // Test 6: Unique Paths
    println("Test 6 - Unique Paths(3,7): " + uniquePaths(3, 7))
    
    // Test 7: 0/1 Knapsack
    println("Test 7 - Knapsack([1,3,4,5], [1,4,5,7], 7): " + knapsack(Array(1,3,4,5), Array(1,4,5,7), 7))
    
    // Test 8: Edit Distance
    println("Test 8 - Edit Distance('horse', 'ros'): " + minDistance("horse", "ros"))
    
    // Test 9: Longest Common Subsequence
    println("Test 9 - LCS('abcde', 'ace'): " + longestCommonSubsequence("abcde", "ace"))
    
    // Test 10: Word Break
    println("Test 10 - Word Break('leetcode', ['leet','code']): " + wordBreak("leetcode", List("leet", "code")))
  }
}