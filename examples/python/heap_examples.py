import heapq
from collections import defaultdict, Counter

# 1. Find Kth Largest
def find_kth_largest(nums, k):
    heap = []
    for num in nums:
        heapq.heappush(heap, num)
        if len(heap) > k:
            heapq.heappop(heap)
    return heap[0]

# 2. Top K Frequent
def top_k_frequent(nums, k):
    count = Counter(nums)
    heap = []
    for num, freq in count.items():
        heapq.heappush(heap, (freq, num))
        if len(heap) > k:
            heapq.heappop(heap)
    return [num for freq, num in heap]

class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# 3. Find Median Data Stream
def find_median_data_stream():
    mf = MedianFinder()
    mf.add_num(1)
    mf.add_num(2)
    return mf.find_median()

# 4. Merge K Lists
def merge_k_lists(lists):
    heap = []
    for i, node in enumerate(lists):
        if node:
            heapq.heappush(heap, (node.val, i, node))
    
    dummy = ListNode(0)
    curr = dummy
    while heap:
        val, i, node = heapq.heappop(heap)
        curr.next = node
        curr = curr.next
        if node.next:
            heapq.heappush(heap, (node.next.val, i, node.next))
    return dummy.next

class MedianFinder:
    def __init__(self):
        self.max_heap = []  # for smaller half
        self.min_heap = []  # for larger half
    
    def add_num(self, num):
        heapq.heappush(self.max_heap, -num)
        heapq.heappush(self.min_heap, -heapq.heappop(self.max_heap))
        if len(self.min_heap) > len(self.max_heap):
            heapq.heappush(self.max_heap, -heapq.heappop(self.min_heap))
    
    def find_median(self):
        if len(self.max_heap) > len(self.min_heap):
            return -self.max_heap[0]
        return (-self.max_heap[0] + self.min_heap[0]) / 2.0

# 5. K Closest
def k_closest(points, k):
    heap = []
    for x, y in points:
        dist = -(x*x + y*y)  # negative for max heap
        heapq.heappush(heap, (dist, x, y))
        if len(heap) > k:
            heapq.heappop(heap)
    return [[x, y] for dist, x, y in heap]

# 6. Kth Smallest
def kth_smallest(matrix, k):
    heap = []
    for i in range(len(matrix)):
        heapq.heappush(heap, (matrix[i][0], i, 0))
    
    for _ in range(k - 1):
        val, row, col = heapq.heappop(heap)
        if col + 1 < len(matrix[0]):
            heapq.heappush(heap, (matrix[row][col + 1], row, col + 1))
    return heap[0][0]

# 7. Last Stone Weight
def last_stone_weight(stones):
    heap = [-stone for stone in stones]
    heapq.heapify(heap)
    
    while len(heap) > 1:
        first = -heapq.heappop(heap)
        second = -heapq.heappop(heap)
        if first != second:
            heapq.heappush(heap, -(first - second))
    return -heap[0] if heap else 0

# 8. Least Interval
def least_interval(tasks, n):
    count = Counter(tasks)
    heap = [-freq for freq in count.values()]
    heapq.heapify(heap)
    
    time = 0
    while heap:
        temp = []
        for _ in range(n + 1):
            if heap:
                freq = heapq.heappop(heap)
                if freq < -1:
                    temp.append(freq + 1)
            time += 1
            if not heap and not temp:
                break
        for freq in temp:
            heapq.heappush(heap, freq)
    return time

# 9. Nth Ugly Number
def nth_ugly_number(n):
    heap = [1]
    seen = {1}
    
    for _ in range(n):
        ugly = heapq.heappop(heap)
        for factor in [2, 3, 5]:
            next_ugly = ugly * factor
            if next_ugly not in seen:
                heapq.heappush(heap, next_ugly)
                seen.add(next_ugly)
    return ugly

# 10. Min Meeting Rooms
def min_meeting_rooms(intervals):
    intervals.sort()
    heap = []
    
    for start, end in intervals:
        if heap and heap[0] <= start:
            heapq.heappop(heap)
        heapq.heappush(heap, end)
    return len(heap)

def run_examples():
    print("=== Heap Examples ===")
    
    # Test 1: Kth Largest Element
    print(f"Kth Largest([3,2,1,5,6,4], k=2): {find_kth_largest([3,2,1,5,6,4], 2)}")
    
    # Test 2: Top K Frequent Elements
    print(f"Top K Frequent([1,1,1,2,2,3], k=2): {top_k_frequent([1,1,1,2,2,3], 2)}")
    
    # Test 3: Find Median Data Stream
    print(f"Find Median Data Stream: {find_median_data_stream()}")
    
    # Test 4: Median Finder
    mf = MedianFinder()
    mf.add_num(1)
    mf.add_num(2)
    print(f"Median after adding 1,2: {mf.find_median()}")
    
    # Test 5: K Closest Points
    points = [[1,3],[-2,2]]
    print(f"K Closest Points(k=1): {k_closest(points, 1)}")
    
    # Test 6: Kth Smallest in Matrix
    matrix = [[1,5,9],[10,11,13],[12,13,15]]
    print(f"Kth Smallest in Matrix(k=8): {kth_smallest(matrix, 8)}")
    
    # Test 7: Last Stone Weight
    print(f"Last Stone Weight([2,7,4,1,8,1]): {last_stone_weight([2,7,4,1,8,1])}")
    
    # Test 8: Task Scheduler
    print(f"Least Interval(['A','A','A','B','B','B'], n=2): {least_interval(['A','A','A','B','B','B'], 2)}")
    
    # Test 9: Ugly Number II
    print(f"10th Ugly Number: {nth_ugly_number(10)}")
    
    # Test 10: Meeting Rooms II
    meetings = [[0,30],[5,10],[15,20]]
    print(f"Min Meeting Rooms: {min_meeting_rooms(meetings)}")

if __name__ == "__main__":
    run_examples()