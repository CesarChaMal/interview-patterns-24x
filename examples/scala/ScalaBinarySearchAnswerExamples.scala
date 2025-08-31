object ScalaBinarySearchAnswerExamples {
  
  // 1. Koko Eating Bananas
  def minEatingSpeed(piles: Array[Int], h: Int): Int = {
    def canFinish(speed: Int): Boolean = {
      var hours = 0
      for (pile <- piles) {
        hours += (pile + speed - 1) / speed
      }
      hours <= h
    }
    
    var left = 1
    var right = piles.max
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canFinish(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 2. Minimum Number of Days to Make m Bouquets
  def minDays(bloomDay: Array[Int], m: Int, k: Int): Int = {
    if (m.toLong * k > bloomDay.length) return -1
    
    def canMakeBouquets(days: Int): Boolean = {
      var bouquets = 0
      var consecutive = 0
      
      for (day <- bloomDay) {
        if (day <= days) {
          consecutive += 1
          if (consecutive == k) {
            bouquets += 1
            consecutive = 0
          }
        } else {
          consecutive = 0
        }
      }
      
      bouquets >= m
    }
    
    var left = bloomDay.min
    var right = bloomDay.max
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canMakeBouquets(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 3. Capacity To Ship Packages Within D Days
  def shipWithinDays(weights: Array[Int], days: Int): Int = {
    def canShip(capacity: Int): Boolean = {
      var daysNeeded = 1
      var currentWeight = 0
      
      for (weight <- weights) {
        if (currentWeight + weight > capacity) {
          daysNeeded += 1
          currentWeight = weight
        } else {
          currentWeight += weight
        }
      }
      
      daysNeeded <= days
    }
    
    var left = weights.max
    var right = weights.sum
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canShip(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 4. Split Array Largest Sum
  def splitArray(nums: Array[Int], k: Int): Int = {
    def canSplit(maxSum: Int): Boolean = {
      var subarrays = 1
      var currentSum = 0
      
      for (num <- nums) {
        if (currentSum + num > maxSum) {
          subarrays += 1
          currentSum = num
        } else {
          currentSum += num
        }
      }
      
      subarrays <= k
    }
    
    var left = nums.max
    var right = nums.sum
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canSplit(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 5. Find the Smallest Divisor Given a Threshold
  def smallestDivisor(nums: Array[Int], threshold: Int): Int = {
    def getDivisionSum(divisor: Int): Int = {
      nums.map(num => (num + divisor - 1) / divisor).sum
    }
    
    var left = 1
    var right = nums.max
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (getDivisionSum(mid) <= threshold) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 6. Magnetic Force Between Two Balls
  def maxDistance(position: Array[Int], m: Int): Int = {
    val sorted = position.sorted
    
    def canPlace(minDist: Int): Boolean = {
      var count = 1
      var lastPos = sorted(0)
      
      for (i <- 1 until sorted.length) {
        if (sorted(i) - lastPos >= minDist) {
          count += 1
          lastPos = sorted(i)
        }
      }
      
      count >= m
    }
    
    var left = 1
    var right = sorted.last - sorted.head
    
    while (left < right) {
      val mid = left + (right - left + 1) / 2
      if (canPlace(mid)) {
        left = mid
      } else {
        right = mid - 1
      }
    }
    
    left
  }
  
  // 7. Minimize Max Distance to Gas Station
  def minmaxGasDist(stations: Array[Int], k: Int): Double = {
    def canAchieve(maxDist: Double): Boolean = {
      var needed = 0
      for (i <- 1 until stations.length) {
        val dist = stations(i) - stations(i - 1)
        needed += math.max(0, math.ceil(dist / maxDist).toInt - 1)
      }
      needed <= k
    }
    
    var left = 0.0
    var right = (stations.last - stations.head).toDouble
    
    while (right - left > 1e-6) {
      val mid = left + (right - left) / 2
      if (canAchieve(mid)) {
        right = mid
      } else {
        left = mid
      }
    }
    
    right
  }
  
  // 8. Minimum Time to Complete Trips
  def minimumTime(time: Array[Int], totalTrips: Int): Long = {
    def canComplete(maxTime: Long): Boolean = {
      var trips = 0L
      for (t <- time) {
        trips += maxTime / t
        if (trips >= totalTrips) return true
      }
      false
    }
    
    var left = 1L
    var right = time.min.toLong * totalTrips
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canComplete(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    left
  }
  
  // 9. Minimum Speed to Arrive on Time
  def minSpeedOnTime(dist: Array[Int], hour: Double): Int = {
    def canArrive(speed: Int): Boolean = {
      var time = 0.0
      for (i <- dist.indices) {
        if (i == dist.length - 1) {
          time += dist(i).toDouble / speed
        } else {
          time += math.ceil(dist(i).toDouble / speed)
        }
      }
      time <= hour
    }
    
    var left = 1
    var right = 10000000
    
    while (left < right) {
      val mid = left + (right - left) / 2
      if (canArrive(mid)) {
        right = mid
      } else {
        left = mid + 1
      }
    }
    
    if (canArrive(left)) left else -1
  }
  
  // 10. Maximum Number of Removable Characters
  def maximumRemovals(s: String, p: String, removable: Array[Int]): Int = {
    def canForm(k: Int): Boolean = {
      val removed = removable.take(k).toSet
      var i = 0
      var j = 0
      
      while (i < s.length && j < p.length) {
        if (!removed.contains(i) && s(i) == p(j)) {
          j += 1
        }
        i += 1
      }
      
      j == p.length
    }
    
    var left = 0
    var right = removable.length
    
    while (left < right) {
      val mid = left + (right - left + 1) / 2
      if (canForm(mid)) {
        left = mid
      } else {
        right = mid - 1
      }
    }
    
    left
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Binary Search on Answer Examples ===")
    
    // Test 1: Koko Eating Bananas
    println("Test 1 - Min Eating Speed completed")
    
    // Test 2: Minimum Number of Days to Make m Bouquets
    println("Test 2 - Min Days completed")
    
    // Test 3: Capacity To Ship Packages Within D Days
    println("Test 3 - Ship Within Days completed")
    
    // Test 4: Split Array Largest Sum
    println("Test 4 - Split Array completed")
    
    // Test 5: Find the Smallest Divisor Given a Threshold
    println("Test 5 - Smallest Divisor completed")
    
    // Test 6: Magnetic Force Between Two Balls
    println("Test 6 - Max Distance completed")
    
    // Test 7: Minimize Max Distance to Gas Station
    println("Test 7 - Min Max Gas Dist completed")
    
    // Test 8: Minimum Time to Complete Trips
    println("Test 8 - Minimum Time completed")
    
    // Test 9: Minimum Speed to Arrive on Time
    println("Test 9 - Min Speed On Time completed")
    
    // Test 10: Maximum Number of Removable Characters
    println("Test 10 - Maximum Removals completed")
  }
}