// ListNode class
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
        if (slow === fast) return true;
    }
    return false;
}

// 2. Linked List Cycle II
function detectCycle(head) {
    let slow = head, fast = head;
    
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow === fast) break;
    }
    
    if (!fast || !fast.next) return null;
    
    slow = head;
    while (slow !== fast) {
        slow = slow.next;
        fast = fast.next;
    }
    return slow;
}

// 3. Find the Duplicate Number
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

// 4. Happy Number
function isHappy(n) {
    const getSumOfSquares = (num) => {
        let sum = 0;
        while (num > 0) {
            const digit = num % 10;
            sum += digit * digit;
            num = Math.floor(num / 10);
        }
        return sum;
    };
    
    let slow = n, fast = n;
    do {
        slow = getSumOfSquares(slow);
        fast = getSumOfSquares(getSumOfSquares(fast));
    } while (slow !== fast);
    
    return slow === 1;
}

// 5. Middle of the Linked List
function middleNode(head) {
    let slow = head, fast = head;
    
    while (fast && fast.next) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}

// 6. Palindrome Linked List
function isPalindrome(head) {
    if (!head || !head.next) return true;
    
    // Find middle
    let slow = head, fast = head;
    while (fast.next && fast.next.next) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Reverse second half
    let prev = null, curr = slow.next;
    while (curr) {
        const next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    
    // Compare
    let left = head, right = prev;
    while (right) {
        if (left.val !== right.val) return false;
        left = left.next;
        right = right.next;
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
    if (!headA || !headB) return null;
    
    let pA = headA, pB = headB;
    
    while (pA !== pB) {
        pA = pA ? pA.next : headB;
        pB = pB ? pB.next : headA;
    }
    return pA;
}

// 9. Reorder List
function reorderList(head) {
    if (!head || !head.next) return;
    
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
    
    // Merge
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

// 10. Circular Array Loop
function circularArrayLoop(nums) {
    const n = nums.length;
    
    for (let i = 0; i < n; i++) {
        if (nums[i] === 0) continue;
        
        let slow = i, fast = i;
        const direction = nums[i] > 0;
        
        while (true) {
            slow = getNext(nums, slow, direction);
            if (slow === -1) break;
            
            fast = getNext(nums, fast, direction);
            if (fast === -1) break;
            fast = getNext(nums, fast, direction);
            if (fast === -1) break;
            
            if (slow === fast) return true;
        }
        
        // Mark visited
        let curr = i;
        while (nums[curr] !== 0 && (nums[curr] > 0) === direction) {
            const next = getNext(nums, curr, direction);
            nums[curr] = 0;
            curr = next;
        }
    }
    return false;
}

function getNext(nums, i, direction) {
    const n = nums.length;
    const nextIndex = ((i + nums[i]) % n + n) % n;
    
    if (nextIndex === i || (nums[nextIndex] > 0) !== direction) {
        return -1;
    }
    return nextIndex;
}

// Test cases
console.log("=== Fast/Slow Pointers Examples ===");

// Create test linked list: 1->2->3->4->5
const head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
head.next.next.next = new ListNode(4);
head.next.next.next.next = new ListNode(5);

console.log("Has Cycle (no cycle):", hasCycle(head));
console.log("Find Duplicate([1,3,4,2,2]):", findDuplicate([1,3,4,2,2]));
console.log("Is Happy(19):", isHappy(19));
console.log("Middle Node val:", middleNode(head).val);
console.log("Is Palindrome:", isPalindrome(head));

// Create cycle for testing
const cycleHead = new ListNode(3);
cycleHead.next = new ListNode(2);
cycleHead.next.next = new ListNode(0);
cycleHead.next.next.next = new ListNode(-4);
cycleHead.next.next.next.next = cycleHead.next; // Creates cycle

console.log("Has Cycle (with cycle):", hasCycle(cycleHead));
console.log("Detect Cycle val:", detectCycle(cycleHead).val);

console.log("Circular Array Loop([2,-1,1,2,2]):", circularArrayLoop([2,-1,1,2,2]));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        ListNode,
        hasCycle,
        detectCycle,
        findDuplicate,
        isHappy,
        middleNode,
        isPalindrome,
        removeNthFromEnd,
        getIntersectionNode,
        reorderList,
        circularArrayLoop
    };
}