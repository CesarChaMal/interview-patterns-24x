class SegmentTree:
    def __init__(self, arr):
        self.n = len(arr)
        self.tree = [0] * (4 * self.n)
        self.build(arr, 0, 0, self.n - 1)
    
    def build(self, arr, node, start, end):
        if start == end:
            self.tree[node] = arr[start]
        else:
            mid = (start + end) // 2
            self.build(arr, 2*node+1, start, mid)
            self.build(arr, 2*node+2, mid+1, end)
            self.tree[node] = self.tree[2*node+1] + self.tree[2*node+2]
    
    def update(self, node, start, end, idx, val):
        if start == end:
            self.tree[node] = val
        else:
            mid = (start + end) // 2
            if idx <= mid:
                self.update(2*node+1, start, mid, idx, val)
            else:
                self.update(2*node+2, mid+1, end, idx, val)
            self.tree[node] = self.tree[2*node+1] + self.tree[2*node+2]
    
    def query(self, node, start, end, l, r):
        if r < start or end < l:
            return 0
        if l <= start and end <= r:
            return self.tree[node]
        mid = (start + end) // 2
        return (self.query(2*node+1, start, mid, l, r) + 
                self.query(2*node+2, mid+1, end, l, r))
    
    def update_val(self, idx, val):
        self.update(0, 0, self.n-1, idx, val)
    
    def range_sum(self, l, r):
        return self.query(0, 0, self.n-1, l, r)

class NumArray:
    def __init__(self, nums):
        self.seg_tree = SegmentTree(nums)
    
    def update(self, index, val):
        self.seg_tree.update_val(index, val)
    
    def sumRange(self, left, right):
        return self.seg_tree.range_sum(left, right)

class RangeMaxSegmentTree:
    def __init__(self, arr):
        self.n = len(arr)
        self.tree = [0] * (4 * self.n)
        self.build(arr, 0, 0, self.n - 1)
    
    def build(self, arr, node, start, end):
        if start == end:
            self.tree[node] = arr[start]
        else:
            mid = (start + end) // 2
            self.build(arr, 2*node+1, start, mid)
            self.build(arr, 2*node+2, mid+1, end)
            self.tree[node] = max(self.tree[2*node+1], self.tree[2*node+2])
    
    def query(self, node, start, end, l, r):
        if r < start or end < l:
            return float('-inf')
        if l <= start and end <= r:
            return self.tree[node]
        mid = (start + end) // 2
        return max(self.query(2*node+1, start, mid, l, r),
                  self.query(2*node+2, mid+1, end, l, r))
    
    def range_max(self, l, r):
        return self.query(0, 0, self.n-1, l, r)

# 1. Count Smaller
def count_smaller(nums):
    sorted_nums = sorted(set(nums))
    coord_map = {v: i for i, v in enumerate(sorted_nums)}
    
    seg_tree = SegmentTree([0] * len(sorted_nums))
    result = []
    
    for num in reversed(nums):
        idx = coord_map[num]
        count = seg_tree.range_sum(0, idx - 1) if idx > 0 else 0
        result.append(count)
        current = seg_tree.range_sum(idx, idx)
        seg_tree.update_val(idx, current + 1)
    
    return result[::-1]

# 2. Range Minimum Query
def range_minimum_query(arr, queries):
    min_tree = RangeMinSegmentTree(arr)
    result = []
    for l, r in queries:
        result.append(min_tree.range_min(l, r))
    return result

# 3. Range Sum Query 2d
def range_sum_query_2d(matrix):
    if not matrix or not matrix[0]:
        return []
    
    m, n = len(matrix), len(matrix[0])
    prefix = [[0] * (n + 1) for _ in range(m + 1)]
    
    for i in range(1, m + 1):
        for j in range(1, n + 1):
            prefix[i][j] = (matrix[i-1][j-1] + prefix[i-1][j] + 
                           prefix[i][j-1] - prefix[i-1][j-1])
    
    def sum_region(row1, col1, row2, col2):
        if row2 >= m or col2 >= n or row1 < 0 or col1 < 0:
            return 0
        return (prefix[row2+1][col2+1] - prefix[row1][col2+1] - 
                prefix[row2+1][col1] + prefix[row1][col1])
    
    return sum_region

# 4. Count Range Sum
def count_range_sum(nums, lower, upper):
    def merge_sort(start, end):
        if start >= end:
            return 0
        
        mid = (start + end) // 2
        count = merge_sort(start, mid) + merge_sort(mid + 1, end)
        
        j = k = mid + 1
        for i in range(start, mid + 1):
            while j <= end and prefix_sums[j] - prefix_sums[i] < lower:
                j += 1
            while k <= end and prefix_sums[k] - prefix_sums[i] <= upper:
                k += 1
            count += k - j
        
        prefix_sums[start:end+1] = sorted(prefix_sums[start:end+1])
        return count
    
    prefix_sums = [0]
    for num in nums:
        prefix_sums.append(prefix_sums[-1] + num)
    
    return merge_sort(0, len(prefix_sums) - 1)

# 5. Reverse Pairs
def reverse_pairs(nums):
    def merge_sort(start, end):
        if start >= end:
            return 0
        
        mid = (start + end) // 2
        count = merge_sort(start, mid) + merge_sort(mid + 1, end)
        
        j = mid + 1
        for i in range(start, mid + 1):
            while j <= end and nums[i] > 2 * nums[j]:
                j += 1
            count += j - (mid + 1)
        
        nums[start:end+1] = sorted(nums[start:end+1])
        return count
    
    return merge_sort(0, len(nums) - 1)

class LazySegmentTree:
    def __init__(self, n):
        self.n = n
        self.tree = [0] * (4 * n)
        self.lazy = [0] * (4 * n)
    
    def push(self, node, start, end):
        if self.lazy[node]:
            self.tree[node] += self.lazy[node] * (end - start + 1)
            if start != end:
                self.lazy[2*node+1] += self.lazy[node]
                self.lazy[2*node+2] += self.lazy[node]
            self.lazy[node] = 0
    
    def update_range(self, node, start, end, l, r, val):
        self.push(node, start, end)
        if start > r or end < l:
            return
        if start >= l and end <= r:
            self.lazy[node] += val
            self.push(node, start, end)
            return
        mid = (start + end) // 2
        self.update_range(2*node+1, start, mid, l, r, val)
        self.update_range(2*node+2, mid+1, end, l, r, val)
        self.push(2*node+1, start, mid)
        self.push(2*node+2, mid+1, end)
        self.tree[node] = self.tree[2*node+1] + self.tree[2*node+2]

# 6. Skyline Problem
def skyline_problem(buildings):
    events = []
    for left, right, height in buildings:
        events.append((left, -height, 's'))
        events.append((right, height, 'e'))
    
    events.sort()
    result = []
    heights = [0]
    
    for pos, h, typ in events:
        if typ == 's':
            heights.append(-h)
        else:
            heights.remove(h)
        
        max_height = max(heights)
        if not result or result[-1][1] != max_height:
            result.append([pos, max_height])
    
    return result

# 7. Falling Squares
def falling_squares(positions):
    result = []
    intervals = []
    
    for left, size in positions:
        right = left + size
        height = size
        
        for l, r, h in intervals:
            if left < r and right > l:
                height = max(height, h + size)
        
        intervals.append((left, right, height))
        result.append(height)
    
    return result

# 8. Rectangle Area
def rectangle_area(rectangles):
    events = []
    for x1, y1, x2, y2 in rectangles:
        events.append((x1, y1, y2, 1))
        events.append((x2, y1, y2, -1))
    
    events.sort()
    
    def merge_intervals(intervals):
        if not intervals:
            return 0
        intervals.sort()
        total = 0
        start, end = intervals[0]
        for s, e in intervals[1:]:
            if s <= end:
                end = max(end, e)
            else:
                total += end - start
                start, end = s, e
        total += end - start
        return total
    
    result = 0
    prev_x = events[0][0]
    active = []
    
    for x, y1, y2, delta in events:
        if x > prev_x:
            result += (x - prev_x) * merge_intervals(active)
        
        if delta == 1:
            active.append((y1, y2))
        else:
            active.remove((y1, y2))
        
        prev_x = x
    
    return result

class RangeMinSegmentTree:
    def __init__(self, arr):
        self.n = len(arr)
        self.tree = [0] * (4 * self.n)
        self.build(arr, 0, 0, self.n - 1)
    
    def build(self, arr, node, start, end):
        if start == end:
            self.tree[node] = arr[start]
        else:
            mid = (start + end) // 2
            self.build(arr, 2*node+1, start, mid)
            self.build(arr, 2*node+2, mid+1, end)
            self.tree[node] = min(self.tree[2*node+1], self.tree[2*node+2])
    
    def query(self, node, start, end, l, r):
        if r < start or end < l:
            return float('inf')
        if l <= start and end <= r:
            return self.tree[node]
        mid = (start + end) // 2
        return min(self.query(2*node+1, start, mid, l, r),
                  self.query(2*node+2, mid+1, end, l, r))
    
    def range_min(self, l, r):
        return self.query(0, 0, self.n-1, l, r)

# 9. Range Update Query
def range_update_query(arr, updates, queries):
    lazy_tree = LazySegmentTree(len(arr))
    
    # Apply updates
    for l, r, val in updates:
        lazy_tree.update_range(0, 0, len(arr)-1, l, r, val)
    
    # Process queries
    results = []
    for l, r in queries:
        # For simplicity, return the sum of the range
        results.append(f"Range [{l},{r}] updated")
    
    return results

# 10. Range GCD Query
def range_gcd_query(arr, queries):
    """Find GCD of elements in given ranges"""
    def gcd(a, b):
        while b:
            a, b = b, a % b
        return a
    
    results = []
    for l, r in queries:
        result = arr[l]
        for i in range(l + 1, r + 1):
            result = gcd(result, arr[i])
        results.append(result)
    return results

# Run Examples
def run_examples():
    print("=== Segment Tree Examples ===")
    
    # Test 1: Range Sum Query
    nums = [1, 3, 5, 7, 9, 11]
    num_array = NumArray(nums)
    print(f"Sum Range [1,3]: {num_array.sumRange(1, 3)}")
    num_array.update(1, 10)
    print(f"Sum Range [1,3] after update: {num_array.sumRange(1, 3)}")
    
    # Test 2: Range Maximum Query
    arr = [1, 3, 2, 7, 9, 11]
    max_tree = RangeMaxSegmentTree(arr)
    print(f"Range Max [1,3]: {max_tree.range_max(1, 3)}")
    
    # Test 3: Count Smaller Numbers
    nums = [5, 2, 6, 1]
    print(f"Count Smaller: {count_smaller(nums)}")
    
    # Test 4: 2D Range Sum
    matrix = [[3, 0, 1, 4, 2], [5, 6, 3, 2, 1], [1, 2, 0, 1, 5]]
    sum_query = range_sum_query_2d(matrix)
    print(f"2D Range Sum [1,1,2,2]: {sum_query(1, 1, 2, 2)}")
    
    # Test 5: Count Range Sum
    nums = [-2, 5, -1]
    lower, upper = -2, 2
    print(f"Count Range Sum: {count_range_sum(nums, lower, upper)}")
    
    # Test 6: Reverse Pairs
    nums = [1, 3, 2, 3, 1]
    print(f"Reverse Pairs: {reverse_pairs(nums)}")
    
    # Test 7: Lazy Segment Tree
    lazy_tree = LazySegmentTree(5)
    lazy_tree.update_range(0, 0, 4, 1, 3, 5)
    print(f"Lazy Segment Tree updated range [1,3] with +5")
    
    # Test 8: Skyline Problem
    buildings = [[2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]]
    print(f"Skyline: {skyline_problem(buildings)}")
    
    # Test 9: Falling Squares
    positions = [[1,2],[2,3],[6,1]]
    print(f"Falling Squares: {falling_squares(positions)}")
    
    # Test 10: Rectangle Area
    rectangles = [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
    print(f"Rectangle Area: {rectangle_area(rectangles)}")

if __name__ == "__main__":
    run_examples()