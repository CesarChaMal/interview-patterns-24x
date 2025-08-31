# 1. Next Greater Element
def next_greater_element(nums1, nums2):
    stack = []
    next_greater = {}
    
    for num in nums2:
        while stack and stack[-1] < num:
            next_greater[stack.pop()] = num
        stack.append(num)
    
    return [next_greater.get(num, -1) for num in nums1]

# 2. Daily Temperatures
def daily_temperatures(temperatures):
    result = [0] * len(temperatures)
    stack = []
    
    for i, temp in enumerate(temperatures):
        while stack and temperatures[stack[-1]] < temp:
            idx = stack.pop()
            result[idx] = i - idx
        stack.append(i)
    
    return result

# 3. Largest Rectangle Area
def largest_rectangle_area(heights):
    stack = []
    max_area = 0
    
    for i in range(len(heights) + 1):
        h = 0 if i == len(heights) else heights[i]
        while stack and heights[stack[-1]] > h:
            height = heights[stack.pop()]
            width = i if not stack else i - stack[-1] - 1
            max_area = max(max_area, height * width)
        stack.append(i)
    
    return max_area

# 4. Trap
def trap(height):
    stack = []
    water = 0
    
    for i, h in enumerate(height):
        while stack and h > height[stack[-1]]:
            top = stack.pop()
            if not stack:
                break
            distance = i - stack[-1] - 1
            bounded_height = min(h, height[stack[-1]]) - height[top]
            water += distance * bounded_height
        stack.append(i)
    
    return water

# 5. Remove Duplicate Letters
def remove_duplicate_letters(s):
    count = {}
    in_stack = set()
    stack = []
    
    for c in s:
        count[c] = count.get(c, 0) + 1
    
    for c in s:
        count[c] -= 1
        if c in in_stack:
            continue
        
        while stack and stack[-1] > c and count[stack[-1]] > 0:
            in_stack.remove(stack.pop())
        
        stack.append(c)
        in_stack.add(c)
    
    return ''.join(stack)

# 6. Next Greater Elements
def next_greater_elements(nums):
    n = len(nums)
    result = [-1] * n
    stack = []
    
    for i in range(2 * n):
        while stack and nums[stack[-1]] < nums[i % n]:
            result[stack.pop()] = nums[i % n]
        if i < n:
            stack.append(i)
    
    return result

# 7. Is Valid
def is_valid(s):
    stack = []
    mapping = {')': '(', '}': '{', ']': '['}
    
    for c in s:
        if c in mapping:
            if not stack or stack.pop() != mapping[c]:
                return False
        else:
            stack.append(c)
    
    return not stack

# 8. Maximal Rectangle
def maximal_rectangle(matrix):
    if not matrix:
        return 0
    
    heights = [0] * len(matrix[0])
    max_area = 0
    
    for row in matrix:
        for i, val in enumerate(row):
            heights[i] = heights[i] + 1 if val == '1' else 0
        max_area = max(max_area, largest_rectangle_area(heights))
    
    return max_area

# 9. Sum Subarray Mins
def sum_subarray_mins(arr):
    MOD = 10**9 + 7
    stack = []
    result = 0
    
    for i in range(len(arr) + 1):
        while stack and (i == len(arr) or arr[stack[-1]] >= arr[i]):
            mid = stack.pop()
            left = -1 if not stack else stack[-1]
            right = i
            count = (mid - left) * (right - mid)
            result = (result + count * arr[mid]) % MOD
        stack.append(i)
    
    return result

# 10. Min Stack Operations
def min_stack_operations(operations):
    min_stack = MinStack()
    results = []
    
    for op, val in operations:
        if op == "push":
            min_stack.push(val)
            results.append(None)
        elif op == "pop":
            min_stack.pop()
            results.append(None)
        elif op == "top":
            results.append(min_stack.top())
        elif op == "getMin":
            results.append(min_stack.get_min())
    
    return results

class MinStack:
    def __init__(self):
        self.stack = []
        self.min_stack = []
    
    def push(self, val):
        self.stack.append(val)
        if not self.min_stack or val <= self.min_stack[-1]:
            self.min_stack.append(val)
    
    def pop(self):
        if self.stack.pop() == self.min_stack[-1]:
            self.min_stack.pop()
    
    def top(self):
        return self.stack[-1]
    
    def get_min(self):
        return self.min_stack[-1]

def run_examples():
    print("=== Monotonic Stack Examples ===")
    
    # Test 1: Next Greater Element I
    print(f"Next Greater Element I([4,1,2], [1,3,4,2]): {next_greater_element([4,1,2], [1,3,4,2])}")
    
    # Test 2: Daily Temperatures
    print(f"Daily Temperatures([73,74,75,71,69,72,76,73]): {daily_temperatures([73,74,75,71,69,72,76,73])}")
    
    # Test 3: Largest Rectangle in Histogram
    print(f"Largest Rectangle([2,1,5,6,2,3]): {largest_rectangle_area([2,1,5,6,2,3])}")
    
    # Test 4: Trapping Rain Water
    print(f"Trap([0,1,0,2,1,0,1,3,2,1,2,1]): {trap([0,1,0,2,1,0,1,3,2,1,2,1])}")
    
    # Test 5: Remove Duplicate Letters
    print(f"Remove Duplicate Letters('bcabc'): {remove_duplicate_letters('bcabc')}")
    
    # Test 6: Next Greater Elements II
    print(f"Next Greater Elements II([1,2,1]): {next_greater_elements([1,2,1])}")
    
    # Test 7: Valid Parentheses
    print(f"Valid Parentheses('()[]{{}}'): {is_valid('()[]{}')}")
    
    # Test 8: Maximal Rectangle
    matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']]
    print(f"Maximal Rectangle: {maximal_rectangle(matrix)}")
    
    # Test 9: Sum of Subarray Minimums
    print(f"Sum Subarray Mins([3,1,2,4]): {sum_subarray_mins([3,1,2,4])}")
    
    # Test 10: Min Stack
    min_stack = MinStack()
    min_stack.push(-2)
    min_stack.push(0)
    min_stack.push(-3)
    print(f"Min Stack getMin(): {min_stack.get_min()}")
    min_stack.pop()
    print(f"Min Stack top(): {min_stack.top()}")
    print(f"Min Stack getMin(): {min_stack.get_min()}")

if __name__ == "__main__":
    run_examples()