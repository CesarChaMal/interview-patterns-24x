from collections import defaultdict

# 1. Subarray Sum
def subarray_sum(nums, k):
    count = 0
    prefix_sum = 0
    sum_count = defaultdict(int)
    sum_count[0] = 1
    
    for num in nums:
        prefix_sum += num
        count += sum_count[prefix_sum - k]
        sum_count[prefix_sum] += 1
    
    return count

# 2. Check Subarray Sum
def check_subarray_sum(nums, k):
    sum_index = {0: -1}
    prefix_sum = 0
    
    for i, num in enumerate(nums):
        prefix_sum += num
        remainder = prefix_sum % k
        if remainder in sum_index:
            if i - sum_index[remainder] > 1:
                return True
        else:
            sum_index[remainder] = i
    
    return False

# 3. Max Subarray Len
def max_subarray_len(nums, k):
    sum_index = {0: -1}
    prefix_sum = 0
    max_len = 0
    
    for i, num in enumerate(nums):
        prefix_sum += num
        if prefix_sum - k in sum_index:
            max_len = max(max_len, i - sum_index[prefix_sum - k])
        if prefix_sum not in sum_index:
            sum_index[prefix_sum] = i
    
    return max_len

# 4. Num Subarrays With Sum
def num_subarrays_with_sum(nums, goal):
    count = 0
    prefix_sum = 0
    sum_count = defaultdict(int)
    sum_count[0] = 1
    
    for num in nums:
        prefix_sum += num
        count += sum_count[prefix_sum - goal]
        sum_count[prefix_sum] += 1
    
    return count

# 5. Find Max Length
def find_max_length(nums):
    sum_index = {0: -1}
    prefix_sum = 0
    max_len = 0
    
    for i, num in enumerate(nums):
        prefix_sum += 1 if num == 1 else -1
        if prefix_sum in sum_index:
            max_len = max(max_len, i - sum_index[prefix_sum])
        else:
            sum_index[prefix_sum] = i
    
    return max_len

class NumArray:
    def __init__(self, nums):
        self.prefix_sum = [0] * (len(nums) + 1)
        for i, num in enumerate(nums):
            self.prefix_sum[i + 1] = self.prefix_sum[i] + num
    
    def sum_range(self, left, right):
        return self.prefix_sum[right + 1] - self.prefix_sum[left]

# 6. Product Except Self
def product_except_self(nums):
    result = [1] * len(nums)
    
    for i in range(1, len(nums)):
        result[i] = result[i - 1] * nums[i - 1]
    
    right = 1
    for i in range(len(nums) - 1, -1, -1):
        result[i] *= right
        right *= nums[i]
    
    return result

# 7. Subarrays Div By K
def subarrays_div_by_k(nums, k):
    count = 0
    prefix_sum = 0
    remainder_count = defaultdict(int)
    remainder_count[0] = 1
    
    for num in nums:
        prefix_sum += num
        remainder = prefix_sum % k
        count += remainder_count[remainder]
        remainder_count[remainder] += 1
    
    return count

class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

# 8. Path Sum
def path_sum(root, target_sum):
    def dfs(node, curr_sum, prefix_sums):
        if not node:
            return 0
        
        curr_sum += node.val
        count = prefix_sums.get(curr_sum - target_sum, 0)
        
        prefix_sums[curr_sum] = prefix_sums.get(curr_sum, 0) + 1
        count += dfs(node.left, curr_sum, prefix_sums)
        count += dfs(node.right, curr_sum, prefix_sums)
        prefix_sums[curr_sum] -= 1
        
        return count
    
    return dfs(root, 0, {0: 1})

# 9. Min Operations
def min_operations(nums, x):
    target = sum(nums) - x
    if target < 0:
        return -1
    if target == 0:
        return len(nums)
    
    sum_index = {0: -1}
    prefix_sum = 0
    max_len = -1
    
    for i, num in enumerate(nums):
        prefix_sum += num
        if prefix_sum - target in sum_index:
            max_len = max(max_len, i - sum_index[prefix_sum - target])
        if prefix_sum not in sum_index:
            sum_index[prefix_sum] = i
    
    return -1 if max_len == -1 else len(nums) - max_len

# 10. Range Sum Query Operations
def range_sum_query_operations(nums, queries):
    num_array = NumArray(nums)
    results = []
    for left, right in queries:
        results.append(num_array.sum_range(left, right))
    return results

def run_examples():
    print("=== Prefix Sum Examples ===")
    
    # Test 1: Subarray Sum Equals K
    print(f"Subarray Sum K([1,1,1], k=2): {subarray_sum([1,1,1], 2)}")
    
    # Test 2: Continuous Subarray Sum
    print(f"Check Subarray Sum([23,2,4,6,7], k=6): {check_subarray_sum([23,2,4,6,7], 6)}")
    
    # Test 3: Maximum Size Subarray Sum Equals k
    print(f"Max SubArray Len([1,-1,5,-2,3], k=3): {max_subarray_len([1,-1,5,-2,3], 3)}")
    
    # Test 4: Binary Subarrays With Sum
    print(f"Num Subarrays With Sum([1,0,1,0,1], goal=2): {num_subarrays_with_sum([1,0,1,0,1], 2)}")
    
    # Test 5: Contiguous Array
    print(f"Find Max Length([0,1]): {find_max_length([0,1])}")
    
    # Test 6: Range Sum Query
    num_array = NumArray([-2, 0, 3, -5, 2, -1])
    print(f"Sum Range(0,2): {num_array.sum_range(0, 2)}")
    print(f"Sum Range(2,5): {num_array.sum_range(2, 5)}")
    
    # Test 7: Product Except Self
    print(f"Product Except Self([1,2,3,4]): {product_except_self([1,2,3,4])}")
    
    # Test 8: Subarrays Divisible by K
    print(f"Subarrays Div By K([4,5,0,-2,-3,1], k=5): {subarrays_div_by_k([4,5,0,-2,-3,1], 5)}")
    
    # Test 9: Path Sum III
    root = TreeNode(10, TreeNode(5, TreeNode(3, TreeNode(3), TreeNode(-2)), TreeNode(2, None, TreeNode(1))), TreeNode(-3, None, TreeNode(11)))
    print(f"Path Sum III(targetSum=8): {path_sum(root, 8)}")
    
    # Test 10: Min Operations
    print(f"Min Operations([1,1,4,2,3], x=5): {min_operations([1,1,4,2,3], 5)}")

if __name__ == "__main__":
    run_examples()