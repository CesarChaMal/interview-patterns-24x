from collections import deque

# 1. Max Sliding Window
def max_sliding_window(nums, k):
    dq = deque()
    result = []
    
    for i in range(len(nums)):
        while dq and dq[0] < i - k + 1:
            dq.popleft()
        while dq and nums[dq[-1]] < nums[i]:
            dq.pop()
        dq.append(i)
        if i >= k - 1:
            result.append(nums[dq[0]])
    return result

# 2. Min Sliding Window
def min_sliding_window(nums, k):
    dq = deque()
    result = []
    
    for i in range(len(nums)):
        while dq and dq[0] < i - k + 1:
            dq.popleft()
        while dq and nums[dq[-1]] > nums[i]:
            dq.pop()
        dq.append(i)
        if i >= k - 1:
            result.append(nums[dq[0]])
    return result

# 3. Constrained Subset Sum
def constrained_subset_sum(nums, k):
    dq = deque()
    dp = [0] * len(nums)
    
    for i in range(len(nums)):
        while dq and dq[0] < i - k:
            dq.popleft()
        dp[i] = nums[i] + (max(0, dp[dq[0]]) if dq else 0)
        while dq and dp[dq[-1]] <= dp[i]:
            dq.pop()
        dq.append(i)
    return max(dp)

# 4. Shortest Subarray
def shortest_subarray(nums, k):
    prefix_sum = [0]
    for num in nums:
        prefix_sum.append(prefix_sum[-1] + num)
    
    dq = deque()
    min_len = float('inf')
    
    for i in range(len(prefix_sum)):
        while dq and prefix_sum[i] - prefix_sum[dq[0]] >= k:
            min_len = min(min_len, i - dq.popleft())
        while dq and prefix_sum[dq[-1]] >= prefix_sum[i]:
            dq.pop()
        dq.append(i)
    
    return min_len if min_len != float('inf') else -1

# 5. Max Result
def max_result(nums, k):
    dq = deque()
    dp = [0] * len(nums)
    dp[0] = nums[0]
    dq.append(0)
    
    for i in range(1, len(nums)):
        while dq and dq[0] < i - k:
            dq.popleft()
        dp[i] = nums[i] + dp[dq[0]]
        while dq and dp[dq[-1]] <= dp[i]:
            dq.pop()
        dq.append(i)
    
    return dp[-1]

# 6. Largest Rectangle Area
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

# 7. Maximal Rectangle
def maximal_rectangle(matrix):
    if not matrix:
        return 0
    heights = [0] * len(matrix[0])
    max_area = 0
    
    for row in matrix:
        for i in range(len(row)):
            heights[i] = heights[i] + 1 if row[i] == '1' else 0
        max_area = max(max_area, largest_rectangle_area(heights))
    
    return max_area

# 8. Remove K Digits
def remove_k_digits(num, k):
    stack = []
    
    for digit in num:
        while stack and k > 0 and stack[-1] > digit:
            stack.pop()
            k -= 1
        stack.append(digit)
    
    while k > 0:
        stack.pop()
        k -= 1
    
    result = ''.join(stack).lstrip('0')
    return result if result else '0'

# 9. Max Number
def max_number(nums1, nums2, k):
    def max_array(nums, k):
        result = []
        drop = len(nums) - k
        for num in nums:
            while result and result[-1] < num and drop > 0:
                result.pop()
                drop -= 1
            if len(result) < k:
                result.append(num)
            else:
                drop -= 1
        return result
    
    def merge(nums1, nums2):
        result = []
        i = j = 0
        while i < len(nums1) or j < len(nums2):
            if nums1[i:] > nums2[j:]:
                result.append(nums1[i])
                i += 1
            else:
                result.append(nums2[j])
                j += 1
        return result
    
    result = []
    for i in range(max(0, k - len(nums2)), min(k, len(nums1)) + 1):
        candidate = merge(max_array(nums1, i), max_array(nums2, k - i))
        if candidate > result:
            result = candidate
    return result

# 10. Min Window
def min_window(s, t):
    if not s or not t:
        return ""
    
    def find_subsequence(start):
        i = start
        for char in t:
            while i < len(s) and s[i] != char:
                i += 1
            if i == len(s):
                return -1
            i += 1
        return i - 1
    
    min_len = float('inf')
    result = ""
    
    for i in range(len(s)):
        if s[i] == t[0]:
            end = find_subsequence(i)
            if end != -1 and end - i + 1 < min_len:
                min_len = end - i + 1
                result = s[i:end + 1]
    
    return result

if __name__ == "__main__":
    print("=== Monotonic Deque Examples ===")
    
    # Test 1: Sliding Window Maximum
    print(f"Max Sliding Window([1,3,-1,-3,5,3,6,7], 3): {max_sliding_window([1,3,-1,-3,5,3,6,7], 3)}")
    
    # Test 2: Sliding Window Minimum
    print(f"Min Sliding Window([1,3,-1,-3,5,3,6,7], 3): {min_sliding_window([1,3,-1,-3,5,3,6,7], 3)}")
    
    # Test 3: Constrained Subsequence Sum
    print(f"Constrained Subset Sum([10,2,-10,5,20], 2): {constrained_subset_sum([10,2,-10,5,20], 2)}")
    
    # Test 4: Shortest Subarray
    print(f"Shortest Subarray([1], 1): {shortest_subarray([1], 1)}")
    
    # Test 5: Jump Game VI
    print(f"Max Result([1,-1,-2,4,-7,3], 2): {max_result([1,-1,-2,4,-7,3], 2)}")
    
    # Test 6: Largest Rectangle
    print(f"Largest Rectangle([2,1,5,6,2,3]): {largest_rectangle_area([2,1,5,6,2,3])}")
    
    # Test 7: Maximal Rectangle
    matrix = [['1','0','1','0','0'],['1','0','1','1','1'],['1','1','1','1','1'],['1','0','0','1','0']]
    print(f"Maximal Rectangle: {maximal_rectangle(matrix)}")
    
    # Test 8: Remove K Digits
    print(f"Remove K Digits('1432219', 3): {remove_k_digits('1432219', 3)}")
    
    # Test 9: Create Maximum Number
    print(f"Max Number([3,4,6,5], [9,1,2,5,8,3], 5): {max_number([3,4,6,5], [9,1,2,5,8,3], 5)}")
    
    # Test 10: Minimum Window Subsequence
    print(f"Min Window('abcdebdde', 'bde'): {min_window('abcdebdde', 'bde')}")