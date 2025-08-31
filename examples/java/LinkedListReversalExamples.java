public class LinkedListReversalExamples {
    
    // 1. Reverse Linked List
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
    
    // 2. Reverse Linked List II
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        for (int i = 0; i < left - 1; i++) {
            prev = prev.next;
        }
        
        ListNode curr = prev.next;
        for (int i = 0; i < right - left; i++) {
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }
        
        return dummy.next;
    }
    
    // 3. Reverse Nodes in k-Group
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode curr = head;
        int count = 0;
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }
        
        if (count == k) {
            curr = reverseKGroup(curr, k);
            while (count-- > 0) {
                ListNode next = head.next;
                head.next = curr;
                curr = head;
                head = next;
            }
            head = curr;
        }
        return head;
    }
    
    // 4. Swap Nodes in Pairs
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;
            ListNode second = prev.next.next;
            
            prev.next = second;
            first.next = second.next;
            second.next = first;
            prev = first;
        }
        
        return dummy.next;
    }
    
    // 5. Rotate List
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        
        ListNode tail = head;
        int length = 1;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }
        
        k = k % length;
        if (k == 0) return head;
        
        ListNode newTail = head;
        for (int i = 0; i < length - k - 1; i++) {
            newTail = newTail.next;
        }
        
        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;
        
        return newHead;
    }
    
    // 6. Odd Even Linked List
    public ListNode oddEvenList(ListNode head) {
        if (head == null) return null;
        
        ListNode odd = head, even = head.next, evenHead = even;
        
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        
        odd.next = evenHead;
        return head;
    }
    
    // 7. Partition List
    public ListNode partition(ListNode head, int x) {
        ListNode before = new ListNode(0), after = new ListNode(0);
        ListNode beforeCurr = before, afterCurr = after;
        
        while (head != null) {
            if (head.val < x) {
                beforeCurr.next = head;
                beforeCurr = beforeCurr.next;
            } else {
                afterCurr.next = head;
                afterCurr = afterCurr.next;
            }
            head = head.next;
        }
        
        afterCurr.next = null;
        beforeCurr.next = after.next;
        return before.next;
    }
    
    // 8. Reverse Nodes in Even Length Groups
    public ListNode reverseEvenLengthGroups(ListNode head) {
        ListNode prev = head;
        int groupLen = 2;
        
        while (prev.next != null) {
            ListNode node = prev;
            int nodeCount = 0;
            
            for (int i = 0; i < groupLen && node.next != null; i++) {
                node = node.next;
                nodeCount++;
            }
            
            if (nodeCount % 2 == 0) {
                ListNode reverse = reverseGroup(prev.next, nodeCount);
                ListNode temp = prev.next;
                prev.next = reverse;
                prev = temp;
            } else {
                prev = node;
            }
            
            groupLen++;
        }
        
        return head;
    }
    
    private ListNode reverseGroup(ListNode head, int k) {
        ListNode prev = null;
        ListNode curr = head;
        
        while (k-- > 0 && curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        head.next = curr;
        return prev;
    }
    
    // 9. Add Two Numbers
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            int sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
        }
        
        return dummy.next;
    }
    
    // 10. Remove Duplicates from Sorted List II
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        
        while (head != null) {
            if (head.next != null && head.val == head.next.val) {
                while (head.next != null && head.val == head.next.val) {
                    head = head.next;
                }
                prev.next = head.next;
            } else {
                prev = prev.next;
            }
            head = head.next;
        }
        
        return dummy.next;
    }
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public static void main(String[] args) {
        LinkedListReversalExamples examples = new LinkedListReversalExamples();
        
        System.out.println("=== Linked List Reversal Examples ===");
        
        // Test 1: Reverse Linked List
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode reversed = examples.reverseList(list1);
        System.out.println("Reverse List completed");
        
        // Test 2: Reverse Between
        ListNode list2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode reversedBetween = examples.reverseBetween(list2, 2, 4);
        System.out.println("Reverse Between completed");
        
        // Test 3: Reverse K Group
        ListNode list3 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode reversedK = examples.reverseKGroup(list3, 2);
        System.out.println("Reverse K Group completed");
        
        // Test 4: Swap Pairs
        ListNode list4 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        ListNode swapped = examples.swapPairs(list4);
        System.out.println("Swap Pairs completed");
        
        // Test 5: Rotate Right
        ListNode list5 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode rotated = examples.rotateRight(list5, 2);
        System.out.println("Rotate Right completed");
        
        // Test 6: Odd Even List
        ListNode list6 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode oddEven = examples.oddEvenList(list6);
        System.out.println("Odd Even List completed");
        
        // Test 7: Partition List
        ListNode list7 = new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2))))));
        ListNode partitioned = examples.partition(list7, 3);
        System.out.println("Partition List completed");
        
        // Test 9: Add Two Numbers
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
        ListNode sum = examples.addTwoNumbers(l1, l2);
        System.out.println("Add Two Numbers completed");
        
        // Test 10: Delete Duplicates
        ListNode list10 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
        ListNode noDuplicates = examples.deleteDuplicates(list10);
        System.out.println("Delete Duplicates completed");
    }
}