class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# 1. Reverse List
def reverse_list(head):
    prev = None
    while head:
        next_node = head.next
        head.next = prev
        prev = head
        head = next_node
    return prev

# 2. Reverse Between
def reverse_between(head, left, right):
    dummy = ListNode(0)
    dummy.next = head
    prev = dummy
    
    for _ in range(left - 1):
        prev = prev.next
    
    curr = prev.next
    for _ in range(right - left):
        next_node = curr.next
        curr.next = next_node.next
        next_node.next = prev.next
        prev.next = next_node
    
    return dummy.next

# 3. Reverse K Group
def reverse_k_group(head, k):
    curr = head
    count = 0
    while curr and count < k:
        curr = curr.next
        count += 1
    
    if count == k:
        curr = reverse_k_group(curr, k)
        while count > 0:
            next_node = head.next
            head.next = curr
            curr = head
            head = next_node
            count -= 1
        head = curr
    
    return head

# 4. Swap Pairs
def swap_pairs(head):
    dummy = ListNode(0)
    dummy.next = head
    prev = dummy
    
    while prev.next and prev.next.next:
        first = prev.next
        second = prev.next.next
        
        prev.next = second
        first.next = second.next
        second.next = first
        prev = first
    
    return dummy.next

# 5. Rotate Right
def rotate_right(head, k):
    if not head or not head.next or k == 0:
        return head
    
    tail = head
    length = 1
    while tail.next:
        tail = tail.next
        length += 1
    
    k = k % length
    if k == 0:
        return head
    
    new_tail = head
    for _ in range(length - k - 1):
        new_tail = new_tail.next
    
    new_head = new_tail.next
    new_tail.next = None
    tail.next = head
    
    return new_head

# 6. Odd Even List
def odd_even_list(head):
    if not head:
        return None
    
    odd = head
    even = head.next
    even_head = even
    
    while even and even.next:
        odd.next = even.next
        odd = odd.next
        even.next = odd.next
        even = even.next
    
    odd.next = even_head
    return head

# 7. Partition
def partition(head, x):
    before = ListNode(0)
    after = ListNode(0)
    before_curr = before
    after_curr = after
    
    while head:
        if head.val < x:
            before_curr.next = head
            before_curr = before_curr.next
        else:
            after_curr.next = head
            after_curr = after_curr.next
        head = head.next
    
    after_curr.next = None
    before_curr.next = after.next
    return before.next

# 8. Reverse Even Length Groups
def reverse_even_length_groups(head):
    prev = head
    group_len = 2
    
    while prev.next:
        node = prev
        node_count = 0
        
        for _ in range(group_len):
            if node.next:
                node = node.next
                node_count += 1
            else:
                break
        
        if node_count % 2 == 0:
            reverse_node = reverse_group(prev.next, node_count)
            temp = prev.next
            prev.next = reverse_node
            prev = temp
        else:
            prev = node
        
        group_len += 1
    
    return head

# 9. Reverse Group
def reverse_group(head, k):
    prev = None
    curr = head
    
    while k > 0 and curr:
        next_node = curr.next
        curr.next = prev
        prev = curr
        curr = next_node
        k -= 1
    
    head.next = curr
    return prev

# 10. Add Two Numbers
def add_two_numbers(l1, l2):
    dummy = ListNode(0)
    curr = dummy
    carry = 0
    
    while l1 or l2 or carry:
        total = carry
        if l1:
            total += l1.val
            l1 = l1.next
        if l2:
            total += l2.val
            l2 = l2.next
        
        carry = total // 10
        curr.next = ListNode(total % 10)
        curr = curr.next
    
    return dummy.next

# 11. Delete Duplicates
def delete_duplicates(head):
    dummy = ListNode(0)
    dummy.next = head
    prev = dummy
    
    while head:
        if head.next and head.val == head.next.val:
            while head.next and head.val == head.next.val:
                head = head.next
            prev.next = head.next
        else:
            prev = prev.next
        head = head.next
    
    return dummy.next

def run_examples():
    print("=== Linked List Reversal Examples ===")
    
    # Test 1: Reverse Linked List
    list1 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    reversed_list = reverse_list(list1)
    print("Reverse List completed")
    
    # Test 2: Reverse Between
    list2 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    reversed_between = reverse_between(list2, 2, 4)
    print("Reverse Between completed")
    
    # Test 3: Reverse K Group
    list3 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    reversed_k = reverse_k_group(list3, 2)
    print("Reverse K Group completed")
    
    # Test 4: Swap Pairs
    list4 = ListNode(1, ListNode(2, ListNode(3, ListNode(4))))
    swapped = swap_pairs(list4)
    print("Swap Pairs completed")
    
    # Test 5: Rotate Right
    list5 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    rotated = rotate_right(list5, 2)
    print("Rotate Right completed")
    
    # Test 6: Odd Even List
    list6 = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    odd_even = odd_even_list(list6)
    print("Odd Even List completed")
    
    # Test 7: Partition List
    list7 = ListNode(1, ListNode(4, ListNode(3, ListNode(2, ListNode(5, ListNode(2))))))
    partitioned = partition(list7, 3)
    print("Partition List completed")
    
    # Test 9: Add Two Numbers
    l1 = ListNode(2, ListNode(4, ListNode(3)))
    l2 = ListNode(5, ListNode(6, ListNode(4)))
    sum_result = add_two_numbers(l1, l2)
    print("Add Two Numbers completed")
    
    # Test 10: Delete Duplicates
    list10 = ListNode(1, ListNode(2, ListNode(3, ListNode(3, ListNode(4, ListNode(4, ListNode(5)))))))
    no_duplicates = delete_duplicates(list10)
    print("Delete Duplicates completed")

if __name__ == "__main__":
    run_examples()