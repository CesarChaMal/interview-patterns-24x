// ListNode class
class ListNode {
    constructor(val, next) {
        this.val = val === undefined ? 0 : val;
        this.next = next === undefined ? null : next;
    }
}

// 1. Reverse Linked List
function reverseList(head) {
    let prev = null, curr = head;
    
    while (curr) {
        const next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}

// 2. Reverse Linked List II
function reverseBetween(head, left, right) {
    if (!head || left === right) return head;
    
    const dummy = new ListNode(0);
    dummy.next = head;
    let prev = dummy;
    
    for (let i = 1; i < left; i++) {
        prev = prev.next;
    }
    
    let curr = prev.next;
    for (let i = 0; i < right - left; i++) {
        const next = curr.next;
        curr.next = next.next;
        next.next = prev.next;
        prev.next = next;
    }
    
    return dummy.next;
}

// 3. Reverse Nodes in k-Group
function reverseKGroup(head, k) {
    let curr = head;
    let count = 0;
    
    while (curr && count < k) {
        curr = curr.next;
        count++;
    }
    
    if (count === k) {
        curr = reverseKGroup(curr, k);
        
        while (count > 0) {
            const next = head.next;
            head.next = curr;
            curr = head;
            head = next;
            count--;
        }
        head = curr;
    }
    return head;
}

// 4. Swap Nodes in Pairs
function swapPairs(head) {
    const dummy = new ListNode(0);
    dummy.next = head;
    let prev = dummy;
    
    while (prev.next && prev.next.next) {
        const first = prev.next;
        const second = prev.next.next;
        
        prev.next = second;
        first.next = second.next;
        second.next = first;
        prev = first;
    }
    
    return dummy.next;
}

// 5. Rotate List
function rotateRight(head, k) {
    if (!head || !head.next || k === 0) return head;
    
    let length = 1;
    let tail = head;
    while (tail.next) {
        tail = tail.next;
        length++;
    }
    
    k = k % length;
    if (k === 0) return head;
    
    let newTail = head;
    for (let i = 0; i < length - k - 1; i++) {
        newTail = newTail.next;
    }
    
    const newHead = newTail.next;
    newTail.next = null;
    tail.next = head;
    
    return newHead;
}

// 6. Odd Even Linked List
function oddEvenList(head) {
    if (!head || !head.next) return head;
    
    let odd = head;
    let even = head.next;
    const evenHead = even;
    
    while (even && even.next) {
        odd.next = even.next;
        odd = odd.next;
        even.next = odd.next;
        even = even.next;
    }
    
    odd.next = evenHead;
    return head;
}

// 7. Partition List
function partition(head, x) {
    const beforeHead = new ListNode(0);
    const afterHead = new ListNode(0);
    let before = beforeHead;
    let after = afterHead;
    
    while (head) {
        if (head.val < x) {
            before.next = head;
            before = before.next;
        } else {
            after.next = head;
            after = after.next;
        }
        head = head.next;
    }
    
    after.next = null;
    before.next = afterHead.next;
    
    return beforeHead.next;
}

// 8. Remove Duplicates from Sorted List II
function deleteDuplicates(head) {
    const dummy = new ListNode(0);
    dummy.next = head;
    let prev = dummy;
    
    while (head) {
        if (head.next && head.val === head.next.val) {
            while (head.next && head.val === head.next.val) {
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

// 9. Add Two Numbers
function addTwoNumbers(l1, l2) {
    const dummy = new ListNode(0);
    let curr = dummy;
    let carry = 0;
    
    while (l1 || l2 || carry) {
        const sum = (l1 ? l1.val : 0) + (l2 ? l2.val : 0) + carry;
        carry = Math.floor(sum / 10);
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
        
        if (l1) l1 = l1.next;
        if (l2) l2 = l2.next;
    }
    
    return dummy.next;
}

// 10. Merge Two Sorted Lists
function mergeTwoLists(list1, list2) {
    const dummy = new ListNode(0);
    let curr = dummy;
    
    while (list1 && list2) {
        if (list1.val <= list2.val) {
            curr.next = list1;
            list1 = list1.next;
        } else {
            curr.next = list2;
            list2 = list2.next;
        }
        curr = curr.next;
    }
    
    curr.next = list1 || list2;
    return dummy.next;
}

// Helper function to print list
function printList(head) {
    const values = [];
    while (head) {
        values.push(head.val);
        head = head.next;
    }
    return values;
}

// Test cases
console.log("=== Linked List Reversal Examples ===");

// Create test list: 1->2->3->4->5
const head = new ListNode(1);
head.next = new ListNode(2);
head.next.next = new ListNode(3);
head.next.next.next = new ListNode(4);
head.next.next.next.next = new ListNode(5);

console.log("Original list:", printList(head));

const reversed = reverseList(head);
console.log("Reversed list:", printList(reversed));

// Create new test list for other operations
const head2 = new ListNode(1);
head2.next = new ListNode(2);
head2.next.next = new ListNode(3);
head2.next.next.next = new ListNode(4);
head2.next.next.next.next = new ListNode(5);

const swapped = swapPairs(head2);
console.log("Swap Pairs:", printList(swapped));

// Test add two numbers
const l1 = new ListNode(2);
l1.next = new ListNode(4);
l1.next.next = new ListNode(3);

const l2 = new ListNode(5);
l2.next = new ListNode(6);
l2.next.next = new ListNode(4);

const sum = addTwoNumbers(l1, l2);
console.log("Add Two Numbers (342 + 465):", printList(sum));

// Export functions
if (typeof module !== 'undefined' && module.exports) {
    module.exports = {
        ListNode,
        reverseList,
        reverseBetween,
        reverseKGroup,
        swapPairs,
        rotateRight,
        oddEvenList,
        partition,
        deleteDuplicates,
        addTwoNumbers,
        mergeTwoLists
    };
}