object ScalaLinkedListReversalExamples {
  
  final class ListNode(var x: Int, var next: ListNode) {
    def this(x: Int) = this(x, null)
    def this() = this(0, null)
  }
  object ListNode {
    def apply(x: Int): ListNode = new ListNode(x)
    def apply(x: Int, next: ListNode): ListNode = new ListNode(x, next)
  }

  // 1. Reverse Linked List
  def reverseList(head: ListNode): ListNode = {
    var prev: ListNode = null
    var curr = head
    while (curr != null) {
      val next = curr.next
      curr.next = prev
      prev = curr
      curr = next
    }
    prev
  }
  
  // 2. Reverse Linked List II
  def reverseBetween(head: ListNode, left: Int, right: Int): ListNode = {
    if (head == null || left == right) return head
    
    val dummy = ListNode(0)
    dummy.next = head
    var prev = dummy
    
    for (_ <- 1 until left) {
      prev = prev.next
    }
    
    val start = prev.next
    var `then` = start.next
    
    for (_ <- 0 until right - left) {
      start.next = `then`.next
      `then`.next = prev.next
      prev.next = `then`
      `then` = start.next
    }
    
    dummy.next
  }
  
  // 3. Reverse Nodes in k-Group
  def reverseKGroup(head: ListNode, k: Int): ListNode = {
    def getLength(node: ListNode): Int = {
      var count = 0
      var curr = node
      while (curr != null) {
        count += 1
        curr = curr.next
      }
      count
    }
    
    def reverseK(node: ListNode, k: Int): ListNode = {
      var prev: ListNode = null
      var curr = node
      for (_ <- 0 until k) {
        val next = curr.next
        curr.next = prev
        prev = curr
        curr = next
      }
      prev
    }
    
    val length = getLength(head)
    val dummy = ListNode(0)
    dummy.next = head
    var prevGroup = dummy
    
    for (_ <- 0 until length / k) {
      val groupStart = prevGroup.next
      val groupEnd = groupStart
      for (_ <- 1 until k) {
        groupEnd.next = groupEnd.next
      }
      val nextGroup = groupEnd.next
      
      val newGroupStart = reverseK(groupStart, k)
      prevGroup.next = newGroupStart
      groupStart.next = nextGroup
      prevGroup = groupStart
    }
    
    dummy.next
  }
  
  // 4. Swap Nodes in Pairs
  def swapPairs(head: ListNode): ListNode = {
    val dummy = ListNode(0)
    dummy.next = head
    var prev = dummy
    
    while (prev.next != null && prev.next.next != null) {
      val first = prev.next
      val second = prev.next.next
      
      prev.next = second
      first.next = second.next
      second.next = first
      prev = first
    }
    
    dummy.next
  }
  
  // 5. Rotate List
  def rotateRight(head: ListNode, k: Int): ListNode = {
    if (head == null || head.next == null || k == 0) return head
    
    var length = 1
    var tail = head
    while (tail.next != null) {
      tail = tail.next
      length += 1
    }
    
    val rotations = k % length
    if (rotations == 0) return head
    
    val newTailPos = length - rotations
    var newTail = head
    for (_ <- 1 until newTailPos) {
      newTail = newTail.next
    }
    
    val newHead = newTail.next
    newTail.next = null
    tail.next = head
    
    newHead
  }
  
  // 6. Remove Duplicates from Sorted List II
  def deleteDuplicates(head: ListNode): ListNode = {
    val dummy = ListNode(0)
    dummy.next = head
    var prev = dummy
    var curr = head
    
    while (curr != null) {
      if (curr.next != null && curr.x == curr.next.x) {
        val value = curr.x
        while (curr != null && curr.x == value) {
          curr = curr.next
        }
        prev.next = curr
      } else {
        prev = curr
        curr = curr.next
      }
    }
    
    dummy.next
  }
  
  // 7. Partition List
  def partition(head: ListNode, x: Int): ListNode = {
    val beforeHead = ListNode(0)
    val afterHead = ListNode(0)
    var before = beforeHead
    var after = afterHead
    var curr = head
    
    while (curr != null) {
      if (curr.x < x) {
        before.next = curr
        before = before.next
      } else {
        after.next = curr
        after = after.next
      }
      curr = curr.next
    }
    
    after.next = null
    before.next = afterHead.next
    beforeHead.next
  }
  
  // 8. Reverse Nodes in Even Length Groups
  def reverseEvenLengthGroups(head: ListNode): ListNode = {
    var prev = head
    var groupLength = 2
    
    while (prev.next != null) {
      var curr = prev.next
      var count = 0
      
      // Count nodes in current group
      while (curr != null && count < groupLength) {
        curr = curr.next
        count += 1
      }
      
      // Reverse if even length
      if (count % 2 == 0) {
        val groupStart = prev.next
        var reversePrev: ListNode = null
        var reverseCurr = groupStart
        
        for (_ <- 0 until count) {
          val next = reverseCurr.next
          reverseCurr.next = reversePrev
          reversePrev = reverseCurr
          reverseCurr = next
        }
        
        prev.next = reversePrev
        groupStart.next = reverseCurr
        prev = groupStart
      } else {
        for (_ <- 0 until count) {
          prev = prev.next
        }
      }
      
      groupLength += 1
    }
    
    head
  }
  
  // 9. Add Two Numbers
  def addTwoNumbers(l1: ListNode, l2: ListNode): ListNode = {
    val dummy = ListNode(0)
    var curr = dummy
    var carry = 0
    var p1 = l1
    var p2 = l2
    
    while (p1 != null || p2 != null || carry != 0) {
      val val1 = if (p1 != null) p1.x else 0
      val val2 = if (p2 != null) p2.x else 0
      val sum = val1 + val2 + carry
      
      carry = sum / 10
      curr.next = ListNode(sum % 10)
      curr = curr.next
      
      if (p1 != null) p1 = p1.next
      if (p2 != null) p2 = p2.next
    }
    
    dummy.next
  }
  
  // 10. Merge Two Sorted Lists
  def mergeTwoLists(list1: ListNode, list2: ListNode): ListNode = {
    val dummy = ListNode(0)
    var curr = dummy
    var l1 = list1
    var l2 = list2
    
    while (l1 != null && l2 != null) {
      if (l1.x <= l2.x) {
        curr.next = l1
        l1 = l1.next
      } else {
        curr.next = l2
        l2 = l2.next
      }
      curr = curr.next
    }
    
    curr.next = if (l1 != null) l1 else l2
    dummy.next
  }
  
  def main(args: Array[String]): Unit = {
    println("=== Linked List Reversal Examples ===")
    
    // Test 1: Reverse Linked List
    val list1 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    val reversed = reverseList(list1)
    println("Test 1 - Reverse Linked List: Completed")
    
    // Test 2: Reverse Linked List II
    val list2 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    val reversedBetween = reverseBetween(list2, 2, 4)
    println("Test 2 - Reverse Between: Completed")
    
    // Test 3: Reverse Nodes in k-Group
    val list3 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    val reversedK = reverseKGroup(list3, 2)
    println("Test 3 - Reverse K Group: Completed")
    
    // Test 4: Swap Nodes in Pairs
    val list4 = ListNode(1, ListNode(2, ListNode(3, ListNode(4))))
    val swapped = swapPairs(list4)
    println("Test 4 - Swap Pairs: Completed")
    
    // Test 5: Rotate List
    val list5 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    val rotated = rotateRight(list5, 2)
    println("Test 5 - Rotate Right: Completed")
    
    // Test 6: Remove Duplicates from Sorted List II
    val list6 = ListNode(1, ListNode(2, ListNode(3, ListNode(3, ListNode(4, ListNode(4, ListNode(5)))))))
    val noDuplicates = deleteDuplicates(list6)
    println("Test 6 - Delete Duplicates: Completed")
    
    // Test 7: Partition List
    val list7 = ListNode(1, ListNode(4, ListNode(3, ListNode(2, ListNode(5, ListNode(2))))))
    val partitioned = partition(list7, 3)
    println("Test 7 - Partition List: Completed")
    
    // Test 8: Reverse Nodes in Even Length Groups
    val list8 = ListNode(5, ListNode(2, ListNode(6, ListNode(3, ListNode(9, ListNode(1, ListNode(7, ListNode(3, ListNode(8, ListNode(4))))))))))
    val reversedEven = reverseEvenLengthGroups(list8)
    println("Test 8 - Reverse Even Length Groups: Completed")
    
    // Test 9: Add Two Numbers
    val num1 = ListNode(2, ListNode(4, ListNode(3)))
    val num2 = ListNode(5, ListNode(6, ListNode(4)))
    val sum = addTwoNumbers(num1, num2)
    println("Test 9 - Add Two Numbers: Completed")
    
    // Test 10: Merge Two Sorted Lists
    val sorted1 = ListNode(1, ListNode(2, ListNode(4)))
    val sorted2 = ListNode(1, ListNode(3, ListNode(4)))
    val merged = mergeTwoLists(sorted1, sorted2)
    println("Test 10 - Merge Two Sorted Lists: Completed")
  }
}