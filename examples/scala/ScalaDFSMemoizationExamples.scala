import scala.collection.mutable

object ScalaDFSMemoizationExamples {
  
  // 1. Fibonacci Number
  def fib(n: Int): Int = {
    val memo = mutable.Map[Int, Int]()
    
    def dfs(num: Int): Int = {
      if (num <= 1) return num
      if (memo.contains(num)) return memo(num)
      
      val result = dfs(num - 1) + dfs(num - 2)
      memo(num) = result
      result
    }
    
    dfs(n)
  }
  
  // 2. Climbing Stairs
  def climbStairs(n: Int): Int = {
    val memo = mutable.Map[Int, Int]()
    
    def dfs(steps: Int): Int = {
      if (steps <= 2) return steps
      if (memo.contains(steps)) return memo(steps)
      
      val result = dfs(steps - 1) + dfs(steps - 2)
      memo(steps) = result
      result
    }
    
    dfs(n)
  }
  
  // 3. House Robber
  def rob(nums: Array[Int]): Int = {
    val memo = mutable.Map[Int, Int]()
    
    def dfs(i: Int): Int = {
      if (i >= nums.length) return 0
      if (memo.contains(i)) return memo(i)
      
      val result = math.max(nums(i) + dfs(i + 2), dfs(i + 1))
      memo(i) = result
      result
    }
    
    dfs(0)
  }
  
  // 4. Unique Paths
  def uniquePaths(m: Int, n: Int): Int = {
    val memo = mutable.Map[(Int, Int), Int]()
    
    def dfs(row: Int, col: Int): Int = {
      if (row == m - 1 && col == n - 1) return 1
      if (row >= m || col >= n) return 0
      if (memo.contains((row, col))) return memo((row, col))
      
      val result = dfs(row + 1, col) + dfs(row, col + 1)
      memo((row, col)) = result
      result
    }
    
    dfs(0, 0)
  }
  
  // 5. Longest Common Subsequence
  def longestCommonSubsequence(text1: String, text2: String): Int = {
    val memo = mutable.Map[(Int, Int), Int]()
    
    def dfs(i: Int, j: Int): Int = {
      if (i >= text1.length || j >= text2.length) return 0
      if (memo.contains((i, j))) return memo((i, j))
      
      val result = if (text1(i) == text2(j)) {
        1 + dfs(i + 1, j + 1)
      } else {
        math.max(dfs(i + 1, j), dfs(i, j + 1))
      }
      
      memo((i, j)) = result
      result
    }
    
    dfs(0, 0)
  }
  
  // 6. Edit Distance
  def minDistance(word1: String, word2: String): Int = {
    val memo = mutable.Map[(Int, Int), Int]()
    
    def dfs(i: Int, j: Int): Int = {
      if (i >= word1.length) return word2.length - j
      if (j >= word2.length) return word1.length - i
      if (memo.contains((i, j))) return memo((i, j))
      
      val result = if (word1(i) == word2(j)) {
        dfs(i + 1, j + 1)
      } else {
        1 + math.min(dfs(i + 1, j), math.min(dfs(i, j + 1), dfs(i + 1, j + 1)))
      }
      
      memo((i, j)) = result
      result
    }
    
    dfs(0, 0)
  }
  
  // 7. Target Sum
  def findTargetSumWays(nums: Array[Int], target: Int): Int = {
    val memo = mutable.Map[(Int, Int), Int]()
    
    def dfs(i: Int, sum: Int): Int = {
      if (i == nums.length) return if (sum == target) 1 else 0
      if (memo.contains((i, sum))) return memo((i, sum))
      
      val result = dfs(i + 1, sum + nums(i)) + dfs(i + 1, sum - nums(i))
      memo((i, sum)) = result
      result
    }
    
    dfs(0, 0)
  }
  
  // 8. Coin Change
  def coinChange(coins: Array[Int], amount: Int): Int = {
    val memo = mutable.Map[Int, Int]()
    
    def dfs(remaining: Int): Int = {
      if (remaining == 0) return 0
      if (remaining < 0) return -1
      if (memo.contains(remaining)) return memo(remaining)
      
      var minCoins = Int.MaxValue
      for (coin <- coins) {
        val result = dfs(remaining - coin)
        if (result != -1) {
          minCoins = math.min(minCoins, result + 1)
        }
      }
      
      val result = if (minCoins == Int.MaxValue) -1 else minCoins
      memo(remaining) = result
      result
    }
    
    dfs(amount)
  }
  
  // 9. Word Break
  def wordBreak(s: String, wordDict: List[String]): Boolean = {
    val wordSet = wordDict.toSet
    val memo = mutable.Map[Int, Boolean]()
    
    def dfs(start: Int): Boolean = {
      if (start == s.length) return true
      if (memo.contains(start)) return memo(start)
      
      for (end <- start + 1 to s.length) {
        val word = s.substring(start, end)
        if (wordSet.contains(word) && dfs(end)) {
          memo(start) = true
          return true
        }
      }
      
      memo(start) = false
      false
    }
    
    dfs(0)
  }
  
  // 10. Palindrome Partitioning II
  def minCut(s: String): Int = {
    val n = s.length
    val isPalindrome = Array.ofDim[Boolean](n, n)
    
    // Precompute palindromes
    for (i <- n - 1 to 0 by -1; j <- i until n) {
      isPalindrome(i)(j) = s(i) == s(j) && (j - i <= 2 || isPalindrome(i + 1)(j - 1))
    }
    
    val memo = mutable.Map[Int, Int]()
    
    def dfs(start: Int): Int = {
      if (start == n) return 0
      if (memo.contains(start)) return memo(start)
      
      var minCuts = Int.MaxValue
      for (end <- start until n) {
        if (isPalindrome(start)(end)) {
          minCuts = math.min(minCuts, 1 + dfs(end + 1))
        }
      }
      
      memo(start) = minCuts
      minCuts
    }
    
    dfs(0) - 1
  }
  
  def main(args: Array[String]): Unit = {
    println("=== DFS + Memoization Examples ===")
    
    // Test 1: Fibonacci Number
    println("Test 1 - Fibonacci(10): " + fib(10))
    
    // Test 2: Climbing Stairs
    println("Test 2 - Climb Stairs(5): " + climbStairs(5))
    
    // Test 3: House Robber
    println("Test 3 - House Robber([2,7,9,3,1]): " + rob(Array(2, 7, 9, 3, 1)))
    
    // Test 4: Unique Paths
    println("Test 4 - Unique Paths(3, 7): " + uniquePaths(3, 7))
    
    // Test 5: Longest Common Subsequence
    println("Test 5 - LCS('abcde', 'ace'): " + longestCommonSubsequence("abcde", "ace"))
    
    // Test 6: Edit Distance
    println("Test 6 - Edit Distance('horse', 'ros'): " + minDistance("horse", "ros"))
    
    // Test 7: Target Sum
    println("Test 7 - Target Sum Ways([1,1,1,1,1], 3): " + findTargetSumWays(Array(1, 1, 1, 1, 1), 3))
    
    // Test 8: Coin Change
    println("Test 8 - Coin Change([1,3,4], 6): " + coinChange(Array(1, 3, 4), 6))
    
    // Test 9: Word Break
    println("Test 9 - Word Break('leetcode', ['leet','code']): " + wordBreak("leetcode", List("leet", "code")))
    
    // Test 10: Palindrome Partitioning II
    println("Test 10 - Min Cut('aab'): " + minCut("aab"))
  }
}