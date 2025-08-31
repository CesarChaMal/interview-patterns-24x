object ScalaFastSlowPointersExamples {
  
  // Replace case class to avoid heavy synthetic methods (toString/copy) that may trip scalac emitter on JDK 24
  final class ListNode(var x: Int, var next: ListNode) {
    def this(x: Int) = this(x, null)
    def this() = this(0, null)
  }
  object ListNode {
    def apply(x: Int): ListNode = new ListNode(x)
    def apply(x: Int, next: ListNode): ListNode = new ListNode(x, next)
  }

  // 1. Linked List Cycle
  def hasCycle(head: ListNode): Boolean = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
      if (slow eq fast) return true
    }
    false
  }
  
  // 2. Linked List Cycle II
  def detectCycle(head: ListNode): ListNode = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
      if (slow eq fast) {
        var ptr = head
        while (ptr ne slow) {
          ptr = ptr.next
          slow = slow.next
        }
        return ptr
      }
    }
    null
  }
  
  // 3. Middle of the Linked List
  def middleNode(head: ListNode): ListNode = {
    var slow = head
    var fast = head
    while (fast != null && fast.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    slow
  }
  
  // 4. Happy Number
  def isHappy(n: Int): Boolean = {
    def getNext(num: Int): Int = {
      var sum = 0
      var temp = num
      while (temp > 0) {
        val digit = temp % 10
        sum += digit * digit
        temp /= 10
      }
      sum
    }
    
    var slow = n
    var fast = n
    do {
      slow = getNext(slow)
      fast = getNext(getNext(fast))
    } while (slow != fast)
    
    slow == 1
  }
  
  // 5. Find the Duplicate Number
  def findDuplicate(nums: Array[Int]): Int = {
    var slow = nums(0)
    var fast = nums(0)
    
    do {
      slow = nums(slow)
      fast = nums(nums(fast))
    } while (slow != fast)
    
    var ptr = nums(0)
    while (ptr != slow) {
      ptr = nums(ptr)
      slow = nums(slow)
    }
    ptr
  }
  
  // 6. Palindrome Linked List
  def isPalindrome(head: ListNode): Boolean = {
    if (head == null || head.next == null) return true
    
    // Find middle
    var slow = head
    var fast = head
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    
    // Reverse second half
    def reverse(node: ListNode): ListNode = {
      var prev: ListNode = null
      var curr = node
      while (curr != null) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
      }
      prev
    }
    
    val secondHalf = reverse(slow.next)
    
    // Compare
    var p1 = head
    var p2 = secondHalf
    while (p2 != null) {
      if (p1.x != p2.x) return false
      p1 = p1.next
      p2 = p2.next
    }
    true
  }
  
  // 7. Remove Nth Node From End of List
  def removeNthFromEnd(head: ListNode, n: Int): ListNode = {
    val dummy = new ListNode(0, head)
    var slow = dummy
    var fast = dummy
    
    var i = 0
    while (i <= n && fast != null) { fast = fast.next; i += 1 }

    while (fast != null) {
      slow = slow.next
      fast = fast.next
    }
    
    if (slow.next != null) slow.next = slow.next.next
    dummy.next
  }
  
  // 8. Intersection of Two Linked Lists
  def getIntersectionNode(headA: ListNode, headB: ListNode): ListNode = {
    if (headA == null || headB == null) return null
    
    var pA = headA
    var pB = headB
    
    while (pA ne pB) {
      pA = if (pA == null) headB else pA.next
      pB = if (pB == null) headA else pB.next
    }
    pA
  }
  
  // 9. Circular Array Loop
  def circularArrayLoop(nums: Array[Int]): Boolean = {
    def getNext(i: Int): Int = {
      val n = nums.length
      ((i + nums(i)) % n + n) % n
    }
    
    for (i <- nums.indices) {
      if (nums(i) != 0) {
        var slow = i
        var fast = i

        while (nums(slow) * nums(getNext(slow)) > 0 &&
               nums(fast) * nums(getNext(fast)) > 0 &&
               nums(fast) * nums(getNext(getNext(fast))) > 0) {
          slow = getNext(slow)
          fast = getNext(getNext(fast))

          if (slow == fast) {
            // reject 1-length loop
            if (slow == getNext(slow)) return false
            return true
          }
        }

        // Mark as visited
        var curr = i
        while (nums(curr) * nums(getNext(curr)) > 0) {
          val next = getNext(curr)
          nums(curr) = 0
          curr = next
        }
      }
    }
    false
  }
  
  // 10. Reorder List
  def reorderList(head: ListNode): Unit = {
    if (head == null || head.next == null) return
    
    // Find middle
    var slow = head
    var fast = head
    while (fast.next != null && fast.next.next != null) {
      slow = slow.next
      fast = fast.next.next
    }
    
    // Reverse second half
    def reverse(node: ListNode): ListNode = {
      var prev: ListNode = null
      var curr = node
      while (curr != null) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
      }
      prev
    }
    
    val secondHalf = reverse(slow.next)
    slow.next = null
    
    // Merge
    var first = head
    var second = secondHalf
    while (second != null) {
      val temp1 = first.next
      val temp2 = second.next
      first.next = second
      second.next = temp1
      first = temp1
      second = temp2
    }
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Fast/Slow Pointers Examples ===")
    
    // Test 1: Linked List Cycle
    val node1 = ListNode(3)
    val node2 = ListNode(2)
    val node3 = ListNode(0)
    val node4 = ListNode(-4)
    node1.next = node2
    node2.next = node3
    node3.next = node4
    node4.next = node2
    println("Test 1 - Has Cycle: " + hasCycle(node1))
    
    // Test 2: Linked List Cycle II
    println("Test 2 - Detect Cycle: " + (if (detectCycle(node1) != null) "Found" else "Not Found"))
    
    // Test 3: Middle of the Linked List
    val list = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    println("Test 3 - Middle Node: " + middleNode(list).x)
    
    // Test 4: Happy Number
    println("Test 4 - Is Happy(19): " + isHappy(19))
    
    // Test 5: Find the Duplicate Number
    println("Test 5 - Find Duplicate([1,3,4,2,2]): " + findDuplicate(Array(1,3,4,2,2)))
    
    // Test 6: Palindrome Linked List
    val palindrome = ListNode(1, ListNode(2, ListNode(2, ListNode(1))))
    println("Test 6 - Is Palindrome: " + isPalindrome(palindrome))
    
    // Test 7: Remove Nth Node From End
    val removeList = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    val result = removeNthFromEnd(removeList, 2)
    println("Test 7 - Remove Nth From End: Completed")
    
    // Test 8: Intersection of Two Linked Lists
    val common = ListNode(8, ListNode(4, ListNode(5)))
    val listA = ListNode(4, ListNode(1, common))
    val listB = ListNode(5, ListNode(6, ListNode(1, common)))
    println("Test 8 - Intersection: " + (if (getIntersectionNode(listA, listB) != null) "Found" else "Not Found"))
    
    // Test 9: Circular Array Loop
    println("Test 9 - Circular Array Loop([2,-1,1,2,2]): " + circularArrayLoop(Array(2,-1,1,2,2)))
    
    // Test 10: Reorder List
    val reorderHead = ListNode(1, ListNode(2, ListNode(3, ListNode(4))))
    reorderList(reorderHead)
    println("Test 10 - Reorder List: Completed")
  }
}