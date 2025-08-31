import java.util.*;

public class FastSlowPointersExamples {
    
    // 1. Linked List Cycle
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
    
    // 2. Linked List Cycle II
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || fast.next == null) return null;
        
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
    
    // 3. Middle of the Linked List
    public ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    // 4. Happy Number
    public boolean isHappy(int n) {
        int slow = n, fast = n;
        do {
            slow = getNext(slow);
            fast = getNext(getNext(fast));
        } while (slow != fast);
        return slow == 1;
    }
    
    private int getNext(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
    
    // 5. Find the Duplicate Number
    public int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        
        slow = nums[0];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }
    
    // 6. Palindrome Linked List
    public boolean isPalindrome(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode secondHalf = reverse(slow);
        ListNode firstHalf = head;
        
        while (secondHalf != null) {
            if (firstHalf.val != secondHalf.val) return false;
            firstHalf = firstHalf.next;
            secondHalf = secondHalf.next;
        }
        return true;
    }
    
    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
    
    // 7. Remove Nth Node From End of List
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        
        slow.next = slow.next.next;
        return dummy.next;
    }
    
    // 8. Intersection of Two Linked Lists
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = (a == null) ? headB : a.next;
            b = (b == null) ? headA : b.next;
        }
        return a;
    }
    
    // 9. Circular Array Loop
    public boolean circularArrayLoop(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;
            
            int slow = i, fast = i;
            boolean forward = nums[i] > 0;
            
            do {
                slow = getNextIndex(nums, slow, forward);
                fast = getNextIndex(nums, fast, forward);
                if (fast != -1) fast = getNextIndex(nums, fast, forward);
            } while (slow != -1 && fast != -1 && slow != fast);
            
            if (slow != -1 && slow == fast) return true;
        }
        return false;
    }
    
    private int getNextIndex(int[] nums, int i, boolean forward) {
        boolean direction = nums[i] > 0;
        if (direction != forward) return -1;
        
        int next = (i + nums[i]) % nums.length;
        if (next < 0) next += nums.length;
        
        return next == i ? -1 : next;
    }
    
    // 10. Reorder List
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) return;
        
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        ListNode secondHalf = reverse(slow.next);
        slow.next = null;
        
        ListNode first = head, second = secondHalf;
        while (second != null) {
            ListNode temp1 = first.next, temp2 = second.next;
            first.next = second;
            second.next = temp1;
            first = temp1;
            second = temp2;
        }
    }
    
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    public static void main(String[] args) {
        FastSlowPointersExamples examples = new FastSlowPointersExamples();
        
        System.out.println("=== Fast/Slow Pointers Examples ===");
        
        // Test 1: Has Cycle
        ListNode cycle = new ListNode(3);
        cycle.next = new ListNode(2);
        cycle.next.next = new ListNode(0);
        cycle.next.next.next = new ListNode(-4);
        cycle.next.next.next.next = cycle.next;
        System.out.println("Has Cycle: " + examples.hasCycle(cycle));
        
        // Test 2: Detect Cycle
        System.out.println("Cycle Start: " + (examples.detectCycle(cycle) != null ? examples.detectCycle(cycle).val : "null"));
        
        // Test 3: Middle Node
        ListNode list = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        System.out.println("Middle Node: " + examples.middleNode(list).val);
        
        // Test 4: Happy Number
        System.out.println("Is Happy(19): " + examples.isHappy(19));
        
        // Test 5: Find Duplicate
        System.out.println("Find Duplicate([1,3,4,2,2]): " + examples.findDuplicate(new int[]{1,3,4,2,2}));
        
        // Test 6: Palindrome Linked List
        ListNode palindrome = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))));
        System.out.println("Is Palindrome: " + examples.isPalindrome(palindrome));
        
        // Test 7: Remove Nth From End
        ListNode removed = examples.removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
        System.out.println("Remove Nth From End completed");
        
        // Test 8: Intersection
        ListNode common = new ListNode(8, new ListNode(4, new ListNode(5)));
        ListNode listA = new ListNode(4, new ListNode(1, common));
        ListNode listB = new ListNode(5, new ListNode(6, new ListNode(1, common)));
        ListNode intersection = examples.getIntersectionNode(listA, listB);
        System.out.println("Intersection: " + (intersection != null ? intersection.val : "null"));
        
        // Test 9: Circular Array Loop
        System.out.println("Circular Array Loop([2,-1,1,2,2]): " + examples.circularArrayLoop(new int[]{2,-1,1,2,2}));
        
        // Test 10: Reorder List
        ListNode reorder = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
        examples.reorderList(reorder);
        System.out.println("Reorder List completed");
    }
}