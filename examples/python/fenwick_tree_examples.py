class FenwickTree:
    def __init__(self, n):
        self.n = n
        self.tree = [0] * (n + 1)
    
    def update(self, i, delta):
        while i <= self.n:
            self.tree[i] += delta
            i += i & (-i)
    
    def query(self, i):
        result = 0
        while i > 0:
            result += self.tree[i]
            i -= i & (-i)
        return result
    
    def range_query(self, l, r):
        return self.query(r) - self.query(l - 1)

class NumArray:
    def __init__(self, nums):
        self.nums = nums[:]
        self.bit = FenwickTree(len(nums))
        for i, num in enumerate(nums):
            self.bit.update(i + 1, num)
    
    def update(self, index, val):
        delta = val - self.nums[index]
        self.nums[index] = val
        self.bit.update(index + 1, delta)
    
    def sumRange(self, left, right):
        return self.bit.range_query(left + 1, right + 1)

# 1. Count Smaller
def count_smaller(nums):
    sorted_nums = sorted(set(nums))
    coord_map = {v: i + 1 for i, v in enumerate(sorted_nums)}
    
    bit = FenwickTree(len(sorted_nums))
    result = []
    
    for num in reversed(nums):
        idx = coord_map[num]
        count = bit.query(idx - 1)
        result.append(count)
        bit.update(idx, 1)
    
    return result[::-1]

# 2. Reverse Pairs
def reverse_pairs(nums):
    sorted_nums = sorted(set(nums + [2 * x for x in nums]))
    coord_map = {v: i + 1 for i, v in enumerate(sorted_nums)}
    
    bit = FenwickTree(len(sorted_nums))
    count = 0
    
    for num in reversed(nums):
        count += bit.query(coord_map[2 * num] - 1)
        bit.update(coord_map[num], 1)
    
    return count

class RangeSumQuery2D:
    def __init__(self, matrix):
        if not matrix or not matrix[0]:
            return
        self.m, self.n = len(matrix), len(matrix[0])
        self.matrix = [[0] * self.n for _ in range(self.m)]
        self.bit = [[0] * (self.n + 1) for _ in range(self.m + 1)]
        
        for i in range(self.m):
            for j in range(self.n):
                self.update(i, j, matrix[i][j])
    
    def update(self, row, col, val):
        delta = val - self.matrix[row][col]
        self.matrix[row][col] = val
        
        i = row + 1
        while i <= self.m:
            j = col + 1
            while j <= self.n:
                self.bit[i][j] += delta
                j += j & (-j)
            i += i & (-i)
    
    def query(self, row, col):
        if row < 0 or col < 0:
            return 0
        result = 0
        i = row + 1
        while i > 0:
            j = col + 1
            while j > 0:
                if i < len(self.bit) and j < len(self.bit[i]):
                    result += self.bit[i][j]
                j -= j & (-j)
            i -= i & (-i)
        return result
    
    def sumRegion(self, row1, col1, row2, col2):
        return (self.query(row2, col2) - self.query(row1 - 1, col2) - 
                self.query(row2, col1 - 1) + self.query(row1 - 1, col1 - 1))

# 3. Count Range Sum
def count_range_sum(nums, lower, upper):
    prefix_sums = [0]
    for num in nums:
        prefix_sums.append(prefix_sums[-1] + num)
    
    sorted_sums = sorted(set(prefix_sums))
    coord_map = {v: i + 1 for i, v in enumerate(sorted_sums)}
    
    bit = FenwickTree(len(sorted_sums))
    count = 0
    
    for prefix_sum in prefix_sums:
        left_idx = bisect_left(sorted_sums, prefix_sum - upper)
        right_idx = bisect_right(sorted_sums, prefix_sum - lower) - 1
        
        if left_idx <= right_idx:
            count += bit.range_query(left_idx + 1, right_idx + 1)
        
        bit.update(coord_map[prefix_sum], 1)
    
    return count

# 4. Bisect Left
def bisect_left(arr, x):
    left, right = 0, len(arr)
    while left < right:
        mid = (left + right) // 2
        if arr[mid] < x:
            left = mid + 1
        else:
            right = mid
    return left

# 5. Bisect Right
def bisect_right(arr, x):
    left, right = 0, len(arr)
    while left < right:
        mid = (left + right) // 2
        if arr[mid] <= x:
            left = mid + 1
        else:
            right = mid
    return left

class CountIntervals:
    def __init__(self):
        self.intervals = []
        self.bit = FenwickTree(10**6)
    
    def add(self, left, right):
        self.intervals.append((left, right))
        for i in range(left, right + 1):
            self.bit.update(i, 1)
    
    def count(self):
        return len(set().union(*[set(range(l, r + 1)) for l, r in self.intervals]))

# 6. Create Sorted Array
def create_sorted_array(instructions):
    MOD = 10**9 + 7
    sorted_nums = sorted(set(instructions))
    coord_map = {v: i + 1 for i, v in enumerate(sorted_nums)}
    
    bit = FenwickTree(len(sorted_nums))
    cost = 0
    
    for num in instructions:
        idx = coord_map[num]
        smaller = bit.query(idx - 1)
        larger = bit.query(len(sorted_nums)) - bit.query(idx)
        cost = (cost + min(smaller, larger)) % MOD
        bit.update(idx, 1)
    
    return cost

class FenwickTree2D:
    def __init__(self, m, n):
        self.m, self.n = m, n
        self.tree = [[0] * (n + 1) for _ in range(m + 1)]
    
    def update(self, x, y, delta):
        i = x + 1
        while i <= self.m:
            j = y + 1
            while j <= self.n:
                self.tree[i][j] += delta
                j += j & (-j)
            i += i & (-i)
    
    def query(self, x, y):
        result = 0
        i = x + 1
        while i > 0:
            j = y + 1
            while j > 0:
                result += self.tree[i][j]
                j -= j & (-j)
            i -= i & (-i)
        return result

# 7. Max Sum Rectangle
def max_sum_rectangle(matrix, k):
    m, n = len(matrix), len(matrix[0])
    max_sum = float('-inf')
    
    for top in range(m):
        temp = [0] * n
        for bottom in range(top, m):
            for j in range(n):
                temp[j] += matrix[bottom][j]
            
            # Find max subarray sum <= k
            prefix_sums = [0]
            for val in temp:
                prefix_sums.append(prefix_sums[-1] + val)
            
            for i in range(len(prefix_sums)):
                for j in range(i + 1, len(prefix_sums)):
                    current_sum = prefix_sums[j] - prefix_sums[i]
                    if current_sum <= k:
                        max_sum = max(max_sum, current_sum)
    
    return max_sum

# 8. Range Add Query
def range_add_query(length, updates):
    bit = FenwickTree(length)
    
    for left, right, val in updates:
        bit.update(left + 1, val)
        if right + 2 <= length:
            bit.update(right + 2, -val)
    
    result = []
    for i in range(length):
        result.append(bit.query(i + 1))
    return result

# 9. Count Inversions
def count_inversions(nums):
    sorted_nums = sorted(set(nums))
    coord_map = {v: i + 1 for i, v in enumerate(sorted_nums)}
    
    bit = FenwickTree(len(sorted_nums))
    count = 0
    
    for num in reversed(nums):
        idx = coord_map[num]
        count += bit.query(idx - 1)
        bit.update(idx, 1)
    
    return count

# 10. Run Examples
def run_examples():
    print("=== Fenwick Tree Examples ===")
    
    # Test 1: Range Sum Query
    nums = [1, 3, 5, 7, 9, 11]
    num_array = NumArray(nums)
    print(f"Sum Range [1,3]: {num_array.sumRange(1, 3)}")
    num_array.update(1, 10)
    print(f"Sum Range [1,3] after update: {num_array.sumRange(1, 3)}")
    
    # Test 2: Count Smaller Numbers
    nums = [5, 2, 6, 1]
    print(f"Count Smaller: {count_smaller(nums)}")
    
    # Test 3: Reverse Pairs
    nums = [1, 3, 2, 3, 1]
    print(f"Reverse Pairs: {reverse_pairs(nums)}")
    
    # Test 4: 2D Range Sum Query
    matrix = [[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5]]
    range_sum_2d = RangeSumQuery2D(matrix)
    print(f"2D Sum Region [1,1,2,2]: {range_sum_2d.sumRegion(1, 1, 2, 2)}")
    
    # Test 5: Create Sorted Array
    instructions = [1, 5, 6, 2]
    print(f"Create Sorted Array Cost: {create_sorted_array(instructions)}")
    
    # Test 6: Range Add Query
    updates = [[1, 3, 2], [2, 4, 3], [0, 2, -2]]
    print(f"Range Add Query: {range_add_query(5, updates)}")
    
    # Test 7: Count Range Sum
    nums = [-2, 5, -1]
    lower, upper = -2, 2
    print(f"Count Range Sum: {count_range_sum(nums, lower, upper)}")
    
    # Test 8: 2D Fenwick Tree
    bit_2d = FenwickTree2D(3, 3)
    bit_2d.update(1, 1, 5)
    bit_2d.update(2, 2, 3)
    print(f"2D Fenwick Query (2,2): {bit_2d.query(2, 2)}")
    
    # Test 9: Max Sum Rectangle
    matrix = [[1, 0, 1], [0, -2, 3]]
    k = 2
    print(f"Max Sum Rectangle <= {k}: {max_sum_rectangle(matrix, k)}")
    
    # Test 10: Count Intervals
    counter = CountIntervals()
    counter.add(2, 3)
    counter.add(4, 5)
    counter.add(6, 7)
    print(f"Count Intervals: {counter.count()}")

if __name__ == "__main__":
    run_examples()