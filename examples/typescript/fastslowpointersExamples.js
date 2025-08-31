"use strict";
class ListNode {
    constructor(val, next) {
        this.val = val === undefined ? 0 : val;
        this.next = next === undefined ? null : next;
    }
}
// 1. Linked List Cycle
function hasCycle(head) {
    let slow = head, fast = head;
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow === fast)
            return true;
    }
    return false;
}
// 2. Linked List Cycle II
function detectCycle(head) {
    let slow = head, fast = head;
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow === fast)
            break;
    }
    if (!fast || !fast.next)
        return null;
    slow = head;
    while (slow !== fast) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
}
// 3. Middle of the Linked List
function middleNode(head) {
    let slow = head, fast = head;
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
// 4. Happy Number
function isHappy(n) {
    function getNext(num) {
        let sum = 0;
        while (num > 0) {
            const digit = num % 10;
            sum += digit * digit;
            num = Math.floor(num / 10);
        }
        return sum;
    }
    let slow = n, fast = n;
    do {
        slow = getNext(slow);
        fast = getNext(getNext(fast));
    } while (slow !== fast);
    return slow === 1;
}
// 5. Find the Duplicate Number
function findDuplicate(nums) {
    let slow = nums[0], fast = nums[0];
    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow !== fast);
    slow = nums[0];
    while (slow !== fast) {
        slow = nums[slow];
        fast = nums[fast];
    }
    return slow;
}
// 6. Palindrome Linked List
function isPalindrome(head) {
    function reverse(node) {
        let prev = null;
        while (node) {
            const next = node.next;
            node.next = prev;
            prev = node;
            node = next;
        }
        return prev;
    }
    let slow = head, fast = head;
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
    }
    let secondHalf = reverse(slow);
    let firstHalf = head;
    while (secondHalf) {
        if (firstHalf.val !== secondHalf.val)
            return false;
        firstHalf = firstHalf.next;
        secondHalf = secondHalf.next;
    }
    return true;
}
// 7. Remove Nth Node From End of List
function removeNthFromEnd(head, n) {
    const dummy = new ListNode(0);
    dummy.next = head;
    let slow = dummy, fast = dummy;
    for (let i = 0; i <= n; i++) {
        fast = fast.next;
    }
    while (fast) {
        slow = slow.next;
        fast = fast.next;
    }
    slow.next = slow.next.next;
    return dummy.next;
}
// 8. Intersection of Two Linked Lists
function getIntersectionNode(headA, headB) {
    let a = headA, b = headB;
    while (a !== b) {
        a = a ? a.next : headB;
        b = b ? b.next : headA;
    }
    return a;
}
// 9. Circular Array Loop
function circularArrayLoop(nums) {
    function getNextIndex(i, forward) {
        const direction = nums[i] > 0;
        if (direction !== forward)
            return -1;
        let next = (i + nums[i]) % nums.length;
        if (next < 0)
            next += nums.length;
        return next === i ? -1 : next;
    }
    for (let i = 0; i < nums.length; i++) {
        if (nums[i] === 0)
            continue;
        let slow = i, fast = i;
        const forward = nums[i] > 0;
        do {
            slow = getNextIndex(slow, forward);
            fast = getNextIndex(fast, forward);
            if (fast !== -1)
                fast = getNextIndex(fast, forward);
        } while (slow !== -1 && fast !== -1 && slow !== fast);
        if (slow !== -1 && slow === fast)
            return true;
    }
    return false;
}
// 10. Reorder List
function reorderList(head) {
    if (!head || !head.next)
        return;
    // Find middle
    let slow = head, fast = head;
    while (fast.next && fast.next.next) {
        slow = slow.next;
        fast = fast.next.next;
    }
    // Reverse second half
    let prev = null, curr = slow.next;
    slow.next = null;
    while (curr) {
        const next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    // Merge two halves
    let first = head, second = prev;
    while (second) {
        const temp1 = first.next;
        const temp2 = second.next;
        first.next = second;
        second.next = temp1;
        first = temp1;
        second = temp2;
    }
}
// Test cases
console.log("=== Fast/Slow Pointers Examples ===");
// Test 1: Has Cycle
const cycle = new ListNode(3);
cycle.next = new ListNode(2);
cycle.next.next = new ListNode(0);
cycle.next.next.next = new ListNode(-4);
cycle.next.next.next.next = cycle.next;
console.log("Has Cycle:", hasCycle(cycle));
// Test 2: Detect Cycle
const cycleStart = detectCycle(cycle);
console.log("Cycle Start:", cycleStart ? cycleStart.val : null);
// Test 3: Middle Node
const list = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
console.log("Middle Node:", middleNode(list)?.val);
// Test 4: Happy Number
console.log("Is Happy(19):", isHappy(19));
// Test 5: Find Duplicate
console.log("Find Duplicate([1,3,4,2,2]):", findDuplicate([1, 3, 4, 2, 2]));
// Test 6: Palindrome Linked List
const palindrome = new ListNode(1, new ListNode(2, new ListNode(2, new ListNode(1))));
console.log("Is Palindrome:", isPalindrome(palindrome));
// Test 7: Remove Nth From End
const removed = removeNthFromEnd(new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5))))), 2);
console.log("Remove Nth From End completed");
// Test 8: Intersection
const common = new ListNode(8, new ListNode(4, new ListNode(5)));
const listA = new ListNode(4, new ListNode(1, common));
const listB = new ListNode(5, new ListNode(6, new ListNode(1, common)));
const intersection = getIntersectionNode(listA, listB);
console.log("Intersection:", intersection ? intersection.val : null);
// Test 9: Circular Array Loop
console.log("Circular Array Loop([2,-1,1,2,2]):", circularArrayLoop([2, -1, 1, 2, 2]));
// Test 10: Reorder List
const reorder = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
reorderList(reorder);
console.log("Reorder List completed");
//# sourceMappingURL=fastslowpointersExamples.js.map