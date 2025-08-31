import scala.collection.mutable

object ScalaTwoPointersExamples {
  
  // 1. Two Sum II - Input Array Is Sorted
  def twoSum(numbers: Array[Int], target: Int): Array[Int] = {
    var left = 0
    var right = numbers.length - 1
    while (left < right) {
      val sum = numbers(left) + numbers(right)
      if (sum == target) return Array(left + 1, right + 1)
      else if (sum < target) left += 1
      else right -= 1
    }
    Array()
  }
  
  // 2. 3Sum
  def threeSum(nums: Array[Int]): List[List[Int]] = {
    val sorted = nums.sorted
    val result = mutable.ListBuffer[List[Int]]()
    for (i <- sorted.indices.dropRight(2)) {
      if (i == 0 || sorted(i) != sorted(i - 1)) {
        var left = i + 1
        var right = sorted.length - 1
        while (left < right) {
          val sum = sorted(i) + sorted(left) + sorted(right)
          if (sum == 0) {
            result += List(sorted(i), sorted(left), sorted(right))
            while (left < right && sorted(left) == sorted(left + 1)) left += 1
            while (left < right && sorted(right) == sorted(right - 1)) right -= 1
            left += 1
            right -= 1
          } else if (sum < 0) left += 1
          else right -= 1
        }
      }
    }
    result.toList
  }
  
  // 3. Container With Most Water
  def maxArea(height: Array[Int]): Int = {
    var left = 0
    var right = height.length - 1
    var maxArea = 0
    while (left < right) {
      val area = math.min(height(left), height(right)) * (right - left)
      maxArea = math.max(maxArea, area)
      if (height(left) < height(right)) left += 1
      else right -= 1
    }
    maxArea
  }
  
  // 4. Valid Palindrome
  def isPalindrome(s: String): Boolean = {
    var left = 0
    var right = s.length - 1
    while (left < right) {
      while (left < right && !s(left).isLetterOrDigit) left += 1
      while (left < right && !s(right).isLetterOrDigit) right -= 1
      if (s(left).toLower != s(right).toLower) return false
      left += 1
      right -= 1
    }
    true
  }
  
  // 5. Remove Duplicates from Sorted Array
  def removeDuplicates(nums: Array[Int]): Int = {
    var slow = 0
    for (fast <- 1 until nums.length) {
      if (nums(fast) != nums(slow)) {
        slow += 1
        nums(slow) = nums(fast)
      }
    }
    slow + 1
  }
  
  // 6. Move Zeroes
  def moveZeroes(nums: Array[Int]): Unit = {
    var slow = 0
    for (fast <- nums.indices) {
      if (nums(fast) != 0) {
        nums(slow) = nums(fast)
        slow += 1
      }
    }
    while (slow < nums.length) {
      nums(slow) = 0
      slow += 1
    }
  }
  
  // 7. Trapping Rain Water
  def trap(height: Array[Int]): Int = {
    var left = 0
    var right = height.length - 1
    var leftMax = 0
    var rightMax = 0
    var water = 0
    while (left < right) {
      if (height(left) < height(right)) {
        if (height(left) >= leftMax) leftMax = height(left)
        else water += leftMax - height(left)
        left += 1
      } else {
        if (height(right) >= rightMax) rightMax = height(right)
        else water += rightMax - height(right)
        right -= 1
      }
    }
    water
  }
  
  // 8. Sort Colors
  def sortColors(nums: Array[Int]): Unit = {
    var left = 0
    var right = nums.length - 1
    var i = 0
    while (i <= right) {
      if (nums(i) == 0) {
        val temp = nums(i)
        nums(i) = nums(left)
        nums(left) = temp
        left += 1
        i += 1
      } else if (nums(i) == 2) {
        val temp = nums(i)
        nums(i) = nums(right)
        nums(right) = temp
        right -= 1
      } else {
        i += 1
      }
    }
  }
  
  // 9. 4Sum
  def fourSum(nums: Array[Int], target: Int): List[List[Int]] = {
    val sorted = nums.sorted
    val result = mutable.ListBuffer[List[Int]]()
    for (i <- sorted.indices.dropRight(3)) {
      if (i == 0 || sorted(i) != sorted(i - 1)) {
        for (j <- (i + 1) until sorted.length - 2) {
          if (j == i + 1 || sorted(j) != sorted(j - 1)) {
            var left = j + 1
            var right = sorted.length - 1
            while (left < right) {
              val sum = sorted(i) + sorted(j) + sorted(left) + sorted(right)
              if (sum == target) {
                result += List(sorted(i), sorted(j), sorted(left), sorted(right))
                while (left < right && sorted(left) == sorted(left + 1)) left += 1
                while (left < right && sorted(right) == sorted(right - 1)) right -= 1
                left += 1
                right -= 1
              } else if (sum < target) left += 1
              else right -= 1
            }
          }
        }
      }
    }
    result.toList
  }
  
  // 10. Reverse String
  def reverseString(s: Array[Char]): Unit = {
    var left = 0
    var right = s.length - 1
    while (left < right) {
      val temp = s(left)
      s(left) = s(right)
      s(right) = temp
      left += 1
      right -= 1
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Two Pointers Examples ===")
    
    // Test 1: Two Sum II
    println("Test 1 - Two Sum II([2,7,11,15], 9): " + twoSum(Array(2,7,11,15), 9).mkString("[", ",", "]"))
    
    // Test 2: 3Sum
    println("Test 2 - 3Sum([-1,0,1,2,-1,-4]): " + threeSum(Array(-1,0,1,2,-1,-4)))
    
    // Test 3: Container With Most Water
    println("Test 3 - Max Area([1,8,6,2,5,4,8,3,7]): " + maxArea(Array(1,8,6,2,5,4,8,3,7)))
    
    // Test 4: Valid Palindrome
    println("Test 4 - Is Palindrome('A man, a plan, a canal: Panama'): " + isPalindrome("A man, a plan, a canal: Panama"))
    
    // Test 5: Remove Duplicates
    val nums5 = Array(1,1,2)
    println("Test 5 - Remove Duplicates([1,1,2]): " + removeDuplicates(nums5))
    
    // Test 6: Move Zeroes
    val nums6 = Array(0,1,0,3,12)
    moveZeroes(nums6)
    println("Test 6 - Move Zeroes([0,1,0,3,12]): " + nums6.mkString("[", ",", "]"))
    
    // Test 7: Trapping Rain Water
    println("Test 7 - Trap([0,1,0,2,1,0,1,3,2,1,2,1]): " + trap(Array(0,1,0,2,1,0,1,3,2,1,2,1)))
    
    // Test 8: Sort Colors
    val nums8 = Array(2,0,2,1,1,0)
    sortColors(nums8)
    println("Test 8 - Sort Colors([2,0,2,1,1,0]): " + nums8.mkString("[", ",", "]"))
    
    // Test 9: 4Sum
    println("Test 9 - 4Sum([1,0,-1,0,-2,2], 0): " + fourSum(Array(1,0,-1,0,-2,2), 0))
    
    // Test 10: Reverse String
    val str = Array('h','e','l','l','o')
    reverseString(str)
    println("Test 10 - Reverse String(['h','e','l','l','o']): " + str.mkString("[", ",", "]"))
  }
}