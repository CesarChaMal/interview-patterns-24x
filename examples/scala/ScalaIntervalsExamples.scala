import scala.collection.mutable

object ScalaIntervalsExamples {
  
  // 1. Merge Intervals
  def merge(intervals: Array[Array[Int]]): Array[Array[Int]] = {
    if (intervals.isEmpty) return Array()
    
    val sorted = intervals.sortBy(_(0))
    val result = mutable.ListBuffer[Array[Int]]()
    result += sorted(0)
    
    for (i <- 1 until sorted.length) {
      val current = sorted(i)
      val last = result.last
      
      if (current(0) <= last(1)) {
        last(1) = math.max(last(1), current(1))
      } else {
        result += current
      }
    }
    
    result.toArray
  }
  
  // 2. Insert Interval
  def insert(intervals: Array[Array[Int]], newInterval: Array[Int]): Array[Array[Int]] = {
    val result = mutable.ListBuffer[Array[Int]]()
    var i = 0
    
    // Add all intervals before newInterval
    while (i < intervals.length && intervals(i)(1) < newInterval(0)) {
      result += intervals(i)
      i += 1
    }
    
    // Merge overlapping intervals
    var start = newInterval(0)
    var end = newInterval(1)
    while (i < intervals.length && intervals(i)(0) <= newInterval(1)) {
      start = math.min(start, intervals(i)(0))
      end = math.max(end, intervals(i)(1))
      i += 1
    }
    result += Array(start, end)
    
    // Add remaining intervals
    while (i < intervals.length) {
      result += intervals(i)
      i += 1
    }
    
    result.toArray
  }
  
  // 3. Non-overlapping Intervals
  def eraseOverlapIntervals(intervals: Array[Array[Int]]): Int = {
    if (intervals.isEmpty) return 0
    
    val sorted = intervals.sortBy(_(1))
    var count = 0
    var end = sorted(0)(1)
    
    for (i <- 1 until sorted.length) {
      if (sorted(i)(0) < end) {
        count += 1
      } else {
        end = sorted(i)(1)
      }
    }
    
    count
  }
  
  // 4. Meeting Rooms
  def canAttendMeetings(intervals: Array[Array[Int]]): Boolean = {
    val sorted = intervals.sortBy(_(0))
    for (i <- 1 until sorted.length) {
      if (sorted(i)(0) < sorted(i - 1)(1)) {
        return false
      }
    }
    true
  }
  
  // 5. Meeting Rooms II
  def minMeetingRooms(intervals: Array[Array[Int]]): Int = {
    val starts = intervals.map(_(0)).sorted
    val ends = intervals.map(_(1)).sorted
    
    var rooms = 0
    var endPtr = 0
    
    for (start <- starts) {
      if (start >= ends(endPtr)) {
        endPtr += 1
      } else {
        rooms += 1
      }
    }
    
    rooms
  }
  
  // 6. Interval List Intersections
  def intervalIntersection(firstList: Array[Array[Int]], secondList: Array[Array[Int]]): Array[Array[Int]] = {
    val result = mutable.ListBuffer[Array[Int]]()
    var i = 0
    var j = 0
    
    while (i < firstList.length && j < secondList.length) {
      val start = math.max(firstList(i)(0), secondList(j)(0))
      val end = math.min(firstList(i)(1), secondList(j)(1))
      
      if (start <= end) {
        result += Array(start, end)
      }
      
      if (firstList(i)(1) < secondList(j)(1)) {
        i += 1
      } else {
        j += 1
      }
    }
    
    result.toArray
  }
  
  // 7. Employee Free Time
  final class Interval(var start: Int, var end: Int)
  object Interval {
    def apply(start: Int, end: Int): Interval = new Interval(start, end)
  }

  def employeeFreeTime(schedule: List[List[Interval]]): List[Interval] = {
    val allIntervals = schedule.flatten.sortBy(_.start)
    val merged = mutable.ListBuffer[Interval]()
    
    for (interval <- allIntervals) {
      if (merged.isEmpty || merged.last.end < interval.start) {
        merged += interval
      } else {
        val lastInterval = merged.last
        merged(merged.length - 1) = Interval(lastInterval.start, math.max(lastInterval.end, interval.end))
      }
    }
    
    val result = mutable.ListBuffer[Interval]()
    for (i <- 1 until merged.length) {
      result += Interval(merged(i - 1).end, merged(i).start)
    }
    
    result.toList
  }
  
  // 8. Summary Ranges
  def summaryRanges(nums: Array[Int]): List[String] = {
    if (nums.isEmpty) return List()
    
    val result = mutable.ListBuffer[String]()
    var start = nums(0)
    var end = nums(0)
    
    for (i <- 1 until nums.length) {
      if (nums(i) == end + 1) {
        end = nums(i)
      } else {
        if (start == end) {
          result += start.toString
        } else {
          result += s"$start->$end"
        }
        start = nums(i)
        end = nums(i)
      }
    }
    
    if (start == end) {
      result += start.toString
    } else {
      result += s"$start->$end"
    }
    
    result.toList
  }
  
  // 9. Missing Ranges
  def findMissingRanges(nums: Array[Int], lower: Int, upper: Int): List[String] = {
    val result = mutable.ListBuffer[String]()
    
    def formatRange(start: Int, end: Int): String = {
      if (start == end) start.toString else s"$start->$end"
    }
    
    var prev = lower - 1
    
    for (i <- nums.indices :+ nums.length) {
      val curr = if (i == nums.length) upper + 1 else nums(i)
      
      if (curr - prev >= 2) {
        result += formatRange(prev + 1, curr - 1)
      }
      prev = curr
    }
    
    result.toList
  }
  
  // 10. Data Stream as Disjoint Intervals
  class SummaryRanges {
    private val intervals = mutable.TreeMap[Int, Int]()
    
    def addNum(value: Int): Unit = {
      var start = value
      var end = value
      
      // Check if we can merge with previous interval
      intervals.maxBefore(value) match {
        case Some((s, e)) if e >= value - 1 =>
          start = s
          end = math.max(e, value)
          intervals.remove(s)
        case _ =>
      }
      
      // Check if we can merge with next interval
      intervals.minAfter(value) match {
        case Some((s, e)) if s <= value + 1 =>
          end = math.max(end, e)
          intervals.remove(s)
        case _ =>
      }
      
      intervals(start) = end
    }
    
    def getIntervals(): Array[Array[Int]] = {
      intervals.map { case (start, end) => Array(start, end) }.toArray
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Intervals Examples ===")
    
    // Test 1: Merge Intervals
    val intervals1 = Array(Array(1,3), Array(2,6), Array(8,10), Array(15,18))
    println("Test 1 - Merge Intervals: " + merge(intervals1).map(_.mkString("[", ",", "]")).mkString("[", ",", "]"))
    
    // Test 2: Insert Interval
    val intervals2 = Array(Array(1,3), Array(6,9))
    val newInterval = Array(2,5)
    println("Test 2 - Insert Interval: " + insert(intervals2, newInterval).map(_.mkString("[", ",", "]")).mkString("[", ",", "]"))
    
    // Test 3: Non-overlapping Intervals
    val intervals3 = Array(Array(1,2), Array(2,3), Array(3,4), Array(1,3))
    println("Test 3 - Erase Overlap Intervals: " + eraseOverlapIntervals(intervals3))
    
    // Test 4: Meeting Rooms
    val meetings1 = Array(Array(0,30), Array(5,10), Array(15,20))
    println("Test 4 - Can Attend Meetings: " + canAttendMeetings(meetings1))
    
    // Test 5: Meeting Rooms II
    val meetings2 = Array(Array(0,30), Array(5,10), Array(15,20))
    println("Test 5 - Min Meeting Rooms: " + minMeetingRooms(meetings2))
    
    // Test 6: Interval List Intersections
    val first = Array(Array(0,2), Array(5,10), Array(13,23), Array(24,25))
    val second = Array(Array(1,5), Array(8,12), Array(15,24), Array(25,26))
    println("Test 6 - Interval Intersection: " + intervalIntersection(first, second).map(_.mkString("[", ",", "]")).mkString("[", ",", "]"))
    
    // Test 7: Employee Free Time
    val schedule = List(List(Interval(1,3), Interval(6,7)), List(Interval(2,4)), List(Interval(2,5), Interval(9,12)))
    println("Test 7 - Employee Free Time: " + employeeFreeTime(schedule).length + " free intervals")
    
    // Test 8: Summary Ranges
    val nums = Array(0,1,2,4,5,7)
    println("Test 8 - Summary Ranges: Completed")
    
    // Test 9: Missing Ranges
    val nums2 = Array(0,1,3,50,75)
    println("Test 9 - Missing Ranges: " + findMissingRanges(nums2, 0, 99))
    
    // Test 10: Data Stream as Disjoint Intervals
    val summaryRanges = new SummaryRanges()
    summaryRanges.addNum(1)
    summaryRanges.addNum(3)
    summaryRanges.addNum(7)
    println("Test 10 - Summary Ranges: " + summaryRanges.getIntervals().length + " intervals")
  }
}