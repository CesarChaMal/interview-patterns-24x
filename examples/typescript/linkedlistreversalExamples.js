"use strict";
class ListNode {
    constructor(val, next) {
        this.val = val === undefined ? 0 : val;
        this.next = next === undefined ? null : next;
    }
}
// 1. Reverse Linked List
function reverseList(head) {
    let prev = null;
    while (head) {
        const next = head.next;
        head.next = prev;
        prev = head;
        head = next;
    }
    return prev;
}
// 2. Reverse Linked List II
function reverseBetween(head, left, right) {
    const dummy = new ListNode(0);
    dummy.next = head;
    let prev = dummy;
    for (let i = 0; i < left - 1; i++) {
        prev = prev.next;
    }
    const curr = prev.next;
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
        while (count-- > 0) {
            const next = head.next;
            head.next = curr;
            curr = head;
            head = next;
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
    if (!head || !head.next || k === 0)
        return head;
    let tail = head;
    let length = 1;
    while (tail.next) {
        tail = tail.next;
        length++;
    }
    k = k % length;
    if (k === 0)
        return head;
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
    if (!head)
        return null;
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
    const before = new ListNode(0);
    const after = new ListNode(0);
    let beforeCurr = before;
    let afterCurr = after;
    while (head) {
        if (head.val < x) {
            beforeCurr.next = head;
            beforeCurr = beforeCurr.next;
        }
        else {
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
function reverseEvenLengthGroups(head) {
    let prev = head;
    let groupLen = 2;
    while (prev.next) {
        let node = prev;
        let nodeCount = 0;
        for (let i = 0; i < groupLen && node.next; i++) {
            node = node.next;
            nodeCount++;
        }
        if (nodeCount % 2 === 0) {
            const reverse = reverseGroup(prev.next, nodeCount);
            const temp = prev.next;
            prev.next = reverse;
            prev = temp;
        }
        else {
            prev = node;
        }
        groupLen++;
    }
    return head;
}
function reverseGroup(head, k) {
    let prev = null;
    let curr = head;
    while (k-- > 0 && curr) {
        const next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    head.next = curr;
    return prev;
}
// 9. Add Two Numbers
function addTwoNumbers(l1, l2) {
    const dummy = new ListNode(0);
    let curr = dummy;
    let carry = 0;
    while (l1 || l2 || carry) {
        let sum = carry;
        if (l1) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2) {
            sum += l2.val;
            l2 = l2.next;
        }
        carry = Math.floor(sum / 10);
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
    }
    return dummy.next;
}
// 10. Remove Duplicates from Sorted List II
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
        }
        else {
            prev = prev.next;
        }
        head = head.next;
    }
    return dummy.next;
}
// Test cases
console.log("=== Linked List Reversal Examples ===");
// Test 1: Reverse Linked List
const list1 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
const reversed = reverseList(list1);
console.log("Reverse List completed");
// Test 2: Reverse Between
const list2 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
const reversedBetween = reverseBetween(list2, 2, 4);
console.log("Reverse Between completed");
// Test 3: Reverse K Group
const list3 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
const reversedK = reverseKGroup(list3, 2);
console.log("Reverse K Group completed");
// Test 4: Swap Pairs
const list4 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4))));
const swapped = swapPairs(list4);
console.log("Swap Pairs completed");
// Test 5: Rotate Right
const list5 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
const rotated = rotateRight(list5, 2);
console.log("Rotate Right completed");
// Test 6: Odd Even List
const list6 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
const oddEven = oddEvenList(list6);
console.log("Odd Even List completed");
// Test 7: Partition List
const list7 = new ListNode(1, new ListNode(4, new ListNode(3, new ListNode(2, new ListNode(5, new ListNode(2))))));
const partitioned = partition(list7, 3);
console.log("Partition List completed");
// Test 9: Add Two Numbers
const l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
const l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
const sum = addTwoNumbers(l1, l2);
console.log("Add Two Numbers completed");
// Test 10: Delete Duplicates
const list10 = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3, new ListNode(4, new ListNode(4, new ListNode(5)))))));
const noDuplicates = deleteDuplicates(list10);
console.log("Delete Duplicates completed");
//# sourceMappingURL=linkedlistreversalExamples.js.map