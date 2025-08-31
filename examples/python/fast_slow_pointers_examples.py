class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# 1. Has Cycle
def has_cycle(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            return True
    return False

# 2. Detect Cycle
def detect_cycle(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
        if slow == fast:
            break
    else:
        return None
    
    slow = head
    while slow != fast:
        slow = slow.next
        fast = fast.next
    return slow

# 3. Middle Node
def middle_node(head):
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
    return slow

# 4. Is Happy
def is_happy(n):
    def get_next(num):
        total = 0
        while num > 0:
            digit = num % 10
            total += digit * digit
            num //= 10
        return total
    
    slow = fast = n
    while True:
        slow = get_next(slow)
        fast = get_next(get_next(fast))
        if slow == fast:
            break
    return slow == 1

# 5. Find Duplicate
def find_duplicate(nums):
    slow = fast = nums[0]
    while True:
        slow = nums[slow]
        fast = nums[nums[fast]]
        if slow == fast:
            break
    
    slow = nums[0]
    while slow != fast:
        slow = nums[slow]
        fast = nums[fast]
    return slow

# 6. Is Palindrome
def is_palindrome(head):
    def reverse_list(node):
        prev = None
        while node:
            next_node = node.next
            node.next = prev
            prev = node
            node = next_node
        return prev
    
    slow = fast = head
    while fast and fast.next:
        slow = slow.next
        fast = fast.next.next
    
    second_half = reverse_list(slow)
    first_half = head
    
    while second_half:
        if first_half.val != second_half.val:
            return False
        first_half = first_half.next
        second_half = second_half.next
    return True

# 7. Remove Nth From End
def remove_nth_from_end(head, n):
    dummy = ListNode(0)
    dummy.next = head
    slow = fast = dummy
    
    for _ in range(n + 1):
        fast = fast.next
    
    while fast:
        slow = slow.next
        fast = fast.next
    
    slow.next = slow.next.next
    return dummy.next

# 8. Get Intersection Node
def get_intersection_node(head_a, head_b):
    a, b = head_a, head_b
    while a != b:
        a = head_b if a is None else a.next
        b = head_a if b is None else b.next
    return a

# 9. Circular Array Loop
def circular_array_loop(nums):
    def get_next_index(i, forward):
        direction = nums[i] > 0
        if direction != forward:
            return -1
        
        next_i = (i + nums[i]) % len(nums)
        return -1 if next_i == i else next_i
    
    for i in range(len(nums)):
        if nums[i] == 0:
            continue
        
        slow = fast = i
        forward = nums[i] > 0
        
        while True:
            slow = get_next_index(slow, forward)
            fast = get_next_index(fast, forward)
            if fast != -1:
                fast = get_next_index(fast, forward)
            
            if slow == -1 or fast == -1 or slow == fast:
                break
        
        if slow != -1 and slow == fast:
            return True
    
    return False

# 10. Reorder List
def reorder_list(head):
    if not head or not head.next:
        return
    
    def reverse_list(node):
        prev = None
        while node:
            next_node = node.next
            node.next = prev
            prev = node
            node = next_node
        return prev
    
    slow = fast = head
    while fast.next and fast.next.next:
        slow = slow.next
        fast = fast.next.next
    
    second_half = reverse_list(slow.next)
    slow.next = None
    
    first, second = head, second_half
    while second:
        temp1, temp2 = first.next, second.next
        first.next = second
        second.next = temp1
        first, second = temp1, temp2

def run_examples():
    print("=== Fast/Slow Pointers Examples ===")
    
    # Test 1: Has Cycle
    cycle = ListNode(3)
    cycle.next = ListNode(2)
    cycle.next.next = ListNode(0)
    cycle.next.next.next = ListNode(-4)
    cycle.next.next.next.next = cycle.next
    print(f"Has Cycle: {has_cycle(cycle)}")
    
    # Test 2: Detect Cycle
    cycle_start = detect_cycle(cycle)
    print(f"Cycle Start: {cycle_start.val if cycle_start else None}")
    
    # Test 3: Middle Node
    list_node = ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5)))))
    print(f"Middle Node: {middle_node(list_node).val}")
    
    # Test 4: Happy Number
    print(f"Is Happy(19): {is_happy(19)}")
    
    # Test 5: Find Duplicate
    print(f"Find Duplicate([1,3,4,2,2]): {find_duplicate([1,3,4,2,2])}")
    
    # Test 6: Palindrome Linked List
    palindrome = ListNode(1, ListNode(2, ListNode(2, ListNode(1))))
    print(f"Is Palindrome: {is_palindrome(palindrome)}")
    
    # Test 7: Remove Nth From End
    removed = remove_nth_from_end(ListNode(1, ListNode(2, ListNode(3, ListNode(4, ListNode(5))))), 2)
    print("Remove Nth From End completed")
    
    # Test 8: Intersection
    common = ListNode(8, ListNode(4, ListNode(5)))
    list_a = ListNode(4, ListNode(1, common))
    list_b = ListNode(5, ListNode(6, ListNode(1, common)))
    intersection = get_intersection_node(list_a, list_b)
    print(f"Intersection: {intersection.val if intersection else None}")
    
    # Test 9: Circular Array Loop
    print(f"Circular Array Loop([2,-1,1,2,2]): {circular_array_loop([2,-1,1,2,2])}")
    
    # Test 10: Reorder List
    reorder = ListNode(1, ListNode(2, ListNode(3, ListNode(4))))
    reorder_list(reorder)
    print("Reorder List completed")

if __name__ == "__main__":
    run_examples()